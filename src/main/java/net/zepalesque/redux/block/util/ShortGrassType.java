package net.zepalesque.redux.block.util;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.misc.ReduxTags;

import java.util.Map;

public enum ShortGrassType implements StringRepresentable {



    AETHER("aether_short_grass"), ENCHANTED("enchanted_short_grass"), FROSTED("frosted_short_grass"), FROSTED_SNOWY("frosted_short_grass_snowy"), BLIGHTED("blighted_short_grass");
    private final String id;
    public static final Map<TagKey<Block>, ShortGrassType> TAGS = ImmutableMap.<TagKey<Block>, ShortGrassType>builder()
            .put(ReduxTags.Blocks.HIGHLANDS_GRASSES, AETHER)
            .put(ReduxTags.Blocks.ENCHANTED_GRASSES, ENCHANTED)
            .put(ReduxTags.Blocks.FROSTED_GRASSES, FROSTED)
            .put(ReduxTags.Blocks.FROSTED_PLANTS_PLACEMENT, FROSTED_SNOWY)
            .put(ReduxTags.Blocks.BLIGHTED_GRASSES, BLIGHTED)
            .build();

    ShortGrassType(String name) {
        this.id = name;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }
}
