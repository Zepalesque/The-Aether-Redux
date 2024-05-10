package net.zepalesque.redux.entity.geo;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.Moa;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GeoMoa implements GeoReplacedEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public EntityType<?> getReplacingEntityType() {
        return AetherEntityTypes.MOA.get();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "wings", 5, this::wingAnim));
        registrar.add(new AnimationController<>(this, "legs", 5, this::legAnim));
        registrar.add(new AnimationController<>(this, "legs", 10, this::bodyAnim));
//        registrar.add(new AnimationController<>(this, "hurt", 10, this::hurtAnim));
    }

    private static final String WINGS_FLY_START = "animation.moa.wings.fly_start";
    private static final String WINGS_FLY = "animation.moa.wings.fly";
    private static final String WINGS_GLIDE = "animation.moa.wings.glide";
    private static final String WINGS_IDLE = "animation.moa.wings.idle";

    private static final String BODY_WALK = "animation.moa.body.walk";
    private static final String BODY_FLY = "animation.moa.body.fly";
    private static final String BODY_GLIDE = "animation.moa.body.glide";
    private static final String BODY_IDLE = "animation.moa.body.idle";

    private static final String BODY_HURT = "animation.moa.body.hurt";

    private static final String LEGS_WALK = "animation.moa.legs.walk";
    private static final String LEGS_FLY = "animation.moa.legs.fly";

    private PlayState wingAnim(AnimationState<GeoMoa> state) {
        Moa moa = (Moa) state.getData(DataTickets.ENTITY);

        if (!moa.isEntityOnGround()) {
            state.getController().setAnimation(RawAnimation.begin().thenPlayXTimes(WINGS_FLY_START, 1)
                    .then(state.isMoving() ? WINGS_GLIDE : WINGS_FLY, Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        } else {

            state.getController().setAnimation(RawAnimation.begin().thenPlay(WINGS_IDLE));
            return PlayState.CONTINUE;
        }
    }
    private PlayState legAnim(AnimationState<GeoMoa> state) {
        Moa moa = (Moa) state.getData(DataTickets.ENTITY);

        state.getController().setAnimation(RawAnimation.begin().thenLoop(moa.isEntityOnGround() ? LEGS_WALK : LEGS_FLY));
        return PlayState.CONTINUE;
    }

    private PlayState bodyAnim(AnimationState<GeoMoa> state) {
        Moa moa = (Moa) state.getData(DataTickets.ENTITY);

        if (!moa.isEntityOnGround()) {
            state.getController().setAnimation(RawAnimation.begin().thenLoop(state.isMoving() ? BODY_GLIDE : BODY_FLY));
            return PlayState.CONTINUE;
        } else {

            state.getController().setAnimation(RawAnimation.begin().thenLoop(state.isMoving() ? BODY_WALK : BODY_IDLE));
            return PlayState.CONTINUE;
        }
    }
/*
    private PlayState hurtAnim(AnimationState<GeoMoa> state) {
        Moa moa = (Moa) state.getData(DataTickets.ENTITY);

//        if (moa.animateHurt();) {
//            state.getController().setAnimation(RawAnimation.begin().thenPlay(BODY_HURT));
//        }
        return PlayState.CONTINUE;
    }*/


    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
