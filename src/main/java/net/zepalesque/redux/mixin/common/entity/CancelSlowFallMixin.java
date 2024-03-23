package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.FlyingCow;
import com.aetherteam.aether.entity.passive.Phyg;
import com.aetherteam.aether.entity.passive.WingedAnimal;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(WingedAnimal.class)
public class CancelSlowFallMixin {
    @WrapWithCondition(method = "tick", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/entity/passive/WingedAnimal;setDeltaMovement(DDD)V"))
    private boolean preventSlowFall(WingedAnimal instance, double x, double y, double z) {
        return instance.getType() != AetherEntityTypes.PHYG.get() && instance.getType() != AetherEntityTypes.FLYING_COW.get();
    }

}
