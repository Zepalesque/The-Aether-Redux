package net.zepalesque.redux.world.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxPlacementModifiers {
    
    public static final DeferredRegister<PlacementModifierType<?>> MODIFIERS = DeferredRegister.create(Registry.PLACEMENT_MODIFIER_REGISTRY, Redux.MODID);
    
    
    public static final RegistryObject<PlacementModifierType<ConditionFilter>> DATA_CONDITION =
        MODIFIERS.register("data_condition", () -> () -> ConditionFilter.CODEC);
    
    
    // TODO: why the hell was i not using a deferred registry? if any issues arise then return to this tho
    
    public static void init() {}
    
    private static <T extends PlacementModifier> PlacementModifierType<T> register(ResourceLocation name, Codec<T> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> codec);
    }
}
