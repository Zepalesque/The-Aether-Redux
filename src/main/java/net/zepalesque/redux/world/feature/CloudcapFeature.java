package net.zepalesque.redux.world.feature;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.world.feature.config.JellyshroomConfig;

import java.util.HashMap;
import java.util.Map;

public class CloudcapFeature extends Feature<CloudcapFeature.CloudcapConfig> {


    public CloudcapFeature(Codec<CloudcapConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<CloudcapFeature.CloudcapConfig> context) {
        int totalheight = context.config().height.sample(context.random());
        Map<BlockPos, BlockState> toPlace = new HashMap<>();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();
        BlockPos origin = context.origin();
        if (context.level().isStateAtPosition(origin.below(), state -> !isDirt(state))) {
            return false;
        }

        // Stem
        for (int i = 0; i < totalheight - 1; i++) {
            mutable.setWithOffset(origin, 0, i, 0);
            BlockPos immutable1 = mutable.immutable();
            toPlace.putIfAbsent(immutable1, context.config().stem.getState(context.random(), immutable1));
        }

        // Roots
        for (Direction d : Direction.Plane.HORIZONTAL) {
            int rootHeight = context.config().rootHeight.sample(context.random());
            int rootWallHeight = context.config().rootWallHeight.sample(context.random());
            for (int i = 0; i < rootHeight; i++) {
                mutable.setWithOffset(origin, d.getStepX(), i, d.getStepZ());
                BlockPos immutable1 = mutable.immutable();
                toPlace.putIfAbsent(immutable1, context.config().stem.getState(context.random(), immutable1));
            }
            BlockPos wallOrigin = origin.above(rootHeight);
            for (int i = 0; i < rootWallHeight; i++) {
                mutable.setWithOffset(wallOrigin, d.getStepX(), i, d.getStepZ());
                BlockPos immutable1 = mutable.immutable();
                toPlace.putIfAbsent(immutable1, context.config().stemWall.getState(context.random(), immutable1));
            }
        }

        BlockPos stemTop = origin.above(totalheight - 1);

        // Spore Blocks
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 0; y++) {
                    if (x != 0 || z != 0) {
                        mutable.setWithOffset(stemTop, x, y, z);
                        BlockPos immutable1 = mutable.immutable();
                        toPlace.putIfAbsent(immutable1, context.config().spore.getState(context.random(), immutable1));
                    }
                }
            }
        }

        BlockPos capTop = origin.above(totalheight);

        // Cap Top
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                mutable.setWithOffset(capTop, x, 0, z);
                BlockPos immutable1 = mutable.immutable();
                toPlace.putIfAbsent(immutable1, context.config().cap.getState(context.random(), immutable1));
            }
        }

        // Cap Sides
        int innerRadius = 0;
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                mutable.setWithOffset(capTop, x, 0, z);
                BlockPos immutable1 = mutable.immutable();
                BlockState state = context.config().cap.getState(context.random(), immutable1);
                if (state.hasProperty(HugeMushroomBlock.WEST) && state.hasProperty(HugeMushroomBlock.EAST) && state.hasProperty(HugeMushroomBlock.NORTH) && state.hasProperty(HugeMushroomBlock.SOUTH) && state.hasProperty(HugeMushroomBlock.UP)) {
                    state = state.setValue(HugeMushroomBlock.WEST, x < -innerRadius).setValue(HugeMushroomBlock.EAST, x > innerRadius).setValue(HugeMushroomBlock.NORTH, z < -innerRadius).setValue(HugeMushroomBlock.SOUTH, z > innerRadius);
                }
                toPlace.putIfAbsent(immutable1, state);
            }
        }
        for (Map.Entry<BlockPos, BlockState> entry : toPlace.entrySet()) {
            if (!canPlaceBlockHere(context.level(), entry.getKey())) {
                return false;
            }
        }
        for (Map.Entry<BlockPos, BlockState> entry : toPlace.entrySet()) {
            this.setBlock(context.level(), entry.getKey(), entry.getValue());
        }
        return true;
    }
    
    protected boolean canPlaceBlockHere(LevelAccessor level, BlockPos pos) {
        int i = pos.getY();
        if (i >= level.getMinBuildHeight() + 1 && i + 1 < level.getMaxBuildHeight()) {
            return level.isStateAtPosition(pos, state -> state.isAir() || state.is(BlockTags.LOGS) || state.canBeReplaced() || state.is(ReduxTags.Blocks.MUSHROOM_CAPS));
        }
        return false;
    }

    public static class CloudcapConfig implements FeatureConfiguration {
        public static final Codec<CloudcapConfig> CODEC = RecordCodecBuilder.create((mushroom) ->
                mushroom.group(BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter((config) -> config.cap),
                                BlockStateProvider.CODEC.fieldOf("spore_provider").forGetter((config) -> config.spore),
                                BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter((config) -> config.stem),
                                BlockStateProvider.CODEC.fieldOf("stem_wall_provider").forGetter((config) -> config.stemWall),
                                IntProvider.CODEC.fieldOf("height").forGetter((config) -> config.height),
                                IntProvider.CODEC.fieldOf("root_height").forGetter((config) -> config.rootHeight),
                                IntProvider.CODEC.fieldOf("root_wall_height").forGetter((config) -> config.rootWallHeight),
                                IntProvider.CODEC.fieldOf("cap_height").forGetter((config) -> config.capHeight))
                        .apply(mushroom, CloudcapConfig::new));
        public final BlockStateProvider cap;
        public final BlockStateProvider spore;
        public final BlockStateProvider stem;
        public final BlockStateProvider stemWall;
        public final IntProvider height;
        public final IntProvider rootHeight;
        public final IntProvider rootWallHeight;
        public final IntProvider capHeight;

        public CloudcapConfig(BlockStateProvider cap, BlockStateProvider spore, BlockStateProvider stem, BlockStateProvider stemWall, IntProvider height, IntProvider rootHeight, IntProvider rootWallHeight, IntProvider capHeight) {
            this.cap = cap;
            this.spore = spore;
            this.stem = stem;
            this.stemWall = stemWall;
            this.height = height;
            this.rootHeight = rootHeight;
            this.rootWallHeight = rootWallHeight;
            this.capHeight = capHeight;
        }
    }
}
