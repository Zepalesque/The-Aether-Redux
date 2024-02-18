package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.builderdog.ancient_aether.block.AncientAetherBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import teamrazor.deepaether.init.DABlocks;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ReduxColors {

    public static Map<Biome, Integer> AETHER_GRASS_COLORS = new HashMap<>();
    public static ColorResolver AETHER_GRASS_RESOLVER = (biome, x, z) -> AETHER_GRASS_COLORS.getOrDefault(biome, ReduxBiomes.AETHER_GRASS_COLOR);


    public static void blockColors(RegisterColorHandlersEvent.Block event) {
        // Undo Aether's grass changes
        event.register((state, level, pos, index) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor(), Blocks.GRASS, Blocks.FERN);
        event.register((state, level, pos, index) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos) : GrassColor.getDefaultColor(), Blocks.TALL_GRASS, Blocks.LARGE_FERN);
        // Register Redux's stuff
        event.getBlockColors().register((state, level, pos, index) -> index == 1 ? level != null && pos != null  ? getAverageColor(level, pos, AETHER_GRASS_RESOLVER) : ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.getBlockColors().register((state, level, pos, index) -> state.hasProperty(ReduxStates.ENCHANTED) && state.getValue(ReduxStates.ENCHANTED) ? 0xFFFFFF : getColor(state, level, pos, index, 0), ReduxBlocks.SHORT_AETHER_GRASS.get(), ReduxBlocks.SPLITFERN.get());
        event.getBlockColors().register((state, level, pos, index) -> {
            int color = getColor(state, level, pos, 1, 1);
            return index == 1 ? color : index == 2 ? FastColor.ARGB32.color(255, (FastColor.ARGB32.red(color) + 255) / 2, (FastColor.ARGB32.green(color) + 255) / 2, (FastColor.ARGB32.blue(color) + 255) / 2) : 0xFFFFFF;
        }, ReduxBlocks.GOLDEN_CLOVER.get());
        event.getBlockColors().register((state, level, pos, index) -> getColor(state, level, pos, index, 1),
                ReduxBlocks.WYNDSPROUTS.get(),
                ReduxBlocks.POTTED_WYNDSPROUTS.get(),
                AetherBlocks.WHITE_FLOWER.get(),
                AetherBlocks.POTTED_WHITE_FLOWER.get(),
                AetherBlocks.PURPLE_FLOWER.get(),
                AetherBlocks.POTTED_PURPLE_FLOWER.get(),
                ReduxBlocks.SKYSPROUTS.get(),
                ReduxBlocks.POTTED_SKYSPROUTS.get(),
                ReduxBlocks.LUXWEED.get(),
                ReduxBlocks.POTTED_LUXWEED.get(),
                ReduxBlocks.BLIGHTSHADE.get(),
                ReduxBlocks.POTTED_BLIGHTSHADE.get(),
                ReduxBlocks.DAGGERBLOOM.get(),
                ReduxBlocks.POTTED_DAGGERBLOOM.get(),
                ReduxBlocks.AURUM.get(),
                ReduxBlocks.POTTED_AURUM.get(),
                ReduxBlocks.IRIDIA.get(),
                ReduxBlocks.POTTED_IRIDIA.get(),
                ReduxBlocks.LUMINA.get(),
                ReduxBlocks.POTTED_LUMINA.get(),
                ReduxBlocks.ZYATRIX.get(),
                ReduxBlocks.POTTED_ZYATRIX.get(),
                ReduxBlocks.SPIROLYCTIL.get(),
                ReduxBlocks.POTTED_SPIROLYCTIL.get(),
                ReduxBlocks.XAELIA_FLOWERS.get()
        );

        // Ancient Aether Compat
        if (Redux.ancientAetherCompat()) {
            event.getBlockColors().register((state, level, pos, index) -> level != null && pos != null  ? getAverageColor(level, pos, AETHER_GRASS_RESOLVER) : ReduxBiomes.AETHER_GRASS_COLOR, AncientAetherBlocks.SKY_GRASS.get());
            event.getBlockColors().register((state, level, pos, index) -> getColor(state, level, pos, index, 1),
                    AncientAetherBlocks.HIGHLAND_VIOLA.get(),
                    AncientAetherBlocks.POTTED_HIGHLAND_VIOLA.get(),
                    AncientAetherBlocks.WYND_THISTLE.get(),
                    AncientAetherBlocks.POTTED_WYND_THISTLE.get(),
                    AncientAetherBlocks.SAKURA_BLOSSOMS.get(),
                    AncientAetherBlocks.POTTED_SAKURA_BLOSSOMS.get(),
                    AncientAetherBlocks.SKY_BLUES.get(),
                    AncientAetherBlocks.POTTED_SKY_BLUES.get(),
                    AncientAetherBlocks.TRAPPED_SAKURA_BLOSSOMS.get()
            );
        }

        // Deep Aether compat
        if (Redux.deepAetherCompat()) {
            event.getBlockColors().register((state, level, pos, index) -> getColor(state, level, pos, index, 1),
                    DABlocks.RADIANT_ORCHID.get(),
                    DABlocks.POTTED_RADIANT_ORCHID.get(),
                    DABlocks.AERLAVENDER.get(),
                    DABlocks.POTTED_AERLAVENDER.get(),
                    DABlocks.TALL_AERLAVENDER.get(),
                    DABlocks.POTTED_TALL_AERLAVENDER.get(),
                    DABlocks.FEATHER_GRASS.get(),
                    DABlocks.SKY_TULIPS.get(),
                    DABlocks.POTTED_SKY_TULIPS.get(),
                    DABlocks.IASPOVE.get(),
                    DABlocks.POTTED_IASPOVE.get(),
                    DABlocks.GOLDEN_ASPESS.get(),
                    DABlocks.POTTED_GOLDEN_ASPESS.get(),
                    DABlocks.ECHAISY.get(),
                    DABlocks.POTTED_ECHAISY.get()
            );
            event.getBlockColors().register((state, level, pos, index) -> pos != null ? getColor(state, level, state.getValue(DoublePlantBlock.HALF) == DoubleBlockHalf.UPPER ? pos.below() : pos, index, 1)  : ReduxBiomes.AETHER_GRASS_COLOR,
                    DABlocks.TALL_FEATHER_GRASS.get()
            );
        }

    }

    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.WHITE_FLOWER.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.PURPLE_FLOWER.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SHORT_AETHER_GRASS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SPLITFERN.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.LUXWEED.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.WYNDSPROUTS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SKYSPROUTS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.XAELIA_FLOWERS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.AURUM.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASSLANDS_COLOR : 0xFFFFFF, ReduxBlocks.ZYATRIX.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.IRIDIA.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.DAGGERBLOOM.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.FROSTED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.LUMINA.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SPIROLYCTIL.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.BLIGHTSHADE.get());

        // Ancient Aether Compat
        if (Redux.ancientAetherCompat()) {
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AncientAetherBlocks.SKY_GRASS.get());

            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.FROZEN_GRASS_COLOR : 0xFFFFFF, AncientAetherBlocks.WYND_THISTLE.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AncientAetherBlocks.SKY_BLUES.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AncientAetherBlocks.SAKURA_BLOSSOMS.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AncientAetherBlocks.TRAPPED_SAKURA_BLOSSOMS.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AncientAetherBlocks.HIGHLAND_VIOLA.get());
        }
        // Deep AetherAa Compat
        if (Redux.deepAetherCompat()) {
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AERGLOW_GRASS_COLOR : 0xFFFFFF, DABlocks.RADIANT_ORCHID.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AERLAVENDER_GRASS_COLOR : 0xFFFFFF, DABlocks.AERLAVENDER.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AERLAVENDER_GRASS_COLOR : 0xFFFFFF, DABlocks.TALL_AERLAVENDER.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLUE_AERGLOW_GRASS_COLOR : 0xFFFFFF, DABlocks.FEATHER_GRASS.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLUE_AERGLOW_GRASS_COLOR : 0xFFFFFF, DABlocks.TALL_FEATHER_GRASS.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, DABlocks.SKY_TULIPS.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, DABlocks.IASPOVE.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, DABlocks.GOLDEN_ASPESS.get());
            event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, DABlocks.ECHAISY.get());
        }
    }

    public static int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index, int indexGoal) {
        return index == indexGoal ? level != null && pos != null ? level.getBlockState(pos.below()).is(ReduxBlocks.BLIGHTMOSS_BLOCK.get()) ? ReduxBiomes.BLIGHT_GRASS_COLOR : getAverageColor(level, pos, AETHER_GRASS_RESOLVER) : ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF;
    }

    private static int getAverageColor(BlockAndTintGetter level, BlockPos blockPos, ColorResolver colorResolver) {
        return level.getBlockTint(blockPos, colorResolver);
    }

    public static void resolvers(RegisterColorHandlersEvent.ColorResolvers event) {
        event.register(AETHER_GRASS_RESOLVER);
    }
}
