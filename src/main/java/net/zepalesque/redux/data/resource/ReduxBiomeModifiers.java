package net.zepalesque.redux.data.resource;

import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.ForgeRegistries;
import net.zepalesque.redux.Redux;

public class ReduxBiomeModifiers {
    public static String FEATURE = "feature/";
    public static String MOB = "mob/";
    public static String MODIFY = "modify/";
    
    public static final ResourceKey<BiomeModifier> ADD_AETHER_CAVES = createKey(FEATURE + "aether_caves");
    public static final ResourceKey<BiomeModifier> ADD_BLIGHTED_CAVES = createKey(FEATURE + "blighted_caves");
    public static final ResourceKey<BiomeModifier> ADD_FUNGAL_CAVES = createKey(FEATURE + "fungal_caves");
    public static final ResourceKey<BiomeModifier> ADD_MOSSY_HOLYSTONE_ORE = createKey(FEATURE + "mossy_holystone_ore");
    public static final ResourceKey<BiomeModifier> ADD_MOSSY_ROCKS = createKey(FEATURE + "mossy_rocks");
    public static final ResourceKey<BiomeModifier> ADD_WYNDSPROUTS = createKey(FEATURE + "wyndsprouts");
    public static final ResourceKey<BiomeModifier> ADD_GENESIS_SPROUTS = createKey(FEATURE + "genesis_sprouts");
    public static final ResourceKey<BiomeModifier> ADD_VANILLA_SWET = createKey(MOB + "vanilla_swet");
    public static final ResourceKey<BiomeModifier> ADD_ENDERMAN = createKey(MOB + "enderman");
    public static final ResourceKey<BiomeModifier> ADD_VERIDIUM = createKey(FEATURE + "veridium_ore");
    public static final ResourceKey<BiomeModifier> ADD_DIVINITE = createKey(FEATURE + "divinite");
    public static final ResourceKey<BiomeModifier> ADD_SENTRITE = createKey(FEATURE + "sentrite");
    public static final ResourceKey<BiomeModifier> ADD_SNOW = createKey(FEATURE + "snow");
    public static final ResourceKey<BiomeModifier> MODIFY_MUSIC = createKey(MODIFY + "modify_music");
    public static final ResourceKey<BiomeModifier> WATER_COLOR_AETHER = createKey(MODIFY + "redux_water_colors");
    public static final ResourceKey<BiomeModifier> SKY_COLOR_AETHER = createKey(MODIFY + "redux_sky_colors");
    public static final ResourceKey<BiomeModifier> VANILLA_GRASS_OVERRIDE = createKey(MODIFY + "redux_vanilla_grasses");
    public static final ResourceKey<BiomeModifier> AETHER_GRASS_COLORS = createKey(MODIFY + "redux_aether_grasses");
    
    // ok so i wish i knew why single-line javadocs with three slashes aren't working smh my head
    /** Fix for RazorDevs/Deep-Aether<a href="https://github.com/RazorDevs/Deep-Aether/issues/271">#271</a> */
    public static final ResourceKey<BiomeModifier> FIX_DEEP_AETHER_ISSUE_271 = createKey(MODIFY + "fix_deep_aether_issue_271");
    
    
    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, Redux.locate(name));
    }
}
