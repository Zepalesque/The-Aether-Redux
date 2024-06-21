package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ReduxSurfaceRules {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        RegistryAccess registryAccess = server.registryAccess();
        Registry<LevelStem> levelStemRegistry = registryAccess.registryOrThrow(Registries.LEVEL_STEM);
        LevelStem levelStem = levelStemRegistry.get(AetherDimensions.AETHER_LEVEL_STEM);
        if (levelStem != null) {
            ChunkGenerator chunkGenerator = levelStem.generator();
            if (chunkGenerator instanceof NoiseBasedChunkGenerator noiseGenerator) {
                NoiseGeneratorSettings noiseGeneratorSettings = noiseGenerator.settings.value();
                SurfaceRules.RuleSource currentRuleSource = noiseGeneratorSettings.surfaceRule();
                if (currentRuleSource instanceof SurfaceRules.SequenceRuleSource sequenceRuleSource) {
                    Redux.LOGGER.info("Patching Redux surface rules...");
                    SurfaceRules.RuleSource sequence = makeRules(sequenceRuleSource);
                    NoiseGeneratorSettings moddedNoiseGeneratorSettings = new NoiseGeneratorSettings(noiseGeneratorSettings.noiseSettings(), noiseGeneratorSettings.defaultBlock(), noiseGeneratorSettings.defaultFluid(), noiseGeneratorSettings.noiseRouter(), sequence, noiseGeneratorSettings.spawnTarget(), noiseGeneratorSettings.seaLevel(), noiseGeneratorSettings.disableMobGeneration(), noiseGeneratorSettings.aquifersEnabled(), noiseGeneratorSettings.oreVeinsEnabled(), noiseGeneratorSettings.useLegacyRandomSource());
                    noiseGenerator.settings = Holder.direct(moddedNoiseGeneratorSettings);
                }
            }
        }
    }

    public static SurfaceRules.RuleSource makeRules(SurfaceRules.SequenceRuleSource base) {
        List<SurfaceRules.RuleSource> list = new ArrayList<>(base.sequence());
        List<SurfaceRules.RuleSource> list1 = List.of(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES), SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state((AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState()))))
        );
        list.addAll(list1);
        return SurfaceRules.sequence(list.toArray(new SurfaceRules.RuleSource[0]));
    }
}