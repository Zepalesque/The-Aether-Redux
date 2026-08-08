package net.zepalesque.redux.world.tree.trunk;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
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

public class PrismaTrunkPlacer extends TrunkPlacer {
	protected final IntProvider height;
	protected final IntProvider offset1;
	protected final IntProvider offset2;

	public static final MapCodec<PrismaTrunkPlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> builder.group(
			IntProvider.codec(8, Integer.MAX_VALUE).fieldOf("height").forGetter(i -> i.height),
			IntProvider.codec(1, 8).fieldOf("offset1").forGetter(i -> i.offset1),
			IntProvider.codec(1, 8).fieldOf("offset2").forGetter(i -> i.offset2)
		)
		.apply(builder, PrismaTrunkPlacer::new)
	);


	public PrismaTrunkPlacer(IntProvider height, IntProvider offset1, IntProvider offset2) {
		super(0, 0, 0);
		this.height = height;
		this.offset1 = offset1;
		this.offset2 = offset2;
	}

	@Override
	protected TrunkPlacerType<?> type() {
		return ReduxTrunkPlacers.PRISMA_TRUNK.get();
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
		var height = Math.max(this.height.sample(rand) - 8, 0);
		var pos = blockPos.mutable();
		var dir = Direction.Plane.HORIZONTAL.getRandomDirection(rand);
		var offset1 = this.offset1.sample(rand);
		var offset2 = this.offset2.sample(rand);

		// place log at starting position
		this.placeLog(level, setter, rand, pos, cfg);

		// place logs up until first bend
		for (int i = 0; i <= height; i++) {
			pos.move(Direction.UP);
			this.placeLog(level, setter, rand, pos, cfg);
		}

		// Bends
		this.doBend(level, setter, rand, pos, cfg, dir, offset1);
		this.doBend(level, setter, rand, pos, cfg, dir.getOpposite(), offset1 + offset2 + 1);
		this.doBend(level, setter, rand, pos, cfg, dir, offset2);

		pos.move(Direction.UP);
		this.placeLog(level, setter, rand, pos, cfg);
		pos.move(Direction.UP);
		return List.of(new FoliageAttachment(pos, 0, false));
	}

	private void doBend(
		LevelSimulatedReader level,
		BiConsumer<BlockPos, BlockState> setter,
		RandomSource rand,
		MutableBlockPos pos,
		TreeConfiguration cfg,
		Direction dir,
		int amount
	) {
		for (int i = 0; i < amount; i++) {
			pos.move(dir);
			this.placeLog(
				level,
				setter,
				rand,
				pos,
				cfg,
				state -> state.setValue(BlockStateProperties.AXIS, dir.getAxis())
			);
		}
		for (int i = 0; i < 2; i++) {
			pos.move(Direction.UP);
			this.placeLog(level, setter, rand, pos, cfg);
		};
	}
}
