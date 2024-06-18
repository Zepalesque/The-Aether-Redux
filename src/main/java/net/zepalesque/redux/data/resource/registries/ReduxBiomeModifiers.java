package net.zepalesque.redux.data.resource.registries;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxMusic;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.util.codec.CodecPredicates;
import net.zepalesque.zenith.world.biome.modifier.ConditionalBiomeModifier;
import net.zepalesque.zenith.world.biome.modifier.MusicModifier;
import net.zepalesque.zenith.world.biome.modifier.SkyModifier;

import java.util.Optional;

public class ReduxBiomeModifiers {
    public static final ResourceKey<BiomeModifier> ADD_CLOUDBED = createKey("add_cloudbed");
    public static final ResourceKey<BiomeModifier> SKY_COLOR_AETHER = createKey("modify_sky_color");
    public static final ResourceKey<BiomeModifier> MUSIC_MODIFY = createKey("modify_music");
    public static final ResourceKey<BiomeModifier> ADD_SENTRITE = createKey("add_sentrite");

    private static ResourceKey<BiomeModifier> createKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Redux.loc(name));
    }

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<ConfiguredWorldCarver<?>> carvers = context.lookup(Registries.CONFIGURED_CARVER);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);
        HolderGetter<Condition<?>> conditions = context.lookup(Zenith.Keys.CONDITION);


        BiomeModifier cloudbed = new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_CLOUDBED), HolderSet.direct(features.getOrThrow(ReduxPlacements.CLOUDBED)),
                GenerationStep.Decoration.RAW_GENERATION);
        context.register(ADD_CLOUDBED, new ConditionalBiomeModifier(Holder.direct(cloudbed), conditions.get(ReduxConditions.CLOUDBED).orElseThrow()));

        BiomeModifier sky = new SkyModifier(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_SKY_COLOR),
                CodecPredicates.DualInt.of(12632319, 9671612), 0x9FA4DD, 0xBEC4E5);
        context.register(SKY_COLOR_AETHER, new ConditionalBiomeModifier(Holder.direct(sky), conditions.get(ReduxConditions.SKY_COLORS).orElseThrow()));

        // TODO: MusicPredicate, with optional fields for each field of the Music class
        context.register(MUSIC_MODIFY, new MusicModifier(biomes.getOrThrow(ReduxTags.Biomes.MODIFY_MUSIC),
                Optional.empty(), Optional.of(CodecPredicates.DualInt.of(ReduxMusic.MUSIC_MIN, ReduxMusic.MUSIC_MAX)), Optional.of(false), Optional.empty(), Optional.empty(), Optional.empty()));

        context.register(ADD_SENTRITE, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(ReduxTags.Biomes.HAS_SENTRITE), HolderSet.direct(features.getOrThrow(ReduxPlacements.SENTRITE_ORE)),
                GenerationStep.Decoration.UNDERGROUND_ORES));


    }
}
