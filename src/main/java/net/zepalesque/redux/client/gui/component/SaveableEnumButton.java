package net.zepalesque.redux.client.gui.component;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.client.gui.screen.PackConfigMenu;

public class SaveableEnumButton<T extends Enum<T>> extends Button implements ISaveable, IDisplayPage {
    public final PackConfig<T> config;
    public final int page;
    private T value;
    private final PackConfigMenu menu;


    public SaveableEnumButton(int x, int y, int width, int height, PackConfig<T> config, int page, PackConfigMenu menu) {
        super(x, y, width, height, CommonComponents.EMPTY, SaveableEnumButton::cycle
        , Button.DEFAULT_NARRATION);
        this.config = config;
        this.value = config.get();
        this.page = page;
        this.menu = menu;
        if (config.hasComment()) {
            this.setTooltip(Tooltip.create(Component.translatable("gui.aether_redux.pack_config.config_desc." + config.id())));
        }
    }

    public static void cycle(Button b) {
        if (b instanceof SaveableEnumButton<?> enumButton) {
            enumButton.cycle();
        }
    }


    public void cycle()
    {
        T[] values = (T[]) this.value.getClass().getEnumConstants();
        int ordinal = (this.value.ordinal() + 1) % values.length;
        this.setValue(values[ordinal]);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.visible = this.menu.getCurrentPage() == this.page;
        this.active = this.menu.getCurrentPage() == this.page;
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }



    @Override
    public Component getMessage() {
        return Component.translatable("gui.aether_redux.pack_config.enums." + this.value.name());
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void save() {
        this.menu.markChanged(this.config.set(this.getValue()));
    }

    @Override
    public int getPage() {
        return this.page;
    }
}
