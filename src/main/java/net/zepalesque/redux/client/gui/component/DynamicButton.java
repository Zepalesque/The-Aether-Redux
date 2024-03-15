package net.zepalesque.redux.client.gui.component;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.client.gui.backported.SlicedButton;
import net.zepalesque.redux.client.gui.component.config.IDisplayPage;
import net.zepalesque.redux.client.gui.screen.config.PackConfigMenu;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

public class DynamicButton extends SlicedButton implements IDisplayPage {


    @Nullable Supplier<Boolean> activeSupplier = null;
    @Nullable Supplier<Boolean> visibleSupplier = null;
    public final int page;
    protected Component tooltip;
    protected final PackConfigMenu menu;

    public DynamicButton(int x, int y, int width, int height, PackConfigMenu menu, Component message, OnPress onPress, int page) {
        super(x, y, width, height, message, onPress);
        this.page = page;
        this.menu = menu;
    }

    public DynamicButton(int x, int y, int width, int height, PackConfigMenu menu, Component message, OnPress onPress, int page, @Nullable Component hoverText) {
        this(x, y, width, height, menu, message, onPress, page);
        if (hoverText != null) {
            this.tooltip = hoverText;
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
    public void render(PoseStack guiGraphics, int mouseX, int mouseY, float partialTick) {
        if (this.activeSupplier != null) {
            this.active = this.activeSupplier.get();
        }
        if (this.visibleSupplier != null) {
            this.visible = this.visibleSupplier.get();
        }
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        if (this.isHoveredOrFocused() && this.tooltip != null) {
            this.menu.renderTooltip(guiGraphics, List.of(this.tooltip), Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    public int getPage() {
        return this.page;
    }
}
