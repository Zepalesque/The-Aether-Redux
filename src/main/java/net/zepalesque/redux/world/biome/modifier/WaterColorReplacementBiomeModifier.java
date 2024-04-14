package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.api.condition.AbstractCondition;
import net.zepalesque.redux.util.function.CodecPredicates;

import java.util.Optional;

public record WaterColorReplacementBiomeModifier(HolderSet<Biome> biomes, CodecPredicates.DualInt predicate, int water, int fog, Optional<AbstractCondition<?>> condition) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome) && (condition.isEmpty() || condition.get().isConditionMet()) && predicate.test(builder.getSpecialEffects().waterColor(), builder.getSpecialEffects().getWaterFogColor()))
        { builder.getSpecialEffects().waterColor(water).waterFogColor(fog); }

    }

    public static Codec<CodecPredicates.DualInt> WATER_PREDICATE = CodecPredicates.DualInt.createCodec("water", "fog");



    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierCodecs.REPLACE_WATER_COLOR.get();
    }
}
