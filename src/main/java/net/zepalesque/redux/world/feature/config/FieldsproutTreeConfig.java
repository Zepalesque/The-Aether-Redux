package net.zepalesque.redux.world.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class FieldsproutTreeConfig implements FeatureConfiguration {
    public static final Codec<FieldsproutTreeConfig> CODEC = RecordCodecBuilder.create((mushroom) ->
            mushroom.group(BlockStateProvider.CODEC.fieldOf("leaf_provider").forGetter((config) -> config.leafProvider),
                            BlockStateProvider.CODEC.fieldOf("log_provider").forGetter((config) -> config.logProvider),
                            BlockStateProvider.CODEC.fieldOf("wood_provider").forGetter((config) -> config.woodProvider),
                            IntProvider.CODEC.fieldOf("vine_length").forGetter((config) -> config.vineLength),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("patch_xz_spread").orElse(7).forGetter(patchTreeDecorator -> patchTreeDecorator.patchXZSpread),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("patch_y_spread").orElse(3).forGetter(patchTreeDecorator -> patchTreeDecorator.patchYSpread),
                    ExtraCodecs.POSITIVE_INT.fieldOf("patch_tries").orElse(128).forGetter(patchTreeDecorator -> patchTreeDecorator.patchTries))
                    .apply(mushroom, FieldsproutTreeConfig::new));
    public final BlockStateProvider leafProvider;
    public final BlockStateProvider logProvider;
    public final BlockStateProvider woodProvider;
    public final IntProvider vineLength;

    public final int patchXZSpread;
    public final int patchYSpread;
    public final int patchTries;

    public FieldsproutTreeConfig(BlockStateProvider leafProvider, BlockStateProvider logProvider, BlockStateProvider woodProvider, IntProvider vineLength, int patchXZSpread, int patchYSpread, int patchTries) {
        this.leafProvider = leafProvider;
        this.logProvider = logProvider;
        this.woodProvider = woodProvider;
        this.vineLength = vineLength;
        this.patchXZSpread = patchXZSpread;
        this.patchYSpread = patchYSpread;
        this.patchTries = patchTries;
    }

}
