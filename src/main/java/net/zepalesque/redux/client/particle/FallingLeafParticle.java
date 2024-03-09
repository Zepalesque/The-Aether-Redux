package net.zepalesque.redux.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.util.math.MathUtil;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class FallingLeafParticle extends TextureSheetParticle {
    private float rotSpeed;
    private final float particleRandom;
    private final float spinAcceleration;
    private float onGroundTime = 40;
    protected static final double MAXIMUM_COLLISION_VELOCITY_SQUARED = Mth.square(100.0D);

    protected FallingLeafParticle(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
        this.rotSpeed = (float)Math.toRadians(this.random.nextBoolean() ? -30.0D : 30.0D);
        this.roll = MathUtil.degToRad(this.random.nextFloat() * 360F);
        this.oRoll = this.roll;
        this.particleRandom = this.random.nextFloat();
        this.spinAcceleration = (float)Math.toRadians(this.random.nextBoolean() ? -5.0D : 5.0D);
        this.lifetime = 300;
        this.gravity = 7.5E-4F;
        float f = this.random.nextBoolean() ? 0.05F : 0.075F;
        this.quadSize = f;
        this.setSize(f, f);
        this.friction = 1.0F;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        if (this.lifetime-- <= 0) {
            this.remove();
        }

        if (!this.removed) {
            float f = (float)(300 - this.lifetime);
            float f1 = Math.min(f / 300.0F, 1.0F);
            double d0 = Math.cos(Math.toRadians(this.particleRandom * 60.0F)) * 2.0D * Math.pow(f1, 1.25D);
            double d1 = Math.sin(Math.toRadians(this.particleRandom * 60.0F)) * 2.0D * Math.pow(f1, 1.25D);
            this.oRoll = this.roll;
            if (!(this.onGround || this.onGroundTime < 40)) {
                this.xd += d0 * (double) 0.0025F;
                this.zd += d1 * (double) 0.0025F;
                this.yd -= this.gravity;
                this.rotSpeed += this.spinAcceleration / 20.0F;
                this.roll += this.rotSpeed / 20.0F;
            }
            this.move(this.xd, this.yd, this.zd);
            if ((this.onGround || this.onGroundTime < 40) && this.lifetime < 299) {
                this.onGroundTime--;
                this.xd = 0.0D;
                this.zd = 0.0D;
            }
            if (this.onGroundTime < 0) {
                this.remove();
            }


            if (onGroundTime <= 20) {
                this.alpha = this.onGroundTime / 20;
            }

            if (!this.removed) {
                this.xd *= this.friction;
                this.yd *= this.friction;
                this.zd *= this.friction;
            }
        }
    }

    @Override
    public void move(double x, double y, double z) {
        double d0 = x;
        double d1 = y;
        double d2 = z;
        if (this.hasPhysics && (x != 0.0D || y != 0.0D || z != 0.0D) && x * x + y * y + z * z < MAXIMUM_COLLISION_VELOCITY_SQUARED) {
            Vec3 vec3 = Entity.collideBoundingBox(null, new Vec3(x, y, z), this.getBoundingBox(), this.level, List.of());
            x = vec3.x;
            y = vec3.y;
            z = vec3.z;
        }
        if (x != 0.0D || y != 0.0D || z != 0.0D) {
            this.setBoundingBox(this.getBoundingBox().move(x, y, z));
            this.setLocationFromBoundingbox();
        }
        this.onGround = d1 != y && d1 < 0.0D;

        if (d0 != x) {
            this.xd = 0.0D;
        }

        if (d2 != z) {
            this.zd = 0.0D;
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SimpleParticleType> {
        private final SpriteSet spriteSet;

        public Provider(SpriteSet spriteSetIn) {
            this.spriteSet = spriteSetIn;
        }

        public Particle createParticle(SimpleParticleType typeIn, ClientLevel worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            FallingLeafParticle leavesParticle = new FallingLeafParticle(worldIn, x, y, z);
            leavesParticle.pickSprite(this.spriteSet);
            return leavesParticle;
        }
    }
}