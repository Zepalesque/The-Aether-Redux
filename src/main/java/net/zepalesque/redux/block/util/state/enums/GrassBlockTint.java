package net.zepalesque.redux.block.util.state.enums;

import net.minecraft.util.StringRepresentable;

public enum GrassBlockTint implements StringRepresentable {

    TINTABLE("tintable"), BLIGHTED("blighted"), FROSTED("frosted"), AETHER_COLOR("highlands");
    final String id;
    GrassBlockTint(String id) {
        this.id = id;
    }

    @Override
    public String getSerializedName() {
        return this.id;
    }
}
