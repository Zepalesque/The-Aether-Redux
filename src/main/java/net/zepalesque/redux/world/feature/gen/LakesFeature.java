package net.zepalesque.redux.world.feature.gen;

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
import net.zepalesque.zenith.api.world.density.PerlinNoiseFunction;

public class LakesFeature extends Feature<LakesFeature.Config> {

	public LakesFeature(Codec<Config> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<Config> context) {
		var config = context.config();
		var level = context.level();

		var cloudNoise = config.cloudNoise();
		var yOffsetNoise = config.yOffset();
		var visitor = PerlinNoiseFunction.createOrGetVisitor(level.getSeed() + 1);

		cloudNoise.mapAll(visitor);
		yOffsetNoise.mapAll(visitor);

		// The feature should be placed once per chunk as it places one-chunk pieces of the cloudbed
		var chunkX = context.origin().getX() - (context.origin().getX() % 16);
		var chunkZ = context.origin().getZ() - (context.origin().getZ() % 16);

		var yLevel = config.yLevel();

		// Place blocks across the entire chunk
		for (var x = 0; x < 16; x++) {
			for (var z = 0; z < 16; z++) {
				// calculate new coords based on the for loops' values
				var xCoord = chunkX + x;
				var zCoord = chunkZ + z;
				// The main cloud noise is what is used for the distinction of gaps and non-gaps
				var cloudCalc = cloudNoise.compute(
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
				var realCloud = cosineInterp((float) Mth.clamp(cloudCalc, 0, 1), 0, 1);
				// Calculate how deep the lake should be
				var depth = Mth.floor(
					-(Mth.lerp(realCloud, 0F, (float) config.cloudRadius() - 1F) - realOffset)
				);

				if (depth < 1) {
					// Place the water itself
					placeWater(context, xCoord, yLevel, zCoord, depth);
					// Place stone underneath the water
					placeBottom(context, xCoord, yLevel, zCoord, depth);
				}

				// Place the quicksoil shores
				var pos = new BlockPos(xCoord, yLevel, zCoord);
				if (config.predicate().test(level, pos) && depth == 1) {
					this.setBlock(level, pos, AetherFeatureStates.QUICKSOIL);
				}
			}
		}
		return false;
	}

	private void placeWater(FeaturePlaceContext<Config> context, int x, int y, int z, int depth) {
		for (var i = depth; i <= 0; i++) {
			var y2 = Mth.clamp(
				y + i,
				context.level().getMinBuildHeight(),
				context.level().getMaxBuildHeight()
			);
			var pos = new BlockPos(x, y2, z);
			if (context.config().predicate().test(context.level(), pos)) {
				this.setBlock(context.level(), pos, context.config().block().getState(context.random(), pos));
			}
		}
	}

	private void placeBottom(FeaturePlaceContext<Config> context, int x, int y, int z, int depth) {
		var btm = new BlockPos(x, depth + y, z);
		if (context.config().predicate().test(context.level(), btm.below())) {
			this.setBlock(context.level(), btm.below(), AetherFeatureStates.HOLYSTONE);
		}
	}

	private static float cosineInterp(float progress, float start, float end) {
		return (-Mth.cos((float) (Math.PI * progress)) + 1F) * 0.5F * (end - start) + start;
	}

	public record Config(
		BlockStateProvider block,
		BlockPredicate predicate,
		int yLevel,
		DensityFunction cloudNoise,
		double cloudRadius,
		DensityFunction yOffset,
		double maxYOffset
	) implements FeatureConfiguration {
		public static final Codec<Config> CODEC = RecordCodecBuilder.create((builder) ->
			builder
				.group(
					BlockStateProvider.CODEC.fieldOf("block").forGetter(Config::block),
					BlockPredicate.CODEC.fieldOf("predicate").forGetter(Config::predicate),
					Codec.INT.fieldOf("y_level").forGetter(Config::yLevel),
					DensityFunction.HOLDER_HELPER_CODEC.fieldOf("cloud_noise").forGetter(Config::cloudNoise),
					Codec.DOUBLE.fieldOf("cloud_radius").forGetter(Config::cloudRadius),
					DensityFunction.HOLDER_HELPER_CODEC.fieldOf("offset_noise").forGetter(Config::yOffset),
					Codec.DOUBLE.fieldOf("offset_max").forGetter(Config::maxYOffset)
				)
				.apply(builder, Config::new)
		);
	}
}
