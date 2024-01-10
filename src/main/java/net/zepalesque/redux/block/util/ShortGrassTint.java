package net.zepalesque.redux.block.util;

import net.minecraft.util.StringRepresentable;

public enum ShortGrassTint implements StringRepresentable {

    TINTABLE("tintable"), ENCHANTED("enchanted"), AETHER_COLOR("highlands"), BLOCK_DEPENDENT("depends_on_below");
    final String id;
    ShortGrassTint(String id) {
        this.id = id;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }
}
