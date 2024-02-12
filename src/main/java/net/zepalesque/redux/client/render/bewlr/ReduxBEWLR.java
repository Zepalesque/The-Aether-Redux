package net.zepalesque.redux.client.render.bewlr;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.TridentModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.client.render.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.SpearModel;
import net.zepalesque.redux.item.ReduxItems;

public class ReduxBEWLR extends BlockEntityWithoutLevelRenderer {

    private SpearModel model;
    public ReduxBEWLR(BlockEntityRenderDispatcher blockEntityRenderDispatcher, EntityModelSet entityModelSet) {
        super(blockEntityRenderDispatcher, entityModelSet);
        this.model = new SpearModel(entityModelSet.bakeLayer(ReduxModelLayers.SPEAR));
    }
    public ReduxBEWLR() {
        this(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels());
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        super.renderByItem(stack, displayContext, poseStack, buffer, packedLight, packedOverlay);
        if (stack.is(ReduxItems.SPEAR_OF_THE_BLIGHT.get())) {
            poseStack.pushPose();
            poseStack.scale(1.0F, -1.0F, -1.0F);
            VertexConsumer consumer = ItemRenderer.getFoilBufferDirect(buffer, this.model.renderType(TridentModel.TEXTURE), false, stack.hasFoil());
            this.model.renderToBuffer(poseStack, consumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }
}
