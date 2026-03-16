package net.zepalesque.redux.world.tree.trunk;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer.FoliageAttachment;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

public class OffsetTrunkPlacer extends TrunkPlacer {
	protected final IntProvider height;

	public static final MapCodec<OffsetTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> builder.group(
			IntProvider.codec(4, Integer.MAX_VALUE).fieldOf("height").forGetter(i -> i.height)
		)
		.apply(builder, OffsetTrunkPlacer::new)
	);


	public OffsetTrunkPlacer(IntProvider height) {
		super(0, 0, 0);
		this.height = height;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return ReduxTrunkPlacers.OFFSET_TRUNK.get();
	}

	@Override
	public List<FoliageAttachment> placeTrunk(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> setter,
		RandomSource rand,
		int iunno,
		BlockPos blockPos,
		TreeConfiguration cfg
	) {
		var height = this.height.sample(rand);
		var pos = blockPos.mutable();
		var dir = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
		var offsetHeight = rand.nextInt((int)(height / 2.25), (int)(height / 1.5));

		// place log at starting position
		placeLog(level, setter, rand, pos, cfg);

		// place logs up until offset
		for (int i = 0; i <= offsetHeight; i++) {
			pos.move(Direction.UP);
			placeLog(level, setter, rand, pos, cfg);
		}

		// perform offset
		pos.move(dir);
		placeLog(
			level,
			setter,
			rand,
			pos,
			cfg,
			state -> state.setValue(BlockStateProperties.AXIS, dir.getAxis())
		);

		// place remainder of logs
		for (int i = offsetHeight; i <= height; i++) {
			pos.move(Direction.UP);
			placeLog(level, setter, rand, pos, cfg);
		}

		pos.move(Direction.UP);
		return List.of(new FoliageAttachment(pos, 0, false));
	}
}
