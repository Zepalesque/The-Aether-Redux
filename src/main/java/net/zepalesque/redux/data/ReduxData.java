package net.zepalesque.redux.data;

import com.aetherteam.aether.data.generators.AetherRegistrySets;
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
import net.zepalesque.redux.data.gen.ReduxAdvancementData;
import net.zepalesque.redux.data.gen.ReduxBlockStateData;
import net.zepalesque.redux.data.gen.ReduxItemModelData;
import net.zepalesque.redux.data.gen.ReduxLanguageData;
import net.zepalesque.redux.data.gen.ReduxLootData;
import net.zepalesque.redux.data.gen.ReduxLootModifierData;
import net.zepalesque.redux.data.gen.ReduxMapData;
import net.zepalesque.redux.data.gen.ReduxParticleData;
import net.zepalesque.redux.data.gen.ReduxRecipeData;
import net.zepalesque.redux.data.gen.ReduxRegistrySets;
import net.zepalesque.redux.data.gen.ReduxSoundsData;
import net.zepalesque.redux.data.gen.tags.ReduxBiomeTagsData;
import net.zepalesque.redux.data.gen.tags.ReduxBlockTagsData;
import net.zepalesque.redux.data.gen.tags.ReduxEntityTagsData;
import net.zepalesque.redux.data.gen.tags.ReduxItemTagsData;

import java.nio.file.Path;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ReduxData {
    public static void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookups = event.getLookupProvider();
        PackOutput output = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new ReduxBlockStateData(output, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxItemModelData(output, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxLanguageData(output));
        generator.addProvider(event.includeClient(), new ReduxParticleData(output, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxSoundsData(output, fileHelper));

        AetherRegistrySets patch = new AetherRegistrySets(output, lookups);
        lookups = patch.getRegistryProvider();

        // Server Data
        DatapackBuiltinEntriesProvider registrySets = new ReduxRegistrySets(output, lookups, Redux.MODID);
            // Use for structure and damage type data, plus any custom ones that need to access the condition registry
        CompletableFuture<Provider> registries = registrySets.getRegistryProvider();
        generator.addProvider(event.includeServer(), registrySets);
        generator.addProvider(event.includeServer(), new ReduxRecipeData(output, lookups));
        generator.addProvider(event.includeServer(), ReduxLootData.create(output, lookups));
        generator.addProvider(event.includeServer(), new ReduxMapData(output, lookups));
        generator.addProvider(event.includeServer(), new ReduxLootModifierData(output, registries));
        generator.addProvider(event.includeServer(), new ReduxAdvancementData(output, registries, fileHelper));

        // Tags
        ReduxBlockTagsData blockTags = new ReduxBlockTagsData(output, lookups, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ReduxItemTagsData(output, lookups, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new ReduxEntityTagsData(output, lookups, fileHelper));

        generator.addProvider(event.includeServer(), new ReduxBiomeTagsData(output, registries, fileHelper));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_redux.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));


        Path builtinData = output.getOutputFolder().resolve("packs").resolve("data");
        
        DataGenerator.PackGenerator noisePack = generator.new PackGenerator(event.includeServer(), "reduxnoise", new PackOutput(builtinData.resolve("redux_noise")));
        final CompletableFuture<Provider> finalLookups = lookups;
        noisePack.addProvider(output1 -> new ReduxRegistrySets.NoisePack(output1, finalLookups, Redux.MODID));
    }
}