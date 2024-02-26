package net.zepalesque.redux.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.projectile.InfusedVeridiumDart;

import javax.annotation.Nonnull;

public class InfusedVeridiumDartRenderer extends ArrowRenderer<InfusedVeridiumDart> {
    public static final ResourceLocation TEX = Redux.locate("textures/entity/projectile/dart/infused_veridium_dart.png");

    public InfusedVeridiumDartRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull InfusedVeridiumDart dart) {
        return TEX;
    }
}
