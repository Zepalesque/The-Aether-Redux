package net.zepalesque.redux.data.resource;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherConfiguredFeatures;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;


// TODO (maybe not the perfect spot for this but whatever) IntProvider-based straight Trunk placer thing (should really be a vanilla feature)
public class ReduxConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> AEVELIUM_GRASSES_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.AVELIUM) + "_grasses_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGAL_PATCH = createKey(Folders.CAVE + "fungal_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGAL_PATCH_BONEMEAL = createKey(Folders.CAVE + "fungal_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AURUM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.AURUM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZYATRIX_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.ZYATRIX) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ZANBERRY_BUSH_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.ZANBERRY_BUSH) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTED_SKYROOT_TREE = createKey(Folders.TREE + "blighted_skyroot_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_PATCH = createKey(Folders.CAVE + "blightmoss_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_PATCH_BONEMEAL = createKey(Folders.CAVE + "blightmoss_patch_bonemeal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_VEGETATION = createKey(Folders.CAVE + "blightmoss_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_PINE = createKey(Folders.TREE + "skyroot_pine");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FUNGAL_VEGETATION = createKey(Folders.CAVE + "fungal_vegetation");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTSHADE_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.BLIGHTSHADE) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTWILLOW_TREE = createKey(Folders.TREE + "blightwillow_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTED_AERCLOUD = createKey(Folders.MISC + "blighted_aercloud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_ROCK = createKey(Folders.SURFACE + "blight_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_TREES = createKey(Folders.TREE + "blight_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYROOT_BUSH = createKey(Folders.PATCH + "skyroot_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUDCAP_MUSHLING_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.CLOUDCAP_MUSHLING) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_GILDED_OAK_TREE = createKey(Folders.TREE + "fancy_gilded_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FANCY_GOLDEN_OAK_TREE = createKey(Folders.TREE + "fancy_golden_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DAGGERBLOOM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.DAGGERBLOOM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> THERATIP_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.THERATIP) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPLITFERN_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.SPLITFERN) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AEROGEL_ORE = createKey(Folders.ORE + name(AetherBlocks.AEROGEL) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTED_PURPLE_FLOWER_PATCH = createKey(Folders.PATCH + "frosted_purple_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FROSTED_TREES = createKey(Folders.TREE + "frosted_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIAL_TREES = createKey(Folders.TREE + "glacial_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORRUPTED_VINES_PATCH = createKey(Folders.PATCH + "corrupted_vines_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_HOLYSTONE_ORE = createKey(Folders.ORE + name(ReduxBlocks.GILDED_HOLYSTONE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHTMOSS_HOLYSTONE_ORE = createKey(Folders.ORE + name(ReduxBlocks.BLIGHTMOSS_HOLYSTONE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_ICESTONE_CHUNK = createKey(Folders.ORE + "large_icestone_chunk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_LEAF_PATCH  = createKey(Folders.PATCH + "gilded_leaf_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_OAK_TREE = createKey(Folders.TREE + "gilded_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_GILDED_OAK_TREE = createKey(Folders.TREE + "small_gilded_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_ROCK  = createKey(Folders.SURFACE + "gilded_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GILDED_WHITE_FLOWER_PATCH = createKey(Folders.PATCH + "gilded_white_flower_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.LUXWEED) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_CLOVER_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.GOLDEN_CLOVER) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_LEAF_PATCH = createKey(Folders.PATCH + "golden_leaf_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_TREES = createKey(Folders.TREE + "grove_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GROVE_TREES_ALT = createKey(Folders.TREE + "grove_trees_alt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSLAND_TREES = createKey(Folders.TREE + "grassland_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASSLAND_TREES_ALT = createKey(Folders.TREE + "grassland_trees_alt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYFIELDS_ROCK = createKey(Folders.SURFACE + "skyfields_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUBLANDS_ROCK  = createKey(Folders.SURFACE + "shrublands_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERSTOOL_ROCK = createKey(Folders.SURFACE + name(ReduxBlocks.SHIMMERSTOOL) + "_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLIMMERSTOOL_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.SHIMMERSTOOL) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLYSILT_DISK  = createKey(Folders.SURFACE + "holysilt_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AEROGEL_DISK  = createKey(Folders.SURFACE + "aerogel_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.SKYSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKYFIELDS_TREES = createKey(Folders.TREE + "skyfields_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHRUBLANDS_TREES = createKey(Folders.TREE + "shrublands_trees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IRIDIA_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.IRIDIA) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> XAELIA_PATCH  = createKey(Folders.PATCH + "xaelia_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_CLOUDCAP  = createKey(Folders.TREE + "large_cloudcap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LIGHTROOTS  = createKey(Folders.MISC + "lightroots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLACIA_TREE = createKey(Folders.TREE + "glacia_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_LEAF_TREE = createKey(Folders.TREE + "crystal_leaf_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_RARE_FRUIT_TREE = createKey(Folders.TREE + "crystal_rare_fruit_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_CRYSTAL_RARE_FRUIT_TREE = createKey(Folders.COMPAT + "purple_crystal_rare_fruit_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_MUSHROOMS = createKey(Folders.TREE + "large_mushrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_JELLYSHROOM = createKey(Folders.TREE + "large_jellyshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LUMINA_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.LUMINA) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_HOLYSTONE_ORE  = createKey(Folders.ORE + name(AetherBlocks.MOSSY_HOLYSTONE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MOSSY_ROCK  = createKey(Folders.SURFACE + "mossy_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ICESTONE_ROCK  = createKey(Folders.SURFACE + "icestone_rock");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELDSPROOT_TREE = createKey(Folders.TREE + "fieldsproot_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WYNDSPROUTS_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.WYNDSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GENESIS_WYNDSPROUTS_PATCH = createKey(Folders.PATCH + "genesis_" +  name(ReduxBlocks.WYNDSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GENESIS_SKYSPROUTS_PATCH = createKey(Folders.PATCH + "genesis_" + name(ReduxBlocks.SKYSPROUTS) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PURPLE_GLACIA_TREE = createKey(Folders.TREE + "purple_glacia_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPIROLYCTIL_PATCH  = createKey(Folders.PATCH + name(ReduxBlocks.SPIROLYCTIL) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> JELLYSHROOM_PATCH = createKey(Folders.PATCH + name(ReduxBlocks.JELLYSHROOM) + "_patch");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SURFACE_RULE_WATER_LAKE = createKey(Folders.SURFACE + "surface_rule_water_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_LAKE = createKey(Folders.SURFACE + "blight_lake");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLIGHT_SPRING = createKey(Folders.SURFACE + "blight_spring");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VERIDIUM_ORE = createKey(Folders.ORE + name(ReduxBlocks.VERIDIUM_ORE));
    public static final ResourceKey<ConfiguredFeature<?, ?>> DIVINITE_ORE = createKey(Folders.ORE + name(ReduxBlocks.DIVINITE) + "_ore");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SENTRITE_ORE = createKey(Folders.ORE + name(ReduxBlocks.SENTRITE) + "_ore");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CRYSTAL_TREE_OVERRIDE = AetherConfiguredFeatures.CRYSTAL_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLIDAY_TREE_OVERRIDE = AetherConfiguredFeatures.HOLIDAY_TREE_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> GOLDEN_OAK_TREE_OVERRIDE = AetherConfiguredFeatures.GOLDEN_OAK_TREE_CONFIGURATION;

    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH_OVERRIDE = AetherConfiguredFeatures.GRASS_PATCH_CONFIGURATION;
    public static final ResourceKey<ConfiguredFeature<?, ?>> TALL_GRASS_PATCH_OVERRIDE = AetherConfiguredFeatures.TALL_GRASS_PATCH_CONFIGURATION;

    public static final ResourceKey<ConfiguredFeature<?, ?>> GRASS_PATCH_BONEMEAL = createKey(Folders.MISC + "aether_grass_bonemeal");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ANCIENT_ENCHANTED_GRASS = createKey(Folders.COMPAT + "ancient_enchanted_grass");

    public static final ResourceKey<ConfiguredFeature<?, ?>> AETHER_SNOW_LAYER = createKey(Folders.SURFACE + "snow_layer");

    public static final ResourceKey<ConfiguredFeature<?, ?>> CLOUD_LAYER = createKey(Folders.MISC + "cloud_layer");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(BuiltinRegistries.CONFIGURED_FEATURE.key(), new ResourceLocation(Redux.MODID, name));
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> aetherKey(String name) {
        return ResourceKey.create(BuiltinRegistries.CONFIGURED_FEATURE.key(), new ResourceLocation(Aether.MODID, name));
    }
}
