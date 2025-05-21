package net.zepalesque.redux.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;

import javax.annotation.Nullable;
import java.io.IOException;

@Mod.EventBusSubscriber(
    modid = Redux.MODID,
    value = {Dist.CLIENT},
    bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ReduxRenderTypes {
    
    @Nullable
    private static ShaderInstance rendertypeBreezeWindShader;
    
    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            ShaderInstance si = new ShaderInstance(event.getResourceProvider(), Redux.locate("rendertype_breeze_wind_backport"), DefaultVertexFormat.NEW_ENTITY);
            event.registerShader(si, s -> rendertypeBreezeWindShader = s);
        } catch (IOException io) {
            Redux.LOGGER.error("Failed to register breeze wind shader!", io);
        }
    }
    
    @Nullable
    public static ShaderInstance getRendertypeBreezeWindShader() {
        return rendertypeBreezeWindShader;
    }
    
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_BREEZE_WIND_SHADER = new RenderStateShard.ShaderStateShard(
        ReduxRenderTypes::getRendertypeBreezeWindShader
    );
    
    public static RenderType breezeWind(ResourceLocation location, float u, float v) {
        return RenderType.create(
            "breeze_wind",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_BREEZE_WIND_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(u, v))
                .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderType.NO_CULL)
                .setLightmapState(RenderType.LIGHTMAP)
                .setOverlayState(RenderType.NO_OVERLAY)
                .createCompositeState(false)
        );
    }
    
    public static RenderType whirlwindParticleTranslucency(ResourceLocation location, float u, float v) {
        return RenderType.create(
            "whirlwind_post_translucency",
            DefaultVertexFormat.NEW_ENTITY,
            VertexFormat.Mode.QUADS,
            1536,
            false,
            true,
            RenderType.CompositeState.builder()
                .setShaderState(RENDERTYPE_BREEZE_WIND_SHADER)
                .setTextureState(new RenderStateShard.TextureStateShard(location, false, false))
                .setTexturingState(new RenderStateShard.OffsetTexturingStateShard(u, v))
                .setTransparencyState(RenderType.TRANSLUCENT_TRANSPARENCY)
                .setCullState(RenderType.NO_CULL)
                .setLightmapState(RenderType.LIGHTMAP)
                .setOverlayState(RenderType.NO_OVERLAY)
                .setOutputState(RenderType.WEATHER_TARGET)
                .createCompositeState(false)
        );
    }
}
