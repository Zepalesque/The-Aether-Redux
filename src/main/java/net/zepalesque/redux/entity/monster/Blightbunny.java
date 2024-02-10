package net.zepalesque.redux.entity.monster;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.ai.goal.ContinuousMeleeAttackGoal;
import com.aetherteam.aether.entity.ai.goal.FallingRandomStrollGoal;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.aetherteam.aether.entity.passive.Aerbunny;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
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

    // Based on Aerbunny movement control, but has no mid-air jumps
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
                    this.aerbunny.anim = State.JUMPING;
                }
            }
        }
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
        state.getController().setAnimation(RawAnimation.begin().then("animations.mykapod.idle", Animation.LoopType.LOOP));
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
