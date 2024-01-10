package net.zepalesque.redux.block.util;

import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.zepalesque.redux.block.natural.ExtendedDistanceLeavesBlock;

public class ReduxStates {
    public static final BooleanProperty SNOWY_TEXTURE = BooleanProperty.create("snowy_texture");
    public static final BooleanProperty SNOWLOGGED = BooleanProperty.create("snowlogged");

    public static final BooleanProperty SNOWED = BooleanProperty.create("snowed");

    public static final BooleanProperty HARVESTED = BooleanProperty.create("harvested");

    public static final IntegerProperty LEAF_LAYERS = IntegerProperty.create("layers", 1, 12);


    public static final IntegerProperty PRISMATICNESS = IntegerProperty.create("prismaticness", 0, 6);

    public static final IntegerProperty EXTENDED_DISTANCE = IntegerProperty.create("distance", 1, ExtendedDistanceLeavesBlock.DECAY_DISTANCE);

    public static final EnumProperty<ShortGrassType> GRASS_TYPE = EnumProperty.create("aether_grass_type", ShortGrassType.class);

    public static final BooleanProperty ENCHANTED = BooleanProperty.create("enchanted");

    public static final EnumProperty<ShortGrassTint> SHORT_GRASS_TINT = EnumProperty.create("short_grass_tint", ShortGrassTint.class);
    public static final EnumProperty<GrassBlockTint> GRASS_BLOCK_TINT = EnumProperty.create("grass_block_tint", GrassBlockTint.class);

    public static final EnumProperty<PetalPrismaticness> PETAL_1 = EnumProperty.create("petal_1_val", PetalPrismaticness.class, petalPrismaticness -> petalPrismaticness != PetalPrismaticness.NONE);
    public static final EnumProperty<PetalPrismaticness> PETAL_2 = EnumProperty.create("petal_2_val", PetalPrismaticness.class);
    public static final EnumProperty<PetalPrismaticness> PETAL_3 = EnumProperty.create("petal_3_val", PetalPrismaticness.class);
    public static final EnumProperty<PetalPrismaticness> PETAL_4 = EnumProperty.create("petal_4_val", PetalPrismaticness.class);

}
