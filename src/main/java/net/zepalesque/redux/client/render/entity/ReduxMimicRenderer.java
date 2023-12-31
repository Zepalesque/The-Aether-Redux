package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.MimicModel;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.fml.ModList;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.capability.animation.mimic.MimicAnimation;
import net.zepalesque.redux.client.render.entity.layer.entity.bronze.ClassicMimicLayer;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.bronze.ReduxMimicModel;
import net.zepalesque.redux.util.math.EasingUtil;

import javax.annotation.Nonnull;
import java.util.Calendar;

public class ReduxMimicRenderer extends MobRenderer<Mimic, EntityModel<Mimic>> {
	private static final ResourceLocation REDUX_TEXTURE = new ResourceLocation(Redux.MODID, "textures/entity/mobs/mimic/normal.png");
	private static final ResourceLocation REDUX_XMAS_TEXTURE = new ResourceLocation(Redux.MODID, "textures/entity/mobs/mimic/christmas.png");
	private static final ResourceLocation REDUX_LOOTR_TEXTURE = new ResourceLocation(Redux.MODID, "textures/entity/mobs/mimic/lootr.png");

	private boolean isChristmas;


	public ReduxMimicRenderer(EntityRendererProvider.Context renderer) {
		super(renderer, new ReduxMimicModel(renderer.bakeLayer(ReduxModelLayers.MIMIC)), 1.0F);
		this.addLayer(new ClassicMimicLayer<>(this, new MimicModel(renderer.bakeLayer(AetherModelLayers.MIMIC))));
		Calendar calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) == Calendar.DECEMBER && calendar.get(Calendar.DAY_OF_MONTH) >= 24 && calendar.get(Calendar.DAY_OF_MONTH) <= 26) {
			this.isChristmas = true;
		}
	}

	private boolean shouldUseReduxModel()
	{
		return ReduxConfig.COMMON.better_mimics.get();
	}

	@Nonnull
	@Override
	public ResourceLocation getTextureLocation(@Nonnull Mimic mimic) {
		ResourceLocation reduxTexture = ModList.get().isLoaded("lootr") ? REDUX_LOOTR_TEXTURE : this.isChristmas ? REDUX_XMAS_TEXTURE : REDUX_TEXTURE;
		return reduxTexture;
	}

	@Override
	public void render(Mimic mimic, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
		super.render(mimic, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);

		if (ReduxConfig.COMMON.better_mimics.get())
		{
			MimicAnimation.get(mimic).ifPresent((anim) -> {

				float prevAnim = anim.getPrevOpenAnim() == 0 && anim.getOpenAnim() > 1 ? 10F : anim.getPrevOpenAnim();
				float progress = (10F - Mth.lerp(pPartialTicks, prevAnim, anim.getOpenAnim())) / 10F;
				if (anim.getOpenAnim() != 0 && anim.getPrevOpenAnim() != 0)
				{
					this.shadowRadius = (EasingUtil.Sinusoidal.inOut(progress) * 0.5F) + 0.25F;
				} else {
					this.shadowRadius = mimic.tickCount <= 1 ? 0.25F : 0.75F;
				}
			});
		} else {
			this.shadowRadius = 1.0F;
		}
	}
}
