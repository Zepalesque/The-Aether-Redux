package net.zepalesque.redux.api.condition;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

import java.util.function.Supplier;

public class ConditionSerializers {


        public static Codec<? extends AbstractCondition<?>> get(ResourceLocation resourceLocation) {
        return CONDITION_SERIALIZER_REGISTRY.get().getValue(resourceLocation);

    }
    public static ResourceLocation getKey(Codec<? extends AbstractCondition<?>> condition) {
        return CONDITION_SERIALIZER_REGISTRY.get().getKey(condition);
    }

    public static final DeferredRegister<Codec<? extends AbstractCondition<?>>> CONDITION_SERIALIZERS = DeferredRegister.create(Redux.Keys.CONDITION_SERIALIZER, Redux.MODID);
    public static final Supplier<IForgeRegistry<Codec<? extends AbstractCondition<?>>>> CONDITION_SERIALIZER_REGISTRY = CONDITION_SERIALIZERS.makeRegistry(RegistryBuilder::new);

    public static final RegistryObject<Codec<ModLoaded>> MOD_LOADED = CONDITION_SERIALIZERS.register("mod_loaded", () -> ModLoaded.CODEC);
    public static final RegistryObject<Codec<ReduxConfigCondition>> REDUX_CONFIG = CONDITION_SERIALIZERS.register("redux_config", () -> ReduxConfigCondition.CODEC);
    public static final RegistryObject<Codec<AetherConfigCondition>> AETHER_CONFIG = CONDITION_SERIALIZERS.register("aether_config", () -> AetherConfigCondition.CODEC);

    // Logic
    public static final RegistryObject<Codec<Not<?>>> NOT = CONDITION_SERIALIZERS.register("not", () -> Not.CODEC);
    public static final RegistryObject<Codec<Or<?, ?>>> OR = CONDITION_SERIALIZERS.register("or", () -> Or.CODEC);
    public static final RegistryObject<Codec<And<?, ?>>> AND = CONDITION_SERIALIZERS.register("and", () -> And.CODEC);


}
