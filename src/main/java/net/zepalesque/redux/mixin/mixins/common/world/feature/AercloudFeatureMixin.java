package net.zepalesque.redux.mixin.mixins.common.world.feature;

import com.aetherteam.aether.world.configuration.AercloudConfiguration;
import com.aetherteam.aether.world.feature.AercloudFeature;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.zepalesque.redux.mixin.ReduxCloudMath;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * See <a href="https://github.com/KdotJPG/The-Aether/blob/feature/tuneups/src/main/java/com/aetherteam/aether/world/feature/AercloudFeature.java">github.com/KdotJPG/The-Aether/.../AercloudFeature.java</a>
 */
@Mixin(AercloudFeature.class)
public abstract class AercloudFeatureMixin extends FeatureMixin<AercloudConfiguration> {
	// TODO: Decipher how the heck this works via reverse-engineering idk
	@Inject(
		method = "place",
		at = @At("HEAD"),
		cancellable = true
	)
	private void redux$place(
		FeaturePlaceContext<AercloudConfiguration> context,
		CallbackInfoReturnable<Boolean> cir
	) {
		var level = context.level();
		var random = context.random();
		var dir = ReduxCloudMath.sampleDirection(random, ReduxCloudMath.DIRECTION_MAX_Y, new Vector3f());
		var binormal = new Vector3f(0, random.nextBoolean() ? -1 : 1, 0).cross(dir).normalize();
		var normal = binormal.cross(dir, new Vector3f());
		var tiltedNormal = new Vector3f(normal).mul(Mth.cos(ReduxCloudMath.TILT_RADIANS)).add(new Vector3f(binormal).mul(Mth.sin(ReduxCloudMath.TILT_RADIANS)));
		var tiltedBinormal = dir.cross(tiltedNormal, new Vector3f());

		var config = context.config();

		// TODO (was in original, see link in class javadoc):  the purpose of the counterpart to this in the original code seems to have been to keep the cloud in the feature gen range. Try a more directed approach.
		var blockPosRelative = new Vector3f(dir).negate().mul(config.bounds() / 2.0f);

		var blockState = config.block().getState(random, context.origin());

		var iterationDisplacement = new Vector3f();
		var deltaFromCurrentCenter = new Vector3f();
		var scaledDirection = new Vector3f();
		var scaledTiltedNormal = new Vector3f();
		var scaledTiltedBinormal = new Vector3f();

		for (int amount = 0; amount < config.bounds(); ++amount) {
			ReduxCloudMath.sampleDirection(random, 1.0f, iterationDisplacement);
			iterationDisplacement.mul(ReduxCloudMath.DIRECTION_DISPLACEMENT_AMOUNT);
			iterationDisplacement.add(dir);

			blockPosRelative.add(iterationDisplacement);

			var radiusXZ = Mth.randomBetween(random, ReduxCloudMath.RADIUS_XZ_MIN, ReduxCloudMath.RADIUS_XZ_MAX);
			var radiusY = Mth.randomBetween(random,  ReduxCloudMath.RADIUS_Y_MIN, ReduxCloudMath.RADIUS_Y_MAX);
			var rangeX = Mth.sqrt(Mth.square(dir.x() * radiusXZ) + Mth.square(tiltedNormal.x() * radiusY) + Mth.square(tiltedBinormal.x() * radiusXZ));
			var rangeY = Mth.sqrt(Mth.square(dir.y() * radiusXZ) + Mth.square(tiltedNormal.y() * radiusY) + Mth.square(tiltedBinormal.y() * radiusXZ));
			var rangeZ = Mth.sqrt(Mth.square(dir.z() * radiusXZ) + Mth.square(tiltedNormal.z() * radiusY) + Mth.square(tiltedBinormal.z() * radiusXZ));
			var rangeYWithCutoff = rangeY * ReduxCloudMath.Y_FLATTENING_CUTOFF_RATIO;
			dir.mul(1.0f / radiusXZ, scaledDirection);
			tiltedNormal.mul(1.0f / radiusY, scaledTiltedNormal);
			tiltedBinormal.mul(1.0f / radiusXZ, scaledTiltedBinormal);
			for (int dz = Mth.ceil(blockPosRelative.z() - rangeZ); dz <= Mth.floor(blockPosRelative.z() + rangeZ); dz++) {
				for (int dy = Mth.ceil(blockPosRelative.y() - rangeY); dy <= Mth.floor(blockPosRelative.y() + rangeYWithCutoff); dy++) {
					for (int dx = Mth.ceil(blockPosRelative.x() - rangeX); dx <= Mth.floor(blockPosRelative.x() + rangeX); dx++) {
						BlockPos newPosition = context.origin().offset(dx, dy, dz);

						if (level.isEmptyBlock(newPosition)) {
							deltaFromCurrentCenter.set(dx - blockPosRelative.x(), dy - blockPosRelative.y(), dz - blockPosRelative.z());
							if (Mth.square(deltaFromCurrentCenter.dot(scaledDirection)) + Mth.square(deltaFromCurrentCenter.dot(scaledTiltedNormal)) + Mth.square(deltaFromCurrentCenter.dot(scaledTiltedBinormal)) < 1.0f) {
								this.setBlock(level, newPosition, blockState);
							}
						}
					}
				}
			}
		}

		cir.setReturnValue(true);
	}

}
