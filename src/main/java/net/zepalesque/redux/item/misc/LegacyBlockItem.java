package net.zepalesque.redux.item.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.item.tools.VeridiumItem;
import net.zepalesque.redux.item.util.TooltipUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import com.google.common.base.Supplier;

public class LegacyBlockItem extends BlockItem {
    private final Supplier<? extends Block> replacement;

    public LegacyBlockItem(Block block, Supplier<? extends Block> replacement, Properties properties) {
        super(block, properties);
        this.replacement = replacement;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
        MutableComponent infusion = Component.translatable("tooltip.aether_redux.legacy_item").withStyle(ChatFormatting.GRAY);

        tooltips.add(infusion);
        Component info = TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(Component.translatable("tooltip.aether_redux.item_replacement", this.replacement.get().getName()).withStyle(ChatFormatting.GRAY));
        tooltips.add(info);
        super.appendHoverText(stack, level, tooltips, advanced);
    }
}
