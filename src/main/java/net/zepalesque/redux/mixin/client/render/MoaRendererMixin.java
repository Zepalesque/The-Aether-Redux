package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether.client.renderer.entity.MoaRenderer;
import com.aetherteam.aether.client.renderer.entity.model.MoaModel;
import com.aetherteam.aether.entity.passive.Moa;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.moa.MoaAdditionsLayer;
import net.zepalesque.redux.client.render.entity.model.entity.moa.MoaAdditionsModel;
import net.zepalesque.redux.client.render.util.MoaUtils;
import net.zepalesque.redux.util.math.MathUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoaRenderer.class)
public class MoaRendererMixin extends MobRenderer<Moa, MoaModel> {

    public MoaRendererMixin(EntityRendererProvider.Context context, MoaModel model, float shadowRadius) {
        super(context, model, shadowRadius);
    }




    @Override
    public ResourceLocation getTextureLocation(Moa entity) {
        return null;
    }
}
