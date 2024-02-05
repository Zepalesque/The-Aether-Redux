package net.zepalesque.redux.block.natural.enchanted;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.natural.CustomBoundsFlowerBlock;
import net.zepalesque.redux.block.util.state.ReduxStates;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EnchantableFlowerBlock extends CustomBoundsFlowerBlock {
    public EnchantableFlowerBlock(VoxelShape shape, Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
        super(shape, effectSupplier, pEffectDuration, pProperties);
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
            if (level.getBlockState(facingPos).is(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get())) {
                return b.setValue(ReduxStates.ENCHANTED, true);
            }
            return b.setValue(ReduxStates.ENCHANTED, false);
        }
        return b;
    }
}
