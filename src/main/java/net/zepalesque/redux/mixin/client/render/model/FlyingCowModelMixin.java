package net.zepalesque.redux.mixin.client.render.model;

import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.passive.FlyingCow;
import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.model.CowModel;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CowModel.class)
public class FlyingCowModelMixin extends QuadrupedModelMixin<FlyingCow> {
    @Override
    public void setupAnim(FlyingCow entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        this.head.skipDraw = ReduxConfig.CLIENT.flying_cow_model_upgrade.get();
    }
}
