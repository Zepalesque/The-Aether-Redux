package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.natural.AetherDoubleDropBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Fallable;
import net.minecraft.world.level.block.state.BlockState;

public class HolysiltBlock extends AetherDoubleDropBlock implements Fallable {
	public HolysiltBlock(Properties properties) {
		super(properties);
	}
	
	@Override
	public void stepOn(Level lvl, BlockPos pos, BlockState state, Entity entity) {
		super.stepOn(lvl, pos, state, entity);
		if (!lvl.isClientSide())
			if (!entity.isSteppingCarefully() && isFree(lvl.getBlockState(pos.below())) && pos.getY() >= lvl.getMinBuildHeight()) {
				var fall = FallingBlockEntity.fall(lvl, pos, state);
				this.falling(fall);
			}
	}
	public static boolean isFree(BlockState state) {
		return state.isAir() || state.is(BlockTags.FIRE) || state.liquid() || state.canBeReplaced();
	}
	
	protected void falling(FallingBlockEntity entity) {}
	
	public void animateTick(BlockState state, Level lvl, BlockPos pos, RandomSource rand) {
		if (rand.nextInt(16) == 0) {
			var below = pos.below();
			if (isFree(lvl.getBlockState(below))) {
				var x = (double)pos.getX() + rand.nextDouble();
				var y = (double)pos.getY() - 0.05;
				var z = (double)pos.getZ() + rand.nextDouble();
				lvl.addParticle(new BlockParticleOption(ParticleTypes.FALLING_DUST, state), x, y, z, 0, 0, 0);
			}
		}
	}
}
