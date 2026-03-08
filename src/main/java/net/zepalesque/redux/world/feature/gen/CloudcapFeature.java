package net.zepalesque.redux.world.feature.gen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.data.ReduxTags;

public class CloudcapFeature extends Feature<CloudcapFeature.Config> {
	public CloudcapFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Config> context) {
		var totalheight = context.config().height.sample(context.random());
		Map<BlockPos, BlockState> toPlace = new HashMap<>();
		Map<Direction, Map<BlockPos, BlockState>> roots = new HashMap<>();
		var mutable = new BlockPos.MutableBlockPos();
		var origin = context.origin();
		
		if (context.level().isStateAtPosition(origin.below(), state -> !isDirt(state))) {
			return false;
		}

		// Stem
		for (var i = 0; i < totalheight; i++) {
			mutable.setWithOffset(origin, 0, i, 0);
			var immutable1 = mutable.immutable();
			toPlace.putIfAbsent(immutable1, context.config().stem.getState(context.random(), immutable1));
		}

		// Roots
		for (var d : Direction.Plane.HORIZONTAL) {
			HashMap<BlockPos, BlockState> stem = new HashMap<>();
			var rootHeight = context.config().rootHeight.sample(context.random());

			for (var i = 0; i < rootHeight; i++) {
				mutable.setWithOffset(origin, d.getStepX(), i, d.getStepZ());
				var immutable1 = mutable.immutable();
				stem.putIfAbsent(
					immutable1,
					(i == rootHeight - 1 ? context.config().stemHyphae : context.config().stem).getState(
						context.random(),
						immutable1
					)
				);
			}
			for (int i = -1; i > -5; i--) {
				mutable.setWithOffset(origin, d.getStepX(), i, d.getStepZ());
				var immutable2 = mutable.immutable();
				mutable.setWithOffset(origin, 0, i, 0);
				var immutable3 = mutable.immutable();
				if (
					context
						.level()
						.isStateAtPosition(immutable2, state -> state.isAir() || state.canBeReplaced()) &&
					context
						.level()
						.isStateAtPosition(immutable3, state ->
							state.isFaceSturdy(context.level(), immutable3, d)
						)
				) {
					stem.putIfAbsent(
						immutable2,
						context.config().stem.getState(context.random(), immutable2)
					);
				} else {
					break;
				}
			}
			roots.put(d, stem);
		}

		var stemTop = origin.above(totalheight - 1);

		// Spore Blocks
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				for (int y = -3; y <= 0; y++) {
					if (x != 0 || z != 0) {
						mutable.setWithOffset(stemTop, x, y, z);
						var immutable1 = mutable.immutable();
						toPlace.putIfAbsent(
							immutable1,
							context.config().spore.getState(context.random(), immutable1)
						);
					}
				}
			}
		}

		var capTop = origin.above(totalheight);

		// Cap Top
		for (var x = -1; x <= 1; x++) {
			for (var z = -1; z <= 1; z++) {
				mutable.setWithOffset(capTop, x, 0, z);
				var immutable1 = mutable.immutable();
				toPlace.putIfAbsent(
					immutable1,
					context.config().cap.getState(context.random(), immutable1)
				);
			}
		}

		var capHeight = context.config().capHeight.sample(context.random());
		// Cap Sides
		var innerRadius = 0;
		for (var x = -2; x <= 2; x++) {
			for (var y = -1; y >= -capHeight; y--) {
				for (var z = -2; z <= 2; z++) {
					var xEdges = x == -2 || x == 2;
					var zEdges = z == -2 || z == 2;
					var outer = (xEdges && !zEdges) || (!xEdges && zEdges);
					if (outer) {
						mutable.setWithOffset(capTop, x, y, z);
						var immutable1 = mutable.immutable();
						var state = context.config().cap.getState(context.random(), immutable1);
						if (
							state.hasProperty(HugeMushroomBlock.WEST) &&
							state.hasProperty(HugeMushroomBlock.EAST) &&
							state.hasProperty(HugeMushroomBlock.NORTH) &&
							state.hasProperty(HugeMushroomBlock.SOUTH) &&
							state.hasProperty(HugeMushroomBlock.UP)
						) {
							state = state
								.setValue(HugeMushroomBlock.WEST, x < -innerRadius)
								.setValue(HugeMushroomBlock.EAST, x > innerRadius)
								.setValue(HugeMushroomBlock.NORTH, z < -innerRadius)
								.setValue(HugeMushroomBlock.SOUTH, z > innerRadius);
						}
						if (y < -1 && state.hasProperty(HugeMushroomBlock.UP)) {
							state = state.setValue(HugeMushroomBlock.UP, false);
						}
						toPlace.putIfAbsent(immutable1, state);
					}
				}
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

		// Roots
		for (Direction d : Direction.Plane.HORIZONTAL) {
			if (roots.containsKey(d)) {
				Map<BlockPos, BlockState> map = roots.get(d);
				boolean flag = false;
				for (Map.Entry<BlockPos, BlockState> entry : map.entrySet()) {
					if (canPlaceBlockHere(context.level(), entry.getKey())) continue;

					flag = true;
					break;
				}
				if (!flag) {
					for (Map.Entry<BlockPos, BlockState> entry : map.entrySet()) {
						this.setBlock(context.level(), entry.getKey(), entry.getValue());
					}
				}
			}
		}

		return true;
	}

	protected boolean canPlaceBlockHere(LevelAccessor level, BlockPos pos) {
		int i = pos.getY();
		if (i >= level.getMinBuildHeight() + 1 && i + 1 < level.getMaxBuildHeight()) {
			return level.isStateAtPosition(
				pos,
				state -> state.isAir()
					|| state.is(BlockTags.LOGS)
					|| state.canBeReplaced()
					|| state.is(ReduxTags.Blocks.MUSHROOM_CAPS)
			);
		}
		return false;
	}

	public static record Config(
		BlockStateProvider cap,
		BlockStateProvider spore,
		BlockStateProvider stem,
		BlockStateProvider stemHyphae,
		IntProvider height,
		IntProvider rootHeight,
		IntProvider capHeight
	) implements FeatureConfiguration {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create(builder -> builder
				.group(
					BlockStateProvider.CODEC.fieldOf("cap_provider").forGetter(Config::cap),
					BlockStateProvider.CODEC.fieldOf("spore_provider").forGetter(Config::spore),
					BlockStateProvider.CODEC.fieldOf("stem_provider").forGetter(Config::stem),
					BlockStateProvider.CODEC.fieldOf("stem_hyphae_provider").forGetter(Config::stemHyphae),
					IntProvider.CODEC.fieldOf("height").forGetter(Config::height),
					IntProvider.CODEC.fieldOf("root_height").forGetter(Config::rootHeight),
					IntProvider.CODEC.fieldOf("cap_height").forGetter(Config::capHeight)
				).apply(builder, Config::new)
		);
	}
}
