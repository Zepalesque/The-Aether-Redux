package net.zepalesque.redux.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class RandomMovementItemParticle extends TextureSheetParticle {
   private final float uo;
   private final float vo;


   public ParticleRenderType getRenderType() {
      return ParticleRenderType.TERRAIN_SHEET;
   }

   protected RandomMovementItemParticle(ClientLevel level, double x, double y, double z, ItemStack stack) {
      super(level, x, y, z, 0.0D, 0.0D, 0.0D);
      var model = Minecraft.getInstance().getItemRenderer().getModel(stack, level, (LivingEntity)null, 0);
      this.setSprite(model.getOverrides().resolve(model, stack, level, null, 0).getParticleIcon(net.minecraftforge.client.model.data.ModelData.EMPTY));
      this.gravity = 1.0F;
      this.quadSize /= 2.0F;
      this.uo = this.random.nextFloat() * 3.0F;
      this.vo = this.random.nextFloat() * 3.0F;
   }

   protected float getU0() {
      return this.sprite.getU((double)((this.uo + 1.0F) / 4.0F * 16.0F));
   }

   protected float getU1() {
      return this.sprite.getU((double)(this.uo / 4.0F * 16.0F));
   }

   protected float getV0() {
      return this.sprite.getV((double)(this.vo / 4.0F * 16.0F));
   }

   protected float getV1() {
      return this.sprite.getV((double)((this.vo + 1.0F) / 4.0F * 16.0F));
   }

   @OnlyIn(Dist.CLIENT)
   public static class Provider implements ParticleProvider<ItemParticleOption> {
      public Particle createParticle(ItemParticleOption type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
         return new RandomMovementItemParticle(level, x, y, z, type.getItem());
      }
   }
}
