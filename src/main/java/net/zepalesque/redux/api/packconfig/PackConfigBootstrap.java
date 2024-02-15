package net.zepalesque.redux.api.packconfig;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.loading.FMLLoader;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/** Main startup system for Pack Config related code */
public class PackConfigBootstrap {

    /** Saved configuration files, with keys for their file names. */
    private static final Map<String, Category> configFiles = Maps.newHashMap();

    /** A {@link Gson} used to serialize and deserialize the configs */
    private static final Gson gson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    /** Starts up the pack config system. */
    public static void bootstrap()
    {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            for (Map.Entry<String, Category> entry : configFiles.entrySet()) {
                JsonElement read = read(entry.getKey());
                entry.getValue().read(read);
                JsonElement written = entry.getValue().write();
                write(entry.getKey(), written);
            }
        }
    }

    /** Registers a new config file */
    @Nullable
    public static <T> T register(String id, Function<Category, T> constructor)
    {
        if (FMLLoader.getDist() == Dist.CLIENT) {
            Category base = new Category(id, null);
            T finalized = constructor.apply(base);
            configFiles.put(id, base);
            return finalized;
        } else {
            return null;
        }
    }


    /** Reads a file from the disk */
    public static JsonElement read(String filename)
    {
        Path path = Minecraft.getInstance().gameDirectory.toPath().resolve(Paths.get("config"));
        if (!Files.isDirectory(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Path config = path.resolve(Paths.get(filename + ".json"));
        if (!Files.exists(config))
        {
            Redux.LOGGER.info("Found no pack settings config, Creating...");
            try {
                Files.createFile(config);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new JsonObject();
        } else {
            String s;
            try {
                s = Files.readString(config);
                return gson.fromJson(s, JsonElement.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    /** Writes a file to the disk */
    public static void write(String id, JsonElement json)
    {
        Path path = Minecraft.getInstance().gameDirectory.toPath().resolve(Paths.get("config"));
        if (!Files.isDirectory(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Path config = path.resolve(Paths.get(id + ".json"));
        if (!Files.exists(config))
        {
            Redux.LOGGER.info("Found no pack settings config, Creating...");
            try {
                Files.createFile(config);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            Files.write(config, List.of(gson.toJson(json)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
