package net.zepalesque.redux.entity.geo;

import com.aetherteam.aether.entity.AetherEntityTypes;
import net.minecraft.world.entity.EntityType;
import software.bernie.geckolib.animatable.GeoReplacedEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

public class GeoMoa implements GeoReplacedEntity {
    private final AnimatableInstanceCache cache;

    @Override
    public EntityType<?> getReplacingEntityType() {
        return AetherEntityTypes.MOA.get();
        this.cache = GeckoLibUtil.createInstanceCache(this);
        this.cache.addDataPoint();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar registrar) {
        registrar.add(new AnimationController<>(this, "wings", 3, this::wingAnim));
    }

    private PlayState wingAnim(AnimationState<GeoMoa> state) {
        this.cache.addDataPoint();
    }

    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

}
