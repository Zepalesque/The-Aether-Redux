
package net.zepalesque.redux.entity.passive;

import com.aetherteam.aether.AetherTags;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolActions;
import net.zepalesque.redux.entity.ai.goal.MykapodLookGoal;
import net.zepalesque.redux.entity.ai.goal.MykapodStareGoal;
import net.zepalesque.redux.entity.ai.goal.MykapodWanderGoal;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Mykapod extends PathfinderMob implements GeoEntity {

    private AnimatableInstanceCache cache;

    public Mykapod(EntityType<? extends Mykapod> entityType, Level level) {
        super(entityType, level);
        this.cache = GeckoLibUtil.createInstanceCache(this);
    }
    private static final EntityDataAccessor<Float> HURT_ANGLE = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> HURT_ANGLE_X = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.FLOAT);;
    private static final EntityDataAccessor<Float> HURT_ANGLE_Z = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.FLOAT);;

    private static final EntityDataAccessor<Boolean> IS_HIDING = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.BOOLEAN);

    private static final EntityDataAccessor<Boolean> HAS_SHELL = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.BOOLEAN);

    public int hideTickCounter = 0;

    @OnlyIn(Dist.CLIENT)
    public State anim;



    private @OnlyIn(Dist.CLIENT) enum State {
        NONE, HIDE, UNHIDE;
    }


    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new MykapodWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new MykapodStareGoal(this, Player.class, 6.0F) );
        this.goalSelector.addGoal(7, new MykapodLookGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.1F).add(Attributes.KNOCKBACK_RESISTANCE, 0.75F);
    }

/*    @Override
    public void tick() {
        super.tick();
    }*/

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_HIDING, false);
        this.getEntityData().define(HAS_SHELL, true);
        this.getEntityData().define(HURT_ANGLE, 0.0F);
        this.getEntityData().define(HURT_ANGLE_X, 0.0F);
        this.getEntityData().define(HURT_ANGLE_Z, 0.0F);
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

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            if (this.isHiding()) {
                this.hideTickCounter++;
            } else {
                this.hideTickCounter = 0;
            }
            if (this.isHiding() && this.hideTickCounter > 140 && this.random.nextInt(140) < (this.hideTickCounter - 140)) {
                this.setHiding(false);
            }
        }
    }

    public void setHiding(boolean hiding) {
        this.getEntityData().set(IS_HIDING, hiding);
    }


    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (key == IS_HIDING && this.level().isClientSide()) {
            if (this.isHiding()) {
                this.anim = State.HIDE;
            } else {
                this.anim = State.UNHIDE;
            }
        }
    }


    public float getHurtAngleX() {
        return this.getEntityData().get(HURT_ANGLE_X);
    }

    public void setHurtAngleX(float hurtAngleX) {
        this.getEntityData().set(HURT_ANGLE_X, hurtAngleX);
    }

    public float getHurtAngleZ() {
        return this.getEntityData().get(HURT_ANGLE_Z);
    }

    public void setHurtAngleZ(float hurtAngleZ) {
        this.getEntityData().set(HURT_ANGLE_Z, hurtAngleZ);
    }

    public float getHurtAngle() {
        return this.getEntityData().get(HURT_ANGLE);
    }

    public void setHurtAngle(float hurtAngle) {
        this.getEntityData().set(HURT_ANGLE, hurtAngle);
    }



/*    protected SoundEvent getAmbientSound() {
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
    }*/


/**
     * Returns the volume for the sounds this mob makes.
     */

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isHiding() && !source.is(ReduxTags.DamageTypes.BYPASS_MYKAPOD) && !source.is(DamageTypes.GENERIC_KILL)) {
            Entity attacker = source.getEntity();
            if (this.random.nextFloat() > 0.5) {
                this.hideTickCounter = Math.max(this.hideTickCounter - 20, 0);
            }
            if (attacker != null) {
                if (attacker instanceof LivingEntity le) {
                    this.wobbleAttack(attacker);
                    if (le.getMainHandItem().canPerformAction(ToolActions.PICKAXE_DIG) || le.getMainHandItem().is(AetherTags.Items.SLIDER_DAMAGING_ITEMS)) {
                        return super.hurt(source, amount);
                    }

                }
            }
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

    private void wobbleAttack(Entity attacker) {
        double a = Math.abs(this.position().x() - attacker.position().x());

        double c = Math.abs(this.position().z() - attacker.position().z());
        if (a > c) {
            this.setHurtAngleZ(1.0F);
            this.setHurtAngleX(0.0F);
            if (this.position().x() > attacker.position().x()) {
                this.setHurtAngleZ(-1.0F);
            }
        } else {
            this.setHurtAngleX(1.0F);
            this.setHurtAngleZ(0.0F);
            if (this.position().z() > attacker.position().z()) {
                this.setHurtAngleX(-1.0F);
            }
        }
        this.setHurtAngle(0.7F - this.getHealth() / 875.0F);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsHiding", this.isHiding());
        compound.putBoolean("HasShell", this.hasShell());
        compound.putInt("HideCounter", this.hideTickCounter);
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
        if (compound.contains("HideCounter")) {
            this.hideTickCounter = compound.getInt("HideCounter");
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "controller", 3, this::predicate));
        registrar.add(new AnimationController<>(this, "hideController", 3, this::hiding));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {

        if (this.isHiding() || (this.anim == State.HIDE && !state.isCurrentAnimationStage("animations.mykapod.unhide"))) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.hidden", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        if (state.isMoving()) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.move", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;



    }
    private <T extends GeoAnimatable> PlayState hiding(AnimationState<T> state) {

        if (this.anim == State.HIDE) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.hide", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        } else if (this.anim == State.UNHIDE && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.unhide", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
