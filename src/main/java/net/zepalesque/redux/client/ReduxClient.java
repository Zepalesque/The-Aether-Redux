package net.zepalesque.redux.client;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterEntitySpectatorShadersEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.capability.living.VampireAmulet;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.resource.ReduxBiomes;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.item.accessory.VampireAmuletItem;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mod.EventBusSubscriber(
        modid = Redux.MODID,
        value = {Dist.CLIENT},
        bus = Mod.EventBusSubscriber.Bus.MOD
)
public class ReduxClient {

    public static Map<Biome, Integer> AETHER_GRASS_COLORS = new HashMap<>();

    public static ColorResolver AETHER_GRASS_RESOLVER = (biome, x, z) -> AETHER_GRASS_COLORS.getOrDefault(biome, ReduxBiomes.AETHER_GRASS_COLOR);

    @SubscribeEvent
    public static void registerSpectatorShaders(RegisterEntitySpectatorShadersEvent event) {
        event.register(ReduxEntityTypes.GLIMMERCOW.get(), Redux.locate("shaders/post/adrenaline_low.json"));
    }
/*

    @SubscribeEvent
    public static void shaderRegistry(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), Redux.locate("radial_blur"), DefaultVertexFormat.POSITION_TEX), shaderInstance -> ReduxRenderTypes.radialBlurShader = shaderInstance);
    }
*/


    public static void registerItemModelProperties() {
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), new ResourceLocation("pull"), (stack, level, entity, seed) -> {
            if (entity == null) {
                return 0.0F;
            } else {
                return SubzeroCrossbowItem.isCharged(stack) ? 0.0F : (float)(stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (float)SubzeroCrossbowItem.getChargeDuration(stack);
            }
        });
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Redux.locate("pulling"), (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !SubzeroCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Redux.locate("charged"), (stack, level, entity, seed) -> SubzeroCrossbowItem.isCharged(stack) ? 1.0F : 0.0F);
        ItemProperties.register(ReduxItems.SUBZERO_CROSSBOW.get(), Redux.locate("firework"), (stack, level, entity, seed) -> SubzeroCrossbowItem.isCharged(stack) && SubzeroCrossbowItem.containsChargedProjectile(stack, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F);
        ItemProperties.register(ReduxItems.VAMPIRE_AMULET.get(), Redux.locate("active"), (stack, level, entity, seed) -> {
            if (entity == null) return 0.0F;
            Optional<Boolean> isActive = VampireAmulet.get(entity).map(VampireAmulet::canUseAbility);
            return VampireAmuletItem.validForActivation(stack) && isActive.isPresent() && isActive.get() ? 1.0F : 0.0F;
        });

        ItemProperties.register(ReduxBlocks.AURUM.get().asItem(), Redux.locate("enchanted"), (stack, world, living, i) -> ReduxConfig.COMMON.enchanted_gilded_grass.get() ? 1.0F : 0.0F);
        ItemProperties.register(ReduxBlocks.GOLDEN_CLOVER.get().asItem(), Redux.locate("enchanted"), (stack, world, living, i) -> ReduxConfig.COMMON.enchanted_gilded_grass.get() ? 1.0F : 0.0F);
    }
    public static void blockColors(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((state, level, pos, index) -> index == 1 ? level != null && pos != null  ? getAverageColor(level, pos, AETHER_GRASS_RESOLVER) : ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.getBlockColors().register((state, level, pos, index) -> state.hasProperty(ReduxStates.ENCHANTED) && state.getValue(ReduxStates.ENCHANTED) ? 0xFFFFFF : getColor(state, level, pos, index, 0), ReduxBlocks.AETHER_SHORT_GRASS.get(), ReduxBlocks.SPLITFERN.get());
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

    }



    public static void itemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.AETHER_GRASS_BLOCK.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.WHITE_FLOWER.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, AetherBlocks.PURPLE_FLOWER.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.AETHER_SHORT_GRASS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 0 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SPLITFERN.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.LUXWEED.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.AETHER_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.WYNDSPROUTS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SKYSPROUTS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.XAELIA_FLOWERS.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.AURUM.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASSLANDS_COLOR : 0xFFFFFF, ReduxBlocks.ZYATRIX.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.IRIDIA.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.HIGHFIELDS_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.DAGGERBLOOM.get());
//        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.GILDED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.GOLDEN_CLOVER.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.FROSTED_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.LUMINA.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.SPIROLYCTIL.get());
        event.getItemColors().register((stack, tintIndex) -> tintIndex == 1 ? ReduxBiomes.BLIGHT_GRASS_COLOR : 0xFFFFFF, ReduxBlocks.BLIGHTSHADE.get());
        event.getItemColors().register((stack, index) -> index == 1 ? ReduxBiomes.GILDED_GRASS_COLOR : index == 2 ? FastColor.ARGB32.color(255, (FastColor.ARGB32.red(ReduxBiomes.GILDED_GRASS_COLOR) + 255) / 2, (FastColor.ARGB32.green(ReduxBiomes.GILDED_GRASS_COLOR) + 255) / 2, (FastColor.ARGB32.blue(ReduxBiomes.GILDED_GRASS_COLOR) + 255) / 2) : 0xFFFFFF, ReduxBlocks.GOLDEN_CLOVER.get());
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
