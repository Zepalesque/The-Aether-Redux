package net.zepalesque.redux.mixin.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.zepalesque.redux.api.MixinMenuStorage;
import net.zepalesque.redux.client.gui.screen.PackConfigMenu;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PackSelectionScreen.class)
public class PackSelectionScreenMixin implements MixinMenuStorage {
    @Unique
    @Nullable
    public PackConfigMenu menu;

    @Override
    public @Nullable PackConfigMenu getMenu() {
        return this.menu;
    }

    @Override
    public void setMenu(PackConfigMenu menu) {
        this.menu = menu;
        this.menu.setSelectionScreen((PackSelectionScreen) (Object) this);
    }

    @Inject(method = "onClose", at = @At(value = "TAIL"))
    public void close(CallbackInfo ci) {
        if (this.menu != null && this.menu.isMarked()) {
            Minecraft.getInstance().reloadResourcePacks();
        }
    }
}
