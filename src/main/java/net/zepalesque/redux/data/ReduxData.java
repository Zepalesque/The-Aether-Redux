package net.zepalesque.redux.data;

import com.aetherteam.aether.data.generators.AetherSoundData;
import net.minecraft.DetectedVersion;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.gen.ReduxBlockStateGen;
import net.zepalesque.redux.data.gen.ReduxDataMapGen;
import net.zepalesque.redux.data.gen.ReduxItemModelGen;
import net.zepalesque.redux.data.gen.ReduxLanguageGen;
import net.zepalesque.redux.data.gen.ReduxLootGen;
import net.zepalesque.redux.data.gen.ReduxParticleGen;
import net.zepalesque.redux.data.gen.ReduxRecipeGen;
import net.zepalesque.redux.data.gen.ReduxRegistrySets;
import net.zepalesque.redux.data.gen.ReduxSoundsGen;
import net.zepalesque.redux.data.gen.tags.ReduxBiomeTagsGen;
import net.zepalesque.redux.data.gen.tags.ReduxBlockTagsGen;
import net.zepalesque.redux.data.gen.tags.ReduxItemTagsGen;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ReduxData {
    public static void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new ReduxBlockStateGen(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxItemModelGen(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxLanguageGen(packOutput));
        generator.addProvider(event.includeClient(), new ReduxParticleGen(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxSoundsGen(packOutput, fileHelper));

        // Server Data
        generator.addProvider(event.includeServer(), new ReduxRecipeGen(packOutput, lookupProvider));
        generator.addProvider(event.includeServer(), ReduxLootGen.create(packOutput));
        generator.addProvider(event.includeServer(), new ReduxDataMapGen(packOutput, lookupProvider));
        DatapackBuiltinEntriesProvider registrySets = new ReduxRegistrySets(packOutput, lookupProvider, Redux.MODID);
        // Use for structure and damage type data
        CompletableFuture<Provider> registryProvider = registrySets.getRegistryProvider();
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
}
