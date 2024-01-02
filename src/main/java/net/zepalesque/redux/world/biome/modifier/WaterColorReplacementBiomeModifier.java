package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

import java.util.function.Predicate;

public record WaterColorReplacementBiomeModifier(HolderSet<Biome> biomes, WaterColorPredicate predicate, int water, int fog) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome) && predicate.test(builder.getSpecialEffects().waterColor(), builder.getSpecialEffects().getWaterFogColor()))
        { builder.getSpecialEffects().waterColor(water).waterFogColor(fog); }

    }

    public static class WaterColorPredicate {

        public static Codec<WaterColorPredicate> CODEC = RecordCodecBuilder.create((condition) ->
                    condition.group(Codec.INT.fieldOf("water").forGetter((config) -> config.w), Codec.INT.fieldOf("fog").forGetter((config) -> config.f))
                        .apply(condition, WaterColorPredicate::new));

        final int w, f;
        WaterColorPredicate(int water, int fog) {
            w = water;
            f = fog;
        }

        public static WaterColorPredicate of(int water, int fog) {
            return new WaterColorPredicate(water, fog);
        }

        public boolean test(int water, int fog) {
            return water == w && fog == f;
        }
    }



    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierSerializers.REPLACE_WATER_COLOR_CODEC.get();
    }
}
