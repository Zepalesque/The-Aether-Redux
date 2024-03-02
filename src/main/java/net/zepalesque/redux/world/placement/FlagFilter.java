package net.zepalesque.redux.world.placement;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.zepalesque.redux.api.flag.DataFlag;
import net.zepalesque.redux.api.flag.Not;

import javax.annotation.Nonnull;

public class FlagFilter extends PlacementFilter {
    public static final Codec<FlagFilter> CODEC = RecordCodecBuilder.create(builder ->
            builder.group(DataFlag.CODEC.fieldOf("flag").forGetter(filter -> filter.flag)).apply(builder, FlagFilter::new));

    public final DataFlag<?> flag;

    private FlagFilter(DataFlag<?> pCondition) {
        this.flag = pCondition;
    }

    public static FlagFilter whenTrue(DataFlag<?> condition) {
        return new FlagFilter(condition);
    }
    public static FlagFilter whenFalse(DataFlag<?> condition) {
        return new FlagFilter(new Not<>(condition));
    }

    protected boolean shouldPlace(@Nonnull PlacementContext context, @Nonnull RandomSource random, @Nonnull BlockPos pos) {
        return this.flag.test();
    }

    @Nonnull
    public PlacementModifierType<?> type() {
        return ReduxPlacementModifiers.DATA_CONDITION;
    }
}


