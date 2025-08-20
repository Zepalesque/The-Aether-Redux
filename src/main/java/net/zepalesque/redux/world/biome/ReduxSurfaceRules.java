package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.condition.Conditions;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.blight.BlightedGrassBlock;
import net.zepalesque.redux.block.util.state.ReduxStates;
import net.zepalesque.redux.block.util.state.enums.BlightGrassColor;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.mixin.common.world.NoiseSettingsAccessor;
import net.zepalesque.redux.util.level.WorldgenUtil;
import net.zepalesque.redux.world.biome.surfacerule.ConditionCondition;
import teamrazor.deepaether.init.DABlocks;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(
    modid = Redux.MODID,
    bus = EventBusSubscriber.Bus.FORGE
)
public class ReduxSurfaceRules {
    @SubscribeEvent
    public static void onServerAboutToStart(ServerAboutToStartEvent event) {
        MinecraftServer server = event.getServer();
        RegistryAccess access = server.registryAccess();
        Registry<LevelStem> registry = access.registryOrThrow(Registries.LEVEL_STEM);
        LevelStem levelStem = registry.get(AetherDimensions.AETHER_LEVEL_STEM);
        if (levelStem != null) {
            ChunkGenerator generator = levelStem.generator();
            if (generator instanceof NoiseBasedChunkGenerator noiseGen) {
                NoiseGeneratorSettings settings = noiseGen.settings.value();
                SurfaceRules.RuleSource current = settings.surfaceRule();
                if (current instanceof SurfaceRules.SequenceRuleSource sequence) {
                    Supplier<Block> coarseDirt = getCoarseDirt();
                    BlockState cds = coarseDirt.get().defaultBlockState()
                        .setValue(AetherBlockStateProperties.DOUBLE_DROPS, true);
                    List<SurfaceRules.RuleSource> newRules = new LinkedList<>(sequence.sequence());
                    
                    newRules.add(0, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.THE_BLIGHT),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.state(
                                WorldgenUtil.trySetValue(ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get().defaultBlockState(),
                                    ReduxStates.BLIGHT_GRASS_COLOR, BlightGrassColor.TINTABLE).setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)
                            )))
                    );
                    newRules.add(0, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.THE_BLIGHT),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.4),
                                SurfaceRules.state(cds))))
                    );
                    newRules.add(1, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.FROSTED_FORESTS),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.ifTrue(
                                SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.4),
                                SurfaceRules.state(Blocks.SNOW_BLOCK.defaultBlockState()))))
                    );
                    newRules.add(1, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.FROSTED_FORESTS),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.POWDER_SNOW, 0.35D, 0.45D),
                                SurfaceRules.state(Blocks.POWDER_SNOW.defaultBlockState()))))
                    );
                    newRules.add(2, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GLACIAL_TUNDRA),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.15),
                                SurfaceRules.state(cds))))
                    );
                    newRules.add(3, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES),
                        SurfaceRules.ifTrue(new ConditionCondition(Conditions.ENCHGRASS),
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState()))))
                    );
                    newRules.add(3, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES),
                        SurfaceRules.ifTrue(new ConditionCondition(Conditions.LEGACY_GILDED),
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2),
                                    SurfaceRules.state(AetherFeatureStates.HOLYSTONE)))))
                    );
                    newRules.add(3, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES),
                        SurfaceRules.ifTrue(new ConditionCondition(Conditions.LEGACY_GILDED),
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2),
                                    SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SWAMP, 0.3, 0.4),
                                        SurfaceRules.state(ReduxBlocks.GILDED_HOLYSTONE.get().defaultBlockState()
                                            .setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)))))))
                    );
                    newRules.add(4, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.GILDED_GRASSLANDS),
                        SurfaceRules.ifTrue(new ConditionCondition(Conditions.ENCHGRASS),
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                                SurfaceRules.state(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState()))))
                    );
                    newRules.add(5, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.CLOUDCAPS),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.state(ReduxBlocks.AVELIUM.get().defaultBlockState()
                                .setValue(AetherBlockStateProperties.DOUBLE_DROPS, true))))
                    );
                    newRules.add(5, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.CLOUDCAPS),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2),
                                SurfaceRules.state(cds))))
                    );
                    newRules.add(6, SurfaceRules.ifTrue(SurfaceRules.isBiome(ReduxBiomes.SKYROOT_SHRUBLANDS),
                        SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2),
                                SurfaceRules.state(cds))))
                    );
                  
                    SurfaceRules.RuleSource modified = SurfaceRules.sequence(newRules.toArray(SurfaceRules.RuleSource[]::new));
                    ((NoiseSettingsAccessor) (Object) noiseGen.settings.value()).redux$setSurfaceRule(modified);
                }
            }
        }
    }
    
    private static Supplier<Block> getCoarseDirt() {
        return Redux.deepAetherCompat() ? DABlocks.AETHER_COARSE_DIRT : ReduxBlocks.COARSE_AETHER_DIRT;
    }
}