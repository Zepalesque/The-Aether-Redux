package net.zepalesque.redux.item.misc;

import com.aetherteam.aether.item.materials.behavior.ItemUseConversion;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.recipe.BlightedSporeRecipe;
import net.zepalesque.redux.recipe.ReduxRecipeTypes;

public class BlightedSporesItem extends Item implements ItemUseConversion<BlightedSporeRecipe>  {

    public BlightedSporesItem(Properties pProperties) {
        super(pProperties);
    }

    public InteractionResult useOn(UseOnContext context) {
        InteractionResult result = this.convertBlock(ReduxRecipeTypes.SPORE_BLIGHTING.get(), context);
        if (context.getLevel().isClientSide() && result == InteractionResult.SUCCESS) {
            context.getLevel().playSound(context.getPlayer(), context.getClickedPos(), ReduxSoundEvents.CONVERT_BLIGHTED_SPORES.get(), SoundSource.BLOCKS, 0.8F, 1.0F + (context.getLevel().getRandom().nextFloat() - context.getLevel().getRandom().nextFloat()) * 0.2F);
        }
        return result;
    }

}
