package net.zepalesque.redux.item.accessories;

import com.aetherteam.aether.item.accessories.ring.RingItem;
import io.wispforest.accessories.api.slot.SlotType;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

public class AbilityTooltipRingItem extends RingItem {
	protected final String[] abilities;

	public AbilityTooltipRingItem(Holder<SoundEvent> ringSound, Properties properties, String... pAbilities) {
		super(ringSound, properties);
		this.abilities = pAbilities;
	}

	@Override
	public void getAttributesTooltip(
		ItemStack stack,
		SlotType type,
		List<Component> tooltips,
		TooltipContext tooltipContext,
		TooltipFlag tooltipType
	) {
		for (var ability : this.abilities) {
			tooltips.add(Component.translatable("gui.aether_redux." + ability).withStyle(ChatFormatting.BLUE));
		}
		super.getAttributesTooltip(stack, type, tooltips, tooltipContext, tooltipType);
	}
}