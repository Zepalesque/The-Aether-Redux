package net.zepalesque.redux.data.resource;

import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.zenith.Zenith;
import net.zepalesque.zenith.api.condition.Condition;
import net.zepalesque.zenith.api.condition.ConfigCondition;
import net.zepalesque.zenith.api.condition.ModLoadedCondition;

public class ReduxConditions {

    public static final ResourceKey<Condition<?>> DEEP = createKey("deep_aether");
    public static final ResourceKey<Condition<?>> GENESIS = createKey("aether_genesis");
    public static final ResourceKey<Condition<?>> LOST = createKey("lost_content");
    public static final ResourceKey<Condition<?>> ANCIENT = createKey("ancient_aether");

    public static final ResourceKey<Condition<?>> SKY_COLORS = createKey("sky_colors");
    public static final ResourceKey<Condition<?>> CLOUDBED = createKey("cloudbed");

    public static void bootstrap(BootstapContext<Condition<?>> context) {
        context.register(DEEP, new ModLoadedCondition("deep_aether"));
        context.register(GENESIS, new ModLoadedCondition("aether_genesis"));
        context.register(LOST, new ModLoadedCondition("lost_aether_content"));
        context.register(ANCIENT, new ModLoadedCondition("ancient_aether"));
        context.register(SKY_COLORS, new ConfigCondition(ReduxConfig.SERVER.serializerID(), ReduxConfig.SERVER.redux_sky_colors));
        context.register(CLOUDBED, new ConfigCondition(ReduxConfig.SERVER.serializerID(), ReduxConfig.SERVER.cloudbed));
    }

    private static ResourceKey<Condition<?>> createKey(String name) {
        return ResourceKey.create(Zenith.Keys.CONDITION, Zenith.loc(name));
    }
}
