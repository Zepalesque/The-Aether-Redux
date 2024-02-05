package net.zepalesque.redux.block.util.state.enums;

import net.minecraft.util.StringRepresentable;

public enum GrassSize implements StringRepresentable {
    shrt("short"), med("medium"), tall("tall");

    final String name;
    GrassSize(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}
