package net.zepalesque.redux.capability.player;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.ReduxPlayerSyncPacket;
import net.zepalesque.redux.util.player.AbilityUtil;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReduxPlayerCapability implements ReduxPlayer {
    private final Player player;

    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setMaxAirJumps", Triple.of(Type.INT, (object) -> this.setMaxAirJumps((int) object), this::getMaxAirJumps))
    );
    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    private final LoreBookModule lore;

    private int blightshadeCooldown;
    private int blightshadeEffectCooldown;

    int maxAirJumps = 0;

    int fireballCooldown;

    int ticksInAir = 0;

    int airJumps = 0;
    int prevTickAirJumps = 0;

    int airJumpCooldown = 0;
    private boolean secondFireball = false;

    public ReduxPlayerCapability(Player pPlayer) {
        this.player = pPlayer;
        this.lore = new LoreBookModule();
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public int getMaxAirJumps() {
        return maxAirJumps;
    }

    @Override
    public void setMaxAirJumps(int jumps) {
        this.maxAirJumps = jumps;
    }

    @Override
    public int getAirJumpsPerformed() {
        return airJumps;
    }

    public boolean increaseAirJumpCount()
    {
        if (this.airJumpCooldown == 0 && airJumps < maxAirJumps)
        {
            this.airJumps++;
            this.airJumpCooldown = 4;
            return true;
        }
        return false;
    }

    @Override
    public int getPrevTickAirJumps() {
        return this.prevTickAirJumps;
    }

    @Override
    public int getAirJumpCooldown() {
        return this.airJumpCooldown;
    }

    @Override
    public int ticksInAir() {
        return ticksInAir;
    }

    @Override
    public LoreBookModule getLoreModule() {
        return this.lore;
    }


    @Override
    public void tick() {
        if (this.blightshadeCooldown > 0)
        {
            this.blightshadeCooldown--;
        }
        if (this.blightshadeEffectCooldown > 0)
        {
            if (this.blightshadeEffectCooldown == 1)
            {
                this.getPlayer().addEffect(new MobEffectInstance(MobEffects.DARKNESS, 210, 0));
            }
            this.blightshadeEffectCooldown--;
        }

        this.prevTickAirJumps = airJumps;
        if (this.getPlayer().onGround()) {
            this.ticksInAir = 0;
            this.airJumps = 0;
            this.airJumpCooldown = 0;

            } else {
            this.ticksInAir++;

        }
        if (this.airJumpCooldown > 0) {
            this.airJumpCooldown--;
        }
        if (this.fireballCooldown > 0)
        {
            this.fireballCooldown--;
        }

    }

    @Override
    public boolean doubleJump() {
        if (this.increaseAirJumpCount())
        {
                AbilityUtil.doDoubleJumpMovement(this.getPlayer());
            return true;
        } else {
            return false;
        }
    }
    @Override
    public boolean fireballSetup() {
        if (this.fireballCooldown > 0)
        {
            return false;
        } else {
        this.fireballCooldown = this.player.isCreative() ? 4 : 40;
        return true;
        }
    }

    @Override
    public boolean canShootFireball() {
        return !this.player.level().isClientSide() && (this.fireballCooldown <= 0) && EquipmentUtil.hasCurio(this.player, ReduxItems.PHOENIX_EMBLEM.get());
    }

    public boolean doubleFireball()
    {
        if (this.secondFireball) {
            this.secondFireball = false;
            return true;
        } else {
            return false;
        }
    }


    @Override
    public int getBlightshadeCooldown() {
        return this.blightshadeCooldown;
    }
    @Override
    public boolean blightshade(BlockPos pos, AABB bounds) {
        if (!this.getPlayer().level().isClientSide()) {
            if (this.blightshadeCooldown > 0) {
                return false;
            } else {
                this.blightshadeCooldown = 100;
                ((ServerLevel)this.getPlayer().level()).sendParticles(ParticleTypes.WARPED_SPORE, bounds.getCenter().x(), bounds.getCenter().y() + 0.25, bounds.getCenter().z(), 100, 0.1, 0.1, 0.1, 1.0);
                this.getPlayer().level().playSound(null, pos, ReduxSoundEvents.BLIGHTSHADE_SPRAY.get(), SoundSource.BLOCKS, 0.8F, 0.9F + this.getPlayer().level().random.nextFloat() * 0.2F);
                this.blightshadeEffectCooldown = 10;
                return true;
            }
        } else { return false; }
    }
    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new ReduxPlayerSyncPacket(this.getPlayer().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return ReduxPacketHandler.INSTANCE;
    }
    @Override
    public void deserializeSynchableNBT(CompoundTag tag) {
        deserializeNBT(tag);
    }

    @Override
    public int fireballCooldown() {
        return this.fireballCooldown;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("max_jumps", this.maxAirJumps);
        tag.putInt("jumps", this.airJumps);
        tag.putInt("ticks_in_air", this.ticksInAir);
        tag.put("lore_module", this.lore.serializeNBT());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.maxAirJumps = nbt.getInt("max_jumps");
        this.airJumps = nbt.getInt("jumps");
        this.ticksInAir = nbt.getInt("ticks_in_air");
        this.lore.deserializeNBT(nbt.get("lore_module"));
    }


}