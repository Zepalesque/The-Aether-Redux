package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.GrassBlockTint;
import net.zepalesque.redux.block.util.ReduxStates;

import java.util.List;

public class ReduxAetherGrassBlock extends AetherGrassBlock {


    public ReduxAetherGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.GRASS_BLOCK_TINT, GrassBlockTint.TINTABLE));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.GRASS_BLOCK_TINT);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return setValues(context.getLevel(), context.getClickedPos(), super.getStateForPlacement(context));
    }

    public BlockState setValues(LevelAccessor level, BlockPos pos, BlockState state) {
        if (state != null && state.hasProperty(ReduxStates.GRASS_BLOCK_TINT) && !level.getBiome(pos).is(AetherTags.Biomes.IS_AETHER)) {
            return state.setValue(ReduxStates.GRASS_BLOCK_TINT, GrassBlockTint.AETHER_COLOR);
        }
        return state;
    }

    @Override
    public BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        return setValues(level, currentPos, super.updateShape(state, facing, facingState, level, currentPos, facingPos));
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        BlockPos abovePos = pos.above();
        Block grass = AetherBlocks.AETHER_GRASS_BLOCK.get();

        start:
        for (int i = 0; i < 128; ++i) {
            BlockPos blockPos = abovePos;

            for (int j = 0; j < i / 16; ++j) {
                blockPos = blockPos.offset(random.nextInt(3) - 1, (random.nextInt(3) - 1) * random.nextInt(3) / 2, random.nextInt(3) - 1);
                if (!level.getBlockState(blockPos.below()).is(this) || level.getBlockState(blockPos).isCollisionShapeFullBlock(level, blockPos)) {
                    continue start;
                }
            }

            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.is(grass) && random.nextInt(10) == 0) {
                ((BonemealableBlock) grass).performBonemeal(level, random, blockPos, blockState);
            }

            if (blockState.isAir()) {
                Holder<PlacedFeature> featureHolder;
                if (random.nextInt(8) == 0) {
                    List<ConfiguredFeature<?, ?>> list = level.getBiome(blockPos).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) {
                        continue;
                    }
                    featureHolder = ((RandomPatchConfiguration) list.get(random.nextInt(list.size())).config()).feature();
                    featureHolder.value().place(level, level.getChunkSource().getGenerator(), random, blockPos);
                } else {
                    if (level.ensureCanWrite(blockPos))
                    {
                        level.setBlock(blockPos, ReduxBlocks.AETHER_SHORT_GRASS.get().defaultBlockState(), 3);
                    }
                }
            }
        }
    }
}
