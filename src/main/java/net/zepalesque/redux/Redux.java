package net.zepalesque.redux;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.generators.AetherDataMapData;
import com.mojang.logging.LogUtils;
import net.minecraft.DetectedVersion;
import net.minecraft.SharedConstants;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackCompatibility;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.InclusiveRange;
import net.minecraft.world.flag.FeatureFlagSet;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.blockset.wood.ReduxWoodSets;
import net.zepalesque.redux.client.ReduxColors;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.ReduxConfigHandler;
import net.zepalesque.redux.data.gen.ReduxBlockStateGen;
import net.zepalesque.redux.data.gen.ReduxDataMapGen;
import net.zepalesque.redux.data.gen.ReduxItemModelGen;
import net.zepalesque.redux.data.gen.ReduxLanguageGen;
import net.zepalesque.redux.data.gen.ReduxLootGen;
import net.zepalesque.redux.data.gen.ReduxRecipeGen;
import net.zepalesque.redux.data.gen.ReduxRegistrySets;
import net.zepalesque.redux.data.gen.tags.ReduxBiomeTagsGen;
import net.zepalesque.redux.data.gen.tags.ReduxBlockTagsGen;
import net.zepalesque.redux.data.gen.tags.ReduxItemTagsGen;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.tile.ReduxTiles;
import net.zepalesque.redux.world.biome.tint.ReduxBiomeTints;
import net.zepalesque.redux.world.feature.gen.ReduxFeatures;
import net.zepalesque.zenith.api.blockset.AbstractStoneSet;
import net.zepalesque.zenith.api.blockset.AbstractWoodSet;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mod(Redux.MODID)
public class Redux {
    public static final String MODID = "aether_redux";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final Collection<AbstractWoodSet> WOOD_SETS = new ArrayList<>();

    public static final Collection<AbstractStoneSet> STONE_SETS = new ArrayList<>();

    public Redux(IEventBus bus, Dist dist) {
        bus.addListener(this::commonSetup);
        bus.addListener(this::dataSetup);
        bus.addListener(this::registerDataMaps);
        bus.addListener(this::packSetup);
        if (dist == Dist.CLIENT) {
            bus.addListener(EventPriority.LOWEST, ReduxColors::blockColors);
            bus.addListener(ReduxColors::itemColors);
            bus.addListener(ReduxColors::resolvers);
        }

        ReduxWoodSets.init();
        ReduxStoneSets.init();

        DeferredRegister<?>[] registers = {
                ReduxBlocks.BLOCKS,
                ReduxItems.ITEMS,
                ReduxEntities.ENTITIES,
                ReduxTiles.TILES,
                ReduxBiomeTints.TINTS,
                ReduxFeatures.FEATURES
        };

        for (DeferredRegister<?> register : registers) {
            register.register(bus);
        }

        ReduxConfigHandler.setup(bus);

        ReduxConfig.SERVER.registerSerializer();
        ReduxConfig.COMMON.registerSerializer();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ReduxBlocks.registerFlammability();
            ReduxBlocks.registerToolConversions();
        });
    }

    private void registerDataMaps(RegisterDataMapTypesEvent event) {
    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new ReduxBlockStateGen(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxItemModelGen(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxLanguageGen(packOutput));

        // Server Data
        generator.addProvider(event.includeServer(), new ReduxRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), ReduxLootGen.create(packOutput));
        generator.addProvider(event.includeServer(), new ReduxDataMapGen(packOutput, lookupProvider));
        DatapackBuiltinEntriesProvider registrySets = new ReduxRegistrySets(packOutput, lookupProvider, MODID);
        // Use for structure and damage type data
        CompletableFuture<HolderLookup.Provider> registryProvider = registrySets.getRegistryProvider();
        generator.addProvider(event.includeServer(), registrySets);

        // Tags
        ReduxBlockTagsGen blockTags = new ReduxBlockTagsGen(packOutput, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ReduxItemTagsGen(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new ReduxBiomeTagsGen(packOutput, lookupProvider, fileHelper));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_redux.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }

    // TODO: probably fairly obvious what is TODO (pack config)
    public  void packSetup(AddPackFindersEvent event) {

        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            Path resourcePath = ModList.get().getModFileById(Aether.MODID).getFile().findResource("packs/resource/tintable_grass");
            PackMetadataSection metadata = new PackMetadataSection(Component.translatable("pack.aether_redux.tintable_grass.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
            event.addRepositorySource((source) ->
                    source.accept(Pack.create(
                            "builtin/redux/tintable_grass",
                            Component.translatable("pack.aether_redux.tintable_grass"),
                            false,
                            new PathPackResources.PathResourcesSupplier(resourcePath, true),
                            new Pack.Info(metadata.description(), PackCompatibility.COMPATIBLE, FeatureFlagSet.of(), List.of(), false),
                            Pack.Position.TOP,
                            false,
                            PackSource.BUILT_IN)
                    )
            );
        }
}

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MODID, path);
    }

}
