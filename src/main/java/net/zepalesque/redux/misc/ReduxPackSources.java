package net.zepalesque.redux.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;

import java.util.function.UnaryOperator;

public class ReduxPackSources {

    public static final PackSource OPTIONAL_DATAPACK = decorating("pack.source.optional_datapack");

    public static final PackSource AUTO_APPLY_RESOURCE = decorating("pack.source.auto_apply_resource");

    public static UnaryOperator<Component> decorateWithSource(String pTranslationKey) {
        Component component = Component.translatable(pTranslationKey);
        return (p_10539_) -> Component.translatable("pack.nameAndSource", p_10539_, component).withStyle(ChatFormatting.GRAY);
    }

    static PackSource decorating(String source) {
        Component component = Component.translatable(source);
        return (p_10539_) -> Component.translatable("pack.nameAndSource", p_10539_, component).withStyle(ChatFormatting.GRAY);
    }
}