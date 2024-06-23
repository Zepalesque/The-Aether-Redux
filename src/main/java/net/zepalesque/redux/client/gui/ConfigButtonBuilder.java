package net.zepalesque.redux.client.gui;

import net.minecraft.client.gui.components.AbstractWidget;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ConfigButtonBuilder {

    private static Map<Class<?>, Supplier<? extends AbstractWidget>> BUTTON_BUILDERS = constructButtons();

    private static Map<Class<?>, Supplier<? extends AbstractWidget>> constructButtons() {
        Map<Class<?>, Supplier<? extends AbstractWidget>> map = new HashMap<>();

    }
}
