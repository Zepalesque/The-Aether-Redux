package net.zepalesque.redux.client.gui.component;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.client.gui.component.config.IDisplayPage;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class DynamicButton extends Button implements IDisplayPage {


    @Nullable Supplier<Boolean> activeSupplier = null;
    @Nullable Supplier<Boolean> visibleSupplier = null;
    public final int page;

    protected DynamicButton(int x, int y, int width, int height, Component message, OnPress onPress, CreateNarration createNarration, int page) {
        super(x, y, width, height, message, onPress, createNarration);
        this.page = page;
    }

    public DynamicButton(Builder builder, int page, @Nullable Component hoverText) {
        super(builder);
        this.page = page;
        if (hoverText != null) {
            this.setTooltip(Tooltip.create(hoverText));
        }
    }

    public DynamicButton setActiveSupplier(@Nullable Supplier<Boolean> activeSupplier) {
        this.activeSupplier = activeSupplier;
        return this;
    }
    public DynamicButton setVisibleSupplier(@Nullable Supplier<Boolean> visibleSupplier) {
        this.visibleSupplier = visibleSupplier;
        return this;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.activeSupplier != null) {
            this.active = this.activeSupplier.get();
        }
        if (this.visibleSupplier != null) {
            this.visible = this.visibleSupplier.get();
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public int getPage() {
        return this.page;
    }
}
