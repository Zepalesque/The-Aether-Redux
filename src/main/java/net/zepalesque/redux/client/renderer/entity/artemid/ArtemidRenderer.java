package net.zepalesque.redux.client.renderer.entity.artemid;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.Artemid;

@ParametersAreNonnullByDefault
public class ArtemidRenderer extends MobRenderer<Artemid, ArtemidModel<Artemid>> {
	public ArtemidRenderer(EntityRendererProvider.Context context) {
		super(
			context,
			new ArtemidModel<>(context.bakeLayer(ArtemidModel.LAYER_LOCATION)),
			0.75F
		);
		this.addLayer(new Antlers(this));
	}

	@Override
	public ResourceLocation getTextureLocation(Artemid artemid) {
		return Redux.loc("textures/entity/artemid/artemid.png");
	}

	@Override
	protected float getFlipDegrees(Artemid artemid) {
		return 180F;
	}
}
