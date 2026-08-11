package net.zepalesque.redux.mixin.mixins.common.world.structure;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.structure.SilverDungeonStructure;
import com.aetherteam.aether.world.structurepiece.LargeAercloudChunk;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.zepalesque.redux.mixin.ReduxCloudMath;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SilverDungeonStructure.class)
public class SilverDungeonStructureMixin {
	
	@Unique private static final int BOUNDS = 10;
	
	@Inject(
		method = "buildCloudBed",
		at = @At(value = "INVOKE", target = "Ljava/util/HashMap;<init>()V"),
		cancellable = true,
		locals = LocalCapture.CAPTURE_FAILSOFT
	)
	private void redux$buildCloudBed(
		StructurePiecesBuilder builder,
		RandomSource random,
		BlockPos origin,
		Direction direction,
		CallbackInfo ci,
		int xBounds,
		int zBounds,
		BlockPos.MutableBlockPos offset) {
		
		Map<ChunkPos, Set<BlockPos>> chunks = new HashMap<>();
		Set<BlockPos> positions = new HashSet<>();

		for (int tries = 0; tries < 100; tries++) {
			Vector3f dir = ReduxCloudMath.sampleDirection(random, ReduxCloudMath.DIRECTION_MAX_Y, new Vector3f());
			Vector3f binormal = new Vector3f(0, random.nextBoolean() ? -1 : 1, 0).cross(dir).normalize();
			Vector3f normal = binormal.cross(dir, new Vector3f());
			Vector3f tiltedNormal = new Vector3f(normal).mul(Mth.cos(ReduxCloudMath.TILT_RADIANS)).add(new Vector3f(binormal).mul(Mth.sin(ReduxCloudMath.TILT_RADIANS)));
			Vector3f tiltedBinormal = dir.cross(tiltedNormal, new Vector3f());
			
			// TODO (was in original, see link in class javadoc):  the purpose of the counterpart to this in the original code seems to have been to keep the cloud in the feature gen range. Try a more directed approach.
			Vector3f blockPosRelative = new Vector3f(dir).negate().mul(BOUNDS / 2f);
			
			Vector3f iterationDisplacement = new Vector3f();
			Vector3f deltaFromCurrentCenter = new Vector3f();
			Vector3f scaledDirection = new Vector3f();
			Vector3f scaledTiltedNormal = new Vector3f();
			Vector3f scaledTiltedBinormal = new Vector3f();
			
			int x = offset.getX() + random.nextInt(xBounds);
			int y = offset.getY();
			int z = offset.getZ() + random.nextInt(zBounds);
			for (int n = 0; n < BOUNDS; n++) {
				ReduxCloudMath.sampleDirection(random, 1.0f, iterationDisplacement);
				iterationDisplacement.mul(ReduxCloudMath.DIRECTION_DISPLACEMENT_AMOUNT);
				iterationDisplacement.add(dir);
				
				blockPosRelative.add(iterationDisplacement);
				
				float radiusXZ = Mth.randomBetween(random, ReduxCloudMath.RADIUS_XZ_MIN, ReduxCloudMath.RADIUS_XZ_MAX);
				float radiusY = Mth.randomBetween(random, ReduxCloudMath.RADIUS_Y_MIN, ReduxCloudMath.RADIUS_Y_MAX);
				float rangeX = Mth.sqrt(Mth.square(dir.x() * radiusXZ) + Mth.square(tiltedNormal.x() * radiusY) + Mth.square(tiltedBinormal.x() * radiusXZ));
				float rangeY = Mth.sqrt(Mth.square(dir.y() * radiusXZ) + Mth.square(tiltedNormal.y() * radiusY) + Mth.square(tiltedBinormal.y() * radiusXZ));
				float rangeZ = Mth.sqrt(Mth.square(dir.z() * radiusXZ) + Mth.square(tiltedNormal.z() * radiusY) + Mth.square(tiltedBinormal.z() * radiusXZ));
				float rangeYWithCutoff = rangeY * ReduxCloudMath.Y_FLATTENING_CUTOFF_RATIO;
				dir.mul(1.0f / radiusXZ, scaledDirection);
				tiltedNormal.mul(1.0f / radiusY, scaledTiltedNormal);
				tiltedBinormal.mul(1.0f / radiusXZ, scaledTiltedBinormal);
				for (int dz = Mth.ceil(blockPosRelative.z() - rangeZ); dz <= Mth.floor(blockPosRelative.z() + rangeZ); dz++) {
					for (int dy = Mth.ceil(blockPosRelative.y() - rangeY); dy <= Mth.floor(blockPosRelative.y() + rangeYWithCutoff); dy++) {
						for (int dx = Mth.ceil(blockPosRelative.x() - rangeX); dx <= Mth.floor(blockPosRelative.x() + rangeX); dx++) {
							var newPosition = new BlockPos(x + dx, y + dy, z + dz);
							
							deltaFromCurrentCenter.set(dx - blockPosRelative.x(), dy - blockPosRelative.y(), dz - blockPosRelative.z());
							if (Mth.square(deltaFromCurrentCenter.dot(scaledDirection)) + Mth.square(deltaFromCurrentCenter.dot(scaledTiltedNormal)) + Mth.square(deltaFromCurrentCenter.dot(scaledTiltedBinormal)) < 1.0f) {
								positions.add(newPosition);
								chunks.computeIfAbsent(new ChunkPos(newPosition), pos -> new HashSet<>());
							}
						}
					}
				}
			}
		}
		
		chunks.forEach((chunkPos, blockPosSet) -> {
			blockPosSet.addAll(positions.stream().filter(pos -> new ChunkPos(pos).equals(chunkPos)).toList());
			builder.addPiece(new LargeAercloudChunk(blockPosSet,
				BlockStateProvider.simple(AetherBlocks.COLD_AERCLOUD.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, true)),
				new BoundingBox(chunkPos.getMinBlockX(), origin.getY(), chunkPos.getMinBlockZ(), chunkPos.getMaxBlockX(), origin.getY(), chunkPos.getMaxBlockZ()),
				direction));
		});
		
		ci.cancel();
	}
}
