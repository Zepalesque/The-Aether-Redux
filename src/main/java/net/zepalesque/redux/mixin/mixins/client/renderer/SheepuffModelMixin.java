package net.zepalesque.redux.mixin.mixins.client.renderer;

import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.passive.Sheepuff;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SheepuffModel.class)
public class SheepuffModelMixin extends QuadrupedModelMixin<Sheepuff> {

    @Override
    public void setupAnim(Sheepuff entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
        boolean skip = ReduxConfig.CLIENT.improved_sheepuffs.get();
        this.leftFrontLeg.skipDraw = skip;
        this.leftHindLeg.skipDraw = skip;
        this.rightFrontLeg.skipDraw = skip;
        this.rightHindLeg.skipDraw = skip;
    }
}
