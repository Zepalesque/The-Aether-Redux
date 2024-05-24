package net.zepalesque.redux.block.state.enums;

import net.minecraft.util.StringRepresentable;

public enum GrassSize implements StringRepresentable {
    small("small"), medium("medium"), tall("tall");

    final String name;
    GrassSize(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
