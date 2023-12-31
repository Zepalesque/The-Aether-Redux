package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.SlimeModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry.ClassicSentryEyeLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry.ClassicSentryLayer;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.bronze.sentry.ReduxSentryEyeLayer;
import net.zepalesque.redux.client.render.entity.model.entity.bronze.ReduxSentryModel;

import javax.annotation.Nonnull;

// TODO: Move to mixin
public class ReduxSentryRenderer extends MobRenderer<Sentry, EntityModel<Sentry>> {
	private static final ResourceLocation SENTRY_OFF = new ResourceLocation(Redux.MODID, "textures/entity/mobs/sentry/sentry_off.png");
	private static final ResourceLocation SENTRY_ON = new ResourceLocation(Redux.MODID, "textures/entity/mobs/sentry/sentry_on.png");
	
	public ReduxSentryRenderer(EntityRendererProvider.Context context) {
		super(context, new ReduxSentryModel<>(context.bakeLayer(ReduxModelLayers.SENTRY)), 0.3F);
		this.addLayer(new ReduxSentryEyeLayer<>(this));
		this.addLayer(new ClassicSentryLayer<>(this, new SlimeModel<>(context.bakeLayer(AetherModelLayers.SENTRY))));
		this.addLayer(new ClassicSentryEyeLayer<>(this, new SlimeModel<>(context.bakeLayer(AetherModelLayers.SENTRY))));
	}

	@Override
	protected void scale(Sentry sentry, PoseStack poseStack, float partialTickTime) {
		if (!ReduxConfig.CLIENT.sentry_improvements.get()) {
			float f = 0.879F;
			poseStack.scale(f, f, f);
			float f1 = sentry.getSize() + 1.0F;
			float f2 = 0.0F;
			float f3 = 1.0F / (f2 + 1.0F);
			poseStack.scale(f3 * f1, 1.0F / f3 * f1, f3 * f1);
		}
	}

	public boolean shouldBeOn(Sentry sentry)
	{
		if (sentry.hurtTime > 0)
		{
			return Mth.sin(sentry.hurtTime) >= 0;
		} else {
			return sentry.isAwake();
		}
	}

	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(Sentry sentry) {
		return shouldBeOn(sentry) ? SENTRY_ON : SENTRY_OFF;
	}
}
