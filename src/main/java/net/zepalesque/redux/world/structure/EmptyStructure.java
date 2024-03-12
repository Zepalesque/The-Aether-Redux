package net.zepalesque.redux.world.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Optional;

public class EmptyStructure extends Structure {
    protected EmptyStructure(StructureSettings settings) {
        super(settings);
    }

    public static final Codec<EmptyStructure> CODEC = RecordCodecBuilder.create((builder) -> builder.group(settingsCodec(builder)).apply(builder, EmptyStructure::new));

    @Override
    public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        return Optional.empty();
    }

    @Override
    public StructureType<?> type() {
        return null;
    }
}