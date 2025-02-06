package net.zepalesque.redux.client.particle;

import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import org.joml.Vector3f;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ReduxParticles {

    // TODO: Custom Particles for Aerbound Cape, PLEASE!!!!
    private static final Vector3f SHINY_CLOUD_COLOR = Vec3.fromRGB24(16777215).toVector3f();
    public static final DustParticleOptions SHINY_CLOUD = new DustParticleOptions(SHINY_CLOUD_COLOR, 1.0F);

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Redux.MODID);

    public static final DeferredHolder<ParticleType<?>, SimpleParticleType>
            GILDENROOT_LEAF = PARTICLES.register("gildenroot_leaf", () -> new SimpleParticleType(false)),
            SHADEROOT_LEAF = PARTICLES.register("shaderoot_leaf", () -> new SimpleParticleType(false)),
            BLIGHTWILLOW_LEAF = PARTICLES.register("blightwillow_leaf", () -> new SimpleParticleType(false)),
            INFECTED_BLIGHTWILLOW_LEAF = PARTICLES.register("infected_blightwillow_leaf", () -> new SimpleParticleType(false)),
            CRYSTAL_LEAF = PARTICLES.register("crystal_leaf", () -> new SimpleParticleType(false)),
            SKYROOT_LEAF = PARTICLES.register("skyroot_leaf", () -> new SimpleParticleType(false)),
            GOLDEN_OAK_LEAF = PARTICLES.register("golden_oak_leaf", () -> new SimpleParticleType(false)),
            WHIRLWIND_LIGHTNING = PARTICLES.register("whirlwind_lightning", () -> new SimpleParticleType(false)),
            SPARK = PARTICLES.register("spark", () -> new SimpleParticleType(false));

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GILDENROOT_LEAF.get(), ReduxLeafParticle.Provider::new);
        event.registerSpriteSet(SHADEROOT_LEAF.get(), ReduxLeafParticle.Provider::new);
        event.registerSpriteSet(BLIGHTWILLOW_LEAF.get(), ReduxLeafParticle.Provider::new);
        event.registerSpriteSet(INFECTED_BLIGHTWILLOW_LEAF.get(), ReduxLeafParticle.Provider::new);
        event.registerSpriteSet(CRYSTAL_LEAF.get(), ReduxLeafParticle.Provider::new);
        event.registerSpriteSet(SKYROOT_LEAF.get(), ReduxLeafParticle.Provider::new);
        event.registerSpriteSet(GOLDEN_OAK_LEAF.get(), ReduxLeafParticle.Provider::new);


        event.registerSpriteSet(WHIRLWIND_LIGHTNING.get(), ReduxGlowParticle.Lightning::new);
        event.registerSpriteSet(SPARK.get(), SparkParticle.Provider::new);

    }
}
