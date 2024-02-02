
package net.zepalesque.redux.entity.passive;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.entity.ai.goal.MykapodWanderGoal;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;

public class Mykapod extends PathfinderMob implements GeoEntity {

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    public Mykapod(EntityType<? extends Mykapod> entityType, Level level) {
        super(entityType, level);
    }

    private static final EntityDataAccessor<Boolean> IS_HIDING = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> HAS_SHELL = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.BOOLEAN);


    /**
     * Should be a value from 0 to 60. 0 to 30 represent the progress of the hiding animation, 30 to 60 represent it exiting the shell.
     */
    @OnlyIn(Dist.CLIENT)
    public int hideAnim, prevHideAnim = 0;


    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MykapodWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.1F).add(Attributes.KNOCKBACK_RESISTANCE, 0.75F);
    }

    @Override
    public void tick() {
        this.prevHideAnim = this.hideAnim;
        super.tick();

        if (!this.level().isClientSide()) {
            if (this.getLastHurtByMob() == null && this.isHiding()) {
                this.setHiding(false);
            }
        }

        // Handle animation stuff
        if (this.level().isClientSide()) {
            if ((this.isHiding() && this.hideAnim < 30) || (this.hideAnim > 30 && this.hideAnim < 60)) {
                this.hideAnim++;
            }
            if ((!this.isHiding() && this.hideAnim > 0 && this.hideAnim < 31)) {
                this.hideAnim = 31;
                this.prevHideAnim = 30;
            }
            if (!this.isHiding() && this.hideAnim >= 60 || this.isHiding() && this.hideAnim > 30) {
                this.hideAnim = 0;
                this.prevHideAnim = 0;
            }
        }
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_HIDING, false);
        this.getEntityData().define(HAS_SHELL, false);
    }

    public boolean hasShell() {
        return this.getEntityData().get(HAS_SHELL);
    }
    public void setShell(boolean shell) {
        this.getEntityData().set(HAS_SHELL, shell);
    }

    public boolean isHiding() {
        return this.getEntityData().get(IS_HIDING);
    }

    public void setHiding(boolean hiding) {
        this.getEntityData().set(IS_HIDING, hiding);
    }




    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.COW_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }


/**
     * Returns the volume for the sounds this mob makes.
     */

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isHiding() && !source.is(ReduxTags.DamageTypes.BYPASS_MYKAPOD)) {
            return false;
        } else {
            boolean b = super.hurt(source, amount);
            Entity entity1 = source.getEntity();
            if (entity1 != null) {
                if (entity1 instanceof LivingEntity) {
                    this.setHiding(true);
                }
            }
            return b;
        }
    }

    @Override
    public void setLastHurtByMob(@Nullable LivingEntity livingEntity) {
        super.setLastHurtByMob(livingEntity);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsHiding", this.isHiding());
        compound.putBoolean("HasShell", this.hasShell());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("IsHiding")) {
            this.setHiding(compound.getBoolean("IsHiding"));
        }
        if (compound.contains("HasShell")) {
            this.setShell(compound.getBoolean("HasShell"));
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {

        if (state.isMoving()) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
