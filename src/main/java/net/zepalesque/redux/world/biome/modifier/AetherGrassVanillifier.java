package net.zepalesque.redux.world.biome.modifier;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

import java.util.HashMap;
import java.util.Map;

public record AetherGrassVanillifier(HolderSet<Biome> biomes, int defaultGrass, int defaultFoliage) implements BiomeModifier {

    public static final Map<Holder<Biome>, Pair<Integer, Integer>> colors = new HashMap<>();
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome)) {
            if (colors.containsKey(biome)) {
                Pair<Integer, Integer> pair = colors.get(biome);
                builder.getSpecialEffects().grassColorOverride(pair.getFirst()).foliageColorOverride(pair.getSecond());
            } else {
                builder.getSpecialEffects().grassColorOverride(defaultGrass).foliageColorOverride(defaultFoliage);
            }
        }

    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierSerializers.AETHER_GRASS_VANILLIFY.get();
    }
}
