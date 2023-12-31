package net.zepalesque.redux.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.projectile.SpectralDart;

import javax.annotation.Nonnull;

public class SpectralDartRenderer extends ArrowRenderer<SpectralDart> {
    public static final ResourceLocation SPECTRAL_DART_TEXTURE = new ResourceLocation(Redux.MODID, "textures/entity/projectile/dart/spectral_dart.png");

    public SpectralDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull SpectralDart dart) {
        return SPECTRAL_DART_TEXTURE;
    }
}
