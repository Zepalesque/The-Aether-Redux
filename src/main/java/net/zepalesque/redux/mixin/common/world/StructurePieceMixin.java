package net.zepalesque.redux.mixin.common.world;

import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.zepalesque.redux.util.ReduxMixinHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(StructurePiece.class)
public abstract class StructurePieceMixin {
    @ModifyVariable(
            method = "createChest(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/util/RandomSource;Lnet/minecraft/core/BlockPos;Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/world/level/block/state/BlockState;)Z",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/ServerLevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", shift = At.Shift.BY, by = -2),
            argsOnly = true
    )
    protected BlockState createChest(BlockState state, ServerLevelAccessor accessor) {
        StructurePiece structurePiece = (StructurePiece) (Object) this;
        return ReduxMixinHooks.replaceChest(state, structurePiece.getType());
    }

    @ModifyVariable(
            method = "placeBlock(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/block/state/BlockState;IIILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/WorldGenLevel;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", shift = At.Shift.BY, by = -2),
            argsOnly = true
    )
    protected BlockState placeBlock(BlockState state, WorldGenLevel level) {
        StructurePiece structurePiece = (StructurePiece) (Object) this;
        return ReduxMixinHooks.replaceChest(state, structurePiece.getType());
    }
}