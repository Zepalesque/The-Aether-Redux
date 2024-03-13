package net.zepalesque.redux.mixin.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.MixinMenuStorage;
import net.zepalesque.redux.client.gui.screen.config.PackConfigMenu;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(TransferableSelectionList.PackEntry.class)
public class PackEntryMixin implements MixinMenuStorage {
    @Unique private static final ResourceLocation SETTINGS_BUTTON = Redux.locate("textures/gui/config_menu/settings.png");
    @Unique private static final String ID = "builtin/resource/overrides_pack";



    @Unique @Nullable public PackConfigMenu menu;


    @Shadow @Final private PackSelectionModel.Entry pack;

    @Shadow @Final private TransferableSelectionList parent;

    @Shadow @Final protected Minecraft minecraft;

    @Inject(method = "render", at = @At(value = "TAIL"))
    public void render(PoseStack guiGraphics, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean hovering, float partialTick, CallbackInfo ci) {
        if (this.pack instanceof PackSelectionModel.EntryBase base && base.pack.getId().equals(ID)) {
            int minX = left + width - 21;
            int minY = top;
            RenderSystem.setShaderTexture(0, SETTINGS_BUTTON);
            guiGraphics.pushPose();
            if (mouseX > minX && mouseX < minX + 10 && mouseY > minY && mouseY < minY + 10) {
                GuiComponent.blit(guiGraphics, minX, minY, 0F, 10F, 10, 10, 32, 32);
            } else {
                GuiComponent.blit(guiGraphics, minX, minY, 0F, 0F, 10, 10, 32, 32);
            }
            guiGraphics.popPose();

        }
    }

    @Inject(method = "mouseClicked", at = @At(value = "HEAD"), cancellable = true)
    public void mouseClick(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if (button == 0 && this.pack instanceof PackSelectionModel.EntryBase base && base.pack.getId().equals(ID)) {
            
            int left = this.parent.getRowLeft();
            int top = this.parent.getRowTop(this.parent.children().indexOf((TransferableSelectionList.PackEntry) (Object) this));
            int width = this.parent.getRowWidth();
            int height = this.parent.itemHeight - 4;
            int minX = left + width - 21;
            int minY = top;


            if (mouseX > minX && mouseX < minX + 10 && mouseY > minY && mouseY < minY + 10) {
                if (this.menu == null) {
                    PackConfigMenu mu = ((MixinMenuStorage) this.parent).getMenu();
                    this.menu = Objects.requireNonNullElseGet(mu, () -> new PackConfigMenu(CommonComponents.EMPTY, Redux.packConfig.base, null));
                }
                ((MixinMenuStorage) this.parent).setMenu(this.menu);
                this.minecraft.setScreen(this.menu);
                this.minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
                cir.setReturnValue(true);
            }
        }

    }


    @Override
    public @Nullable PackConfigMenu getMenu() {
        return this.menu;
    }

    @Override
    public void setMenu(PackConfigMenu menu) {
        this.menu = menu;
    }
}
