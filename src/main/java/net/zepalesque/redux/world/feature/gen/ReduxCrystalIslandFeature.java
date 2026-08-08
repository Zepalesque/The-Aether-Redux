package net.zepalesque.redux.world.feature.gen;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.aetherteam.aether.world.BlockLogicUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.material.Fluids;
import net.zepalesque.redux.data.resource.registries.ReduxFeatureConfig;
import net.zepalesque.zenith.mixin.mixins.common.accessor.ChunkAccessAccessor;

public class ReduxCrystalIslandFeature extends Feature<NoneFeatureConfiguration> {
	public ReduxCrystalIslandFeature() {
		super(NoneFeatureConfiguration.CODEC);
	}

	@Override
	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
		var level = context.level();
		var pos = context.origin();
		var rand = context.random();
		var feature = PlacementUtils.inlinePlaced(level.holderOrThrow(ReduxFeatureConfig.CRYSTAL_TREE)).value();
		
		if (level.getFluidState(pos.above()).is(Fluids.WATER)) {
			return false;
		}

		if (!feature.place(level, context.chunkGenerator(), context.random(), pos.above())) {
			return false;
		}
		
		this.placeSphere(level, pos);

		for (int i = 0; i < 2; i++) {
			var x = rand.nextInt(-1, 1);
			var y = rand.nextInt(-1, 2);
			var z = rand.nextInt(-1, 1);

			if ((x | z) == 0) continue;
			
			this.placeSphere(level, pos.offset(x, y, z));
		}

		return true;
	}

	protected void placeSphere(WorldGenLevel level, BlockPos pos) {
		for (int i = 0; i < 4; i++) {
			var state = i < 2
				? AetherFeatureStates.AETHER_GRASS_BLOCK
				: AetherFeatureStates.HOLYSTONE;

			var offset = i;
			this.setIslandBlock(level, pos.below(offset), state);

			Direction.Plane.HORIZONTAL.forEach(dir -> {
				this.setIslandBlock(level, pos.relative(dir).below(offset), state);
				if (offset == 0 || offset == 3) return;

				this.setIslandBlock(level, pos.relative(dir, 2).below(offset), state);
				this.setIslandBlock(level, pos.relative(dir).relative(dir.getClockWise()).below(offset), state);
			});
		}
	}

	private void setIslandBlock(WorldGenLevel level, BlockPos pos, BlockState testState) {
		// If the processor is running outside the center chunk, return immediately.
		if (level instanceof WorldGenRegion region && BlockLogicUtil.isOutOfBounds(pos, region.getCenter())) return;
		// If the block is a log, return immediately.
		if (level.getBlockState(pos).is(BlockTags.LOGS)) return;

		var state = this.chooseState(level, pos, testState);
		this.setBlock(level, pos, state);

		// Ensure island doesn't leave grass beneath it
		if (state.is(AetherBlocks.HOLYSTONE) && level.getBlockState(pos.below()).is(AetherTags.Blocks.AETHER_DIRT)) {
			level.setBlock(pos.below(), AetherFeatureStates.HOLYSTONE, 3);
		}
	}

	private BlockState chooseState(WorldGenLevel level, BlockPos pos, BlockState testState) {
		if (!testState.is(AetherTags.Blocks.AETHER_DIRT)) {
			return testState;
		}

		if (
			level.getBlockState(pos.above()).isSolidRender(level, pos)
			|| !level.getFluidState(pos.above()).is(Fluids.EMPTY)
		) {
			return AetherFeatureStates.AETHER_DIRT;
		}

		if (
			level.getChunkSource() instanceof ServerChunkCache chunkCache 
			&& chunkCache.getGenerator() instanceof NoiseBasedChunkGenerator generator
		) {
			var settingsHolder = generator.generatorSettings().value();
			var surfaceRule = settingsHolder.surfaceRule();
			var chunkAccess = level.getChunk(pos);
			var noiseChunk = ((ChunkAccessAccessor) chunkAccess).getNoiseChunk();
			
			if (noiseChunk != null) {
				var carvingcontext = new CarvingContext(
					generator,
					level.registryAccess(),
					chunkAccess.getHeightAccessorForGeneration(),
					noiseChunk,
					chunkCache.randomState(),
					surfaceRule
				);
				@SuppressWarnings("deprecation") // `carvingcontext.topMaterial` is fine to use
				var state = carvingcontext.topMaterial(
					level.getBiomeManager()::getNoiseBiomeAtPosition,
					chunkAccess,
					pos,
					false
				);
				
				if (state.isPresent() && state.get().is(AetherTags.Blocks.AETHER_DIRT)) {
					return state.get();
				}
			}
		}

		return testState;
	}
}
