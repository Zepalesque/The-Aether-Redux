package net.zepalesque.redux.blockset.stone.type;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.MapColor;

public class BrickStoneSet extends BaseStoneSet {
    public BrickStoneSet(String id, MapColor color, SoundType sound, float breakTime, float blastResistance, String textureFolder) {
        super(id, color, sound, breakTime, blastResistance, textureFolder);
    }

    @Override
    public String baseName(boolean isBaseBlock) {
        return isBaseBlock ? this.id + "s" : this.id;
    }
}
