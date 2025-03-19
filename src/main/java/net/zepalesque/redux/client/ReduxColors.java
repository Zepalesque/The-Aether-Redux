package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.util.TintableSet;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.unity.client.UnityColors;
import net.zepalesque.zenith.api.blockset.BlockSet;
import net.zepalesque.zenith.api.blockset.type.AbstractFlowerSet;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class ReduxColors {

    public static class Tints {
        public static final int GILDED_GRASS_COLOR = 0xFFF3FFDD;
        public static final int BLIGHT_GRASS_COLOR = 0xFFBEAEE5;

        public static final int BLEAKMOSS_GRASS_COLOR = 0xFFB79EC1;
    }
    
    public static final BlockColor PERMABLIGHT = (state, level, pos, index) -> index == 1 ? Tints.BLIGHT_GRASS_COLOR : 0xFFFFFFFF;
    public static final ItemColor ITEM_PERMABLIGHT = (stack, index) -> index == 1 ? Tints.BLIGHT_GRASS_COLOR : 0xFFFFFFFF;
    
    public static Integer reduxColors(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index, Predicate<Integer> indexGoal, boolean useBelowProperties) {
        if (level == null || pos == null) return null;
        if (level.getBlockState(pos.below()).is(ReduxTags.Blocks.SHORT_AETHER_GRASS_BLEAKMOSS_COLORING))
            return Tints.BLEAKMOSS_GRASS_COLOR;
        return null;
    }

    public static void blockColors(RegisterColorHandlersEvent.Block event) {
        Redux.LOGGER.debug("Beginning block color registration for the Aether: Redux");

        event.register(UnityColors.OVERLAY_INHERITING,
            AetherBlocks.WHITE_FLOWER.get(),
            AetherBlocks.POTTED_WHITE_FLOWER.get(),
            AetherBlocks.PURPLE_FLOWER.get(),
            AetherBlocks.POTTED_PURPLE_FLOWER.get(),
            ReduxBlocks.WYNDSPROUTS.get()
        );
        
        event.register(PERMABLIGHT,
            ReduxBlocks.PERMABLIGHT_AETHER_GRASS_BLOCK.get()
        );
        
        for (BlockSet set : Redux.BLOCK_SETS)
            if (set instanceof TintableSet tintable && set instanceof AbstractFlowerSet flowerSet)
                event.register((state, level, pos, index) -> UnityColors.getColor(state, level, pos, index, i -> i == tintable.getTintIndex(), true), flowerSet.flower().get(), flowerSet.pot().get());
    }

    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        Redux.LOGGER.debug("Beginning item color registration for the Aether: Redux");
        event.register(UnityColors.ITEM_OVERLAY_AETHER,
                ReduxBlocks.WYNDSPROUTS.get()
        );
        
        event.register(ITEM_PERMABLIGHT,
            ReduxBlocks.PERMABLIGHT_AETHER_GRASS_BLOCK.get()
        );

        for (BlockSet set : Redux.BLOCK_SETS)
            if (set instanceof TintableSet tintable && set instanceof AbstractFlowerSet flowerSet)
                event.register((stack, tintIndex) -> tintIndex == tintable.getTintIndex() ? tintable.getDefaultItemTint() : 0xFFFFFFFF, flowerSet.flower().get());
    }
}
