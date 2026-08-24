package net.zepalesque.redux.mixin.mixins.client.renderer;

import com.aetherteam.aether.client.renderer.entity.model.CockatriceModel;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.zepalesque.redux.attachment.anim.CockatriceAnimAttachment;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.zenith.util.math.EasingUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CockatriceModel.class)
public class CockatriceModelMixin extends BipedBirdModelMixin<Cockatrice> {
	
	@Unique private boolean useNewModel;
	
	@Inject(method = "setupAnim(Lcom/aetherteam/aether/entity/monster/Cockatrice;FFFFF)V", at = @At("TAIL"))
	public void setupAnim(Cockatrice cockatrice, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, CallbackInfo ci) {
		// TODO: why even store this?
		this.useNewModel = ReduxConfig.CLIENT.improved_cockatrices.get();
		if (this.useNewModel) this.jaw.xRot = 0.10F;
		
		var mc = Minecraft.getInstance();
		var ticker = cockatrice.level().tickRateManager();
		
		var partial = mc.getTimer().getGameTimeDeltaPartialTick(!ticker.isEntityFrozen(cockatrice));
		
		var swingCalc = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
		
		var att = CockatriceAnimAttachment.get(cockatrice);
		var progress = att.getLegAnim(cockatrice, partial);
		
		var sin = EasingUtil.Sinusoidal.inOut(progress);
		this.rightLeg.xRot = Mth.lerp(sin, swingCalc, 0.6F);
		this.leftLeg.xRot = Mth.lerp(sin, -swingCalc, 0.6F);
	}
	
	
	@Inject(method = "renderToBuffer", at = @At("HEAD"), cancellable = true)
	public void renderToBuffer(PoseStack poseStack, VertexConsumer consumer, int packedLight, int packedOverlay, int color, CallbackInfo ci) {
		if (!this.useNewModel) {
			this.head.render(poseStack, consumer, packedLight, packedOverlay);
			this.rightTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
			this.middleTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
			this.leftTailFeather.render(poseStack, consumer, packedLight, packedOverlay);
			this.body.render(poseStack, consumer, packedLight, packedOverlay);
		}
		this.rightLeg.render(poseStack, consumer, packedLight, packedOverlay);
		this.leftLeg.render(poseStack, consumer, packedLight, packedOverlay);
		ci.cancel();
	}
	
	
}
