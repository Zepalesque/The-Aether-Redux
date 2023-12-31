package net.zepalesque.redux.mixin.common.item;

import com.aetherteam.aether.item.food.GummySwetItem;
import net.minecraft.world.food.FoodProperties;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.item.food.ReduxFoods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GummySwetItem.class)
public abstract class   GummySwetItemMixin extends ItemMixin {

    @Override
    public void getFoodProperties(CallbackInfoReturnable<FoodProperties> cir) {
        if (ReduxConfig.COMMON.nerf_gummy_swets.get())
        {
            cir.setReturnValue(ReduxFoods.NERFED_GUMMY_SWET);
        }
    }
}
