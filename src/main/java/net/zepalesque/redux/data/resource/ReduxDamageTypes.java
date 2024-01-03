package net.zepalesque.redux.data.resource;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.Redux;

import javax.annotation.Nullable;

public class ReduxDamageTypes {
    public static final ResourceKey<DamageType> CHROMATIC_SHRUB = createKey("chromatic_berry_bush");
    public static final ResourceKey<DamageType> BLIGHTED_FUNGI = createKey("blighted_fungi");
    public static final ResourceKey<DamageType> EMBER = createKey("ember");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(CHROMATIC_SHRUB, new DamageType("aether_redux.chromatic_shrub", 0.1F, DamageEffects.POKING));
        context.register(BLIGHTED_FUNGI, new DamageType("aether_redux.blighted_fungi", 0.1F, DamageEffects.POKING));
        context.register(EMBER, new DamageType("aether_redux.ember", 0.1F, DamageEffects.BURNING));
    }

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(Redux.MODID, name));
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
