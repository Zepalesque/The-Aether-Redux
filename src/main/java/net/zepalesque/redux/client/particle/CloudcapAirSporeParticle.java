package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.ParticleGroup;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class CloudcapAirSporeParticle extends TextureSheetParticle {

   protected boolean isGlowing;

   CloudcapAirSporeParticle(ClientLevel level, SpriteSet sprites, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
      super(level, x, y - 0.125D, z, xSpeed, ySpeed, zSpeed);
      this.setSize(0.005F, 0.005F);
      this.pickSprite(sprites);
      this.quadSize *= this.random.nextFloat() * 0.6F + 0.6F;
      this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      this.hasPhysics = false;
      this.friction = 1.0F;
      this.gravity = 0.0F;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getLightColor(float partialTick) {
      return this.isGlowing ? 240 : super.getLightColor(partialTick);
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet sprites) {
         this.sprite = sprites;
      }

      public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CloudcapAirSporeParticle particle = new CloudcapAirSporeParticle(level, this.sprite, x, y, z, 0.0D, -0.8F, 0.0D) {
            public Optional<ParticleGroup> getParticleGroup() {
               return Optional.of(ParticleGroup.SPORE_BLOSSOM);
            }
         };
         particle.lifetime = Mth.randomBetweenInclusive(level.random, 500, 1000);
         particle.gravity = 0.01F;
         particle.isGlowing = true;
         return particle;
      }
   }
}