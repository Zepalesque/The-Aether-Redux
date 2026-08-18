package net.zepalesque.redux.world.feature.gen;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.carver.CarvingContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.RuleBasedBlockStateProvider;
import net.minecraft.world.level.material.Fluids;
import net.zepalesque.zenith.api.world.density.PerlinNoiseFunction;
import net.zepalesque.zenith.mixin.mixins.common.accessor.ChunkAccessAccessor;
import net.zepalesque.zenith.util.function.Functions;

public class LakesFeature extends Feature<LakesFeature.Config> {
	private static final int SHORE_DEPTH = -1;
	private static final int WATER_DEPTH = SHORE_DEPTH - 1;
	
	public static final int Y_LEVEL_DEFAULT = 52;

	public LakesFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Config> context) {
		var config = context.config();
		var lvl = context.level();
		var rand = context.random();

		var lakeNoise = config.lakeNoise();
		var yOffsetNoise = config.yOffset();
		var visitor = PerlinNoiseFunction.createOrGetVisitor(lvl.getSeed() + 1);

		lakeNoise.mapAll(visitor);
		yOffsetNoise.mapAll(visitor);

		// The feature should be placed once per chunk as it places one-chunk pieces of the lake
		var chunkX = context.origin().getX() - context.origin().getX() % 16;
		var chunkZ = context.origin().getZ() - context.origin().getZ() % 16;

		var y = config.yLevel();

		// Place blocks across the entire chunk
		for (var inChunkX = 0; inChunkX < 16; inChunkX++)
			for (var inChunkZ = 0; inChunkZ < 16; inChunkZ++) {
				// calculate new coords based on the for loops' values
				var x = chunkX + inChunkX;
				var z = chunkZ + inChunkZ;
				// The main lake noise is what is used for the distinction of gaps and non-gaps
				var lakeCalc = lakeNoise.compute(
					new DensityFunction.SinglePointContext(x, y, z)
				);
				
				// A Y offset is then calculated and applied using a second, smoother and larger noise
				var offsetCalc = yOffsetNoise.compute(
					new DensityFunction.SinglePointContext(x, y, z)
				);
				var realOffset = cosineInterp(
					(float) Mth.inverseLerp(offsetCalc, -0.5, 0.5),
					0F,
					(float) config.maxYOffset()
				);
				
				// Interpolate for some extra smoothness
				var reallake = cosineInterp((float) Mth.clamp(lakeCalc, 0, 1), 0, 1);
				// Calculate how deep the lake should be
				var depth = Mth.floor(
					-(Mth.lerp(reallake, 0F, (float) config.lakeDepth() - 1F) - realOffset)
				);
				
				// Place the quicksoil shores
				var pos = new BlockPos(x, y, z);
				if (config.predicate().test(lvl, pos) && (depth == SHORE_DEPTH)) {
					this.setBlock(lvl, pos, config.shore().getState(lvl, rand, pos));

					var below = pos.below();
					if (lvl.getBlockState(below).is(AetherTags.Blocks.AETHER_DIRT)) {
						this.setBlock(lvl, below, context.config().floor().getState(lvl, rand, below));
					}
				}

				if (depth < SHORE_DEPTH) {
					// Place the water itself
					this.placeWater(context, x, y, z, depth);
					// Place stone underneath the water
					this.placeBottom(context, x, y, z, depth, visitor);
				}
			}
		return false;
	}

	private void placeWater(FeaturePlaceContext<Config> context, int x, int y, int z, int depth) {
		var level = context.level();
		var floor = context.config().floor();
		
		for (var i = depth - WATER_DEPTH; i <= 0; i++) {
			var y2 = Mth.clamp(y + i, level.getMinBuildHeight(), level.getMaxBuildHeight());
			var pos = new BlockPos(x, y2, z);
			if (context.config().predicate().test(level, pos)) {
				this.setBlock(level, pos, context.config().fluid().getState(context.random(), pos));

				// Ensure that exposed water flows
				if (i == 0) level.scheduleTick(pos, Fluids.WATER, 0);

				// Ensure there is grass below the water
				var below = pos.below();
				if (level.getBlockState(below).is(AetherTags.Blocks.AETHER_DIRT)) {
					this.setBlock(level, below, floor.getState(level, context.random(), below));
				}
			}
		}
	}

	private void placeBottom(FeaturePlaceContext<Config> context, int x, int y, int z, int depth, PerlinNoiseFunction.PerlinNoiseVisitor visitor) {
		var btm = new BlockPos(x, depth + y - WATER_DEPTH, z).below();
		var level = context.level();
		var predicate = context.config().predicate();
		var floor = context.config().floor();
		var shore = context.config().shore();
		// why must java not allow primitives in generics smh,,,,
		BiFunction<Integer, BlockPos, Optional<Functions.F3<WorldGenLevel, RandomSource, BlockPos, BlockState>>>
			fun = (i, p) -> {						   // vvv  used to be rus,,,, 🦀
				if (i == SHORE_DEPTH) return Optional.of((lvl, rand, pos) -> shore.getState(lvl, rand, pos));
				else if (predicate.test(level, p)) return Optional.of(floor::getState);
				else return Optional.empty();
			};
		
		this.placeBlob(context, btm, fun, depth, visitor);

		for (var dir : Direction.Plane.HORIZONTAL) {
			var pos = btm.relative(dir);
			
			this.placeBlob(context, pos, fun, depth, visitor);
		}
	}
	
	private void placeBlob(
		FeaturePlaceContext<Config> ctx,
		BlockPos origin,
		BiFunction<Integer, BlockPos, Optional<Functions.F3<WorldGenLevel, RandomSource, BlockPos, BlockState>>> fn,
		int depth,
		PerlinNoiseFunction.PerlinNoiseVisitor visitor
	) {
		var x = origin.getX();
		var y = origin.getY();
		var z = origin.getZ();
		
		var noise = ctx.config().thicknessNoise();
		noise.mapAll(visitor);
		var lakeCalc = noise.compute(new DensityFunction.SinglePointContext(x, y, z));
		
		var inverp = Mth.inverseLerp(lakeCalc, -1, 1);
		
		var lvl = ctx.level();
		var rand = ctx.random();
		var predicate = ctx.config().predicate();
		
		var thickness = Mth.clampedLerp(0, ctx.config().maxThicknessRadius(), inverp);
		var ceil = Mth.abs(Mth.ceil(thickness));

		BiConsumer<Integer, BlockPos> fun = (d, pos) -> {
			var state = lvl.getBlockState(pos.above());
			BlockState block;

			if (state.is(Blocks.AIR)) {
				block = this.getSurfaceState(lvl, pos);
			} else if (state.is(Blocks.WATER)) {
				var placer = fn.apply(d, origin);
				if (placer.isEmpty()) return;

				block = placer.get().apply(lvl, rand, pos);
			} else {
				block = AetherFeatureStates.HOLYSTONE;
			}

			this.setBlock(lvl, pos, block);

			var below = pos.below();
			if (lvl.getBlockState(below).is(AetherTags.Blocks.AETHER_DIRT)) {
				this.setBlock(lvl, below, ctx.config().floor().getState(lvl, ctx.random(), below));
			}
		};

		fun.accept(depth, origin);

		for (var i = -ceil; i <= ceil; i++) {
			for (var j = ceil; j >= -ceil; j--) {
				for (var k = -ceil; k <= ceil; k++) {
					if (!(i * i + j * j + k * k <= thickness * thickness)) continue;

					var pos = origin.offset(i, j, k);
					if (predicate.test(lvl, pos)) {
						fun.accept(depth + j, pos);
					}
				}
			}
		}
	}

	private BlockState getSurfaceState(WorldGenLevel level, BlockPos pos) {
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
				
				if (state.isPresent()) {
					return state.get();
				}
			}
		}

		return AetherFeatureStates.AETHER_GRASS_BLOCK;
	}

	// TODO: Modular system for interpolation?
	private static float cosineInterp(float progress, float start, float end) {
		return (-Mth.cos((float) (Math.PI * progress)) + 1F) * 0.5F * (end - start) + start;
	}

	public record Config(
		BlockStateProvider fluid,
		RuleBasedBlockStateProvider shore,
		RuleBasedBlockStateProvider floor,
		BlockPredicate predicate,
		int yLevel,
		DensityFunction lakeNoise,
		double lakeDepth,
		DensityFunction yOffset,
		double maxYOffset,
		DensityFunction thicknessNoise,
		double maxThicknessRadius
	) implements FeatureConfiguration {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create(
			builder -> builder.group(
				BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Config::fluid),
				RuleBasedBlockStateProvider.CODEC.fieldOf("shore").forGetter(Config::shore),
				RuleBasedBlockStateProvider.CODEC.fieldOf("floor").forGetter(Config::floor), BlockPredicate.CODEC.fieldOf("predicate").forGetter(Config::predicate),
				Codec.INT.fieldOf("y_level").forGetter(Config::yLevel),
				DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_noise").forGetter(Config::lakeNoise),
				Codec.DOUBLE.fieldOf("lake_depth").forGetter(Config::lakeDepth),
				DensityFunction.HOLDER_HELPER_CODEC.fieldOf("offset_noise").forGetter(Config::yOffset),
				Codec.DOUBLE.fieldOf("offset_max").forGetter(Config::maxYOffset),
				DensityFunction.HOLDER_HELPER_CODEC.fieldOf("thickness_noise").forGetter(Config::thicknessNoise),
				Codec.DOUBLE.fieldOf("thickness_max").forGetter(Config::maxThicknessRadius)
			).apply(builder, Config::new)
		);
	}
}
