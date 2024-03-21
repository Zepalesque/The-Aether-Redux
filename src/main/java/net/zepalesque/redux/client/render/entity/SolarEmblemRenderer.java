package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.item.EquipmentUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.PinModel;
import net.zepalesque.redux.item.ReduxItems;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

public class SolarEmblemRenderer implements ICurioRenderer {
    private final PinModel pin;
    ResourceLocation LOC = Redux.locate("textures/models/accessory/misc/solar_emblem_accessory.png");
    ResourceLocation DUAL_LOC = Redux.locate("textures/models/accessory/misc/solar_emblem_accessory_dual.png");

    public SolarEmblemRenderer() {
        this.pin = new PinModel(Minecraft.getInstance().getEntityModels().bakeLayer(ReduxModelLayers.PIN));
    }

    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followBodyRotations(slotContext.entity(), this.pin);
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(EquipmentUtil.getCurios(slotContext.entity(), ReduxItems.SOLAR_EMBLEM.get()).size() >= 2 ? DUAL_LOC : LOC), false, stack.isEnchanted());
        this.pin.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
