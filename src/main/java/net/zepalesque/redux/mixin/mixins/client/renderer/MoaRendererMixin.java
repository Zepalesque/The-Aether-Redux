package net.zepalesque.redux.mixin.mixins.client.renderer;

import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.renderer.entity.moa.MoaUtils;
import net.zepalesque.redux.util.LegacyAnimUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoaRenderer.class)
public class MoaRendererMixin extends LivingEntityRendererMixin<Moa, MoaModel> {

    @Override
    public void redux$render(Moa moa, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        if (MoaUtils.useNewModel(moa)) {
	        var breathe = LegacyAnimUtil.breathe(moa, partialTicks);

            if (moa.hurtTime > 0 && moa.hurtTime - partialTicks > 0.0F) {
	            var hit = moa.hurtDuration - moa.hurtTime;
	            var hitSmooth = hit + partialTicks;
                final var baseRot = hitSmooth >= moa.hurtDuration * 0.25F + 0.0F
                    ? -Mth.cos(0.133333333F * (float) Math.PI * (hitSmooth + 5.0F)) + 1
                    : -Mth.cos(0.4F * (float) Math.PI * hitSmooth);
	            
	            var rot = baseRot * ((float) Math.PI * 0.125F);

                this.model.body.xRot = (float) (0.3333F * rot + Math.PI * 0.5F) + breathe;
            } else this.model.body.xRot = (float) (Math.PI * 0.5F) + breathe;
        } else this.model.body.xRot = (float) (Math.PI * 0.5F);


        super.redux$render(moa, entityYaw, partialTicks, poseStack, buffer, packedLight, ci);
    }
}
