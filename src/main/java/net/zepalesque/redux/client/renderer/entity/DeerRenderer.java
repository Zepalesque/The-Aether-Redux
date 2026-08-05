package net.zepalesque.redux.client.renderer.entity;

import javax.annotation.ParametersAreNonnullByDefault;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.renderer.entity.layer.Antlers;
import net.zepalesque.redux.client.renderer.entity.model.DeerModel;
import net.zepalesque.redux.entity.Deer;

@ParametersAreNonnullByDefault
public class DeerRenderer extends MobRenderer<Deer, DeerModel<Deer>> {
	public DeerRenderer(EntityRendererProvider.Context context) {
		super(
			context,
			new DeerModel<>(context.bakeLayer(DeerModel.LAYER_LOCATION)),
			0.75F
		);
		this.addLayer(new Antlers(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Deer deer) {
		return Redux.loc("textures/entity/deer/deer.png");
	}

	@Override
	protected float getFlipDegrees(Deer deer) {
		return 180F;
	}
}
