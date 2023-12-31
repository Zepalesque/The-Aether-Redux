package net.zepalesque.redux.world.density;

import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public record SquareRootFunction(DensityFunction input, double minValue, double maxValue) implements DensityFunction {
    public static final KeyDispatchDataCodec<SquareRootFunction> SQRT_CODEC = singleFunctionArgumentCodec(SquareRootFunction::create, SquareRootFunction::input);

    public double compute(DensityFunction.@NotNull FunctionContext pContext) {
        return transform(this.input().compute(pContext));
    }


    public void fillArray(double @NotNull [] pArray, DensityFunction.@NotNull ContextProvider pContextProvider) {
        this.input().fillArray(pArray, pContextProvider);

        for(int i = 0; i < pArray.length; ++i) {
            pArray[i] = transform(pArray[i]);
        }

    }

    public @NotNull SquareRootFunction mapAll(DensityFunction.@NotNull Visitor pVisitor) {
        return create(this.input.mapAll(pVisitor));
    }

    public static SquareRootFunction create(DensityFunction pInput) {
        double d0 = pInput.minValue();
        double d1 = transform(d0);
        double d2 = transform(pInput.maxValue());
        return new SquareRootFunction(pInput, Math.max(0.0D, d0), Math.max(d1, d2));
    }

    private static double transform(double pValue) {
        return Math.sqrt(pValue);
    }


    @Override
    public double minValue() {
        return this.minValue;
    }

    @Override
    public double maxValue() {
        return this.maxValue;
    }

    @NotNull
    @Override
    public KeyDispatchDataCodec<? extends DensityFunction> codec() {
        return SQRT_CODEC;
    }



    static <A, O> KeyDispatchDataCodec<O> singleArgumentCodec(Codec<A> pCodec, Function<A, O> pFromFunction, Function<O, A> pToFunction) {
        return KeyDispatchDataCodec.of(pCodec.fieldOf("argument").xmap(pFromFunction, pToFunction));
    }

    static <O> KeyDispatchDataCodec<O> singleFunctionArgumentCodec(Function<DensityFunction, O> p_224043_, Function<O, DensityFunction> p_224044_) {
        return singleArgumentCodec(DensityFunction.HOLDER_HELPER_CODEC, p_224043_, p_224044_);
    }

}