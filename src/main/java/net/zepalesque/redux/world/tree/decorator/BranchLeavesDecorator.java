package net.zepalesque.redux.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class BranchLeavesDecorator extends TreeDecorator {

    public static final MapCodec<BranchLeavesDecorator> 
        CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                Codec.floatRange(0.0F, 1.0F)
                    .fieldOf("probability")
                    .forGetter(instance -> instance.probability),
                IntProvider.CODEC.fieldOf("range")
                    .forGetter(instance -> instance.range),
                IntProvider.CODEC.fieldOf("radius").forGetter(instance -> instance.radius),
                BlockStateProvider.CODEC.fieldOf("leaf_block").forGetter(instance -> instance.leaf)
            ).apply(builder, BranchLeavesDecorator::new));
    
    private final float probability;
    private final IntProvider range;
    private final IntProvider radius;
    private final BlockStateProvider leaf;

    public BranchLeavesDecorator(float probability, IntProvider range, IntProvider radius, BlockStateProvider leaf) {
        this.probability = probability;
        this.range = range;
        this.radius = radius;
        this.leaf = leaf;
    }

    public void place(Context context) {
        var yTest = Integer.MIN_VALUE;
        final var range = this.range.sample(context.random());
        for (var pos : context.logs())
            if (pos.getY() > yTest) yTest = pos.getY();
        var highest = yTest;
        var positions = context.logs().stream().filter(pos -> pos.getY() > highest - range && (context.random().nextBoolean() ? Mth.isMultipleOf(pos.getY(), 2) : Mth.isMultipleOf(pos.getY() + 1, 2))).toList();
        for (var pos : positions) {
            if (context.random().nextFloat() < this.probability) {
                var radius = this.radius.sample(context.random());
                var dir = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
	            this.placeBlob(pos, context, radius);
	            this.placeBlob(pos.relative(dir), context, radius);
            }
        }
    }

    private void placeBlob(BlockPos pos, Context context, int radius) {
        for (var x = -radius; x <= radius; x++) {
            for (var y = -radius; y <= radius; y++) {
                for (var z = -radius; z <= radius; z++) {
                    var total = Math.abs(x) + Math.abs(y) + Math.abs(z);
                    var place = context.random().nextBoolean() ? total <= 1 : total <= 2;
                    if (place) {
                        var offs = pos.offset(x, y, z);
                        if (context.level().isStateAtPosition(offs, BlockBehaviour.BlockStateBase::isAir)) {
                            context.setBlock(offs, this.leaf.getState(context.random(), offs));
                            context.leaves().add(offs);
                        }
                    }
                }
            }
        }

    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ReduxTreeDecorators.BRANCH_LEAVES.get();
    }
}
