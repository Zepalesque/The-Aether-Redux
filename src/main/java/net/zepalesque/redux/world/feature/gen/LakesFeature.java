package net.zepalesque.redux.world.feature.gen;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.AetherFeatureStates;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.material.Fluids;
import net.zepalesque.zenith.api.world.density.PerlinNoiseFunction;

public class LakesFeature extends Feature<LakesFeature.Config> {
	private static final int SHORE_DEPTH = -1;
	private static final int WATER_DEPTH = SHORE_DEPTH - 1;

	public LakesFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Config> context) {
		var config = context.config();
		var level = context.level();

		var lakeNoise = config.lakeNoise();
		var yOffsetNoise = config.yOffset();
		var visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed() + 1);

		lakeNoise.mapAll(visitor);
		yOffsetNoise.mapAll(visitor);

		// The feature should be placed once per chunk as it places one-chunk pieces of the lake
		var chunkX = context.origin().getX() - (context.origin().getX() % 16);
		var chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);

		var yLevel = config.yLevel();

		// Place blocks across the entire chunk
		for (var x = 0; x < 16; x++) {
			for (var z = 0; z < 16; z++) {
				// calculate new coords based on the for loops' values
				var xCoord = chunkX + x;
				var zCoord = chunkZ + z;
				// The main lake noise is what is used for the distinction of gaps and non-gaps
				var lakeCalc = lakeNoise.compute(
					new DensityFunction.SinglePointContext(xCoord, yLevel, zCoord)
				);

				// A Y offset is then calculated and applied using a second, smoother and larger noise
				var offsetCalc = yOffsetNoise.compute(
					new DensityFunction.SinglePointContext(xCoord, yLevel, zCoord)
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
					-(Mth.lerp(reallake, 0F, (float) config.lakeRadius() - 1F) - realOffset)
				);

				if (depth < SHORE_DEPTH) {
					// Place the water itself
					placeWater(context, xCoord, yLevel, zCoord, depth);
					// Place stone underneath the water
					placeBottom(context, xCoord, yLevel, zCoord, depth);
				}

				// Place the quicksoil shores
				var pos = new BlockPos(xCoord, yLevel, zCoord);
				if (config.predicate().test(level, pos) && depth == SHORE_DEPTH) {
					this.setBlock(level, pos, config.shore().getState(context.random(), pos));
				}
			}
		}
		return false;
	}

	private void placeWater(FeaturePlaceContext<Config> context, int x, int y, int z, int depth) {
		var level = context.level();

		for (var i = depth - WATER_DEPTH; i <= 0; i++) {
			var y2 = Mth.clamp(y + i, level.getMinBuildHeight(), level.getMaxBuildHeight());
			var pos = new BlockPos(x, y2, z);
			if (context.config().predicate().test(level, pos)) {
				this.setBlock(level, pos, context.config().fluid().getState(context.random(), pos));

				// Ensure that exposed water flows
				if (i == 0) {
					level.scheduleTick(pos, Fluids.WATER, 0);
				}

				// Ensure there is grass below the water
				if (level.getBlockState(pos.below()).is(AetherTags.Blocks.AETHER_DIRT)) {
					this.setBlock(level, pos.below(), AetherFeatureStates.AETHER_DIRT);
				}
			}
		}
	}

	private void placeBottom(FeaturePlaceContext<Config> context, int x, int y, int z, int depth) {
		var btm = new BlockPos(x, depth + y - WATER_DEPTH, z);
		if (context.config().predicate().test(context.level(), btm.below())) {
			this.setBlock(context.level(), btm.below(), AetherFeatureStates.HOLYSTONE);
		}
	}

	private static float cosineInterp(float progress, float start, float end) {
		return (-Mth.cos((float) (Math.PI * progress)) + 1F) * 0.5F * (end - start) + start;
	}

	public record Config(
		BlockStateProvider fluid,
		BlockStateProvider shore,
		BlockPredicate predicate,
		int yLevel,
		DensityFunction lakeNoise,
		double lakeRadius,
		DensityFunction yOffset,
		double maxYOffset
	) implements FeatureConfiguration {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((builder) ->
			builder
				.group(
					BlockStateProvider.CODEC.fieldOf("fluid").forGetter(Config::fluid),
					BlockStateProvider.CODEC.fieldOf("shore").forGetter(Config::shore),
					BlockPredicate.CODEC.fieldOf("predicate").forGetter(Config::predicate),
					Codec.INT.fieldOf("y_level").forGetter(Config::yLevel),
					DensityFunction.HOLDER_HELPER_CODEC.fieldOf("lake_noise").forGetter(Config::lakeNoise),
					Codec.DOUBLE.fieldOf("lake_radius").forGetter(Config::lakeRadius),
					DensityFunction.HOLDER_HELPER_CODEC.fieldOf("offset_noise").forGetter(Config::yOffset),
					Codec.DOUBLE.fieldOf("offset_max").forGetter(Config::maxYOffset)
				)
				.apply(builder, Config::new)
		);
	}
}
