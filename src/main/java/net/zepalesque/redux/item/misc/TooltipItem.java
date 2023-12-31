package net.zepalesque.redux.item.misc;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.util.function.QuadConsumer;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TooltipItem extends Item {
    private final QuadConsumer<ItemStack, Level, List<Component>, TooltipFlag> tooltipConsumer;

    public TooltipItem(Properties pProperties, QuadConsumer<ItemStack, Level, List<Component>, TooltipFlag> tooltipAppendment) {
        super(pProperties);
        this.tooltipConsumer = tooltipAppendment;
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        this.tooltipConsumer.accept(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
