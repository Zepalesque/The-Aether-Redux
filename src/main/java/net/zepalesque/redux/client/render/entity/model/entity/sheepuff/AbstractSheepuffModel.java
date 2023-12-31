package net.zepalesque.redux.client.render.entity.model.entity.sheepuff;

import com.aetherteam.aether.entity.passive.Sheepuff;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;

// TODO: just redo this whole thing
public abstract class AbstractSheepuffModel<T extends Sheepuff> extends EntityModel<T> {

    public final ModelPart hurtanim_base;
    public final ModelPart main_body;
    public final ModelPart leftFrontLeg;
    public final ModelPart rightFrontLeg;
    public final ModelPart rightHindLeg;
    public final ModelPart leftHindLeg;
    public final ModelPart head;
    public final ModelPart head_baserot;
    public final ModelPart tail;
    public final ModelPart kirrid_tail;
    public final ModelPart sheepuff_tail;

    public AbstractSheepuffModel(ModelPart root) {
        this.hurtanim_base = root.getChild("hurtanim_base");
        this.main_body = hurtanim_base.getChild("main_body");
        this.leftFrontLeg = main_body.getChild("leg1");
        this.rightFrontLeg = main_body.getChild("leg2");
        this.rightHindLeg = main_body.getChild("leg3");
        this.leftHindLeg = main_body.getChild("leg4");
        this.head = main_body.getChild("head");
        this.head_baserot = head.getChild("head_baserot");
        this.tail = main_body.getChild("tail");
        this.kirrid_tail = tail.getChild("kirrid_tail");
        this.sheepuff_tail = tail.getChild("sheepuff_tail");

    }

    public void sync(AbstractSheepuffModel model)
    {
        this.hurtanim_base.xRot = model.hurtanim_base.xRot;
        this.main_body.xRot = model.main_body.xRot;
        this.leftFrontLeg.xRot = model.leftFrontLeg.xRot;
        this.rightFrontLeg.xRot = model.rightFrontLeg.xRot;
        this.rightHindLeg.xRot = model.rightHindLeg.xRot;
        this.leftHindLeg.xRot = model.leftHindLeg.xRot;
        this.head.xRot = model.head.xRot;
        this.head.yRot = model.head.yRot;
        this.head_baserot.xRot = model.head_baserot.xRot;
        this.tail.zRot = model.tail.zRot;
        this.kirrid_tail.skipDraw = model.kirrid_tail.skipDraw;
        this.sheepuff_tail.skipDraw = model.sheepuff_tail.skipDraw;
    }

}
