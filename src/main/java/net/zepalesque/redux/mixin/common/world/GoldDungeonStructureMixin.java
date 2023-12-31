package net.zepalesque.redux.mixin.common.world;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.world.structure.GoldDungeonStructure;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GoldDungeonStructure.class)
public class GoldDungeonStructureMixin {

    @Redirect(method = "placeGoldenOaks", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
    private static BlockState placeGoldenOaks(Block instance)
    {
        return instance == Blocks.DANDELION ? AetherBlocks.PURPLE_FLOWER.get().defaultBlockState() : AetherBlocks.WHITE_FLOWER.get().defaultBlockState();
    }
}
