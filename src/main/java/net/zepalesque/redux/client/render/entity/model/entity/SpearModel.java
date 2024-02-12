package net.zepalesque.redux.client.render.entity.model.entity;// Made with Blockbench 4.9.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;

public class SpearModel extends Model {
	private final ModelPart root;
	public static final ResourceLocation TEXTURE = Redux.locate("textures/models/weapon/spear.png");

	public SpearModel(ModelPart root) {
		super(RenderType::entityCutoutNoCull);
		this.root = root;
	}

	public static LayerDefinition createLayer() {
		MeshDefinition mesh = new MeshDefinition();
		PartDefinition definition = mesh.getRoot();
		definition.addOrReplaceChild("spear", CubeListBuilder.create().texOffs(4, 0).addBox(-1.5F, -27.0F, -0.5F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 0).addBox(-0.5F, -28.0F, -0.5F, 1.0F, 31.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(7, 7).addBox(-0.5F, 1.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.183F))
				.texOffs(4, 3).addBox(-0.5F, -22.0F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.125F)), PartPose.offset(0.0F, 24.0F, 0.0F));
		return LayerDefinition.create(mesh, 16, 32);
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		this.root.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}