package net.zepalesque.redux.client.render.entity.layer.entity.sheepuff;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.client.render.entity.layer.misc.ReduxRenderLayer;
import net.zepalesque.redux.client.render.entity.model.entity.sheepuff.AbstractSheepuffModel;
import net.zepalesque.redux.client.render.entity.model.entity.sheepuff.ConfiguredSheepuffModel;
import net.zepalesque.redux.client.render.entity.model.entity.sheepuff.ConfiguredSheepuffWoolModel;
import net.zepalesque.redux.client.render.entity.model.entity.sheepuff.PuffedConfiguredSheepuffWoolModel;
import net.zepalesque.redux.util.math.MathUtil;

import javax.annotation.Nonnull;


// TODO: Redo from scratch
public class ConfiguredSheepuffLayer extends ReduxRenderLayer<Sheepuff, SheepuffModel> {
    private static final ResourceLocation WOOL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/redux_sheepuff_wool.png");
    private static final ResourceLocation SHEEPUFF_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/redux_sheepuff.png");
    private static final ResourceLocation SHEARED_WOOL_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/redux_sheepuff_sheared_wool.png");

    private final ConfiguredSheepuffWoolModel wool;
    private final PuffedConfiguredSheepuffWoolModel puffed;
    private final ConfiguredSheepuffModel sheep;

    public ConfiguredSheepuffLayer(RenderLayerParent<Sheepuff, SheepuffModel> entityRenderer, ConfiguredSheepuffModel sheepModel, ConfiguredSheepuffWoolModel woolModel, PuffedConfiguredSheepuffWoolModel puffedModel) {
        super(entityRenderer);
        this.sheep = sheepModel;
        this.puffed = puffedModel;
        this.wool = woolModel;

    }

