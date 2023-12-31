package net.zepalesque.redux.client.render.entity.layer.entity.sheepuff;

import com.aetherteam.aether.client.renderer.entity.layers.SheepuffWoolLayer;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffModel;
import com.aetherteam.aether.client.renderer.entity.model.SheepuffWoolModel;
import com.aetherteam.aether.entity.passive.Sheepuff;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

public class ReduxSheepuffWoolLayer extends SheepuffWoolLayer {
    public ReduxSheepuffWoolLayer(RenderLayerParent<Sheepuff, SheepuffModel> entityRenderer, SheepuffWoolModel woolModel, SheepuffWoolModel puffedModel) {
        super(entityRenderer, woolModel, puffedModel);
    }

    @Override
    public void render(@NotNull PoseStack poseStack, @NotNull MultiBufferSource buffer, int packedLight, Sheepuff sheepuff, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(!ReduxConfig.CLIENT.sheepuff_improvements.get())
            super.render(poseStack, buffer, packedLight, sheepuff, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
    }
}
