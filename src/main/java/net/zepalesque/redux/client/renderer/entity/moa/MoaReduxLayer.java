package net.zepalesque.redux.client.renderer.entity.moa;

import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.attachment.anim.MoaAnimAttachment;
import net.zepalesque.redux.util.LegacyAnimUtil;
import net.zepalesque.zenith.util.math.EasingUtil;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ConcurrentHashMap;

public class MoaReduxLayer extends RenderLayer<Moa, MoaModel> {

	protected final MoaRenderer parent;
	private final MoaReduxModel model;

	// concurrent because static final (memory safety !!!! :3)
	private static final ConcurrentHashMap<ResourceLocation, ResourceLocation> TRANSLATION_MAP = new ConcurrentHashMap<>();

	public MoaReduxLayer(MoaRenderer renderer, MoaReduxModel model) {
		super(renderer);
		this.model = model;
		this.parent = renderer;
	}

	@Override
	public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, @NotNull Moa moa, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		if (MoaUtils.useNewModel(moa)) {
			poseStack.pushPose();
			poseStack.scale(0.5F, 0.5F, 0.5F);
			poseStack.translate(0F, 1.5F, /*-0.125F*/ 0F);
			var model = this.model;
			model.neck.yRot = this.getParentModel().head.yRot * 0.333F;
			model.neck.xRot = this.getParentModel().head.xRot * 0.125F;
			model.head_part.yRot = this.getParentModel().head.yRot * 0.667F;
			model.head_part.xRot = this.getParentModel().head.xRot * 0.875F;

			model.jaw.xRot = this.getParentModel().jaw.xRot;
			
			
			var breathe = LegacyAnimUtil.breathe(moa, partialTicks);
			if (moa.hurtTime > 0 && moa.hurtTime - partialTicks > 0.0F) {
				var hit = moa.hurtDuration - moa.hurtTime;
				var hitSmooth = hit + partialTicks;
				final var baseRot =
					hitSmooth >= moa.hurtDuration * 0.25F + 0.0F
						? -Mth.cos(0.133333333F * (float) Math.PI * (hitSmooth + 5.0F)) + 1
						: -Mth.cos(0.4F * (float) Math.PI * hitSmooth);
				
				var rot = baseRot * ((float) Math.PI * 0.125F);
				model.neck_hurtanim.xRot = 0.6667F * rot;
				model.body_additions.xRot = 0.3333F * rot + breathe;
				model.head_hurtanim.xRot = -rot;
			} else {
				model.neck_hurtanim.xRot = breathe * 0.5F;
				model.head_hurtanim.xRot = -1.5F * breathe;
				model.neck_hurtanim.yRot = 0.0F;
				model.head_hurtanim.yRot =  0.0F;
				model.body_additions.xRot = breathe;
			}


			model.middle_feather.xRot = LegacyAnimUtil.breatheBase(moa,
				partialTicks,
				0.1F,
				0.1F,
				0.0F
			);
			model.left_feather.xRot = LegacyAnimUtil.breatheBase(moa,
				partialTicks,
				0.1F,
				0.1F,
				0.6667F
			);
			model.right_feather.xRot = LegacyAnimUtil.breatheBase(moa,
				partialTicks,
				0.1F,
				0.1F,
				0.3333F
			);

			model.wing_1.xRot = this.getParentModel().rightWing.xRot * 0.625F
				+ (float) Math.PI * 0.5F
				- (float) Math.PI * 0.08333F;
			model.z_rot_wing_1.zRot = this.getParentModel().rightWing.yRot * 0.875F
				+ (float) Math.PI * 0.08333F
				- breathe;
			model.wing_2.xRot = this.getParentModel().leftWing.xRot * 0.625F
				+ (float) Math.PI * 0.5F
				- (float) Math.PI * 0.08333F;
			model.z_rot_wing_2.zRot = this.getParentModel().leftWing.yRot * 0.875F
				- (float) Math.PI * 0.08333F
				+ breathe;

			model.feathers_3_wing1.xRot = (moa.isEntityOnGround() ? 0F : -45F * Mth.DEG_TO_RAD)
				- LegacyAnimUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.0F);
			model.feathers_2_wing1.xRot = (moa.isEntityOnGround() ? 0F : -30F * Mth.DEG_TO_RAD)
				- LegacyAnimUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.3333F);
			model.feathers_1_wing1.xRot = (moa.isEntityOnGround() ? 0F : -25F * Mth.DEG_TO_RAD)
				- LegacyAnimUtil.breatheBase(moa, partialTicks, 0.025F, 0.1F, 0.6667F);
			model.feathers_3_wing2.xRot = model.feathers_3_wing1.xRot;
			model.feathers_2_wing2.xRot = model.feathers_2_wing1.xRot;
			model.feathers_1_wing2.xRot = model.feathers_1_wing1.xRot;
			
			var swingCalc = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

			var attachment = MoaAnimAttachment.get(moa);
			var progress/* = moa.isEntityOnGround() ? 0 : 1;
			progress*/ = Mth.lerp(partialTicks, attachment.getPrevLegAnim(moa), attachment.getLegAnim(moa)) * 0.2F;
			
			model.leg1.skipDraw = true;
			model.leg2.skipDraw = true;
			this.getParentModel().rightLeg.xRot = EasingUtil.Sinusoidal.inOut(Mth.lerp(progress, swingCalc, 0.6F));
			this.getParentModel().leftLeg.xRot = EasingUtil.Sinusoidal.inOut(Mth.lerp(progress, -swingCalc, 0.6F));
			
//			if (Redux.protectCompat()) {
//				this.doHeadFeathersArmorStuff(moa, model);
//			}

			model.setupAnim(moa, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			if (Minecraft.getInstance().player == null || !moa.isInvisibleTo(Minecraft.getInstance().player)) {
				var loc = this.getTextureLocation(moa);
				var consumer = buffer.getBuffer(RenderType.entityCutoutNoCull(loc));
				model.renderToBuffer(poseStack, consumer, packedLight, LivingEntityRenderer.getOverlayCoords(moa, 0.0F));
			}
			poseStack.popPose();
		}
	}

//	private void doHeadFeathersArmorStuff(Moa moa, MoaReduxModel model) {
//		MoaArmor.get(moa).ifPresent((moaArmor) -> {
//			ItemStack itemStack = moaArmor.getArmor();
//			model.head_feather_top.skipDraw = itemStack != null && !itemStack.isEmpty();
//		});
//	}

	@NotNull
	@Override
	public ResourceLocation getTextureLocation(@NotNull Moa moa) {
		var key = this.parent.getTextureLocation(moa);
		
		return TRANSLATION_MAP.computeIfAbsent(
			key,
			loc -> ResourceLocation.fromNamespaceAndPath(
				key.getNamespace(),
				key.getPath().replace(".png", "_redux.png")
			)
		);
	}
}
