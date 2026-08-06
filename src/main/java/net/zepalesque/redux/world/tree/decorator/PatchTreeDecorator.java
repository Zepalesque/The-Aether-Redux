package net.zepalesque.redux.world.tree.decorator;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class PatchTreeDecorator extends TreeDecorator {
    
    public static final MapCodec<PatchTreeDecorator>
        CODEC = RecordCodecBuilder.mapCodec(
            builder -> builder.group(
                BlockStateProvider.CODEC
                    .fieldOf("provider")
                    .forGetter(instance -> instance.provider),
                ExtraCodecs.NON_NEGATIVE_INT
                    .fieldOf("xz_spread")
                    .orElse(7)
                    .forGetter(instance -> instance.xzSpread),
                ExtraCodecs.NON_NEGATIVE_INT
                    .fieldOf("y_spread")
                    .orElse(3)
                    .forGetter(instance -> instance.ySpread),
                ExtraCodecs.POSITIVE_INT
                    .fieldOf("tries")
                    .orElse(128)
                    .forGetter(instance -> instance.tries)
            ).apply(builder, PatchTreeDecorator::new));
    
    public final BlockStateProvider provider;
    public final int tries;
    public final int xzSpread;
    public final int ySpread;

    public PatchTreeDecorator(BlockStateProvider provider, int xzSpread, int ySpread, int tries) {
        this.provider = provider;
        this.tries = tries;
        this.xzSpread = xzSpread;
        this.ySpread = ySpread;
    }

    protected TreeDecoratorType<?> type() {
        return ReduxTreeDecorators.PATCH.get();
    }

    @Override
    public void place(Context ctx) {
        List<BlockPos> logs = ctx.logs();
        if (!logs.isEmpty()) {
	        var i = logs.getFirst().getY();
            logs.stream().filter(pos -> pos.getY() == i).forEach(pos ->
                this.place(this.xzSpread, this.ySpread, this.tries, pos, ctx)
            );
        }
    }

    public void place(int xzSpread, int ySpread, int tries, BlockPos origin, Context ctx) {
	    var rand = ctx.random();
        // tis unfortunate that `mut` is not a keyword in java (explicit mutability my beloved) but alas :pensive:
        var mut = new BlockPos.MutableBlockPos();
	    var j = xzSpread + 1;
	    var k = ySpread + 1;

        for(var l = 0; l < tries; ++l) {
            mut.setWithOffset(
                origin,
                rand.nextInt(j) - rand.nextInt(j),
                rand.nextInt(k) - rand.nextInt(k),
                rand.nextInt(j) - rand.nextInt(j)
            );
            this.placeBlockAt(ctx, this.provider.getState(rand, mut), mut);
        }
    }
    
    void placeBlockAt(Context context, BlockState state, BlockPos pos) {
        if (context.isAir(pos)) context.setBlock(pos, state);
    }
}