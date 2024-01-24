package net.zepalesque.redux.client.particle;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SplashParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

@EventBusSubscriber(
    modid = Redux.MODID,
    value = {Dist.CLIENT},
    bus = Bus.MOD
)
public class ReduxParticleTypes {
    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Redux.MODID);
    public static final RegistryObject<SimpleParticleType> GILDED_SKYROOT_LEAVES = PARTICLES.register("gilded_skyroot_leaves", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FALLING_GILDED_LEAVES = PARTICLES.register("falling_gilded_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_GOLDEN_LEAVES = PARTICLES.register("falling_golden_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_CRYSTAL_LEAVES = PARTICLES.register("falling_crystal_leaves", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FALLING_CONBERRY_LEAVES = PARTICLES.register("falling_conberry_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_CRUDEROOT_NEEDLES = PARTICLES.register("falling_cruderoot_needles", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_SUNROOT_LEAVES = PARTICLES.register("falling_sunroot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_YAGROOT_LEAVES = PARTICLES.register("falling_yagroot_leaves", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FIELDSPROUT_PETALS_0 = PARTICLES.register("fieldsprout_petals_0", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FIELDSPROUT_PETALS_1 = PARTICLES.register("fieldsprout_petals_1", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FIELDSPROUT_PETALS_2 = PARTICLES.register("fieldsprout_petals_2", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FIELDSPROUT_PETALS_3 = PARTICLES.register("fieldsprout_petals_3", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FIELDSPROUT_PETALS_4 = PARTICLES.register("fieldsprout_petals_4", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FIELDSPROUT_PETALS_5 = PARTICLES.register("fieldsprout_petals_5", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FIELDSPROUT_PETALS_6 = PARTICLES.register("fieldsprout_petals_6", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FALLING_SKYROOT_LEAVES = PARTICLES.register("falling_skyroot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_BLIGHTWILLOW_LEAVES = PARTICLES.register("falling_blightwillow_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_BLIGHTED_SKYROOT_LEAVES = PARTICLES.register("falling_blighted_skyroot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_PRISMATIC_LEAVES = PARTICLES.register("falling_prismatic_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_GLACIA_NEEDLES = PARTICLES.register("falling_glacia_needles", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_PURPLE_GLACIA_NEEDLES = PARTICLES.register("falling_purple_glacia_needles", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FALLING_CLOUDCAP_SPORE = PARTICLES.register("falling_cloudcap_spore", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> LANDING_CLOUDCAP_SPORE = PARTICLES.register("landing_cloudcap_spore", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> CLOUDCAP_AIR_SPORE = PARTICLES.register("cloudcap_air_spore", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FROST = PARTICLES.register("frost", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_ICE = PARTICLES.register("falling_ice", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> ICE_SPLASH = PARTICLES.register("ice_splash", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> SPARK = PARTICLES.register("spark", () -> new SimpleParticleType(false));


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GILDED_SKYROOT_LEAVES.get(), GildedSkyrootLeavesParticle.Provider::new);
//        event.registerSpriteSet(PINK_PRISMATIC_LEAVES.get(), PrismaticLeafParticle.Provider::new);
//        event.registerSpriteSet(TEAL_PRISMATIC_LEAVES.get(), PrismaticLeafParticle.Provider::new);
        event.registerSpriteSet(FIELDSPROUT_PETALS_0.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FIELDSPROUT_PETALS_1.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FIELDSPROUT_PETALS_2.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FIELDSPROUT_PETALS_3.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FIELDSPROUT_PETALS_4.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FIELDSPROUT_PETALS_5.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FIELDSPROUT_PETALS_6.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_CONBERRY_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_CRUDEROOT_NEEDLES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_SUNROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_YAGROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_GILDED_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_GOLDEN_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_CRYSTAL_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_SKYROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_BLIGHTED_SKYROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_BLIGHTWILLOW_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_PRISMATIC_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_GLACIA_NEEDLES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_PURPLE_GLACIA_NEEDLES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FROST.get(), FrostParticle.Provider::new);
        event.registerSpriteSet(FALLING_ICE.get(), FrostParticle.DripProvider::new);
        event.registerSpriteSet(ICE_SPLASH.get(), SplashParticle.Provider::new);

        register(event, FALLING_CLOUDCAP_SPORE.get(), CloudcapSporeParticle::falling);
        register(event, LANDING_CLOUDCAP_SPORE.get(), CloudcapSporeParticle::landing);
        event.registerSpriteSet(CLOUDCAP_AIR_SPORE.get(), CloudcapAirSporeParticle.Provider::new);

        event.registerSpriteSet(SPARK.get(), SparkParticle.Provider::new);
    }

    public static <T extends ParticleOptions> void register(RegisterParticleProvidersEvent event, ParticleType<T> particleType, ParticleProvider.Sprite<T> sprite) {
        event.registerSpriteSet(particleType, (p_272320_) -> (p_272323_, p_272324_, p_272325_, p_272326_, p_272327_, p_272328_, p_272329_, p_272330_) -> {
            TextureSheetParticle texturesheetparticle = sprite.createParticle(p_272323_, p_272324_, p_272325_, p_272326_, p_272327_, p_272328_, p_272329_, p_272330_);
            if (texturesheetparticle != null) {
                texturesheetparticle.pickSprite(p_272320_);
            }

            return texturesheetparticle;
        });
    }
}
