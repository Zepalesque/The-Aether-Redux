package net.zepalesque.redux.world.tree.foliage;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class CloudcapFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<CloudcapFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			IntProvider.codec(3, 12).fieldOf("height").forGetter(fp -> fp.height)
		)
		.apply(instance, CloudcapFoliagePlacer::new)
	);

	protected final IntProvider height;

	public CloudcapFoliagePlacer(IntProvider height) {
		super(ConstantInt.ZERO, ConstantInt.ZERO);
		this.height = height;
	}

	@Override
	public int foliageHeight(RandomSource rand, int a, TreeConfiguration b) {
		return height.sample(rand);
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
		// need to make a custom array instead of Direction.Plane.HORIZONTAL so I can index it
		Direction[] horizontal = {
			Direction.WEST,
			Direction.NORTH,
			Direction.EAST,
			Direction.SOUTH,
		};
		BlockPos[] positions = {
			attachment.pos(),
			attachment.pos().east(),
			attachment.pos().south().east(),
			attachment.pos().south(),
		};

		for (var pos : positions) {
			for (var dir : horizontal) {
				var offsetPos = pos.mutable().move(dir);

				tryPlaceLeaf(level, setter, rand, cfg, offsetPos);
				tryPlaceLeaf(level, setter, rand, cfg, offsetPos.move(Direction.DOWN));
				tryPlaceLeaf(level, setter, rand, cfg, offsetPos.move(dir));
			}
		}

		List<BlockPos> positions2 = new ArrayList<>();

		// Please forgive me for the horrors that is this code 
		var index = 0;
		for (var pos : positions) {
			var newIndex = index >= 3 ? 0 : index + 1;
			pos = pos.below();
			positions2.add(pos.relative(horizontal[index], 2));
			positions2.add(pos.relative(horizontal[newIndex], 2));
			positions2.add(pos.relative(horizontal[index]).relative(horizontal[newIndex]));
			index = newIndex;
		}

		for (var h = 0; h < height - 2; h++) {
			// I hate GC because you can never tell if you're modifying the original object or not,
			// and code like this needs to be written defensively.
			// In Rust this'd be far simpler, no indexing would be necessary.
			for (var i = 0; i < positions2.size(); i++) {
				var pos = positions2.get(i).below();
				tryPlaceLeaf(level, setter, rand, cfg, pos);
				positions2.set(i, pos);
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
		return false;
	}

	@Override
	protected FoliagePlacerType<?> type() {
		return ReduxFoliagePlacers.CLOUDCAP_FOLIAGE.get();
	}
}
