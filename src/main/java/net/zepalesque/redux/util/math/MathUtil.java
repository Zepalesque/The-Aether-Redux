package net.zepalesque.redux.util.math;

import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;

public class MathUtil {


    public static int rgb(int r, int g, int b)
    {
        return FastColor.ARGB32.color(0, r, g, b);
    }

    public static float returnZeroWhenNegative(float ret, float test) {
        return test <= 0F ? 0F : ret;
    }

    public static double clampedInverp(double delta, double min, double max) {
        return Mth.clamp(Mth.inverseLerp(delta, min, max), min, max);
    }
    public static float clampedInverp(float delta, float min, float max) {
        return Mth.clamp(Mth.inverseLerp(delta, min, max), min, max);
    }
    public static int clampedLerpInt(float delta, int min, int max) {
        return Mth.clamp(Mth.lerpInt(delta, min, max), min, max);
    }

    public static int toNearestEven(float f)
    {
        int floored = Mth.floor(f);
        return (floored % 2) == 0 ? floored : floored + 1;
    }
    public static int toNearestEven(double d)
    {
        int floored = Mth.floor(d);
        return (floored % 2) == 0 ? floored : floored + 1;
    }

    public static float breathe(Entity entity, float partialTicks) { return breathe(entity, partialTicks, 1F, 1F, 0F); }

    public static float breatheOffs(Entity entity, float partialTicks, float offset) { return breathe(entity, partialTicks, 1F, 1F, offset); }

    public static float breathe(Entity entity, float partialTicks, float amplitude, float frequency, float offset) {
        return breatheBase(entity, partialTicks, 0.05F * amplitude, 0.075F * frequency, 0.8333F + (offset * ((float) Math.PI)));
    }

    public static float breatheBase(Entity entity, float partialTicks, float amplitude, float frequency, float offset) {
        float age = entity.tickCount + partialTicks;
        return (Mth.sin((age * frequency) + (float) (Math.PI * offset)) * amplitude);
    }

    public static float staggeredBreathe(Entity entity, float partialTicks) { return staggeredBreathe(entity, partialTicks, 1F, 1F, 0F, 1F, 1F); }

    public static float staggeredBreathe(Entity entity, float partialTicks, float amplitude, float frequency, float offset, float staggerIndex, float staggerAmount) {
        return staggeredBreatheBase(entity, partialTicks, 0.1F * amplitude, 0.05F * frequency, 0.8333F + (offset * ((float) Math.PI)), 7.3F * staggerIndex, 0.15F * staggerAmount);
    }

    public static float staggeredBreatheBase(@NotNull Entity entity, float partialTicks, float amplitude, float frequency, float offset, float staggerIndex, float staggerAmount) {
        float age = entity.tickCount + partialTicks;
        return ((1.0F - staggerAmount) * (Mth.sin((age * frequency) + (float) (Math.PI * offset)) * amplitude)) + (staggerAmount * (Mth.sin((age * frequency * staggerIndex) + (float) (Math.PI * offset)) * amplitude));
    }

    public static float cockatriceBreathing(@NotNull Entity entity, float partialTicks, float frequency) {
        float age = entity.tickCount + partialTicks;
        return (float) ((0.65 * Mth.sin(age * frequency)) + (0.03 * Mth.sin(age * frequency * 13)) + (0.012 * Mth.sin(age * frequency * 4.1F)) + (0.014 * Mth.sin(age * frequency * 27)));
    }

    public static float cockatriceBreathing(@NotNull Entity entity, float partialTicks) {
        return cockatriceBreathing(entity, partialTicks, 0.065F) * 0.1F;
    }

    public static float animCos(float pValue) {
        return Mth.cos((float) ((2 * pValue) + (Math.PI))) - 1F;
    }

    /**
     * Cosine interpolation, for smoother movement through actions
     */
    public static float costrp(float progress, float start, float end) {
        return (((-Mth.cos((float) (Math.PI * progress)) + 1F) * 0.5F) * (end - start)) + start;
    }
    /**
     * Cosine interpolation, for smoother movement through actions, double edition(TM)
     */
    public static double costrp(double progress, double start, double end) {
        return (((-Mth.cos((float) (Math.PI * progress)) + 1F) * 0.5F) * (end - start)) + start;
    }

    /**
     * Curved interpolation, using a cosine function mixed with a linear one
     */
    public static float cerp(float progress, float start, float end, float curvedness) {
        return (progress * (1F - curvedness)) + (costrp(progress, start, end) * curvedness);
    }
    /**
     * Curved interpolation, using a cosine function mixed with a linear one, double edition(TM)
     */
    public static double cerp(double progress, double start, double end, double curvedness) {
        return (progress * (1F - curvedness)) + (costrp(progress, start, end) * curvedness);
    }

    public static double nextDouble(double bounds, RandomSource random) {
        return random.nextDouble() * bounds;
    }
    public static float nextFloat(float bounds, RandomSource random) {
        return random.nextFloat() * bounds;
    }
    public static double nextDouble(double min, double max, RandomSource random) {
        return min + (random.nextDouble() * (max - min));
    }
    public static float nextFloat(float min, float max, RandomSource random) {
        return min + (random.nextFloat() * (max - min));
    }

}
