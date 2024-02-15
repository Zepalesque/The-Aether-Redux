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
import net.zepalesque.redux.api.condition.AbstractCondition;

public class ReduxBiomeModifierCodecs {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> CODECS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Redux.MODID);

    static RegistryObject<Codec<WaterColorReplacementBiomeModifier>> REPLACE_WATER_COLOR = CODECS.register("water_color_replacement", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(WaterColorReplacementBiomeModifier::biomes),
                    WaterColorReplacementBiomeModifier.WaterColorPredicate.CODEC.fieldOf("predicate").forGetter(WaterColorReplacementBiomeModifier::predicate),
                    Codec.INT.fieldOf("water_color").forGetter(WaterColorReplacementBiomeModifier::water),
                    Codec.INT.fieldOf("water_fog_color").forGetter(WaterColorReplacementBiomeModifier::fog),
                    AbstractCondition.CODEC.fieldOf("condition").forGetter(WaterColorReplacementBiomeModifier::condition)
            ).apply(builder, WaterColorReplacementBiomeModifier::new)));

    static RegistryObject<Codec<GrassAndFoliageColorModifier>> GRASS_AND_FOLIAGE = CODECS.register("grass_and_foliage", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(GrassAndFoliageColorModifier::biomes),
                    Codec.INT.fieldOf("grass_color").forGetter(GrassAndFoliageColorModifier::grass),
                    Codec.INT.fieldOf("foliage_color").forGetter(GrassAndFoliageColorModifier::foliage)
            ).apply(builder, GrassAndFoliageColorModifier::new)));




    static RegistryObject<Codec<CarverModifier>> CARVER = CODECS.register("add_carver", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(CarverModifier::biomes),
                    ConfiguredWorldCarver.CODEC.fieldOf("carver").forGetter(CarverModifier::carver)
            ).apply(builder, CarverModifier::new)));

    static RegistryObject<Codec<AetherGrassColorModifier>> AETHER_GRASS_COLOR = CODECS.register("aether_grass_color", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(AetherGrassColorModifier::biomes),
                    Codec.INT.fieldOf("grass_color").forGetter(AetherGrassColorModifier::grass)
            ).apply(builder, AetherGrassColorModifier::new)));

}