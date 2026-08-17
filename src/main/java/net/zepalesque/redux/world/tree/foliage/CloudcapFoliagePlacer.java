package net.zepalesque.redux.world.tree.foliage;

import com.ibm.icu.impl.Pair;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntUnaryOperator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.zepalesque.redux.block.natural.CloudCapBlock;
import net.zepalesque.redux.block.natural.CloudcapGillBlock;
import net.zepalesque.redux.util.world.PureRand;

public class CloudcapFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<CloudcapFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
			IntProvider.codec(3, 12).fieldOf("height").forGetter(instance -> instance.height),
			BlockStateProvider.CODEC.fieldOf("netting_body").forGetter(instance -> instance.nettingBody),
			BlockStateProvider.CODEC.fieldOf("netting_head").forGetter(instance -> instance.nettingHead),
			BlockStateProvider.CODEC.fieldOf("gill").forGetter(instance -> instance.gill)
		).apply(builder, CloudcapFoliagePlacer::new)
	);

	protected final IntProvider height;
	protected final BlockStateProvider nettingBody;
	protected final BlockStateProvider nettingHead;
	protected final BlockStateProvider gill;

	public CloudcapFoliagePlacer(IntProvider height, BlockStateProvider nettingBody, BlockStateProvider nettingHead, BlockStateProvider gill) {
		super(ConstantInt.ZERO, ConstantInt.ZERO);
		this.height = height;
		this.nettingBody = nettingBody;
		this.nettingHead = nettingHead;
		this.gill = gill;
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
		final var capSetter = new CapSetter(level, setter, rand, cfg);
		final var dirsToCut = rand.nextBoolean();
		final IntUnaryOperator cutAmount = i -> i % 2 == 0 ^ dirsToCut ? 0 : 1;

		var pos = attachment.pos();

		capSetter.set(pos, List.of(
			Direction.DOWN,
			Direction.NORTH,
			Direction.SOUTH,
			Direction.EAST,
			Direction.WEST
		));

		for (var dir : Direction.Plane.HORIZONTAL) {
			var offsetPos = pos.mutable().move(dir);

			capSetter.set(offsetPos, List.of(dir.getOpposite(), Direction.DOWN));
			capSetter.set(offsetPos.move(Direction.DOWN), List.of(
				Direction.UP,
				Direction.DOWN,
				dir,
				dir.getOpposite()
			));

			var nettingHeight = rand.nextInt(height - 3, height - 1);
			this.placeGill(setter, rand, offsetPos.move(Direction.DOWN), dir);
			this.placeGill(setter, rand, offsetPos.move(Direction.DOWN), dir);
			
			for (var j = 0; j < nettingHeight; j++) {
				this.tryPlaceNetting(level, setter, rand, offsetPos.move(Direction.DOWN), false);
			}
			this.tryPlaceNetting(level, setter, rand, offsetPos.move(Direction.DOWN), true);
		}

		for (var dir : Direction.Plane.HORIZONTAL) {
			var h = height - cutAmount.applyAsInt(dir.ordinal());
			var offsetPos = pos.mutable().move(dir, 2);
			capSetter.setColumn(offsetPos, h, List.of(dir.getOpposite()));
		}

		var dirs = List.of(
			Pair.of(Direction.NORTH, Direction.EAST),
			Pair.of(Direction.NORTH, Direction.WEST),
			Pair.of(Direction.SOUTH, Direction.EAST),
			Pair.of(Direction.SOUTH, Direction.WEST)
		);
		for (var i = 0; i < dirs.size(); ++i) {
			var p = dirs.get(i);
			var h = height - cutAmount.applyAsInt(i) - 1;
			var offsetPos = pos.mutable().move(p.first).move(p.second).move(Direction.DOWN);
			capSetter.setColumn(offsetPos, h, List.of(p.first.getOpposite(), p.second.getOpposite()));
		}
	}

	protected boolean tryPlaceNetting(
		LevelSimulatedReader level,
		FoliageSetter foliageSetter,
		RandomSource random,
		BlockPos pos,
		boolean bottom
	) {
		if (!level.isStateAtPosition(pos, BlockBehaviour.BlockStateBase::isAir)) {
			return false;
		}

		var state = (bottom ? this.nettingHead : this.nettingBody).getState(random, pos);
		if (state.hasProperty(BlockStateProperties.HALF)) {
			var seed = Mth.getSeed(pos.atY(0));
			var i = PureRand.getInt(seed);
			if ((pos.getY() & 1) == 0) i ^= 1;
			state = state.setValue(BlockStateProperties.HALF, Half.values()[i & 1]);
		}

		foliageSetter.set(pos, state);
		return true;
	}

	protected void placeGill(
		FoliageSetter foliageSetter,
		RandomSource random,
		BlockPos pos,
		Direction dir
	) {

		var state = this.gill.getState(random, pos);
		if (state.hasProperty(CloudcapGillBlock.FACING))
			state = state.setValue(CloudcapGillBlock.FACING, dir);

		foliageSetter.set(pos, state);
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

	private static record CapSetter(
		LevelSimulatedReader level,
		FoliageSetter setter,
		RandomSource rand,
		TreeConfiguration cfg
	) {
		void set(BlockPos pos, List<Direction> dirs) {
			// TODO: See if this can be optimized
			tryPlaceLeaf(
				this.level,
				new FoliageSetter() {
					@Override
					public boolean isSet(BlockPos pos) {
						return CapSetter.this.setter.isSet(pos);
					}

					@Override
					public void set(BlockPos pos, BlockState state) {
						if (state.getBlock() instanceof CloudCapBlock cap) state = cap.transform(
							state
								.setValue(BlockStateProperties.NORTH, !dirs.contains(Direction.NORTH))
								.setValue(BlockStateProperties.SOUTH, !dirs.contains(Direction.SOUTH))
								.setValue(BlockStateProperties.EAST, !dirs.contains(Direction.EAST))
								.setValue(BlockStateProperties.WEST, !dirs.contains(Direction.WEST))
								.setValue(BlockStateProperties.DOWN, false)
								.setValue(BlockStateProperties.UP, !dirs.contains(Direction.UP)),
							pos
						);
						CapSetter.this.setter.set(pos, state);
					}
				},
				this.rand,
				this.cfg,
				pos
			);
		}

		void setColumn(MutableBlockPos pos, int height, List<Direction> dirs) {
			var list = new ArrayList<>(dirs);

			list.add(Direction.DOWN);
			this.set(pos.move(Direction.DOWN), list);

			list.add(Direction.UP);
			for (var j = 1; j < height - 1; j++) {
				this.set(pos.move(Direction.DOWN), list);
			}

			list.remove(Direction.DOWN);
			this.set(pos.move(Direction.DOWN), list);
		}
	}
}
