package net.zepalesque.redux.item.misc;

import com.aetherteam.aether.item.materials.behavior.ItemUseConversion;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.recipe.ReduxRecipes;
import net.zepalesque.redux.recipe.recipes.WillowSporeRecipe;

public class WillowSporesItem extends Item implements ItemUseConversion<WillowSporeRecipe> {
    public WillowSporesItem(Properties properties) {
        super(properties);
    }
    
    @Override
    public InteractionResult useOn(UseOnContext context) {
	    var result = this.convertBlock(ReduxRecipes.WILLOW_SPORES.get(), context);
        if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS)
            context.getLevel().playSound(
                context.getPlayer(), context.getClickedPos(),
                ReduxSounds.WILLOW_SPORES_CONVERT.get(), SoundSource.BLOCKS,
                1.0F,
                3.0F + (
                    context.getLevel().getRandom().nextFloat()
                  - context.getLevel().getRandom().nextFloat()
                ) * 0.8F
            );
        return result;
    }
}