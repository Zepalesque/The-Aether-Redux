package net.zepalesque.redux.item.accessory;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import top.theillusivec4.curios.api.SlotContext;

public class AirboundCapeItem extends AbilityTooltipCapeItem {
    public AirboundCapeItem(String location, Properties properties, String... pAbilities) {
        super(location, properties, pAbilities);
    }

    @Override
    public void onEquip(SlotContext slotContext, ItemStack prevStack, ItemStack stack) {
        super.onEquip(slotContext, prevStack, stack);
        if (slotContext.entity() instanceof Player player) {
            ReduxPlayer.get(player).ifPresent(reduxPlayer ->
                    reduxPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setMaxAirJumps", 1)
            );
        }
    }

    @Override
    public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
        super.onUnequip(slotContext, newStack, stack);
        if (slotContext.entity() instanceof Player player) {
            ReduxPlayer.get(player).ifPresent((reduxPlayer) -> {
                reduxPlayer.setSynched(INBTSynchable.Direction.CLIENT, "setMaxAirJumps", 0);
            });
        }
    }
}
