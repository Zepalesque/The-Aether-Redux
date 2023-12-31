package net.zepalesque.redux.config.enums.pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.api.serialization.client.WidgetMapper;

@OnlyIn(Dist.CLIENT)
public enum ClassicAnyOption {
    classic, modern, original;

    public static final WidgetMapper<ClassicAnyOption> MAPPER = WidgetMapper.fromEnum(ClassicAnyOption.class);
}
