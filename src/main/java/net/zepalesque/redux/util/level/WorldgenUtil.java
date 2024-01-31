package net.zepalesque.redux.util.level;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.WallSide;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class WorldgenUtil {

    public static BlockPos.MutableBlockPos setWithOffset(BlockPos.MutableBlockPos mutable, Vec3i origin, Direction direction, int amount) {
        return mutable.set(origin.getX() + (direction.getStepX() * amount), origin.getY() + (direction.getStepY() * amount), origin.getZ() + (direction.getStepZ() * amount));
    }
    public static BlockPos withOffset(BlockPos pos, Direction direction, int amount) {
        return pos.offset(direction.getStepX() * amount, direction.getStepY() * amount, direction.getStepZ() * amount);
    }

    public static Property<WallSide> getWallSide(Direction d) {
        if (d.getAxis() == Direction.Axis.Y) {
            throw new IllegalArgumentException("Cannot use non-y-axis direction!");
        }
        return d == Direction.NORTH ? BlockStateProperties.NORTH_WALL :
               d == Direction.EAST ? BlockStateProperties.EAST_WALL :
               d == Direction.WEST ? BlockStateProperties.WEST_WALL :
                       BlockStateProperties.SOUTH_WALL;
    }

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
