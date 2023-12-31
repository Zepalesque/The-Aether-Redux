package net.zepalesque.redux.world.tree.decorator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.util.List;

public class PatchTreeDecorator extends TreeDecorator {


    public static final Codec<PatchTreeDecorator> CODEC = RecordCodecBuilder.create((builder) ->
            builder.group(
                    BlockStateProvider.CODEC.fieldOf("provider").forGetter(patchTreeDecorator -> patchTreeDecorator.provider),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("xz_spread").orElse(7).forGetter(patchTreeDecorator -> patchTreeDecorator.xzSpread),
                            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("y_spread").orElse(3).forGetter(patchTreeDecorator -> patchTreeDecorator.ySpread),
                            ExtraCodecs.POSITIVE_INT.fieldOf("tries").orElse(128).forGetter(patchTreeDecorator -> patchTreeDecorator.tries))
                    .apply(builder, PatchTreeDecorator::new));
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
        return ReduxTreeDecorators.PATCH_TREE_DECORATOR.get();
    }

    @Override
    public void place(Context context) {
        List<BlockPos> logPositions = context.logs();
        if (!logPositions.isEmpty()) {
            int i = logPositions.get(0).getY();
            logPositions.stream().filter((logs) -> logs.getY() == i).forEach((logPos) -> {
                this.place(this.xzSpread, this.ySpread, this.tries, logPos, context);
            });
        }
    }

    public void place(int xzSpread, int ySpread, int tries, BlockPos origin, Context context) {
                RandomSource random = context.random();
        LevelSimulatedReader level = context.level();
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int j = xzSpread + 1;
        int k = ySpread + 1;

        for(int l = 0; l < tries; ++l) {

            blockpos$mutableblockpos.setWithOffset(origin, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
                this.placeBlockAt(context, level, random, blockpos$mutableblockpos);

        }
    }



    private void placeBlockAt(Context context, LevelSimulatedReader pLevel, RandomSource pRandom, BlockPos pPos) {
     if (context.isAir(pPos)) {
            context.setBlock(pPos, this.provider.getState(pRandom, pPos));
        }
    }

}