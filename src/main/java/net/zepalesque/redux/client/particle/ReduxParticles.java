package net.zepalesque.redux.client.particle;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import org.joml.Vector3f;

import java.util.function.Function;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT)
public class ReduxParticles {
	// TODO: Custom Particles for Aerbound Cape, PLEASE!!!!
	private static final Vector3f SHINY_CLOUD_COLOR = Vec3.fromRGB24(16777215).toVector3f();
	public static final DustParticleOptions SHINY_CLOUD = new DustParticleOptions(
		SHINY_CLOUD_COLOR,
		1.0F
	);

	public static final DeferredRegister<ParticleType<?>> PARTICLES = Redux.reg(
		BuiltInRegistries.PARTICLE_TYPE
	);

	public static final DeferredHolder<ParticleType<?>, SimpleParticleType>
		SILVEROOT_LEAF = PARTICLES.register("silveroot_leaf", () -> new SimpleParticleType(false)),
		STORMFIR_LEAF = PARTICLES.register("stormfir_leaf", () -> new SimpleParticleType(false)),
		MOONFIR_LEAF = PARTICLES.register("moonfir_leaf", () -> new SimpleParticleType(false)),
		BLIGHTWILLOW_LEAF = PARTICLES.register("blightwillow_leaf", () -> new SimpleParticleType(false)),
		INFECTED_BLIGHTWILLOW_LEAF = PARTICLES.register("infected_blightwillow_leaf", () -> new SimpleParticleType(false)),
		CRYSTAL_LEAF = PARTICLES.register("crystal_leaf", () -> new SimpleParticleType(false)),
		SKYROOT_LEAF = PARTICLES.register("skyroot_leaf", () -> new SimpleParticleType(false)),
		GOLDEN_OAK_LEAF = PARTICLES.register("golden_oak_leaf", () -> new SimpleParticleType(false)),
		WHIRLWIND_LIGHTNING = PARTICLES.register("whirlwind_lightning", () -> new SimpleParticleType(false)),
		SPARK = PARTICLES.register("spark", () -> new SimpleParticleType(false)),
		FALLING_CLOUDCAP_SPORE = PARTICLES.register("falling_cloudcap_spore", () -> new SimpleParticleType(false)),
		LANDING_CLOUDCAP_SPORE = PARTICLES.register("landing_cloudcap_spore", () -> new SimpleParticleType(false)),
		CLOUDCAP_AIR_SPORE = PARTICLES.register("cloudcap_air_spore", () -> new SimpleParticleType(false)),
		BLOSSOM_FLARE = PARTICLES.register("blossom_flare", () -> new SimpleParticleType(false));
	
	public static final DeferredHolder<ParticleType<?>, ParticleType<ItemParticleOption>> RANDOM_MOVEMENT_ITEM = register(
		"random_movement_item",
		false,
		ItemParticleOption::codec,
		ItemParticleOption::streamCodec
	);

	@SubscribeEvent
	@OnlyIn(Dist.CLIENT)
	public static void registerParticleFactories(RegisterParticleProvidersEvent event) {
		event.registerSpriteSet(SILVEROOT_LEAF.get(), ReduxLeafParticle.Provider::new);
		event.registerSpriteSet(STORMFIR_LEAF.get(), ReduxLeafParticle.Provider::new);
		event.registerSpriteSet(MOONFIR_LEAF.get(), ReduxLeafParticle.Provider::new);
		event.registerSpriteSet(BLIGHTWILLOW_LEAF.get(), ReduxLeafParticle.Provider::new);
		event.registerSpriteSet(INFECTED_BLIGHTWILLOW_LEAF.get(), ReduxLeafParticle.Provider::new);
		event.registerSpriteSet(CRYSTAL_LEAF.get(), ReduxLeafParticle.Provider::new);
		event.registerSpriteSet(SKYROOT_LEAF.get(), ReduxLeafParticle.Provider::new);
		event.registerSpriteSet(GOLDEN_OAK_LEAF.get(), ReduxLeafParticle.Provider::new);

		event.registerSpriteSet(WHIRLWIND_LIGHTNING.get(), ReduxGlowParticle.Lightning::new);
		event.registerSpriteSet(SPARK.get(), SparkParticle.Provider::new);
		event.registerSpriteSet(BLOSSOM_FLARE.get(), FlameParticle.Provider::new);
		event.registerSpriteSet(FALLING_CLOUDCAP_SPORE.get(), CloudcapSporeParticle.Falling::new);
		event.registerSpriteSet(LANDING_CLOUDCAP_SPORE.get(), CloudcapSporeParticle.Landing::new);
		event.registerSpriteSet(CLOUDCAP_AIR_SPORE.get(), CloudcapAirSporeParticle.Provider::new);
		event.registerSpriteSet(BLOSSOM_FLARE.get(), FlameParticle.Provider::new);
	}
	
	private static <T extends ParticleOptions> DeferredHolder<ParticleType<?>, ParticleType<T>> register(
		String name,
		boolean overrideLimitter,
		final Function<ParticleType<T>, MapCodec<T>> codecGetter,
		final Function<ParticleType<T>, StreamCodec<? super RegistryFriendlyByteBuf, T>> streamCodecGetter
	) {
		return PARTICLES.register(name, () -> new ParticleType<T>(overrideLimitter) {
			@Override
			public MapCodec<T> codec() {
				return codecGetter.apply(this);
			}
			
			@Override
			public StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec() {
				return streamCodecGetter.apply(this);
			}
		});
	}
}
