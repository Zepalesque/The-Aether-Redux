package net.zepalesque.redux.mixin;

import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import org.joml.Vector3f;

public final class ReduxCloudMath {
	public static final float DIRECTION_MAX_Y = 0.27f;
	public static final float TILT_RADIANS = 0.3f;
	public static final float DIRECTION_DISPLACEMENT_AMOUNT = 0.5f;
	public static final float RADIUS_XZ_MIN = 1.5f;
	public static final float RADIUS_XZ_MAX = 2.7f;
	public static final float RADIUS_Y_MIN = 1.1f;
	public static final float RADIUS_Y_MAX = 1.5f;
	public static final float Y_FLATTENING_CUTOFF_RATIO = 0.9f;
	
	// me omw to reverse-engineer ts
	public static Vector3f sampleDirection(RandomSource random, float yRange, Vector3f destination) {
		// ok so this is a random angle ig
		var thetaXZ = random.nextFloat() * Mth.TWO_PI;
		// another random angle within a given range
		var sinThetaY = Mth.randomBetween(random, -yRange, yRange);
		// and the cosine, sqrt(1 - sin^2)
		var cosThetaY = Mth.sqrt(1.0f - sinThetaY * sinThetaY);
		//OH okay so its pointing at a random yaw and a narrow range for the pitch? coole :3
		destination.set(
			Mth.sin(thetaXZ) * cosThetaY,
			sinThetaY,
			Mth.cos(thetaXZ) * cosThetaY
		);
		return destination;
	}
	
}
