package net.zepalesque.redux.block.natural;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.NetherVines;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class HangingAetherVinesHead extends GrowingPlantHeadBlock {
	protected static final VoxelShape SHAPE = Block.box(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);

	private final Optional<TagKey<Block>> leafTag;
	private final Holder<Block> body;

	public HangingAetherVinesHead(Properties properties, Optional<TagKey<Block>> leafTag, Holder<Block> body) {
		super(properties, Direction.DOWN, SHAPE, false, 0.1D);
		this.leafTag = leafTag;
		this.body = body;
	}

	public static final MapCodec<HangingAetherVinesHead> CODEC = RecordCodecBuilder
		.mapCodec(builder -> builder.group(
			propertiesCodec(),
			TagKey.codec(Registries.BLOCK)
				.optionalFieldOf("leaf_tag")
				.forGetter(instance -> instance.leafTag),
			BuiltInRegistries.BLOCK.holderByNameCodec()
				.fieldOf("body_block")
				.forGetter(instance -> instance.body)
		).apply(builder, HangingAetherVinesHead::new)
	);

	@Override
	protected MapCodec<? extends GrowingPlantHeadBlock> codec() {
		return CODEC;
	}

	@Override
	protected int getBlocksToGrowWhenBonemealed(RandomSource random) {
		return NetherVines.getBlocksToGrowWhenBonemealed(random);
	}

	@Override
	protected Block getBodyBlock() {
		return this.body.value();
	}

	@Override
	protected boolean canGrowInto(BlockState state) {
		return NetherVines.isValidGrowthState(state);
	}

	public int getLength(Level level, BlockPos pos) {
		var i = 0;
		while (
				!level.isOutsideBuildHeight(pos.getY() + i) &&
				level.isStateAtPosition(
					pos.above(i),
					(state) -> state.is(this.getHeadBlock()) || state.is(this.getBodyBlock())
				)
			)
			i++;
		return i;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader lvl, BlockPos pos) {
		var relative = pos.relative(this.growthDirection.getOpposite());
		var relState = lvl.getBlockState(relative);
		return !this.canAttachTo(relState)
			? super.canSurvive(state, lvl, pos)
			: super.canSurvive(state, lvl, pos) || this.checkAboveState(relState);
	}
	
	protected boolean checkAboveState(BlockState state) {
		return this.leafTag.isEmpty() || state.is(this.leafTag.get());
	}
	
	
	@Override
	public void randomTick(
		BlockState pState,
		ServerLevel pLevel,
		BlockPos pPos,
		RandomSource pRandom
	) {
		if (this.getLength(pLevel, pPos) < 10) super.randomTick(pState, pLevel, pPos, pRandom);
	}
}
