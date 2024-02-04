package net.zepalesque.redux.block.util.dispenser;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.level.block.DispenserBlock;

public class ShellShinglesDispenserBehavior extends OptionalDispenseItemBehavior {

    protected ItemStack execute(BlockSource source, ItemStack stack) {
        this.setSuccess(false);
        Item item = stack.getItem();
        if (item instanceof BlockItem) {
            Direction direction = source.getBlockState().getValue(DispenserBlock.FACING);
            BlockPos blockpos = source.getPos().relative(direction);

            try {
                this.setSuccess(((BlockItem)item).place(new DirectionalPlaceContext(source.getLevel(), blockpos, direction, stack, direction)).consumesAction());
            } catch (Exception exception) {
                LOGGER.error("Error trying to place shell shingles at {}", blockpos, exception);
            }
        }

        return stack;
    }
}
