package net.zepalesque.redux.world.tree.trunk;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.zepalesque.redux.Redux;

public class HookedTrunkPlacer extends GiantTrunkPlacer {
	public static final MapCodec<HookedTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> trunkPlacerParts(instance)
			.apply(instance, HookedTrunkPlacer::new)
	);

	public HookedTrunkPlacer(int baseHeight, int heightRandA, int heightRandB) {
		super(baseHeight, heightRandA, heightRandB);
	}

	@Override
	public List<FoliagePlacer.FoliageAttachment> placeTrunk(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> setter,
		RandomSource rand,
		int height,
		BlockPos pos,
		TreeConfiguration cfg
	) {
		var list = new ArrayList<>(super.placeTrunk(level, setter, rand, height, pos, cfg));

		list.addAll(placeBranches(cfg, rand, level, setter, height, Direction.NORTH, pos));
		list.addAll(placeBranches(cfg, rand, level, setter, height, Direction.EAST, pos.east()));
		list.addAll(placeBranches(cfg, rand, level, setter, height, Direction.SOUTH, pos.south().east()));
		list.addAll(placeBranches(cfg, rand, level, setter, height, Direction.WEST, pos.south()));

		return list;
	}

	protected List<FoliagePlacer.FoliageAttachment> placeBranches(
		TreeConfiguration cfg,
		RandomSource rand,
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> setter,
		int height,
		Direction dir,
		BlockPos blockPos
	) {
		var branchHeight = switch (dir) {
			case Direction.EAST -> 4;
			case Direction.SOUTH -> 5;
			case Direction.WEST -> 6;
			default -> 3;
		};

		for (; branchHeight < height - 2; branchHeight += 4) {
			var pos = blockPos.above(branchHeight).mutable();
			if (pos == null) {
				Redux.LOGGER.error("pos is null!");
			}

			for (int i = 0; i < 4; i++) {
				pos = pos.move(dir);
				this.placeLog(
					level,
					setter,
					rand,
					pos,
					cfg,
					state -> state.setValue(BlockStateProperties.AXIS, dir.getAxis())
				);
			}
		}
		return List.of();
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return ReduxTrunkPlacers.HOOKED_TRUNK.get();
	}
}
