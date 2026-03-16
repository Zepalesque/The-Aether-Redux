package net.zepalesque.redux.world.tree.decorator;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.zepalesque.redux.Redux;

public class VineDecorator extends TreeDecorator {

    public static final MapCodec<VineDecorator> CODEC = RecordCodecBuilder.mapCodec(
        builder -> builder.group(
            Codec.floatRange(
                0.0F, 1.0F
            ).fieldOf(
                "probability"
            ).forGetter(
                instance -> instance.probability
            ), BlockStateProvider.CODEC.fieldOf(
                "plant_body_provider"
            ).forGetter(
                instance -> instance.bodyBlock
            ), BlockStateProvider.CODEC.fieldOf(
                "plant_head_provider"
            ).forGetter(
                instance -> instance.headBlock
            ), IntProvider.codec(
                1,128
            ).fieldOf(
                "length"
            ).forGetter(instance -> instance.length),
            BlockPredicate.CODEC.optionalFieldOf(
                "predicate"
            ).forGetter(
                instance -> instance.predicate
            )
        ).apply(builder, VineDecorator::new));
    private final float probability;
    private final BlockStateProvider bodyBlock;
    private final BlockStateProvider headBlock;
    private final IntProvider length;
    private final Optional<BlockPredicate> predicate;

    public VineDecorator(float vineProbability, BlockStateProvider bodyBlock, BlockStateProvider headBlock, IntProvider vineLength, Optional<BlockPredicate> predicate) {
        this.probability = vineProbability;
        this.bodyBlock = bodyBlock;
        this.headBlock = headBlock;
        this.length = vineLength;
        this.predicate = predicate;
    }

    // Avoid new table instantiation for every single tree, formatted as [X, Z, Y]
    private static final Table<Integer, Integer, Integer> LOWEST_BY_XZ = HashBasedTable.create();

    public void place(TreeDecorator.Context context) {
        if (context.level() instanceof WorldGenLevel level) {
            if (this.predicate.isPresent()) {
                var predicate = this.predicate.get();
                List<BlockPos> logs = context.logs();
                if (!logs.isEmpty()) {
                    if (!predicate.test(level, logs.getFirst())) return;
                }
            }
        } else Redux.LOGGER.warn("Failed BlockPredicate check as level was not an instance of WorldGenLevel!");

        if (!LOWEST_BY_XZ.isEmpty()) {
            LOWEST_BY_XZ.clear();
        }

        List<BlockPos> leavesClone = context.leaves().clone();
        Collections.reverse(leavesClone);
        for (var leafPos : leavesClone) {
            var x = leafPos.getX();
            var y = leafPos.getY();
            var z = leafPos.getZ();
            try {
                if (!LOWEST_BY_XZ.contains(x, z)) LOWEST_BY_XZ.put(x, z, y);
                else // noinspection DataFlowIssue
                    if (y < LOWEST_BY_XZ.get(x, z)) LOWEST_BY_XZ.put(x, z, y);
            } catch (NullPointerException exception) {
                Redux.LOGGER.error("Caught error when trying to add leaf to table!", exception);
            }
        }
        var rand = context.random();
        for (var leafPos : LOWEST_BY_XZ.cellSet()) {
            var pos = new BlockPos(leafPos.getRowKey(), leafPos.getValue(), leafPos.getColumnKey());
            var length = this.length.sample(rand);
            if (rand.nextFloat() < probability) {
                var below = pos.below();
                if (context.isAir(below)) this.addVine(below, context, length);
            }
        }
    }

    private void addVine(BlockPos pos, TreeDecorator.Context ctx, int length) {
        for (var i = 1; i <= length; i++) {
            var offset = pos.offset(0, 1 - i, 0);
            var notAirBelow = !ctx.isAir(offset.below());
            var maxLength = i >= length;
            if (notAirBelow || maxLength) {
                ctx.setBlock(offset, this.headBlock.getState(ctx.random(), pos));
                break;
            } else ctx.setBlock(offset, this.bodyBlock.getState(ctx.random(), pos));
        }
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ReduxTreeDecorators.GOLDEN_VINES.get();
    }
}
