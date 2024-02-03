
package net.zepalesque.redux.entity.passive;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.passive.AetherAnimal;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolActions;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.entity.ai.goal.*;
import net.zepalesque.redux.entity.dataserializer.ReduxDataSerializers;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class Mykapod extends AetherAnimal implements GeoEntity {

    private AnimatableInstanceCache cache;
    @OnlyIn(Dist.CLIENT)
    private int clientAnimTickCount = 0;

    public Mykapod(EntityType<? extends Mykapod> entityType, Level level) {
        super(entityType, level);
        this.cache = GeckoLibUtil.createInstanceCache(this);
        this.refreshDimensions();
    }
    private static final EntityDataAccessor<Float> HURT_ANGLE = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> HURT_ANGLE_X = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Float> HURT_ANGLE_Z = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.FLOAT);

    private static final EntityDataAccessor<HideStatus> IS_HIDING = SynchedEntityData.defineId(Mykapod.class, ReduxDataSerializers.HIDE_STATUS.get());

    private static final EntityDataAccessor<Boolean> HAS_SHELL = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.BOOLEAN);

    public int timeHiding = 0;
    public int hideCooldown = 0;
    public int timeSinceShed = 0;

    public int hitTicker = 0;
    @OnlyIn(Dist.CLIENT)
    public State anim;



    private @OnlyIn(Dist.CLIENT) enum State {
        NONE, HIDE, UNHIDE, UNHIDE_INTERRUPT;
    }



    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MykapodPanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new MykapodBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new MykapodTemptGoal(this, 1.25D, Ingredient.of(ReduxTags.Items.MYKAPOD_TEMPTATION_ITEMS), false));
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
        this.getEntityData().define(IS_HIDING, HideStatus.FALSE);
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
        return this.getEntityData().get(IS_HIDING).isHidden();
    }
    public HideStatus hideStatus() {
        return this.getEntityData().get(IS_HIDING);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide()) {
            this.clientAnimTickCount++;
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            if (this.hideCooldown > 0) {
                this.hideCooldown--;
            }
            if (this.isHiding()) {
                this.timeHiding++;
            } else {
                this.timeHiding = 0;
            }
            if (this.isHiding() && this.timeHiding > 140 && this.random.nextInt(140) < (this.timeHiding - 140)) {
                this.setHiding(HideStatus.FALSE);
            }
            if (!this.hasShell()) {
                this.timeSinceShed++;
                if (this.timeSinceShed > 3000 && this.random.nextInt(this.timeSinceShed - 3000) > 150) {
                    this.setShell(true);
                    // TODO: Regrow sound?
                }
            }
        }
    }

    public void setHiding(HideStatus hiding) {
        this.getEntityData().set(IS_HIDING, hiding);
    }

    public enum HideStatus implements StringRepresentable {
        TRUE(true, "true"), FALSE(false, "false"), FALSE_INTERRUPTED(false, "false_interrupted");

        private final boolean hidden;
        private final String id;
        HideStatus(boolean b, String id) {
            this.hidden = b;
            this.id = id;
        }

        public boolean isHidden() {
            return this.hidden;
        }

        @Override
        public String getSerializedName() {
            return this.id;
        }
    }


    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (key == IS_HIDING) {
            this.refreshDimensions();
            if (this.level().isClientSide() && this.clientAnimTickCount > 10) {
                if (this.isHiding()) {
                    this.anim = State.HIDE;
                } else {
                    if (this.hideStatus() == HideStatus.FALSE) {
                        this.anim = State.UNHIDE;

                    } else if (this.hideStatus() == HideStatus.FALSE) {
                        this.anim = State.UNHIDE_INTERRUPT;

                    }
                }
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
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.isHiding();
    }

    @Override
    public boolean hurt(@NotNull DamageSource source, float amount) {
        if (this.isHiding() && !source.is(ReduxTags.DamageTypes.BYPASS_MYKAPOD) && !source.is(DamageTypes.GENERIC_KILL)) {
            Entity attacker = source.getEntity();
            if (this.random.nextFloat() > 0.5) {
                this.timeHiding = Math.max(this.timeHiding - 20, 0);
            }
            if (attacker != null) {
                if (attacker instanceof LivingEntity le) {
                    this.wobbleAttack(attacker);
                    if (le.getMainHandItem().canPerformAction(ToolActions.PICKAXE_DIG) || le.getMainHandItem().is(AetherTags.Items.SLIDER_DAMAGING_ITEMS)) {
                        this.setHiding(HideStatus.FALSE_INTERRUPTED);
                        this.breakParticles();
                        if (!this.level().isClientSide() && !this.isBaby()) {
                            this.setShell(false);
                            // TODO: Sound for breaking the shell, should be a cracky/crunchy sound
                        }
                        return super.hurt(source, amount);
                    }
                }
            }
            this.hitTicker++;
            if (this.hasShell() && this.hitTicker > 20 && random.nextInt(this.hitTicker - 20) > 19) {
                this.breakParticles();
                if (!this.level().isClientSide() && !this.isBaby()) {
                    this.setShell(false);
                    this.spawnAtLocation(ReduxItems.MYKAPOD_SHELL_CHUNK.get(), 1);
                    // TODO: Sound for getting shell, perhaps a crunchy sort of sound. Can also add some lesser sounds for hitting it without getting the shell
                }
            }
            return false;
        } else {
            boolean b = super.hurt(source, amount);
            if (this.hideCooldown <= 0) {
                Entity entity1 = source.getEntity();
                if (entity1 != null) {
                    if (entity1 instanceof LivingEntity) {
                        this.setHiding(HideStatus.TRUE);
                        this.hideCooldown = 900;
                    }
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
    private void breakParticles() {
        for(int j = 0; j < (this.getHealth() <= 0 ? 2 : 4); j++)
        {
            double a = this.getBoundingBox().minX + (this.random.nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX));
            double b = this.getBoundingBox().minY + (this.random.nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY));
            double c = this.getBoundingBox().minZ + (this.random.nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ));

            this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ReduxItems.MYKAPOD_SHELL_CHUNK.get())), a, b, c, 0, 0, 0);
        }
    }



    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsHiding", this.isHiding());
        compound.putBoolean("HasShell", this.hasShell());
        compound.putInt("TimeHiding", this.timeHiding);
        compound.putInt("HideCooldown", this.hideCooldown);
        compound.putInt("TimeSinceShed", this.timeSinceShed);
    }


    public EntityDimensions getDimensions(Pose pose) {
        return super.getDimensions(pose).scale(1.0F, this.isHiding() ? 0.5F : 1.0F);
    }

    @Nullable
    public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob entity) {
        return ReduxEntityTypes.MYKAPOD.get().create(level);
    }

    public boolean isFood(ItemStack stack) {
        return stack.is(ReduxTags.Items.MYKAPOD_TEMPTATION_ITEMS) && !this.isHiding();
    }



    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("IsHiding")) {
            this.setHiding(compound.getBoolean("IsHiding") ? HideStatus.TRUE : HideStatus.FALSE);
        }
        if (compound.contains("HasShell")) {
            this.setShell(compound.getBoolean("HasShell"));
        }
        if (compound.contains("TimeHiding")) {
            this.timeHiding = compound.getInt("TimeHiding");
        }
        if (compound.contains("HideCooldown")) {
            this.hideCooldown = compound.getInt("HideCooldown");
        }
        if (compound.contains("TimeSinceShed")) {
            this.timeSinceShed = compound.getInt("TimeSinceShed");
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
            Vec3 velocity = this.getDeltaMovement();
            float avgVelocity = (float)(Math.abs(velocity.x) + Math.abs(velocity.z)) / 2f;
            state.getController().setAnimation(RawAnimation.begin().then(avgVelocity > 0.01F ? "animations.mykapod.runaway" : "animations.mykapod.move", Animation.LoopType.LOOP));
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
        } else if (this.anim == State.UNHIDE_INTERRUPT && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.force_unhide", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
