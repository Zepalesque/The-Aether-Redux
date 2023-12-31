package net.zepalesque.redux.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.zepalesque.redux.api.condition.AbstractCondition;

import javax.annotation.Nonnull;

public class ConditionFilter extends PlacementFilter {
    public static final Codec<ConditionFilter> CODEC = RecordCodecBuilder.create((filter) ->
            filter.group(AbstractCondition.CODEC.fieldOf("data_condition").forGetter(dcf -> dcf.condition)).apply(filter, ConditionFilter::new));

    public final AbstractCondition<?> condition;

    public ConditionFilter(AbstractCondition<?> pCondition) {
        this.condition = pCondition;
    }

    protected boolean shouldPlace(@Nonnull PlacementContext context, @Nonnull RandomSource random, @Nonnull BlockPos pos) {
        return this.condition.isConditionMet();
    }

    @Nonnull
    public PlacementModifierType<?> type() {
        return ReduxPlacementModifiers.DATA_CONDITION;
    }
}


