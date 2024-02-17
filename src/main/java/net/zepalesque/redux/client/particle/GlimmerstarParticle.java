package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GlimmerstarParticle extends TextureSheetParticle {
   static final RandomSource RANDOM = RandomSource.create();
   private final SpriteSet sprites;

   GlimmerstarParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed, SpriteSet sprites) {
      super(level, x, y, z, xSpeed, ySpeed, zSpeed);
      this.friction = 0.96F;
      this.speedUpWhenYMotionIsBlocked = true;
      this.sprites = sprites;
      this.quadSize *= 0.75F;
      this.hasPhysics = false;
      this.setSpriteFromAge(sprites);
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   public int getLightColor(float partialTick) {
      float f = ((float)this.age + partialTick) / (float)this.lifetime;
      f = Mth.clamp(f, 0.0F, 1.0F);
      int i = super.getLightColor(partialTick);
      int j = i & 255;
      int k = i >> 16 & 255;
      j += (int)(f * 15.0F * 16.0F);
      if (j > 240) {
         j = 240;
      }

      return j | k << 16;
   }

   public void tick() {
      super.tick();
      this.setSpriteFromAge(this.sprites);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet sprites) {
         this.sprite = sprites;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         GlimmerstarParticle glimmerstar = new GlimmerstarParticle(level, x, y, z, 0.5D - GlimmerstarParticle.RANDOM.nextDouble(), ySpeed, 0.5D - GlimmerstarParticle.RANDOM.nextDouble(), this.sprite);
         glimmerstar.yd *= 0.05F;
         if (xSpeed == 0.0D && zSpeed == 0.0D) {
            glimmerstar.xd *= 0.05F;
            glimmerstar.zd *= 0.05F;
         }
         glimmerstar.setLifetime((int)(8.0D / (level.random.nextDouble() * 0.8D + 0.2D)));
         return glimmerstar;
      }
   }

}