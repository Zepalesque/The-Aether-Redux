package net.zepalesque.redux.config.enums.pack;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.zepalesque.redux.api.serialization.client.WidgetMapper;

@OnlyIn(Dist.CLIENT)
public enum ClassicOption {
    classic, modern;

    public static final WidgetMapper<ClassicOption> MAPPER = WidgetMapper.fromEnum(ClassicOption.class);
}
