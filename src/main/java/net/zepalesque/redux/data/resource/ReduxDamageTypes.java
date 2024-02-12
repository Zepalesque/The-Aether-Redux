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
    public static final ResourceKey<DamageType> ZANBERRY_BUSH = createKey("zanberry_bush");
    public static final ResourceKey<DamageType> CORRUPTED_VINES = createKey("corrupted_vines");
    public static final ResourceKey<DamageType> EMBER = createKey("ember");
    public static final ResourceKey<DamageType> SPEAR = createKey("spear");
    public static final ResourceKey<DamageType> BLIGHT = createKey("blight");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(ZANBERRY_BUSH, new DamageType("aether_redux.zanberry_bush", 0.1F, DamageEffects.POKING));
        context.register(CORRUPTED_VINES, new DamageType("aether_redux.corrupted_vines", 0.1F, DamageEffects.POKING));
        context.register(EMBER, new DamageType("aether_redux.ember", 0.1F, DamageEffects.BURNING));
        context.register(SPEAR, new DamageType("aether_redux.spear", 0.1F));
        context.register(BLIGHT, new DamageType("aether_redux.blight", 0.1F));
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
