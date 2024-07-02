package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.util.holder.HolderUtil;
import net.zepalesque.redux.util.codec.ReduxCodecs;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record AetherGrassColors(Map<ResourceKey<Biome>, Integer> colorMap) implements BiomeModifier {

    public static Map<ResourceLocation, Integer> SERVER_MAP = new HashMap<>();

    public static final Codec<AetherGrassColors> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            ReduxCodecs.MAP_CODEC.fieldOf("colormap").forGetter(AetherGrassColors::colorMap)).apply(builder, AetherGrassColors::new));


    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        Optional<ResourceKey<Biome>> unwrapped = HolderUtil.unwrapKey(biome);
        if (phase == Phase.AFTER_EVERYTHING && unwrapped.isPresent()) {
            ResourceKey<Biome> key = unwrapped.get();
            if (colorMap.containsKey(key)) {
                SERVER_MAP.put(key.location(), colorMap.get(key));
            }
        }
    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
