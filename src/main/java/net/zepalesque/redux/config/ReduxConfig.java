package net.zepalesque.redux.config;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.enums.AACompatFeature;
import net.zepalesque.redux.config.enums.ConditionalConfig;
import net.zepalesque.redux.mixin.mixins.common.accessor.CfgBuilderAccessor;
import net.zepalesque.zenith.api.serialization.config.DataSerializableConfig;
import net.zepalesque.zenith.util.data.DatagenUtil;
import org.jetbrains.annotations.Unmodifiable;

public final class ReduxConfig {
	public static <T> T getOrDefault(ModConfigSpec.ConfigValue<T> val) {
		try {
			return val.get();
		} catch(Exception e) {
			return val.getDefault();
		}
	}
	
	public static class Server extends DataSerializableConfig {
		public final CfgTranslations trans;
		
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
		public final ModConfigSpec.BooleanValue improved_cockatrice_behavior;
		public final ModConfigSpec.BooleanValue cockatrice_burn_in_daylight;

		public Server(ModConfigSpec.Builder builder) {
			super(() -> SERVER_SPEC, "redux_server");
			
			var trans = new TranslationsBuilder("server");
			
			trans.push(builder, "Worldgen");
			trans.push(builder, "Tweaks");
			this.redux_sky_colors = trans.add(builder
				.worldRestart()
				.comment("Use Redux's alternative sky colors for the Aether")
				.translation(trans.transKey(builder, "redux_sky_colors"))
				.define("Redux Sky Colors", true));
			this.redux_water_colors = trans.add(builder
				.comment("Use Redux's alternative water colors for the Aether")
				.translation(trans.transKey(builder, "redux_water_colors"))
				.worldRestart()
				.define("Redux Water Colors", true));
			this.cloudbed = trans.add(builder
				.comment("Replace the Aether's large Aercloud features with a noise-based cloudbed")
				.translation(trans.transKey(builder, "cloudbed"))
				.worldRestart()
				.define("Cloudbed", true));
			this.lakes = trans.add(builder
				.comment("Add large lakes to the Aether")
				.translation(trans.transKey(builder, "lakes"))
				.worldRestart()
				.define("Lakes", true));
			this.mossy_holystone_gen = trans.add(builder
				.comment("Enables the natural spawning of Mossy Holystone, alongside Gilded and Bleakmoss Holystone in their respective biomes.")
				.translation(trans.transKey(builder, "mossy_holystone_gen"))
				.worldRestart()
				.define("Mossy Holystone Generation", true));
			builder.pop();

			trans.push(builder, "Cliffs");
			this.patch_steep = trans.add(builder
				.comment("Fix MC-258859, allowing the `steep` surface rule to work on all slope faces.")
				.translation(trans.transKey(builder, "patch_steep"))
				.worldRestart()
				.define("Patch `steep` Surface Rule", true));
			builder.pop();
			
			this.use_wood_blocks = trans.add(builder
				.comment("Allow generation of wood blocks (6-sided log block) in certain tree generators in order to make more natural-looking trees")
				.translation(trans.transKey(builder, "use_wood_blocks"))
				.worldRestart()
				.define("Use Wood Blocks in Tree Generation", true));
			builder.pop();

			trans.push(builder, "Gameplay");
			
			this.max_veridium_tool_infusion = trans.add(builder
				.comment("The maximum amount of infusion a Veridium tool is able to carry. Note that by default, a tools infusion level is increased by 4 when it is infused with a single Ambrosium Shard.")
				.translation(trans.transKey(builder, "max_veridium_tool_infusion"))
				.defineInRange("Max Veridium Tool Infusion", 64, 1, Short.MAX_VALUE));
			this.revamped_quicksoil_movement = trans.add(builder
				.comment("Changes quicksoil to make it use a better movement system, based on the way it worked in the Aether II: Highlands in 1.12")
				.translation(trans.transKey(builder, "revamped_quicksoil_movement"))
				.define("Revamped Quicksoil Movement", true));
			this.consistent_break_speeds = trans.add(builder
				.comment("Slows down the mining speeds of some Aether blocks, to be more vanilla-consistent")
				.translation(trans.transKey(builder, "consistent_break_speeds"))
				.define("Consistent Break Speeds", false));
			this.raw_ores = trans.add(builder
				.comment("Use raw ores like modern vanilla versions, instead of just getting the ore block when mining it")
				.translation(trans.transKey(builder, "raw_ores"))
				.worldRestart()
				.define("Raw Ores", true));
			this.gummy_swet_nerf = trans.add(builder
				.translation(trans.transKey(builder, "gummy_swet_nerf"))
				.comment("Nerfs Gummy Swets and makes them craftable.")
				.worldRestart()
				.define("Gummy Swet Nerf", true));
			this.improved_cockatrice_behavior = trans.add(builder
				.comment("Makes Cockatrices shoot at you and chase you if they hit you. Requires world restart to refresh existing mob AI.")
				.translation(trans.transKey(builder, "improved_cockatrice_behavior"))
				.worldRestart()
				.define("Improved Cockatrice Behavior", true));
			this.cockatrice_burn_in_daylight = trans.add(builder
				.comment("Makes Cockatrices burn in daylight. Requires world restart to refresh existing mob AI.")
				.translation(trans.transKey(builder, "cockatrice_burn_in_daylight"))
				.worldRestart()
				.define("Cockatrices burn in daylight", false));
			
			
			builder.pop();
			
			this.trans = trans.build();
		}
	}

