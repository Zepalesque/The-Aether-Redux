package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Slime;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Swet.class)
public abstract class SwetMixin extends SlimeMixin {

    @Override
    protected void redux$push(Entity entity, CallbackInfo ci) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            ci.cancel();
        } else {
            super.redux$push(entity, ci);
        }
    }

    @Inject(method = "setSize", at = @At("HEAD"))
    protected void redux$setSize(int size, boolean resetHealth, CallbackInfo ci) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            int i = Mth.clamp(size, 1, 127);
            this.entityData.set(ID_SIZE, i);
            this.reapplyPosition();
            this.refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double) (i * i));
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) (0.2F + 0.1F * (float) i));
//        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)i);
            if (resetHealth) {
                this.setHealth(this.getMaxHealth());
            }

            this.xpReward = i;
        }
    }

    @Inject(method = "getSize", at = @At("RETURN"), cancellable = true)
    protected void redux$getSize(CallbackInfoReturnable<Integer> cir) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            cir.setReturnValue(this.entityData.get(ID_SIZE));
        }
    }

    @Override
    protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            cir.setReturnValue(false);
        } else {
            super.redux$isPushable(cir);
        }
    }
}
