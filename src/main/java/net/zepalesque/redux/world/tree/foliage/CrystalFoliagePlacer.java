package net.zepalesque.redux.world.tree.foliage;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class CrystalFoliagePlacer extends FoliagePlacer {
	public static final Logger LOGGER = LogUtils.getLogger();

	public static final MapCodec<CrystalFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> foliagePlacerParts(builder).apply(
			builder,
			CrystalFoliagePlacer::new
		)
	);

	public CrystalFoliagePlacer(IntProvider pRadius, IntProvider pOffset) {
		super(pRadius, pOffset);
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader level,
		FoliageSetter setter,
		RandomSource rand,
		TreeConfiguration config,
		int maxHeight,
		FoliageAttachment attachment,
		int height,
		int radius,
		int offset
	) {
		var origin = attachment.pos();

		// Place main piece
		int[] layers = { 2, 1, 0, -1, -2, -3, -4, -5, -6, };
		for (var y : layers) {
			placeLeavesRow(level, setter, rand, config, origin, radius, y, false);
		}
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
		BiDoublePredicate layer = (a, b) -> {
			var len = Mth.sqrt(x * x + z * z);
			return !(len < a || (len < b && rand.nextInt(1, 4) == 1));
		};

		return switch (y) {
			case -6 -> layer.test(1.25, 1.5);
			case -5 -> layer.test(1.75, 2.0);
			case -4 -> layer.test(2.00, 2.5);
			case -3 -> layer.test(1.75, 2.0);
			case -2 -> layer.test(1.25, 2.0);
			case -1 -> layer.test(1.25, 1.0);
			case 0 -> layer.test(0.75, 1.0);
			case 1 -> layer.test(0.75, 0.5);
			case 2 -> layer.test(0.00, 0.5);
			default -> {
				LOGGER.error("Invalid y: " + y);
				yield true;
			}
		}; 
	}

	@Override
	public int foliageHeight(RandomSource rand, int pHeight, TreeConfiguration config) {
		return 0;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ReduxFoliagePlacers.CRYSTAL_FOLIAGE.get();
	}

	@FunctionalInterface
	private interface BiDoublePredicate {
		boolean test(double a, double b);
	}
}
