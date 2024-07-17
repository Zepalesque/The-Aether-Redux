package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherPlacedFeatures;
import com.aetherteam.aether.world.placementmodifier.DungeonBlacklistFilter;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.NoiseThresholdCountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;

import static net.zepalesque.redux.data.resource.Folders.ORE;

public class ReduxPlacements {

    public static final DungeonBlacklistFilter DUNGEON_BLACKLIST = new DungeonBlacklistFilter();
    public static final NoiseThresholdCountPlacement NOISE_THRESHOLD = NoiseThresholdCountPlacement.of(-0.8D, 5, 10);

    public static final ResourceKey<PlacedFeature> AEVELIUM_GRASSES_PATCH = copyKey(ReduxFeatureConfig.AEVELIUM_GRASSES_PATCH);
    public static final ResourceKey<PlacedFeature> AURUM_PATCH = copyKey(ReduxFeatureConfig.AURUM_PATCH);
    public static final ResourceKey<PlacedFeature> ZYATRIX_PATCH = copyKey(ReduxFeatureConfig.ZYATRIX_PATCH);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_PURPLE_PATCH = createKey(Folders.PATCH + "shrublands_purple_patch");
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_WHITE_PATCH = createKey(Folders.PATCH + "shrublands_white_patch");
    public static final ResourceKey<PlacedFeature> ZANBERRY_BUSH_PATCH = copyKey(ReduxFeatureConfig.ZANBERRY_BUSH_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_SPARSE_VEGETATION = createKey(Folders.CAVE + "blightmoss_sparse_vegetation");
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_VEGETATION = createKey(Folders.CAVE + "blightmoss_vegetation");
    public static final ResourceKey<PlacedFeature> FUNGAL_SPARSE_VEGETATION = createKey(Folders.CAVE + "fungal_sparse_vegetation");
    public static final ResourceKey<PlacedFeature> FUNGAL_VEGETATION = createKey(Folders.CAVE + "fungal_vegetation");
    public static final ResourceKey<PlacedFeature> BLIGHTSHADE_PATCH = copyKey(ReduxFeatureConfig.BLIGHTSHADE_PATCH);
    public static final ResourceKey<PlacedFeature> CORRUPTED_VINES_PATCH = copyKey(ReduxFeatureConfig.CORRUPTED_VINES_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHT_ROCK = copyKey(ReduxFeatureConfig.BLIGHT_ROCK);
    public static final ResourceKey<PlacedFeature> GLIMMERSTOOL_ROCK = copyKey(ReduxFeatureConfig.GLIMMERSTOOL_ROCK);
    public static final ResourceKey<PlacedFeature> DENSE_GRASS = createKey(Folders.PATCH + "dense_grass");
    public static final ResourceKey<PlacedFeature> GLIMMERSTOOL_PATCH = copyKey(ReduxFeatureConfig.GLIMMERSTOOL_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHT_TREES = copyKey(ReduxFeatureConfig.BLIGHT_TREES);
    public static final ResourceKey<PlacedFeature> CLOUDCAP_MUSHLING_PATCH = copyKey(ReduxFeatureConfig.CLOUDCAP_MUSHLING_PATCH);
    public static final ResourceKey<PlacedFeature> DAGGERBLOOM_PATCH = copyKey(ReduxFeatureConfig.DAGGERBLOOM_PATCH);
    public static final ResourceKey<PlacedFeature> THERATIP_PATCH = copyKey(ReduxFeatureConfig.THERATIP_PATCH);
    public static final ResourceKey<PlacedFeature> SPLITFERN_PATCH = copyKey(ReduxFeatureConfig.SPLITFERN_PATCH);
    public static final ResourceKey<PlacedFeature> AEROGEL_ORE = copyKey(ReduxFeatureConfig.AEROGEL_ORE);
    public static final ResourceKey<PlacedFeature> SPARSE_AEROGEL_ORE = createKey(Folders.ORE + "sparse_aerogel_ore");
    public static final ResourceKey<PlacedFeature> BLIGHTED_AERCLOUD = createKey(Folders.MISC + "blighted_aercloud");
    public static final ResourceKey<PlacedFeature> SPARSE_BLUE_AERCLOUD = createKey(Folders.MISC + "sparse_blue_aercloud");
    public static final ResourceKey<PlacedFeature> SPARSE_ZANITE_ORE = createKey(Folders.ORE + "sparse_zanite_ore");
    public static final ResourceKey<PlacedFeature> SPARSE_AMBROSIUM_ORE = createKey(Folders.ORE + "sparse_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> DENSE_ZANITE_ORE = createKey(Folders.ORE + "dense_zanite_ore");
    public static final ResourceKey<PlacedFeature> DENSE_AMBROSIUM_ORE = createKey(Folders.ORE + "dense_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> DENSE_BLUE_AERCLOUD = createKey(Folders.MISC + "dense_blue_aercloud");
    public static final ResourceKey<PlacedFeature> LARGE_ICESTONE_CHUNK = copyKey(ReduxFeatureConfig.LARGE_ICESTONE_CHUNK);
    public static final ResourceKey<PlacedFeature> FROSTED_PURPLE_FLOWER_PATCH = copyKey(ReduxFeatureConfig.FROSTED_PURPLE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> FROSTED_TREES = copyKey(ReduxFeatureConfig.FROSTED_TREES);
    public static final ResourceKey<PlacedFeature> GLACIAL_TREES = copyKey(ReduxFeatureConfig.GLACIAL_TREES);
    public static final ResourceKey<PlacedFeature> GILDED_HOLYSTONE_ORE = copyKey(ReduxFeatureConfig.GILDED_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_HOLYSTONE_ORE = copyKey(ReduxFeatureConfig.BLIGHTMOSS_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> GILDED_ROCK  = copyKey(ReduxFeatureConfig.GILDED_ROCK);
    public static final ResourceKey<PlacedFeature> GILDED_WHITE_FLOWER_PATCH = copyKey(ReduxFeatureConfig.GILDED_WHITE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> GLOWSPROUTS_PATCH = copyKey(ReduxFeatureConfig.GLOWSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_CLOVER_PATCH = copyKey(ReduxFeatureConfig.GOLDEN_CLOVER_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_HEIGHTS_GILDED_HOLYSTONE_ORE = createKey(ORE + "golden_heights_" + name(ReduxBlocks.GILDED_HOLYSTONE) + "_ore");
    public static final ResourceKey<PlacedFeature> GROVE_TREES_LEGACY = copyKey(ReduxFeatureConfig.GROVE_TREES_LEGACY);
    public static final ResourceKey<PlacedFeature> GRASSLAND_TREES_LEGACY = copyKey(ReduxFeatureConfig.GRASSLAND_TREES_LEGACY);
    public static final ResourceKey<PlacedFeature> GROVE_TREES = copyKey(ReduxFeatureConfig.GROVE_TREES);
    public static final ResourceKey<PlacedFeature> GRASSLAND_TREES = copyKey(ReduxFeatureConfig.GRASSLAND_TREES);
    public static final ResourceKey<PlacedFeature> SKYFIELDS_ROCK = copyKey(ReduxFeatureConfig.SKYFIELDS_ROCK);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_ROCK  = copyKey(ReduxFeatureConfig.SHRUBLANDS_ROCK);
    public static final ResourceKey<PlacedFeature> SKYSPROUTS_PATCH = copyKey(ReduxFeatureConfig.SKYSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> SKYFIELDS_TREES = copyKey(ReduxFeatureConfig.SKYFIELDS_TREES);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_TREES = copyKey(ReduxFeatureConfig.SHRUBLANDS_TREES);
    public static final ResourceKey<PlacedFeature> SHIMMERING_TREES = createKey(Folders.TREE + "shimmering_trees");
    public static final ResourceKey<PlacedFeature> IRIDIA_PATCH  = copyKey(ReduxFeatureConfig.IRIDIA_PATCH);
    public static final ResourceKey<PlacedFeature> XAELIA_PATCH  = copyKey(ReduxFeatureConfig.XAELIA_PATCH);
    public static final ResourceKey<PlacedFeature> LARGE_MUSHROOMS = copyKey(ReduxFeatureConfig.LARGE_MUSHROOMS);
    public static final ResourceKey<PlacedFeature> LUMINA_PATCH = copyKey(ReduxFeatureConfig.LUMINA_PATCH);
    public static final ResourceKey<PlacedFeature> LIGHTROOTS = copyKey(ReduxFeatureConfig.LIGHTROOTS);
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_ORE  = createKey(ORE + name(AetherBlocks.MOSSY_HOLYSTONE) + "_ore");
    public static final ResourceKey<PlacedFeature> MOSSY_ROCK  = copyKey(ReduxFeatureConfig.MOSSY_ROCK);
    public static final ResourceKey<PlacedFeature> ICESTONE_ROCK  = copyKey(ReduxFeatureConfig.ICESTONE_ROCK);
    public static final ResourceKey<PlacedFeature> WYNDSPROUTS_PATCH = copyKey(ReduxFeatureConfig.WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GENESIS_WYNDSPROUTS_PATCH = copyKey(ReduxFeatureConfig.GENESIS_WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GENESIS_SKYSPROUTS_PATCH = copyKey(ReduxFeatureConfig.GENESIS_SKYSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> SPIROLYCTIL_PATCH  = copyKey(ReduxFeatureConfig.SPIROLYCTIL_PATCH);
    public static final ResourceKey<PlacedFeature> JELLYSHROOM_PATCH = copyKey(ReduxFeatureConfig.JELLYSHROOM_PATCH);
    public static final ResourceKey<PlacedFeature> SURFACE_RULE_WATER_LAKE = copyKey(ReduxFeatureConfig.SURFACE_RULE_WATER_LAKE);
    public static final ResourceKey<PlacedFeature> BLIGHT_LAKE = copyKey(ReduxFeatureConfig.BLIGHT_LAKE);
    public static final ResourceKey<PlacedFeature> BLIGHT_SPRING = copyKey(ReduxFeatureConfig.BLIGHT_SPRING);
    public static final ResourceKey<PlacedFeature> OASIS_LAKE = createKey(Folders.SURFACE + "oasis_lake");
    public static final ResourceKey<PlacedFeature> VERIDIUM_ORE = copyKey(ReduxFeatureConfig.VERIDIUM_ORE);
    public static final ResourceKey<PlacedFeature> DIVINITE_ORE = copyKey(ReduxFeatureConfig.DIVINITE_ORE);
    public static final ResourceKey<PlacedFeature> SENTRITE_ORE = copyKey(ReduxFeatureConfig.SENTRITE_ORE);
    public static final ResourceKey<PlacedFeature> DIVINITE_ORE_INCREASED = createKey(Folders.ORE + "divinite_ore_increased");
    public static final ResourceKey<PlacedFeature> HOLYSILT_DISK = copyKey(ReduxFeatureConfig.HOLYSILT_DISK);
    public static final ResourceKey<PlacedFeature> AEROGEL_DISK = copyKey(ReduxFeatureConfig.AEROGEL_DISK);
    public static final ResourceKey<PlacedFeature> CLOUD_LAYER = copyKey(ReduxFeatureConfig.CLOUD_LAYER);

    public static final ResourceKey<PlacedFeature> AETHER_SNOW_LAYER = copyKey(ReduxFeatureConfig.AETHER_SNOW_LAYER);

    public static final ResourceKey<PlacedFeature> ANCIENT_ENCHANTED_GRASS = copyKey(ReduxFeatureConfig.ANCIENT_ENCHANTED_GRASS);

    public static final ResourceKey<PlacedFeature> BONEMEAL_OVERRIDE = AetherPlacedFeatures.AETHER_GRASS_BONEMEAL;

    private static ResourceKey<PlacedFeature> createKey(String name) {
        return ResourceKey.create(BuiltinRegistries.PLACED_FEATURE.key(), new ResourceLocation(Redux.MODID, name));
    }
    private static ResourceKey<PlacedFeature> aetherKey(String name) {
        return ResourceKey.create(BuiltinRegistries.PLACED_FEATURE.key(), new ResourceLocation(Aether.MODID, name));
    }
    private static String name(RegistryObject<?> reg)
    {
        return reg.getId().getPath();
    }

    private static ResourceKey<PlacedFeature> copyKey(ResourceKey<ConfiguredFeature<?, ?>> configFeat)
    {
        return createKey(configFeat.location().getPath());
    }

}
