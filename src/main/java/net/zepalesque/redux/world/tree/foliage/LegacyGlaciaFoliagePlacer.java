package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class LegacyGlaciaFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<LegacyGlaciaFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> foliagePlacerParts(builder)
			.and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter(f -> f.trunkHeight))
			.apply(builder, LegacyGlaciaFoliagePlacer::new)
	);

	private final IntProvider trunkHeight;

	public LegacyGlaciaFoliagePlacer(IntProvider radius, IntProvider offset, IntProvider trunkHeight) {
		super(radius, offset);
		this.trunkHeight = trunkHeight;
	}

	protected FoliagePlacerType<?> type() {
		return ReduxFoliagePlacers.MOONFIR.get();
	}

	@Override
	public int foliageHeight(RandomSource rand, int i, TreeConfiguration cfg) {
		return this.trunkHeight.sample(rand) + 1;
	}

	@Override
	protected void createFoliage(
		LevelSimulatedReader reader,
		FoliageSetter setter,
		RandomSource rand,
		TreeConfiguration cfg,
		int maxHeight,
		FoliagePlacer.FoliageAttachment attachment,
		int height,
		int radius,
		int offset
	) {
		var origin = attachment.pos().above(offset + 1);

		for (int i = 0; i < height; i++) {
			this.placeLeavesRow(reader, setter, rand, cfg, origin, radius, -i, false);
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
		final var x2 = (float)(x & 1) / 2f;
		final var z2 = (float)(z & 1) / 2f;

		final var delta = Math.log(-y) / (y < 4 ? 1 : 2);
		final var maxDist = Mth.lerp(delta, 0, 1) + (float)rand.nextInt(-4, 4) / 10f;
		final var dist = Mth.sqrt(Mth.square(x - x2) + Mth.square(z - z2));
		
		return dist > maxDist;
	}
}
