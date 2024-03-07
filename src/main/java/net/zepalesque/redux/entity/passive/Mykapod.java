
package net.zepalesque.redux.entity.passive;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.passive.AetherAnimal;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolActions;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
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
import software.bernie.geckolib.core.keyframe.event.data.CustomInstructionKeyframeData;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class Mykapod extends AetherAnimal implements GeoEntity {

    private final AnimatableInstanceCache cache;
/*    @OnlyIn(Dist.CLIENT)*/
    private int clientAnimTickCount = 0;
    private int sheddingTicker = 0;

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

    public int hideCounter = 0;
    public int hideCooldown = 0;
    public int timeSinceShed = 0;

    @OnlyIn(Dist.CLIENT)
    public State anim;



    private @OnlyIn(Dist.CLIENT) enum State {
        NONE, FEAR, HIDE, UNHIDE, INTERRUPT, SHED;
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MykapodPanicGoal(this, 1.5D));
        this.goalSelector.addGoal(2, new MykapodBreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new MykapodTemptGoal(this, 1.25D, Ingredient.of(ReduxTags.Items.MYKAPOD_FOLLOW_ITEMS), false));
//        this.goalSelector.addGoal(4, new MykapodHideFromSprintingPlayerGoal(this));
        this.goalSelector.addGoal(5, new MykapodWanderGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new MykapodStareGoal(this, Player.class, 6.0F) );
        this.goalSelector.addGoal(7, new MykapodLookGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, 0.1F).add(Attributes.KNOCKBACK_RESISTANCE, 0.75F);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(IS_HIDING, HideStatus.OUT);
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

            if (this.sheddingTicker > 0) {
                this.sheddingTicker--;
            }
            if (this.sheddingTicker == 10) {
                this.shedShell();
            }
            if (this.sheddingTicker == 1) {
                this.setHiding(HideStatus.OUT);
            }
            if (this.sheddingTicker == 25) {
                this.shedAnim();
            }
            if (this.isHiding() && this.hideCounter > 1) {
                this.hideCounter--;
            } else {
                this.hideCounter = 0;
            }
            if (this.isHiding() && this.hideCounter <= 0) {
                this.setHiding(HideStatus.OUT);
            }
            if (!this.hasShell()) {
                this.timeSinceShed++;
                if (this.timeSinceShed > 3000 && this.random.nextInt(this.timeSinceShed - 3000) > 150) {
                    this.setShell(true);
                    this.timeSinceShed = 0;
                }
            }
        }
    }

    public void setHiding(HideStatus hiding) {
        this.getEntityData().set(IS_HIDING, hiding);
    }

    public enum HideStatus implements StringRepresentable {
        SCARED(true, "scared"), HIDING(true, "hiding"), OUT(false, "out"), INTERRUPTED(false, "interrupted");

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
            if (this.level().isClientSide() && this.clientAnimTickCount > 1) {
                if (this.hideStatus() == HideStatus.OUT) {
                    this.anim = State.UNHIDE;
                } else if (this.hideStatus() == HideStatus.INTERRUPTED) {
                    this.anim = State.INTERRUPT;
                } else if (this.hideStatus() == HideStatus.SCARED) {
                    this.anim = State.FEAR;
                } else if (this.hideStatus() == HideStatus.HIDING) {
                    this.anim = State.HIDE;
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

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ReduxSoundEvents.MYKAPOD_HURT.get();
    }

    protected SoundEvent getDeathSound() {
        return ReduxSoundEvents.MYKAPOD_DEATH.get();
    }
    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(ReduxSoundEvents.MYKAPOD_MOVE.get(), 0.15F, 1.0F);
    }


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
        Entity entitySource = source.getEntity();
        if (entitySource instanceof LivingEntity attacker) {


            if (source.is(ReduxTags.DamageTypes.BYPASS_MYKAPOD)) {
                if (this.isHiding()) {
                    this.setHiding(HideStatus.INTERRUPTED);
                    this.hideCounter = Math.max(this.hideCounter - 5, 0);
                    return super.hurt(source, amount);
                }
            } else if (this.isHiding()) {
                if (canAttackShell(attacker.getMainHandItem())) {
                    if (!this.breakShell(attacker)) {
                        this.crackShell();
                    }
                    this.setHiding(HideStatus.INTERRUPTED);
                    return super.hurt(source, amount);
                } else {
                    this.hideCounter = Math.max(this.hideCounter - 5, 0);
                    this.wobbleAttack(entitySource);
                    return false;
                }
            } else {
                if (!canAttackShell(attacker.getMainHandItem())) {
                    if (this.hideCooldown <= 0) {
                        this.setHiding(HideStatus.SCARED);
                        this.getNavigation().stop();
                        this.hideCooldown = 900;
                        this.hideCounter = 240 + this.random.nextInt(120);
                    }
                } else {
                    if (!this.breakShell(attacker)) {
                        this.crackShell();
                    }
                }
                return super.hurt(source, amount);
            }
        }
        return super.hurt(source, amount);
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
    private void breakParticles(int count) {
        if (!this.level().isClientSide() && this.level() instanceof ServerLevel sl) {
            for (int j = 0; j < count; j++) {
                double a = this.getBoundingBox().minX + (this.random.nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX));
                double b = this.getBoundingBox().minY + (this.random.nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY));
                double c = this.getBoundingBox().minZ + (this.random.nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ));
                sl.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(ReduxItems.MYKAPOD_SHELL_CHUNK.get())), a, b, c, 1, 0, 0, 0, 0);
            }
        }
    }

    protected boolean canAttackShell(ItemStack stack) {
        return stack.canPerformAction(ToolActions.PICKAXE_DIG) || stack.is(AetherTags.Items.SLIDER_DAMAGING_ITEMS);
    }




    public boolean shedShell() {
        if (!this.level().isClientSide() && this.hasShell() && !this.isBaby()) {
            this.breakParticles(10);
            this.setShell(false);
            this.spawnAtLocation(ReduxItems.MYKAPOD_SHELL_CHUNK.get(), 1);
            this.level().playSound(null, this.position().x, this.position().y, this.position().z, ReduxSoundEvents.MYKAPOD_SHELL_SHED.get(), SoundSource.NEUTRAL, 1, 0.8F + (this.random.nextFloat() * 0.4F));
            return true;
        }
        return false;
    }
    public void shedAnim() {
        if (!this.level().isClientSide()) {
            this.getNavigation().stop();
            this.level().broadcastEntityEvent(this, (byte) 17);
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        super.handleEntityEvent(id);
        if (id == 17 && this.level().isClientSide()) {
            this.anim = State.SHED;
        } else if (id == 16 && this.level().isClientSide()) {
            for(int i = 0; i < 7; ++i) {
                double d0 = this.random.nextGaussian() * 0.02D;
                double d1 = this.random.nextGaussian() * 0.02D;
                double d2 = this.random.nextGaussian() * 0.02D;
                this.level().addParticle(ParticleTypes.HAPPY_VILLAGER, this.getRandomX(1.0D), this.getRandomY() + 0.5D, this.getRandomZ(1.0D), d0, d1, d2);
            }
        }
    }

    @Override
    public boolean shouldPlayAnimsWhileGamePaused() {
        return true;
    }

    public boolean breakShell(LivingEntity entity) {
        if (!this.level().isClientSide() && this.hasShell() && !this.isBaby() && (entity instanceof Player p ? this.random.nextFloat() < p.getAttackStrengthScale(0.0F) : this.random.nextBoolean())) {
            this.breakParticles(15);
            this.setShell(false);
            this.level().playSound(null, this.position().x, this.position().y, this.position().z, ReduxSoundEvents.MYKAPOD_SHELL_BREAK.get(), SoundSource.NEUTRAL, 1, 0.8F + (this.random.nextFloat() * 0.4F));
            return true;
        }
        return false;
    }
    public void crackShell() {
        if (!this.level().isClientSide() && this.hasShell() && !this.isBaby()) {
            this.breakParticles(5);
            this.level().playSound(null, this.position().x, this.position().y, this.position().z, ReduxSoundEvents.MYKAPOD_SHELL_CRACK.get(), SoundSource.NEUTRAL, 1, 0.8F + (this.random.nextFloat() * 0.4F));
        }
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("IsHiding", this.isHiding());
        compound.putBoolean("HasShell", this.hasShell());
        compound.putInt("HideCounter", this.hideCounter);
        compound.putInt("HideCooldown", this.hideCooldown);
        compound.putInt("TimeSinceShed", this.timeSinceShed);
        compound.putInt("SheddingTicker", this.sheddingTicker);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("IsHiding")) {
            this.setHiding(compound.getBoolean("IsHiding") ? HideStatus.SCARED : HideStatus.OUT);
        }
        if (compound.contains("HasShell")) {
            this.setShell(compound.getBoolean("HasShell"));
        }
        if (compound.contains("HideCounter")) {
            this.hideCounter = compound.getInt("HideCounter");
        }
        if (compound.contains("HideCooldown")) {
            this.hideCooldown = compound.getInt("HideCooldown");
        }
        if (compound.contains("TimeSinceShed")) {
            this.timeSinceShed = compound.getInt("TimeSinceShed");
        }
        if (compound.contains("SheddingTicker")) {
            this.sheddingTicker = compound.getInt("SheddingTicker");
        }
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

    public boolean canUseToShed(ItemStack stack) {
        return stack.is(ReduxTags.Items.MYKAPOD_SHED_FOOD) && !this.isHiding();
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.canUseToShed(itemstack)) {
            if (!this.level().isClientSide && !this.isBaby() && this.hasShell()) {
                this.usePlayerItem(player, hand, itemstack);
                this.setHiding(HideStatus.HIDING);
                this.hideCounter = 50;
                this.sheddingTicker = 50;
                this.level().broadcastEntityEvent(this, (byte) 16);
                return InteractionResult.SUCCESS;
            }
        }
        return super.mobInteract(player, hand);
    }

    protected void landAnim(CustomInstructionKeyframeData keyframeData) {
        if (keyframeData.getInstructions().equals("land")) {
            BlockPos blockpos = this.getOnPosLegacy();
            BlockState blockstate = this.level().getBlockState(blockpos);
            if (!blockstate.addRunningEffects(this.level(), blockpos, this)) {
                if (blockstate.getRenderShape() != RenderShape.INVISIBLE) {
                    Vec3 vec3 = this.getDeltaMovement();
                    BlockPos blockpos1 = this.blockPosition();
                    double d0 = this.getX();
                    double d1 = this.getZ();
                    if (blockpos1.getX() != blockpos.getX()) {
                        d0 = Mth.clamp(d0, blockpos.getX(), (double) blockpos.getX() + 1.0D);
                    }

                    if (blockpos1.getZ() != blockpos.getZ()) {
                        d1 = Mth.clamp(d1, blockpos.getZ(), (double) blockpos.getZ() + 1.0D);
                    }

                    for (int i = 0; i < 10; i++) {
                        this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, blockstate).setPos(blockpos), d0, this.getY() + 0.1D, d1, vec3.x * -4.0D, 1.5D, vec3.z * -4.0D);
                    }
                    SoundType soundtype = blockstate.getSoundType(this.level(), blockpos, this);
                    this.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
                }
            }
        }
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "idle_anims", 3, this::predicate));
        registrar.add(new AnimationController<>(this, "other_anims", 3, this::hiding).setCustomInstructionKeyframeHandler(event -> {
                this.landAnim(event.getKeyframeData());
        }));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {

        if (this.isHiding()) {
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

        State current = this.anim;
        if (current == State.FEAR) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.scared", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        } else if (current == State.HIDE) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.hide", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        } else if (current == State.SHED) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.shed", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        } else if (current == State.UNHIDE && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.unhide", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        } else if (current == State.INTERRUPT && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
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
