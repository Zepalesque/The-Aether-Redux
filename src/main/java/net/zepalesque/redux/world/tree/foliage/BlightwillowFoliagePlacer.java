package net.zepalesque.redux.world.tree.foliage;

import com.aetherteam.aether.world.foliageplacer.GoldenOakFoliagePlacer;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class BlightwillowFoliagePlacer extends GoldenOakFoliagePlacer {
	public static final MapCodec<BlightwillowFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> foliagePlacerParts(builder)
			.apply(builder, BlightwillowFoliagePlacer::new)
	);

	public BlightwillowFoliagePlacer(IntProvider radius, IntProvider offset) {
		// GoldenOakFoliagePlacer does not seem to make use of the trunkHeight field
		super(radius, offset, null);
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
		int offset
	) {
		super.createFoliage(level, setter, rand, cfg, maxHeight, attachment, height, radius, offset);

		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if (Mth.sqrt(x * x + z * z) > radius + 1) continue;
				if (rand.nextInt(4) == 1) continue;

				var pos = attachment.pos().mutable();
				pos.move(x, -radius, z);

				for (int i = 1; i < rand.nextInt(radius, radius * 2); i++) {
					tryPlaceLeaf(level, setter, rand, cfg, pos);
					pos.move(Direction.DOWN);
				}
			}
		}
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ReduxFoliagePlacers.BLIGHTWILLOW_FOLIAGE.get();
	}
}
