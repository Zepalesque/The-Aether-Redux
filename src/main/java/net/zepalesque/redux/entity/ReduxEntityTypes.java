package net.zepalesque.redux.entity;

import com.aetherteam.aether.data.resources.AetherMobCategory;
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
import net.zepalesque.redux.entity.passive.Shimmercow;
import net.zepalesque.redux.entity.projectile.Ember;
import net.zepalesque.redux.entity.projectile.SpectralDart;
import net.zepalesque.redux.entity.projectile.VolatileFireCrystal;

@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ReduxEntityTypes {

    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Redux.MODID);
    
    public static final RegistryObject<EntityType<VolatileFireCrystal>> VOLATILE_FIRE_CRYSTAL = ENTITY_TYPES.register("volatile_fire_crystal", () -> {
        return EntityType.Builder.<VolatileFireCrystal>of(VolatileFireCrystal::new, MobCategory.MISC).sized(0.85F, 0.85F).clientTrackingRange(4).updateInterval(10).fireImmune().build("fire_crystal");
    });
    public static final RegistryObject<EntityType<Swet>> VANILLA_SWET = ENTITY_TYPES.register("vanilla_swet",
            () -> EntityType.Builder.of(Swet::new, AetherMobCategory.AETHER_SURFACE_MONSTER).sized(0.9F, 0.95F).clientTrackingRange(10).build("vanilla_swet"));

    public static final RegistryObject<EntityType<SpectralDart>> SPECTRAL_DART = ENTITY_TYPES.register("spectral_dart",
            () -> EntityType.Builder.<SpectralDart>of(SpectralDart::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build("spectral_dart"));

    public static final RegistryObject<EntityType<Ember>> EMBER = ENTITY_TYPES.register("ember",
            () -> EntityType.Builder.<Ember>of(Ember::new, MobCategory.MISC).sized(0.125F, 0.125F).clientTrackingRange(4).updateInterval(20).build("ember"));

    public static final RegistryObject<EntityType<Shimmercow>> SHIMMERCOW = ENTITY_TYPES.register("shimmercow",
            () -> EntityType.Builder.<Shimmercow>of(Shimmercow::new, MobCategory.MISC).sized(1.125F, 1.625F).clientTrackingRange(10).build("shimmercow"));

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {
        event.register(ReduxEntityTypes.VANILLA_SWET.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Swet::checkSwetSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ReduxEntityTypes.SHIMMERCOW.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, AetherAnimal::checkAetherAnimalSpawnRules, SpawnPlacementRegisterEvent.Operation.OR);
    }

    @SubscribeEvent
    public static void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(ReduxEntityTypes.VANILLA_SWET.get(), Swet.createMobAttributes().build());
        event.put(ReduxEntityTypes.SHIMMERCOW.get(), Shimmercow.createMobAttributes().build());
    }
}

