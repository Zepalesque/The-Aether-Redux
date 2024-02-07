package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.biomemodifier.VanillifyGrassOverride;
import net.zepalesque.redux.api.condition.AbstractCondition;

public class ReduxBiomeModifierSerializers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Redux.MODID);

    static RegistryObject<Codec<WaterColorBiomeModifier>> WATER_COLOR = BIOME_MODIFIER_SERIALIZERS.register("water_color", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(WaterColorBiomeModifier::biomes),
                    Codec.INT.fieldOf("water_color").forGetter(WaterColorBiomeModifier::water),
                    Codec.INT.fieldOf("water_fog_color").forGetter(WaterColorBiomeModifier::fog)
            ).apply(builder, WaterColorBiomeModifier::new)));




    static RegistryObject<Codec<WaterColorReplacementBiomeModifier>> REPLACE_WATER_COLOR = BIOME_MODIFIER_SERIALIZERS.register("water_color_replacement", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(WaterColorReplacementBiomeModifier::biomes),
                    WaterColorReplacementBiomeModifier.WaterColorPredicate.CODEC.fieldOf("predicate").forGetter(WaterColorReplacementBiomeModifier::predicate),
                    Codec.INT.fieldOf("water_color").forGetter(WaterColorReplacementBiomeModifier::water),
                    Codec.INT.fieldOf("water_fog_color").forGetter(WaterColorReplacementBiomeModifier::fog),
                    AbstractCondition.CODEC.fieldOf("condition").forGetter(WaterColorReplacementBiomeModifier::condition)
            ).apply(builder, WaterColorReplacementBiomeModifier::new)));

    static RegistryObject<Codec<PlantColorReplacementModifier>> REPLACE_PLANTS_COLOR = BIOME_MODIFIER_SERIALIZERS.register("plant_color_replacement", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(PlantColorReplacementModifier::biomes),
                    PlantColorReplacementModifier.PlantsColorPredicate.CODEC.fieldOf("predicate").forGetter(PlantColorReplacementModifier::predicate),
                    Codec.INT.fieldOf("grass_color").forGetter(PlantColorReplacementModifier::grass),
                    Codec.INT.fieldOf("foliage_color").forGetter(PlantColorReplacementModifier::foliage)
            ).apply(builder, PlantColorReplacementModifier::new)));


    static RegistryObject<Codec<GrassAndFoliageColorModifier>> GRASS_AND_FOLIAGE = BIOME_MODIFIER_SERIALIZERS.register("grass_and_foliage", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(GrassAndFoliageColorModifier::biomes),
                    Codec.INT.fieldOf("grass_color").forGetter(GrassAndFoliageColorModifier::grass),
                    Codec.INT.fieldOf("foliage_color").forGetter(GrassAndFoliageColorModifier::foliage)
            ).apply(builder, GrassAndFoliageColorModifier::new)));




    static RegistryObject<Codec<CarverModifier>> CARVER = BIOME_MODIFIER_SERIALIZERS.register("add_carver", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(CarverModifier::biomes),
                    ConfiguredWorldCarver.CODEC.fieldOf("carver").forGetter(CarverModifier::carver)
            ).apply(builder, CarverModifier::new)));

    static RegistryObject<Codec<AetherGrassColorModifier>> AETHER_GRASS_COLOR = BIOME_MODIFIER_SERIALIZERS.register("aether_grass_color", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(AetherGrassColorModifier::biomes),
                    Codec.INT.fieldOf("grass_color").forGetter(AetherGrassColorModifier::grass)
            ).apply(builder, AetherGrassColorModifier::new)));




    static RegistryObject<Codec<AetherGrassVanillifier>> AETHER_GRASS_VANILLIFY = BIOME_MODIFIER_SERIALIZERS.register("vanillify_aether_grass", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(AetherGrassVanillifier::biomes),
                    Codec.INT.fieldOf("default_grass_color").forGetter(AetherGrassVanillifier::defaultGrass),
                    Codec.INT.fieldOf("default_foliage_color").forGetter(AetherGrassVanillifier::defaultFoliage)
            ).apply(builder, AetherGrassVanillifier::new)));


    public static RegistryObject<Codec<VanillifyGrassOverride>> VANILLIFY_OVERRIDE = BIOME_MODIFIER_SERIALIZERS.register("aether_grass_vanillification_override", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(VanillifyGrassOverride::biomes),
                    Codec.INT.fieldOf("grass_color").forGetter(VanillifyGrassOverride::grass),
                    Codec.INT.fieldOf("foliage_color").forGetter(VanillifyGrassOverride::foliage)
            ).apply(builder, VanillifyGrassOverride::new)));


}