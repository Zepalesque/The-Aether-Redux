package net.zepalesque.redux.world.tree.decorator;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.ReduxTags;

import java.util.Collection;
import java.util.Optional;

public class GoldenVineDecorator extends TreeDecorator {

    public static final Codec<GoldenVineDecorator> CODEC = RecordCodecBuilder.create((vines) ->
            vines.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((config) -> config.probability),
                            BlockStateProvider.CODEC.fieldOf("plant_body_provider").forGetter((config) -> config.bodyBlock),
                            BlockStateProvider.CODEC.fieldOf("plant_head_provider").forGetter((config) -> config.headBlock),
                            IntProvider.codec(1,128).fieldOf("length").forGetter((config) -> config.length))
                                    .apply(vines, GoldenVineDecorator::new));
    private final float probability;
    private final BlockStateProvider bodyBlock;
    private final BlockStateProvider headBlock;
    private final IntProvider length;

    public GoldenVineDecorator(float vineProbability, BlockStateProvider bodyBlock, BlockStateProvider headBlock, IntProvider vineLength) {
        this.probability = vineProbability;
        this.bodyBlock = bodyBlock;
        this.headBlock = headBlock;
        this.length = vineLength;
    }

    public void place(TreeDecorator.Context pContext) {
        Table<Integer, Integer, Integer> xzyLowestMap = HashBasedTable.create();
        for (BlockPos leafPos : pContext.leaves()) {
            int x = leafPos.getX();
            int y = leafPos.getY();
            int z = leafPos.getZ();
            try {
                if (!xzyLowestMap.contains(x, z) || y < xzyLowestMap.get(x, z)) {
                    xzyLowestMap.put(x, z, y);
                }
            } catch (NullPointerException exception) {
                Redux.LOGGER.error("Caught error when trying to add leaf to map!", exception);
            }
        }
        Collection<BlockPos> lowestLeaves = xzyLowestMap.cellSet().stream().map(cell -> new BlockPos(cell.getRowKey(), cell.getValue(), cell.getColumnKey())).toList();
        RandomSource randomsource = pContext.random();
        for (BlockPos leafPos : lowestLeaves) {
            int length = this.length.sample(randomsource);
            if (randomsource.nextFloat() < probability) {
                BlockPos blockpos = leafPos.below();
                if (pContext.isAir(blockpos)) {
                    this.addVine(blockpos, pContext, length);
                }
            }
        }
    }

    private void addVine(BlockPos pPos, TreeDecorator.Context pContext, int length) {
        for (int i = 1; i <= length; i++) {
            BlockPos placement = pPos.offset(0, 1 - i, 0);
            if (!pContext.isAir(placement)) {
                break;
            }

            if (!pContext.isAir(placement.below()) || i >= length) {
                pContext.setBlock(placement, this.headBlock.getState(pContext.random(), pPos));
                break;
            } else {
                pContext.setBlock(placement, this.bodyBlock.getState(pContext.random(), pPos));
            }
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ReduxTreeDecorators.GOLDEN_VINES.get();
    }
}
