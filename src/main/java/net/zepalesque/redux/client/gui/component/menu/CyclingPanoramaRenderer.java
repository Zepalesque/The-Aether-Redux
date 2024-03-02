package net.zepalesque.redux.client.gui.component.menu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.client.gui.PanoramaHolder;

@OnlyIn(Dist.CLIENT)
public class CyclingPanoramaRenderer extends PanoramaRenderer {
    private final Minecraft minecraft;
    private final PanoramaHolder holder;
    private float spin;
    private float bob;

    public CyclingPanoramaRenderer(PanoramaHolder holder) {
        super(new CubeMap(new ResourceLocation("null")));
        this.holder = holder;
        this.minecraft = Minecraft.getInstance();
    }

    @Override
    public void render(float deltaT, float alpha) {
        float f = (float)((double)deltaT * this.minecraft.options.panoramaSpeed().get());
        this.spin = wrap(this.spin + f * 0.1F, 360.0F);
        this.bob = wrap(this.bob + f * 0.001F, ((float)Math.PI * 2F));
        this.holder.getCubemap().render(this.minecraft, 10.0F, -this.spin, alpha);
    }
    
    private static float wrap(float value, float max) {
        return value > max ? value - max : value;
    }
}