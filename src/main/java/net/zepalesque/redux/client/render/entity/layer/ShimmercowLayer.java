package net.zepalesque.redux.client.render.entity.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.entity.animal.MushroomCow;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.render.entity.model.entity.ShimmercowModel;
import net.zepalesque.redux.entity.passive.Shimmercow;

@OnlyIn(Dist.CLIENT)
public class ShimmercowLayer<T extends Shimmercow> extends RenderLayer<T, ShimmercowModel<T>> {
   private final BlockRenderDispatcher blockRenderer;

   public ShimmercowLayer(RenderLayerParent<T, ShimmercowModel<T>> renderer, BlockRenderDispatcher blockRenderer) {
      super(renderer);
      this.blockRenderer = blockRenderer;
   }

   public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
      if (!livingEntity.isBaby()) {
         Minecraft minecraft = Minecraft.getInstance();
         boolean flag = minecraft.shouldEntityAppearGlowing(livingEntity) && livingEntity.isInvisible();
         if (!livingEntity.isInvisible() || flag) {
            BlockState blockstate = ReduxBlocks.SHIMMERSTOOL.get().defaultBlockState();
            int i = LivingEntityRenderer.getOverlayCoords(livingEntity, 0.0F);
            BakedModel bakedmodel = this.blockRenderer.getBlockModel(blockstate);
            poseStack.pushPose();
            poseStack.translate(0.2F, -0F, 0.5F);
            poseStack.scale(-0.75F, -0.75F, 0.75F);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            this.renderMushroomBlock(poseStack, buffer, packedLight, flag, blockstate, i, bakedmodel);
            poseStack.popPose();
            poseStack.pushPose();
            poseStack.translate(0.2F, -0F, 0.5F);
            poseStack.translate(0.1F, 0.0F, -0.6F);
            poseStack.scale(-0.75F, -0.75F, 0.75F);
            poseStack.translate(-0.5F, -0.5F, -0.5F);
            this.renderMushroomBlock(poseStack, buffer, packedLight, flag, blockstate, i, bakedmodel);
            poseStack.popPose();
         }
      }
   }

   private void renderMushroomBlock(PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean outlineOnly, BlockState state, int packedOverlay, BakedModel model) {
      if (outlineOnly) {
         this.blockRenderer.getModelRenderer().renderModel(poseStack.last(), buffer.getBuffer(RenderType.outline(TextureAtlas.LOCATION_BLOCKS)), state, model, 0.0F, 0.0F, 0.0F, packedLight, packedOverlay);
      } else {
         this.blockRenderer.renderSingleBlock(state, poseStack, buffer, packedLight, packedOverlay);
      }

   }
}