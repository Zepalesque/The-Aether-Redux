package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.event.ItemUseConvertEvent;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.nitrogen.recipe.recipes.AbstractBlockStateRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.advancement.trigger.BlockStateRecipeTrigger;
import net.zepalesque.redux.advancement.trigger.InfuseItemTrigger;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.event.hook.StackingRecipeHelper;
import net.zepalesque.redux.recipe.InfusionRecipe;
import net.zepalesque.redux.recipe.ReduxRecipeTypes;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class RecipeListener {
    
    
    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public static void blockstateConversion(ItemUseConvertEvent event) {
        BlockPos pos = event.getPos();
        LevelAccessor level = event.getLevel();
        
        if (!level.isClientSide()) {
            if (event.getNewBlockState().getBlock() instanceof DoublePlantBlock) {
                if (pos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(pos.above()).getMaterial().isReplaceable()) {
                    DoublePlantBlock.placeAt(event.getLevel(), event.getNewBlockState(), event.getPos(), 3);
                } else {
                    event.setCanceled(true);
                    return;
                }
            }
            if (event.getEntity() instanceof ServerPlayer sp) BlockStateRecipeTrigger.INSTANCE.trigger(sp, event.getOldBlockState(), event.getNewBlockState(), (RecipeType<? extends AbstractBlockStateRecipe>) event.getRecipeType());
        }
    }
    @SubscribeEvent
    public static void onStackItem(ItemStackedOnOtherEvent event) {
        if (event.getClickAction() == ClickAction.SECONDARY && StackingRecipeHelper.stack(event, stack -> stack.is(AetherItems.AMBROSIUM_SHARD.get()), ReduxRecipeTypes.INFUSION.get())) {
            event.setCanceled(true);
        }
    }
}
