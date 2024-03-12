package net.zepalesque.redux.data.resource;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.damagesource.IndirectEntityDamageSource;
import net.minecraft.world.entity.Entity;

import javax.annotation.Nullable;

public class ReduxDamageTypes {

    public static final DamageSource BLIGHT = (new DamageSource("aether_redux.blight")).bypassArmor();
    public static final DamageSource CORRUPTED_VINES = (new DamageSource("aether_redux.corrupted_vines"));
    public static final DamageSource ZANBERRY_BUSH = (new DamageSource("aether_redux.zanberry_bush"));

    public static DamageSource spear(Entity source, @Nullable Entity indirectEntity) {
        return (new IndirectEntityDamageSource("aether_redux.spear", source, indirectEntity)).setProjectile();
    }
    public static DamageSource swetAbsorption(Entity source) {
        return (new EntityDamageSource("aether_redux.swet_absorption", source)).bypassArmor();
    }
    public static DamageSource ember(Entity source, @Nullable Entity indirectEntity) {
        return (new IndirectEntityDamageSource("aether_redux.ember", source, indirectEntity)).setProjectile();
    }
}
