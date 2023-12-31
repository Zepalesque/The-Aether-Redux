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

public class ReduxBiomeModifierSerializers {
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, Redux.MODID);

    static RegistryObject<Codec<WaterColorBiomeModifier>> WATER_COLOR_CODEC = BIOME_MODIFIER_SERIALIZERS.register("water_color", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(WaterColorBiomeModifier::biomes),
                    Codec.INT.fieldOf("water_color").forGetter(WaterColorBiomeModifier::water),
                    Codec.INT.fieldOf("water_fog_color").forGetter(WaterColorBiomeModifier::fog)
            ).apply(builder, WaterColorBiomeModifier::new)));

    static RegistryObject<Codec<CarverModifier>> CARVER_CODEC = BIOME_MODIFIER_SERIALIZERS.register("add_carver", () ->
            RecordCodecBuilder.create(builder -> builder.group(
                    Biome.LIST_CODEC.fieldOf("biomes").forGetter(CarverModifier::biomes),
                    ConfiguredWorldCarver.CODEC.fieldOf("carver").forGetter(CarverModifier::carver)
            ).apply(builder, CarverModifier::new)));
}