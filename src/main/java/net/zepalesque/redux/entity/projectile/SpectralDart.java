package net.zepalesque.redux.entity.projectile;

import com.aetherteam.aether.entity.projectile.dart.AbstractDart;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;

public class SpectralDart extends AbstractDart {
    public SpectralDart(EntityType<? extends SpectralDart> type, Level level) {
        super(type, level, ReduxItems.SPECTRAL_DART);
        this.setBaseDamage(3.0);
    }

    public SpectralDart(Level level) {
        super(ReduxEntityTypes.SPECTRAL_DART.get(), level, ReduxItems.SPECTRAL_DART);
        this.setBaseDamage(3.0);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.level().isClientSide && !this.inGround) {
            this.level().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);
        MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.GLOWING, 200, 0);
        pLiving.addEffect(mobeffectinstance, this.getEffectSource());
    }


}
