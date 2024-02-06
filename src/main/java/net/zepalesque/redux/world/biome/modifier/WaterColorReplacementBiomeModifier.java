package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.api.condition.AbstractCondition;

public record WaterColorReplacementBiomeModifier(HolderSet<Biome> biomes, WaterColorPredicate predicate, int water, int fog, AbstractCondition<?> condition) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome) && condition.isConditionMet() && predicate.test(builder.getSpecialEffects().waterColor(), builder.getSpecialEffects().getWaterFogColor()))
        { builder.getSpecialEffects().waterColor(water).waterFogColor(fog); }

    }

    public static class WaterColorPredicate {

        public static Codec<WaterColorPredicate> CODEC = RecordCodecBuilder.create((condition) ->
                    condition.group(
                            Codec.INT.fieldOf("water").forGetter((config) -> config.water),
                            Codec.INT.fieldOf("fog").forGetter((config) -> config.fog)
                            )
                        .apply(condition, WaterColorPredicate::new));

        final int water, fog;
        WaterColorPredicate(int water, int fog) {
            this.water = water;
            this.fog = fog;
        }

        public static WaterColorPredicate of(int water, int fog) {
            return new WaterColorPredicate(water, fog);
        }

        public boolean test(int water, int fog) {
            return water == this.water && fog == this.fog;
        }
    }



    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierSerializers.REPLACE_WATER_COLOR.get();
    }
}
