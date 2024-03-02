package net.zepalesque.redux.world.stateprov;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.zepalesque.redux.api.condition.AbstractCondition;

public class SimpleConditionAlternativeStateProvider extends BlockStateProvider {
    private final BlockState base;
    private final AbstractCondition<?> condition;
    private final BlockState alternative;

    public static final Codec<SimpleConditionAlternativeStateProvider> CODEC = RecordCodecBuilder.create((condition) ->
            condition.group(
                    BlockState.CODEC.fieldOf("base_state").forGetter((alt) -> alt.base),
                    AbstractCondition.CODEC.fieldOf("condition").forGetter((alt) -> alt.condition),
                    BlockState.CODEC.fieldOf("alternative_state").forGetter((alt) -> alt.alternative))
                    .apply(condition, SimpleConditionAlternativeStateProvider::new));

    public SimpleConditionAlternativeStateProvider(BlockState base, AbstractCondition<?> condition, BlockState alternative) {
        this.base = base;
        this.condition = condition;
        this.alternative = alternative;
    }

    @Override
    public BlockState getState(RandomSource random, BlockPos pos) {
        return this.condition.isConditionMet() ? this.base : this.alternative;
    }
    protected BlockStateProviderType<?> type() {
        return ReduxStateProviders.SIMPLE_ALTERNATIVE.get();
    }

}
