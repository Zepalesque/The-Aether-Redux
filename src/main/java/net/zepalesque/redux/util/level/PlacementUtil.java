package net.zepalesque.redux.util.level;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class PlacementUtil {



    public static void placeRootsDisk(WorldGenLevel level, BlockStateProvider blockProvider, BlockPos center, float radius, RandomSource random, float chance, BlockStateProvider blockAbove) {
        float radiusSq = radius * radius;
        if (random.nextFloat() <= chance) {
            placeProvidedBlock(level, blockProvider, center, random);
        }
        for (int z = 0; z < radius; z++) {
            for (int x = 0; x < radius; x++) {
                if (x * x + z * z > radiusSq) continue;
                if (random.nextFloat() <= chance) { placeProvidedBlockUnderBlock(level, blockProvider, center.offset(x, 0, z), random, blockAbove); }
                if (random.nextFloat() <= chance) { placeProvidedBlockUnderBlock(level, blockProvider, center.offset(-x, 0, -z), random, blockAbove); }
                if (random.nextFloat() <= chance) { placeProvidedBlockUnderBlock(level, blockProvider, center.offset(-z, 0, x), random, blockAbove); }
                if (random.nextFloat() <= chance) { placeProvidedBlockUnderBlock(level, blockProvider, center.offset(z, 0, -x), random, blockAbove); }
            }
        }
    }



    @SuppressWarnings("UnusedReturnValue")
    public static boolean placeProvidedBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random) {
        if (level.getBlockState(pos).isAir()) {
            return level.setBlock(pos, provider.getState(random, pos), 2);
        } else {
            return false;
        }
    }
    @SuppressWarnings("UnusedReturnValue")
    public static boolean placeProvidedBlockUnderBlock(WorldGenLevel level, BlockStateProvider provider, BlockPos pos, RandomSource random, BlockStateProvider requiredBlock) {
        if (level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).is(requiredBlock.getState(random, pos.above()).getBlock())) {
            return level.setBlock(pos, provider.getState(random, pos), 2);
        } else {
            return false;
        }
    }

}
