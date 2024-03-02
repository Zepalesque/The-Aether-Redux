package net.zepalesque.redux.mixin.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.server.packs.repository.Pack;
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

import java.util.HashSet;

@Mixin(PackSelectionScreen.class)
public class PackSelectionScreenMixin implements MixinMenuStorage {
    @Shadow @Final private PackSelectionModel model;
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

    @Inject(method = "onClose", at = @At(value = "HEAD"))
    public void close(CallbackInfo ci) {
        if (this.menu != null && this.menu.isMarked()) {
            HashSet<String> a = new HashSet<>(((PackSelectionModelAccessor) this.model).getPacks().stream().map(Pack::getId).toList());
            HashSet<String> b = new HashSet<>(Minecraft.getInstance().options.resourcePacks);
            if (a.containsAll(b) && b.containsAll(a)) {
                Minecraft.getInstance().reloadResourcePacks();
            }
        }
    }
}
