package net.zepalesque.redux.mixin.common.compat;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether_genesis.entity.GenesisEntityTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.fml.ModList;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.addon.core.ModNameProvider;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.util.ModIdentification;

import java.util.Map;
import java.util.function.Supplier;

@Mixin(ModNameProvider.class)

public class ModNameProviderMixin {
    @Unique
    private static final Component ENTITY_TOOLTIP = Component.literal(" + " + Redux.DISPLAY).withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC);

    @Unique
    private static final Map<EntityType<?>, Supplier<Boolean>> ENTITIES = new ImmutableMap.Builder<EntityType<?>, Supplier<Boolean>>()
            .put(AetherEntityTypes.MOA.get(), ReduxConfig.CLIENT.moa_model_upgrade)
            .put(AetherEntityTypes.COCKATRICE.get(), ReduxConfig.CLIENT.cockatrice_model_upgrade)
            .put(AetherEntityTypes.SENTRY.get(), ReduxConfig.CLIENT.sentry_model_upgrade)
            .put(AetherEntityTypes.MIMIC.get(),() -> ReduxConfig.CLIENT.mimic_model_upgrade.get().shouldUseModern())
            .put(AetherEntityTypes.SHEEPUFF.get(), ReduxConfig.CLIENT.sheepuff_model_upgrade)
            .put(AetherEntityTypes.PHYG.get(), ReduxConfig.CLIENT.phyg_model_upgrade)
            .put(AetherEntityTypes.FLYING_COW.get(), ReduxConfig.CLIENT.flying_cow_model_upgrade)
            .build();

    @Inject(method = "appendTooltip(Lsnownee/jade/api/ITooltip;Lsnownee/jade/api/EntityAccessor;Lsnownee/jade/api/config/IPluginConfig;)V", at = @At(value = "TAIL"), remap = false)
    protected void tooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config, CallbackInfo ci) {
        EntityType<?> type = accessor.getEntity().getType();
        if (ENTITIES.containsKey(type) && ((ReduxConfig.CLIENT.enable_all_model_upgrades.get() && !isMimic(type)) || ENTITIES.get(type).get())) {
            tooltip.append(ENTITY_TOOLTIP);
        }
    }

    @Unique
    private boolean isMimic(EntityType<?> type) {
        return type == AetherEntityTypes.MIMIC.get() || (Redux.aetherGenesisCompat() && type == GenesisEntityTypes.SKYROOT_MIMIC.get());
    }
}
