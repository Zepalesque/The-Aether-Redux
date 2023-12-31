package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.PortalParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class GildedSkyrootLeavesParticle extends PortalParticle {
    protected GildedSkyrootLeavesParticle(ClientLevel worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.rCol = 0.90980392156F;
        this.gCol = 0.90588235294F;
        this.bCol = 0.87058823529F;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            GildedSkyrootLeavesParticle leavesParticle = new GildedSkyrootLeavesParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            leavesParticle.pickSprite(this.spriteSet);
            return leavesParticle;
        }
    }
}
