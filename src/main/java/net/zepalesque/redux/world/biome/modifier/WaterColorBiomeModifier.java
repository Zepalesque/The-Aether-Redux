package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public record WaterColorBiomeModifier(HolderSet<Biome> biomes, int water, int fog) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome))
        { builder.getSpecialEffects().waterColor(water).waterFogColor(fog); }

    }

    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierSerializers.WATER_COLOR_CODEC.get();
    }
}
