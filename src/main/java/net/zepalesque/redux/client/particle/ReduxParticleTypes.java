package net.zepalesque.redux.client.particle;

import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.phys.Vec3;
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
import org.joml.Vector3f;

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

    public static final RegistryObject<SimpleParticleType> FALLING_CRYSTAL_SKYROOT_LEAVES = PARTICLES.register("falling_crystal_skyroot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_ENCHANTED_SKYROOT_LEAVES = PARTICLES.register("falling_enchanted_skyroot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_SKYROOT_PINE_NEEDLES = PARTICLES.register("falling_skyroot_pine_needles", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_HIGHSPROOT_NEEDLES = PARTICLES.register("falling_highsproot_needles", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> SAKURA_PETALS = PARTICLES.register("sakura_petals", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> FALLING_PURPLE_CRYSTAL_LEAVES = PARTICLES.register("falling_purple_crystal_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_BLUE_SKYROOT_LEAVES = PARTICLES.register("falling_blue_skyroot_leaves", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FALLING_DARK_BLUE_SKYROOT_LEAVES = PARTICLES.register("falling_dark_blue_skyroot_leaves", () -> new SimpleParticleType(false));

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

    public static final RegistryObject<SimpleParticleType> ICE_SHARD = PARTICLES.register("ice_shard", () -> new SimpleParticleType(false));
    public static final RegistryObject<SimpleParticleType> FROST = PARTICLES.register("frost", () -> new SimpleParticleType(false));

    public static final RegistryObject<SimpleParticleType> SHIMMERSTAR = PARTICLES.register("shimmerstar", () -> new SimpleParticleType(false));


    public static final RegistryObject<SimpleParticleType> SPARK = PARTICLES.register("spark", () -> new SimpleParticleType(false));
    private static final Vector3f SHINY_CLOUD_COLOR = Vec3.fromRGB24(16777215).toVector3f();
    public static final DustParticleOptions SHINY_CLOUD = new DustParticleOptions(SHINY_CLOUD_COLOR, 1.0F);


    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(GILDED_SKYROOT_LEAVES.get(), GildedSkyrootLeavesParticle.Provider::new);
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
        event.registerSpriteSet(SAKURA_PETALS.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_CRYSTAL_SKYROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_SKYROOT_PINE_NEEDLES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_ENCHANTED_SKYROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_HIGHSPROOT_NEEDLES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_PURPLE_CRYSTAL_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_BLUE_SKYROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_DARK_BLUE_SKYROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_SKYROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_BLIGHTED_SKYROOT_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_BLIGHTWILLOW_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_PRISMATIC_LEAVES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_GLACIA_NEEDLES.get(), FallingLeafParticle.Provider::new);
        event.registerSpriteSet(FALLING_PURPLE_GLACIA_NEEDLES.get(), FallingLeafParticle.Provider::new);

        event.registerSpriteSet(ICE_SHARD.get(), IceShardParticle.Provider::new);
        event.registerSpriteSet(FROST.get(), FrostParticle.Provider::new);

        register(event, FALLING_CLOUDCAP_SPORE.get(), CloudcapSporeParticle::falling);
        register(event, LANDING_CLOUDCAP_SPORE.get(), CloudcapSporeParticle::landing);
        event.registerSpriteSet(CLOUDCAP_AIR_SPORE.get(), CloudcapAirSporeParticle.Provider::new);

        event.registerSpriteSet(SPARK.get(), SparkParticle.Provider::new);

        event.registerSpriteSet(SHIMMERSTAR.get(), ShimmerstarParticle.Provider::new);
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
