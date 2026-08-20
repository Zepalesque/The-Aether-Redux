package net.zepalesque.redux.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.enums.AACompatFeature;
import net.zepalesque.redux.config.enums.ConditionalConfig;
import net.zepalesque.zenith.api.serialization.config.DataSerializableConfig;

public class ReduxConfig {
	public static <T> T getOrDefault(ModConfigSpec.ConfigValue<T> val) {
		try {
			return val.get();
		} catch(Exception e) {
			return val.getDefault();
		}
	}
	
	public static class Server extends DataSerializableConfig {
		public final ModConfigSpec.BooleanValue redux_sky_colors;
		public final ModConfigSpec.BooleanValue redux_water_colors;
		public final ModConfigSpec.BooleanValue cloudbed;
		public final ModConfigSpec.BooleanValue lakes;
		public final ModConfigSpec.BooleanValue patch_steep;
		public final ModConfigSpec.BooleanValue use_wood_blocks;
		public final ModConfigSpec.BooleanValue revamped_quicksoil_movement;
		// TODO: Item component?
		public final ModConfigSpec.IntValue max_veridium_tool_infusion;
		public final ModConfigSpec.BooleanValue consistent_break_speeds;
		// TODO: Other Ores
		public final ModConfigSpec.BooleanValue raw_ores;
		public final ModConfigSpec.BooleanValue gummy_swet_nerf;
		public final ModConfigSpec.BooleanValue mossy_holystone_gen;

		public Server(ModConfigSpec.Builder builder) {
			super(() -> SERVER_SPEC, "redux_server");
			builder.push("Worldgen");
			builder.push("Tweaks");
			this.redux_sky_colors = builder
				.worldRestart()
				.comment("Use Redux's alternative sky colors for the Aether")
				.define("Redux Sky Colors", true);
			this.redux_water_colors = builder
				.comment("Use Redux's alternative water colors for the Aether")
				.worldRestart()
				.define("Redux Water Colors", true);
			this.cloudbed = builder
				.comment("Replace the Aether's large Aercloud features with a noise-based cloudbed")
				.worldRestart()
				.define("Cloudbed", true);
			this.lakes = builder.comment("Add large lakes to the Aether").worldRestart().define("Lakes", true);
			this.mossy_holystone_gen = builder
				.comment("Enables the natural spawning of Mossy Holystone, alongside Gilded and Bleakmoss Holystone in their respective biomes.")
				.worldRestart()
				.define("Mossy Holystone Generation", true);
			builder.pop();

			builder.push("Cliffs");
			this.patch_steep = builder
				.comment("Fix MC-258859, allowing the `steep` surface rule to work on all slope faces.")
				.worldRestart()
				.define("Patch `steep` Surface Rule", true);
			builder.pop();
			
			this.use_wood_blocks = builder
				.comment("Allow generation of wood blocks (6-sided log block) in certain tree generators in order to make more natural-looking trees")
				.worldRestart()
				.define("Use Wood Blocks in Tree Generation", true);
			builder.pop();

			builder.push("Gameplay");
			
			this.max_veridium_tool_infusion = builder
				.comment("The maximum amount of infusion a Veridium tool is able to carry. Note that by default, a tools infusion level is increased by 4 when it is infused with a single Ambrosium Shard.")
				.defineInRange("Max Veridium Tool Infusion", 64, 1, Short.MAX_VALUE);
			this.revamped_quicksoil_movement = builder
				.comment("Changes quicksoil to make it use a better movement system, based on the way it worked in the Aether II: Highlands in 1.12")
				.define("Revamped Quicksoil Movement", true);
			this.consistent_break_speeds = builder
				.comment("Slows down the mining speeds of some Aether blocks, to be more vanilla-consistent")
				.define("Consistent Break Speeds", false);
			this.raw_ores = builder
				.comment("Use raw ores like modern vanilla versions, instead of just getting the ore block when mining it")
				.worldRestart()
				.define("Raw Ores", true);
			this.gummy_swet_nerf = builder
				.comment("Nerfs Gummy Swets and makes them craftable.")
				.worldRestart()
				.define("Gummy Swet Nerf", true);

			builder.pop();
		}
	}

