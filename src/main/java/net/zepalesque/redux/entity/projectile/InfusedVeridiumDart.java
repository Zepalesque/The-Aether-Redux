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

public class InfusedVeridiumDart extends AbstractDart {
    public InfusedVeridiumDart(EntityType<? extends InfusedVeridiumDart> type, Level level) {
        super(type, level, ReduxItems.VERIDIUM_DART);
        this.setBaseDamage(2.0);
    }

    public InfusedVeridiumDart(Level level) {
        super(ReduxEntityTypes.INFUSED_VERIDIUM_DART.get(), level, ReduxItems.VERIDIUM_DART);
        this.setBaseDamage(2.0);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void tick() {
        super.tick();
        if (this.level.isClientSide && !this.inGround) {
            this.level.addParticle(ParticleTypes.INSTANT_EFFECT, this.getX(), this.getY(), this.getZ(), 0.0D, 0.0D, 0.0D);
        }

    }

    protected void doPostHurtEffects(LivingEntity pLiving) {
        super.doPostHurtEffects(pLiving);
        MobEffectInstance mobeffectinstance = new MobEffectInstance(MobEffects.GLOWING, 200, 0);
        pLiving.addEffect(mobeffectinstance, this.getEffectSource());
    }


}
