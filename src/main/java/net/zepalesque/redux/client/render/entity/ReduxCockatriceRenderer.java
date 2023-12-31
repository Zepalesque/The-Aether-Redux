package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.cockatrice.ReduxCockatriceMarkingsLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.cockatrice.UpdatedCockatriceLayer;
import net.zepalesque.redux.client.render.entity.model.entity.cockatrice.ReduxCockatriceModel;
import net.zepalesque.redux.client.render.entity.model.entity.cockatrice.UpdatedCockatriceModel;

import javax.annotation.Nonnull;
public class ReduxCockatriceRenderer extends MobRenderer<Cockatrice, ReduxCockatriceModel> {
    private static final ResourceLocation COCKATRICE_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice.png");
    private static final RenderType COCKATRICE_MARKINGS = RenderType.entityTranslucentEmissive(new ResourceLocation(Aether.MODID, "textures/entity/mobs/cockatrice/cockatrice_emissive.png"));

    public ReduxCockatriceRenderer(EntityRendererProvider.Context context) {
        super(context, new ReduxCockatriceModel(context.bakeLayer(AetherModelLayers.COCKATRICE)), 0.7F);
        this.addLayer(new ReduxCockatriceMarkingsLayer<>(this));
        this.addLayer(new UpdatedCockatriceLayer(this, new UpdatedCockatriceModel(context.bakeLayer(ReduxModelLayers.REDUX_COCKATRICE))));
    }

    protected void scale(@Nonnull Cockatrice cockatrice, PoseStack poseStack, float partialTickTime) {
        poseStack.scale(1.8F, 1.8F, 1.8F);
    }

    protected float getBob(@Nonnull Cockatrice cockatrice, float partialTicks) {
        return this.model.setupWingsAnimation(cockatrice, partialTicks);
    }
    public void render(@Nonnull Cockatrice cockatrice, float pEntityYaw, float partialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(cockatrice, pEntityYaw, partialTicks, pMatrixStack, pBuffer, pPackedLight);
    }
    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Cockatrice cockatrice) {
        return COCKATRICE_TEXTURE;
    }
}
