package net.zepalesque.redux.item.accessory;

import com.aetherteam.aether.item.accessories.AccessoryItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AbilityTooltipMiscItem extends AccessoryItem {


    protected final String[] abilities;

    public AbilityTooltipMiscItem(Properties properties, String... pAbilities) {
        super(properties);
        this.abilities = pAbilities;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tagTooltips, ItemStack stack) {
        tagTooltips.add(Component.empty());
        tagTooltips.add(Component.translatable("curios.modifiers.aether_accessory").withStyle(ChatFormatting.GOLD));
        for (String ability : this.abilities)
        {
            tagTooltips.add(Component.translatable("gui.aether_redux." + ability).withStyle(ChatFormatting.BLUE));
        }
        return super.getAttributesTooltip(tagTooltips, stack);
    }
}