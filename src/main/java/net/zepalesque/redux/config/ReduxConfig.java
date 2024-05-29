package net.zepalesque.redux.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ReduxConfig {

    public static class Server extends DataSerializableConfig {

        public final ModConfigSpec.ConfigValue<Boolean> placeholder;

        public Server(ModConfigSpec.Builder builder) {
            super(SERVER_SPEC);
            builder.push("TODO");
            placeholder = builder
                    .comment("Temporary placeholder config, used")
                    .define("Placeholder Config", true);
            builder.pop();
        }
    }

    public static class Common extends DataSerializableConfig {

        public final ModConfigSpec.ConfigValue<Boolean> placeholder;

        public Common(ModConfigSpec.Builder builder) {
            super(COMMON_SPEC);
            builder.push("TODO");
            placeholder = builder
                    .comment("Temporary placeholder config, used")
                    .define("Placeholder Config", true);
            builder.pop();
        }
    }

    public static class Client {

//        public final ModConfigSpec.ConfigValue<Boolean> placeholder;

        public Client(ModConfigSpec.Builder builder) {
            /*builder.push("TODO");
            placeholder = builder
                    .comment("Temporary placeholder config, used")
                    .define("Placeholder Config", true);
            builder.pop();*/
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
