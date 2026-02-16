package net.zepalesque.redux.world.feature.gen;

import com.aetherteam.aether.block.AetherBlocks;
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
import net.zepalesque.redux.Redux;
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

				// We don't need to, and shouldn't, generate anything if the cloud noise value is below zero
				if (cloudCalc < 0) continue;

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
				// Calculate how many blocks down from the main y offset plane should be generated
				var blocksDown = Mth.floor(
					-(Mth.lerp(realCloud, 0F, (float) config.cloudRadius() - 1F) - realOffset)
				);
				
				// Floor these values and then place the blocks
				for (var i = blocksDown; i <= 0; i++) {
					var y = Mth.clamp(
						yLevel + i,
						context.level().getMinBuildHeight(),
						context.level().getMaxBuildHeight()
					);
					var pos = new BlockPos(xCoord, y, zCoord);
					if (config.predicate().test(context.level(), pos)) {
						this.setBlock(context.level(), pos, config.block().getState(context.random(), pos));
					}
				}

				if (blocksDown > 1) continue;

				var btm = new BlockPos(xCoord, blocksDown + yLevel, zCoord);
				if (config.predicate().test(context.level(), btm.below())) {
					this.setBlock(context.level(), btm.below(), AetherBlocks.HOLYSTONE.get().defaultBlockState());
				}
			}
		}
		return false;
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
