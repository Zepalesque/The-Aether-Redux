package net.zepalesque.redux.item.accessory;

import com.aetherteam.aether.item.accessories.pendant.PendantItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class AbilityTooltipPendantItem extends PendantItem {


    protected final String[] abilities;

    public AbilityTooltipPendantItem(ResourceLocation pendantLocation, Supplier<? extends SoundEvent> ringSound, Properties properties, String... pAbilities) {
        super(pendantLocation, ringSound, properties);
        this.abilities = pAbilities;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tagTooltips, ItemStack stack) {
        tagTooltips.add(Component.empty());
        tagTooltips.add(Component.translatable("curios.modifiers.aether_pendant").withStyle(ChatFormatting.GOLD));
        for (String ability : this.abilities)
        {
            tagTooltips.add(Component.translatable("gui.aether_redux." + ability).withStyle(ChatFormatting.BLUE));
        }
        return super.getAttributesTooltip(tagTooltips, stack);
    }
}
