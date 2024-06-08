package net.zepalesque.redux.builtin;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.resource.PathPackResources;
import net.zepalesque.redux.Redux;


import java.nio.file.Path;

public class BuiltinPackUtils {
    public static PathPackResources createPack(String path) {
        Path resourcePath = path(path);
        return new PathPackResources(ModList.get().getModFileById(Redux.MODID).getFile().getFileName() + ":" + resourcePath, resourcePath);
    }


    public static Path path(String path)
    {
        return ModList.get().getModFileById(Redux.MODID).getFile().findResource("packs/" + path);
    }

    public static void mandatory(AddPackFindersEvent event, AbstractPackResources pack, String packId, String title, Component description) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            PackMetadataSection metadata = new PackMetadataSection(description
                    , SharedConstants.getCurrentVersion().getPackVersion(com.mojang.bridge.game.PackType.RESOURCE));
            event.addRepositorySource((packConsumer, packConstructor) ->
                packConsumer.accept(packConstructor.create(
                        "builtin/" + packId,
                        Component.translatable(title),
                        true,
                        () -> pack,
                        metadata,
                        Pack.Position.TOP,
                        PackSource.BUILT_IN,
                        false
                ))
            );
        }

    }

    public static void mandatory(AddPackFindersEvent event, String packId, String title, Component description) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            mandatory(event, createPack(packId), packId, title, description);
        }
    }
}
