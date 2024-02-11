package net.zepalesque.redux.entity.monster;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.ai.goal.ContinuousMeleeAttackGoal;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether.entity.passive.Aerbunny;
import com.aetherteam.aether.entity.passive.AetherAnimal;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import net.zepalesque.redux.entity.passive.Mykapod;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class Blightbunny extends Monster implements GeoEntity {

    @OnlyIn(Dist.CLIENT)
    public State anim;


    private static final EntityDataAccessor<Integer> DATA_PUFFINESS_ID = SynchedEntityData.defineId(Blightbunny.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> DATA_FAST_FALLING_ID = SynchedEntityData.defineId(Blightbunny.class, EntityDataSerializers.BOOLEAN);
    private static final int MAXIMUM_PUFFS = 11;
    private int puffSubtract;

    public int getPuffSubtract() {
        return this.puffSubtract;
    }

    private final AnimatableInstanceCache cache;

    public Blightbunny(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
        this.cache = GeckoLibUtil.createInstanceCache(this);
        this.moveControl = new BlightbunnyMoveControl(this);
    }
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new ContinuousMeleeAttackGoal(this, 1.0, false));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(5, new FallingRandomStrollGoal(this, 1.0, 80));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this, Blightbunny.class));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal(this, Player.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.ATTACK_DAMAGE, 3.0).add(Attributes.MOVEMENT_SPEED, 0.5).add(Attributes.FOLLOW_RANGE, 16.0);
    }



    private @OnlyIn(Dist.CLIENT) enum State {
        ATTACKING, HURT, JUMPING, NONE
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.isFastFalling()) {
            this.handleFallSpeed();
        } else if (this.onGround()) {
            this.setFastFalling(false);
        }


        this.setPuffiness(this.getPuffiness() - this.puffSubtract);
        if (this.getPuffiness() > 0) {
            this.puffSubtract = 1;
        } else {
            this.puffSubtract = 0;
            this.setPuffiness(0);
        }

    }

    protected void midairJump() {
        Vec3 motion = this.getDeltaMovement();
        if (motion.y() < 0.0) {
            this.puff();
            this.level().broadcastEntityEvent(this, (byte)70);
        }

        this.setDeltaMovement(new Vec3(motion.x(), 0.25, motion.z()));
    }


    public void puff() {
        if (this.level() instanceof ServerLevel) {
            this.setPuffiness(11);
        }

    }

    private void handleFallSpeed() {
        AttributeInstance gravity = this.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            double fallSpeed = Math.max(gravity.getValue() * -1.25, -0.1);
            if (this.getDeltaMovement().y() < fallSpeed) {
                this.setDeltaMovement(this.getDeltaMovement().x(), fallSpeed, this.getDeltaMovement().z());
            }
        }

    }

    public static class BlightbunnyMoveControl extends MoveControl {
        private final Blightbunny aerbunny;

        public BlightbunnyMoveControl(Blightbunny aerbunny) {
            super(aerbunny);
            this.aerbunny = aerbunny;
        }

        public void tick() {
            super.tick();
            if (this.aerbunny.zza != 0.0F) {
                if (this.aerbunny.onGround()) {
                    this.aerbunny.getJumpControl().jump();
                } else {
                    int x = Mth.floor(this.aerbunny.getX());
                    int y = Mth.floor(this.aerbunny.getBoundingBox().minY);
                    int z = Mth.floor(this.aerbunny.getZ());
                    if (this.checkForSurfaces(this.aerbunny.level(), x, y, z) && !this.aerbunny.horizontalCollision) {
                        this.aerbunny.midairJump();
                    }
                }
            }

        }

        private boolean checkForSurfaces(Level level, int x, int y, int z) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, y, z);
            if (level.getBlockState(pos.setY(y - 1)).isAir()) {
                return false;
            } else {
                return level.getBlockState(pos.setY(y + 2)).isAir() && level.getBlockState(pos.setY(y + 1)).isAir();
            }
        }
    }

    public int getPuffiness() {
        return this.entityData.get(DATA_PUFFINESS_ID);
    }

    public void setPuffiness(int puffiness) {
        this.entityData.set(DATA_PUFFINESS_ID, puffiness);
    }


    public void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_PUFFINESS_ID, 0);
        this.getEntityData().define(DATA_FAST_FALLING_ID, false);
    }


    public boolean isFastFalling() {
        return this.entityData.get(DATA_FAST_FALLING_ID);
    }

    public void setFastFalling(boolean fastFalling) {
        this.entityData.set(DATA_FAST_FALLING_ID, fastFalling);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "idle_anims", 3, this::predicate));
        registrar.add(new AnimationController<>(this, "complex_anims", 3, this::complexAnims));
    }

    private <T extends GeoAnimatable> PlayState predicate(AnimationState<T> state) {


        if (state.isMoving()) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.blightbunny.walk", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }
        state.getController().setAnimation(RawAnimation.begin().then("animations.blightbunny.idle", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;
    }
    private <T extends GeoAnimatable> PlayState complexAnims(AnimationState<T> state) {

        State current = this.anim;

        if (current == State.ATTACKING) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.blightbunny.attack", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        } else if (current == State.HURT) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.blightbunny.hurt", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        } else if (current == State.JUMPING) {
            state.getController().setAnimation(RawAnimation.begin().then("animations.blightbunny.jump", Animation.LoopType.PLAY_ONCE));
            this.anim = State.NONE;
        }

        return PlayState.CONTINUE;
    }
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }



}