	public static class Common extends DataSerializableConfig {
		public final ModConfigSpec.BooleanValue bronze_dungeon_upgrade;
		public final ModConfigSpec.EnumValue<AACompatFeature.Overridden> redux_noise;

		public Common(ModConfigSpec.Builder builder) {
			super(() -> COMMON_SPEC, "redux_common");
			builder.push("Datapack Registration");
			this.redux_noise = Redux.DATA_CONFIG.register(
				builder
					.comment("Uses an alternative noise for the Aether. By default, this is disabled with the Ancient Aether mod installed.")
					.worldRestart()
					.defineEnum("Redux Noise", AACompatFeature.Overridden.WITHOUT_ANCIENT_AETHER),
				"redux_noise",
				ConditionalConfig::enabled
			);
			this.bronze_dungeon_upgrade = Redux.DATA_CONFIG.register(
				builder
					.comment("Upgrades the Bronze Dungeon structure with new blocks and more depth")
					.worldRestart()
					.define("Bronze Dungeon Upgrade", true),
				"dungeon_upgrades/bronze"
			);
			builder.pop();
		}
	}

	public static class Client {
		public final ModConfigSpec.BooleanValue leaf_particles;
		public final ModConfigSpec.BooleanValue improved_whirlwinds;
		public final ModConfigSpec.BooleanValue improved_sheepuffs;
		public final ModConfigSpec.BooleanValue move_clouds;

		public final ModConfigSpec.BooleanValue jappafied_textures;
		public final ModConfigSpec.BooleanValue slider_sfx_upgrade;
		public final ModConfigSpec.BooleanValue slider_signal_sfx;
		public final ModConfigSpec.BooleanValue upgraded_nature;
		public final ModConfigSpec.BooleanValue upgraded_dungeons;
		public final ModConfigSpec.BooleanValue upgraded_resources;

		public Client(ModConfigSpec.Builder builder) {
			builder.push("Visual");
			
			this.leaf_particles = builder
				.comment("Use nice falling leaf particles for Aether leaf blocks")
				.define("Leaf Particles", true);
			this.improved_whirlwinds = builder
				.comment("Gives Whirlwinds a new design, based on Minecraft 1.21's new Breeze mob")
				.define("Improved Whirlwinds", true);
			this.improved_sheepuffs = builder
				.comment("Enables Redux's updated Sheepuff model")
				.define("Improved Sheepuffs", true);
			this.move_clouds = builder
				.comment("Move the clouds in the Aether to above the islands")
				.gameRestart()
				.define("Move Clouds", true);
			
			this.jappafied_textures = Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use textures designed to fit with the Jappafied Aethers resource pack.")
					.define("Jappafied Textures", false),
				"jappafied"
			);
			this.upgraded_nature = Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated Aether nature textures.")
					.define("Upgraded Nature", true),
				"upgraded_nature"
			);
			this.upgraded_dungeons = Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated dungeon textures.")
					.define("Upgraded Dungeons", true),
				"upgraded_dungeons"
			);
			this.upgraded_resources = Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated resource textures.")
					.define("Upgraded Resources", true),
				"upgraded_resources"
			);

			builder.pop();

			builder.push("Audio");
			builder.push("Slider");
			
			this.slider_sfx_upgrade = Redux.ASSETS_CONFIG.register(
				builder
					.comment("Improve the hurt, death, and ambient sounds of the Slider.")
					.define("Slider SFX Upgrades", true),
				"sfx/",
				"slider"
			);
			
			this.slider_signal_sfx = builder
				.comment("Gives the Slider a subtle signal effect before sliding.")
				.define("Slider Movement Signal", true);

			builder.pop(2);
		}
	}

	public static final ModConfigSpec COMMON_SPEC, SERVER_SPEC, CLIENT_SPEC;
	public static final Common COMMON;
	public static final Server SERVER;
	public static final Client CLIENT;

	static {
		final var server = new ModConfigSpec.Builder().configure(Server::new);
		SERVER_SPEC = server.getRight();
		SERVER = server.getLeft();

		final var common = new ModConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();

		final var client = new ModConfigSpec.Builder().configure(Client::new);
		CLIENT_SPEC = client.getRight();
		CLIENT = client.getLeft();
	}
}
