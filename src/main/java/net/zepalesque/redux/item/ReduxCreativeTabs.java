package net.zepalesque.redux.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.item.util.VeridiumItem;

@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ReduxCreativeTabs {

    @SubscribeEvent
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();

        if (tab == AetherCreativeTabs.AETHER_BUILDING_BLOCKS.get()) {
            RegistryObject<? extends ItemLike> f1 = doBuildingWoods(AetherBlocks.GOLDEN_OAK_WOOD, event);

            putAfter(AetherBlocks.MOSSY_HOLYSTONE_WALL, ReduxBlocks.GILDED_HOLYSTONE, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE, ReduxBlocks.GILDED_HOLYSTONE_STAIRS, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_STAIRS, ReduxBlocks.GILDED_HOLYSTONE_SLAB, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_SLAB, ReduxBlocks.GILDED_HOLYSTONE_WALL, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_WALL, ReduxBlocks.BLIGHTMOSS_HOLYSTONE, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL, ReduxBlocks.FROSTED_HOLYSTONE, event);
            putAfter(ReduxBlocks.FROSTED_HOLYSTONE, ReduxBlocks.FROSTED_HOLYSTONE_STAIRS, event);
            putAfter(ReduxBlocks.FROSTED_HOLYSTONE_STAIRS, ReduxBlocks.FROSTED_HOLYSTONE_SLAB, event);
            putAfter(ReduxBlocks.FROSTED_HOLYSTONE_SLAB, ReduxBlocks.FROSTED_HOLYSTONE_WALL, event);

            putAfter(AetherBlocks.ICESTONE_WALL, ReduxBlocks.DIVINITE, event);
            putAfter(ReduxBlocks.DIVINITE, ReduxBlocks.DIVINITE_STAIRS, event);
            putAfter(ReduxBlocks.DIVINITE_STAIRS, ReduxBlocks.DIVINITE_SLAB, event);
            putAfter(ReduxBlocks.DIVINITE_SLAB, ReduxBlocks.DIVINITE_WALL, event);

            putAfter(AetherBlocks.ZANITE_BLOCK, ReduxBlocks.VERIDIUM_BLOCK, event);

            event.getEntries().put(new ItemStack(ReduxBlocks.VERIDIUM_LANTERN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().put(new ItemStack(ReduxBlocks.VERIDIUM_CHAIN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

        }

        if (tab == AetherCreativeTabs.AETHER_NATURAL_BLOCKS.get()) {

            putAfter(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, ReduxBlocks.AEVELIUM, event);


            putAfter(AetherBlocks.HOLYSTONE, ReduxBlocks.DIVINITE, event);
            putAfter(ReduxBlocks.DIVINITE, ReduxBlocks.HOLYSILT, event);

            putAfter(AetherBlocks.AETHER_DIRT, ReduxBlocks.COARSE_AETHER_DIRT, event);
            putAfter(ReduxBlocks.COARSE_AETHER_DIRT, ReduxBlocks.LIGHTROOT_AETHER_DIRT, event);

            putAfter(AetherBlocks.AETHER_FARMLAND, ReduxBlocks.AETHER_SHORT_GRASS, event);
            putAfter(ReduxBlocks.AETHER_SHORT_GRASS, ReduxBlocks.AEVELIUM_GROWTH, event);
            putAfter(ReduxBlocks.AEVELIUM_GROWTH, ReduxBlocks.AEVELIUM_SPROUTS, event);

            putAfter(AetherBlocks.MOSSY_HOLYSTONE, ReduxBlocks.GILDED_HOLYSTONE, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE, ReduxBlocks.BLIGHTMOSS_HOLYSTONE, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE, ReduxBlocks.FROSTED_HOLYSTONE, event);

            putAfter(AetherBlocks.QUICKSOIL, ReduxBlocks.BLIGHTMOSS_BLOCK, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_BLOCK, ReduxBlocks.BLIGHTMOSS_CARPET, event);

            doNaturalWoods(AetherBlocks.GOLDEN_OAK_LOG, event);

            putAfter(AetherBlocks.GOLDEN_OAK_LEAVES, ReduxBlocks.GOLDEN_LEAF_PILE, event);
            putAfter(ReduxBlocks.GOLDEN_LEAF_PILE, ReduxBlocks.GOLDEN_VINES, event);
            putAfter(ReduxBlocks.GOLDEN_VINES, ReduxBlocks.GILDED_OAK_LEAVES, event);
            putAfter(ReduxBlocks.GILDED_OAK_LEAVES, ReduxBlocks.GILDED_LEAF_PILE, event);
            putAfter(ReduxBlocks.GILDED_LEAF_PILE, ReduxBlocks.GILDED_VINES, event);

            putBefore(AetherBlocks.HOLIDAY_LEAVES, ReduxBlocks.BLIGHTED_SKYROOT_LEAVES, event);
            putBefore(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES, ReduxBlocks.BLIGHTWILLOW_LEAVES, event);
            putBefore(ReduxBlocks.BLIGHTWILLOW_LEAVES, ReduxBlocks.GLACIA_LEAVES, event);


            putAfter(AetherBlocks.DECORATED_HOLIDAY_LEAVES, ReduxBlocks.FLOWERING_FIELDSPROUT_LEAVES, event);
            putAfter(ReduxBlocks.FLOWERING_FIELDSPROUT_LEAVES, ReduxBlocks.CLOUD_CAP_BLOCK, event);
            putAfter(ReduxBlocks.CLOUD_CAP_BLOCK, ReduxBlocks.CLOUDCAP_SPORES, event);
            putAfter(ReduxBlocks.CLOUDCAP_SPORES, ReduxBlocks.SPRINGSHROOM_CAP_BLOCK, event);
            putAfter(ReduxBlocks.SPRINGSHROOM_CAP_BLOCK, ReduxBlocks.SPRINGSHROOM_SPORES, event);

            putAfter(AetherBlocks.GOLDEN_OAK_SAPLING, ReduxBlocks.BLIGHTWILLOW_SAPLING, event);
            putAfter(ReduxBlocks.BLIGHTWILLOW_SAPLING, ReduxBlocks.BLIGHTED_SKYROOT_SAPLING, event);
            putAfter(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING, ReduxBlocks.GLACIA_SAPLING, event);
            putAfter(ReduxBlocks.GLACIA_SAPLING, ReduxBlocks.GILDED_OAK_SAPLING, event);
            putAfter(ReduxBlocks.GILDED_OAK_SAPLING, ReduxBlocks.FLOWERING_FIELDSPROUT_SAPLING, event);

            putAfter(AetherBlocks.BERRY_BUSH, ReduxBlocks.ZANBERRY_SHRUB, event);
            putAfter(ReduxBlocks.ZANBERRY_SHRUB, ReduxBlocks.ZANBERRY_BUSH, event);


            putAfter(AetherBlocks.WHITE_FLOWER, ReduxBlocks.WYNDSPROUTS, event);
            putAfter(ReduxBlocks.WYNDSPROUTS, ReduxBlocks.SKYSPROUTS, event);
            putAfter(ReduxBlocks.SKYSPROUTS, ReduxBlocks.FIELDSPROUT_PETALS, event);
            putAfter(ReduxBlocks.FIELDSPROUT_PETALS, ReduxBlocks.IRIDIA, event);
            putAfter(ReduxBlocks.IRIDIA, ReduxBlocks.AURUM, event);
            putAfter(ReduxBlocks.AURUM, ReduxBlocks.GOLDEN_CLOVER, event);
            putAfter(ReduxBlocks.GOLDEN_CLOVER, ReduxBlocks.LUXWEED, event);
            putAfter(ReduxBlocks.LUXWEED, ReduxBlocks.SPIROLYCTIL, event);
            putAfter(ReduxBlocks.SPIROLYCTIL, ReduxBlocks.BLIGHTSHADE, event);
            putAfter(ReduxBlocks.BLIGHTSHADE, ReduxBlocks.LUMINA, event);
            putAfter(ReduxBlocks.LUMINA, ReduxBlocks.DAGGERBLOOM, event);
            putAfter(ReduxBlocks.DAGGERBLOOM, ReduxBlocks.FROSTED_FERN, event);
            putAfter(ReduxBlocks.CLOUDCAP_MUSHLING, ReduxBlocks.CLOUDCAP_MUSHLING, event);
            putAfter(ReduxBlocks.CLOUDCAP_MUSHLING, ReduxBlocks.SPRINGSHROOM, event);
            putAfter(ReduxBlocks.CLOUDCAP_MUSHLING, ReduxBlocks.TALL_CLOUDCAP, event);
            putAfter(ReduxBlocks.TALL_CLOUDCAP, ReduxBlocks.SPROUTING_LIGHTROOTS, event);

            putAfter(AetherBlocks.ZANITE_ORE, ReduxBlocks.VERIDIUM_ORE, event);
            putAfter(AetherBlocks.GRAVITITE_ORE, ReduxBlocks.RAW_VERIDIUM_BLOCK, event);
        }
        if (tab == AetherCreativeTabs.AETHER_REDSTONE_BLOCKS.get()) {

        }
        if (tab == AetherCreativeTabs.AETHER_FUNCTIONAL_BLOCKS.get()) {
            putAfter(AetherBlocks.AMBROSIUM_TORCH, ReduxBlocks.VERIDIUM_LANTERN, event);
            doSigns(AetherBlocks.SKYROOT_HANGING_SIGN, event);
        }
        if (tab == AetherCreativeTabs.AETHER_DUNGEON_BLOCKS.get()) {
            putAfter(AetherBlocks.CARVED_STONE, ReduxBlocks.CARVED_STONE_BRICKS, event);
            putAfter(ReduxBlocks.CARVED_STONE_BRICKS, ReduxBlocks.CARVED_STONE_BRICK_STAIRS, event);
            putAfter(ReduxBlocks.CARVED_STONE_BRICK_STAIRS, ReduxBlocks.CARVED_STONE_BRICK_SLAB, event);
            putAfter(ReduxBlocks.CARVED_STONE_BRICK_SLAB, ReduxBlocks.CARVED_STONE_BRICK_WALL, event);
            putAfter(ReduxBlocks.CARVED_STONE_BRICK_WALL, ReduxBlocks.CARVED_STONE_PILLAR, event);
        }
        if (tab == AetherCreativeTabs.AETHER_FOOD_AND_DRINKS.get()) {
            putAfter(AetherItems.ENCHANTED_BERRY, ReduxItems.BLUEBERRY_PIE, event);
            putAfter(ReduxItems.BLUEBERRY_PIE, ReduxItems.ENCHANTED_BLUEBERRY_PIE, event);
            putAfter(ReduxItems.ENCHANTED_BLUEBERRY_PIE, ReduxItems.OAT_MUFFIN, event);
            putAfter(ReduxItems.OAT_MUFFIN, ReduxItems.OATS, event);
            putAfter(ReduxItems.OATS, ReduxItems.LUXBUDS, event);
            putAfter(ReduxItems.LUXBUDS, ReduxItems.PURIFIED_LUXBUDS, event);

            putAfter(AetherItems.HEALING_STONE, ReduxItems.QUICKROOT, event);

            putAfter(AetherItems.GOLDEN_GUMMY_SWET, ReduxItems.VANILLA_GUMMY_SWET, event);


                putAfter(ReduxItems.VANILLA_GUMMY_SWET, Redux.aetherGenesisCompat() ? ReduxItems.VANILLA_SWET_JELLY : ReduxItems.BLUE_SWET_JELLY, event);
                if (!Redux.aetherGenesisCompat()) {
                    putAfter(ReduxItems.BLUE_SWET_JELLY, ReduxItems.GOLDEN_SWET_JELLY, event);
                    putAfter(ReduxItems.GOLDEN_SWET_JELLY, ReduxItems.VANILLA_SWET_JELLY, event);
            }
            putAfter(AetherItems.WHITE_APPLE, ReduxItems.ZANBERRY, event);

            putBefore(AetherItems.SKYROOT_MILK_BUCKET, ReduxItems.MOUSE_EAR_SOUP, event);

            if (!Redux.aetherGenesisCompat()) {
                event.getEntries().remove(new ItemStack(ReduxItems.MOUSE_EAR_SOUP.get()));
            }
        }
        if (tab == AetherCreativeTabs.AETHER_INGREDIENTS.get()) {
            putAfter(AetherItems.ZANITE_GEMSTONE, ReduxItems.RAW_VERIDIUM, event);
            putAfter(ReduxItems.RAW_VERIDIUM, ReduxItems.VERIDIUM_INGOT, event);
            putAfter(ReduxItems.VERIDIUM_INGOT, ReduxItems.VERIDIUM_NUGGET, event);
            putAfter(ReduxItems.VERIDIUM_NUGGET, ReduxItems.SENTRY_CIRCUIT, event);

            putAfter(AetherItems.SKYROOT_STICK, ReduxItems.BLIGHTED_SPORES, event);

            putAfter(AetherItems.AECHOR_PETAL, ReduxItems.OATS, event);
            putAfter(ReduxItems.OATS, ReduxItems.LUXBUDS, event);
            putAfter(ReduxItems.LUXBUDS, ReduxItems.BUNDLE_OF_AETHER_GRASS, event);
            putAfter(ReduxItems.BUNDLE_OF_AETHER_GRASS, ReduxItems.LIGHTROOT_CLUMP, event);

            putAfter(AetherItems.SWET_BALL, Redux.aetherGenesisCompat() ? ReduxItems.VANILLA_SWET_BALL : ReduxItems.GOLDEN_SWET_BALL, event);
            if (!Redux.aetherGenesisCompat()) { putAfter(ReduxItems.GOLDEN_SWET_BALL, ReduxItems.VANILLA_SWET_BALL, event); }

        }
        if (tab == AetherCreativeTabs.AETHER_ARMOR_AND_ACCESSORIES.get()) {

            putAfter(AetherItems.VALKYRIE_CAPE, ReduxItems.GRAND_VICTORY_MEDAL, event);
            putAfter(ReduxItems.GRAND_VICTORY_MEDAL, ReduxItems.COCKATRICE_FEATHER, event);

            putAfter(AetherItems.ICE_PENDANT, ReduxItems.ENCHANTED_RING, event);
            putAfter(ReduxItems.ENCHANTED_RING, ReduxItems.RING_OF_WISDOM, event);

            putAfter(AetherItems.SENTRY_BOOTS, ReduxItems.SENTRY_RING, event);

            putAfter(AetherItems.IRON_BUBBLE, ReduxItems.PHOENIX_EMBLEM, event);

            putAfter(AetherItems.REGENERATION_STONE, ReduxItems.VAMPIRE_AMULET, event);

            putAfter(AetherItems.SWET_CAPE, ReduxItems.AIRBOUND_CAPE, event);
        }
        if (tab == AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES.get()) {

            putBefore(AetherItems.GRAVITITE_SWORD, ReduxItems.INFUSED_VERIDIUM_HOE, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_HOE, ReduxItems.INFUSED_VERIDIUM_AXE, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_AXE, ReduxItems.INFUSED_VERIDIUM_PICKAXE, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_PICKAXE, ReduxItems.INFUSED_VERIDIUM_SHOVEL, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_SHOVEL, ReduxItems.INFUSED_VERIDIUM_SWORD, event);
            putBefore(ReduxItems.INFUSED_VERIDIUM_SWORD, ReduxItems.VERIDIUM_HOE, event);
            putBefore(ReduxItems.VERIDIUM_HOE, ReduxItems.VERIDIUM_AXE, event);
            putBefore(ReduxItems.VERIDIUM_AXE, ReduxItems.VERIDIUM_PICKAXE, event);
            putBefore(ReduxItems.VERIDIUM_PICKAXE, ReduxItems.VERIDIUM_SHOVEL, event);
            putBefore(ReduxItems.VERIDIUM_SHOVEL, ReduxItems.VERIDIUM_SWORD, event);

            putAfter(AetherItems.ENCHANTED_DART, ReduxItems.SPECTRAL_DART_SHOOTER, event);
            putAfter(ReduxItems.SPECTRAL_DART_SHOOTER, ReduxItems.SPECTRAL_DART, event);

            putAfter(AetherItems.PHOENIX_BOW, ReduxItems.SUBZERO_CROSSBOW, event);
            putAfter(AetherItems.MUSIC_DISC_ASCENDING_DAWN, ReduxItems.MUSIC_DISC_LABYRINTHS_VENGEANCE, event);
            doBoats(AetherItems.SKYROOT_CHEST_BOAT, event);
        }

        if (tab == AetherCreativeTabs.AETHER_SPAWN_EGGS.get())
        {
            putBefore(AetherItems.WHIRLWIND_SPAWN_EGG, ReduxItems.VANILLA_SWET_SPAWN_EGG, event);
        }
    }

    private static RegistryObject<? extends ItemLike> doBuildingWoods(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.Handlers.Wood.WOOD_HANDLERS)
        {
            boolean addSporingLogs = woodHandler.hasSporingLogs && woodHandler.sporingLog.isPresent() && woodHandler.sporingWood.isPresent();
            putAfter(b == null ? prevEntry : b, woodHandler.log, event);
            boolean lw = (Redux.aetherGenesisCompat() || woodHandler.alwaysLogWalls);
            boolean s = woodHandler.hasStrippedLogs;
            if (lw) { putAfter(woodHandler.log, woodHandler.logWall, event); }
            putAfter(lw ? woodHandler.logWall : woodHandler.log, woodHandler.wood, event);
            if (lw) { putAfter(woodHandler.wood, woodHandler.woodWall, event); }
            putAfter(lw ? woodHandler.woodWall : woodHandler.wood, s ? woodHandler.strippedLog.get() : addSporingLogs ? woodHandler.sporingLog.get() : woodHandler.planks, event);
            if (lw && s) { putAfter(woodHandler.strippedLog.get(), woodHandler.strippedLogWall.get(), event); }
            if (s) { putAfter(lw ? woodHandler.strippedLogWall.get() : woodHandler.strippedLog.get(), woodHandler.strippedWood.get(), event); }
            if (lw && s) { putAfter(woodHandler.strippedWood.get(), woodHandler.strippedWoodWall.get(), event); }
            if (s) { putAfter(lw ? woodHandler.strippedWoodWall.get() : woodHandler.strippedWood.get(), addSporingLogs ? woodHandler.sporingLog.get() : woodHandler.planks, event); }
            if (addSporingLogs)
            {
                putAfter(woodHandler.sporingLog.get(), woodHandler.sporingWood.get(), event);
                putAfter(woodHandler.sporingWood.get(), woodHandler.planks, event);
            }
            putAfter(woodHandler.planks, woodHandler.stairs, event);
            putAfter(woodHandler.stairs, woodHandler.slab, event);
            putAfter(woodHandler.slab, woodHandler.fence, event);
            putAfter(woodHandler.fence, woodHandler.fenceGate, event);
            putAfter(woodHandler.fenceGate, woodHandler.door, event);
            putAfter(woodHandler.door, woodHandler.trapdoor, event);
            putAfter(woodHandler.trapdoor, woodHandler.pressurePlate, event);
            putAfter(woodHandler.pressurePlate, woodHandler.button, event);
            b = woodHandler.button;
        }
        return b;
    }
    private static RegistryObject<? extends ItemLike> doNaturalWoods(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.Handlers.Wood.WOOD_HANDLERS)
        {
            boolean addSporingLogs = woodHandler.hasSporingLogs && woodHandler.sporingLog.isPresent() && woodHandler.sporingWood.isPresent();
            putAfter(b == null ? prevEntry : b, woodHandler.log, event);
            if (Redux.aetherGenesisCompat() || woodHandler.alwaysLogWalls) {
                putAfter(woodHandler.log, woodHandler.logWall, event);
            }
            b = Redux.aetherGenesisCompat() || woodHandler.alwaysLogWalls ? woodHandler.logWall : woodHandler.log;
            if (addSporingLogs)
            {
                putAfter(b, woodHandler.sporingLog.get(), event);
                putAfter(woodHandler.sporingLog.get(), woodHandler.sporingWood.get(), event);
                b = woodHandler.sporingWood.get();
            }
        }
        return b;
    }
    private static RegistryObject<? extends ItemLike> doSigns(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.Handlers.Wood.WOOD_HANDLERS)
        {
            putAfter(b == null ? prevEntry : b, woodHandler.signItem, event);
            putAfter(woodHandler.signItem, woodHandler.hangingSignItem, event);
            b = woodHandler.hangingSignItem;
        }
        return b;
    }

    private static RegistryObject<? extends ItemLike> doBoats(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.Handlers.Wood.WOOD_HANDLERS)
        {
            putAfter(b == null ? prevEntry : b, woodHandler.boatItem, event);
            putAfter(woodHandler.boatItem, woodHandler.chestBoatItem, event);
            b = woodHandler.chestBoatItem;
        }
        return b;
    }
    private static void doRedstoneWoods(RegistryObject<? extends ItemLike>  pressurePlate, RegistryObject<? extends ItemLike>  door, RegistryObject<? extends ItemLike>  button, RegistryObject<? extends ItemLike>  trapdoor, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> plate = null;
        RegistryObject<? extends ItemLike> dr = null;
        RegistryObject<? extends ItemLike> btn = null;
        RegistryObject<? extends ItemLike> td = null;
        for (WoodHandler woodHandler : Redux.Handlers.Wood.WOOD_HANDLERS)
        {
            putAfter(plate == null ? pressurePlate : plate, woodHandler.pressurePlate, event);
            plate = woodHandler.pressurePlate;
            putAfter(dr == null ? door : dr, woodHandler.door, event);
            dr = woodHandler.door;
            putAfter(btn == null ? button : btn, woodHandler.button, event);
            btn = woodHandler.button;
            putAfter(td == null ? trapdoor : td, woodHandler.trapdoor, event);
            td = woodHandler.trapdoor;
        }
    }

    private static void putAfter(RegistryObject<? extends ItemLike> itemBefore, RegistryObject<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event)
    {
        event.getEntries().putAfter(new ItemStack(itemBefore.get()), new ItemStack(insertedItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
    private static void putBefore(RegistryObject<? extends ItemLike> itemAfter, RegistryObject<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event)
    {
        ItemStack stackAfter = new ItemStack(itemAfter.get());
        if (itemAfter.get() instanceof VeridiumItem veridium && veridium.isCharged(stackAfter))
        {
            CompoundTag compound = VeridiumItem.createCompoundFor(stackAfter);
            compound.putByte("ambrosium_charge", VeridiumItem.MAXIUMUM_VERIDIUM_INFUSION);
            stackAfter.setTag(compound);
        }
        ItemStack stackInserted = new ItemStack(insertedItem.get());
        if (insertedItem.get() instanceof VeridiumItem veridium && veridium.isCharged(stackInserted))
        {
            CompoundTag compound1 = VeridiumItem.createCompoundFor(stackInserted);
            compound1.putByte("ambrosium_charge", VeridiumItem.MAXIUMUM_VERIDIUM_INFUSION);
            stackInserted.setTag(compound1);
        }
        event.getEntries().putBefore(stackAfter, stackInserted, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

}
