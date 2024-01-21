package net.zepalesque.redux.client;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.pipeline.RenderTarget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// TODO: Investigate whether or not this is a good way to do this, because I'm not convinced it is...
public class ReduxPostProcessHandler {

    public static final String SHADER = Redux.MODID + ":shaders/post/adrenaline.json";
    private static PostChain adrenaline = null;

    public static void setAdrenaline(PostChain chain) {
        if (adrenaline != null) {
            adrenaline.close();
        }
        adrenaline = chain;
    }

    public static void initAdrenalineShader() {
        setAdrenaline(new ResourceLocation(SHADER));
    }

    public static void setAdrenaline(ResourceLocation loc) {
        if (adrenaline != null) {
            adrenaline.close();
        }
        Minecraft minecraft = Minecraft.getInstance();
        try {
            adrenaline = new PostChain(minecraft.getTextureManager(), minecraft.getResourceManager(), minecraft.getMainRenderTarget(), loc);
            adrenaline.resize(minecraft.getWindow().getWidth(), minecraft.getWindow().getHeight());
        } catch (IOException ioexception) {
            Redux.LOGGER.warn("Failed to load shader: {}", loc, ioexception);

        } catch (JsonSyntaxException jsonsyntaxexception) {
            Redux.LOGGER.warn("Failed to parse shader: {}", loc, jsonsyntaxexception);
        }
    }
    public static @Nullable PostChain getAdrenaline() {
        return ReduxConfig.CLIENT.enable_adrenaline_postproccess.get() ? adrenaline : null;
    }

}
