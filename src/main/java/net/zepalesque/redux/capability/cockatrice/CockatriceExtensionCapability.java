package net.zepalesque.redux.capability.cockatrice;

import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.ReduxCockatriceSyncPacket;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class CockatriceExtensionCapability implements CockatriceExtension {
    private final Cockatrice cockatrice;

    // Synched
    private boolean isShooting;
    // Server-Only
    private @Nullable Collection<Entity> nearby = new ArrayList<>();
    // Server-Only
    private int refreshTime = 0;
    // Server-Only
    private boolean wasMelee = false;
    // Client-Only
    private byte targetAnim;
    // Client-Only
    private byte prevTargetAnim;
    // Client-Only
    private byte legAnim;
    // Client-Only
    private byte prevLegAnim;

    public CockatriceExtensionCapability(Cockatrice pCockatrice) {
        this.cockatrice = pCockatrice;
    }


    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("shooting", Triple.of(Type.BOOLEAN, (obj) -> this.setShooting((boolean) obj), this::isShooting))
    );

    @Override
    public Cockatrice getCockatrice() {
        return this.cockatrice;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("targeting", this.isShooting());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("targeting")) {
            this.setShooting(tag.getBoolean("targeting"));
        }
    }

    public void setShooting(boolean shooting) {
            this.isShooting = shooting;
    }

    public boolean isShooting() {
        return this.isShooting;
    }

    public byte getTargetAnim() {
        return this.targetAnim;
    }

    public byte getPrevTargetAnim() {
        return this.prevTargetAnim;
    }

    public byte getLegAnim() {
        return this.legAnim;
    }

    public byte getPrevLegAnim() {
        return this.prevLegAnim;
    }

    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new ReduxCockatriceSyncPacket(this.getCockatrice().getId(), key, type, value);
    }
    @Override
    public SimpleChannel getPacketChannel() {
        return ReduxPacketHandler.INSTANCE;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    @Override
    public void tick() {
        this.handleLegAnim();
        this.handleTargetAnim();
        this.handleBurning();
    }

    public void handleBurning() {
        if (!this.cockatrice.level().isClientSide() && this.cockatrice.isAlive()) {
            if (this.isSunSensitive() && this.isSunBurnTick()) {
                this.cockatrice.setSecondsOnFire(8);
            }
        }
    }

    @Override
    public void handleTargetAnim() {
        if (ReduxConfig.COMMON.improved_cockatrice_behavior.get()) {
            if (this.getCockatrice().level().isClientSide()) {
                this.prevTargetAnim = this.targetAnim;
                if (this.isShooting && this.targetAnim < 10) {
                    this.targetAnim++;
                }
                if (!this.isShooting && this.targetAnim > 0) {
                    this.targetAnim--;
                }
            } else {

                // Server-side checks
                int refreshReset = 200;
                if (this.refreshTime <= 0)
                {
                    this.refreshNearby();
                    this.refreshTime = refreshReset;
                } else {
                    this.refreshTime--;
                }
                 if (!this.wasMelee && this.cockatrice.getTarget() != null && this.refreshTime < refreshReset) {
                    this.refreshNearby();
                }

                boolean hasInebriation = this.getCockatrice().getTarget() != null && (this.getCockatrice().getTarget().hasEffect(AetherEffects.INEBRIATION.get()) || EquipmentUtil.hasCurio(this.cockatrice.getTarget(), ReduxItems.FEATHER_OF_WARDING.get()));
                boolean inGroup = this.nearbyCount() >= 3;
                boolean shooting = (!hasInebriation && this.getCockatrice().getTarget() != null && !inGroup) || !ReduxConfig.COMMON.improved_cockatrice_behavior.get();
                if (!shooting && this.cockatrice.getTarget() != null) {
                    this.wasMelee = true;
                } else if (this.cockatrice.getTarget() == null) {
                    this.wasMelee = false;
                }
                this.setSynched(Direction.CLIENT, "shooting", shooting && !wasMelee);

            }
        }
    }

    @Override
    public boolean wasMelee()
    {
        return this.wasMelee;
    }

    @Override
    public void refreshNearby() {
        if (this.cockatrice.getTarget() != null) {
            Level level = this.cockatrice.level();
            AABB bounds = this.cockatrice.getBoundingBox().inflate(10.0D);

            this.nearby = level.getEntities(this.cockatrice, bounds, entity -> entity.getType() == AetherEntityTypes.COCKATRICE.get());
        } else {
            if (this.nearby != null) {
                this.nearby.clear();
            } else {
                this.nearby = new ArrayList<>();
            }
        }
    }

    @Override
    public @Nullable Collection<Entity> getNearby() {
        return this.nearby;
    }

    @Override
    public int nearbyCount() {
        return this.getNearby() != null ? this.getNearby().size() : 0;
    }


    @Override
    public void handleLegAnim() {
        if (this.getCockatrice().level().isClientSide()) {
            this.prevLegAnim = this.legAnim;
            if (!this.cockatrice.isEntityOnGround() && this.legAnim < 5) {
                this.legAnim++;
            }
            if (this.cockatrice.isEntityOnGround() && this.legAnim > 0) {
                this.legAnim--;
            }
        }
    }

    protected boolean isSunBurnTick() {
        if (this.cockatrice.level().isDay() && !this.cockatrice.level().isClientSide) {
            float f = this.cockatrice.getLightLevelDependentMagicValue();
            BlockPos blockpos = BlockPos.containing(this.cockatrice.getX(), this.cockatrice.getEyeY(), this.cockatrice.getZ());
            boolean flag = this.cockatrice.isInWaterRainOrBubble() || this.cockatrice.isInPowderSnow || this.cockatrice.wasInPowderSnow;
            if (f > 0.5F && this.cockatrice.level().random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && !flag && this.cockatrice.level().canSeeSky(blockpos)) {
                return true;
            }
        }

        return false;
    }
    protected boolean isSunSensitive() {
        return ReduxConfig.COMMON.cockatrice_burn_in_daylight.get();
    }

}