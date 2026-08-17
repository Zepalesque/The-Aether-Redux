package net.zepalesque.redux.block.natural;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Optional;

public class HangingAetherVinesBody extends GrowingPlantBodyBlock {
	public static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 16.0D, 15.0D);

	private final Optional<TagKey<Block>> leafTag;
	private final Holder<Block> head;

	public HangingAetherVinesBody(Properties properties, Optional<TagKey<Block>> leafTag, Holder<Block> head) {
		super(properties, Direction.DOWN, SHAPE, false);
		this.leafTag = leafTag;
		this.head = head;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader lvl, BlockPos pos) {
		var relative = pos.relative(this.growthDirection.getOpposite());
		var relState = lvl.getBlockState(relative);
		return !this.canAttachTo(relState)
			? super.canSurvive(state, lvl, pos)
			: super.canSurvive(state, lvl, pos)
				|| this.checkAboveState(relState);
	}
	
	protected boolean checkAboveState(BlockState state) {
		return this.leafTag.isEmpty() || state.is(this.leafTag.get());
	}

	@Override
	protected GrowingPlantHeadBlock getHeadBlock() {
		try {
			var head = this.head.value();
			return (GrowingPlantHeadBlock) head;
		} catch (ClassCastException e) { // Don't cast the IllegalStateException that occurs if the holder has no value, as it should give enough info
			throw new IllegalStateException("HangingAetherVinesBody's associated head block was not an instance of GrowingPlantHeadBlock!", e);
		}
	}

	public static final MapCodec<HangingAetherVinesBody> CODEC = RecordCodecBuilder.mapCodec(
		builder -> builder.group(
			propertiesCodec(),
			TagKey.codec(Registries.BLOCK)
				.optionalFieldOf("leaf_tag")
				.forGetter(instance -> instance.leafTag),
			BuiltInRegistries.BLOCK
				.holderByNameCodec()
				.fieldOf("head_block")
				.forGetter(instance -> instance.head)
		).apply(builder, HangingAetherVinesBody::new));


	@Override
	protected MapCodec<? extends GrowingPlantBodyBlock> codec() {
		return CODEC;
	}
}
