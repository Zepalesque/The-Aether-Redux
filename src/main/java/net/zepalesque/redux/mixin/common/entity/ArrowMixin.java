package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.zepalesque.redux.capability.arrow.SubzeroArrow;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.SubzeroArrowPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class ArrowMixin
{
    @Shadow
    protected boolean inGround;
    @Shadow
    protected int inGroundTime;

    @Inject(at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/projectile/Projectile;tick()V"), method = "tick")
    private void tick(CallbackInfo ci) {
        AbstractArrow arrow = (AbstractArrow) (Object) this;
        SubzeroArrow.get(arrow).ifPresent(subzeroArrow -> {
            if (subzeroArrow.isSubzeroArrow()) {
                subzeroArrow.tick();
                if (!arrow.level().isClientSide) {
                    ReduxPacketHandler.sendToAll(new SubzeroArrowPacket(arrow.getId(), true));
                } else if (!this.inGround) {
                    arrow.level().addParticle(ParticleTypes.SNOWFLAKE, arrow.getX(), arrow.getY(), arrow.getZ(), 0.0D, 0.0D, 0.0D);
                }
            }
        });
    }

}
