package net.zepalesque.redux.item.accessory;

import com.aetherteam.aether.item.accessories.ring.RingItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class AbilityTooltipRingItem extends RingItem {


    protected final String[] abilities;

    public AbilityTooltipRingItem(Supplier<? extends SoundEvent> ringSound, Properties properties, String... pAbilities) {
        super(ringSound, properties);
        this.abilities = pAbilities;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tagTooltips, ItemStack stack) {
        tagTooltips.add(Component.empty());
        tagTooltips.add(Component.translatable("curios.modifiers.aether_ring").withStyle(ChatFormatting.GOLD));
        for (String ability : this.abilities)
        {
            tagTooltips.add(Component.translatable("gui.aether_redux." + ability).withStyle(ChatFormatting.BLUE));
        }
        return super.getAttributesTooltip(tagTooltips, stack);
    }
}
