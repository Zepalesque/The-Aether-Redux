package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.data.resources.registries.AetherDimensions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.SurfaceRules.RuleSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
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
			/*SurfaceRules.ifTrue(
				SurfaceRules.steep(),
				SurfaceRules.ifTrue(
					SurfaceRules.UNDER_FLOOR,
					surfaceState(AetherBlocks.HOLYSTONE)
				)
			),*/

			scars(ReduxBiomes.GILDED_GROVES, ReduxStoneSets.GILDED_HOLYSTONE.block()),
			scars(ReduxBiomes.THE_BLIGHT, ReduxStoneSets.BLEAKMOSS_HOLYSTONE.block()),

			inBiome(
				ReduxBiomes.GILDED_GROVES,

				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					surfaceState(AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK)
				)
			),

			inBiome(
				ReduxBiomes.THE_BLIGHT,

				SurfaceRules.ifTrue(
					SurfaceRules.UNDER_FLOOR,
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(Noises.ICE, 0.2, 0.4),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.SWAMP, 0.5, 0.8),
							surfaceState(ReduxBlocks.BLEAKMOSS_BLOCK)
						)
					)
				),
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(Noises.SWAMP, 0.4),
						surfaceState(UnityBlocks.COARSE_AETHER_DIRT)
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
			),

			inBiome(
				ReduxBiomes.FROSTED_FORESTS,

				SurfaceRules.ifTrue(
					SurfaceRules.isBiome(
						ReduxBiomes.FROSTED_FORESTS
					), SurfaceRules.ifTrue(
						SurfaceRules.ON_FLOOR, SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(
								Noises.POWDER_SNOW,
								0.35D,
								0.45D
							), SurfaceRules.state(
								Blocks.POWDER_SNOW.defaultBlockState()
							)
						)
					)
				),
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(
							Noises.ICE,
							0.0,
							0.3
						),
						surfaceState(UnityBlocks.COARSE_AETHER_DIRT)
					)
				)
			),
			
			inBiome(
				ReduxBiomes.CLOUDCAP_RIDGE,
				SurfaceRules.ifTrue(
					SurfaceRules.ON_FLOOR,
					SurfaceRules.sequence(
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.ICE, 0.0, 0.2),
							surfaceState(UnityBlocks.COARSE_AETHER_DIRT)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.ICE, 0.2, 0.4),
							surfaceState(ReduxBlocks.AVELIUM)
						),
						SurfaceRules.ifTrue(
							SurfaceRules.noiseCondition(Noises.ICE, 0.4, 0.6),
							surfaceState(UnityBlocks.COARSE_AETHER_DIRT)
						),
						surfaceState(AetherBlocks.AETHER_GRASS_BLOCK)
					)
				)
			)
		);
	}

	private static RuleSource scars(ResourceKey<Biome> biome, DeferredBlock<?> mossy) {
		return inBiome(
			biome,
			SurfaceRules.ifTrue(
				SurfaceRules.UNDER_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.noiseCondition(Noises.ICE, 0.2, 0.4),
					SurfaceRules.ifTrue(
						SurfaceRules.noiseCondition(Noises.SWAMP, 0.4, 0.8),
						surfaceState(mossy)
					)
				)
			),
			SurfaceRules.ifTrue(
				SurfaceRules.UNDER_FLOOR,
				SurfaceRules.ifTrue(
					SurfaceRules.noiseCondition(Noises.ICE, 0.2, 0.4),
					SurfaceRules.state(AetherFeatureStates.HOLYSTONE)
				)
			)
		);
	}

	private static RuleSource inBiome(ResourceKey<Biome> biome, RuleSource... rules) {
		return SurfaceRules.ifTrue(
			SurfaceRules.isBiome(biome),
			rules.length == 1 ? rules[0] : SurfaceRules.sequence(rules)
		);
	}

	private static RuleSource surfaceState(DeferredBlock<?> block) {
		return SurfaceRules.state(UnityFeatureBuilders.drops(block));
	}
}
