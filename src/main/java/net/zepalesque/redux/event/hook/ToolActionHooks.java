package net.zepalesque.redux.event.hook;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ToolAction;
import net.neoforged.neoforge.common.ToolActions;

import java.util.HashMap;
import java.util.Map;

public class ToolActionHooks {

    public static final Map<Block, Block> STRIPPABLES = new HashMap<>();

    public static final Map<Block, Block> FLATTENABLES = new HashMap<>();

    public static final Map<Block, Block> TILLABLES = new HashMap<>();


    public static BlockState setupToolActions(LevelAccessor accessor, BlockPos pos, BlockState old, ToolAction action) {
        Block oldBlock = old.getBlock();
        if (action == ToolActions.AXE_STRIP) {
            if (STRIPPABLES.containsKey(oldBlock)) {
                return STRIPPABLES.get(oldBlock).withPropertiesOf(old);
            }
        } else if (action == ToolActions.SHOVEL_FLATTEN) {
            if (FLATTENABLES.containsKey(oldBlock)) {
                return FLATTENABLES.get(oldBlock).withPropertiesOf(old);
            }
        } else if (action == ToolActions.HOE_TILL) {
            if (accessor.getBlockState(pos.above()).isAir()) {
                if (TILLABLES.containsKey(oldBlock)) {
                    return TILLABLES.get(oldBlock).withPropertiesOf(old);
                }
            }
        }
        return old;
    }
}
