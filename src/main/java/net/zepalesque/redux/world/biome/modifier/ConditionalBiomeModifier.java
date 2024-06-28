package net.zepalesque.redux.world.biome.modifier;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.AbstractCondition;

public record ConditionalBiomeModifier(Holder<BiomeModifier> modifier, AbstractCondition<?> condition) implements BiomeModifier {
    
    public static final Codec<ConditionalBiomeModifier> CODEC = RecordCodecBuilder.create(builder -> builder.group(
            BiomeModifier.REFERENCE_CODEC.fieldOf("modify").forGetter(ConditionalBiomeModifier::modifier),
            AbstractCondition.CODEC.fieldOf("when").forGetter(ConditionalBiomeModifier::condition)
    ).apply(builder, ConditionalBiomeModifier::new));
    
    @Override
    public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
        if (!this.modifier().isBound()) {
            Redux.LOGGER.error("Failed to conditionally modify biome!");
            return;
        }

        if (this.condition().isConditionMet()) {
            this.modifier.value().modify(biome, phase, builder);
        }
    }
    @Override
    public Codec<? extends BiomeModifier> codec() {
        return CODEC;
    }
}
