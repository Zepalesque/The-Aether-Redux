package net.zepalesque.redux.world.biome.surfacerule;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxConditionSources {
    public static final DeferredRegister<Codec<? extends SurfaceRules.ConditionSource>> CONDITIONS = DeferredRegister.create(Registry.CONDITION.key(), Redux.MODID);

    public static RegistryObject<Codec<? extends SurfaceRules.ConditionSource>> CONDITION_CONDITION = CONDITIONS.register("condition_condition", ConditionCondition.CODEC::codec);


}