package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SkyrootFoliagePlacer extends FoliagePlacer {
    public static final Codec<SkyrootFoliagePlacer> CODEC = RecordCodecBuilder.create((instance) -> foliagePlacerParts(instance)
            .apply(instance, SkyrootFoliagePlacer::new));

    public SkyrootFoliagePlacer(IntProvider pRadius, IntProvider pOffset) {
        super(pRadius, pOffset);
    }

    @Override
    protected void createFoliage(LevelSimulatedReader level, FoliageSetter setter, RandomSource rand, TreeConfiguration config, int maxHeight, FoliageAttachment attachment, int height, int radius, int offset) {
        BlockPos origin = attachment.pos();

        // Place main piece
        placeLeavesRow(level, setter, rand, config, origin, radius - 1, 0, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -1, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -2, false);
        placeLeavesRow(level, setter, rand, config, origin, radius, -3, false);
    }

    // Override vanilla behavior of using the 'large' boolean value to actually affect the size, this is unwanted behavior in this case
    protected void placeLeavesRow(LevelSimulatedReader level, FoliageSetter setter, RandomSource rand, TreeConfiguration config, BlockPos pos, int radius, int y, boolean large) {
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        for(int j = -radius; j <= radius; ++j) {
            for(int k = -radius; k <= radius; ++k) {
                if (!this.shouldSkipLocationSigned(rand, j, y, k, radius, large)) {
                    blockpos$mutableblockpos.setWithOffset(pos, j, y, k);
                    tryPlaceLeaf(level, setter, rand, config, blockpos$mutableblockpos);
                }
            }
        }
    }

    @Override
    public int foliageHeight(RandomSource rand, int pHeight, TreeConfiguration config) {
        return 0;
    }


    @Override
    protected boolean shouldSkipLocation(RandomSource rand, int x, int y, int z, int radius, boolean remove) {
        if (y == 0) {
            // If the y offset is 0, only skip the location if it is on the corners, AND a boolean check succeeds
            return x + z >= radius * 2 && rand.nextBoolean();
        } else if (y == -1) {
            boolean diamond = x + z <= radius;
            return !diamond;
        } else if (y == -2) {
            // If the y offset is -2, do not skip
            return false;
        } else {
            // If the y offset is -3, skip the location if it is on the corners and a boolean check succeeds
            return x + z >= radius * 2;
        }
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return ReduxFoliagePlacers.SKYROOT_FOLIAGE.get();
    }
}
