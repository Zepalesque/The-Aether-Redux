package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.ItemLike;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.entity.misc.Rebux;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Chicken.class)
public abstract class ChickenMixin extends MobMixin {

    @Inject(method = "getAmbientSound", at = @At("RETURN"), cancellable = true)
    protected void redux$getAmbientSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.hasCustomName() && "Quail".equals(this.getName().getString())) {
            cir.setReturnValue(ReduxSoundEvents.QUAIL_AMBIENT.get());
        }
    }

    @Inject(method = "getHurtSound", at = @At("RETURN"), cancellable = true)
    protected void redux$getHurtSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.hasCustomName() && "Quail".equals(this.getName().getString())) {
            cir.setReturnValue(ReduxSoundEvents.QUAIL_HURT.get());
        }
    }

    @Inject(method = "getDeathSound", at = @At("RETURN"), cancellable = true)
    protected void redux$getDeathSound(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.hasCustomName() && "Quail".equals(this.getName().getString())) {
            cir.setReturnValue(ReduxSoundEvents.QUAIL_DEATH.get());
        }
    }


    @WrapWithCondition(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/animal/Chicken;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"))
    private boolean openEvent(Chicken instance, ItemLike itemLike) {
        return this.redux$replaceEgg();
    }

    @Unique
    private boolean redux$replaceEgg() {
        if (this.hasCustomName() && "Quail".equals(this.getName().getString())) {
            Rebux rebux = new Rebux(this.level(), this.getX(), this.getY(), this.getZ(), 1);
            this.level().addFreshEntity(rebux);
            return false;
        }
        return true;
    }
}
