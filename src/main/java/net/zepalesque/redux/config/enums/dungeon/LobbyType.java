package net.zepalesque.redux.config.enums.dungeon;

import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum LobbyType implements StringRepresentable {

    classic("none"), doors("doors"), two_story("two_story");

    public final String id;
    LobbyType(String id) {
        this.id = id;
    }
    @Override
    @NotNull
    public  String getSerializedName() {
        return this.id;
    }
}
