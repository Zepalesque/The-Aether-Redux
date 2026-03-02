package net.zepalesque.redux.block.natural;

import com.mojang.serialization.MapCodec;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class CaelgaeBlock extends Block implements BonemealableBlock {
	public static final MapCodec<CaelgaeBlock> CODEC = simpleCodec(CaelgaeBlock::new);
	protected static final VoxelShape SHAPE = Block.box(0.0, 0.0, 0.0, 16.0, 1.5, 16.0);
	public CaelgaeBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}
	
	@Override
	public MapCodec<CaelgaeBlock> codec() {
		return CODEC;
	}
	
	@Override
	protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}
	
	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return mayPlaceOn(level, pos.below());
	}
	
	private boolean mayPlaceOn(BlockGetter level, BlockPos pos) {
		var fluid = level.getFluidState(pos);
		var above = level.getFluidState(pos.above());
		return fluid.getType() == Fluids.WATER && above.getType() == Fluids.EMPTY;
	}
	
	@Override
	public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
		return anySpreadPos(level, pos);
	}
	
	@Override
	public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
		return true;
	}
	
	@Override
	public void performBonemeal(ServerLevel lvl, RandomSource rand, BlockPos pos, BlockState state) {
		var spreadTo = findSpreadPos(lvl, pos, rand);
		spreadTo.ifPresent(p -> lvl.setBlock(p, this.defaultBlockState(), 3));
	}
	
	public Optional<BlockPos> findSpreadPos(BlockGetter lvl, BlockPos pos, RandomSource rand) {
		return Direction.Plane.HORIZONTAL
			.shuffledCopy(rand)
			.stream()
			.map(pos::relative)
			.filter(p -> canSpreadTo(lvl, p))
			.findFirst();
	}
	
	public boolean anySpreadPos(BlockGetter lvl, BlockPos pos) {
		return Direction.Plane.HORIZONTAL
			.stream()
			.map(pos::relative)
			.anyMatch(p -> canSpreadTo(lvl, p));
	}
	
	public boolean canSpreadTo(BlockGetter lvl, BlockPos pos) {
		return mayPlaceOn(lvl, pos.below()) && !lvl.getBlockState(pos).is(this);
	}
	
	@Override
	protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity.getType().equals(EntityType.FALLING_BLOCK)) level.destroyBlock(pos, true);
		else if (entity instanceof LivingEntity && !entity.getType().is(EntityTypeTags.AQUATIC) && isEntityIntersecting(state, level, pos, entity) && entity.isInWater())
			entity.setDeltaMovement(entity.getDeltaMovement().multiply(new Vec3(0.85, 0.85, 0.85)));
	}
	
	protected boolean isEntityIntersecting(BlockState state, Level level, BlockPos pos, Entity entity) {
		var entityBox = entity.getBoundingBox();
		var selfBox = getShape(state, level, pos, CollisionContext.of(entity)).bounds().move(pos);
		
		return entityBox.intersects(selfBox);
	}
	
	@Override
	protected BlockState updateShape(
		BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos pos, BlockPos neighborPos
	) {
		return !this.canSurvive(state, level, pos)
			? Blocks.AIR.defaultBlockState()
			: super.updateShape(state, direction, neighborState, level, pos, neighborPos);
	}
}
