package net.zepalesque.redux.item.accessory;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.client.keys.ReduxKeys;

import java.util.List;

public class SolarEmblemItem extends AccessoryItem {

    private String keyID = null;

    public SolarEmblemItem(Properties properties) {
        super(properties);
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tagTooltips, ItemStack stack) {
        tagTooltips.add(Component.empty());
        tagTooltips.add(Component.translatable("curios.modifiers.aether_accessory").withStyle(ChatFormatting.GOLD));
        String name = ReduxKeys.SHOOT_FIREBALL.getKey().getDisplayName().getString();
        if (keyID == null || !keyID.equalsIgnoreCase(name)) {
            keyID = name.toUpperCase();
        }
        tagTooltips.add(Component.translatable("gui.aether_redux.solar_emblem_fireball", keyID).withStyle(ChatFormatting.BLUE));
        return super.getAttributesTooltip(tagTooltips, stack);
    }


}
