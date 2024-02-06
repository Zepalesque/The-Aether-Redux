package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.api.condition.AbstractCondition;

public record GrassColorReplacementModifier(HolderSet<Biome> biomes, IntPredicate predicate, int grass) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome) &&
                builder.getSpecialEffects().getGrassColorOverride().isPresent() &&
                predicate.test(builder.getSpecialEffects().getGrassColorOverride().get()))
        { builder.getSpecialEffects().grassColorOverride(grass); }

    }

    public static class IntPredicate {

        public static Codec<IntPredicate> CODEC = RecordCodecBuilder.create((condition) ->
                condition.group(
                                Codec.INT.fieldOf("grass").forGetter((config) -> config.grass))
                        .apply(condition, IntPredicate::new));

        final int grass;
        IntPredicate(int grass) {
            this.grass = grass;
        }

        public static IntPredicate of(int grass) {
            return new IntPredicate(grass);
        }

        public boolean test(int grass) {
            return grass == this.grass;
        }
    }



    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierSerializers.REPLACE_PLANTS_COLOR.get();
    }
}
