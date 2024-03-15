package net.zepalesque.redux.client.gui.screen.config;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.packconfig.Category;
import net.zepalesque.redux.api.packconfig.IConfigSaving;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.client.gui.backported.GuiGraphicsHelper;
import net.zepalesque.redux.client.gui.component.DynamicButton;
import net.zepalesque.redux.client.gui.component.DynamicImageButton;
import net.zepalesque.redux.client.gui.component.DynamicRenderableString;
import net.zepalesque.redux.client.gui.component.RenderableString;
import net.zepalesque.redux.client.gui.component.config.ISaveable;
import net.zepalesque.redux.client.gui.component.config.PageDependentString;
import net.zepalesque.redux.util.math.MathUtil;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.*;
import java.util.function.Supplier;

public class PackConfigMenu extends Screen {

    private final Map<Integer, List<IConfigSaving>> pages = new HashMap<>();
    private final Map<Category, PackConfigMenu> cachedMenus = new HashMap<>();
    public static final ResourceLocation LIGHT_DIRT_BACKGROUND = new ResourceLocation("textures/gui/light_dirt_background.png");
    public static final ResourceLocation PARENT_BUTTON_LOC = Redux.locate("textures/gui/config_menu/parent.png");
    public static final ResourceLocation BACK_BUTTON_LOC = Redux.locate("textures/gui/config_menu/back.png");
    public static final ResourceLocation NEXT_BUTTON_LOC = Redux.locate("textures/gui/config_menu/next.png");
    public static final ResourceLocation MENU_LOC = Redux.locate("textures/gui/config_menu/config_menu.png");
    public static final ResourceLocation LIST_LOC = Redux.locate("textures/gui/config_menu/config_bars.png");
    private final @Nullable PackConfigMenu parentScreen;
    private final Category category;
    private final Category top;
    private List<Category> categories;
    private List<PackConfig<?>> configs;
    private int currentPageNumber;
    private boolean valChanged = false;
    private boolean goingUp = false;
    private @Nullable PackSelectionScreen selSc = null;

    private final Collection<ISaveable> saveables = new ArrayList<>();

    public PackConfigMenu(Component title, Category base, @Nullable PackConfigMenu parent) {
        super(title);
        this.category = base;
        this.top = base.getRoot();
        this.parentScreen = parent;
    }

    public void setSelectionScreen(PackSelectionScreen screen)
    {
        this.selSc = screen;
    }

    public void markChanged(boolean shouldMark)
    {
        if (shouldMark) {
            if (this.parentScreen != null) {
                this.parentScreen.markChanged(true);
                this.markChildren(true);
            } else {
                this.valChanged = true;
            }
        }
    }
    public void markChildren(boolean shouldMark)
    {
        if (shouldMark) {
            this.cachedMenus.forEach((category1, menu) -> menu.markChildren(true));
        }
    }

    public boolean isMarked()
    {
        return this.valChanged;
    }


