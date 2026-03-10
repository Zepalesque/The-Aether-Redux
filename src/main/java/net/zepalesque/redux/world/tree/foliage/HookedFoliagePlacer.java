package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.Consumer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class HookedFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<HookedFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> foliagePlacerParts(builder).apply(
			builder,
			HookedFoliagePlacer::new
		)
	);

	public HookedFoliagePlacer(IntProvider radius, IntProvider offset) {
		super(radius, offset);
	}

	@Override
	public int foliageHeight(RandomSource rand, int i, TreeConfiguration cfg) {
		return 0;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader level,
		FoliageSetter setter,
		RandomSource rand,
		TreeConfiguration cfg,
		int maxHeight,
		FoliageAttachment attachment,
		int height,
		int radius,
		int offsetFromGround
	) {
		var pos = attachment.pos();

		if (attachment.doubleTrunk()) {
			this.placeTopLeaves(pos, level, setter, rand, cfg);
		} else {
			this.placeSideLeaves(pos, level, setter, rand, cfg);
		}
	}

	private void placeTopLeaves(
		BlockPos blockPos,
		LevelSimulatedReader level,
		FoliageSetter setter, 
		RandomSource rand,
		TreeConfiguration cfg
	) {
		Consumer<BlockPos> place = pos -> tryPlaceLeaf(level, setter, rand, cfg, pos);
		Consumer<BlockPos> sphere = p -> {
			var pos = rand.nextBoolean() ? p : p.above();

			for (int i = 0; i < 4; i++) {
				var offset = i;
				place.accept(pos.below(offset));

				Direction.Plane.HORIZONTAL.forEach(dir -> {
					place.accept(pos.relative(dir).below(offset));
					if (offset == 0 || offset == 3) return;

					place.accept(pos.relative(dir, 2).below(offset));
					place.accept(pos.relative(dir).relative(dir.getClockWise()).below(offset));
				});
			}
		};

		sphere.accept(blockPos);
		sphere.accept(blockPos.south().east());
	}

	private void placeSideLeaves(
		BlockPos blockPos,
		LevelSimulatedReader level,
		FoliageSetter setter, 
		RandomSource rand,
		TreeConfiguration cfg
	) {
		// TODO: implement
	}

	@Override
	protected boolean shouldSkipLocation(
		RandomSource rand,
		int x,
		int y,
		int z,
		int radius,
		boolean large
	) {
		return false;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ReduxFoliagePlacers.HOOKED_FOLIAGE.get();
	}
}
