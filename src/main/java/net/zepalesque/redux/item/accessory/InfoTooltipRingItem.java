package net.zepalesque.redux.item.accessory;

import com.aetherteam.aether.item.accessories.ring.RingItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.Supplier;

public class InfoTooltipRingItem extends RingItem {


    protected final String[] tooltips;

    public InfoTooltipRingItem(Supplier<? extends SoundEvent> ringSound, Properties properties, String... pAbilities) {
        super(ringSound, properties);
        this.tooltips = pAbilities;
    }

    @Override
    public List<Component> getAttributesTooltip(List<Component> tagTooltips, ItemStack stack) {
        tagTooltips.add(Component.empty());
        for (String ability : this.tooltips)
        {
            tagTooltips.add(Component.translatable("gui.aether_redux." + ability).withStyle(ChatFormatting.BLUE));
        }
        return super.getAttributesTooltip(tagTooltips, stack);
    }
}
