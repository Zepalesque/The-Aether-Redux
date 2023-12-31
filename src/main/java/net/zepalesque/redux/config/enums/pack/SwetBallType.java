package net.zepalesque.redux.config.enums.pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.api.serialization.client.WidgetMapper;

@OnlyIn(Dist.CLIENT)
public enum SwetBallType {
    original_ball, consistent_name, gel;

    public static final WidgetMapper<SwetBallType> MAPPER = WidgetMapper.fromEnum(SwetBallType.class);
}
