package net.zepalesque.redux.entity.passive;

import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.entity.passive.AetherAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class Shimmercow extends AetherAnimal {

    private static final EntityDataAccessor<Boolean> CRAZY = SynchedEntityData.defineId(Shimmercow.class, EntityDataSerializers.BOOLEAN);

    public Shimmercow(EntityType<? extends Shimmercow> type, Level level) {
        super(type, level);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(CRAZY, false);
    }


    public boolean isCrazy() {
        return this.getEntityData().get(CRAZY);
    }

    public void setCrazy(boolean crazy) {
        this.getEntityData().set(CRAZY, crazy);
    }

    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @org.jetbrains.annotations.Nullable SpawnGroupData spawnData, @org.jetbrains.annotations.Nullable CompoundTag tag) {
        this.setCrazy(this.getRandom().nextFloat() < 0.01);
        return spawnData;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.of(ReduxTags.Items.GLIMMERCOW_TEMPTATION_ITEMS), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new FallingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.@NotNull Builder createMobAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 25.0).add(Attributes.MOVEMENT_SPEED, 0.15).add(Attributes.KNOCKBACK_RESISTANCE, 0.3F);
    }


    public boolean isFood(ItemStack stack) {
        return stack.is(ReduxTags.Items.GLIMMERCOW_TEMPTATION_ITEMS);
    }

    public InteractionResult mobInteract(Player playerEntity, InteractionHand hand) {
        ItemStack itemStack = playerEntity.getItemInHand(hand);
        if (itemStack.is(net.minecraft.world.item.Items.BUCKET) && !this.isBaby()) {
            playerEntity.playSound(AetherSoundEvents.ENTITY_FLYING_COW_MILK.get(), 1.0F, 1.0F);
            ItemStack itemStack1 = ItemUtils.createFilledResult(itemStack, playerEntity, net.minecraft.world.item.Items.MILK_BUCKET.getDefaultInstance());
            playerEntity.setItemInHand(hand, itemStack1);
            return InteractionResult.sidedSuccess(this.level.isClientSide());
        } else {
            return super.mobInteract(playerEntity, hand);
        }
    }


    @Nullable
    protected SoundEvent getAmbientSound() {
        return this.isCrazy() ? ReduxSoundEvents.CRAZY_COW_AMBIENT.get() : ReduxSoundEvents.SHIMMERCOW_AMBIENT.get();
    }

    @Nullable
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return this.isCrazy() ? ReduxSoundEvents.CRAZY_COW_HURT.get() : ReduxSoundEvents.SHIMMERCOW_HURT.get();
    }

    @Nullable
    protected SoundEvent getDeathSound() {
        return this.isCrazy() ? ReduxSoundEvents.CRAZY_COW_DEATH.get() : ReduxSoundEvents.SHIMMERCOW_DEATH.get();
    }


    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(AetherSoundEvents.ENTITY_FLYING_COW_STEP.get(), 0.15F, 1.0F);
    }

    protected float getSoundVolume() {
        return 0.4F;
    }


    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        return ReduxEntityTypes.SHIMMERCOW.get().create(level);
    }

    protected float getStandingEyeHeight(Pose pose, EntityDimensions size) {
        return this.isBaby() ? size.height * 0.95F : 1.3F;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsCrazy", this.isCrazy());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("IsCrazy")) {
            this.setCrazy(compound.getBoolean("IsCrazy"));
        }
    }
}
