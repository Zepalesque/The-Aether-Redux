package net.zepalesque.redux.compat.jade;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.EntityType;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.Map;
import java.util.function.Supplier;

public class ModNameUtils {
    
    public static final Map<EntityType<?>, Supplier<Boolean>> ENTITIES;
    
    static {
        ImmutableMap.Builder<EntityType<?>, Supplier<Boolean>> builder = ImmutableMap.<EntityType<?>, Supplier<Boolean>>builder()
            .put(AetherEntityTypes.MOA.get(), ReduxConfig.CLIENT.moa_model_upgrade)
            .put(AetherEntityTypes.COCKATRICE.get(), ReduxConfig.CLIENT.cockatrice_model_upgrade)
            .put(AetherEntityTypes.SENTRY.get(), ReduxConfig.CLIENT.sentry_model_upgrade)
            .put(AetherEntityTypes.MIMIC.get(), () -> ReduxConfig.CLIENT.mimic_model_upgrade.get().shouldUseModern())
            .put(AetherEntityTypes.SHEEPUFF.get(), ReduxConfig.CLIENT.sheepuff_model_upgrade)
            .put(AetherEntityTypes.PHYG.get(), ReduxConfig.CLIENT.phyg_model_upgrade)
            .put(AetherEntityTypes.FLYING_COW.get(), ReduxConfig.CLIENT.flying_cow_model_upgrade);
        ENTITIES = builder.build();
    }
}
