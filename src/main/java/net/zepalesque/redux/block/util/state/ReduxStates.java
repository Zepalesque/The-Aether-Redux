package net.zepalesque.redux.block.util.state;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.zepalesque.redux.block.natural.ExtendedDistanceLeavesBlock;
import net.zepalesque.redux.block.util.state.enums.GrassBlockTint;
import net.zepalesque.redux.block.util.state.enums.GrassSize;
import net.zepalesque.redux.block.util.state.enums.PetalPrismaticness;

public class ReduxStates {
    public static final BooleanProperty SNOWY_TEXTURE = BooleanProperty.create("snowy_texture");
    public static final BooleanProperty SNOWLOGGED = BooleanProperty.create("snowlogged");

    public static final BooleanProperty SNOWED = BooleanProperty.create("snowed");

    public static final BooleanProperty HARVESTED = BooleanProperty.create("harvested");

    public static final IntegerProperty LEAF_LAYERS = IntegerProperty.create("layers", 1, 16);

    public static final IntegerProperty PRISMATICNESS = IntegerProperty.create("prismaticness", 0, 6);

    public static final IntegerProperty AGE_6 = IntegerProperty.create("age", 0, 6);

    public static final IntegerProperty EXTENDED_DISTANCE = IntegerProperty.create("distance", 1, ExtendedDistanceLeavesBlock.DECAY_DISTANCE);

    public static final BooleanProperty ENCHANTED = BooleanProperty.create("enchanted");

    public static final BooleanProperty NATURAL_GEN = BooleanProperty.create("natural_gen");

    // Flower Garland
    public static final BooleanProperty TOP_NORTH = BooleanProperty.create("top_north");
    public static final BooleanProperty BOTTOM_NORTH = BooleanProperty.create("bottom_north");
    public static final BooleanProperty TOP_SOUTH = BooleanProperty.create("top_south");
    public static final BooleanProperty BOTTOM_SOUTH = BooleanProperty.create("bottom_south");
    public static final BooleanProperty TOP_EAST = BooleanProperty.create("top_east");
    public static final BooleanProperty BOTTOM_EAST = BooleanProperty.create("bottom_east");
    public static final BooleanProperty TOP_WEST = BooleanProperty.create("top_west");
    public static final BooleanProperty BOTTOM_WEST = BooleanProperty.create("bottom_west");

    public static final EnumProperty<GrassBlockTint> GRASS_BLOCK_TINT = EnumProperty.create("grass_block_tint", GrassBlockTint.class);
    public static final EnumProperty<GrassSize> GRASS_SIZE = EnumProperty.create("grass_size", GrassSize.class);

    public static final EnumProperty<PetalPrismaticness> PETAL_1 = EnumProperty.create("petal_1_val", PetalPrismaticness.class, petalPrismaticness -> petalPrismaticness != PetalPrismaticness.NONE);
    public static final EnumProperty<PetalPrismaticness> PETAL_2 = EnumProperty.create("petal_2_val", PetalPrismaticness.class);
    public static final EnumProperty<PetalPrismaticness> PETAL_3 = EnumProperty.create("petal_3_val", PetalPrismaticness.class);
    public static final EnumProperty<PetalPrismaticness> PETAL_4 = EnumProperty.create("petal_4_val", PetalPrismaticness.class);

}
