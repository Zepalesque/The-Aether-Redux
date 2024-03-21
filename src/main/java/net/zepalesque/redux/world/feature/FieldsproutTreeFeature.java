package net.zepalesque.redux.world.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.block.util.state.enums.PetalPrismaticness;
import net.zepalesque.redux.util.level.WorldgenUtil;
import net.zepalesque.redux.world.feature.config.FieldsprootTreeConfig;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;

public class FieldsproutTreeFeature extends Feature<FieldsprootTreeConfig> {


    public FieldsproutTreeFeature(Codec<FieldsprootTreeConfig> codec) {
        super(codec);
    }
    public static boolean isDirt(BlockState state) {
        return state.is(BlockTags.DIRT);
    }
    @Override
    public boolean place(FeaturePlaceContext<FieldsprootTreeConfig> context) {
        WorldGenLevel level = context.level();
        if (level.isStateAtPosition(context.origin().below(), state -> !isDirt(state))) {
            return false;
        }
        Direction d = Direction.Plane.HORIZONTAL.getRandomDirection(context.random());
        Map<BlockPos, BlockState> trunk = Maps.newHashMap();
        boolean longerSecondBend = context.random().nextBoolean();
        BlockPos foliagePos = this.placeTrunk(context, trunk, d, longerSecondBend);
        if (foliagePos == null) {
            return false;
        }
        Map<BlockPos, BlockState> foliage = Maps.newHashMap();
        if (!createFoliage(context, foliage, foliagePos, d, longerSecondBend)) {
            return false;
        }
        for (Map.Entry<BlockPos, BlockState> logEntry : trunk.entrySet()) {
            this.setBlock(level, logEntry.getKey(), logEntry.getValue());
        }
        for (Map.Entry<BlockPos, BlockState> leafEntry : foliage.entrySet()) {
            if (level.isStateAtPosition(leafEntry.getKey(), state -> !state.is(BlockTags.LOGS))) {
                this.setBlock(level, leafEntry.getKey(), leafEntry.getValue());
            }
        }
        Map<BlockPos, BlockState> petals = Maps.newHashMap();
        this.place(context, petals);
        for (Map.Entry<BlockPos, BlockState> petalEntry : petals.entrySet()) {
            if (level.isStateAtPosition(petalEntry.getKey(), state -> state.isAir()) && level.isStateAtPosition(petalEntry.getKey().below(), state -> isDirt(state))) {
                this.setBlock(level, petalEntry.getKey(), petalEntry.getValue());
            }
        }
        return true;
    }

    @Nullable
    public BlockPos placeTrunk(FeaturePlaceContext<FieldsprootTreeConfig> context, Map<BlockPos, BlockState> stateMap, Direction d, boolean longerSecondBend) {
        
        RandomSource random = context.random();
        BlockPos origin = context.origin();

        BlockStateProvider woodProvider = context.config().woodProvider;
        BlockStateProvider logProvider = context.config().logProvider;
        
        int baseHeight = 3 + random.nextInt(2);
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        int forwards = 2;
        int backwards = forwards + (longerSecondBend ? 1 : 0);

        int top = 3;

        for (int i = 0; i < baseHeight; i++) {
            mutable.setWithOffset(origin, 0, i, 0);
            stateMap.put(mutable.immutable(), (i == baseHeight - 1 ? woodProvider : logProvider ).getState(random, mutable));
        }
        BlockPos straightEnd = origin.relative(Direction.UP, baseHeight - 1);
        for (int i = 1; i < forwards; i++) {
            WorldgenUtil.setWithOffset(mutable, straightEnd, d, i);
            stateMap.put(mutable.immutable(), WorldgenUtil.trySetValue(woodProvider.getState(random, mutable), RotatedPillarBlock.AXIS, d.getAxis()));
            stateMap.put(mutable.immutable().above(2), WorldgenUtil.trySetValue(woodProvider.getState(random, mutable), RotatedPillarBlock.AXIS, d.getAxis()));
        }
        BlockPos edge1 = straightEnd.relative(d, forwards);
        for (int i = 0; i <= 2; i++) {
            mutable.setWithOffset(edge1, 0, i, 0);
            stateMap.put(mutable.immutable(), (i == 1 ? logProvider : woodProvider).getState(random, mutable));
        }
        BlockPos row2Start = straightEnd.above(2);
        Direction o = d.getOpposite();
        stateMap.put(row2Start, WorldgenUtil.trySetValue(logProvider.getState(random, row2Start), RotatedPillarBlock.AXIS, d.getAxis()));
        for (int i = 1; i < backwards; i++) {
            WorldgenUtil.setWithOffset(mutable, row2Start, o, i);
            stateMap.put(mutable.immutable(), WorldgenUtil.trySetValue(woodProvider.getState(random, mutable), RotatedPillarBlock.AXIS, o.getAxis()));
            stateMap.put(mutable.immutable().above(2), WorldgenUtil.trySetValue(woodProvider.getState(random, mutable), RotatedPillarBlock.AXIS, o.getAxis()));
        }
        BlockPos edge2 = row2Start.relative(o, backwards);
        for (int i = 0; i <= 2; i++) {
            mutable.setWithOffset(edge2, 0, i, 0);
            stateMap.put(mutable.immutable(), (i == 1 ? logProvider : woodProvider).getState(random, mutable));
        }
        BlockPos topStart = row2Start.above(2);
        for (int i = 0; i < top; i++) {
            mutable.setWithOffset(topStart, 0, i, 0);
            stateMap.put(mutable.immutable(), (i == 0 ? woodProvider : logProvider).getState(random, mutable));
        }
        BlockPos absTop = topStart.above(top);

        WorldGenLevel level = context.level();
        for (BlockPos pos : stateMap.keySet()) {
            if (!canPlaceBlockHere(level, pos)) {
                return null;
            }
        }
        return absTop;

    }


