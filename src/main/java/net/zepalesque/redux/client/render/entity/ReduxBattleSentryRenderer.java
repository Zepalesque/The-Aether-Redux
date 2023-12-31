package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether_genesis.client.renderer.GenesisModelLayers;
import com.aetherteam.aether_genesis.entity.monster.BattleSentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry.battle.ClassicBattleSentryEyeLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry.battle.ClassicBattleSentryLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry.battle.ReduxBattleSentryEyeLayer;
import net.zepalesque.redux.client.render.entity.model.entity.bronze.ReduxBattleSentryModel;
import net.zepalesque.redux.config.ReduxConfig;

import javax.annotation.Nonnull;

public class ReduxBattleSentryRenderer extends MobRenderer<BattleSentry, EntityModel<BattleSentry>> {
	private static final ResourceLocation SENTRY_OFF = new ResourceLocation(Redux.MODID, "textures/entity/mobs/battle_sentry/battle_sentry_off.png");
	private static final ResourceLocation SENTRY_ON = new ResourceLocation(Redux.MODID, "textures/entity/mobs/battle_sentry/battle_sentry_on.png");

	public ReduxBattleSentryRenderer(EntityRendererProvider.Context context) {
		super(context, new ReduxBattleSentryModel<>(context.bakeLayer(ReduxModelLayers.BATTLE_SENTRY)), 0.3F);
		this.addLayer(new ReduxBattleSentryEyeLayer<>(this));
		this.addLayer(new ClassicBattleSentryLayer<>(this, new SlimeModel<>(context.bakeLayer(GenesisModelLayers.BATTLE_SENTRY))));
		this.addLayer(new ClassicBattleSentryEyeLayer<>(this, new SlimeModel<>(context.bakeLayer(GenesisModelLayers.BATTLE_SENTRY))));
	}

	@Override
	protected void scale(BattleSentry sentry, PoseStack poseStack, float partialTickTime) {
		if (!ReduxConfig.CLIENT.sentry_improvements.get()) {
			float f = 0.879F;
			poseStack.scale(f, f, f);
			float f1 = sentry.getSize() + 1.0F;
			float f2 = 0.0F;
			float f3 = 1.0F / (f2 + 1.0F);
			poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
		}
	}

	public boolean shouldBeOn(BattleSentry sentry)
	{
		if (sentry.hurtTime > 0)
		{
			return Mth.sin(sentry.hurtTime) >= 0;
		} else {
			return sentry.isAwake();
		}
	}

	@Override
	public void render(BattleSentry pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
		super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

		if (!ReduxConfig.CLIENT.sentry_improvements.get() && (!pEntity.isAwake() || pEntity.hurtMarked)) {
			pMatrixStack.pushPose();
			pMatrixStack.translate(0.0F, -0.75F, 0.0F);
			super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
			pMatrixStack.popPose();
		}
	}

	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(BattleSentry sentry) {
		return shouldBeOn(sentry) ? SENTRY_ON : SENTRY_OFF;
	}
}