    public void render(PoseStack guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderDirtBackground(0);
        this.renderMenu(guiGraphics);
        this.renderList(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    public void goInto(Category cat, Minecraft mc)
    {
        PackConfigMenu menu;
        this.saveAll();
        if (this.cachedMenus.containsKey(cat)) {
            menu = this.cachedMenus.get(cat);
        } else {
            menu = new PackConfigMenu(this.title, cat, this);
            if (this.valChanged);
            menu.valChanged = true;
            this.cachedMenus.put(cat, menu);
        }
        mc.setScreen(menu);
    }

    public int getCurrentPage() {
        return currentPageNumber;
    }

    private void renderMenu(PoseStack guiGraphics) {
        GuiGraphicsHelper.blitNineSliced(MENU_LOC, guiGraphics,  this.centerXStart(this.frameWidth()), this.centerYStart(this.frameHeight()), this.frameWidth(), this.frameHeight(), 30, 41, 30, 31, 192, 192, 0, 0);
    }
    private void renderList(PoseStack guiGraphics) {
        int width = this.frameWidth() - 28;
        int height = this.frameHeight() - 72;
        RenderSystem.setShaderTexture(0, LIST_LOC);
        guiGraphics.pushPose();
        blit(guiGraphics, this.centerXStart(this.frameWidth()) + 14, this.centerYStart(this.frameHeight()) + 41, width, height, 0.0F, 0.0F, width / 3, height / 3, 16, 16);
        guiGraphics.popPose();
    }



    private int centerXStart(int width)
    {
        return this.width / 2 - width / 2;
    }
    private int centerYStart(int height)
    {
        return this.height / 2 - height / 2;
    }
    private int centerXEnd(int width)
    {
        return this.width / 2 + width / 2;
    }
    private int centerYEnd(int height)
    {
        return this.height / 2 + height / 2;
    }

    private int frameWidth()
    {
        return MathUtil.toNearestEven(this.width * (0.64F));
    }
    private int frameHeight()
    {
        int h = MathUtil.toNearestEven(this.height * (0.90F));
        int diff = h % 24;
        return Math.max(h - diff, 96);
    }

    private int getMaxInList()
    {
        return Math.max(0, (this.frameHeight() / 24) - 3);
    }

    @Override
    protected void init() {
        this.pages.clear();
        super.init();

        int titleX = this.centerXStart(this.frameWidth()) + 32;
        int titleY = this.centerYStart(this.frameHeight()) + 21;
        int wide = this.frameWidth() - 64;

        this.addRenderableOnly(new RenderableString(Component.translatable("gui.aether_redux.pack_config.file", this.top.id()), titleX, titleY, wide, 0xFFFFFF, this.font));
        this.addRenderableOnly(new RenderableString(Component.translatable(this.category.parent() == null ? "gui.aether_redux.pack_config.top" : "gui.aether_redux.pack_config.category",
                Component.translatable("gui.aether_redux.pack_config.category." + this.category.id())), titleX, titleY - this.font.lineHeight, wide, 0xFFFFFF, this.font));

        List<Category> cats = new ArrayList<>();
        for (IConfigSaving iid : this.category.members.values()) {
            if (iid instanceof Category cat) {
                cats.add(cat);
            }
        }
        this.categories = cats;
        List<PackConfig<?>> cfgs = new ArrayList<>();
        for (IConfigSaving iid : this.category.members.values()) {
            if (iid instanceof PackConfig<?> config) {
                cfgs.add(config);
            }
        }
        this.configs = cfgs;
        this.createPages();
        for (Map.Entry<Integer, List<IConfigSaving>> entry : this.pages.entrySet())
        {

            int baseXCat = this.centerXStart(this.frameWidth()) + 16;
            int baseXConfig = this.centerXEnd(this.frameWidth()) - 80;
            int baseY = this.centerYStart(this.frameHeight()) + 42;

            int pg = entry.getKey();
            List<IConfigSaving> list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                IConfigSaving id = list.get(i);
                if (id instanceof PackConfig<?> config) {
                    AbstractWidget widget = config.mapper().createWidget(config, pg, this, Math.max(baseXConfig, baseXCat), baseY + i * 24, Math.min(64, this.frameWidth() - 32), 20, this.font);
                    this.addRenderableWidget(widget);
                    if (widget instanceof ISaveable sav) { this.saveables.add(sav); }
                    int width = this.frameWidth() - 98;
                    PageDependentString string = new PageDependentString(Component.translatable("gui.aether_redux.pack_config.config." + config.id()), this, baseXCat, baseY + i * 24 + (10 - this.font.lineHeight / 2), width, 0xFFFFFF, this.font, pg);
                    this.addRenderableOnly(string);
                } else if (id instanceof Category cat)
                {
                    DynamicButton button = new DynamicButton(baseXCat, baseY + i * 24,this.frameWidth() - 32, 20, this, Component.translatable("gui.aether_redux.pack_config.category." + cat.id()), b -> this.goInto(cat, this.minecraft), pg, Component.translatable("gui.aether_redux.pack_config.category_desc." + cat.id())) ;
                    Supplier<Boolean> rightPage = () -> button.getPage() == this.getCurrentPage();
                    button.setActiveSupplier(rightPage);
                    button.setVisibleSupplier(rightPage);
                    this.addRenderableWidget(button);
                }

            }
        }

        Supplier<Component> supplier = () -> {
            int page = (this.currentPageNumber + 1);
            return Component.translatable("gui.aether_redux.pack_config.page", page + "/" + this.pages.size());
        };
        Supplier<Integer> width = () -> Math.min(this.frameWidth() - 54, this.font.width(supplier.get()));
        DynamicRenderableString pages = new DynamicRenderableString(
                supplier,
                this.width / 2,
                this.centerYEnd(this.frameHeight()) - 16 - (this.font.lineHeight / 2),
                width,
                0xFFFFFF,
                this.font);
        this.addRenderableOnly(pages);

        Button next = new DynamicImageButton(this.centerXEnd(this.frameWidth()) - 26, this.centerYEnd(this.frameHeight()) - 26, 20, 20, 0, 0, 20, NEXT_BUTTON_LOC, 64, 64, (button) -> {
            if (this.hasNext()) this.currentPageNumber++; }).setActiveSupplier(this::hasNext);
        this.addRenderableWidget(next);

        Button prev = new DynamicImageButton(this.centerXStart(this.frameWidth()) + 6, this.centerYEnd(this.frameHeight()) - 26, 20, 20, 0, 0, 20, BACK_BUTTON_LOC, 64, 64, (button) -> {
            if (this.hasPrev()) this.currentPageNumber--; }).setActiveSupplier(this::hasPrev);
        this.addRenderableWidget(prev);

        Button out = new DynamicImageButton(this.centerXStart(this.frameWidth()) + 6, this.centerYStart(this.frameHeight()) + 11, 20, 20, 0, 0, 20, BACK_BUTTON_LOC, 64, 64, (button) -> {
            this.onClose();
        });
        this.addRenderableWidget(out);

        Button up = new DynamicImageButton(this.centerXEnd(this.frameWidth()) - 26, this.centerYStart(this.frameHeight()) + 11, 20, 20, 0, 0, 20, PARENT_BUTTON_LOC, 64, 64, (button) -> {
            if (this.parentScreen != null) {
                this.goingUp = true;
                this.onClose();
                this.goingUp = false;
            }
        }).setActiveSupplier(() -> this.category.parent() != null);
        this.addRenderableWidget(up);

    }


    @Override
    public void onClose() {
        double mX = minecraft.mouseHandler.xpos();
        double mY = minecraft.mouseHandler.ypos();
        this.saveAll();
        if (this.goingUp) {
            super.onClose();
            if (this.parentScreen != null) {
                this.parentScreen.currentPageNumber = 0;
                this.minecraft.setScreen(this.parentScreen);
                GLFW.glfwSetCursorPos(this.minecraft.getWindow().getWindow(), mX, mY);
        }   } else {
            this.closeRecursive(mX, mY);
        }
    }

    public void closeRecursive(double mX, double mY)
    {
        super.onClose();
        if (this.parentScreen != null) {
            this.parentScreen.closeRecursive(mX, mY);
        } else {
            if (this.selSc != null) {
                this.minecraft.setScreen(this.selSc);
            }
            GLFW.glfwSetCursorPos(this.minecraft.getWindow().getWindow(), mX, mY);
        }
    }

    public void saveAll()
    {
        this.saveables.forEach(ISaveable::save);
    }

    private boolean hasNext()
    {
        return this.currentPageNumber < this.pages.size() - 1;
    }

    private boolean hasPrev()
    {
        return this.currentPageNumber > 0;
    }




    private void createPages() {
        List<IConfigSaving> all = new ArrayList<>();
        all.addAll(this.categories);
        all.addAll(this.configs);
        int totalSize = all.size();
        if (totalSize < getMaxInList()) {
            this.pages.put(0, all);
        } else {
            List<List<IConfigSaving>> list = Lists.partition(all, this.getMaxInList());

            for (int i = 0; i < list.size(); ++i) {
                this.pages.put(i, list.get(i));
            }
        }
    }
}
