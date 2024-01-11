package net.zepalesque.redux.mixin.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Shadow public abstract Minecraft getMinecraft();

    @Shadow @Nullable protected Minecraft minecraft;

}
