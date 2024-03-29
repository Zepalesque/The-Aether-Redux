package net.zepalesque.redux.misc;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.PackSource;

import java.util.function.UnaryOperator;

public class ReduxPackSources {

    public static final PackSource OPTIONAL_DATAPACK = PackSource.create(decorateWithSource("pack.source.optional_datapack"), false);

    public static final PackSource AUTO_APPLY_RESOURCE = PackSource.create(decorateWithSource("pack.source.auto_apply_resource"), true);

    public static UnaryOperator<Component> decorateWithSource(String pTranslationKey) {
        Component component = Component.translatable(pTranslationKey);
        return (p_10539_) -> Component.translatable("pack.nameAndSource", p_10539_, component).withStyle(ChatFormatting.GRAY);
    }

}
