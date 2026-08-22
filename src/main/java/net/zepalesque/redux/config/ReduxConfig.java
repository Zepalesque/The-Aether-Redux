package net.zepalesque.redux.config;

import com.google.common.collect.ImmutableSet;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.enums.AACompatFeature;
import net.zepalesque.redux.config.enums.ConditionalConfig;
import net.zepalesque.redux.mixin.mixins.common.accessor.CfgBuilderAccessor;
import net.zepalesque.zenith.api.serialization.config.DataSerializableConfig;
import net.zepalesque.zenith.util.data.DatagenUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class ReduxConfig {
	public static <T> T getOrDefault(ModConfigSpec.ConfigValue<T> val) {
		try {
			return val.get();
		} catch(Exception e) {
			return val.getDefault();
		}
	}
	
	public static class Server extends DataSerializableConfig {
		public final Set<ModConfigSpec.ConfigValue<?>> values;
		
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
			
			var set = new ValSetBuilder();
			
			builder.push("Worldgen");
			builder.push("Tweaks");
			this.redux_sky_colors = set.add(builder
				.worldRestart()
				.comment("Use Redux's alternative sky colors for the Aether")
				.translation(transKey(builder, "server", "redux_sky_colors"))
				.define("Redux Sky Colors", true));
			this.redux_water_colors = set.add(builder
				.comment("Use Redux's alternative water colors for the Aether")
				.translation(transKey(builder, "server", "redux_water_colors"))
				.worldRestart()
				.define("Redux Water Colors", true));
			this.cloudbed = set.add(builder
				.comment("Replace the Aether's large Aercloud features with a noise-based cloudbed")
				.translation(transKey(builder, "server", "cloudbed"))
				.worldRestart()
				.define("Cloudbed", true));
			this.lakes = set.add(builder
				.comment("Add large lakes to the Aether")
				.translation(transKey(builder, "server", "lakes"))
				.worldRestart()
				.define("Lakes", true));
			this.mossy_holystone_gen = set.add(builder
				.comment("Enables the natural spawning of Mossy Holystone, alongside Gilded and Bleakmoss Holystone in their respective biomes.")
				.translation(transKey(builder, "server", "mossy_holystone_gen"))
				.worldRestart()
				.define("Mossy Holystone Generation", true));
			builder.pop();

			builder.push("Cliffs");
			this.patch_steep = set.add(builder
				.comment("Fix MC-258859, allowing the `steep` surface rule to work on all slope faces.")
				.translation(transKey(builder, "server", "patch_steep"))
				.worldRestart()
				.define("Patch `steep` Surface Rule", true));
			builder.pop();
			
			this.use_wood_blocks = set.add(builder
				.comment("Allow generation of wood blocks (6-sided log block) in certain tree generators in order to make more natural-looking trees")
				.translation(transKey(builder, "server", "use_wood_blocks"))
				.worldRestart()
				.define("Use Wood Blocks in Tree Generation", true));
			builder.pop();

			builder.push("Gameplay");
			
			this.max_veridium_tool_infusion = set.add(builder
				.comment("The maximum amount of infusion a Veridium tool is able to carry. Note that by default, a tools infusion level is increased by 4 when it is infused with a single Ambrosium Shard.")
				.translation(transKey(builder, "server", "max_veridium_tool_infusion"))
				.defineInRange("Max Veridium Tool Infusion", 64, 1, Short.MAX_VALUE));
			this.revamped_quicksoil_movement = set.add(builder
				.comment("Changes quicksoil to make it use a better movement system, based on the way it worked in the Aether II: Highlands in 1.12")
				.translation(transKey(builder, "server", "revamped_quicksoil_movement"))
				.define("Revamped Quicksoil Movement", true));
			this.consistent_break_speeds = set.add(builder
				.comment("Slows down the mining speeds of some Aether blocks, to be more vanilla-consistent")
				.translation(transKey(builder, "server", "consistent_break_speeds"))
				.define("Consistent Break Speeds", false));
			this.raw_ores = set.add(builder
				.comment("Use raw ores like modern vanilla versions, instead of just getting the ore block when mining it")
				.translation(transKey(builder, "server", "raw_ores"))
				.worldRestart()
				.define("Raw Ores", true));
			this.gummy_swet_nerf = set.add(builder
				.translation(transKey(builder, "server", "gummy_swet_nerf"))
				.comment("Nerfs Gummy Swets and makes them craftable.")
				.worldRestart()
				.define("Gummy Swet Nerf", true));

			builder.pop();
			
			this.values = set.build();
		}
	}

	public static class Common extends DataSerializableConfig {
		public final Set<ModConfigSpec.ConfigValue<?>> values;
		
		public final ModConfigSpec.BooleanValue bronze_dungeon_upgrade;
		public final ModConfigSpec.EnumValue<AACompatFeature.Overridden> redux_noise;

		public Common(ModConfigSpec.Builder builder) {
			super(() -> COMMON_SPEC, "redux_common");
			
			var set = new ValSetBuilder();
			
			builder.push("Datapack Registration");
			this.redux_noise = set.add(Redux.DATA_CONFIG.register(
				builder
					.translation(transKey(builder, "common", "redux_noise"))
					.comment("Uses an alternative noise for the Aether. By default, this is disabled with the Ancient Aether mod installed.")
					.worldRestart()
					.defineEnum("Redux Noise", AACompatFeature.Overridden.WITHOUT_ANCIENT_AETHER),
				"redux_noise",
				ConditionalConfig::enabled
			));
			this.bronze_dungeon_upgrade = set.add(Redux.DATA_CONFIG.register(
				builder
					.translation(transKey(builder, "common", "bronze_dungeon_upgrade"))
					.comment("Upgrades the Bronze Dungeon structure with new blocks and more depth")
					.worldRestart()
					.define("Bronze Dungeon Upgrade", true),
				"dungeon_upgrades/bronze"
			));
			builder.pop();
			
			this.values = set.build();
		}
	}

	public static class Client {
		public final Set<ModConfigSpec.ConfigValue<?>> values;
		
		public final ModConfigSpec.BooleanValue leaf_particles;
		public final ModConfigSpec.BooleanValue improved_whirlwinds;
		public final ModConfigSpec.BooleanValue improved_sheepuffs;
		public final ModConfigSpec.BooleanValue improved_moas;
		public final ModConfigSpec.BooleanValue improved_aerbunnies;
		public final ModConfigSpec.BooleanValue move_clouds;

		public final ModConfigSpec.BooleanValue slider_sfx_upgrade;
		public final ModConfigSpec.BooleanValue slider_signal_sfx;
		public final ModConfigSpec.BooleanValue upgraded_nature;
		public final ModConfigSpec.BooleanValue upgraded_dungeons;
		public final ModConfigSpec.BooleanValue upgraded_resources;
		public final ModConfigSpec.BooleanValue upgraded_tools;

		public Client(ModConfigSpec.Builder builder) {
			builder.push("Visual");
			
			var set = new ValSetBuilder();
			
			this.leaf_particles = set.add(builder
				.comment("Use nice falling leaf particles for Aether leaf blocks")
				.translation(transKey(builder, "client", "leaf_particles"))
				.define("Leaf Particles", true));
			this.improved_whirlwinds = set.add(builder
				.comment("Gives Whirlwinds a new design, based on Minecraft 1.21's new Breeze mob")
				.translation(transKey(builder, "client", "improved_whirlwinds"))
				.define("Improved Whirlwinds", true));
			this.improved_sheepuffs = set.add(builder
				.comment("Enables Redux's updated Sheepuff model")
				.translation(transKey(builder, "client", "improved_sheepuffs"))
				.define("Improved Sheepuffs", true));
			this.improved_moas = set.add(builder
				.comment("Enables Redux's updated Moa model")
				.translation(transKey(builder, "client", "improved_moas"))
				.define("Improved Moas", true));
			this.improved_aerbunnies = set.add(builder
				.comment("Enables Redux's updated Aerbunny model")
				.translation(transKey(builder, "client", "improved_aerbunnies"))
				.define("Improved Aerbunnies", true));
			this.move_clouds = set.add(builder
				.comment("Move the clouds in the Aether to above the islands")
				.translation(transKey(builder, "client", "move_clouds"))
				.gameRestart()
				.define("Move Clouds", true));

			this.upgraded_nature = set.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated Aether nature textures.")
					.translation(transKey(builder, "client", "upgraded_nature"))
					.define("Upgraded Nature", true),
				"upgraded_nature"
			));
			this.upgraded_dungeons = set.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated dungeon textures.")
					.translation(transKey(builder, "client", "upgraded_dungeons"))
					.define("Upgraded Dungeons", true),
				"upgraded_dungeons"
			));
			this.upgraded_resources = set.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated resource textures.")
					.translation(transKey(builder, "client", "upgraded_resources"))
					.define("Upgraded Resources", true),
				"upgraded_resources"
			));
			this.upgraded_tools = set.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated tool textures.")
					.translation(transKey(builder, "client", "upgraded_tools"))
					.define("Upgraded Tools", true),
				"upgraded_tools"
			));

			builder.pop();

			builder.push("Audio");
			builder.push("Slider");
			
			this.slider_sfx_upgrade = set.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Improve the hurt, death, and ambient sounds of the Slider.")
					.translation(transKey(builder, "client", "slider_sfx_upgrade"))
					.define("Slider SFX Upgrades", true),
				"sfx/",
				"slider"
			));
			
			this.slider_signal_sfx = set.add(builder
				.comment("Gives the Slider a subtle signal effect before sliding.")
				.translation(transKey(builder, "client", "slider_signal_sfx"))
				.define("Slider Movement Signal", true));

			builder.pop(2);
			
			
			this.values = set.build();
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
	
	private static String transKey(ModConfigSpec.Builder builder, String kind, String name) {
		var b = new StringBuilder("config." + Redux.MODID + "." + kind + ".");
		for (var entry : ((CfgBuilderAccessor) builder).redux$currentPath())  {
			b.append(DatagenUtil.unlocalize(entry));
			b.append('.');
		}
		
		b.append(name);
		
		return b.toString();
	}
	
	private static final class ValSetBuilder {
		private final HashSet<ModConfigSpec.ConfigValue<?>> set = new HashSet<>();
		
		public <T, V extends ModConfigSpec.ConfigValue<T>> V add(V val) {
			this.set.add(val);
			return val;
		}
		
		public Set<ModConfigSpec.ConfigValue<?>> build() {
			return this.set.parallelStream().collect(Collectors.toUnmodifiableSet());
		}
		
		@Override
		public String toString() {
			return "ValSetBuilder[" +
				"set=" + this.set +
				']';
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null || this.getClass() != o.getClass()) return false;
			ValSetBuilder that = (ValSetBuilder) o;
			return Objects.equals(this.set, that.set);
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(this.set);
		}
	}
}
