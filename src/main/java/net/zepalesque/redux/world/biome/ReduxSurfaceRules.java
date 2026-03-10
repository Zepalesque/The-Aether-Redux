package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.block.state.enums.BlightGrassColor;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;
import net.zepalesque.unity.block.UnityBlocks;
import net.zepalesque.unity.data.resource.builders.UnityFeatureBuilders;
import net.zepalesque.zenith.api.world.density.PerlinNoiseFunction;

@EventBusSubscriber(modid = Redux.MODID)
public class ReduxSurfaceRules {

	@SubscribeEvent
	public static void onServerAboutToStart(ServerAboutToStartEvent event) {
		var server = event.getServer();
		var access = server.registryAccess();
		var registry = access.registryOrThrow(Registries.LEVEL_STEM);
		var stem = registry.get(AetherDimensions.AETHER_LEVEL_STEM);
		if (stem != null) {
			var gen = stem.generator();
			var level = server.getLevel(AetherDimensions.AETHER_LEVEL);
			if (gen instanceof NoiseBasedChunkGenerator noise && level != null) {
				noise
					.generatorSettings()
					.value()
					.noiseRouter()
					.finalDensity()
					.mapAll(PerlinNoiseFunction.createOrGetVisitor(level.getSeed()));
			}
		}
	}

	public static SurfaceRules.RuleSource makeRules() {
		return SurfaceRules.sequence(
			SurfaceRules.ifTrue(
				SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES),
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.state(
						AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get().defaultBlockState()
					)
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES),
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.6),
						SurfaceRules.state(AetherFeatureStates.HOLYSTONE)
					)
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.isBiome(ReduxBiomes.GILDED_GROVES),
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.6),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SWAMP, 0.3, 0.4),
							SurfaceRules.state(
								ReduxStoneSets.GILDED_HOLYSTONE
									.block()
									.get()
									.defaultBlockState()
									.setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)
							)
						)
					)
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.isBiome(ReduxBiomes.THE_BLIGHT),
				SurfaceRules.sequence(
					SurfaceRules.ifTrue(
						SurfaceRules.ON_FLOOR,
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SWAMP, 0.4),
							SurfaceRules.state(
								UnityFeatureBuilders.drops(UnityBlocks.COARSE_AETHER_DIRT)
							)
						)
					),
					SurfaceRules.ifTrue(
						SurfaceRules.ON_FLOOR,
						SurfaceRules.state(
							UnityFeatureBuilders.drops(
								ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get().defaultBlockState()
							).setValue(
								ReduxStates.BLIGHT_GRASS_COLOR,
								BlightGrassColor.TINTABLE
							)
						)
					)
				)
			)
		);
	}
}
