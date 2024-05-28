package net.zepalesque.redux;

import com.aetherteam.aether.data.AetherData;
import com.aetherteam.aether.data.generators.AetherRegistrySets;
import com.mojang.logging.LogUtils;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.blockset.wood.ReduxWoodSets;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.ReduxConfigHandler;
import net.zepalesque.redux.data.gen.ReduxBlockStateGen;
import net.zepalesque.redux.data.gen.ReduxItemModelGen;
import net.zepalesque.redux.data.gen.ReduxLanguageGen;
import net.zepalesque.redux.data.gen.ReduxLootGen;
import net.zepalesque.redux.data.gen.ReduxRecipeGen;
import net.zepalesque.redux.data.gen.ReduxRegistrySets;
import net.zepalesque.redux.data.gen.tags.ReduxBlockTagsGen;
import net.zepalesque.redux.data.gen.tags.ReduxItemTagsGen;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.tile.ReduxTiles;
import net.zepalesque.zenith.api.blockset.AbstractStoneSet;
import net.zepalesque.zenith.api.blockset.AbstractWoodSet;
import net.zepalesque.zenith.api.condition.ConfigCondition;
import net.zepalesque.zenith.api.condition.config.ConfigSerializer;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Mod(Redux.MODID)
public class Redux {
    public static final String MODID = "aether_redux";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final Collection<AbstractWoodSet> WOOD_SETS = new ArrayList<>();

    public static final Collection<AbstractStoneSet> STONE_SETS = new ArrayList<>();

    public Redux(IEventBus bus) {
        bus.addListener(this::commonSetup);
        bus.addListener(this::dataSetup);

        ReduxWoodSets.init();
        ReduxStoneSets.init();

        ReduxBlocks.BLOCKS.register(bus);
        ReduxItems.ITEMS.register(bus);
        ReduxEntities.ENTITIES.register(bus);
        ReduxTiles.TILES.register(bus);
        ReduxConfigHandler.setup(bus);
        ReduxConfig.COMMON.placeholder.get();
//
//        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, ReduxConfig.SERVER_SPEC, MODID + "/server.toml");
//        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ReduxConfig.COMMON_SPEC, MODID + "/common.toml");
//        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ReduxConfig.CLIENT_SPEC, MODID + "/client.toml");
        ConfigCondition.registerSerializer("redux_server", new ConfigSerializer(ReduxConfig.Server::serialize, ReduxConfig.Server::deserialize));
        ConfigCondition.registerSerializer("redux_common", new ConfigSerializer(ReduxConfig.Common::serialize, ReduxConfig.Common::deserialize));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ReduxBlocks.registerFlammability();
            ReduxBlocks.registerToolConversions();
        });
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
        DatapackBuiltinEntriesProvider registrySets = new ReduxRegistrySets(packOutput, lookupProvider, MODID);
        // Use for structure and damage type data
        CompletableFuture<HolderLookup.Provider> registryProvider = registrySets.getRegistryProvider();
        generator.addProvider(event.includeServer(), registrySets);

        // Tags
        ReduxBlockTagsGen blockTags = new ReduxBlockTagsGen(packOutput, lookupProvider, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ReduxItemTagsGen(packOutput, lookupProvider, blockTags.contentsGetter(), fileHelper));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(packOutput).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_redux.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MODID, path);
    }

}
