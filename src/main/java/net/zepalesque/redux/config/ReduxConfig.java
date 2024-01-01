package net.zepalesque.redux.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.zepalesque.redux.config.enums.QuicksoilSetting;
import net.zepalesque.redux.config.enums.MoaFeetType;
import net.zepalesque.redux.config.enums.SpawnerType;
import net.zepalesque.redux.world.biome.ReduxRegion;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class ReduxConfig {

    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Common COMMON;
    public static final ForgeConfigSpec CLIENT_SPEC;
    public static final Client CLIENT;

    public ReduxConfig() {
    }

    static {
        Pair<Common, ForgeConfigSpec> commonSpecPair = (new ForgeConfigSpec.Builder()).configure(Common::new);
        COMMON_SPEC = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        Pair<Client, ForgeConfigSpec> clientSpecPair = (new ForgeConfigSpec.Builder()).configure(Client::new);
        CLIENT_SPEC = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();
    }

    public static class Common {

        public final ForgeConfigSpec.BooleanValue cockatrice_ai_improvements;
        public final ForgeConfigSpec.BooleanValue nerf_gummy_swets;
        public final ForgeConfigSpec.BooleanValue improved_moa_sounds;
        public final ForgeConfigSpec.BooleanValue improved_cockatrice_sounds;
        public final ForgeConfigSpec.BooleanValue improved_aechor_plant_sounds;
        public final ForgeConfigSpec.EnumValue<QuicksoilSetting> quicksoil_movement_system;
        public final ForgeConfigSpec.BooleanValue mobs_avoid_quicksoil;
        public final ForgeConfigSpec.BooleanValue improved_sentry_sounds;
        public final ForgeConfigSpec.BooleanValue improved_slider_hurt_sounds;
        public final ForgeConfigSpec.BooleanValue mossy_holystone_ores;
        public final ForgeConfigSpec.BooleanValue improved_aerwhale_sounds;
        public final ForgeConfigSpec.BooleanValue improved_tempest_sounds;
        public final ForgeConfigSpec.BooleanValue improved_mimic_awaken_sound;
        public final ForgeConfigSpec.BooleanValue better_mimics;
        public final ForgeConfigSpec.EnumValue<SpawnerType> genesis_spawner_mobs;
        public final ForgeConfigSpec.BooleanValue vanilla_consistent_mine_speeds;

        public final ForgeConfigSpec.BooleanValue auto_reset_biome_values;
        public final ForgeConfigSpec.ConfigValue<Integer> region_size;
        public final ForgeConfigSpec.BooleanValue enable_the_blight;
        public final ForgeConfigSpec.BooleanValue enable_gilded_groves;
        public final ForgeConfigSpec.BooleanValue enable_frosted_forests;
        public final ForgeConfigSpec.BooleanValue enable_highfields;
        public final ForgeConfigSpec.BooleanValue enable_cloudcap_jungle;

        public final ForgeConfigSpec.BooleanValue change_double_plant_hitbox;
        public final ForgeConfigSpec.BooleanValue better_conversion_sounds;

        public Common(ForgeConfigSpec.Builder builder) {
            builder.push("Gameplay Changes");
            this.cockatrice_ai_improvements = builder.comment("Makes Cockatrices shoot at you and chase you if they hit you. Requires world restart to refresh existing mob AI.").worldRestart().translation("config.aether_redux.gameplay.cockatrice_ai").define("Improved Cockatrice Behavior", true);
            this.nerf_gummy_swets = builder.comment("Nerfs Gummy Swets, but also makes them craftable. Note: Requires the Aether's Gummy Swet Healing config to be false to properly nerf the hunger effect. Requires datapack reload.").worldRestart().translation("config.aether_redux.gameplay.nerf_gummy_swets").define("Nerf Gummy Swets", true);
            this.mobs_avoid_quicksoil = builder.comment("Causes mobs to try to avoid walking on quicksoil.").translation("config.aether_redux.gameplay.mobs_avoid_quicksoil").define("Mobs Avoid Quicksoil", true);
            this.better_mimics = builder.comment("Makes Mimics smaller and have a new model. NOTE: Requires MC restart when changed.").translation("config.aether_redux.gameplay.better_mimics").define("Better Mimics", true);
            this.vanilla_consistent_mine_speeds = builder.comment("Makes some Aether blocks take longer to break, to be more consistent with vanilla blocks").translation("config.aether_redux.gameplay.vanilla_consistent_mine_speeds").define("Vanilla-Consistent Mining Speeds", false);
            this.change_double_plant_hitbox = builder.comment("Changes the hitboxes of vanilla double plants. Disable if it causes odd behavior.").translation("config.aether_redux.gameplay.change_double_plant_hitbox").define("Change Double Plant Hitboxes", true);
            this.better_conversion_sounds = builder.comment("Makes blockstate conversion sounds (ambrosium, swet ball) better").translation("config.aether_redux.gameplay.better_conversion_sounds").define("Better Conversion Sounds", true);
            builder.pop();
            builder.push("Mob Sound Changes");
            this.improved_moa_sounds = builder.comment("Gives Moas their sounds from older builds of the Aether II: Highlands").translation("config.aether_redux.mob_sounds.improved_moa_sounds").define("Improved Moa Sounds", true);
            this.improved_cockatrice_sounds = builder.comment("Gives Cockatrices their sounds from older builds of the Aether II: Highlands").translation("config.aether_redux.mob_sounds.improved_cockatrice_sounds").define("Improved Cockatrice Sounds", true);
            this.improved_sentry_sounds = builder.comment("Gives Sentries their sounds from older builds of the Aether II: Highlands").translation("config.aether_redux.mob_sounds.improved_sentry_sounds").define("Improved Sentry Sounds", true);
            this.improved_slider_hurt_sounds = builder.comment("Improves the sounds of the slider").translation("config.aether_redux.mob_sounds.improved_slider_sounds").define("Improved Slider Sounds", true);
            this.improved_aechor_plant_sounds = builder.comment("Improves the sounds of Aechor Plants").translation("config.aether_redux.mob_sounds.improved_aechor_plant_sounds").define("Improved Aechor Plant Sounds", true);
            this.improved_aerwhale_sounds = builder.comment("Gives Aerwhales their sounds from older builds of the Aether II: Highlands").translation("config.aether_redux.mob_sounds.improved_aerwhale_sounds").define("Improved Aerwhale Sounds", true);
            this.improved_tempest_sounds = builder.comment("Gives Tempests from the Aether: Genesis (if installed) their sounds from older builds of the Aether II: Highlands").translation("config.aether_redux.mob_sounds.improved_tempest_sounds").define("Improved Tempest Sounds", true);
            this.improved_mimic_awaken_sound = builder.comment("Improves the awakening sound for mimics. Goes great paired with the better mimics option!").translation("config.aether_redux.mob_sounds.improved_mimic_awaken_sound").define("Improved Mimic Awaken Sound", true);
            builder.pop();
            builder.push("Worldgen");
            this.mossy_holystone_ores = builder.comment("Enables Mossy Holystone as an ore. Configurable so that if you disable it, it's easier to tell if you've come across a dungeon.").worldRestart().translation("config.aether_redux.worldgen.mossy_holystone_ores").define("Mossy Holystone Ores", true);
            this.genesis_spawner_mobs = builder.comment("Adds dungeon mobs from the Aether: Genesis to the spawners added to the Bronze Dungeon if it is installed").translation("config.aether_redux.worldgen.genesis_spawner_mobs").defineEnum("Genesis Mobs in Spawners", SpawnerType.all);
            builder.push("Biomes");

            this.auto_reset_biome_values = builder.comment("When enabled, any biome value config changes will be reset. It is recommended that you disable this if you want to keep any custom biome parameters you may have set.").translation("config.aether_redux.biomes.auto_reset_biome_values").define("Auto-Reset Biome Values", true);
            this.region_size = builder.comment("The value of Redux's biome region size. Larger values correspond to larger biome clumps").translation("config.aether_redux.biomes.region_size").define("Region Size", 20);

            this.enable_the_blight = builder.comment("Enables the Blight biome.").worldRestart().translation("config.aether_redux.biomes.blight.enable").define("Enable the Blight", true);
            this.enable_gilded_groves = builder.comment("Enables the Gilded Groves biome.").worldRestart().translation("config.aether_redux.biomes.grove.enable").define("Enable Gilded Groves", true);
            this.enable_frosted_forests = builder.comment("Enables the Frosted Forests biome.").worldRestart().translation("config.aether_redux.biomes.frost.enable").define("Enable Frosted Forests", true);
            this.enable_highfields = builder.comment("Enables the Highfields biome.").worldRestart().translation("config.aether_redux.biomes.high.enable").define("Enable Highfields", true);
            this.enable_cloudcap_jungle = builder.comment("Enables the Cloudcap Jungle biome.").worldRestart().translation("config.aether_redux.biomes.cap.enable").define("Enable Cloudcap Jungle", true);

            builder.pop(2);
            builder.push("Fun");
            this.quicksoil_movement_system = builder.comment("Changes quicksoil between various behaviors. Genesis is the most chaotic, highlands is the most fine-tuned, classic is the original").translation("config.aether_redux.fun.quicksoil_movement_system").defineEnum("Quicksoil Movement System", QuicksoilSetting.highlands);
            builder.pop();
        }
    }

    public static class Client {

        public final ForgeConfigSpec.BooleanValue moa_improvements;
        public final ForgeConfigSpec.EnumValue<MoaFeetType> moa_feet_type;
        public final ForgeConfigSpec.BooleanValue cockatrice_improvements;
        public final ForgeConfigSpec.BooleanValue change_aether_configs;
        public final ForgeConfigSpec.BooleanValue mimic_slam_sound;
        public final ForgeConfigSpec.BooleanValue sentry_improvements;
        public final ForgeConfigSpec.ConfigValue<String> version_id;
        public final ForgeConfigSpec.BooleanValue better_leaf_particles;
        public final ForgeConfigSpec.BooleanValue auto_apply_overrides;

        public Client(ForgeConfigSpec.Builder builder) {

            builder.push("Core");
            builder.pop();
            builder.push(List.of("Rendering", "Mob Model Improvements"));
            this.moa_improvements = builder.comment("Makes Moas resemble their Mutation models").translation("config.aether_redux.client.mob_models.moa").define("Moa Model Improvements", false);
            this.moa_feet_type = builder.comment("Determines whether the updated Moa model should use more peaceful-appearing toes, or more aggressive talons").translation("config.aether_redux.client.mob_models.moa_feet").defineEnum("Moa Foot Type", MoaFeetType.toes);
            this.cockatrice_improvements = builder.comment("Makes Cockatrices look far better").translation("config.aether_redux.client.mob_models.cockatrice").define("Cockatrice Model Improvements", true);
            this.sentry_improvements = builder.comment("Improves the model for Sentries, giving them a new model and a springy jump animation").translation("config.aether_redux.client.mob_models.sentry").define("Sentry Model Improvements", true);
            builder.pop();
            builder.push("Particles");
            this.better_leaf_particles = builder.comment("Improves the leaf particles for Golden Oaks, Gilded Oaks, and Crystal Trees, based on Minecraft 1.20's new cherry tree particles.").translation("config.aether_redux.client.particles.better_leaf_particles").define("Better Leaf Particles", true);
            builder.pop(2);
            builder.push("Audio");
            this.mimic_slam_sound = builder.comment("Enables a slamming sound for mimics when using the new model. Disable if it gets too annoying XD").translation("config.aether_redux.client.particles.mimic_slam_sound").define("Mimic Slam Sound Effect", true);
            builder.pop();
            builder.push("Packs");
            this.auto_apply_overrides = builder.comment("Automatically adds the overrides pack when disabled.").translation("config.aether_redux.client.packs.auto_apply_overrides").define("Auto-Apply Overrides Pack", true);
            builder.pop();
            builder.push("Internal");
            this.change_aether_configs = builder.comment("Internal value to decide if it is the first startup and the base Aether configs should be modified. DO NOT EDIT THIS VALUE, unless you want things to break!").translation("config.aether_redux.client.internal.change_aether_configs").define("First startup, change Aether configs (DO NOT CHANGE)", true);
            this.version_id = builder.comment("Leftover value. May get used in a future update, or get removed.").translation("config.aether_redux.client.internal.version_id").define("Version ID", "undefined");
            builder.pop();
        }
    }
}