    protected boolean canPlaceBlockHere(LevelAccessor level, BlockPos pos) {
        int i = pos.getY();
        if (i >= level.getMinBuildHeight() + 1 && i + 1 < level.getMaxBuildHeight()) {
            return level.isStateAtPosition(pos, state -> state.isAir() || state.is(BlockTags.LOGS) || state.getMaterial().isReplaceable() || state.is(BlockTags.LEAVES));
        }
        return false;
    }

    public void place(FeaturePlaceContext<FieldsprootTreeConfig> context, Map<BlockPos, BlockState> stateMap) {
        FieldsprootTreeConfig config = context.config();
        int xzSpread = config.patchXZSpread;
        int ySpread = config.patchYSpread;
        int tries = config.patchTries;
        BlockPos origin = context.origin();
        RandomSource random = context.random();
        LevelSimulatedReader level = context.level();
        int i = 0;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
        int j = xzSpread + 1;
        int k = ySpread + 1;

        for(int l = 0; l < tries; ++l) {

            blockpos$mutableblockpos.setWithOffset(origin, random.nextInt(j) - random.nextInt(j), random.nextInt(k) - random.nextInt(k), random.nextInt(j) - random.nextInt(j));
            BlockPos immutable = blockpos$mutableblockpos.immutable();

            int p1 = random.nextInt(7);
            int p2 = random.nextInt(8) - 1;
            int p3 = p2 == -1 ? -1 : random.nextInt(8) - 1;
            int p4 = p3 == -1 ? -1 : random.nextInt(8) - 1;
            int r = (int) (Mth.getSeed(immutable) % 4);
            Direction d = r == 0 ? Direction.NORTH : r == 1 ? Direction.EAST : r == 2 ? Direction.SOUTH : Direction.EAST;

            BlockState state = ReduxBlocks.FIELDSPROOT_PETALS.get().defaultBlockState()
                    .setValue(ReduxStates.PETAL_1, PetalPrismaticness.getFromIndex(p1))
                    .setValue(ReduxStates.PETAL_2, PetalPrismaticness.getFromIndex(p2))
                    .setValue(ReduxStates.PETAL_3, PetalPrismaticness.getFromIndex(p3))
                    .setValue(ReduxStates.PETAL_4, PetalPrismaticness.getFromIndex(p4))
                    .setValue(BlockStateProperties.HORIZONTAL_FACING, d);

            stateMap.put(immutable, state);


        }
    }


