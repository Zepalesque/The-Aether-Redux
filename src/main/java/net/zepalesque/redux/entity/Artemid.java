package net.zepalesque.redux.entity;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.Tags;

@ParametersAreNonnullByDefault
public class Artemid extends Animal {
	public Artemid(EntityType<? extends Artemid> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void registerGoals() {
		final Goal[] goals = {
			new FloatGoal(this),
			new PanicGoal(this, 1.2),
			new BreedGoal(this, 1.0),
			new TemptGoal(this, 1.0, this::isFood, false),
			new FollowParentGoal(this, 1.1),
			new RandomStrollGoal(this, 1.0),
			new LookAtPlayerGoal(this, Player.class, 6.0F),
			new RandomLookAroundGoal(this),
		};

		var priority = 0;
		for (var goal : goals) {
			goalSelector.addGoal(priority, goal);
			priority++;
		}
	}

	public static AttributeSupplier.Builder createAttributes() {
		return createLivingAttributes()
			.add(Attributes.MOVEMENT_SPEED, 0.3)
			.add(Attributes.MAX_HEALTH, 10)
			.add(Attributes.FOLLOW_RANGE, 6);
	}

	@Override
	public boolean isFood(ItemStack stack) {
		return stack.is(Tags.Items.FOODS_BERRY);
	}

	@Override
	@Nullable public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
		return ReduxEntities.ARTEMID.get().create(level);
	}

	@Override
	@Nullable protected SoundEvent getAmbientSound() {
		// todo
		return null;
	}

	@Override
	@Nullable protected SoundEvent getHurtSound(DamageSource damageSource) {
		// todo
		return null;
	}

	@Override
	@Nullable protected SoundEvent getDeathSound() {
		// todo
		return null;
	}
}
