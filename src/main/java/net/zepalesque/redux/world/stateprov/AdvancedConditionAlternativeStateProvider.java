package net.zepalesque.redux.world.stateprov;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.zepalesque.redux.api.condition.AbstractCondition;

public class AdvancedConditionAlternativeStateProvider extends BlockStateProvider {
    private final BlockStateProvider base;
    private final AbstractCondition<?> condition;
    private final BlockStateProvider alternative;

    public static final Codec<AdvancedConditionAlternativeStateProvider> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(
                    BlockStateProvider.CODEC.fieldOf("base_provider").forGetter((alt) -> alt.base),
                    AbstractCondition.CODEC.fieldOf("condition").forGetter((alt) -> alt.condition),
                    BlockStateProvider.CODEC.fieldOf("alternative_provider").forGetter((alt) -> alt.alternative))
                    .apply(condition, AdvancedConditionAlternativeStateProvider::new));

    public AdvancedConditionAlternativeStateProvider(BlockStateProvider base, AbstractCondition<?> condition, BlockStateProvider alternative) {
        this.base = base;
        this.condition = condition;
        this.alternative = alternative;
    }

    @Override
    public BlockState getState(RandomSource random, BlockPos pos) {
        return (this.condition.isConditionMet() ? this.base : this.alternative).getState(random, pos);
    }
    protected BlockStateProviderType<?> type() {
        return ReduxStateProviders.ADVANCED_ALTERNATIVE.get();
    }

}
