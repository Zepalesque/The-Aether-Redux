package net.zepalesque.redux.world.stateprov;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxStateProviders {
    public static final DeferredRegister<BlockStateProviderType<?>> PROVIDERS = DeferredRegister.create(Registries.BLOCK_STATE_PROVIDER_TYPE, Redux.MODID);

    public static RegistryObject<BlockStateProviderType<SimpleConditionAlternativeStateProvider>> SIMPLE_ALTERNATIVE = PROVIDERS.register("simple_alternative", () -> new BlockStateProviderType<>(SimpleConditionAlternativeStateProvider.CODEC));
    public static RegistryObject<BlockStateProviderType<AdvancedConditionAlternativeStateProvider>> ADVANCED_ALTERNATIVE = PROVIDERS.register("advanced_alternative", () -> new BlockStateProviderType<>(AdvancedConditionAlternativeStateProvider.CODEC));


}