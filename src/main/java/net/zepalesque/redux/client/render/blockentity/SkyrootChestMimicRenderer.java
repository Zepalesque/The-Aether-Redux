package net.zepalesque.redux.client.render.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.container.SkyrootChestBlock;
import net.zepalesque.redux.block.container.SkyrootChestMimicBlock;
import net.zepalesque.redux.client.render.ReduxAtlases;
import net.zepalesque.redux.client.render.ReduxModelLayers;

import javax.annotation.Nonnull;

public class SkyrootChestMimicRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
	private final ModelPart lid;
	private final ModelPart bottom;
	private final ModelPart lock;

	public SkyrootChestMimicRenderer(BlockEntityRendererProvider.Context context) {
		ModelPart root = context.bakeLayer(ReduxModelLayers.SKYROOT_CHEST_MIMIC);
		this.bottom = root.getChild("bottom");
		this.lid = root.getChild("lid");
		this.lock = root.getChild("lock");
	}

	@Override
	public void render(T blockEntity, float partialTicks, @Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, int packedOverlay) {
		BlockState blockState = blockEntity.getLevel() != null ? blockEntity.getBlockState() : ReduxBlocks.SKYROOT_CHEST_MIMIC.get().defaultBlockState().setValue(SkyrootChestBlock.FACING, Direction.SOUTH);
		if (blockState.getBlock() instanceof SkyrootChestMimicBlock) {
			poseStack.pushPose();
			float f = blockState.getValue(SkyrootChestBlock.FACING).toYRot();
			poseStack.translate(0.5D, 0.5D, 0.5D);
			poseStack.mulPose(Vector3f.YP.rotationDegrees(-f));
			poseStack.translate(-0.5D, -0.5D, -0.5D);
			Material material = this.getMaterial();
			VertexConsumer vertexconsumer = material.buffer(buffer, RenderType::entityCutout);
			this.render(poseStack, vertexconsumer, this.lid, this.lock, this.bottom, packedLight, packedOverlay);
			poseStack.popPose();
		}
	}

	private void render(PoseStack poseStack, VertexConsumer consumer, ModelPart chestLid, ModelPart chestLatch, ModelPart chestBottom, int packedLight, int packedOverlay) {
		chestLid.render(poseStack, consumer, packedLight, packedOverlay);
		chestLatch.render(poseStack, consumer, packedLight, packedOverlay);
		chestBottom.render(poseStack, consumer, packedLight, packedOverlay);
	}

	private static Material chooseMaterial(ChestType ct, Material pDoubleMaterial, Material pLeftMaterial, Material pRightMaterial) {
		if (ct == ChestType.LEFT) {
			return pLeftMaterial;
		} else if (ct == ChestType.RIGHT) {
			return pRightMaterial;
		} else if (ct == ChestType.SINGLE) {
			return pDoubleMaterial;
		}
		return pDoubleMaterial;
	}

	protected Material getMaterial() {
		return chooseMaterial(ChestType.SINGLE, ReduxAtlases.SKYROOT_CHEST_MATERIAL, ReduxAtlases.SKYROOT_CHEST_LEFT_MATERIAL, ReduxAtlases.SKYROOT_CHEST_RIGHT_MATERIAL);
	}
}