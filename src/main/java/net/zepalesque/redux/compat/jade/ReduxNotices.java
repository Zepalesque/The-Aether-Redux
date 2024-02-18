package net.zepalesque.redux.compat.jade;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether_genesis.Genesis;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import java.util.Map;
import java.util.function.Supplier;

public enum ReduxNotices implements IEntityComponentProvider, IBlockComponentProvider {

    INSTANCE;

    private static final Map<EntityType<?>, Supplier<Boolean>> ENTITIES = new ImmutableMap.Builder<EntityType<?>, Supplier<Boolean>>()
            .put(AetherEntityTypes.MOA.get(), ReduxConfig.CLIENT.moa_model_upgrade)
            .put(AetherEntityTypes.COCKATRICE.get(), ReduxConfig.CLIENT.cockatrice_model_upgrade)
            .put(AetherEntityTypes.SENTRY.get(), ReduxConfig.CLIENT.sentry_model_upgrade)
            .put(AetherEntityTypes.MIMIC.get(),() -> ReduxConfig.CLIENT.mimic_model_upgrade.get().shouldUseModern())
            .put(AetherEntityTypes.SHEEPUFF.get(), ReduxConfig.CLIENT.sheepuff_model_upgrade)
            .put(AetherEntityTypes.PHYG.get(), ReduxConfig.CLIENT.phyg_model_upgrade)
            .put(AetherEntityTypes.FLYING_COW.get(), ReduxConfig.CLIENT.flying_cow_model_upgrade)
            .build();

    private static final Component ENTITY_TOOLTIP = Component.translatable("gui.aether_redux.jade.entity_model").withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC);

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        EntityType<?> type = entityAccessor.getEntity().getType();
        if (ENTITIES.containsKey(type) && ((ReduxConfig.CLIENT.override_model_upgrades.get() && !isMimic(type)) || ENTITIES.get(type).get())) {
            iTooltip.add(ENTITY_TOOLTIP);
        }
    }

    private boolean isMimic(EntityType<?> type) {
        return type == AetherEntityTypes.MIMIC.get() || (Redux.aetherGenesisCompat() && type == GenesisEntityTypes.SKYROOT_MIMIC.get());
    }

    private static final ResourceLocation id = Redux.locate("modification_notices");

    @Override
    public ResourceLocation getUid() {
        return id;
    }



    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        // TODO: figure out if this is necessary
    }
}
