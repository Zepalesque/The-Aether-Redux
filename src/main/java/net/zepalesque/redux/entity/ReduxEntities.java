package net.zepalesque.redux.entity;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import java.util.Map;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.entity.projectile.Ember;
import net.zepalesque.redux.entity.projectile.VeridiumDart;

@EventBusSubscriber(modid = Redux.MODID)
public class ReduxEntities {
	public static final DeferredRegister<EntityType<?>> ENTITIES = Redux.reg(BuiltInRegistries.ENTITY_TYPE);

	public static final DeferredHolder<EntityType<?>, EntityType<CatFish>> CAT_FISH = registerEntity(
		"cat_fish",
		EntityType.Builder.of(CatFish::new, MobCategory.WATER_AMBIENT)
			.sized(0.85F, 0.45F)
			.eyeHeight(0.2f)
	);

	public static final DeferredHolder<EntityType<?>, EntityType<Ember>> EMBER = registerEntity(
		"ember",
		EntityType.Builder.<Ember>of(Ember::new, MobCategory.MISC)
			.sized(0.125F, 0.125F)
			.clientTrackingRange(4)
			.updateInterval(20)
	);

	public static final DeferredHolder<EntityType<?>, EntityType<VeridiumDart>> INFUSED_VERIDIUM_DART = registerEntity(
		"infused_veridium_dart",
		EntityType.Builder.<VeridiumDart>of(VeridiumDart::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
	);

	public static final DeferredHolder<EntityType<?>, EntityType<VeridiumDart.Uninfused>> VERIDIUM_DART = registerEntity(
		"veridium_dart",
		EntityType.Builder.<VeridiumDart.Uninfused>of(VeridiumDart.Uninfused::new, MobCategory.MISC)
			.sized(0.5F, 0.5F)
			.clientTrackingRange(4)
			.updateInterval(20)
	);

	public static void addBossConversions() {
		Map<Block, Function<BlockState, BlockState>> slider = Slider.DUNGEON_BLOCK_CONVERSIONS;

		slider.put(ReduxBlocks.LOCKED_CARVED_BASE.get(), state -> ReduxBlocks.CARVED_BASE.get().defaultBlockState());
		slider.put(ReduxBlocks.LOCKED_SENTRY_BASE.get(), state -> ReduxBlocks.SENTRY_BASE.get().defaultBlockState());
		slider.put(ReduxBlocks.LOCKED_CARVED_PILLAR.get(), state -> ReduxBlocks.CARVED_PILLAR.get().defaultBlockState());
		slider.put(ReduxBlocks.LOCKED_RUNELIGHT.get(), state -> ReduxBlocks.RUNELIGHT.get().defaultBlockState());
		slider.put(ReduxBlocks.LOCKED_POLISHED_SENTRITE.get(), state -> ReduxStoneSets.POLISHED_SENTRITE.block().get().defaultBlockState());
	}

	@SubscribeEvent
	public static void registerAttributes(EntityAttributeCreationEvent event) {
		event.put(
			CAT_FISH.get(),
			CatFish.createAttributes().build()
		);
	}

	@SubscribeEvent
	public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
		event.register(
			CAT_FISH.get(),
			SpawnPlacementTypes.IN_WATER,
			Heightmap.Types.OCEAN_FLOOR_WG,
			CatFish::checkSurfaceWaterAnimalSpawnRules,
			RegisterSpawnPlacementsEvent.Operation.OR
		);
	}

	private static <E extends Entity> DeferredHolder<EntityType<?>, EntityType<E>> registerEntity(
		String name,
		EntityType.Builder<E> builder
	) {
		return ENTITIES.register(
			name,
			() -> builder.build(name)
		);
	}
}
