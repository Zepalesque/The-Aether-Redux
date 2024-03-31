package net.zepalesque.redux.entity;

import com.aetherteam.aether.data.resources.AetherMobCategory;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.entity.passive.AetherAnimal;
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
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.entity.misc.Rebux;
import net.zepalesque.redux.entity.monster.Blightbunny;
import net.zepalesque.redux.entity.neutral.Phudge;
import net.zepalesque.redux.entity.passive.Mykapod;
import net.zepalesque.redux.entity.passive.Shimmercow;
import net.zepalesque.redux.entity.projectile.*;
import net.zepalesque.redux.entity.util.EntitySpawner;
import net.zepalesque.redux.entity.util.ManyEntitySpawner;

@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReduxEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Redux.MODID);
    
    public static final RegistryObject<EntityType<VolatileFireCrystal>> VOLATILE_FIRE_CRYSTAL = ENTITY_TYPES.register("volatile_fire_crystal",
            () -> EntityType.Builder.<VolatileFireCrystal>of(VolatileFireCrystal::new, MobCategory.MISC).sized(0.85F, 0.85F).clientTrackingRange(4).updateInterval(10).fireImmune().build("fire_crystal")
    );
    public static final RegistryObject<EntityType<Swet>> VANILLA_SWET = ENTITY_TYPES.register("vanilla_swet",
            () -> EntityType.Builder.of(Swet::new, AetherMobCategory.AETHER_SURFACE_MONSTER).sized(0.9F, 0.95F).clientTrackingRange(10).build("vanilla_swet"));

    public static final RegistryObject<EntityType<InfusedVeridiumDart>> INFUSED_VERIDIUM_DART = ENTITY_TYPES.register("infused_veridium_dart",
            () -> EntityType.Builder.<InfusedVeridiumDart>of(InfusedVeridiumDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("infused_veridium_dart"));

    public static final RegistryObject<EntityType<VeridiumDart>> VERIDIUM_DART = ENTITY_TYPES.register("veridium_dart",
            () -> EntityType.Builder.<VeridiumDart>of(VeridiumDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("veridium_dart"));

    public static final RegistryObject<EntityType<ThrownSpear>> THROWN_SPEAR = ENTITY_TYPES.register("thrown_spear",
            () -> EntityType.Builder.<ThrownSpear>of(ThrownSpear::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("thrown_spear"));

    public static final RegistryObject<EntityType<Ember>> EMBER = ENTITY_TYPES.register("ember",
            () -> EntityType.Builder.<Ember>of(Ember::new, MobCategory.MISC).sized(0.125F, 0.125F).clientTrackingRange(4).updateInterval(20).build("ember"));

    public static final RegistryObject<EntityType<Rebux>> REBUX = ENTITY_TYPES.register("rebux",
            () -> EntityType.Builder.<Rebux>of(Rebux::new, MobCategory.MISC).sized(0.25F, 0.25F).clientTrackingRange(4).updateInterval(20).build("rebux"));

    public static final RegistryObject<EntityType<Shimmercow>> SHIMMERCOW = ENTITY_TYPES.register("shimmercow",
            () -> EntityType.Builder.of(Shimmercow::new, MobCategory.CREATURE).sized(1.125F, 1.625F).clientTrackingRange(10).build("shimmercow"));

    public static final RegistryObject<EntityType<Phudge>> PHUDGE = ENTITY_TYPES.register("phudge",
            () -> EntityType.Builder.of(Phudge::new, MobCategory.CREATURE).sized(0.95F, 0.95F).clientTrackingRange(10).build("phudge"));

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
    public static final RegistryObject<EntityType<EntitySpawner>> QUAIL_TM = ENTITY_TYPES.register("quail_tm",
            () -> EntityType.Builder.of(EntitySpawner.quail(() -> EntityType.CHICKEN), MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(4).updateInterval(5).fireImmune().build("quail_tm")
    );
    public static final RegistryObject<EntityType<ManyEntitySpawner>> ZEPHYR_SPAWNER = ENTITY_TYPES.register("zephyr_spawner",
            () -> EntityType.Builder.of(ManyEntitySpawner.fabricate(AetherEntityTypes.ZEPHYR), MobCategory.CREATURE).sized(0.4F, 0.7F).clientTrackingRange(4).updateInterval(5).fireImmune().build("zephyr_spawner")
    );
    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ReduxEntityTypes.VANILLA_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.SHIMMERCOW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.MYKAPOD.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.BLIGHTBUNNY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Blightbunny::checkBunnySpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.BLIGHTBUNNY_SPAWNER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySpawner::checkEntitySpawnerSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.COCKATRICE_SPAWNER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, (e, l, s, p, r) -> !ReduxConfig.COMMON.cockatrice_burn_in_daylight.get() && EntitySpawner.checkEntitySpawnerSpawnRules(e, l, s, p, r), SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.QUAIL_TM.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, EntitySpawner::checkEntitySpawnerSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.PHUDGE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.ZEPHYR_SPAWNER.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ManyEntitySpawner::checkManyEntitySpawnerSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ReduxEntityTypes.VANILLA_SWET.get(), Swet.createMobAttributes().build());
        event.put(ReduxEntityTypes.SHIMMERCOW.get(), Shimmercow.createMobAttributes().build());
        event.put(ReduxEntityTypes.MYKAPOD.get(), Mykapod.createAttributes().build());
        event.put(ReduxEntityTypes.BLIGHTBUNNY.get(), Blightbunny.createAttributes().build());
        event.put(ReduxEntityTypes.BLIGHTBUNNY_SPAWNER.get(), EntitySpawner.createAttributes().build());
        event.put(ReduxEntityTypes.COCKATRICE_SPAWNER.get(), EntitySpawner.createAttributes().build());
        event.put(ReduxEntityTypes.QUAIL_TM.get(), EntitySpawner.createAttributes().build());
        event.put(ReduxEntityTypes.ZEPHYR_SPAWNER.get(), ManyEntitySpawner.createAttributes().build());
        event.put(ReduxEntityTypes.PHUDGE.get(), Phudge.createMobAttributes().build());
    }
}