	public static class Common extends DataSerializableConfig {
		public final CfgTranslations trans;
		
		public final ModConfigSpec.BooleanValue bronze_dungeon_upgrade;
		public final ModConfigSpec.EnumValue<AACompatFeature.Overridden> redux_noise;

		public Common(ModConfigSpec.Builder builder) {
			super(() -> COMMON_SPEC, "redux_common");
			
			var trans = new TranslationsBuilder("common");
			
			trans.push(builder, "Datapack Registration");
			this.redux_noise = trans.add(Redux.DATA_CONFIG.register(
				builder
					.translation(trans.transKey(builder, "redux_noise"))
					.comment("Uses an alternative noise for the Aether. By default, this is disabled with the Ancient Aether mod installed.")
					.worldRestart()
					.defineEnum("Redux Noise", AACompatFeature.Overridden.WITHOUT_ANCIENT_AETHER),
				"redux_noise",
				ConditionalConfig::enabled
			));
			this.bronze_dungeon_upgrade = trans.add(Redux.DATA_CONFIG.register(
				builder
					.translation(trans.transKey(builder, "bronze_dungeon_upgrade"))
					.comment("Upgrades the Bronze Dungeon structure with new blocks and more depth")
					.worldRestart()
					.define("Bronze Dungeon Upgrade", true),
				"dungeon_upgrades/bronze"
			));
			builder.pop();
			
			this.trans = trans.build();
		}
	}

	public static class Client {
		public final CfgTranslations trans;
		
		public final ModConfigSpec.BooleanValue leaf_particles;
		public final ModConfigSpec.BooleanValue improved_whirlwinds;
		public final ModConfigSpec.BooleanValue defer_whirlwind_rendering;
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
			
			var trans = new TranslationsBuilder("client");
			
			trans.push(builder, "Visual");
			
			this.leaf_particles = trans.add(builder
				.comment("Use nice falling leaf particles for Aether leaf blocks")
				.translation(trans.transKey(builder, "leaf_particles"))
				.define("Leaf Particles", true));
			trans.push(builder, "Whirlwind");
			this.improved_whirlwinds = trans.add(builder
				.comment("Gives Whirlwinds a new design, based on Minecraft 1.21's new Breeze mob")
				.translation(trans.transKey(builder, "improved_whirlwinds"))
				.define("Improved Whirlwinds", true));
			this.defer_whirlwind_rendering = trans.add(builder
				.comment("Defers Evil Whirlwind rendering to after particles are rendered, in order to fix some transparency sorting issues.")
				.translation(trans.transKey(builder, "defer_whirlwind_rendering"))
				.define("Defer Whirlwind Rendering", true));
			builder.pop();
			this.improved_sheepuffs = trans.add(builder
				.comment("Enables Redux's updated Sheepuff model")
				.translation(trans.transKey(builder, "improved_sheepuffs"))
				.define("Improved Sheepuffs", true));
			this.improved_moas = trans.add(builder
				.comment("Enables Redux's updated Moa model")
				.translation(trans.transKey(builder, "improved_moas"))
				.define("Improved Moas", true));
			this.improved_aerbunnies = trans.add(builder
				.comment("Enables Redux's updated Aerbunny model")
				.translation(trans.transKey(builder, "improved_aerbunnies"))
				.define("Improved Aerbunnies", true));
			this.move_clouds = trans.add(builder
				.comment("Move the clouds in the Aether to above the islands")
				.translation(trans.transKey(builder, "move_clouds"))
				.gameRestart()
				.define("Move Clouds", true));

