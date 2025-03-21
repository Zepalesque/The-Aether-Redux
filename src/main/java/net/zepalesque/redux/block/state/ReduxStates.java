package net.zepalesque.redux.block.state;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.zepalesque.redux.block.state.enums.BlightGrassColor;
import net.zepalesque.redux.block.state.enums.LogicatorMode;

public class ReduxStates {

    public static final BooleanProperty NATURAL_GEN = BooleanProperty.create("natural_gen");

    public static final BooleanProperty LEFT_INPUT = BooleanProperty.create("left_input");
    public static final BooleanProperty RIGHT_INPUT = BooleanProperty.create("right_input");
    
    public static final EnumProperty<LogicatorMode> MODE_LOGICATOR = EnumProperty.create("mode", LogicatorMode.class);
    public static final EnumProperty<BlightGrassColor> BLIGHT_GRASS_COLOR = EnumProperty.create("color", BlightGrassColor.class);
}
