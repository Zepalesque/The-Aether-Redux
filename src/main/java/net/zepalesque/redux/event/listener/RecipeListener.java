package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.event.ItemUseConvertEvent;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ItemStackedOnOtherEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.advancement.trigger.BlockStateRecipeTrigger;
import net.zepalesque.redux.advancement.trigger.InfuseItemTrigger;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.recipe.InfusionRecipe;
import net.zepalesque.redux.recipe.ReduxRecipeTypes;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class RecipeListener {


    @SubscribeEvent
    public static void blockstateConversion(ItemUseConvertEvent event)
    {
        if (!event.getLevel().isClientSide() && event.getEntity() instanceof ServerPlayer sp)
        {
            BlockStateRecipeTrigger.INSTANCE.trigger(sp, event.getOldBlockState(), event.getNewBlockState(), event.getRecipeType());
        }
    }

    @SubscribeEvent
    public static void onStackItem(ItemStackedOnOtherEvent event) {
        // These seem to be inverted for whatever reason?
        ItemStack carried = event.getStackedOnItem();
        ItemStack stackedOn = event.getCarriedItem();
        Level level = event.getPlayer().level();
        Player player = event.getPlayer();
        Slot slot = event.getSlot();
        if (event.getClickAction() == ClickAction.SECONDARY && carried.is(AetherItems.AMBROSIUM_SHARD.get())) {
            for (InfusionRecipe recipe : level.getRecipeManager().getAllRecipesFor(ReduxRecipeTypes.INFUSION.get())) {
                if (recipe != null) {
                    if (recipe.matches(level, stackedOn)) {
                        ItemStack newStack = recipe.getResultStack(stackedOn);
                        if (newStack != null) {
                            if (!level.isClientSide()) {
                                InfuseItemTrigger.INSTANCE.trigger((ServerPlayer) player, stackedOn, newStack);
                            }
                            if (stackedOn.getCount() <= 1) {
                                slot.set(newStack);
                            } else {
                                stackedOn.shrink(1);
                                newStack.setCount(1);
                                boolean flag = player.getInventory().add(newStack);
                                if (!flag) {
                                    double d0 = player.getEyeY() - (double) 0.3F;
                                    ItemEntity itementity = new ItemEntity(level, player.getX(), d0, player.getZ(), newStack);
                                    itementity.setPickUpDelay(40);
                                    level.addFreshEntity(itementity);
                                } else {
                                    player.containerMenu.broadcastChanges();
                                }
                            }
                            carried.shrink(1);
                            slot.setChanged();
                            event.setCanceled(true);
                        }
                        break;
                    }
                }
            }
        }
    }
}
