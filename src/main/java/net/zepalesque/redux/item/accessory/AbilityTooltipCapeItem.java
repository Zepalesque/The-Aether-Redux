package net.zepalesque.redux.item.accessory;

import com.aetherteam.aether.item.accessories.cape.CapeItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.Redux;

import java.util.List;

public class AbilityTooltipCapeItem extends CapeItem {


    protected final String[] abilities;

    public AbilityTooltipCapeItem(String location, Properties properties, String... pAbilities) {
        super(Redux.locate(location), properties);
        this.abilities = pAbilities;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tagTooltips, ItemStack stack) {
        tagTooltips.add(Component.empty());
        tagTooltips.add(Component.translatable("curios.modifiers.aether_cape").withStyle(ChatFormatting.GOLD));
        for (String ability : this.abilities)
        {
            tagTooltips.add(Component.translatable("gui.aether_redux." + ability).withStyle(ChatFormatting.BLUE));
        }
        return super.getAttributesTooltip(tagTooltips, stack);
    }
}
