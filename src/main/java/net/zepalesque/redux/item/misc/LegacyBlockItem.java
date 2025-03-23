package net.zepalesque.redux.item.misc;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.util.Lazy;
import net.zepalesque.redux.item.util.TooltipUtils;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;

public class LegacyBlockItem extends BlockItem {
    private static final Component LEGACY = Component.translatable("tooltip.aether_redux.legacy_item").withStyle(ChatFormatting.YELLOW, ChatFormatting.BOLD);
    @Nullable
    private final Supplier<? extends ItemLike> replacement, counterpart;
    private final Lazy<ItemStack> repStack, counterStack;
    private final Lazy<Collection<Component>> info;

    private LegacyBlockItem(Block block, Properties properties, @Nullable Supplier<? extends ItemLike> replacement, @Nullable Supplier<? extends ItemLike> counterpart) {
        super(block, properties);
        this.replacement = replacement;
        this.counterpart = counterpart;

        this.repStack = replacement == null ? null : () -> replacement.get().asItem().getDefaultInstance();
        this.counterStack = counterpart == null ? null : () -> counterpart.get().asItem().getDefaultInstance();

        this.info = () -> {
            Component replace = this.repStack == null ?
                    Component.translatable("tooltip.aether_redux.legacy_deletion").withStyle(ChatFormatting.GRAY) :
                    Component.translatable("tooltip.aether_redux.legacy_replacement", name(this.repStack.get())
                    ).withStyle(ChatFormatting.GRAY);

            @Nullable Component counter = this.counterStack == null ?
                    null :
                    Component.translatable("tooltip.aether_redux.legacy_counterpart", name(this.counterStack.get())
                    ).withStyle(ChatFormatting.GRAY);

            if (counter == null) return ImmutableList.of(replace);
            else return ImmutableList.of(replace, counter);
        };
    }

    // Creation methods

    public static LegacyBlockItem toDelete(Block block, Properties properties) {
        return new LegacyBlockItem(block, properties, null, null);
    }

    public static LegacyBlockItem toDelete(Block block, Properties properties, Supplier<? extends ItemLike> counterpart) {
        return new LegacyBlockItem(block, properties, null, counterpart);
    }

    public static LegacyBlockItem toReplace(Block block, Properties properties, Supplier<? extends ItemLike> replacement) {
        return new LegacyBlockItem(block, properties, replacement, null);
    }

    public static LegacyBlockItem toReplace(Block block, Properties properties, Supplier<? extends ItemLike> replacement, Supplier<? extends ItemLike> counterpart) {
        return new LegacyBlockItem(block, properties, replacement, counterpart);
    }


    private static Component name(ItemStack stack) {
        return stack.getHoverName().copy().withStyle(stack.getRarity().getStyleModifier());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltips, TooltipFlag advanced) {
        tooltips.add(LEGACY);

        Collection<Component> info = TooltipUtils.shiftForInfo(this.info.get());
        tooltips.addAll(info);

        super.appendHoverText(stack, level, tooltips, advanced);
    }
}
