package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether.client.renderer.entity.AerbunnyRenderer;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ChickenModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ChickenRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.item.DyeColor;
import net.zepalesque.redux.Redux;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;

@Mixin(ChickenRenderer.class)
public class ChickenRendererMixin extends MobRendererMixin<Chicken, ChickenModel<Chicken>> {

    @Override
    public void renderFinal(EntityModel<Chicken> instance, PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha, Operation<Void> original, Chicken entity, float entityYaw, float partialTicks, PoseStack poseStack1, MultiBufferSource buffer, int packedLight1) {
        if (entity.hasCustomName() && "Quail".equals(entity.getName().getString())) {
            int i = entity.tickCount / 25 + entity.getId();
            int j = DyeColor.values().length;
            int k = i % j;
            int l = (i + 1) % j;
            float f3 = ((float)(entity.tickCount % 25) + partialTicks) / 25.0F;
            float[] afloat1 = Sheep.getColorArray(DyeColor.byId(k));
            float[] afloat2 = Sheep.getColorArray(DyeColor.byId(l));
            float f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
            float f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
            float f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            super.renderFinal(instance, poseStack, vertexConsumer, packedLight, packedOverlay, f, f1, f2, alpha, original, entity, entityYaw, partialTicks, poseStack1, buffer, packedLight1);
        } else {
            super.renderFinal(instance, poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha, original, entity, entityYaw, partialTicks, poseStack1, buffer, packedLight1);
        }
    }
}
