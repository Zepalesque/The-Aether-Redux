package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
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

public class ReduxPlacedFeatures {

    public static final DungeonBlacklistFilter DUNGEON_BLACKLIST = new DungeonBlacklistFilter();
    public static final NoiseThresholdCountPlacement NOISE_THRESHOLD = NoiseThresholdCountPlacement.of(-0.8D, 5, 10);

    public static final ResourceKey<PlacedFeature> AEVELIUM_GRASSES_PATCH = copyKey(ReduxConfiguredFeatures.AEVELIUM_GRASSES_PATCH);
    public static final ResourceKey<PlacedFeature> AURUM_PATCH = copyKey(ReduxConfiguredFeatures.AURUM_PATCH);
    public static final ResourceKey<PlacedFeature> ZYATRIX_PATCH = copyKey(ReduxConfiguredFeatures.ZYATRIX_PATCH);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_PURPLE_PATCH = createKey(Folders.PATCH + "shrublands_purple_patch");
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_WHITE_PATCH = createKey(Folders.PATCH + "shrublands_white_patch");
    public static final ResourceKey<PlacedFeature> ZANBERRY_BUSH_PATCH = copyKey(ReduxConfiguredFeatures.ZANBERRY_BUSH_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_SPARSE_VEGETATION = createKey(Folders.CAVE + "blightmoss_sparse_vegetation");
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_VEGETATION = createKey(Folders.CAVE + "blightmoss_vegetation");
    public static final ResourceKey<PlacedFeature> FUNGAL_SPARSE_VEGETATION = createKey(Folders.CAVE + "fungal_sparse_vegetation");
    public static final ResourceKey<PlacedFeature> FUNGAL_VEGETATION = createKey(Folders.CAVE + "fungal_vegetation");
    public static final ResourceKey<PlacedFeature> BLIGHTSHADE_PATCH = copyKey(ReduxConfiguredFeatures.BLIGHTSHADE_PATCH);
    public static final ResourceKey<PlacedFeature> CORRUPTED_VINES_PATCH = copyKey(ReduxConfiguredFeatures.CORRUPTED_VINES_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHT_ROCK = copyKey(ReduxConfiguredFeatures.BLIGHT_ROCK);
    public static final ResourceKey<PlacedFeature> GLIMMERSTOOL_ROCK = copyKey(ReduxConfiguredFeatures.GLIMMERSTOOL_ROCK);
    public static final ResourceKey<PlacedFeature> DENSE_GRASS = createKey(Folders.PATCH + "dense_grass");
    public static final ResourceKey<PlacedFeature> GLIMMERSTOOL_PATCH = copyKey(ReduxConfiguredFeatures.GLIMMERSTOOL_PATCH);
    public static final ResourceKey<PlacedFeature> BLIGHT_TREES = copyKey(ReduxConfiguredFeatures.BLIGHT_TREES);
    public static final ResourceKey<PlacedFeature> CLOUDCAP_MUSHLING_PATCH = copyKey(ReduxConfiguredFeatures.CLOUDCAP_MUSHLING_PATCH);
    public static final ResourceKey<PlacedFeature> DAGGERBLOOM_PATCH = copyKey(ReduxConfiguredFeatures.DAGGERBLOOM_PATCH);
    public static final ResourceKey<PlacedFeature> THERATIP_PATCH = copyKey(ReduxConfiguredFeatures.THERATIP_PATCH);
    public static final ResourceKey<PlacedFeature> SPLITFERN_PATCH = copyKey(ReduxConfiguredFeatures.SPLITFERN_PATCH);
    public static final ResourceKey<PlacedFeature> AEROGEL_ORE = copyKey(ReduxConfiguredFeatures.AEROGEL_ORE);
    public static final ResourceKey<PlacedFeature> SPARSE_AEROGEL_ORE = createKey(Folders.ORE + "sparse_aerogel_ore");
    public static final ResourceKey<PlacedFeature> SPARSE_BLUE_AERCLOUD = createKey(Folders.MISC + "sparse_blue_aercloud");
    public static final ResourceKey<PlacedFeature> DENSE_BLUE_AERCLOUD = createKey(Folders.MISC + "dense_blue_aercloud");
    public static final ResourceKey<PlacedFeature> BLIGHTED_AERCLOUD = createKey(Folders.MISC + "blighted_aercloud");
    public static final ResourceKey<PlacedFeature> SPARSE_ZANITE_ORE = createKey(Folders.ORE + "sparse_zanite_ore");
    public static final ResourceKey<PlacedFeature> SPARSE_AMBROSIUM_ORE = createKey(Folders.ORE + "sparse_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> DENSE_ZANITE_ORE = createKey(Folders.ORE + "dense_zanite_ore");
    public static final ResourceKey<PlacedFeature> DENSE_AMBROSIUM_ORE = createKey(Folders.ORE + "dense_ambrosium_ore");
    public static final ResourceKey<PlacedFeature> LARGE_ICESTONE_CHUNK = copyKey(ReduxConfiguredFeatures.LARGE_ICESTONE_CHUNK);
    public static final ResourceKey<PlacedFeature> FROSTED_PURPLE_FLOWER_PATCH = copyKey(ReduxConfiguredFeatures.FROSTED_PURPLE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> FROSTED_TREES = copyKey(ReduxConfiguredFeatures.FROSTED_TREES);
    public static final ResourceKey<PlacedFeature> GLACIAL_TREES = copyKey(ReduxConfiguredFeatures.GLACIAL_TREES);
    public static final ResourceKey<PlacedFeature> GILDED_HOLYSTONE_ORE = copyKey(ReduxConfiguredFeatures.GILDED_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> BLIGHTMOSS_HOLYSTONE_ORE = copyKey(ReduxConfiguredFeatures.BLIGHTMOSS_HOLYSTONE_ORE);
    public static final ResourceKey<PlacedFeature> GILDED_ROCK  = copyKey(ReduxConfiguredFeatures.GILDED_ROCK);
    public static final ResourceKey<PlacedFeature> GILDED_WHITE_FLOWER_PATCH = copyKey(ReduxConfiguredFeatures.GILDED_WHITE_FLOWER_PATCH);
    public static final ResourceKey<PlacedFeature> GLOWSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.GLOWSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_CLOVER_PATCH = copyKey(ReduxConfiguredFeatures.GOLDEN_CLOVER_PATCH);
    public static final ResourceKey<PlacedFeature> GOLDEN_HEIGHTS_GILDED_HOLYSTONE_ORE = createKey(ORE + "golden_heights_" + name(ReduxBlocks.GILDED_HOLYSTONE) + "_ore");
    public static final ResourceKey<PlacedFeature> GROVE_TREES = copyKey(ReduxConfiguredFeatures.GROVE_TREES);
    public static final ResourceKey<PlacedFeature> GRASSLAND_TREES = copyKey(ReduxConfiguredFeatures.GRASSLAND_TREES);
    public static final ResourceKey<PlacedFeature> GROVE_TREES_ALT = copyKey(ReduxConfiguredFeatures.GROVE_TREES_ALT);
    public static final ResourceKey<PlacedFeature> GRASSLAND_TREES_ALT = copyKey(ReduxConfiguredFeatures.GRASSLAND_TREES_ALT);
    public static final ResourceKey<PlacedFeature> SKYFIELDS_ROCK = copyKey(ReduxConfiguredFeatures.SKYFIELDS_ROCK);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_ROCK  = copyKey(ReduxConfiguredFeatures.SHRUBLANDS_ROCK);
    public static final ResourceKey<PlacedFeature> SKYSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.SKYSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> SKYFIELDS_TREES = copyKey(ReduxConfiguredFeatures.SKYFIELDS_TREES);
    public static final ResourceKey<PlacedFeature> CLASSIC_SKYFIELDS_TREES = copyKey(ReduxConfiguredFeatures.CLASSIC_SKYFIELDS_TREES);
    public static final ResourceKey<PlacedFeature> SHRUBLANDS_TREES = copyKey(ReduxConfiguredFeatures.SHRUBLANDS_TREES);
    public static final ResourceKey<PlacedFeature> SHIMMERING_TREES = createKey(Folders.TREE + "shimmering_trees");
    public static final ResourceKey<PlacedFeature> IRIDIA_PATCH  = copyKey(ReduxConfiguredFeatures.IRIDIA_PATCH);
    public static final ResourceKey<PlacedFeature> XAELIA_PATCH  = copyKey(ReduxConfiguredFeatures.XAELIA_PATCH);
    public static final ResourceKey<PlacedFeature> LARGE_MUSHROOMS = copyKey(ReduxConfiguredFeatures.LARGE_MUSHROOMS);
    public static final ResourceKey<PlacedFeature> LUMINA_PATCH = copyKey(ReduxConfiguredFeatures.LUMINA_PATCH);
    public static final ResourceKey<PlacedFeature> LIGHTROOTS = copyKey(ReduxConfiguredFeatures.LIGHTROOTS);
    public static final ResourceKey<PlacedFeature> MOSSY_HOLYSTONE_ORE  = createKey(ORE + name(AetherBlocks.MOSSY_HOLYSTONE) + "_ore");
    public static final ResourceKey<PlacedFeature> MOSSY_ROCK  = copyKey(ReduxConfiguredFeatures.MOSSY_ROCK);
    public static final ResourceKey<PlacedFeature> ICESTONE_ROCK  = copyKey(ReduxConfiguredFeatures.ICESTONE_ROCK);
    public static final ResourceKey<PlacedFeature> WYNDSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GENESIS_WYNDSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.GENESIS_WYNDSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> GENESIS_SKYSPROUTS_PATCH = copyKey(ReduxConfiguredFeatures.GENESIS_SKYSPROUTS_PATCH);
    public static final ResourceKey<PlacedFeature> SPIROLYCTIL_PATCH  = copyKey(ReduxConfiguredFeatures.SPIROLYCTIL_PATCH);
    public static final ResourceKey<PlacedFeature> JELLYSHROOM_PATCH = copyKey(ReduxConfiguredFeatures.JELLYSHROOM_PATCH);
    public static final ResourceKey<PlacedFeature> SURFACE_RULE_WATER_LAKE = copyKey(ReduxConfiguredFeatures.SURFACE_RULE_WATER_LAKE);
    public static final ResourceKey<PlacedFeature> BLIGHT_LAKE = copyKey(ReduxConfiguredFeatures.BLIGHT_LAKE);
    public static final ResourceKey<PlacedFeature> BLIGHT_SPRING = copyKey(ReduxConfiguredFeatures.BLIGHT_SPRING);
    public static final ResourceKey<PlacedFeature> OASIS_LAKE = createKey(Folders.SURFACE + "oasis_lake");
    public static final ResourceKey<PlacedFeature> VERIDIUM_ORE = copyKey(ReduxConfiguredFeatures.VERIDIUM_ORE);
    public static final ResourceKey<PlacedFeature> DIVINITE_ORE = copyKey(ReduxConfiguredFeatures.DIVINITE_ORE);
    public static final ResourceKey<PlacedFeature> SENTRITE_ORE = copyKey(ReduxConfiguredFeatures.SENTRITE_ORE);
    public static final ResourceKey<PlacedFeature> DIVINITE_ORE_INCREASED = createKey(Folders.ORE + "divinite_ore_increased");
    public static final ResourceKey<PlacedFeature> HOLYSILT_DISK = copyKey(ReduxConfiguredFeatures.HOLYSILT_DISK);
    public static final ResourceKey<PlacedFeature> AEROGEL_DISK = copyKey(ReduxConfiguredFeatures.AEROGEL_DISK);
    public static final ResourceKey<PlacedFeature> CLOUD_LAYER = copyKey(ReduxConfiguredFeatures.CLOUD_LAYER);

    public static final ResourceKey<PlacedFeature> AETHER_SNOW_LAYER = copyKey(ReduxConfiguredFeatures.AETHER_SNOW_LAYER);

    public static final ResourceKey<PlacedFeature> ANCIENT_ENCHANTED_GRASS = copyKey(ReduxConfiguredFeatures.ANCIENT_ENCHANTED_GRASS);
    
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
