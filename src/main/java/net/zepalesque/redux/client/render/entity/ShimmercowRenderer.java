//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.QuadrupedWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.QuadrupedWingsModel;
import com.aetherteam.aether.entity.passive.FlyingCow;
import net.minecraft.client.model.CowModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.entity.layer.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.model.entity.ShimmercowModel;
import net.zepalesque.redux.entity.passive.Shimmercow;

public class ShimmercowRenderer extends MobRenderer<Shimmercow, ShimmercowModel<Shimmercow>> {
    private static final ResourceLocation FLYING_COW_TEXTURE = Redux.locate("textures/entity/mobs/shimmercow.png");

    public ShimmercowRenderer(EntityRendererProvider.Context context) {
        super(context, new ShimmercowModel<>(context.bakeLayer(ReduxModelLayers.SHIMMERCOW)), 0.7F);
    }

    public ResourceLocation getTextureLocation(Shimmercow flyingCow) {
        return FLYING_COW_TEXTURE;
    }
}