    protected boolean createFoliage(FeaturePlaceContext<FieldsprootTreeConfig> context, Map<BlockPos, BlockState> stateMap, BlockPos origin, Direction d, boolean longerSecondBend) {
        Collection<BlockPos> toPlace = Lists.newArrayList();
        RandomSource random = context.random();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

        // add the first and uppermost layer of the main tree-top
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                boolean corners = (x == 1 || x == -1) && (z == 1 || z == -1);
                if (!corners || random.nextInt(3) == 0) {
                    mutable.setWithOffset(origin, x, 0, z);
                    toPlace.add(mutable.immutable());
                }
            }
        }

        // add the second and middle layer of the main tree-top
        BlockPos layer2 = origin.below();
        for (int x = -3; x <= 3; x++) {
            for (int z = -3; z <= 3; z++) {

                int pyth = Mth.square(x) + Mth.square(z);
                if (pyth <= 7 || (pyth <= 9 && random.nextBoolean())) {
                    mutable.setWithOffset(layer2, x, 0, z);
                    toPlace.add(mutable.immutable());
                }
            }
        }

        // add the third and lowest layer of the main tree-top
        BlockPos layer3 = layer2.below();
        for (int x = -4; x <= 4; x++) {
            for (int z = -4; z <= 4; z++) {
                int pyth = Mth.square(x) + Mth.square(z);
                if ((pyth <= 17 || (pyth <= 18 && random.nextBoolean()))) {
                    mutable.setWithOffset(layer3, x, 0, z);
                    toPlace.add(mutable.immutable());
                }
            }
        }



        // Add the shorter vines on the four corners, along with others next to them on the sides perpendicular to the trunk bend
        BlockPos layer4 = layer3.below();
        Direction out = d.getCounterClockWise();
        for (int x = -1; x <= 1; x += 2) {
            for (int z = -1; z <= 1; z += 2) {
                boolean longCorners = random.nextBoolean();
                for (int i = 0; i >= (longCorners ? -1 : 0); i--) {
                    mutable.setWithOffset(layer4, x * 2, i, z * 2);
                    toPlace.add(mutable.immutable());
                }
                for (int i = 0; i >= (longCorners ? 0 : -1); i--) {
                    mutable.setWithOffset(layer4, x * (d.getAxis() == Direction.Axis.X ? 1 : 3), i, z * (d.getAxis() == Direction.Axis.Z ? 1 : 3));
                    toPlace.add(mutable.immutable());
                }

            }
        }

        //Add the vine that has a chance to lean on the bend of the tree
        BlockPos bendVine = layer4.relative(d, 3);
        boolean leaning = random.nextBoolean();
        boolean longer = random.nextBoolean();
        if (longer) {
            Direction d1 = random.nextBoolean() ? out : out.getOpposite();
            mutable.setWithOffset(bendVine, d1);
            toPlace.add(mutable.immutable());
            if (random.nextBoolean()) {
                mutable.setWithOffset(bendVine, d1.getOpposite());
                toPlace.add(mutable.immutable());
            }

        }
        if (leaning) {
            BlockPos extra = bendVine.below().relative(d.getOpposite());
            mutable.set(extra);
            toPlace.add(mutable.immutable());
        }
        for (int i = 0; i >= (longer ? -3 : -2); i--) {
            mutable.setWithOffset(bendVine, 0, i, 0);
            toPlace.add(mutable.immutable());
        }

        //Add the final vine which has to move out of the way if the second bend is longer
        BlockPos finalVine = layer4.relative(d.getOpposite(), longerSecondBend ? 4 : 3);
        boolean finalLonger = random.nextBoolean();
        if (finalLonger) {
            Direction d1 = random.nextBoolean() ? out : out.getOpposite();
            mutable.setWithOffset(finalVine, d1);
            toPlace.add(mutable.immutable());
            if (random.nextBoolean()) {
                mutable.setWithOffset(finalVine, d1.getOpposite());
                toPlace.add(mutable.immutable());
            }

        }
        for (int i = 0; i >= (finalLonger ? -3 : -2); i--) {
            mutable.setWithOffset(finalVine, 0, i, 0);
            toPlace.add(mutable.immutable());
        }

        // Finds the min and max y values
        int minY = origin.getY();
        int maxY = origin.getY();
        float maxDist = 0;
        for (BlockPos pos : toPlace) {
            int y = pos.getY();
            if (y > maxY) {
                maxY = y;
            }
            if (y < minY) {
                minY = y;
            }
            int xLocal = pos.getX() - origin.getX();
            int zLocal = pos.getZ() - origin.getZ();
            float dist = Mth.sqrt(Mth.square(xLocal) + Mth.square(zLocal));
            if (dist > maxDist) {
                maxDist = dist;
            }
        }
        // Maps all of the positions
        LevelAccessor level = context.level();
        for (BlockPos pos : toPlace) {
            if (!canPlaceBlockHere(level, pos)) {
                return false;
            }
            stateMap.put(pos, context.config().leafProvider.getState(random, pos));
        }
        return true;
    }



}
