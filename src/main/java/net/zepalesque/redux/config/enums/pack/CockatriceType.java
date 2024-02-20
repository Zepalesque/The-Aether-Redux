package net.zepalesque.redux.config.enums.pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.api.serialization.client.WidgetMapper;

@OnlyIn(Dist.CLIENT)
public enum CockatriceType {
    blighted, classic, redux_retro;

    public static final WidgetMapper<CockatriceType> MAPPER = WidgetMapper.fromEnum(CockatriceType.class);
}
