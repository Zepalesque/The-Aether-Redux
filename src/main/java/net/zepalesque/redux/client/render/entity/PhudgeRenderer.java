
package net.zepalesque.redux.client.render.entity;

import com.aetherteam.aether.client.renderer.AetherModelLayers;
import com.aetherteam.aether.client.renderer.entity.layers.PhygHaloLayer;
import com.aetherteam.aether.client.renderer.entity.layers.QuadrupedWingsLayer;
import com.aetherteam.aether.client.renderer.entity.model.HaloModel;
import com.aetherteam.aether.client.renderer.entity.model.QuadrupedWingsModel;
import net.minecraft.client.model.PigModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SaddleLayer;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.render.ReduxModelLayers;
import net.zepalesque.redux.client.render.entity.layer.entity.PhudgeReduxLayer;
import net.zepalesque.redux.client.render.entity.layer.entity.PhygReduxLayer;
import net.zepalesque.redux.client.render.entity.model.entity.PhygReduxModel;
import net.zepalesque.redux.entity.neutral.Phudge;
import org.jetbrains.annotations.NotNull;

public class PhudgeRenderer extends MobRenderer<Phudge, PigModel<Phudge>> {
    private static final ResourceLocation PHUDGE_TEXTURE = Redux.locate("textures/entity/mobs/phudge/phudge.png");

    public PhudgeRenderer(EntityRendererProvider.Context context) {
        super(context, new PigModel<>(context.bakeLayer(AetherModelLayers.PHYG)), 0.7F);
        this.addLayer(new QuadrupedWingsLayer<>(this, new QuadrupedWingsModel<>(context.bakeLayer(AetherModelLayers.PHYG_WINGS)), Redux.locate("textures/entity/mobs/phudge/phudge_wings.png")));
        this.addLayer(new SaddleLayer<>(this, new PigModel<>(context.bakeLayer(AetherModelLayers.PHYG_SADDLE)), new ResourceLocation("textures/entity/pig/pig_saddle.png")));
        this.addLayer(new PhudgeReduxLayer(this, new PhygReduxModel<>(context.bakeLayer(ReduxModelLayers.PHYG))));
    }

    public @NotNull ResourceLocation getTextureLocation(@NotNull Phudge phyg) {
        return PHUDGE_TEXTURE;
    }
}
