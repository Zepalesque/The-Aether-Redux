package net.zepalesque.redux.data.resource.biome.registry;

import com.google.common.collect.ImmutableMap;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;

public class ReduxBiomes {
    public static final ResourceKey<Biome> THE_BLIGHT = createKey("the_blight");
    public static final ResourceKey<Biome> FROSTED_FORESTS = createKey("frosted_forests");
    public static final ResourceKey<Biome> GLACIAL_TUNDRA = createKey("glacial_tundra");
    public static final ResourceKey<Biome> GILDED_GROVES = createKey("gilded_groves");
    public static final ResourceKey<Biome> GILDED_GRASSLANDS = createKey("gilded_grasslands");
    public static final ResourceKey<Biome> SKYFIELDS = createKey("skyfields");
    public static final ResourceKey<Biome> CLOUDCAPS = createKey("cloudcaps");
    public static final ResourceKey<Biome> SKYROOT_SHRUBLANDS = createKey("skyroot_shrublands");

    // TODO: Add to 2.1
    public static final ResourceKey<Biome> SHIMMERING_HILLS = createKey("shimmering_hills");
    public static final ResourceKey<Biome> QUICKSOIL_DUNES = createKey("quicksoil_dunes");
    public static final ResourceKey<Biome> QUICKSOIL_OASIS = createKey("quicksoil_oasis");

    private static ResourceKey<Biome> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.BIOMES.getRegistryKey(), Redux.locate(name));
    }


    public static final int AETHER_GRASS_COLOR = 0xADF9C4;
    public static final int GILDED_GRASS_COLOR = 0xFFED96;
    public static final int GILDED_GRASSLANDS_COLOR = 0xFFF5A3;
    public static final int BLIGHT_GRASS_COLOR = 0xD5BAFF;
    public static final int FROSTED_GRASS_COLOR = 0xCCF7FF;
    public static final int SKYFIELDS_GRASS_COLOR = 0xBFFFEC;
    public static final int SHRUBLANDS_GRASS_COLOR = 0xD7FFCC;
    public static final int CLOUDCAP_GRASS_COLOR = 0xC6E5FF;

    public static final int SHIMMERING_GRASS_COLOR = 0xBAF0FF;
    public static final int DUNES_GRASS_COLOR = 0xEEFFE0;
    public static final int OASIS_GRASS_COLOR = 0xD6FFD6;

    // Compat colors for grass tints
    public static final int FROZEN_GRASS_COLOR = 0xCBECFE;
    public static final int PALE_GRASS_COLOR = 0xC1E6CA;

    public static final int AERGLOW_GRASS_COLOR = 0xCFFFF1;
    public static final int AERLAVENDER_GRASS_COLOR = 0xBDEDCC;
    public static final int BLUE_AERGLOW_GRASS_COLOR = 0xCEFFFD;
    public static final int MYSTIC_AERGLOW_GRASS_COLOR = 0xD1FFE7;




    public static final ImmutableMap<TagKey<Biome>, Integer> OVERWORLD_BIOME_AETHER_GRASS_COLORS = new ImmutableMap.Builder<TagKey<Biome>, Integer>()
            .put(Tags.Biomes.IS_COLD, FROSTED_GRASS_COLOR)
            .put(Tags.Biomes.IS_DESERT, OASIS_GRASS_COLOR)
            .put(Tags.Biomes.IS_LUSH, SKYFIELDS_GRASS_COLOR)
            .put(Tags.Biomes.IS_MUSHROOM, CLOUDCAP_GRASS_COLOR)
            .put(Tags.Biomes.IS_MAGICAL, SHIMMERING_GRASS_COLOR)
            .put(Tags.Biomes.IS_PLATEAU, GILDED_GRASS_COLOR)
            .put(Tags.Biomes.IS_SPARSE, GILDED_GRASSLANDS_COLOR)
            .put(BiomeTags.IS_NETHER, DUNES_GRASS_COLOR)
            .put(BiomeTags.IS_END, BLIGHT_GRASS_COLOR)
            .build();


    public static final int WATER = 5403045;
    public static final int WATER_FOG = 791347;

    public static final int CRYSTAL_GRASS = 0xBFE5FF;
}