package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.api.condition.AbstractCondition;

public record PlantColorReplacementModifier(HolderSet<Biome> biomes, PlantsColorPredicate predicate, int grass, int foliage) implements BiomeModifier {
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (phase == Phase.AFTER_EVERYTHING && biomes.contains(biome) && builder.getSpecialEffects().getGrassColorOverride().isPresent() && builder.getSpecialEffects().getFoliageColorOverride().isPresent() && predicate.test(builder.getSpecialEffects().getGrassColorOverride().get(), builder.getSpecialEffects().getFoliageColorOverride().get()))
        { builder.getSpecialEffects().grassColorOverride(grass).foliageColorOverride(foliage); }

    }

    public static class PlantsColorPredicate {

        public static Codec<PlantsColorPredicate> CODEC = RecordCodecBuilder.create((condition) ->
                condition.group(
                                Codec.INT.fieldOf("grass").forGetter((config) -> config.grass),
                                Codec.INT.fieldOf("foliage").forGetter((config) -> config.foliage)
                        )
                        .apply(condition, PlantsColorPredicate::new));

        final int grass, foliage;
        PlantsColorPredicate(int grass, int foliage) {
            this.grass = grass;
            this.foliage = foliage;
        }

        public static PlantsColorPredicate of(int grass, int foliage) {
            return new PlantsColorPredicate(grass, foliage);
        }

        public boolean test(int grass, int foliage) {
            return grass == this.grass && foliage == this.foliage;
        }
    }



    @Override
    public Codec<? extends BiomeModifier> codec() {
        return ReduxBiomeModifierSerializers.REPLACE_PLANTS_COLOR.get();
    }
}
