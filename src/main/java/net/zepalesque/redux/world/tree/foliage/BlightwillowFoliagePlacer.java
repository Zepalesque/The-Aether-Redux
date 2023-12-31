package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;


public class BlightwillowFoliagePlacer extends FoliagePlacer {
    public static final Codec<BlightwillowFoliagePlacer> CODEC = RecordCodecBuilder.create((p2) -> {
        return foliagePlacerParts(p2).apply(p2, BlightwillowFoliagePlacer::new);
    });

    public BlightwillowFoliagePlacer(IntProvider rad, IntProvider offs) {
        super(rad, offs);
    }

    protected FoliagePlacerType<?> type() {
        return ReduxFoliagePlacers.BLIGHTWILLOW_FOLIAGE_PLACER.get();
    }



    @Override
    protected void createFoliage(LevelSimulatedReader levelSimulatedReader, FoliageSetter foliageSetter, RandomSource randomSource, TreeConfiguration treeConfiguration, int i1, FoliageAttachment foliageAttachment, int foliageMaxHeight, int i2, int i3) {
        BlockPos blockpos = foliageAttachment.pos();
        Type type = Type.getFromIndex(foliageAttachment.radiusOffset());
        BlockPos.MutableBlockPos mbp = new BlockPos.MutableBlockPos();
        generateLeaves(type, mbp, levelSimulatedReader, blockpos, foliageSetter, randomSource, treeConfiguration);


    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return 2;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }

    private void generateLeaves(Type type, BlockPos.MutableBlockPos mutable, LevelSimulatedReader level, BlockPos origin, FoliageSetter setter, RandomSource rand, TreeConfiguration config)
    {
        if (type == Type.SMALL)
        {
            mutable.set(origin);
            tryPlaceLeaf(level, setter, rand, config, mutable.immutable());
            BlockPos stemTip = origin.below();
            for (Direction d : Direction.Plane.HORIZONTAL)
            {
                mutable.setWithOffset(stemTip, d);
                tryPlaceLeaf(level, setter, rand, config, mutable.immutable());

            }
        } else
        {
            Direction.Axis axis = type == Type.X_AXIS ? Direction.Axis.X : Direction.Axis.Z;
            mutable.set(origin);
            tryPlaceLeaf(level, setter, rand, config, mutable.immutable());
            for (Direction d : Direction.Plane.HORIZONTAL)
            {
                mutable.setWithOffset(origin, d);
                tryPlaceLeaf(level, setter, rand, config, mutable.immutable());

            }
            for (int x = -1; x <= 1; x++)
            {
                for (int z = -1; z <= 1; z++)
                {
                    if (x != 0 || z != 0)
                    {
                        mutable.setWithOffset(origin, x, -1, z);
                        tryPlaceLeaf(level, setter, rand, config, mutable.immutable());
                    }
                }
            }
            BlockPos below = origin.below();
            setWithOffset(mutable, below, axis == Direction.Axis.X ? Direction.EAST : Direction.NORTH, 2);
            tryPlaceLeaf(level, setter, rand, config, mutable.immutable());
            setWithOffset(mutable, below, axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH, 2);
            tryPlaceLeaf(level, setter, rand, config, mutable.immutable());


        }
    }

    enum Type {
        SMALL, X_AXIS, Z_AXIS;

        public static Type getFromIndex(int i)
        {
            return i == 0 ? SMALL : i == 1 ? X_AXIS : Z_AXIS;
        }
    }
    private static BlockPos.MutableBlockPos setWithOffset(BlockPos.MutableBlockPos mutable, Vec3i origin, Direction direction, int amount) {
        return mutable.set(origin.getX() + (direction.getStepX() * amount), origin.getY() + (direction.getStepY() * amount), origin.getZ() + (direction.getStepZ() * amount));
    }

}