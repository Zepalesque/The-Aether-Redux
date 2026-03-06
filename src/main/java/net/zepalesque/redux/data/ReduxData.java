package net.zepalesque.redux.data;

import com.aetherteam.aether.data.generators.AetherRegistrySets;
import java.util.Optional;
import net.minecraft.DetectedVersion;
import net.minecraft.data.PackOutput;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.ReduxColors;
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

public class ReduxData {
    public static void dataSetup(GatherDataEvent event) {
        var generator = event.getGenerator();
        var fileHelper = event.getExistingFileHelper();
        var lookups = event.getLookupProvider();
        var output = generator.getPackOutput();

        // Client Data
        generator.addProvider(event.includeClient(), new ReduxBlockStateData(output, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxItemModelData(output, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxLanguageData(output));
        generator.addProvider(event.includeClient(), new ReduxParticleData(output, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxSoundsData(output, fileHelper));
        
        var patch = new AetherRegistrySets(output, lookups);
        lookups = patch.getRegistryProvider();

        // Server Data
        DatapackBuiltinEntriesProvider registrySets = new ReduxRegistrySets(output, lookups, Redux.MODID);
        // Use for structure and damage type data, plus any custom ones that need to access the condition registry
        var registries = registrySets.getRegistryProvider();
        generator.addProvider(event.includeServer(), registrySets);
        generator.addProvider(event.includeServer(), new ReduxRecipeData(output, lookups));
        generator.addProvider(event.includeServer(), ReduxLootData.create(output, lookups));
        generator.addProvider(event.includeServer(), new ReduxMapData(output, registries));
        generator.addProvider(event.includeServer(), new ReduxLootModifierData(output, registries));
        generator.addProvider(event.includeServer(), new ReduxAdvancementData(output, registries, fileHelper, ReduxColors.REDUX_PURPLE));

        // Tags
        var blockTags = new ReduxBlockTagsData(output, lookups, fileHelper);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new ReduxItemTagsData(output, lookups, blockTags.contentsGetter(), fileHelper));
        generator.addProvider(event.includeServer(), new ReduxEntityTagsData(output, lookups, fileHelper));

        generator.addProvider(event.includeServer(), new ReduxBiomeTagsData(output, registries, fileHelper));

        // pack.mcmeta
        generator.addProvider(true, new PackMetadataGenerator(output).add(PackMetadataSection.TYPE, new PackMetadataSection(
                Component.translatable("pack.aether_redux.mod.description"),
                DetectedVersion.BUILT_IN.getPackVersion(PackType.SERVER_DATA),
                Optional.of(new InclusiveRange<>(0, Integer.MAX_VALUE)))));
        
        
        var builtinData = output.getOutputFolder().resolve("packs").resolve("data");
        
        var noisePack = generator.new PackGenerator(event.includeServer(), "reduxnoise", new PackOutput(builtinData.resolve("redux_noise")));
        final var immLookups = lookups;
        noisePack.addProvider(output1 -> new ReduxRegistrySets.NoisePack(output1, immLookups, Redux.MODID));
    }
}