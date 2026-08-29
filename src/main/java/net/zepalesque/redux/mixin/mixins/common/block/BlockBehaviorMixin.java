package net.zepalesque.redux.mixin.mixins.common.block;

import com.aetherteam.aether.block.utility.AltarBlock;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BlockBehaviorMixin {
	// Maybe PR a neoforge event? idk
	@SuppressWarnings("CancellableInjectionUsage")
	@Inject(method = "isPathfindable", at = @At("HEAD"), cancellable = true)
	public void redux$pathFindable(
		BlockState state,
		PathComputationType pathComputationType,
		CallbackInfoReturnable<Boolean> cir
	) {}

	@WrapMethod(method = "getShape")
	protected VoxelShape redux$getShape(
		BlockState state,
		BlockGetter level,
		BlockPos pos,
		CollisionContext context,
		Operation<VoxelShape> og
	) {
		// Cursed workaround to Minecraft's weird rendering code,
		// so that the block doesn't cull surrounding blocks
		return (Object)this instanceof AltarBlock
			? Block.box(0.01, 0.01, 0.01, 15.99, 15.99, 15.99)
			: og.call(state, level, pos, context);
	}
}
