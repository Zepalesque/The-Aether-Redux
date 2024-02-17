package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.SubzeroArrowPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class ArrowMixin
{
    @Shadow
    protected boolean inGround;

    @Inject(method = "tick", at = @At("TAIL"))
    private void tick(CallbackInfo ci) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        SubzeroArrow.get(arrow).ifPresent(subzeroArrow -> {
            if (subzeroArrow.isSubzeroArrow()) {
                subzeroArrow.tick();
                if (!arrow.level().isClientSide) {
                    ReduxPacketHandler.sendToAll(new SubzeroArrowPacket(arrow.getId(), true));
                }
            }
        });
    }

    // TODO: there may be a better way to do this, I can't remember how though but I feel like I vaguely remember a mixin type to replace a parameter or something
    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;addParticle(Lnet/minecraft/core/particles/ParticleOptions;DDDDDD)V", ordinal = 0))
    private boolean replaceParticle(Level instance, ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return this.particleCheck(instance, particleData, x, y, z, xSpeed, ySpeed, zSpeed);
    }

    @Unique
    private boolean particleCheck(Level instance, ParticleOptions particleData, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        LazyOptional<SubzeroArrow> subz = SubzeroArrow.get(arrow);

        if (subz.isPresent()
                && subz.orElseThrow(() -> new IllegalStateException("Subzero arrow capability was not present right after checking to ensure it's present??? This should not be possible!"))
                .isSubzeroArrow()) {
            instance.addParticle(ParticleTypes.SNOWFLAKE /* TODO (maybe?) */, x, y, z, xSpeed, ySpeed,  zSpeed);
            return false;
        }
        return true;
    }


}
