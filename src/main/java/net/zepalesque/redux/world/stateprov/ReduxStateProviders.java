package net.zepalesque.redux.world.stateprov;

import com.mojang.serialization.Codec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.world.biome.surfacerule.ConditionCondition;

public class ReduxStateProviders {
    public static final DeferredRegister<BlockStateProviderType<?>> PROVIDERS = DeferredRegister.create(Registries.BLOCK_STATE_PROVIDER_TYPE, Redux.MODID);

    public static RegistryObject<BlockStateProviderType<SimpleConditionAlternativeStateProvider>> ALTERNATIVE = PROVIDERS.register("simple_alternative", () -> new BlockStateProviderType<>(SimpleConditionAlternativeStateProvider.CODEC));


}