    public void render(@Nonnull PoseStack poseStack, @Nonnull MultiBufferSource buffer, int packedLight, Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (ReduxConfig.CLIENT.sheepuff_improvements.get()) {
            if (sheepuff.isBaby()) {
                poseStack.scale(0.5F, 0.5F, 0.5F);
                poseStack.translate(0F, 1.5F, 0F);
            }


            int hit = sheepuff.hurtDuration - sheepuff.hurtTime;
            float hitSmooth = hit + partialTicks;
            final float baseRot = sheepuff.hurtTime == 0 ? 0F : (hitSmooth >= (sheepuff.hurtDuration * 0.25F) + 0.0F ? (-Mth.cos(0.133333333F * ((float) Math.PI) * (hitSmooth + 5.0F)) + 1) : (-Mth.cos(0.4F * ((float) Math.PI) * hitSmooth)));
            float breathe = MathUtil.breathe(sheepuff, partialTicks);

            float rot = (float) ((baseRot * (((float) Math.PI) * 0.125F)) * -0.5);
            this.sheep.head_baserot.xRot = MathUtil.degToRad(-30F);

            this.sheep.hurtanim_base.xRot = (sheepuff.hurtTime == 0 ? 0 : rot) + breathe;
            this.sheep.main_body.xRot = -(sheepuff.hurtTime == 0 ? 0 : rot) - breathe;
            this.sheep.head.xRot = headPitch * ((float) Math.PI / 180F) + (baseRot * 0.25F)+ (breathe);
            this.sheep.head.yRot = netHeadYaw * ((float) Math.PI / 180F);
            this.sheep.rightHindLeg.xRot = (Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount) * (sheepuff.hurtTime == 0 ? 1F : Math.max((1F - (sheepuff.hurtTime - partialTicks)/ sheepuff.hurtDuration), 0F)) + (rot) + breathe;
            this.sheep.leftHindLeg.xRot = (Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount) * (sheepuff.hurtTime == 0 ? 1F : Math.max((1F - (sheepuff.hurtTime - partialTicks)/ sheepuff.hurtDuration), 0F)) + (rot) + breathe;
            this.sheep.leftFrontLeg.xRot = (Mth.cos(limbSwing * 0.6662F + (float) Math.PI) * 1.4F * limbSwingAmount) * (sheepuff.hurtTime == 0 ? 1F : Math.max((1F - (sheepuff.hurtTime - partialTicks)/ sheepuff.hurtDuration), 0F)) + (rot) + breathe;
            this.sheep.rightFrontLeg.xRot = (Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount) * (sheepuff.hurtTime == 0 ? 1F : Math.max((1F - (sheepuff.hurtTime - partialTicks)/ sheepuff.hurtDuration), 0F)) + (rot) + breathe;

            this.sheep.string1_side1.skipDraw = false;
            this.sheep.string1_side2.skipDraw = false;
            this.sheep.string2_side1.skipDraw = false;
            this.sheep.string2_side2.skipDraw = false;
            this.sheep.alpha_horns.skipDraw = false;
            this.sheep.ears.skipDraw = true;
            this.sheep.sheepuff_horn.skipDraw = false;
            this.sheep.kirrid_horn.skipDraw = true;
            this.sheep.kirrid_tail.skipDraw = true;
            this.sheep.sheepuff_tail.skipDraw = false;

            float hangSwing = Mth.cos(limbSwing * 0.6662F * 0.5F + (float) Math.PI) * 0.0625F * limbSwingAmount;
            float hangWave = MathUtil.breathe(sheepuff, partialTicks, 2F, 1F, (float) (Math.PI * 0.75));
            float wave1 = MathUtil.breathe(sheepuff, partialTicks, 1.5F, 2F, 0F);
            float wave2 = MathUtil.breathe(sheepuff, partialTicks, 1.5F, 2F, (float) (Math.PI * 0.25));
            this.sheep.string1_side1.yRot = wave1 - (this.sheep.head.yRot  * 0.5f);
            this.sheep.string1_side2.yRot = -wave1 - (this.sheep.head.yRot  * 0.5f);
            this.sheep.string2_side1.yRot = wave2 - (this.sheep.head.yRot  * 0.5f);
            this.sheep.string2_side2.yRot = -wave2 - (this.sheep.head.yRot  * 0.5f);
            this.sheep.string1_side1.xRot = Math.max( -(this.sheep.head.xRot * 0.5F), 0F);
            this.sheep.string1_side2.xRot = Math.min( -(this.sheep.head.xRot * 0.5F), 0F);
            this.sheep.string2_side1.xRot = Math.max( -(this.sheep.head.xRot * 0.5F), 0F);
            this.sheep.string2_side2.xRot = Math.min( -(this.sheep.head.xRot * 0.5F), 0F);
            this.sheep.hanging_object1.xRot = hangWave + hangSwing * 2 - (this.sheep.head.xRot);
            this.sheep.hanging_object2.xRot = -this.sheep.hanging_object1.xRot - (this.sheep.head.xRot * 2);

            this.wool.sync(this.sheep);
            this.puffed.sync(this.sheep);
            float f;
            float f1;
            float f2;



            if (sheepuff.hasCustomName() && sheepuff.getName().getContents().equals("jeb_")) {
                int i1 = 25;
                int i = sheepuff.tickCount / i1 + sheepuff.getId();
                int j = DyeColor.values().length;
                int k = i % j;
                int l = (i + 1) % j;
                float f3 = ((float) (sheepuff.tickCount % i1) + partialTicks) / (float) i1;
                float[] afloat1 = Sheepuff.getColorArray(DyeColor.byId(k));
                float[] afloat2 = Sheepuff.getColorArray(DyeColor.byId(l));
                f = afloat1[0] * (1.0F - f3) + afloat2[0] * f3;
                f1 = afloat1[1] * (1.0F - f3) + afloat2[1] * f3;
                f2 = afloat1[2] * (1.0F - f3) + afloat2[2] * f3;
            } else {
                float[] afloat = Sheepuff.getColorArray(sheepuff.getColor());
                f = afloat[0];
                f1 = afloat[1];
                f2 = afloat[2];
            }
            if (!sheepuff.isInvisibleTo(Minecraft.getInstance().player)) {
                ResourceLocation sheepLoc = getTextureLocation(sheepuff);
                VertexConsumer consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(sheepLoc));
                this.sheep.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F), 1.0F, 1.0F, 1.0F, 0.25F);
                renderColoredCutoutModel(this.sheep, getShearedWoolLocation(sheepuff), poseStack, buffer, packedLight, sheepuff, f, f1, f2);

            }
            if (!sheepuff.isSheared()) {
                AbstractSheepuffModel woolModel = sheepuff.getPuffed() ? this.puffed : this.wool;
                if (sheepuff.isInvisible()) {
                    Minecraft minecraft = Minecraft.getInstance();
                    boolean flag = minecraft.shouldEntityAppearGlowing(sheepuff);
                    if (flag) {
                        this.getParentModel().copyPropertiesTo(woolModel);
                        woolModel.prepareMobModel(sheepuff, limbSwing, limbSwingAmount, partialTicks);
                        woolModel.setupAnim(sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
                        VertexConsumer consumer = buffer.getBuffer(RenderType.outline(getWoolLocation(sheepuff)));
                        woolModel.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(sheepuff, 0.0F), 0.0F, 0.0F, 0.0F, 1.0F);
                    }
                } else {

                    coloredTranslucentModelCopyLayerRender(this.sheep, woolModel, getWoolLocation(sheepuff), poseStack, buffer, packedLight, sheepuff, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, partialTicks, f, f1, f2);

                }


            }

        }
    }

    protected ResourceLocation getWoolLocation(Sheepuff sheepuff) {
        return WOOL_TEXTURE;
    }
    @Override
    protected ResourceLocation getTextureLocation(Sheepuff sheepuff) {
        return SHEEPUFF_TEXTURE;
    }
    protected ResourceLocation getShearedWoolLocation(Sheepuff sheepuff) {
        return SHEARED_WOOL_TEXTURE;    }
}
