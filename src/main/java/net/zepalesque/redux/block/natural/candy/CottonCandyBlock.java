package net.zepalesque.redux.block.natural.candy;

import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class CottonCandyBlock extends AetherDoubleDropBlock {


    public CottonCandyBlock(Properties properties) {
        super(properties);
    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float dist) {
        entity.causeFallDamage(dist, 0.2F, level.damageSources().fall());
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockGetter blockgetter = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        BlockState blockstate = blockgetter.getBlockState(blockpos);
        return shouldSolidify(blockgetter, blockpos, blockstate) ? Blocks.AIR.defaultBlockState() : super.getStateForPlacement(context);
    }

    private static boolean shouldSolidify(BlockGetter level, BlockPos pos, BlockState state, net.minecraft.world.level.material.FluidState fluidState) {
        return state.canBeHydrated(level, pos, fluidState, pos) || touchesLiquid(level, pos, state);
    }

    private static boolean shouldSolidify(BlockGetter level, BlockPos pos, BlockState state) {
        return shouldSolidify(level, pos, state, level.getFluidState(pos));
    }

    private static boolean touchesLiquid(BlockGetter p_level, BlockPos p_pos, BlockState state) {
        boolean flag = false;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = p_pos.mutable();

        for(Direction direction : Direction.values()) {
            BlockState blockstate = p_level.getBlockState(blockpos$mutableblockpos);
            if (direction != Direction.DOWN || state.canBeHydrated(p_level, p_pos, blockstate.getFluidState(), blockpos$mutableblockpos)) {
                blockpos$mutableblockpos.setWithOffset(p_pos, direction);
                blockstate = p_level.getBlockState(blockpos$mutableblockpos);
                if (state.canBeHydrated(p_level, p_pos, blockstate.getFluidState(), blockpos$mutableblockpos) && !blockstate.isFaceSturdy(p_level, p_pos, direction.getOpposite())) {
                    flag = true;
                    break;
                }
            }
        }

        return flag;
    }

    private static boolean canSolidify(BlockState state) {
        return state.getFluidState().is(FluidTags.WATER);
    }

    /**
     * Update the provided state given the provided neighbor direction and neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific direction passed in.
     */
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return touchesLiquid(level, currentPos, state) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
    }
}