package net.zepalesque.redux.client.gui;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.CubeMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.zepalesque.redux.Redux;

import java.util.Arrays;

public class PanoramaHolder {

    private final ImmutableList<CubeMap> cubeMaps;
    private int index = 0;
    private final RandomSource random;

    private PanoramaHolder(ImmutableList<CubeMap> cubeMaps) {
        this.cubeMaps = cubeMaps;
        this.random = RandomSource.create();
    }

    public PanoramaHolder(CubeMap... maps) {
        this(ImmutableList.copyOf(maps));
    }

    public PanoramaHolder(ResourceLocation... locs) {
        this(ImmutableList.copyOf(Arrays.stream(locs).map(CubeMap::new).toList()));
    }
    public PanoramaHolder(String... ids) {
        this(ImmutableList.copyOf(Arrays.stream(ids).map(s -> new CubeMap(Redux.locate("textures/gui/title/panorama/" + s + "/panorama"))).toList()));
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

    public CubeMap getCubemap() {
        return this.cubeMaps.get(this.index >= this.cubeMaps.size() ? 0 : this.index);
    }

}
