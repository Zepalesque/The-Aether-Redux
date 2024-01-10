package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.GrassBlockTint;
import net.zepalesque.redux.block.util.ReduxStates;
import net.zepalesque.redux.block.util.ShortGrassTint;
import net.zepalesque.redux.capability.living.VampireAmulet;
import net.zepalesque.redux.data.resource.ReduxBiomes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.accessory.VampireAmuletItem;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;
import net.zepalesque.redux.misc.ReduxTags;

import javax.annotation.Nullable;
import java.util.Optional;

public class ReduxClient {


    public static void registerItemModelProperties() {
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), new ResourceLocation("pull"), (stack, level, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return SubzeroCrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float)SubzeroCrossbowItem.getChargeDuration(stack);
            }
        });
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), new ResourceLocation("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !SubzeroCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), new ResourceLocation("charged"), (stack, level, entity, seed) -> SubzeroCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), new ResourceLocation("firework"), (stack, level, entity, seed) -> SubzeroCrossbowItem.isCharged(stack) && SubzeroCrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
        ItemProperties.register(ReduxItems.VAMPIRE_AMULET.get(), Redux.locate("active"), (stack, level, entity, seed) -> {
            if (entity == null) return 0.0F;
            Optional<Boolean> isActive = VampireAmulet.get(entity).map(VampireAmulet::canUseAbility);
            return VampireAmuletItem.validForActivation(stack) && isActive.isPresent() && isActive.get() ? 1.0F : 0.0F;
        });
    }
    public static void blockColors(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((state, level, pos, index) -> index == 1 ? level != null && pos != null  ? BiomeColors.getAverageGrassColor(level, pos) : ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.getBlockColors().register((state, level, pos, index) -> getColor(state, level, pos, index, 0), ReduxBlocks.AETHER_SHORT_GRASS.get());
        event.getBlockColors().register((state, level, pos, index) -> {
            int color = getColor(state, level, pos, 1, 1);
            return index == 1 ? color : index == 2 ? FastColor.ARGB32.color(255, (FastColor.ARGB32.red(color) + 255) / 2, (FastColor.ARGB32.green(color) + 255) / 2, (FastColor.ARGB32.blue(color) + 255) / 2) : 0xFFFFFF;
        }, ReduxBlocks.GOLDEN_CLOVER.get());
        event.getBlockColors().register((state, level, pos, index) -> getColor(state, level, pos, index, 1),
                ReduxBlocks.WYNDSPROUTS.get(),
                ReduxBlocks.SKYSPROUTS.get(),
                ReduxBlocks.LUXWEED.get(),
                ReduxBlocks.BLIGHTSHADE.get(),
                ReduxBlocks.AURUM.get(),
                ReduxBlocks.IRIDIA.get(),
                ReduxBlocks.LUMINA.get(),
                ReduxBlocks.SPIROLYCTIL.get()
        );

    }
    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.AETHER_SHORT_GRASS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.LUXWEED.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.WYNDSPROUTS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SKYSPROUTS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.AURUM.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.IRIDIA.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.DAGGERBLOOM.get());
//        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.GOLDEN_CLOVER.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.FROSTED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.LUMINA.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SPIROLYCTIL.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.BLIGHTSHADE.get());
    }

    public static int getColor(BlockState state, @Nullable BlockAndTintGetter level, @Nullable BlockPos pos, int index, int indexGoal) {
        return index == indexGoal ? level != null && pos != null ? level.getBlockState(pos.below()).is(ReduxBlocks.BLIGHTMOSS_BLOCK.get()) ? ReduxBiomes.BLIGHT_GRASS_COLOR : BiomeColors.getAverageGrassColor(level, pos) : ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF;
    }


}
