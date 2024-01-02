package net.zepalesque.redux.client.particle;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SparkParticle extends TextureSheetParticle {
   private Entity entity;
   SparkParticle(ClientLevel pLevel, double pX, double pY, double pZ) {
      super(pLevel, pX, pY, pZ, 0.0D, 0.0D, 0.0D);
      this.quadSize *= this.random.nextFloat() * 1.5 + 0.15F;
      this.lifetime = 100;
   }

   @Override
   public void render(VertexConsumer buffer, Camera renderInfo, float partialTicks) {
      super.render(buffer, renderInfo, partialTicks);

   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_LIT;
   }

   public float getQuadSize(float pScaleFactor) {
      float f = ((float)this.age + pScaleFactor) / (float)this.lifetime;
      return this.quadSize * (1.0F - f * f);
   }

   public void tick() {
      super.tick();
      if (!this.removed) {
         if (this.entity != null && !this.entity.isRemoved()) {
            this.x = this.entity.position().x;
            this.y = this.entity.position().y;
            this.z = this.entity.position().z;
            this.xo = this.entity.xOld;
            this.yo = this.entity.yOld;
            this.zo = this.entity.zOld;
         }
      }

   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet sprite;
      private final Entity entity;

      public Provider(SpriteSet pSprites, Entity entity) {
         this.sprite = pSprites;
         this.entity = entity;
      }

      public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
         SparkParticle spark = new SparkParticle(pLevel, pX, pY, pZ);
         spark.pickSprite(this.sprite);
         spark.entity = this.entity;
         return spark;
      }
   }



}