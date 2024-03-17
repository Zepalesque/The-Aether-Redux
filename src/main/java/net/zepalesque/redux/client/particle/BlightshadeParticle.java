package net.zepalesque.redux.client.particle;

import java.util.Optional;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BlightshadeParticle extends TextureSheetParticle {

   private final int fadeStartThreshold;

   BlightshadeParticle(ClientLevel level, SpriteSet sprites, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(level, x, y - 0.125D, z, xSpeed, ySpeed, zSpeed);
      this.setSize(0.01F, 0.01F);
      this.pickSprite(sprites);
      this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
      this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      this.fadeStartThreshold = this.lifetime / 2;
      this.hasPhysics = false;
      this.friction = 1.0F;
      this.gravity = -0.2F;
      this.xd = xSpeed + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.yd = ySpeed + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
      this.zd = zSpeed + (Math.random() * 2.0D - 1.0D) * (double)0.05F;
   }

   @Override
   public void tick() {
      super.tick();
      int countdown = (this.lifetime - this.age);
      if (countdown <= this.fadeStartThreshold) {
         this.alpha = (float) countdown / this.fadeStartThreshold;
      }
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet sprites) {
         this.sprite = sprites;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         double d0 = (double)level.random.nextFloat() * -1.9D * (double)level.random.nextFloat() * 0.05D;
         BlightshadeParticle suspendedparticle = new BlightshadeParticle(level, this.sprite, x, y, z, 0.0D, d0, 0.0D);
         suspendedparticle.setSize(0.001F, 0.001F);
         return suspendedparticle;
      }
   }
}