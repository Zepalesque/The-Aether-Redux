package net.zepalesque.redux.config.enums.dungeon;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum ChestRoomType implements StringRepresentable {

    classic("none"), genesis("genesis"), open("open"), pillars("pillars");

    public final String id;
    ChestRoomType(String id) {
        this.id = id;
    }
    @Override
    @NotNull
    public  String getSerializedName() {
        return this.id;
    }
}
