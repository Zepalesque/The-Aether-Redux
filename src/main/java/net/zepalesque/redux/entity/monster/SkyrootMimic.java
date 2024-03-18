package net.zepalesque.redux.entity.monster;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.block.ReduxBlocks;

public class SkyrootMimic extends Mimic {
    public SkyrootMimic(EntityType<? extends Mimic> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean hurt(DamageSource damageSource, float amount) {
        if (!(damageSource.getDirectEntity() instanceof Mimic)) {
            if (damageSource.getDirectEntity() instanceof LivingEntity livingEntity && this.hurtTime == 0) {
                if (this.level instanceof ServerLevel level) {
                    for (int i = 0; i < 20; i++) {
                        level.sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, ReduxBlocks.SKYROOT_CHEST.get().defaultBlockState()), this.getX(), this.getY() + this.getBbHeight() / 1.5, this.getZ(), 1, this.getBbWidth() / 4.0, this.getBbHeight() / 4.0, this.getBbWidth() / 4.0, 0.05F);
                    }
                }
                if (!(livingEntity instanceof Player player) || !player.isCreative()) {
                    this.setTarget(livingEntity);
                }
            }
            return super.hurt(damageSource, amount);
        } else {
            return false;
        }
    }
}
