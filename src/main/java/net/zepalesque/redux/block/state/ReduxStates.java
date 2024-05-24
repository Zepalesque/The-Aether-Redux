package net.zepalesque.redux.block.state;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.zepalesque.redux.block.state.enums.GrassSize;

public class ReduxStates {
    public static final BooleanProperty ENCHANTED = BooleanProperty.create("enchanted");
    public static final EnumProperty<GrassSize> GRASS_SIZE = EnumProperty.create("grass_size", GrassSize.class);

    public static final BooleanProperty NATURAL_GEN = BooleanProperty.create("natural_gen");
}
