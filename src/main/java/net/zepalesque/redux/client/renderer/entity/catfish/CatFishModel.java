package net.zepalesque.redux.client.renderer.entity.catfish;

import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public class CatFishModel<T extends Entity> extends HierarchicalModel<T> {
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;

	public CatFishModel(ModelPart root) {
		this.body = root.getChild("body");
		this.head = this.body.getChild("head");
		this.tail = this.head.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		var meshdefinition = new MeshDefinition();
		var partdefinition = meshdefinition.getRoot();
		var def = new CubeDeformation(0);

		var body = partdefinition.addOrReplaceChild(
			"body",
			CubeListBuilder.create(),
			PartPose.offset(2.5F, 24.0F, -9.0F)
		);

		var head = body.addOrReplaceChild(
			"head",
			CubeListBuilder.create()
				.texOffs(22, -5).addBox(-2.5F, -7.0F, 3.0F, 0.0F, 3.0F, 5.0F, def)
				.texOffs(0, 0).addBox(-5.5F, -4.0F, -1.0F, 6.0F, 4.0F, 10.0F, def)
				.texOffs(22, 3).addBox(-4.5F, -2.0F, -2.0F, 4.0F, 2.0F, 1.0F, def)
				.texOffs(0, 24).addBox(-9.5F, -7.0F, 2.0F, 14.0F, 7.0F, 0.0F, def),
			PartPose.offset(0.0F, 0.0F, 0.0F)
		);

		head.addOrReplaceChild(
			"tail",
			CubeListBuilder.create()
				.texOffs(0, 14).addBox(-2.0F, -1.5F, 0.0F, 4.0F, 3.0F, 7.0F, def)
				.texOffs(0, -5).addBox(0.0F, -3.5F, 5.0F, 0.0F, 7.0F, 5.0F, def),
			PartPose.offset(-2.5F, -1.5F, 9.0F)
		);

		head.addOrReplaceChild(
			"fin_l",
			CubeListBuilder.create()
				.texOffs(24, 6).addBox(0.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, def),
			PartPose.offsetAndRotation(0.5F, -1.0F, 4.0F, 0.0F, 0.0F, 0.5236F)
		);

		head.addOrReplaceChild(
			"fin_r",
			CubeListBuilder.create()
				.texOffs(20, 6).addBox(-2.0F, 0.0F, -1.0F, 2.0F, 0.0F, 2.0F, def),
			PartPose.offsetAndRotation(-5.5F, -1.0F, 4.0F, 0.0F, 0.0F, -0.5236F)
		);

		return LayerDefinition.create(meshdefinition, 32, 32);
	}

	@Override
	public void setupAnim(
		T entity,
		float swing,
		float swingAmount,
		float age,
		float headYaw,
		float headPitch
	) {
		var rotAmount = entity.isInWater() ? 0.5f : 1f;
		this.tail.yRot = -rotAmount * 0.45f * Mth.sin(0.6f * age);
	}

	@Override
	public ModelPart root() {
		return this.body;
	}
}
