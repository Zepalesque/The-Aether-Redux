package net.zepalesque.redux.config.enums.pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.api.serialization.client.WidgetMapper;

@OnlyIn(Dist.CLIENT)
public enum DungeonType {
    original, retro, ancient;


    public static final WidgetMapper<DungeonType> MAPPER = WidgetMapper.fromEnum(DungeonType.class);
}
