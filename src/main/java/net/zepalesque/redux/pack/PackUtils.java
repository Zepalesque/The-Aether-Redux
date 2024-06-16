package net.zepalesque.redux.pack;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.zepalesque.redux.Redux;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Function;

public class PackUtils {

    public static void setupPack(AddPackFindersEvent event, String path, String id, PackType side, boolean required, Function<Path, Pack.ResourcesSupplier> packBuilder) {
        if (event.getPackType() == side) {
            String folder = (side == PackType.SERVER_DATA ? "data/" : "resource/");
            Path resourcePath = ModList.get().getModFileById(Redux.MODID).getFile().findResource("packs/" + folder + path);
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether_redux." + id + ".description"),
                    SharedConstants.getCurrentVersion().getPackVersion(side));
            event.addRepositorySource((source) ->
                source.accept(Pack.create(
                        "builtin/redux/" + folder + path,
                        Component.translatable("pack.aether_redux." + id + ".title"),
                        required,
                        packBuilder.apply(resourcePath),
                        new Pack.Info(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                        Pack.Position.TOP,
                        false,
                        PackSource.BUILT_IN)
                ));
        }
    }

    public static void setupPack(AddPackFindersEvent event, String path, String id, PackType side, boolean required) {
        setupPack(event, path, id, side, required, resourcePath -> new PathPackResources.PathResourcesSupplier(resourcePath, true));
    }
}
