package net.zepalesque.redux.data;

import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.Redux;

public class ReduxTags {
	public static class Blocks {
		// Blocks that should override Short Aether Grass's color to be the color of bleakmoss
		public static final TagKey<Block> SHORT_AETHER_GRASS_BLEAKMOSS_COLORING = tag("short_aether_grass_bleakmoss_coloring");
		// Blocks that should override Short Aether Grass's color to be the blight color (permablight grass block for instance)
		public static final TagKey<Block> SHORT_AETHER_GRASS_BLIGHT_COLORING = tag("short_aether_grass_blight_coloring");
		// Blocks that should use the Highlands quicksoil system
		public static final TagKey<Block> QUICKSOIL_BEHAVIOR = tag("quicksoil_behavior");

		public static final TagKey<Block> MUSHROOM_CAPS = tag("mushroom_caps");

		// Blocks that can be replaced by generated rocks
		public static final TagKey<Block> ROCK_REPLACEABLE = tag("rock_replaceable");
		
		public static final TagKey<Block> AETHER_GRASS_SPREAD_BLACKLIST = tag("aether_grass_spread_blacklist");

		public static final TagKey<Block> QUICKSOIL_PLANTS_SURVIVABLE = tag("quicksoil_plants_survivable");
		
		// NeoForge tags
		public static final TagKey<Block> VERIDIUM_ORES = tag("veridium_ores");
		public static final TagKey<Block> STORAGE_BLOCKS_VERIDIUM = tag("storage_blocks_veridium");
		public static final TagKey<Block> STORAGE_BLOCKS_RAW_VERIDIUM = tag("storage_blocks_raw_veridium");
		public static final TagKey<Block> STORAGE_BLOCKS_SENTRITE = tag("storage_blocks_sentrite");

		public static TagKey<Block> tag(String name) {
			return TagKey.create(Registries.BLOCK, Redux.loc(name));
		}
	}

	public static class Items {
		public static final TagKey<Item> INFUSED_VERIDIUM_ITEMS = tag("infused_veridium_items");
		
		public static final TagKey<Item> WILLOW_SPORE_HARVESTERS = tag("willow_spore_harvesters");

		public static TagKey<Item> tag(String name) {
			return TagKey.create(Registries.ITEM, Redux.loc(name));
		}
	}

	public static class Entities {
		public static final TagKey<EntityType<?>> VALID_PICKAXE_TARGETS = tag("valid_pickaxe_targets");

		public static final TagKey<EntityType<?>> SENTRIES = tag("sentries");

		public static final TagKey<EntityType<?>> SENTRITE_MUSIC_DISC_DROPPING = tag("sentrite_music_disc_dropping");

		private static TagKey<EntityType<?>> tag(String name) {
			return TagKey.create(Registries.ENTITY_TYPE, Redux.loc(name));
		}
	}

	public static class Biomes {
		public static final TagKey<Biome> MODIFY_MUSIC = tag("should_modify_music");
		public static final TagKey<Biome> MODIFY_SKY_COLOR = tag("should_modify_sky_color");
		public static final TagKey<Biome> MODIFY_WATER_COLOR = tag("should_modify_water_color");

		public static final TagKey<Biome> HAS_SENTRITE = tag("has_sentrite");
		public static final TagKey<Biome> HAS_ANGILITE = tag("has_angilite");
		public static final TagKey<Biome> HAS_WYNDSPROUTS = tag("has_wyndsprouts");
		public static final TagKey<Biome> HAS_CLOUDBED = tag("has_cloudbed");
		public static final TagKey<Biome> HAS_LAKES = tag("has_lakes");
		public static final TagKey<Biome> HAS_CAT_FISH = tag("has_cat_fish");
		public static final TagKey<Biome> HAS_VERBENA = tag("has_verbena");
		public static final TagKey<Biome> HAS_CAELGAE = tag("has_caelgae");
		public static final TagKey<Biome> HAS_BLOOMTAIL = tag("has_bloomtail");
		public static final TagKey<Biome> HAS_CAVES = tag("has_wyndsprouts");
		
		public static final TagKey<Biome> BLOOMTAIL_BONEMEAL = tag("bloomtail_bonemeal");
		
		
		public static final TagKey<Biome> HAS_MOSSY_HOLYSTONE = tag("has_mossy_holystone");
		public static final TagKey<Biome> HAS_GILDED_HOLYSTONE = tag("has_gilded_holystone");
		public static final TagKey<Biome> HAS_BLEAKMOSS_HOLYSTONE = tag("has_bleakmoss_holystone");

		private static TagKey<Biome> tag(String name) {
			return TagKey.create(Registries.BIOME, Redux.loc(name));
		}
	}

	public static class DmgTypes {
		@SuppressWarnings("unused")
		private static TagKey<DamageType> tag(String name) {
			return TagKey.create(Registries.DAMAGE_TYPE, Redux.loc(name));
		}
	}

	public static class Sounds {
		@SuppressWarnings("unused")
		private static TagKey<SoundEvent> tag(String name) {
			return TagKey.create(Registries.SOUND_EVENT, Redux.loc(name));
		}
	}

	public static class AdvancementSFX {
		@SuppressWarnings("unused")
		private static TagKey<AdvancementSoundOverride> tag(String name) {
			return TagKey.create(AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, Redux.loc(name));
		}
	}
}
