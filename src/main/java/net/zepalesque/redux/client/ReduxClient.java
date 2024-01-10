package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.accessory.VampireAmuletItem;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;
import net.zepalesque.redux.misc.ReduxTags;

import javax.annotation.Nullable;
import java.util.Optional;

public class ReduxClient {
    public static final int AETHER_GRASS_COLOR = 0xB1FFCB;
    public static final int BLIGHT_GRASS_COLOR = 0xD5BAFF;
    public static final int FROSTED_GRASS_COLOR = 0xCCF7FF;

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
        event.getBlockColors().register((state, level, pos, index) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : AETHER_GRASS_COLOR, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.getBlockColors().register((state, level, pos, index) -> level != null && pos != null ? level.getBlockState(pos.below()).is(ReduxBlocks.BLIGHTMOSS_BLOCK.get()) ? BLIGHT_GRASS_COLOR : BiomeColors.getAverageGrassColor(level, pos) : AETHER_GRASS_COLOR, ReduxBlocks.AETHER_SHORT_GRASS.get());
    }


}
