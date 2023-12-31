package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffWoolModel;
import com.aetherteam.aether.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.sheepuff.ConfiguredSheepuffLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.sheepuff.ReduxSheepuffWoolLayer;
import net.zepalesque.redux.client.render.entity.model.entity.sheepuff.ConfiguredSheepuffModel;
import net.zepalesque.redux.client.render.entity.model.entity.sheepuff.ConfiguredSheepuffWoolModel;
import net.zepalesque.redux.client.render.entity.model.entity.sheepuff.PuffedConfiguredSheepuffWoolModel;
import net.zepalesque.redux.client.render.entity.model.entity.sheepuff.ReduxSheepuffModel;

import javax.annotation.Nonnull;

// TODO: Redo model and texture, move to mixin
public class ReduxSheepuffRenderer extends MobRenderer<Sheepuff, SheepuffModel> {
    private static final ResourceLocation SHEEPUFF_TEXTURE = new ResourceLocation(Aether.MODID, "textures/entity/mobs/sheepuff/sheepuff.png");

    public ReduxSheepuffRenderer(EntityRendererProvider.Context context) {
        super(context, new ReduxSheepuffModel(context.bakeLayer(AetherModelLayers.SHEEPUFF)), 0.7F);
        this.addLayer(new ReduxSheepuffWoolLayer(this, new SheepuffWoolModel(context.bakeLayer(AetherModelLayers.SHEEPUFF_WOOL)), new SheepuffWoolModel(context.bakeLayer(AetherModelLayers.SHEEPUFF_WOOL_PUFFED))));
        this.addLayer(new ConfiguredSheepuffLayer(this, new ConfiguredSheepuffModel(context.bakeLayer(ReduxModelLayers.SHEEPUFF_BODY)),
                new ConfiguredSheepuffWoolModel(context.bakeLayer(ReduxModelLayers.SHEEPUFF_WOOL)),
                new PuffedConfiguredSheepuffWoolModel(context.bakeLayer(ReduxModelLayers.SHEEPUFF_WOOL_PUFFED))));
    }

    @Override
    public void render(Sheepuff pEntity, float pEntityYaw, float pPartialTicks, PoseStack pMatrixStack, MultiBufferSource pBuffer, int pPackedLight) {
        super.render(pEntity, pEntityYaw, pPartialTicks, pMatrixStack, pBuffer, pPackedLight);
    }

    @Nonnull
    public ResourceLocation getTextureLocation(@Nonnull Sheepuff sheepuff) {
        return SHEEPUFF_TEXTURE;
    }
}