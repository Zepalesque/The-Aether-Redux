package net.zepalesque.redux.mixin.client.render.model;

import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepuffModel.class)
public class SheepuffModelMixin extends QuadrupedModelMixin<Sheepuff> {

    @Override
    public void setupAnim(Sheepuff entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        boolean skip = ReduxConfig.CLIENT.sheepuff_model_upgrade.get() || ReduxConfig.CLIENT.override_model_upgrades.get();
        this.leftFrontLeg.skipDraw = skip;
        this.leftHindLeg.skipDraw = skip;
        this.rightFrontLeg.skipDraw = skip;
        this.rightHindLeg.skipDraw = skip;
    }
}
