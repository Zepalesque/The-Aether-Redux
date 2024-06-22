package net.zepalesque.redux.block.natural.bush;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.data.ReduxTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomBoundsBushBlock extends AetherBushBlock {

    protected final VoxelShape shape;

    public CustomBoundsBushBlock(VoxelShape shape, Properties pProperties) {
        super(pProperties);
        this.shape = shape;
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return this.shape.move(vec3.x, vec3.y, vec3.z);
    }


    public static class Enchanted extends CustomBoundsBushBlock {

        public Enchanted(VoxelShape shape, Properties properties) {
            super(shape, properties);
            this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.ENCHANTED, false));
        }

        @Override
        protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
            super.createBlockStateDefinition(builder);
            builder.add(ReduxStates.ENCHANTED);
        }


        public BlockState setValues(Level level, BlockPos pos, BlockState state) {
            BlockPos below = pos.below();
            if (level.getBlockState(below).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
                return state.setValue(ReduxStates.ENCHANTED, true);
            }

            return state;
        }

        @Nullable
        @Override
        public BlockState getStateForPlacement(BlockPlaceContext context) {
            return setValues(context.getLevel(), context.getClickedPos(), super.getStateForPlacement(context));
        }

        @Override
        @NotNull
        public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
            BlockState b = super.updateShape(state, facing, facingState, level, currentPos, facingPos);
            if (b.hasProperty(ReduxStates.ENCHANTED) && facing == Direction.DOWN) {
                if (level.getBlockState(facingPos).is(ReduxTags.Blocks.ENCHANTED_GRASS_BLOCKS)) {
                    return b.setValue(ReduxStates.ENCHANTED, true);
                }
                return b.setValue(ReduxStates.ENCHANTED, false);
            }
            return b;
        }
    }
}
