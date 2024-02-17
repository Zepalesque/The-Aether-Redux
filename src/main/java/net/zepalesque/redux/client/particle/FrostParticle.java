package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrostParticle extends TextureSheetParticle {
   FrostParticle(ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(level, x, y, z, 0.0D, 0.0D, 0.0D);
      this.friction = 0.7F;
      this.gravity = 0.5F;
      this.xd *= 0.1F;
      this.yd *= 0.1F;
      this.zd *= 0.1F;
      this.xd += xSpeed * 0.4D;
      this.yd += ySpeed * 0.4D;
      this.zd += zSpeed * 0.4D;
      this.quadSize *= 0.75F;
      this.lifetime = Math.max((int)(6.0D / (Math.random() * 0.8D + 0.6D)), 1);
      this.hasPhysics = false;
      this.tick();
   }

   public float getQuadSize(float scaleFactor) {
      return this.quadSize * Mth.clamp(((float)this.age + scaleFactor) / (float)this.lifetime * 32.0F, 0.0F, 1.0F);
   }

   public void tick() {
      super.tick();
      this.rCol *= 0.9F;
      this.gCol *= 0.96F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }


   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet sprites) {
         this.sprite = sprites;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         FrostParticle frost = new FrostParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
         frost.pickSprite(this.sprite);
         return frost;
      }
   }
}