package net.zepalesque.redux.entity;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.entity.passive.AetherAnimal;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.monster.Blightbunny;
import net.zepalesque.redux.entity.passive.Glimmercow;
import net.zepalesque.redux.entity.passive.Mykapod;
import net.zepalesque.redux.entity.projectile.Ember;
import net.zepalesque.redux.entity.projectile.SpectralDart;
import net.zepalesque.redux.entity.projectile.VeridiumArrow;
import net.zepalesque.redux.entity.projectile.VolatileFireCrystal;
import net.zepalesque.redux.entity.util.EntitySpawner;

@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReduxEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Redux.MODID);
    
    public static final RegistryObject<EntityType<VolatileFireCrystal>> VOLATILE_FIRE_CRYSTAL = ENTITY_TYPES.register("volatile_fire_crystal",
            () -> EntityType.Builder.<VolatileFireCrystal>of(VolatileFireCrystal::new, MobCategory.MISC).sized(0.85F, 0.85F).clientTrackingRange(4).updateInterval(10).fireImmune().build("fire_crystal")
    );
    public static final RegistryObject<EntityType<Swet>> VANILLA_SWET = ENTITY_TYPES.register("vanilla_swet",
            () -> EntityType.Builder.of(Swet::new, AetherMobCategory.AETHER_SURFACE_MONSTER).sized(0.9F, 0.95F).clientTrackingRange(10).build("vanilla_swet"));

    public static final RegistryObject<EntityType<SpectralDart>> SPECTRAL_DART = ENTITY_TYPES.register("spectral_dart",
            () -> EntityType.Builder.<SpectralDart>of(SpectralDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("spectral_dart"));

    public static final RegistryObject<EntityType<VeridiumArrow>> VERIDIUM_ARROW = ENTITY_TYPES.register("veridium_arrow",
            () -> EntityType.Builder.<VeridiumArrow>of(VeridiumArrow::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("veridium_arrow"));

    public static final RegistryObject<EntityType<Ember>> EMBER = ENTITY_TYPES.register("ember",
            () -> EntityType.Builder.<Ember>of(Ember::new, MobCategory.MISC).sized(0.125F, 0.125F).clientTrackingRange(4).updateInterval(20).build("ember"));

    public static final RegistryObject<EntityType<Glimmercow>> GLIMMERCOW = ENTITY_TYPES.register("glimmercow",
            () -> EntityType.Builder.of(Glimmercow::new, MobCategory.CREATURE).sized(1.125F, 1.625F).clientTrackingRange(10).build("glimmercow"));

    public static final RegistryObject<EntityType<Mykapod>> MYKAPOD = ENTITY_TYPES.register("mykapod",
            () -> EntityType.Builder.of(Mykapod::new, MobCategory.CREATURE).sized(0.35F, 0.35F).clientTrackingRange(10).build("mykapod"));

    public static final RegistryObject<EntityType<Blightbunny>> BLIGHTBUNNY = ENTITY_TYPES.register("blightbunny",
            () -> EntityType.Builder.of(Blightbunny::new, AetherMobCategory.AETHER_DARKNESS_MONSTER).sized(0.6F, 0.5F).clientTrackingRange(10).build("blightbunny"));


    public static final RegistryObject<EntityType<EntitySpawner>> COCKATRICE_SPAWNER = ENTITY_TYPES.register("cockatrice_spawner",
            () -> EntityType.Builder.of(EntitySpawner.fabricate(AetherEntityTypes.COCKATRICE), MobCategory.CREATURE).sized(0.95F, 2.15F).clientTrackingRange(4).updateInterval(5).fireImmune().build("cockatrice_spawner")
    );

    public static final RegistryObject<EntityType<EntitySpawner>> BLIGHTBUNNY_SPAWNER = ENTITY_TYPES.register("blightbunny_spawner",
            () -> EntityType.Builder.of(EntitySpawner.fabricate(ReduxEntityTypes.BLIGHTBUNNY), MobCategory.CREATURE).sized(0.6F, 0.5F).clientTrackingRange(4).updateInterval(5).fireImmune().build("blightbunny_spawner")
    );
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ReduxEntityTypes.VANILLA_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.GLIMMERCOW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.BLIGHTBUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Blightbunny::checkBunnySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.BLIGHTBUNNY_SPAWNER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySpawner::checkEntitySpawnerSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.COCKATRICE_SPAWNER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySpawner::checkEntitySpawnerSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ReduxEntityTypes.VANILLA_SWET.get(), Swet.createMobAttributes().build());
        event.put(ReduxEntityTypes.GLIMMERCOW.get(), Glimmercow.createMobAttributes().build());
        event.put(ReduxEntityTypes.MYKAPOD.get(), Mykapod.createAttributes().build());
        event.put(ReduxEntityTypes.BLIGHTBUNNY.get(), Blightbunny.createAttributes().build());
        event.put(ReduxEntityTypes.BLIGHTBUNNY_SPAWNER.get(), EntitySpawner.createAttributes().build());
        event.put(ReduxEntityTypes.COCKATRICE_SPAWNER.get(), EntitySpawner.createAttributes().build());
    }
}

