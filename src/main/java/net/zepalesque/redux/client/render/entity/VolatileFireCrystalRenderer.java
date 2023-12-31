package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.AbstractCrystalRenderer;
import com.aetherteam.aether.client.renderer.entity.model.CrystalModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.entity.projectile.VolatileFireCrystal;

public class VolatileFireCrystalRenderer extends AbstractCrystalRenderer<VolatileFireCrystal> {
    private static final ResourceLocation FIRE_CRYSTAL_TEXTURE = new ResourceLocation("aether", "textures/entity/projectile/crystals/fire_ball.png");

    public VolatileFireCrystalRenderer(EntityRendererProvider.Context context) {
        super(context, new CrystalModel(context.bakeLayer(AetherModelLayers.CLOUD_CRYSTAL)));
    }

    public ResourceLocation getTextureLocation(VolatileFireCrystal crystal) {
        return FIRE_CRYSTAL_TEXTURE;
    }
}
