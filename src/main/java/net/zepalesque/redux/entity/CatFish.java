package net.zepalesque.redux.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.AbstractSchoolingFish;
import net.minecraft.world.entity.animal.WaterAnimal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.zepalesque.redux.world.feature.gen.LakesFeature;

public class CatFish extends AbstractSchoolingFish {
	public CatFish(EntityType<? extends CatFish> entityType, Level level) {
		super(entityType, level);
	}
	
	public static boolean checkCatFishSpawnRules(
		EntityType<? extends WaterAnimal> waterAnimal, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
	) {
		int i = LakesFeature.Y_LEVEL_DEFAULT;
		int j = i - 13;
		return pos.getY() >= j
			&& pos.getY() <= i
			&& level.getFluidState(pos.below()).is(FluidTags.WATER)
			&& level.getBlockState(pos.above()).is(Blocks.WATER);
	}

	// TODO: Bucket + skyroot bucket
	
	@Override
	public ItemStack getBucketItemStack() {
		return ItemStack.EMPTY;
	}

	// TODO: New sounds, some mechanics or something perhaps as well
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.COD_AMBIENT;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.COD_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSource) {
		return SoundEvents.COD_HURT;
	}

	@Override
	protected SoundEvent getFlopSound() {
		return SoundEvents.COD_FLOP;
	}
}
