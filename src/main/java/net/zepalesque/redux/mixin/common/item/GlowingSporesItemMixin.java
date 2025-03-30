package net.zepalesque.redux.mixin.common.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.item.crafting.RecipeType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import teamrazor.deepaether.item.misc.GlowingSporesItem;
import teamrazor.deepaether.recipe.GlowingSporesRecipe;

@Mixin(GlowingSporesItem.class)
public class GlowingSporesItemMixin {
    
    @Inject(method = "convertBlock(Lnet/minecraft/world/item/crafting/RecipeType;Lnet/minecraft/world/item/context/UseOnContext;)Lnet/minecraft/world/InteractionResult;", at = @At(value = "RETURN", ordinal = 0), cancellable = true, remap = false)
    protected <T extends GlowingSporesRecipe> void redux$convertBlock(RecipeType<T> recipeType, UseOnContext context, CallbackInfoReturnable<InteractionResult> cir) {
        if (context.getLevel().isClientSide()) {
            cir.setReturnValue(InteractionResult.SUCCESS);
        }
    }
    
}
