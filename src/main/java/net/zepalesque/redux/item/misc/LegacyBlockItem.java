package net.zepalesque.redux.item.misc;

import com.google.common.base.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.zepalesque.redux.item.util.TooltipUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class LegacyBlockItem extends BlockItem {
    private static final Component LEGACY = Component.translatable("tooltip.aether_redux.legacy_item").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
    private final Supplier<? extends Item> replacement;
    private final Lazy<ItemStack> stack;
    private final Lazy<Component> component;

    public LegacyBlockItem(Block block, Supplier<? extends Item> replacement, Properties properties) {
        super(block, properties);
        this.replacement = replacement;
        this.stack = () -> new ItemStack(this.replacement.get());
        this.component = () -> Component.translatable("tooltip.aether_redux.item_replacement", stack.get().getDisplayName()).withStyle(ChatFormatting.GRAY);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
        tooltips.add(LEGACY);
        tooltips.add(TooltipUtils.TOOLTIP_SHIFT_FOR_INFO.apply(this.component.get()));
        super.appendHoverText(stack, level, tooltips, advanced);
    }
}
