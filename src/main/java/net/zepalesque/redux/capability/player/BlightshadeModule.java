package net.zepalesque.redux.capability.player;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.INBTSerializable;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.BlightshadeParticlePacket;

public class BlightshadeModule implements INBTSerializable {

    private int blightshadeCooldown;
    private int blightshadeEffectCooldown;
    private final Player player;

    public BlightshadeModule(Player plr) {
        this.player = plr;
    }

    public void tick() {
        if (this.blightshadeCooldown > 0)
        {
            this.blightshadeCooldown--;
        }
        if (this.blightshadeEffectCooldown > 0)
        {
            if (this.blightshadeEffectCooldown == 1)
            {
                this.player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 210, 0));
            }
            this.blightshadeEffectCooldown--;
        }

    }

    public int getBlightshadeCooldown() {
        return this.blightshadeCooldown;
    }

    public boolean blightshade(BlockPos pos, AABB bounds, LivingEntity entity) {
        if (!this.player.level.isClientSide()) {
            if (this.blightshadeCooldown > 0) {
                return false;
            } else {
                this.blightshadeCooldown = 100;
                this.doParticles(bounds, entity);
                this.player.level.playSound(null, pos, ReduxSoundEvents.BLIGHTSHADE_SPRAY.get(), SoundSource.BLOCKS, 0.8F, 0.9F + this.player.level.random.nextFloat() * 0.2F);
                this.blightshadeEffectCooldown = 10;
                return true;
            }
        } else { return false; }
    }


    public void doParticles(AABB bounds, LivingEntity entity) {
        Vec3 pos = entity.position();
        ReduxPacketHandler.sendToNear(new BlightshadeParticlePacket(bounds), pos.x, pos.y, pos.z, 100D, entity.level.dimension());
    }




    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("cooldown", this.blightshadeCooldown);
        tag.putInt("effect_cooldown", this.blightshadeEffectCooldown);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (nbt instanceof CompoundTag tag) {
            this.blightshadeCooldown = tag.getInt("cooldown");
            this.blightshadeEffectCooldown = tag.getInt("effect_cooldown");
        }
    }
}
