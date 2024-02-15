package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

import java.util.Optional;

public record GrassAndFoliageColorModifier(HolderSet<Biome> biomes,  int grass, int foliage) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome)) {
            builder.getSpecialEffects().grassColorOverride(grass);
            builder.getSpecialEffects().foliageColorOverride(foliage);
            biome.get().getSpecialEffects().grassColorOverride = Optional.of(grass);
            biome.get().getSpecialEffects().foliageColorOverride = Optional.of(foliage);
        }

    }
    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierCodecs.GRASS_AND_FOLIAGE.get();
    }
}
