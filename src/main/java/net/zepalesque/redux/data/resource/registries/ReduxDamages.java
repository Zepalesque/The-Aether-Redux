package net.zepalesque.redux.data.resource.registries;

import javax.annotation.Nullable;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.Redux;

public final class ReduxDamages {
	public static final ResourceKey<DamageType> EMBER = createKey("ember");
	public static final ResourceKey<DamageType> SWET = createKey("swet_absorption");
	
	public static void bootstrap(BootstrapContext<DamageType> context) {
		context.register(EMBER, new DamageType("aether_redux.ember", 0.1F, DamageEffects.BURNING));
		context.register(SWET, new DamageType("aether_redux.swet_absorption", 0.1F));
	}
	
	private static ResourceKey<DamageType> createKey(String name) {
		return ResourceKey.create(Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath(Redux.MODID, name));
	}
	
	public static DamageSource source(Level level, ResourceKey<DamageType> key) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key));
	}
	
	public static DamageSource entitySource(Level level, ResourceKey<DamageType> key, @Nullable Entity entity) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), entity);
	}
	
	public static DamageSource indirectSource(Level level, ResourceKey<DamageType> key, @Nullable Entity source, @Nullable Entity trueSource) {
		return new DamageSource(level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(key), source, trueSource);
	}
}
