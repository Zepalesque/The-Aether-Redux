package net.zepalesque.redux.world.density;

import com.mojang.datafixers.util.Function4;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.zepalesque.redux.util.math.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public record InterpolateFunction(DensityFunction lower, DensityFunction upper, int minBlendY, int maxBlendY, double minValue, double maxValue) implements DensityFunction {
    public static final KeyDispatchDataCodec<InterpolateFunction> INTERPOLATE_CODEC = createCodec(InterpolateFunction::create, InterpolateFunction::lower, InterpolateFunction::upper, InterpolateFunction::minBlendY, InterpolateFunction::maxBlendY);

    public double compute(FunctionContext context) {
        return transform(this.lower.compute(context), this.upper.compute(context), this.minBlendY, this.maxBlendY, context.blockY());
    }


    public void fillArray(double @NotNull [] pArray, @NotNull ContextProvider pContextProvider) {

        pContextProvider.fillAllDirectly(pArray, this);
    }

    public InterpolateFunction mapAll(Visitor pVisitor) {
        return create(this.lower.mapAll(pVisitor), this.upper.mapAll(pVisitor), this.minBlendY, this.maxBlendY);
    }

    public static InterpolateFunction create(DensityFunction pLower, DensityFunction pUpper, int minBlendY, int maxBlendY) {
        double minimum = Math.min(pLower.minValue(), pUpper.minValue());
        double maximum = Math.max(pLower.maxValue(), pUpper.maxValue());
        return new InterpolateFunction(pLower, pUpper, minBlendY, maxBlendY, minimum, maximum);
    }

    private static double transform(double funcLower, double funcUpper, int minY, int maxY, int currentY) {
        if (currentY < minY) {
            return funcLower;
        } else if (currentY > maxY) {
            return funcLower;
        }
        int aboveMin = currentY - minY;
        int range = maxY - minY;
        double progress = (double) aboveMin / (double) range;
        return MathUtil.costrp(progress, funcLower, funcUpper);
    }


    @Override
    public double minValue() {
        return this.minValue;
    }

    @Override
    public double maxValue() {
        return this.maxValue;
    }

    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return INTERPOLATE_CODEC;
    }

    static <O> KeyDispatchDataCodec<O> createCodec(Function4<DensityFunction, DensityFunction, Integer, Integer, O> creation, Function<O, DensityFunction> lower, Function<O, DensityFunction> upper, Function<O, Integer> min, Function<O, Integer> max) {
        return KeyDispatchDataCodec.of(RecordCodecBuilder.mapCodec(builder -> builder.group(DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lower").forGetter(lower), DensityFunction.HOLDER_HELPER_CODEC.fieldOf("upper").forGetter(upper), Codec.INT.fieldOf("min").forGetter(min), Codec.INT.fieldOf("max").forGetter(max)).apply(builder, creation)));
    }

}