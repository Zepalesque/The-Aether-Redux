package net.zepalesque.redux.mixin.client.gui;

import com.aetherteam.cumulus.api.Menu;
import com.aetherteam.cumulus.api.MenuHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.MixinMenuStorage;
import net.zepalesque.redux.client.ReduxClient;
import net.zepalesque.redux.client.ReduxMenus;
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

@Mixin(MenuHelper.class)
public abstract class MenuHelperMixin {


    @Shadow public abstract boolean shouldFade();

    @Inject(method = "applyMenu", at = @At(value = "INVOKE", target = "Lcom/aetherteam/cumulus/api/MenuHelper;shouldFade()Z"), remap = false)
    public void render(Menu menu, CallbackInfoReturnable<TitleScreen> cir) {
        if (this.shouldFade()) {
            ReduxMenus.cycle();
        }

    }

}
