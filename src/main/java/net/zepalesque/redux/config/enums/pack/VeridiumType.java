package net.zepalesque.redux.config.enums.pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.api.serialization.client.WidgetMapper;

@OnlyIn(Dist.CLIENT)
public enum VeridiumType {
    modern, shadow, classic;

    public static final WidgetMapper<VeridiumType> MAPPER = WidgetMapper.fromEnum(VeridiumType.class);
}
