package net.zepalesque.redux.block.state;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.zepalesque.redux.block.state.enums.AetherMossType;
import net.zepalesque.redux.block.state.enums.BlightGrassColor;
import net.zepalesque.redux.block.state.enums.LogicatorMode;

public class ReduxStates {

    // TODO: remove
    public static final BooleanProperty NATURAL_GEN = BooleanProperty.create("natural_gen");

    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left_input");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right_input");
    
    public static final BooleanProperty HAS_SPORES = BooleanProperty.create("has_spores");
    
    public static final IntegerProperty CLOUDCAP_VARIANT
        = IntegerProperty.create("cloudcap_variant", 0, 4);
    
    public static final EnumProperty<LogicatorMode>
        MODE_LOGICATOR = EnumProperty
        .create("mode", LogicatorMode.class);
    public static final EnumProperty<BlightGrassColor>
        BLIGHT_GRASS_COLOR = EnumProperty
        .create("color", BlightGrassColor.class);
    
    public static final EnumProperty<AetherMossType>
        AETHER_MOSS_TYPE = EnumProperty
        .create("type", AetherMossType.class);
}
