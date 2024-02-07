package net.zepalesque.redux.client.render.entity;

import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.SpectralArrow;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.projectile.VeridiumArrow;

@OnlyIn(Dist.CLIENT)
public class VeridiumArrowRenderer extends ArrowRenderer<VeridiumArrow> {
   public static final ResourceLocation SPECTRAL_ARROW_LOCATION = Redux.locate("textures/entity/projectile/veridium_arrow.png");

   public VeridiumArrowRenderer(EntityRendererProvider.Context context) {
      super(context);
   }

   public ResourceLocation getTextureLocation(VeridiumArrow entity) {
      return SPECTRAL_ARROW_LOCATION;
   }
}