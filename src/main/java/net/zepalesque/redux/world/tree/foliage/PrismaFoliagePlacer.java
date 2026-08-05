package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class PrismaFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<PrismaFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> foliagePlacerParts(builder)
			.apply(builder, PrismaFoliagePlacer::new)
	);

	public PrismaFoliagePlacer(IntProvider radius, IntProvider offset) {
		super(radius, offset);
	}

	@Override
	public int foliageHeight(RandomSource random, int height, TreeConfiguration config) {
		return 4;
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
		for (int i = offset; i >= offset - height; --i) {
            this.placeLeavesRow(level, setter, rand, cfg, attachment.pos(), radius, i, attachment.doubleTrunk());
        }

		for (int x = -radius; x <= radius; x++) {
			for (int z = -radius; z <= radius; z++) {
				if (Mth.sqrt(x * x + z * z) > radius) continue;
				if (rand.nextInt(2) == 1) continue;

				var pos = attachment.pos().mutable();
				pos.move(x, -radius, z);

				for (int i = 1; i <= rand.nextInt(1, 3); i++) {
					tryPlaceLeaf(level, setter, rand, cfg, pos);
					pos.move(Direction.DOWN);
				}
			}
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
		var dist = Mth.sqrt(x * x + z * z);
		var max = switch (y) {
			case 1 -> radius / 2.5;
			case 0 -> (radius / 1.5) + rand.nextFloat();
			case -1 -> (radius / 1.25) + rand.nextFloat() + 0.15;
			default -> radius;
		};

		return dist > max;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ReduxFoliagePlacers.PRISMA_FOLIAGE.get();
	}
}
