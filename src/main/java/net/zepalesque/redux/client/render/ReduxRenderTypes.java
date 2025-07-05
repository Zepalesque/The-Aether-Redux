package net.zepalesque.redux.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterShadersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import org.apache.commons.lang3.function.TriFunction;
import org.apache.commons.lang3.tuple.Triple;
import oshi.util.tuples.Quartet;
import oshi.util.tuples.Quintet;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;

@Mod.EventBusSubscriber(
    modid = Redux.MODID,
    value = {Dist.CLIENT},
    bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ReduxRenderTypes extends RenderType {
    
    public ReduxRenderTypes(String name, VertexFormat format, VertexFormat.Mode mode, int bufferSize, boolean affectsCrumbling, boolean sortOnUpload, Runnable setupState, Runnable clearState) {
        super(name, format, mode, bufferSize, affectsCrumbling, sortOnUpload, setupState, clearState);
    }
    
    @SubscribeEvent
    public static void registerShaders(RegisterShadersEvent event) {
        try {
            ShaderInstance si = new ShaderInstance(event.getResourceManager(), Redux.locate("rendertype_breeze_wind"), DefaultVertexFormat.NEW_ENTITY);
            event.registerShader(si, s -> rendertypeBreezeWindShader = s);
        } catch (IOException io) {
            Redux.LOGGER.error("Failed to register breeze wind shader!", io);
        }
    }
    
    @Nullable
    private static ShaderInstance rendertypeBreezeWindShader;
    

    
    @Nullable
    public static ShaderInstance getRendertypeBreezeWindShader() {
        return rendertypeBreezeWindShader;
    }
    
    public static final RenderStateShard.ShaderStateShard RENDERTYPE_BREEZE_WIND_SHADER = new RenderStateShard.ShaderStateShard(
        ReduxRenderTypes::getRendertypeBreezeWindShader
    );
    
  /*  public static TriFunction<ResourceLocation, Float, Float, RenderType> BREEZE_WIND = memoize(ReduxRenderTypes::breezeWindInternal);
    
    public static RenderType breezeWind(ResourceLocation texture, float u, float v) {
        return BREEZE_WIND.apply(texture, u, v);
    }*/
    
    
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
                .setOutputState(RenderType.PARTICLES_TARGET)
                .createCompositeState(false)
        );
    }
    
   /* public static <T, U, V, R> TriFunction<T, U, V, R> memoize(final TriFunction<T, U, V, R> p_memoTriFunction) {
        return new TriFunction<>() {
            private final Map<Triple<T, U, V>, R> cache = new ConcurrentHashMap<>();
            
            public R apply(T key1_, U key2_, V key3_) {
                return this.cache.computeIfAbsent(Triple.of(key1_, key2_, key3_), triple -> p_memoTriFunction.apply(triple.getLeft(), triple.getMiddle(), triple.getRight()));
            }
            
            public String toString() {
                return "memoize/3[function=" + p_memoTriFunction + ", size=" + this.cache.size() + "]";
            }
        };
    }*/
}
