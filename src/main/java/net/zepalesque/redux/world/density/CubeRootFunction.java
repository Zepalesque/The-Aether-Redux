package net.zepalesque.redux.world.density;

import com.mojang.serialization.Codec;
import net.minecraft.util.KeyDispatchDataCodec;
import net.minecraft.world.level.levelgen.DensityFunction;

import java.util.function.Function;

public record CubeRootFunction(DensityFunction input, double minValue, double maxValue) implements DensityFunction {
    public static final KeyDispatchDataCodec<CubeRootFunction> CBRT_CODEC = singleFunctionArgumentCodec((densityFunction) -> {
        return CubeRootFunction.create(densityFunction);
    }, CubeRootFunction::input);

    public double compute(FunctionContext pContext) {
        return transform(this.input().compute(pContext));
    }


    public void fillArray(double[] pArray, ContextProvider pContextProvider) {
        this.input().fillArray(pArray, pContextProvider);

        for(int i = 0; i < pArray.length; ++i) {
            pArray[i] = transform(pArray[i]);
        }

    }

    public CubeRootFunction mapAll(Visitor pVisitor) {
        return create(this.input.mapAll(pVisitor));
    }

    public static CubeRootFunction create(DensityFunction pInput) {
        double d0 = pInput.minValue();
        double d1 = transform(d0);
        double d2 = transform(pInput.maxValue());
        return new CubeRootFunction(pInput, Math.max(0.0D, d0), Math.max(d1, d2));
    }

    private static double transform(double pValue) {
        return Math.cbrt(pValue);
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
        return CBRT_CODEC;
    }


    static <A, O> KeyDispatchDataCodec<O> singleArgumentCodec(Codec<A> pCodec, Function<A, O> pFromFunction, Function<O, A> pToFunction) {
        return KeyDispatchDataCodec.of(pCodec.fieldOf("argument").xmap(pFromFunction, pToFunction));
    }

    static <O> KeyDispatchDataCodec<O> singleFunctionArgumentCodec(Function<DensityFunction, O> p_224043_, Function<O, DensityFunction> p_224044_) {
        return singleArgumentCodec(DensityFunction.HOLDER_HELPER_CODEC, p_224043_, p_224044_);
    }

}