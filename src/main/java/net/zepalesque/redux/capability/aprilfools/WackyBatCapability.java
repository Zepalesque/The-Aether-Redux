package net.zepalesque.redux.capability.aprilfools;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.WackyBatSyncPacket;
import org.apache.commons.lang3.tuple.Triple;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class WackyBatCapability implements WackyBat {

    private static final TargetingConditions BAT_RESTING_TARGETING = TargetingConditions.forNonCombat().range(4.0D);

    @Nullable
    private BlockPos targetPosition;

    boolean resting;

    private final LivingEntity mob;

    public WackyBatCapability(LivingEntity mob) {
        this.mob = mob;
    }

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("resting", Triple.of(Type.BOOLEAN, (obj) -> this.setResting((boolean) obj), this::isResting))
    );


    @Override
    public LivingEntity getEntity() {
        return this.mob;
    }

    @Override
    public void tick() {
        if (this.mob.isEffectiveAi() && !this.mob.isDeadOrDying() && !this.mob.isRemoved()) {
            BlockPos blockpos = this.mob.blockPosition();
            BlockPos blockpos1 = blockpos.below();
            if (this.isResting()) {
                if (this.mob.level().getBlockState(blockpos1).isRedstoneConductor(this.mob.level(), blockpos)) {
                    if (this.mob.getRandom().nextInt(200) == 0) {
                        this.mob.yHeadRot = (float) this.mob.getRandom().nextInt(360);
                    }

                    if (this.mob.level().getNearestPlayer(BAT_RESTING_TARGETING, this.mob) != null) {
                        this.setSynched(Direction.CLIENT, "resting", false);
                    }
                } else {
                    this.setSynched(Direction.CLIENT, "resting", false);
                }
            } else {
                if (this.targetPosition != null && (!this.mob.level().isEmptyBlock(this.targetPosition) || this.targetPosition.getY() <= this.mob.level().getMinBuildHeight())) {
                    this.targetPosition = null;
                }

                if (this.targetPosition == null || this.mob.getRandom().nextInt(30) == 0 || this.targetPosition.closerToCenterThan(this.mob.position(), 2.0D)) {
                    this.targetPosition = BlockPos.containing(this.mob.getX() + (double) this.mob.getRandom().nextInt(7) - (double) this.mob.getRandom().nextInt(7), this.mob.getY() + (double)this.mob.getRandom().nextInt(7) - (double)this.mob.getRandom().nextInt(5), this.mob.getZ() + (double) this.mob.getRandom().nextInt(7) - (double) this.mob.getRandom().nextInt(7));
                }

                double d2 = (double) this.targetPosition.getX() + 0.5D - this.mob.getX();
                double d0 = (double) this.targetPosition.getY() + 0.4D - this.mob.getY();
                double d1 = (double) this.targetPosition.getZ() + 0.5D - this.mob.getZ();
                Vec3 vec3 = this.mob.getDeltaMovement();
                Vec3 vec31 = vec3.add((Math.signum(d2) * 0.5D - vec3.x) * (double) 0.1F, (Math.signum(d0) * (double) 0.5F - vec3.y) * (double) 0.1F, (Math.signum(d1) * 0.5D - vec3.z) * (double) 0.1F);
                this.mob.setDeltaMovement(vec31);
                float f = (float) (Mth.atan2(vec31.z, vec31.x) * (double) (180F / (float) Math.PI)) - 90.0F;
                float f1 = Mth.wrapDegrees(f - this.mob.getYRot());
                this.mob.zza = 0.5F;
                this.mob.setYRot(this.mob.getYRot() + f1);
                if (this.mob.getRandom().nextInt(100) == 0 && this.mob.level().getBlockState(blockpos1).isRedstoneConductor(this.mob.level(), blockpos1)) {
                    this.setSynched(Direction.CLIENT, "resting", true);
                }
            }
        }
    }

    @Override
    public boolean isResting() {
        return this.resting;
    }

    @Override
    public void setResting(boolean rest) {
        this.resting = rest;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    @Override
    public BasePacket getSyncPacket(String s, Type type, Object o) {
        return new WackyBatSyncPacket(this.mob.getId(), s, type, o);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return ReduxPacketHandler.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("resting", this.isResting());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("resting")) {
            this.setResting(tag.getBoolean("resting"));
        }
    }
}
