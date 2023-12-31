package net.zepalesque.redux.client.render.entity.model.entity.sheepuff;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;

public abstract class AbstractSheepuffWoolModel extends AbstractSheepuffModel {

    public final ModelPart wool;
    public final ModelPart wool_leftFrontLeg;
    public final ModelPart wool_rightFrontLeg;
    public final ModelPart wool_rightHindLeg;
    public final ModelPart wool_leftHindLeg;
    public final ModelPart head_wool;


    public final ModelPart kirrid_wool;
    public final ModelPart sheepuff_wool;
    public final ModelPart kirrid_wooltail;
    public final ModelPart sheepuff_wooltail;




    public AbstractSheepuffWoolModel(ModelPart root) {
        super(root);
        this.wool = main_body.getChild("wool");
        this.wool_leftFrontLeg = leftFrontLeg.getChild("wool_leg1");
        this.wool_rightFrontLeg = rightFrontLeg.getChild("wool_leg2");
        this.wool_rightHindLeg = rightHindLeg.getChild("wool_leg3");
        this.wool_leftHindLeg = leftHindLeg.getChild("wool_leg4");
        this.head_wool = head_baserot.getChild("head_wool");

        this.kirrid_wool = wool.getChild("kirrid_wool");
        this.sheepuff_wool = wool.getChild("sheepuff_wool");
        this.kirrid_wooltail = kirrid_tail.getChild("kirrid_wooltail");
        this.sheepuff_wooltail = sheepuff_tail.getChild("sheepuff_wooltail");

    }

    @Override
    public void setupAnim(Entity pEntity, float pLimbSwing, float pLimbSwingAmount, float pAgeInTicks, float pNetHeadYaw, float pHeadPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
        this.hurtanim_base.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);


    }
    @Override
    public void sync(AbstractSheepuffModel model)
    {
        super.sync(model);
        this.kirrid_wool.skipDraw = model.kirrid_tail.skipDraw;
        this.sheepuff_wool.skipDraw = model.sheepuff_tail.skipDraw;
    }
}
