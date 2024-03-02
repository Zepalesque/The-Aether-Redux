package net.zepalesque.redux.world.biome.modifier;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.flag.DataFlag;

import java.util.List;
import java.util.function.Function;

public class ReduxBiomeModifierCodecs {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> CODECS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Redux.MODID);

    static RegistryObject<Codec<WaterColorReplacementBiomeModifier>> REPLACE_WATER_COLOR = CODECS.register("water_color_replacement", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(WaterColorReplacementBiomeModifier::biomes),
                    WaterColorReplacementBiomeModifier.WaterColorPredicate.CODEC.fieldOf("predicate").forGetter(WaterColorReplacementBiomeModifier::predicate),
                    Codec.INT.fieldOf("water_color").forGetter(WaterColorReplacementBiomeModifier::water),
                    Codec.INT.fieldOf("water_fog_color").forGetter(WaterColorReplacementBiomeModifier::fog),
                    DataFlag.CODEC.fieldOf("flag").forGetter(WaterColorReplacementBiomeModifier::condition)
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

    public static final RegistryObject<Codec<ReduxSpawnsModifier>> MOB_SPAWN_CONFIG = CODECS.register("mob_spawn_config", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(ReduxSpawnsModifier::biomes),
                    // Allow either a list or single spawner, attempting to decode the list format first.
                    // Uses the better EitherCodec that logs both errors if both formats fail to parse.
                    new ExtraCodecs.EitherCodec<>(MobSpawnSettings.SpawnerData.CODEC.listOf(), MobSpawnSettings.SpawnerData.CODEC).xmap(
                            either -> either.map(Function.identity(), List::of), // convert list/singleton to list when decoding
                            list -> list.size() == 1 ? Either.right(list.get(0)) : Either.left(list) // convert list to singleton/list when encoding
                    ).fieldOf("spawners").forGetter(ReduxSpawnsModifier::spawners),
                    DataFlag.CODEC.fieldOf("flag").forGetter(ReduxSpawnsModifier::condition),
                    Codec.DOUBLE.fieldOf("charge").forGetter(ReduxSpawnsModifier::charge),
                    Codec.DOUBLE.fieldOf("energy_budget").forGetter(ReduxSpawnsModifier::energyBudget)
            ).apply(builder, ReduxSpawnsModifier::new))
    );

}