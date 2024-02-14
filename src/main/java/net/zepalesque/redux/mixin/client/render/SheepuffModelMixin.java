package net.zepalesque.redux.mixin.client.render;

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
public class SheepuffModelMixin extends QuadrupedModel<Sheepuff> {


    // dummy constructor
    protected SheepuffModelMixin(ModelPart root, boolean scaleHead, float babyYHeadOffset, float babyZHeadOffset, float babyHeadScale, float babyBodyScale, int bodyYOffset) {
        super(root, scaleHead, babyYHeadOffset, babyZHeadOffset, babyHeadScale, babyBodyScale, bodyYOffset);
    }

    @Inject(method = "prepareMobModel(Lcom/aetherteam/aether/entity/passive/Sheepuff;FFF)V", at = @At(value = "TAIL"), remap = false)
    public void prepareMobModel(Sheepuff moa, float limbSwing, float limbSwingAmount, float partialTicks, CallbackInfo ci) {
        this.leftFrontLeg.skipDraw = ReduxConfig.CLIENT.moa_model_upgrade.get();
        this.leftHindLeg.skipDraw = ReduxConfig.CLIENT.moa_model_upgrade.get();
        this.rightFrontLeg.skipDraw = ReduxConfig.CLIENT.moa_model_upgrade.get();
        this.rightHindLeg.skipDraw = ReduxConfig.CLIENT.moa_model_upgrade.get();
    }
}
