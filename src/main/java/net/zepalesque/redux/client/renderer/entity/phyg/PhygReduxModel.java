package net.zepalesque.redux.client.renderer.entity.phyg;

import net.minecraft.client.model.QuadrupedModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PhygReduxModel<T extends Entity> extends QuadrupedModel<T> {
    protected final ModelPart right_ear;
    protected final ModelPart left_ear;
    protected final ModelPart tusks;
    protected final ModelPart spines;
    public PhygReduxModel(ModelPart root) {
        super(root, false, 4.0F, 4.0F, 2.0F, 2.0F, 24);
        this.right_ear = this.head.getChild("right_ear");
        this.left_ear = this.head.getChild("left_ear");
        this.tusks = this.head.getChild("tusks");
        this.spines = this.body.getChild("spines");
    }



    public static LayerDefinition createBodyLayer() {
	    var mesh = new MeshDefinition();
	    var part = mesh.getRoot();
	    
	    var body = part.addOrReplaceChild("body", CubeListBuilder.create().texOffs(28, 8).addBox(-5.0F, -10.0F, -7.0F, 10.0F, 16.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 2.0F, (float)Math.PI / 2F, 0.0F, 0.0F));

        body.addOrReplaceChild("spines", CubeListBuilder.create().texOffs(56, -4).addBox(0.0F, -23.0F, -7.0F, 0.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 13.0F, -2.0F, -((float)Math.PI), 0.0F, (float)Math.PI));
        part.addOrReplaceChild("right_hind_leg", CubeListBuilder.create(), PartPose.offset(-3.0F, 18.0F, 7.0F));
        part.addOrReplaceChild("right_front_leg", CubeListBuilder.create(), PartPose.offset(-3.0F, 18.0F, -5.0F));
        part.addOrReplaceChild("left_hind_leg", CubeListBuilder.create(), PartPose.offset(3.0F, 18.0F, 7.0F));
        part.addOrReplaceChild("left_front_leg", CubeListBuilder.create(), PartPose.offset(3.0F, 18.0F, -5.0F));
	    
	    var head = part.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -4.0F, -8.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 12.0F, -6.0F));
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(18, 23).addBox(0.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.5F, -2.0F, -5.0F, 0.0F, 0.0F, -0.6109F));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(18, 23).mirror().addBox(-1.0F, 0.0F, -2.0F, 1.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-3.5F, -2.0F, -5.0F, 0.0F, 0.0F, 0.6109F));
        head.addOrReplaceChild("tusks", CubeListBuilder.create().texOffs(0, 29).addBox(-3.0F, 1.0F, -9.0F, 6.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 32);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
	    var f1 = ageInTicks * 0.1F + limbSwing * 0.5F;
	    var f2 = 0.08F + limbSwingAmount * 0.4F;
        this.left_ear.zRot = -Mth.PI / 6F - Mth.cos(f1 * 1.2F) * f2;
        this.right_ear.zRot = Mth.PI / 6F + Mth.cos(f1) * f2;
        this.tusks.skipDraw = this.young;
        this.spines.skipDraw = this.young;
    }

}