package net.zepalesque.redux.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.zenith.config.DataSerializableConfig;
import org.apache.commons.lang3.tuple.Pair;

public class ReduxConfig {

    public static class Server extends DataSerializableConfig {

        public final ModConfigSpec.ConfigValue<Boolean> redux_sky_colors;
        public final ModConfigSpec.ConfigValue<Boolean> cloudbed;
        public final ModConfigSpec.ConfigValue<Boolean> revamped_quicksoil_movement;

        public Server(ModConfigSpec.Builder builder) {
            super(() -> SERVER_SPEC, "redux_server");
            builder.push("TODO");
            redux_sky_colors = builder
                    .comment("Use Redux's alternative sky colors for the Aether")
                    .define("Redux Sky Colors", true);
            cloudbed = builder
                    .comment("Replace the Aether's large Aercloud features with a noise-based cloudbed")
                    .define("Cloudbed", true);
            revamped_quicksoil_movement = builder
                    .comment("Changes quicksoil to make it use a better movement system, based on the way it worked in the Aether II: Highlands in 1.12")
                    .define("Revamped Quicksoil Movement", true);
            builder.pop();
        }
    }

    public static class Common extends DataSerializableConfig {

        public final ModConfigSpec.ConfigValue<Boolean> bronze_dungeon_upgrade;

        public Common(ModConfigSpec.Builder builder) {
            super(() -> COMMON_SPEC, "redux_common");
            builder.push("TODO");
            bronze_dungeon_upgrade = builder
                    .comment("Upgrades the Bronze Dungeon structure with new blocks and more depth")
                    .define("Bronze Dungeon Upgrade", true);
            builder.pop();
        }
    }

    public static class Client {

        public final ModConfigSpec.ConfigValue<Boolean> leaf_particles;

        public Client(ModConfigSpec.Builder builder) {
            builder.push("TODO");
            leaf_particles = builder
                    .comment("Use nice falling leaf particles for Aether leaf blocks")
                    .define("Leaf Particles", true);
            builder.pop();
        }
    }

    public static final ModConfigSpec COMMON_SPEC, SERVER_SPEC, CLIENT_SPEC;
    public static final Common COMMON;
    public static final Server SERVER;
    public static final Client CLIENT;

    static {
        final Pair<Server, ModConfigSpec> server = new ModConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = server.getRight();
        SERVER = server.getLeft();

        final Pair<Common, ModConfigSpec> common = new ModConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = common.getRight();
        COMMON = common.getLeft();

        final Pair<Client, ModConfigSpec> client = new ModConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = client.getRight();
        CLIENT = client.getLeft();
    }

}
