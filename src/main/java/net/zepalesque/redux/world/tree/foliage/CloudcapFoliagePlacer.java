package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.function.IntUnaryOperator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
		return this.height.sample(rand);
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
		var pos = attachment.pos().mutable();
		tryPlaceLeaf(level, setter, rand, cfg, pos);

		final var dirsToCut = rand.nextBoolean();

		final IntUnaryOperator cutAmount = i -> (i % 2 == 0) ^ dirsToCut ? 0 : 1;

		for (var dir : Direction.Plane.HORIZONTAL) {
			var offsetPos = pos.mutable().move(dir);

			tryPlaceLeaf(level, setter, rand, cfg, offsetPos);
			tryPlaceLeaf(level, setter, rand, cfg, offsetPos.move(Direction.DOWN));

			var nettingHeight = rand.nextInt(height - 1, height + 1);
			for (var j = 0; j < nettingHeight; j++) {
				this.tryPlaceNetting(level, setter, rand, offsetPos.move(Direction.DOWN), false);
			}
			this.tryPlaceNetting(level, setter, rand, offsetPos.move(Direction.DOWN), true);
		}

		for (var dir : Direction.Plane.HORIZONTAL) {
			var h = height - cutAmount.applyAsInt(dir.ordinal());
			var offsetPos = pos.mutable().move(dir, 2);

			for (var j = 0; j < h; j++) {
				tryPlaceLeaf(level, setter, rand, cfg, offsetPos.move(Direction.DOWN));
			}
		}

		MutableBlockPos[] positions = {
			pos.below().north().west().mutable(),
			pos.below().north().east().mutable(),
			pos.below().south().east().mutable(),
			pos.below().south().west().mutable(),
		};

		for (var i = 0; i < positions.length; ++i) {
			var h = height - cutAmount.applyAsInt(i);
			var otherPos = positions[i];

			for (var j = 0; j < h - 1; j++) {
				tryPlaceLeaf(level, setter, rand, cfg, otherPos.move(Direction.DOWN));
			}
		}
	}

	protected boolean tryPlaceNetting(
		LevelSimulatedReader level,
		FoliageSetter foliageSetter,
		RandomSource random,
		BlockPos pos,
		boolean bottom
	) {
		if (!level.isStateAtPosition(pos, state -> state.isAir())) {
			return false;
		}

		var state = this.netting.getState(random, pos);
		if (state.hasProperty(BlockStateProperties.BOTTOM)) {
			state = state.setValue(BlockStateProperties.BOTTOM, bottom);
		}

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
