package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class CloudcapFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<CloudcapFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		instance -> instance.group(
			IntProvider.codec(3, 12).fieldOf("height").forGetter(fp -> fp.height),
			BlockStateProvider.CODEC.fieldOf("netting").forGetter(fp -> fp.netting)
		)
		.apply(instance, CloudcapFoliagePlacer::new)
	);

	protected final IntProvider height;
	protected final BlockStateProvider netting;

	public CloudcapFoliagePlacer(IntProvider height, BlockStateProvider netting) {
		super(ConstantInt.ZERO, ConstantInt.ZERO);
		this.height = height;
		this.netting = netting;
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
		BlockPos[] positions = {
			attachment.pos(),
			attachment.pos().east(),
			attachment.pos().south().east(),
			attachment.pos().south(),
		};

		for (var pos : positions) {
			for (var dir : Direction.Plane.HORIZONTAL) {
				var offsetPos = pos.mutable().move(dir);

				tryPlaceLeaf(level, setter, rand, cfg, offsetPos);
				tryPlaceLeaf(level, setter, rand, cfg, offsetPos.move(Direction.DOWN));

				for (var i = 0; i <= rand.nextInt(height, height + 2); i++) {
					tryPlaceNetting(level, setter, rand, offsetPos.relative(Direction.DOWN, i));
				}
			}
		}
		for (var pos : positions) {
			for (var dir : Direction.Plane.HORIZONTAL) {
				var offsetPos = pos.mutable().move(dir, 2);

				for (var i = 0; i < height; i++) {
					tryPlaceLeaf(level, setter, rand, cfg, offsetPos.move(Direction.DOWN));
				}
			}
		}

		positions[0] = positions[0].below().north().west().mutable();
		positions[1] = positions[1].below().north().east().mutable();
		positions[2] = positions[2].below().south().east().mutable();
		positions[3] = positions[3].below().south().west().mutable();

		for (var pos : positions) {
			for (var i = 0; i < height - 1; i++) {
				tryPlaceLeaf(level, setter, rand, cfg, ((MutableBlockPos)pos).move(Direction.DOWN));
			}
		}
	}

	protected boolean tryPlaceNetting(
		LevelSimulatedReader level,
		FoliageSetter foliageSetter,
		RandomSource random,
		BlockPos pos
	) {
		if (!level.isStateAtPosition(pos, state -> state.isAir())) {
			return false;
		}

		var state = this.netting.getState(random, pos);
		foliageSetter.set(pos, state);
		return true;
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
