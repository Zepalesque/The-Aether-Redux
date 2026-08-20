package net.zepalesque.redux.util;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.NotNull;


// TODO: Remove when all mob remodels have been redone please lmfao
public final class LegacyAnimUtil {
	
	public static float breathe(Entity entity, float partialTicks) {
		return breathe(entity, partialTicks, 1F, 1F, 0F);
	}
	
	public static float breatheOffs(Entity entity, float partialTicks, float offset) {
		return breathe(entity, partialTicks, 1F, 1F, offset);
	}
	
	public static float breathe(Entity entity, float partialTicks, float amplitude, float frequency, float offset) {
		return breatheBase(entity, partialTicks, 0.05F * amplitude, 0.075F * frequency, 0.8333F + offset * (float) Math.PI);
	}
	
	public static float breatheBase(Entity entity, float partialTicks, float amplitude, float frequency, float offset) {
		var age = entity.tickCount + partialTicks;
		return Mth.sin(age * frequency + (float) (Math.PI * offset)) * amplitude;
	}
	
	public static float staggeredBreathe(Entity entity, float partialTicks) {
		return staggeredBreathe(entity, partialTicks, 1F, 1F, 0F, 1F, 1F);
	}
	
	public static float staggeredBreathe(Entity entity, float partialTicks, float amplitude, float frequency, float offset, float staggerIndex, float staggerAmount) {
		return staggeredBreatheBase(entity, partialTicks, 0.1F * amplitude, 0.05F * frequency, 0.8333F + offset * (float) Math.PI, 7.3F * staggerIndex, 0.15F * staggerAmount);
	}
	
	public static float staggeredBreatheBase(@NotNull Entity entity, float partialTicks, float amplitude, float frequency, float offset, float staggerIndex, float staggerAmount) {
		var age = entity.tickCount + partialTicks;
		return (1.0F - staggerAmount) * (Mth.sin(age * frequency + (float) (Math.PI * offset)) * amplitude) + staggerAmount * (Mth.sin(age * frequency * staggerIndex + (float) (Math.PI * offset)) * amplitude);
	}
	
	public static float cockatriceBreathing(@NotNull Entity entity, float partialTicks, float frequency) {
		var age = entity.tickCount + partialTicks;
		return (float) (0.65 * Mth.sin(age * frequency) + 0.03 * Mth.sin(age * frequency * 13) + 0.012 * Mth.sin(age * frequency * 4.1F) + 0.014 * Mth.sin(age * frequency * 27));
	}
	
	public static float cockatriceBreathing(@NotNull Entity entity, float partialTicks) {
		return cockatriceBreathing(entity, partialTicks, 0.065F) * 0.1F;
	}
	
	public static float animCos(float pValue) {
		return Mth.cos((float) (2 * pValue + Math.PI)) - 1F;
	}
}