			this.upgraded_nature = trans.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated Aether nature textures.")
					.translation(trans.transKey(builder, "upgraded_nature"))
					.define("Upgraded Nature", true),
				"upgraded_nature"
			));
			this.upgraded_dungeons = trans.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated dungeon textures.")
					.translation(trans.transKey(builder, "upgraded_dungeons"))
					.define("Upgraded Dungeons", true),
				"upgraded_dungeons"
			));
			this.upgraded_resources = trans.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated resource textures.")
					.translation(trans.transKey(builder, "upgraded_resources"))
					.define("Upgraded Resources", true),
				"upgraded_resources"
			));
			this.upgraded_tools = trans.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Use Redux's updated tool textures.")
					.translation(trans.transKey(builder, "upgraded_tools"))
					.define("Upgraded Tools", true),
				"upgraded_tools"
			));

			builder.pop();

			trans.push(builder, "Audio");
			trans.push(builder, "Slider");
			
			this.slider_sfx_upgrade = trans.add(Redux.ASSETS_CONFIG.register(
				builder
					.comment("Improve the hurt, death, and ambient sounds of the Slider.")
					.translation(trans.transKey(builder, "slider_sfx_upgrade"))
					.define("Slider SFX Upgrades", true),
				"sfx/",
				"slider"
			));
			
			this.slider_signal_sfx = trans.add(builder
				.comment("Gives the Slider a subtle signal effect before sliding.")
				.translation(trans.transKey(builder, "slider_signal_sfx"))
				.define("Slider Movement Signal", true));

			builder.pop(2);
			
			
			this.trans = trans.build();
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
	
	// TODO: refactor into a wrapper around ModConfigSpec.Builder
	private static final class TranslationsBuilder {
		private final HashSet<ModConfigSpec.ConfigValue<?>> cfgs = new HashSet<>();
		private final HashMap<String, String> cats = new HashMap<>();
		private final String kind;
		
		private TranslationsBuilder(String name) {
			this.kind = name;
		}
		
		public <T, V extends ModConfigSpec.ConfigValue<T>> V add(V val) {
			this.cfgs.add(val);
			return val;
		}
		
		private String transKey(ModConfigSpec.Builder builder, String name) {
			var b = new StringBuilder("config." + Redux.MODID + "." + this.kind + ".");
			for (var entry : ((CfgBuilderAccessor) builder).redux$currentPath())  {
				b.append(DatagenUtil.unlocalize(entry));
				b.append('.');
			}
			
			b.append(name);
			
			return b.toString();
		}
		
		private String catTransKey(ModConfigSpec.Builder builder, String name) {
			var b = new StringBuilder("config." + Redux.MODID + ".category." + this.kind + ".");
			for (var entry : ((CfgBuilderAccessor) builder).redux$currentPath())  {
				b.append(DatagenUtil.unlocalize(entry));
				b.append('.');
			}
			
			b.append(name);
			
			return b.toString();
		}
		
		public CfgTranslations build() {
			return new CfgTranslations(
				this.cfgs.parallelStream().collect(Collectors.toUnmodifiableSet()),
				this.cats.entrySet().parallelStream().collect(Collectors.<Map.Entry<String, String>, String, String>toConcurrentMap(Map.Entry::getKey, Map.Entry::getValue)),
				this.kind
			);
		}
		
		@Override
		public String toString() {
			return "ValSetBuilder[" +
				"set=" + this.cfgs +
				']';
		}
		
		public void push(ModConfigSpec.Builder builder, String name) {
			var key = this.catTransKey(builder, DatagenUtil.unlocalize(name));
			builder.translation(key);
			
			builder.push(name);
			this.cats.put(key, name);
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null || this.getClass() != o.getClass()) return false;
			var that = (TranslationsBuilder) o;
			return Objects.equals(this.cfgs, that.cfgs);
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(this.cfgs);
		}
	}
	
	public record CfgTranslations(@Unmodifiable Set<ModConfigSpec.ConfigValue<?>> cfgs, @Unmodifiable ConcurrentMap<String, String> cats, String kind) {}
}
