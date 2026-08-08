package net.zepalesque.redux.mixin.mixins.common.world;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.SurfaceRules;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(targets = "net.minecraft.world.level.levelgen.SurfaceRules$Context$SteepMaterialCondition")
public abstract class SteepConditionMixin extends SurfaceRules.LazyXZCondition {
	private SteepConditionMixin() {
		super(null);
	}

	@WrapMethod(method = "compute")
	private boolean redux$compute(Operation<Boolean> og) {
		var chunkaccess = this.context.chunk;

		int xInChunk = this.context.blockX & 15;
		int zInChunk = this.context.blockZ & 15;

		int zDec = Math.max(zInChunk - 1, 0);
		int zInc = Math.min(zInChunk + 1, 15);

		int zDecHeight = chunkaccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, xInChunk, zDec);
		int zIncHeight = chunkaccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, xInChunk, zInc);

		if (zDecHeight >= zIncHeight + 4 || zIncHeight >= zDecHeight + 4) return true;
		
		int xDec = Math.max(xInChunk - 1, 0);
		int xInc = Math.min(xInChunk + 1, 15);

		int xDecHeight = chunkaccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, xDec, zInChunk);
		int xIncHeight = chunkaccess.getHeight(Heightmap.Types.WORLD_SURFACE_WG, xInc, zInChunk);

		return xDecHeight >= xIncHeight + 4 || xIncHeight >= xDecHeight + 4;
	}
}
