package net.zepalesque.redux.api.biomemodifier;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.world.biome.modifier.AetherGrassVanillifier;
import net.zepalesque.redux.world.biome.modifier.ReduxBiomeModifierSerializers;

import java.util.HashMap;
import java.util.Map;

public record VanillifyGrassOverride(HolderSet<Biome> biomes, int grass, int foliage) implements BiomeModifier {

    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.BEFORE_EVERYTHING && biomes.contains(biome)) {
            AetherGrassVanillifier.colors.put(biome, Pair.of(grass, foliage));
        }

    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierSerializers.VANILLIFY_OVERRIDE.get();
    }
}
