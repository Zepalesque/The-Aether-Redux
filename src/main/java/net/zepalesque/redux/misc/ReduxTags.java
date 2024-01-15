package net.zepalesque.redux.misc;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.Redux;

public class ReduxTags {

    public static class Blocks {
        public static final TagKey<Block> COARSE_AETHER_DIRT = tag("coarse_aether_dirt");
        public static final TagKey<Block> AETHER_CARVER_REPLACEABLES = tag("aether_carver_replaceables");
        public static final TagKey<Block> BLIGHT_REPLACEABLES = tag("blight_replaceables");
        public static final TagKey<Block> FROSTED_PLANTS_PLACEMENT = tag("frosted_plants_placement");
        public static final TagKey<Block> HIGHLANDS_GRASSES = tag("highlands_grasses");
        public static final TagKey<Block> ENCHANTED_GRASSES = tag("enchanted_grasses");
        public static final TagKey<Block> BLIGHTED_GRASSES = tag("blighted_grasses");
        public static final TagKey<Block> QUICKSOIL_BEHAVIOR = tag("quicksoil_behavior");
        public static final TagKey<Block> LOG_WALLS = tagGenesis("log_walls");
        public static final TagKey<Block> LIGHTROOT_GROWABLE = tag("lightroot_growable");
        public static final TagKey<Block> ENCHANTED_VINES_SKIP_PLACEMENT = tag("enchanted_vines_skip_placement");
        public static final TagKey<Block> ENCHANTED_VINES_SURVIVE = tag("enchanted_vines_survive");

        public static final TagKey<Block> BLIGHTWILLOW_ROOTS_CAN_GROW_THROUGH = tag("blightwillow_roots_can_grow_through");
        public static final TagKey<Block> BLIGHTWILLOW_LOGS_CAN_GROW_THROUGH = tag("blightwillow_logs_can_grow_through");

        public static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Redux.locate(name));
        }

        public static TagKey<Block> tagGenesis(String name) {
            return TagKey.create(Registries.BLOCK,new ResourceLocation("aether_genesis",name));
        }
    }

    public static class Items {
        public static final TagKey<Item> REDUX_AERBUNNY_FOOD_ITEMS = tag("redux_aerbunny_food_items");
        public static final TagKey<Item> REDUX_PHYG_FOOD_ITEMS = tag("redux_phyg_food_items");
        public static final TagKey<Item> REDUX_FLYING_COW_FOOD_ITEMS = tag("redux_flying_cow_food_items");
        public static final TagKey<Item> SWET_JELLY = tag("swet_jelly");
        public static final TagKey<Item> BLUE_SWET_JELLY = tag("blue_swet_jelly");
        public static final TagKey<Item> GOLDEN_SWET_JELLY = tag("golden_swet_jelly");
        public static final TagKey<Item> GOLDEN_SWET_BALL = tag("golden_swet_ball");
        public static final TagKey<Item> BLUEBERRY_PIE_EGGS = tag("eggs_for_blueberry_pie");
        public static final TagKey<Item> VERIDIUM_ADVANCEMENT_INFUSABLE = tag("veridium_advancement_infusable");

        public static final TagKey<Item> SKYROOT_BOWLS = tag("compat/skyroot_bowls");
        public static final TagKey<Item> MOUSE_EAR_CAPS = tag("compat/mouse_ear_caps");

        public static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, Redux.locate(name));
        }
    }

    public static class EntityTypes {
        public static final TagKey<EntityType<?>> BLIGHTED_MOBS = tag("blighted_mobs");
        public static final TagKey<EntityType<?>> CAN_WALK_THROUGH_AUBURN_BUSH = tag("can_walk_through_auburn_bush");
        public static final TagKey<EntityType<?>> SENTRIES = tag("sentries");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Redux.locate(name));
        }
    }

    public static class Biomes {

        public static final TagKey<Biome> IS_HIGHLANDS = tag("is/highlands");
        public static final TagKey<Biome> IS_FORGOTTEN = tag("is/forgotten");
        public static final TagKey<Biome> HAS_SENTRY_LAB = tagAncient("has_structure/sentry_lab");
        public static final TagKey<Biome> IS_GILDED = tag("is/gilded");

        public static final TagKey<Biome> HAS_AETHER_CAVES = tag("has/aether_caves");
        public static final TagKey<Biome> HAS_BLIGHTED_CAVES = tag("has/blighted_caves");
        public static final TagKey<Biome> HAS_MOSSY_HOLYSTONE_ORE = tag("has/mossy_holystone_ore");
        public static final TagKey<Biome> HAS_MOSSY_ROCKS = tag("has/mossy_rocks");
        public static final TagKey<Biome> HAS_BOTH_SPROUTS = tag("has/both_sprouts");
        public static final TagKey<Biome> HAS_WYNDSPROUTS = tag("has/wyndsprouts");
        public static final TagKey<Biome> HAS_VANILLA_SWET = tag("has/vanilla_swet");
        public static final TagKey<Biome> HAS_VERIDIUM_ORE = tag("has/veridium_ore");
        public static final TagKey<Biome> HAS_DIVINITE = tag("has/divinite");
        public static final TagKey<Biome> HAS_REDUX_WATER_COLOR = tag("has/redux_water_color");

        public static final TagKey<Biome> DENSE_LEAF_FALL = tag("dense_leaf_fall");


        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, Redux.locate(name));
        }

        private static TagKey<Biome> tagAncient(String name) {
            return TagKey.create(Registries.BIOME, new ResourceLocation("ancient_aether", name));
        }
    }
    public static class DamageTypes {

        public static final TagKey<DamageType> IS_ATTACK = tag("is_attack");
        public static final TagKey<DamageType> FIREBALL = tag("fireball");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, Redux.locate(name));
        }
    }
}
