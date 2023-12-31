package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FrostParticle extends TextureSheetParticle {
   FrostParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
      super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
      this.gravity = 1.5F;
      this.friction = 0.999F;
      this.xd *= 0.8F;
      this.yd *= 0.8F;
      this.zd *= 0.8F;
      this.yd = this.random.nextFloat() * 0.4F + 0.05F;
      this.quadSize *= this.random.nextFloat() * 1.5 + 0.15F;
      this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getLightColor(float pPartialTick) {
      int i = super.getLightColor(pPartialTick);
      int j = 240;
      int k = i >> 16 & 255;
      return 240 | k << 16;
   }

   public float getQuadSize(float pScaleFactor) {
      float f = ((float)this.age + pScaleFactor) / (float)this.lifetime;
      return this.quadSize * (1.0F - f * f);
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         float f = (float)this.age / (float)this.lifetime;
         if (this.random.nextFloat() > f && !this.onGround) {
            this.level.addParticle(ReduxParticleTypes.FALLING_ICE.get(), this.x, this.y, this.z, this.xd, this.yd, this.zd);
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public Provider(SpriteSet pSprites) {
         this.sprite = pSprites;
      }

      public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
         FrostParticle frostparticle = new FrostParticle(pLevel, pX, pY, pZ);
         frostparticle.pickSprite(this.sprite);
         return frostparticle;
      }
   }


   @OnlyIn(Dist.CLIENT)
   public static class DripProvider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;

      public DripProvider(SpriteSet pSprites) {
         this.sprite = pSprites;
      }

      public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
         DripParticle dripparticle = new DripParticle.FallAndLandParticle(pLevel, pX, pY, pZ, Fluids.EMPTY, ReduxParticleTypes.ICE_SPLASH.get());
         dripparticle.setColor(0.55686274509F, 0.72156862745F, 0.99607843137F);
         dripparticle.pickSprite(this.sprite);
         return dripparticle;
      }
   }


}