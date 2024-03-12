package net.zepalesque.redux.world.placement;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.zepalesque.redux.Redux;

public class ReduxPlacementModifiers {

    public static final PlacementModifierType<ConditionFilter> DATA_CONDITION =
            register(new ResourceLocation(Redux.MODID, "data_condition"), ConditionFilter.CODEC);

    public static void init() {
    }

    private static <T extends PlacementModifier> PlacementModifierType<T> register(ResourceLocation name, Codec<T> codec) {
        return Registry.register(Registry.PLACEMENT_MODIFIERS, name, () -> {
            return codec;
        });
    }
}