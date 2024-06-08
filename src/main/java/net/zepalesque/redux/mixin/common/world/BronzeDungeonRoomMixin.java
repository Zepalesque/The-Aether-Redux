package net.zepalesque.redux.mixin.common.world;

import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.world.structurepiece.bronzedungeon.BronzeDungeonRoom;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.zepalesque.redux.world.ReduxDungeonProcessors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BronzeDungeonRoom.class)
public class BronzeDungeonRoomMixin {

    @Inject(method = "handleDataMarker", at = @At(value = "TAIL"))
    protected void processSpawners(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box, CallbackInfo ci) {
        if (name.equals("Spawner")) {
            if (random.nextInt(3) == 0) {
                level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);
                if (level.getBlockEntity(pos) instanceof SpawnerBlockEntity spawner) {

                    spawner.getSpawner().setEntityId(AetherEntityTypes.SENTRY.get());
                }
            }
        }
    }

    @WrapOperation(method = "makeSettings", at = @At(value = "NEW", target = "()Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;"))
    private static StructurePlaceSettings redux$makeSettings(Operation<StructurePlaceSettings> original) {
        return original.call().addProcessor(ReduxDungeonProcessors.BRONZE_BLOCKS).addProcessor(ReduxDungeonProcessors.BRONZE_TRAPS);
    }
}
