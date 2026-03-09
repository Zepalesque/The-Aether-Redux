package net.zepalesque.redux.world.tree.trunk;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;

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
		RandomSource random,
		int height,
		BlockPos pos,
		TreeConfiguration cfg
	) {
		var list = super.placeTrunk(level, setter, random, height, pos, cfg);

		for(int i = height - 2 - random.nextInt(4); i > height / 2; i -= 2 + random.nextInt(4)) {
			var f = random.nextFloat() * ((float)Math.PI * 2F);
			var j = 0;
			var k = 0;

			for(int l = 0; l < 5; ++l) {
				j = (int)(1.5F + Mth.cos(f) * (float)l);
				k = (int)(1.5F + Mth.sin(f) * (float)l);
				var blockpos = pos.offset(j, i - 3 + l / 2, k);
				this.placeLog(level, setter, random, blockpos, cfg);
			}

			list.add(new FoliagePlacer.FoliageAttachment(pos.offset(j, i, k), -2, false));
		}

		return list;
	}
}
