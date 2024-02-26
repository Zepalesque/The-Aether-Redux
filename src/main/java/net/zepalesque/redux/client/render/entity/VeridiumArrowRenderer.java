package net.zepalesque.redux.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.projectile.VeridiumDart;

@OnlyIn(Dist.CLIENT)
public class VeridiumArrowRenderer extends ArrowRenderer<VeridiumDart> {
   public static final ResourceLocation TEX = Redux.locate("textures/entity/projectile/dart/veridium_dart.png");

   public VeridiumArrowRenderer(EntityRendererProvider.Context context) {
      super(context);
   }

   public ResourceLocation getTextureLocation(VeridiumDart entity) {
      return TEX;
   }
}