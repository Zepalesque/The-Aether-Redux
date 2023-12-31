package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.accessory.model.PendantModel;
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
import net.zepalesque.redux.capability.living.VampireAmulet;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.Optional;

public class VampireAmuletRenderer implements ICurioRenderer {
    private final PendantModel pendant;
    private final ResourceLocation INACTIVE = new ResourceLocation(Redux.MODID, "textures/models/accessory/pendant/vampire_amulet_inactive_accessory.png");
    private final ResourceLocation ACTIVE = new ResourceLocation(Redux.MODID, "textures/models/accessory/pendant/vampire_amulet_active_accessory.png");


    public VampireAmuletRenderer() {
        this.pendant = new PendantModel(Minecraft.getInstance().getEntityModels().bakeLayer(AetherModelLayers.PENDANT));
    }

    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource buffer, int packedLight, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ICurioRenderer.followBodyRotations(slotContext.entity(), this.pendant);
        LivingEntity entity = slotContext.entity();
        VertexConsumer consumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(getTexture(entity)), false, stack.isEnchanted());
        this.pendant.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
    }

    private ResourceLocation getTexture(LivingEntity entity) {
        Optional<Boolean> isActive = VampireAmulet.get(entity).map(VampireAmulet::canUseAbility);
        return isActive.isPresent() && isActive.get() ? ACTIVE : INACTIVE;
    }
}
