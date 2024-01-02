package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.SentryRenderer;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.util.math.MathUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SentryRenderer.class)
public class SentryRendererMixin extends MobRendererMixin<Sentry, SlimeModel<Sentry>> {

    @Override
    public void renderMob(Sentry moa, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
        this.model.root().skipDraw = ReduxConfig.CLIENT.sentry_improvements.get();
        super.renderMob(moa, entityYaw, partialTicks, poseStack, buffer, packedLight, ci);
    }

    @Inject(at = @At("HEAD"), method = "scale(Lcom/aetherteam/aether/entity/monster/dungeon/Sentry;Lcom/mojang/blaze3d/vertex/PoseStack;F)V", cancellable = true)
    public void renderMob(Sentry sentry, PoseStack poseStack, float partialTicks, CallbackInfo ci) {
        if (ReduxConfig.CLIENT.sentry_improvements.get()) {
            ci.cancel();
        }
    }

}
