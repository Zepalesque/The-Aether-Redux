package net.zepalesque.redux.client.gui.component.menu;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.client.renderer.PanoramaRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.Redux;

import java.util.Arrays;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class CyclingPanoramaRenderer extends PanoramaRenderer {
    private final Minecraft minecraft;
    private final ImmutableList<CubeMap> cubeMaps;
    private int index = 0;
    private float spin;
    private float bob;
    private final RandomSource random;

    public CyclingPanoramaRenderer(CubeMap... maps) {
        super(new CubeMap(new ResourceLocation("null")));
        this.cubeMaps = ImmutableList.copyOf(maps);
        this.minecraft = Minecraft.getInstance();
        this.random = RandomSource.create();
    }

    public CyclingPanoramaRenderer(ResourceLocation... locs) {
        super(new CubeMap(new ResourceLocation("null")));
        this.cubeMaps = ImmutableList.copyOf(Arrays.stream(locs).map(CubeMap::new).toList());
        this.minecraft = Minecraft.getInstance();
        this.random = RandomSource.create();
    }
    public CyclingPanoramaRenderer(String... ids) {
        super(new CubeMap(new ResourceLocation("null")));
        this.cubeMaps = ImmutableList.copyOf(Arrays.stream(ids).map(s -> new CubeMap(Redux.locate("textures/gui/title/panorama/" + s + "/panorama"))).toList());
        this.minecraft = Minecraft.getInstance();
        this.random = RandomSource.create();
    }

    @Override
    public void render(float deltaT, float alpha) {
        float f = (float)((double)deltaT * this.minecraft.options.panoramaSpeed().get());
        this.spin = wrap(this.spin + f * 0.1F, 360.0F);
        this.bob = wrap(this.bob + f * 0.001F, ((float)Math.PI * 2F));
        this.getMap().render(this.minecraft, 10.0F, -this.spin, alpha);
    }

    public void randomizeIndex() {
        this.index = this.random.nextInt(this.cubeMaps.size());
    }
    public void cycle() {
        if (this.index >= this.cubeMaps.size()) {
            this.index = 0;
        } else {
            this.index++;
        }
    }

    public CubeMap getMap() {
        return this.cubeMaps.get(this.index >= this.cubeMaps.size() ? 0 : this.index);
    }


    private static float wrap(float value, float max) {
        return value > max ? value - max : value;
    }
}