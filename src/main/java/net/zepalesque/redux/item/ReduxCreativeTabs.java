package net.zepalesque.redux.item;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherCreativeTabs;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether_genesis.block.GenesisBlocks;
import com.legacy.lost_aether.registry.LCBlocks;
import net.builderdog.ancient_aether.block.AncientAetherBlocks;
import net.builderdog.ancient_aether.item.AncientAetherItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.item.util.VeridiumItem;
import net.zepalesque.redux.misc.ReduxTags;

@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ReduxCreativeTabs {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void buildCreativeModeTabs(BuildCreativeModeTabContentsEvent event) {
        CreativeModeTab tab = event.getTab();

        if (tab == AetherCreativeTabs.AETHER_BUILDING_BLOCKS.get()) {
            RegistryObject<? extends ItemLike> f1 = doBuildingWoods(AetherBlocks.GOLDEN_OAK_WOOD, event);

            putBefore(Redux.WoodHandlers.MINTY.log, ReduxBlocks.PEPPERMINT_BLOCK, event);
            putBefore(ReduxBlocks.PEPPERMINT_BLOCK, ReduxBlocks.PEPPERMINT_LOG, event);

            putAfter(f1, ReduxBlocks.PAPER_PILLAR, event);
            putAfter(ReduxBlocks.PAPER_PILLAR, ReduxBlocks.PAPER_BLOCK, event);

            putAfter(AetherBlocks.MOSSY_HOLYSTONE_WALL, ReduxBlocks.GILDED_HOLYSTONE, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE, ReduxBlocks.GILDED_HOLYSTONE_STAIRS, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_STAIRS, ReduxBlocks.GILDED_HOLYSTONE_SLAB, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_SLAB, ReduxBlocks.GILDED_HOLYSTONE_WALL, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE_WALL, ReduxBlocks.BLIGHTMOSS_HOLYSTONE, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB, ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL, event);


            putAfter(AetherBlocks.ICESTONE_WALL, ReduxBlocks.DIVINITE, event);
            putAfter(ReduxBlocks.DIVINITE, ReduxBlocks.DIVINITE_STAIRS, event);
            putAfter(ReduxBlocks.DIVINITE_STAIRS, ReduxBlocks.DIVINITE_SLAB, event);
            putAfter(ReduxBlocks.DIVINITE_SLAB, ReduxBlocks.DIVINITE_WALL, event);
            putAfter(ReduxBlocks.DIVINITE_WALL, ReduxBlocks.SENTRITE, event);
            putAfter(ReduxBlocks.SENTRITE, ReduxBlocks.SENTRITE_STAIRS, event);
            putAfter(ReduxBlocks.SENTRITE_STAIRS, ReduxBlocks.SENTRITE_SLAB, event);
            putAfter(ReduxBlocks.SENTRITE_SLAB, ReduxBlocks.SENTRITE_WALL, event);
            putAfter(ReduxBlocks.SENTRITE_WALL, ReduxBlocks.SENTRITE_BRICKS, event);
            putAfter(ReduxBlocks.SENTRITE_BRICKS, ReduxBlocks.SENTRITE_BRICK_STAIRS, event);
            putAfter(ReduxBlocks.SENTRITE_BRICK_STAIRS, ReduxBlocks.SENTRITE_BRICK_SLAB, event);
            putAfter(ReduxBlocks.SENTRITE_BRICK_SLAB, ReduxBlocks.SENTRITE_BRICK_WALL, event);
            putAfter(ReduxBlocks.SENTRITE_BRICK_WALL, ReduxBlocks.SHELL_SHINGLES, event);
            putAfter(ReduxBlocks.SHELL_SHINGLES, ReduxBlocks.SHELL_SHINGLE_STAIRS, event);
            putAfter(ReduxBlocks.SHELL_SHINGLE_STAIRS, ReduxBlocks.SHELL_SHINGLE_SLAB, event);
            putAfter(ReduxBlocks.SHELL_SHINGLE_SLAB, ReduxBlocks.SHELL_SHINGLE_WALL, event);
            putAfter(ReduxBlocks.SHELL_SHINGLE_WALL, ReduxBlocks.ENCHANTED_SHELL_SHINGLES, event);
            putAfter(ReduxBlocks.ENCHANTED_SHELL_SHINGLES, ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS, event);
            putAfter(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS, ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB, event);
            putAfter(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB, ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL, event);
            putAfter(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL, ReduxBlocks.ROCK_CANDY, event);
            putAfter(ReduxBlocks.ROCK_CANDY, ReduxBlocks.ROCK_CANDY_STAIRS, event);
            putAfter(ReduxBlocks.ROCK_CANDY_STAIRS, ReduxBlocks.ROCK_CANDY_SLAB, event);
            putAfter(ReduxBlocks.ROCK_CANDY_SLAB, ReduxBlocks.ROCK_CANDY_WALL, event);

            putAfter(AetherBlocks.ZANITE_BLOCK, ReduxBlocks.VERIDIUM_BLOCK, event);
            putAfter(ReduxBlocks.VERIDIUM_BLOCK, ReduxBlocks.CRYSTAL_GOLD_BLOCK, event);

            event.getEntries().put(new ItemStack(ReduxBlocks.VERIDIUM_LANTERN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.getEntries().put(new ItemStack(ReduxBlocks.VERIDIUM_CHAIN.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);

            if (ReduxConfig.COMMON.gravitite_ingot.get()) {
                putBefore(AetherBlocks.ENCHANTED_GRAVITITE, ReduxBlocks.GRAVITITE_BLOCK, event);
                event.getEntries().remove(new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
            }

        }

        if (tab == AetherCreativeTabs.AETHER_NATURAL_BLOCKS.get()) {



            putAfter(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK, ReduxBlocks.AVELIUM, event);
            putAfter(ReduxBlocks.AVELIUM, ReduxBlocks.DEEP_GRASS_BLOCK, event);
            putAfter(ReduxBlocks.DEEP_GRASS_BLOCK, ReduxBlocks.SUGARGRASS_BLOCK, event);
            putAfter(ReduxBlocks.SUGARGRASS_BLOCK, ReduxBlocks.COCOA_SOIL, event);

            putAfter(AetherBlocks.AEROGEL, ReduxBlocks.CHARCOAL_BLOCK, event);
            putAfter(ReduxBlocks.CHARCOAL_BLOCK, ReduxBlocks.HARDENED_SYRUP, event);

            putAfter(AetherBlocks.HOLYSTONE, ReduxBlocks.DIVINITE, event);
            putAfter(ReduxBlocks.DIVINITE, ReduxBlocks.SENTRITE, event);
            putAfter(ReduxBlocks.SENTRITE, ReduxBlocks.HOLYSILT, event);
            putAfter(ReduxBlocks.HOLYSILT, ReduxBlocks.ROCK_CANDY, event);

            if (!Redux.deepAetherCompat()) {
                putAfter(AetherBlocks.AETHER_DIRT, ReduxBlocks.COARSE_AETHER_DIRT, event);
            }
            
            putAfter(AetherBlocks.AETHER_FARMLAND, ReduxBlocks.SHORT_AETHER_GRASS, event);
            putAfter(ReduxBlocks.SHORT_AETHER_GRASS, ReduxBlocks.AVELIUM_ROOTS, event);
            putAfter(ReduxBlocks.AVELIUM_ROOTS, ReduxBlocks.SHORT_DEEP_GRASS, event);
            putAfter(ReduxBlocks.SHORT_DEEP_GRASS, ReduxBlocks.SPLITFERN, event);
            putAfter(ReduxBlocks.SPLITFERN, ReduxBlocks.AVELIUM_SPROUTS, event);
            putAfter(ReduxBlocks.AVELIUM_SPROUTS, ReduxBlocks.SUGARSPROUTS, event);

            putAfter(AetherBlocks.MOSSY_HOLYSTONE, ReduxBlocks.GILDED_HOLYSTONE, event);
            putAfter(ReduxBlocks.GILDED_HOLYSTONE, ReduxBlocks.BLIGHTMOSS_HOLYSTONE, event);

            putAfter(AetherBlocks.QUICKSOIL, ReduxBlocks.BLIGHTMOSS_BLOCK, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_BLOCK, ReduxBlocks.BLIGHTMOSS_CARPET, event);
            putAfter(ReduxBlocks.BLIGHTMOSS_CARPET, ReduxBlocks.FUNGAL_GROWTH, event);
            putAfter(ReduxBlocks.FUNGAL_GROWTH, ReduxBlocks.FUNGAL_CARPET, event);

            doNaturalWoods(AetherBlocks.GOLDEN_OAK_LOG, event);
            putBefore(Redux.WoodHandlers.MINTY.log, ReduxBlocks.PEPPERMINT_BLOCK, event);
            putBefore(ReduxBlocks.PEPPERMINT_BLOCK, ReduxBlocks.PEPPERMINT_LOG, event);

            putAfter(AetherBlocks.GOLDEN_OAK_LEAVES, ReduxBlocks.GOLDEN_LEAF_PILE, event);
            putAfter(ReduxBlocks.GOLDEN_LEAF_PILE, ReduxBlocks.GOLDEN_VINES, event);
            putAfter(ReduxBlocks.GOLDEN_VINES, ReduxBlocks.GILDED_OAK_LEAVES, event);
            putAfter(ReduxBlocks.GILDED_OAK_LEAVES, ReduxBlocks.GILDED_LEAF_PILE, event);
            putAfter(ReduxBlocks.GILDED_LEAF_PILE, ReduxBlocks.GILDED_VINES, event);

            putBefore(AetherBlocks.HOLIDAY_LEAVES, ReduxBlocks.PURPLE_GLACIA_LEAVES, event);
            putBefore(ReduxBlocks.PURPLE_GLACIA_LEAVES, ReduxBlocks.GLACIA_LEAVES, event);
            putBefore(ReduxBlocks.GLACIA_LEAVES, ReduxBlocks.BLIGHTED_SKYROOT_LEAVES, event);
            putBefore(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES, ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, event);
            putBefore(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, ReduxBlocks.BLIGHTWILLOW_LEAVES, event);

            putAfter(AetherBlocks.GOLDEN_AERCLOUD, ReduxBlocks.BLIGHTED_AERCLOUD, event);
            putAfter(ReduxBlocks.BLIGHTED_AERCLOUD, ReduxBlocks.RAINBOW_AERCLOUD, event);

            if (!ReduxConfig.COMMON.classic_skyfields.get()) {
                putAfter(AetherBlocks.DECORATED_HOLIDAY_LEAVES, ReduxBlocks.FIELDSPROOT_LEAVES, event);
                putAfter(ReduxBlocks.FIELDSPROOT_LEAVES, ReduxBlocks.CLOUD_CAP_BLOCK, event);
            } else {
                putAfter(AetherBlocks.DECORATED_HOLIDAY_LEAVES, ReduxBlocks.PRISMATIC_FIELDSPROOT_LEAVES, event);
                putAfter(ReduxBlocks.PRISMATIC_FIELDSPROOT_LEAVES, ReduxBlocks.AZURE_FIELDSPROOT_LEAVES, event);
                putAfter(ReduxBlocks.AZURE_FIELDSPROOT_LEAVES, ReduxBlocks.SPECTRAL_FIELDSPROOT_LEAVES, event);
                putAfter(ReduxBlocks.SPECTRAL_FIELDSPROOT_LEAVES, ReduxBlocks.CLOUD_CAP_BLOCK, event);
            }
            putAfter(ReduxBlocks.CLOUD_CAP_BLOCK, ReduxBlocks.CLOUDCAP_SPORES, event);
            putAfter(ReduxBlocks.CLOUDCAP_SPORES, ReduxBlocks.JELLYSHROOM_JELLY_BLOCK, event);
            putAfter(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK, ReduxBlocks.PINK_CANDYFLOSS_BLOCK, event);
            putAfter(ReduxBlocks.PINK_CANDYFLOSS_BLOCK, ReduxBlocks.BLUE_CANDYFLOSS_BLOCK, event);

            putAfter(AetherBlocks.GOLDEN_OAK_SAPLING, ReduxBlocks.BLIGHTWILLOW_SAPLING, event);
            putAfter(ReduxBlocks.BLIGHTWILLOW_SAPLING, ReduxBlocks.BLIGHTED_SKYROOT_SAPLING, event);
            putAfter(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING, ReduxBlocks.GLACIA_SAPLING, event);
            putAfter(ReduxBlocks.GLACIA_SAPLING, ReduxBlocks.PURPLE_GLACIA_SAPLING, event);
            putAfter(ReduxBlocks.PURPLE_GLACIA_SAPLING, ReduxBlocks.GILDED_OAK_SAPLING, event);
            if (!ReduxConfig.COMMON.classic_skyfields.get()) {
                putAfter(ReduxBlocks.GILDED_OAK_SAPLING, ReduxBlocks.FIELDSPROOT_SAPLING, event);
            } else {
                putAfter(ReduxBlocks.GILDED_OAK_SAPLING, ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING, event);
                putAfter(ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING, ReduxBlocks.AZURE_FIELDSPROOT_SAPLING, event);
                putAfter(ReduxBlocks.AZURE_FIELDSPROOT_SAPLING, ReduxBlocks.SPECTRAL_FIELDSPROOT_SAPLING, event);
            }

            if (Redux.lostAetherCompat()) {
                event.getEntries().remove(new ItemStack(LCBlocks.crystal_sapling));
            }

            putAfter(ReduxBlocks.GILDED_OAK_SAPLING, ReduxBlocks.CRYSTAL_SAPLING, event);
            putAfter(ReduxBlocks.CRYSTAL_SAPLING, ReduxBlocks.CRYSTAL_FRUIT_SAPLING, event);
            putAfter(ReduxBlocks.CRYSTAL_FRUIT_SAPLING, ReduxBlocks.PEPPERMINT_BARKLING, event);
            putAfter(ReduxBlocks.PEPPERMINT_BARKLING, ReduxBlocks.BLUE_COTTON_CANDY, event);
            putAfter(ReduxBlocks.BLUE_COTTON_CANDY, ReduxBlocks.PINK_COTTON_CANDY, event);

            putAfter(AetherBlocks.BERRY_BUSH, ReduxBlocks.ZANBERRY_BUSH_STEM, event);
            putAfter(ReduxBlocks.ZANBERRY_BUSH_STEM, ReduxBlocks.ZANBERRY_BUSH, event);


            putAfter(AetherBlocks.WHITE_FLOWER, ReduxBlocks.WYNDSPROUTS, event);
            putAfter(ReduxBlocks.WYNDSPROUTS, ReduxBlocks.SKYSPROUTS, event);
            putAfter(ReduxBlocks.SKYSPROUTS, ReduxBlocks.FIELDSPROOT_PETALS, event);
            putAfter(ReduxBlocks.FIELDSPROOT_PETALS, ReduxBlocks.IRIDIA, event);
            putAfter(ReduxBlocks.IRIDIA, ReduxBlocks.XAELIA_PATCH, event);
            putAfter(ReduxBlocks.XAELIA_PATCH, ReduxBlocks.AURUM, event);
            putAfter(ReduxBlocks.AURUM, ReduxBlocks.GOLDEN_CLOVER, event);
            putAfter(ReduxBlocks.GOLDEN_CLOVER, ReduxBlocks.ZYATRIX, event);
            putAfter(ReduxBlocks.ZYATRIX, ReduxBlocks.EDELWEISS, event);
            putAfter(ReduxBlocks.EDELWEISS, ReduxBlocks.LUXWEED, event);
            putAfter(ReduxBlocks.LUXWEED, ReduxBlocks.SPIROLYCTIL, event);
            putAfter(ReduxBlocks.SPIROLYCTIL, ReduxBlocks.BLIGHTSHADE, event);
            putAfter(ReduxBlocks.BLIGHTSHADE, ReduxBlocks.LUMINA, event);
            putAfter(ReduxBlocks.LUMINA, ReduxBlocks.DAGGERBLOOM, event);
            putAfter(ReduxBlocks.DAGGERBLOOM, ReduxBlocks.THERATIP, event);
            putAfter(ReduxBlocks.THERATIP, ReduxBlocks.CLOUDCAP_MUSHLING, event);
            putAfter(ReduxBlocks.CLOUDCAP_MUSHLING, ReduxBlocks.JELLYSHROOM, event);
            putAfter(ReduxBlocks.JELLYSHROOM, ReduxBlocks.SHIMMERSTOOL, event);
            putAfter(ReduxBlocks.SHIMMERSTOOL, ReduxBlocks.FLAREBLOSSOM, event);
            putAfter(ReduxBlocks.FLAREBLOSSOM, ReduxBlocks.INFERNIA, event);
            putAfter(ReduxBlocks.INFERNIA, ReduxBlocks.VANILLA, event);
            putAfter(ReduxBlocks.VANILLA, ReduxBlocks.FLAWLESS_BLOOM, event);
            putAfter(ReduxBlocks.FLAWLESS_BLOOM, ReduxBlocks.ROOTROSE, event);

            putAfter(AetherBlocks.ZANITE_ORE, ReduxBlocks.VERIDIUM_ORE, event);
            putAfter(ReduxBlocks.VERIDIUM_ORE, ReduxBlocks.RAW_VERIDIUM_BLOCK, event);
            if (ReduxConfig.COMMON.raw_ores.get()) {
                putAfter(AetherBlocks.GRAVITITE_ORE, ReduxBlocks.RAW_GRAVITITE_BLOCK, event);
                putAfter(ReduxBlocks.RAW_GRAVITITE_BLOCK, ReduxBlocks.POPROCK_ORE, event);
            } else {
                putAfter(AetherBlocks.GRAVITITE_ORE, ReduxBlocks.POPROCK_ORE, event);
            }
            putAfter(ReduxBlocks.POPROCK_ORE, ReduxBlocks.CRYSTAL_GOLD_ORE, event);
            putAfter(ReduxBlocks.CRYSTAL_GOLD_ORE, ReduxBlocks.RAW_CRYSTAL_GOLD_BLOCK, event);
            if (Redux.ancientAetherCompat()) {
                putAfter(AncientAetherBlocks.VALKYRUM_ORE, ReduxBlocks.RAW_VALKYRUM_BLOCK, event);
            }
            if (Redux.aetherGenesisCompat()) {
                putAfter(GenesisBlocks.PURPLE_CRYSTAL_TREE_SAPLING, ReduxBlocks.PURPLE_CRYSTAL_FRUIT_SAPLING, event);
            }
        }
        if (tab == AetherCreativeTabs.AETHER_REDSTONE_BLOCKS.get()) {
            if (ReduxConfig.COMMON.gravitite_ingot.get()) {
                putBefore(AetherBlocks.ENCHANTED_GRAVITITE, ReduxBlocks.GRAVITITE_BLOCK, event);
                event.getEntries().remove(new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
            }
        }
        if (tab == AetherCreativeTabs.AETHER_FUNCTIONAL_BLOCKS.get()) {
            putAfter(AetherBlocks.AMBROSIUM_TORCH, ReduxBlocks.VERIDIUM_LANTERN, event);
            doSigns(AetherBlocks.SKYROOT_HANGING_SIGN, event);
            doBookshelves(AetherBlocks.SKYROOT_BOOKSHELF, event);
        }
        if (tab == AetherCreativeTabs.AETHER_DUNGEON_BLOCKS.get()) {
            putAfter(AetherBlocks.CARVED_STONE, ReduxBlocks.CARVED_STONE_BRICKS, event);
            putAfter(ReduxBlocks.CARVED_STONE_BRICKS, ReduxBlocks.CARVED_STONE_BRICK_STAIRS, event);
            putAfter(ReduxBlocks.CARVED_STONE_BRICK_STAIRS, ReduxBlocks.CARVED_STONE_BRICK_SLAB, event);
            putAfter(ReduxBlocks.CARVED_STONE_BRICK_SLAB, ReduxBlocks.CARVED_STONE_BRICK_WALL, event);
            putAfter(ReduxBlocks.CARVED_STONE_BRICK_WALL, ReduxBlocks.CARVED_STONE_PILLAR, event);


            putAfter(AetherBlocks.TREASURE_DOORWAY_LIGHT_HELLFIRE_STONE, ReduxBlocks.HOLEFIRE_STONE, event);
            putAfter(ReduxBlocks.HOLEFIRE_STONE, ReduxBlocks.GLOWY_HOLEFIRE_STONE, event);
            putAfter(ReduxBlocks.GLOWY_HOLEFIRE_STONE, ReduxBlocks.HOLEFIRE_STAIRS, event);
            putAfter(ReduxBlocks.HOLEFIRE_STAIRS, ReduxBlocks.HOLEFIRE_WALL, event);
            putAfter(ReduxBlocks.HOLEFIRE_WALL, ReduxBlocks.HOLEFIRE_SLAB, event);
            putAfter(ReduxBlocks.HOLEFIRE_SLAB, ReduxBlocks.HOLEFIRE_PILLAR, event);
            putAfter(ReduxBlocks.HOLEFIRE_PILLAR, ReduxBlocks.HOLEFIRE_PILLAR_TOP, event);
            putAfter(ReduxBlocks.HOLEFIRE_PILLAR_TOP, ReduxBlocks.HOLEFIRE_SPIKE, event);

        }
        if (tab == AetherCreativeTabs.AETHER_FOOD_AND_DRINKS.get()) {
            putAfter(AetherItems.ENCHANTED_BERRY, ReduxItems.BLUEBERRY_PIE, event);
            putAfter(ReduxItems.BLUEBERRY_PIE, ReduxItems.ENCHANTED_BLUEBERRY_PIE, event);
            putAfter(ReduxItems.ENCHANTED_BLUEBERRY_PIE, ReduxItems.WYNDSPROUT_BAGEL, event);
            putAfter(ReduxItems.WYNDSPROUT_BAGEL, ReduxItems.BLUEBERRY_BAGEL, event);
            putAfter(ReduxItems.BLUEBERRY_BAGEL, ReduxItems.WYNDSPROUT_SEEDS, event);
            putAfter(ReduxItems.WYNDSPROUT_SEEDS, ReduxItems.OATMEAL, event);
            putAfter(ReduxItems.OATMEAL, ReduxItems.LEMON_POPROCKS, event);


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
            putAfter(ReduxItems.VERIDIUM_NUGGET, ReduxItems.SENTRY_CHIP, event);

            putAfter(AetherItems.SKYROOT_STICK, ReduxItems.BLIGHTED_SPORES, event);

            putAfter(AetherItems.GOLDEN_AMBER, ReduxItems.BLIGHTBUNNY_FANG, event);

            if (ReduxConfig.COMMON.raw_ores.get()) {
                putAfter(AetherBlocks.ENCHANTED_GRAVITITE, ReduxItems.RAW_GRAVITITE, event);
            }
            if (Redux.ancientAetherCompat()) {
                putAfter(AncientAetherItems.VALKYRUM, ReduxItems.RAW_VALKYRUM, event);
            }

            putAfter(AetherItems.AECHOR_PETAL, ReduxItems.MYKAPOD_SHELL_CHUNK, event);
            putAfter(ReduxItems.WYNDSPROUT_SEEDS, ReduxItems.WYNDSPROUT_SEEDS, event);
            putAfter(ReduxItems.WYNDSPROUT_SEEDS, ReduxItems.BUNDLE_OF_WYNDSPROUTS, event);
            putAfter(ReduxItems.BUNDLE_OF_WYNDSPROUTS, ReduxItems.LIGHTROOT_CLUMP, event);

            putAfter(ReduxItems.LIGHTROOT_CLUMP, ReduxItems.FIREINTHEHOLE, event);

            putAfter(AetherItems.SWET_BALL, Redux.aetherGenesisCompat() ? ReduxItems.VANILLA_SWET_BALL : ReduxItems.GOLDEN_SWET_BALL, event);
            if (!Redux.aetherGenesisCompat()) { putAfter(ReduxItems.GOLDEN_SWET_BALL, ReduxItems.VANILLA_SWET_BALL, event); }

            if (ReduxConfig.COMMON.gravitite_ingot.get()) {
                putBefore(AetherBlocks.ENCHANTED_GRAVITITE, ReduxItems.GRAVITITE_INGOT, event);
                event.getEntries().remove(new ItemStack(AetherBlocks.ENCHANTED_GRAVITITE.get()));
            }

        }
        if (tab == AetherCreativeTabs.AETHER_ARMOR_AND_ACCESSORIES.get()) {

            putAfter(AetherItems.VALKYRIE_CAPE, ReduxItems.GRAND_VICTORY_MEDAL, event);
            putAfter(ReduxItems.GRAND_VICTORY_MEDAL, ReduxItems.COCKATRICE_FEATHER, event);
            putAfter(ReduxItems.COCKATRICE_FEATHER, ReduxItems.FEATHER_OF_WARDING, event);

            putAfter(AetherItems.ICE_PENDANT, ReduxItems.ENCHANTED_RING, event);
            putAfter(ReduxItems.ENCHANTED_RING, ReduxItems.RING_OF_WISDOM, event);
            putAfter(ReduxItems.RING_OF_WISDOM, ReduxItems.SHROOM_RING, event);

            putAfter(AetherItems.SENTRY_BOOTS, ReduxItems.SENTRY_RING, event);

            putAfter(AetherItems.IRON_BUBBLE, ReduxItems.SOLAR_EMBLEM, event);

            putAfter(AetherItems.REGENERATION_STONE, ReduxItems.VAMPIRE_AMULET, event);

            putAfter(AetherItems.SWET_CAPE, ReduxItems.AIRBOUND_CAPE, event);

            putAfter(AetherItems.SHIELD_OF_REPULSION, ReduxItems.SNAILSHELL_SHIELD, event);
        }
        if (tab == AetherCreativeTabs.AETHER_EQUIPMENT_AND_UTILITIES.get()) {

            putBeforeVeridium(AetherItems.GRAVITITE_SWORD, ReduxItems.INFUSED_VERIDIUM_HOE, event);
            putBeforeVeridium(ReduxItems.INFUSED_VERIDIUM_HOE, ReduxItems.INFUSED_VERIDIUM_AXE, event);
            putBeforeVeridium(ReduxItems.INFUSED_VERIDIUM_AXE, ReduxItems.INFUSED_VERIDIUM_PICKAXE, event);
            putBeforeVeridium(ReduxItems.INFUSED_VERIDIUM_PICKAXE, ReduxItems.INFUSED_VERIDIUM_SHOVEL, event);
            putBeforeVeridium(ReduxItems.INFUSED_VERIDIUM_SHOVEL, ReduxItems.INFUSED_VERIDIUM_SWORD, event);
            putBeforeVeridium(ReduxItems.INFUSED_VERIDIUM_SWORD, ReduxItems.VERIDIUM_HOE, event);
            putBefore(ReduxItems.VERIDIUM_HOE, ReduxItems.VERIDIUM_AXE, event);
            putBefore(ReduxItems.VERIDIUM_AXE, ReduxItems.VERIDIUM_PICKAXE, event);
            putBefore(ReduxItems.VERIDIUM_PICKAXE, ReduxItems.VERIDIUM_SHOVEL, event);
            putBefore(ReduxItems.VERIDIUM_SHOVEL, ReduxItems.VERIDIUM_SWORD, event);

            putBefore(AetherItems.GOLDEN_DART_SHOOTER, ReduxItems.SPEAR_OF_THE_BLIGHT, event);

            putAfter(AetherItems.ENCHANTED_DART, ReduxItems.VERIDIUM_DART_SHOOTER, event);
            putAfterVeridium(ReduxItems.VERIDIUM_DART_SHOOTER, ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER, event);
            putAfterVeridium(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER, ReduxItems.VERIDIUM_DART, event);

            putAfter(AetherItems.GOLD_DUNGEON_KEY, ReduxItems.LOBOTIUM_KEY, event);

            putAfter(AetherItems.SKYROOT_POISON_BUCKET, ReduxItems.SKYROOT_SYRUP_BUCKET, event);
            putAfter(ReduxItems.SKYROOT_SYRUP_BUCKET, ReduxItems.SYRUP_BUCKET, event);

            putAfter(AetherItems.PHOENIX_BOW, ReduxItems.SUBZERO_CROSSBOW, event);

            putAfter(AetherItems.MUSIC_DISC_ASCENDING_DAWN, ReduxItems.SLIDER_MUSIC_DISC, event);

            doBoats(AetherItems.SKYROOT_CHEST_BOAT, event);
        }

        if (tab == AetherCreativeTabs.AETHER_SPAWN_EGGS.get()) {
            putBefore(AetherItems.WHIRLWIND_SPAWN_EGG, ReduxItems.VANILLA_SWET_SPAWN_EGG, event);
            putBefore(AetherItems.BLUE_SWET_SPAWN_EGG, ReduxItems.BLIGHTBUNNY_SPAWN_EGG, event);
            putBefore(AetherItems.PHYG_SPAWN_EGG, ReduxItems.PHUDGE_SPAWN_EGG, event);
            putAfter(AetherItems.FLYING_COW_SPAWN_EGG, ReduxItems.SHIMMERCOW_SPAWN_EGG, event);
            putAfter(AetherItems.MOA_SPAWN_EGG, ReduxItems.MYKAPOD_SPAWN_EGG, event);
        }
    }

    private static RegistryObject<? extends ItemLike> doBuildingWoods(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
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
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
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
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
        {
            putAfter(b == null ? prevEntry : b, woodHandler.signItem, event);
            putAfter(woodHandler.signItem, woodHandler.hangingSignItem, event);
            b = woodHandler.hangingSignItem;
        }
        return b;
    }

    private static RegistryObject<? extends ItemLike> doBoats(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
        {
            putAfter(b == null ? prevEntry : b, woodHandler.boatItem, event);
            putAfter(woodHandler.boatItem, woodHandler.chestBoatItem, event);
            b = woodHandler.chestBoatItem;
        }
        return b;
    }
    private static RegistryObject<? extends ItemLike> doBookshelves(RegistryObject<? extends ItemLike>  prevEntry, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> b = null;
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
        {
            putAfter(b == null ? prevEntry : b, woodHandler.bookshelf, event);
            b = woodHandler.bookshelf;
        }
        return b;
    }
    private static void doRedstoneWoods(RegistryObject<? extends ItemLike>  pressurePlate, RegistryObject<? extends ItemLike>  door, RegistryObject<? extends ItemLike>  button, RegistryObject<? extends ItemLike>  trapdoor, BuildCreativeModeTabContentsEvent event) {

        RegistryObject<? extends ItemLike> plate = null;
        RegistryObject<? extends ItemLike> dr = null;
        RegistryObject<? extends ItemLike> btn = null;
        RegistryObject<? extends ItemLike> td = null;
        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)
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

    private static void putAfter(RegistryObject<? extends ItemLike> itemBefore, RegistryObject<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().putAfter(new ItemStack(itemBefore.get()), new ItemStack(insertedItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
    private static void putBefore(RegistryObject<? extends ItemLike> itemAfter, RegistryObject<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event) {
        event.getEntries().putBefore(new ItemStack(itemAfter.get()), new ItemStack(insertedItem.get()), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

    private static void putAfterVeridium(RegistryObject<? extends ItemLike> itemBefore, RegistryObject<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event) {
        ItemStack stackBefore = new ItemStack(itemBefore.get());
        if (stackBefore.is(ReduxTags.Items.INFUSED_VERIDIUM_ITEMS))
        {
            CompoundTag compound = VeridiumItem.createCompoundFor(stackBefore);
            compound.putByte(VeridiumItem.nbt_tag, VeridiumItem.MAXIUMUM_VERIDIUM_INFUSION);
            stackBefore.setTag(compound);
        }
        ItemStack stackInserted = new ItemStack(insertedItem.get());
        if (stackInserted.is(ReduxTags.Items.INFUSED_VERIDIUM_ITEMS))
        {
            CompoundTag compound1 = VeridiumItem.createCompoundFor(stackInserted);
            compound1.putByte(VeridiumItem.nbt_tag, VeridiumItem.MAXIUMUM_VERIDIUM_INFUSION);
            stackInserted.setTag(compound1);
        }
        event.getEntries().putAfter(stackBefore, stackInserted, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }
    private static void putBeforeVeridium(RegistryObject<? extends ItemLike> itemAfter, RegistryObject<? extends ItemLike> insertedItem, BuildCreativeModeTabContentsEvent event) {
        ItemStack stackAfter = new ItemStack(itemAfter.get());
        if (stackAfter.is(ReduxTags.Items.INFUSED_VERIDIUM_ITEMS))
        {
            CompoundTag compound = VeridiumItem.createCompoundFor(stackAfter);
            compound.putByte(VeridiumItem.nbt_tag, VeridiumItem.MAXIUMUM_VERIDIUM_INFUSION);
            stackAfter.setTag(compound);
        }
        ItemStack stackInserted = new ItemStack(insertedItem.get());
        if (stackInserted.is(ReduxTags.Items.INFUSED_VERIDIUM_ITEMS))
        {
            CompoundTag compound1 = VeridiumItem.createCompoundFor(stackInserted);
            compound1.putByte(VeridiumItem.nbt_tag, VeridiumItem.MAXIUMUM_VERIDIUM_INFUSION);
            stackInserted.setTag(compound1);
        }
        event.getEntries().putBefore(stackAfter, stackInserted, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
    }

}
