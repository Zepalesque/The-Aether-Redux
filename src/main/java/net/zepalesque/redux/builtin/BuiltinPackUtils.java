package net.zepalesque.redux.builtin;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.AbstractPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.resource.PathPackResources;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.misc.ReduxPackSources;

import java.nio.file.Path;

public class BuiltinPackUtils {



    public static PathPackResources createPack(String path) {
        Path resourcePath = path(path);
        return new PathPackResources(ModList.get().getModFileById(Redux.MODID).getFile().getFileName() + ":" + resourcePath, true, resourcePath);
    }

    public static Path path(String path)
    {
        return ModList.get().getModFileById(Redux.MODID).getFile().findResource("packs/" + path);
    }

    public static void autoApply(AddPackFindersEvent event, AbstractPackResources pack, String packId, String title, Component description) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            PackMetadataSection metadata = new PackMetadataSection(description
                    , SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((packConsumer) -> {
                packConsumer.accept(Pack.create("builtin/" + packId, Component.translatable(title),
                        false,
                        (string) -> pack,
                        new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                        PackType.CLIENT_RESOURCES,
                        Pack.Position.TOP,
                        false,
                        ReduxPackSources.AUTO_APPLY_RESOURCE));
            });
        }
    }

    public static void mandatory(AddPackFindersEvent event, AbstractPackResources pack, String packId, String title, Component description) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            PackMetadataSection metadata = new PackMetadataSection(description
                    , SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((packConsumer) -> {
                packConsumer.accept(Pack.create("builtin/" + packId, Component.translatable(title),
                        true,
                        (string) -> pack,
                        new Pack.Info(metadata.getDescription(), metadata.getPackFormat(PackType.SERVER_DATA), metadata.getPackFormat(PackType.CLIENT_RESOURCES), FeatureFlagSet.of(), pack.isHidden()),
                        PackType.CLIENT_RESOURCES,
                        Pack.Position.TOP,
                        false,
                        PackSource.BUILT_IN));
            });
        }
    }
}
