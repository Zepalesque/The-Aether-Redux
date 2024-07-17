package net.zepalesque.redux.world.biome.modifier;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.carver.ConfiguredWorldCarver;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.AbstractCondition;
import net.zepalesque.redux.util.function.CodecPredicates;

import java.util.List;
import java.util.function.Function;

public class ReduxBiomeModifierCodecs {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> CODECS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Redux.MODID);

    static RegistryObject<Codec<CarverModifier>> CARVER = CODECS.register("add_carver", () -> CarverModifier.CODEC);

    public static final RegistryObject<Codec<ReduxSpawnsModifier>> MOB_SPAWN_CONFIG = CODECS.register("mob_spawn_config", () -> ReduxSpawnsModifier.CODEC);

    public static final RegistryObject<Codec<AetherGrassColors>> AETHER_GRASSES = CODECS.register("aether_grass", () -> AetherGrassColors.CODEC);

    public static final RegistryObject<Codec<MusicModifier>> MUSIC = CODECS.register("modify_music", () -> MusicModifier.CODEC);
    public static final RegistryObject<Codec<WaterModifier>> WATER_COLOR = CODECS.register("water_colors", () -> WaterModifier.CODEC);
    public static final RegistryObject<Codec<SkiesModifier>> SKIES = CODECS.register("sky_colors", () -> SkiesModifier.CODEC);
    public static final RegistryObject<Codec<FoliageModifier>> GRASS_FOLIAGE_COLOR = CODECS.register("foliage_colors", () -> FoliageModifier.CODEC);
    public static final RegistryObject<Codec<ConditionalBiomeModifier>> CONDITIONAL_MODIFIER = CODECS.register("conditional", () -> ConditionalBiomeModifier.CODEC);
}