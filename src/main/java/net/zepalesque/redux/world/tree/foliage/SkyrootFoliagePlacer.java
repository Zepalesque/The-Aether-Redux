package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class SkyrootFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<SkyrootFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> foliagePlacerParts(builder).apply(
			builder,
			SkyrootFoliagePlacer::new
		)
	);

	public SkyrootFoliagePlacer(IntProvider pRadius, IntProvider pOffset) {
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
		this.placeLeavesRow(level, setter, rand, config, origin, radius - 1, 0, false);
		this.placeLeavesRow(level, setter, rand, config, origin, radius, -1, false);
		this.placeLeavesRow(level, setter, rand, config, origin, radius, -2, false);
		this.placeLeavesRow(level, setter, rand, config, origin, radius, -3, false);
		this.placeLeavesRow(level, setter, rand, config, origin, radius - 1, -4, false);
	}

	@Override
	public int foliageHeight(RandomSource rand, int pHeight, TreeConfiguration config) {
		return 0;
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
		return switch (y) {
			// Only skip the location if it is on the corners, but do so 75% of the time
			case 0, -4 -> x + z >= radius * 2 && rand.nextFloat() < 0.75F;
			// Skip the corners always, and skip stuff outside a diamond shape 75% of the time
			case -1 -> {
				var diamond = x + z <= radius;
				var corners = x + z <= radius + 1;
				yield !corners || (!diamond && rand.nextFloat() < 0.75F);
			}
			// Skip the location if it is on the corners and an unlikely boolean check succeeds
			case -2 -> x + z >= radius * 2 && rand.nextFloat() < 0.25F;
			// Skip the location if it is on the corners and a boolean check succeeds
			default -> x + z >= radius * 2 && rand.nextBoolean();
		};
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ReduxFoliagePlacers.SKYROOT_FOLIAGE.get();
	}
}
