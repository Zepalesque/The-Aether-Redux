package net.zepalesque.redux.mixin.common.compat;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.compat.jade.ModNameUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.addon.core.ModNameProvider;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@Mixin(ModNameProvider.class)

public class ModNameProviderMixin {
    @Unique
    private static final Component ENTITY_TOOLTIP = Component.literal(" + " + Redux.DISPLAY).withStyle(ChatFormatting.BLUE, ChatFormatting.ITALIC);

    @Inject(method = "appendTooltip(Lsnownee/jade/api/ITooltip;Lsnownee/jade/api/EntityAccessor;Lsnownee/jade/api/config/IPluginConfig;)V", at = @At(value = "TAIL"), remap = false)
    protected void tooltip(ITooltip tooltip, EntityAccessor accessor, IPluginConfig config, CallbackInfo ci) {
        EntityType<?> type = accessor.getEntity().getType();
        if (ModNameUtils.ENTITIES.containsKey(type)) tooltip.append(ENTITY_TOOLTIP);
    }
}
