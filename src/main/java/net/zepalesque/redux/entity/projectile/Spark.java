package net.zepalesque.redux.entity.projectile;

import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.TraceableEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.client.particle.options.SparkOptions;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class Spark extends Projectile {


    @OnlyIn(Dist.CLIENT)
    private Particle particle;

    protected Spark(EntityType<? extends Projectile> entityType, Level level) {
        super(entityType, level);
    }

    /**
     * Returns the mob this Spark came off of.
     */
    @Nullable
    public Entity getSource() {
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.tickCount % 5 == 0)
            this.level().addParticle(new SparkOptions(this.getUUID()), this.position().x, this.position().y, this.position().z, 0D, 0D, 0D);
        }
    }

    @Override
    protected void defineSynchedData() {

    }
}
