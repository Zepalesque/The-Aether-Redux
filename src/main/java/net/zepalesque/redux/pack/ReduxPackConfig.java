package net.zepalesque.redux.pack;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.config.ReduxConfig;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ReduxPackConfig {
    public static ConfigAssembledPackResources.AssembledResourcesSupplier generate(Path path) {
        ImmutableMap.Builder<Supplier<Boolean>, PackResources> builder = new ImmutableMap.Builder<>();
        builder.put(ReduxConfig.CLIENT.tintable_grass, createPack("resource/tintable_grass"));

        return new ConfigAssembledPackResources.AssembledResourcesSupplier(true, builder, path);
    }

    public static PathPackResources createPack(String path) {
        Path resourcePath = ModList.get().getModFileById(Redux.MODID).getFile().findResource("packs/" + path);
        return new PathPackResources(ModList.get().getModFileById(Redux.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath, true);
    }
}
