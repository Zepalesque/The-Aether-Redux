package net.zepalesque.redux.client.gui.component;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.client.gui.component.util.PageDisplay;
import net.zepalesque.redux.mixin.mixins.common.accessor.ConfigValueAccessor;

import java.util.List;

public class ConfigButtons {

    public static class Bool extends Button implements PageDisplay {

        private final ModConfigSpec.ConfigValue<Boolean> config;
        private final int page;
        private ModConfigSpec.ValueSpec valueSpec;

        protected Bool(Builder builder, ModConfigSpec.ConfigValue<Boolean> config, int page) {
            super(x, y, width, height, ((ConfigValueAccessor) config).redux$getSpec().get(config.getPath()));
            this.config = config;
            this.page = page;
            ModConfigSpec spec = ((ConfigValueAccessor) config).redux$getSpec();
            if (spec.get(config.getPath()) instanceof ModConfigSpec.ValueSpec value) {
                this.valueSpec = value;
                value.getTranslationKey()
                this.setTooltip(Tooltip.create(Component.));
            }
        }


        private static void flip(Button b) {
            if (b instanceof Bool booleanButton) {
                booleanButton.set(!booleanButton.get());
            }
        }

        public boolean get() {
            return config.get();
        }

        public void set(boolean value) {
            this.config.set(value);
            this.config.save();
        }

        @Override
        public int displayOn() {
            return this.page;
        }
    }





    public static <T> Component getTitle(ModConfigSpec.ConfigValue<T> config) {
        ModConfigSpec spec = ((ConfigValueAccessor) config).redux$getSpec();
        if (spec.get(config.getPath()) instanceof ModConfigSpec.ValueSpec value) {
            List<String> path = config.getPath();
            return value.getTranslationKey() == null ? Component.literal(path.get(path.size() - 1)) : Component.translatable(value.getTranslationKey())
        }

    }
}
