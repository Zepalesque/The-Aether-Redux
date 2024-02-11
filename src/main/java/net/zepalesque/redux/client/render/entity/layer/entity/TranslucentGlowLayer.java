package net.zepalesque.redux.client.render.entity.layer.entity;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.model.entity.BlightbunnyModel;
import net.zepalesque.redux.entity.monster.Blightbunny;

public class TranslucentGlowLayer<T extends Entity, M extends EntityModel<T>> extends EyesLayer<T, M> {

    private final ResourceLocation texture;
    public TranslucentGlowLayer(RenderLayerParent<T, M> renderer, ResourceLocation texture) {
        super(renderer);
        this.texture = texture;
    }

    @Override
    public RenderType renderType() {
        return RenderType.entityTranslucentEmissive(texture);
    }
}
