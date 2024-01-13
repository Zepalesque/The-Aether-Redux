package net.zepalesque.redux.config.enums.dungeon;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum BossRoomType implements StringRepresentable {

    classic("none"), catwalks("catwalks"), open("open"), vault("vault");

    public final String id;
    BossRoomType(String id) {
        this.id = id;
    }
    @Override
    @NotNull
    public  String getSerializedName() {
        return this.id;
    }
}
