package net.zepalesque.redux.mixin.common.event;

import com.aetherteam.aether.event.hooks.DimensionHooks;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.advancement.trigger.FallFromAetherTrigger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = DimensionHooks.class, remap = false)
public class DimensionHooksMixin {

    @Inject(at = @At(value = "HEAD", shift = At.Shift.AFTER), method = "entityFell")
    private static void fall(Entity entity, CallbackInfoReturnable<Entity> cir) {
        if (entity instanceof Player player && !player.level().isClientSide() && player instanceof ServerPlayer sp)
        {
            FallFromAetherTrigger.INSTANCE.trigger(sp);
        }
    }
}
