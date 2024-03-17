package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class CloudcapSporeParticle extends TextureSheetParticle {
   private final Fluid type;
   protected boolean isGlowing;

   CloudcapSporeParticle(ClientLevel level, double x, double y, double z, Fluid type) {
      super(level, x, y, z);
      this.setSize(0.01F, 0.01F);
      this.gravity = 0.06F;
      this.type = type;
   }

   protected Fluid getType() {
      return this.type;
   }

   public ParticleRenderType getRenderType() {
      return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
   }

   public int getLightColor(float partialTick) {
      return this.isGlowing ? 240 : super.getLightColor(partialTick);
   }

   public void tick() {
      this.xo = this.x;
      this.yo = this.y;
      this.zo = this.z;
      this.preMoveUpdate();
      if (!this.removed) {
         this.yd -= (double)this.gravity;
         this.move(this.xd, this.yd, this.zd);
         this.postMoveUpdate();
         if (!this.removed) {
            this.xd *= (double)0.98F;
            this.yd *= (double)0.98F;
            this.zd *= (double)0.98F;
            if (this.type != Fluids.EMPTY) {
               BlockPos blockpos = new BlockPos(this.x, this.y, this.z);
               FluidState fluidstate = this.level.getFluidState(blockpos);
               if (fluidstate.getType() == this.type && this.y < (double)((float)blockpos.getY() + fluidstate.getHeight(this.level, blockpos))) {
                  this.remove();
               }

            }
         }
      }
   }

   protected void preMoveUpdate() {
      if (this.lifetime-- <= 0) {
         this.remove();
      }

   }

   protected void postMoveUpdate() {
   }

   @OnlyIn(Dist.CLIENT)
   public static class Falling implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Falling(SpriteSet spriteSetIn) {
         this.spriteSet = spriteSetIn;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CloudcapSporeParticle dripparticle = new FallingSpore(level, x, y, z, Fluids.EMPTY, ReduxParticleTypes.LANDING_CLOUDCAP_SPORE.get());
         dripparticle.isGlowing = true;
         dripparticle.gravity = 0.0025F;
         dripparticle.pickSprite(this.spriteSet);
         return dripparticle;
      }
   }
   @OnlyIn(Dist.CLIENT)
   public static class Landing implements ParticleProvider<SimpleParticleType> {
      private final SpriteSet spriteSet;

      public Landing(SpriteSet spriteSetIn) {
         this.spriteSet = spriteSetIn;
      }

      public Particle createParticle(SimpleParticleType typeIn, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         CloudcapSporeParticle dripparticle = new LandingSpore(level, x, y, z, Fluids.EMPTY);
         dripparticle.isGlowing = true;
         dripparticle.lifetime = (int)(28.0D / (Math.random() * 0.8D + 0.2D));
         dripparticle.pickSprite(this.spriteSet);
         return dripparticle;
      }
   }



   @OnlyIn(Dist.CLIENT)
   static class LandingSpore extends CloudcapSporeParticle {
      LandingSpore(ClientLevel p_106102_, double p_106103_, double p_106104_, double p_106105_, Fluid p_106106_) {
         super(p_106102_, p_106103_, p_106104_, p_106105_, p_106106_);
         this.lifetime = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public static class FallingSpore extends CloudcapSporeParticle.FallingParticle {
      protected final ParticleOptions landParticle;

      public FallingSpore(ClientLevel level, double x, double y, double z, Fluid type, ParticleOptions landParticle) {
         super(level, x, y, z, type);
         this.landParticle = landParticle;
      }

      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
            this.level.addParticle(this.landParticle, this.x, this.y, this.z, 0.0D, 0.0D, 0.0D);
         }

      }
   }

   @OnlyIn(Dist.CLIENT)
   static class FallingParticle extends CloudcapSporeParticle {
      FallingParticle(ClientLevel level, double x, double y, double z, Fluid type) {
         this(level, x, y, z, type, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
      }

      FallingParticle(ClientLevel level, double x, double y, double z, Fluid type, int lifetime) {
         super(level, x, y, z, type);
         this.lifetime = lifetime;
      }

      protected void postMoveUpdate() {
         if (this.onGround) {
            this.remove();
         }

      }
   }
}