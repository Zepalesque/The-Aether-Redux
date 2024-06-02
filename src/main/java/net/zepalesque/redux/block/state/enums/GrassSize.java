package net.zepalesque.redux.block.state.enums;

import net.minecraft.util.StringRepresentable;

public enum GrassSize implements StringRepresentable {
    SHORT("short"), MEDIUM("medium"), TALL("tall");

    final String name;
    GrassSize(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
