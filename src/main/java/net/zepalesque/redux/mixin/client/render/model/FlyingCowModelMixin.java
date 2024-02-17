package net.zepalesque.redux.mixin.client.render.model;

import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.passive.FlyingCow;
import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.model.CowModel;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CowModel.class)
public class FlyingCowModelMixin<T extends Entity> extends QuadrupedModelMixin<T> {

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        if (entity.getType() == AetherEntityTypes.FLYING_COW.get()) {
            this.head.skipDraw = ReduxConfig.CLIENT.flying_cow_model_upgrade.get() || ReduxConfig.CLIENT.override_model_upgrades.get();
        }
    }
}
