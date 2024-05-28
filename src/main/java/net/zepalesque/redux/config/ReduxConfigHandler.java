package net.zepalesque.redux.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Files;

// See https://github.com/TelepathicGrunt/Bumblezone/blob/6bbcb628672a77cfa7a2648be9b4d2428d1eeeb7/neoforge/src/main/java/com/telepathicgrunt/the_bumblezone/configs/neoforge/BzConfigHandler.java#L26
public class ReduxConfigHandler {

    public static void setup(IEventBus modEventBus) {

        try {
            Files.createDirectories(FMLPaths.CONFIGDIR.get().resolve("aether_redux"));
            createAndLoadConfigs(ModConfig.Type.CLIENT, ReduxConfig.CLIENT_SPEC, "aether_redux/client.toml");
            createAndLoadConfigs(ModConfig.Type.COMMON, ReduxConfig.COMMON_SPEC, "aether_redux/common.toml");
            createAndLoadConfigs(ModConfig.Type.COMMON, ReduxConfig.SERVER_SPEC, "aether_redux/server.toml");
        }
        catch (Exception e) {
            throw new RuntimeException("Failed to create Aether: Redux config files: ", e);
        }
    }

    private static void createAndLoadConfigs(ModConfig.Type type, ModConfigSpec spec, String path) {
        ModLoadingContext.get().registerConfig(type, spec, path);

        final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve(path))
                .preserveInsertionOrder()
                .autoreload()
                .writingMode(WritingMode.REPLACE)
                .sync()
                .build();

        configData.load();
        spec.setConfig(configData);
    }
}
