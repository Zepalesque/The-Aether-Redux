package net.zepalesque.redux.world.tree.foliage;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class MoonfirFoliagePlacer extends FoliagePlacer {
	public static final MapCodec<MoonfirFoliagePlacer> CODEC = RecordCodecBuilder.mapCodec(
		builder -> foliagePlacerParts(builder)
			.and(IntProvider.codec(0, 24).fieldOf("trunk_height").forGetter(instance -> instance.trunkHeight))
			.and(IntProvider.codec(1, 10).fieldOf("arms").forGetter(instance -> instance.arms))
			.and(FloatProvider.CODEC.fieldOf("rotations").forGetter(instance -> instance.rotations))
			.and(FloatProvider.codec(0, Mth.TWO_PI).fieldOf("start_angle").forGetter(instance -> instance.initialAngle))
			.and(FloatProvider.CODEC.fieldOf("pointiness").forGetter(instance -> instance.initialAngle))
			.apply(builder, MoonfirFoliagePlacer::new)
	);

	private final IntProvider trunkHeight;
	private final IntProvider arms;
	private final FloatProvider rotations;
	private final FloatProvider initialAngle;
	private final FloatProvider pointiness;

	public MoonfirFoliagePlacer(
		IntProvider radius,
		IntProvider offset,
		IntProvider trunkHeight,
		IntProvider arms,
		FloatProvider rotations,
		FloatProvider initialAngle,
		FloatProvider pointiness
	) {
		super(radius, offset);
		this.trunkHeight = trunkHeight;
		this.arms = arms;
		this.rotations = rotations;
		this.initialAngle = initialAngle;
		this.pointiness = pointiness;
	}

	protected FoliagePlacerType<MoonfirFoliagePlacer> type() {
		return ReduxFoliagePlacers.MOONFIR.get();
	}

	@Override
	public int foliageHeight(RandomSource rand, int i, TreeConfiguration cfg) {
		return this.trunkHeight.sample(rand) + 1;
	}
	
	@Override
	protected boolean shouldSkipLocation(RandomSource random, int localX, int localY, int localZ, int range, boolean large) {
		return false;
	}
	
	@Override
	protected void createFoliage(
		LevelSimulatedReader reader,
		FoliageSetter setter,
		RandomSource rand,
		TreeConfiguration cfg,
		int maxHeight,
		FoliageAttachment attachment,
		int height,
		int radius,
		int offset
	) {
		var arms = this.arms.sample(rand);
		var rots = this.rotations.sample(rand);
		var start = this.initialAngle.sample(rand);
		var pointiness = this.pointiness.sample(rand);
		
		var origin = attachment.pos().above(offset + 1);

		for (var y = 0; y < height; y++)
			for (var x = -radius; x <= radius; x++)
				for (var z = -radius; z <= radius; z++)
					if (testByHeight(x, y, z, start, rots, arms, height, radius, pointiness)) {
						var pos = origin.offset(x, y, z);
						setter.set(pos, cfg.foliageProvider.getState(rand, pos));
					}
	}
	
	// Ensure parameters are consistent for each block
	boolean testByHeight(
		int x,
		int y,
		int z,
		float startAngle,
		float totalRotations,
		int spiralArms,
		int totalHeight,
		float maxRad,
		float pointiness
	) {
		var rSqr = x*x + z*z;
		var theta = (float) Mth.atan2(y, x);
		
		var perc = y / totalHeight;
		
		var currRad = maxRad * perc;
		
		var sinInput = spiralArms * (theta + startAngle + totalRotations * Mth.TWO_PI * perc);
		
		var unscaledBound = currRad * Mth.sin(sinInput) + 10 * currRad / pointiness;
		var bound = unscaledBound / (1 + 10 / pointiness);
		
		return rSqr <= bound*bound;
	}

}
