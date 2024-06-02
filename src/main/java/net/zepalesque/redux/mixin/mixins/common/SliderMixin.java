package net.zepalesque.redux.mixin.mixins.common;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slider.class)
public class SliderMixin {

    @Inject(method = "convertBlock", at = @At("TAIL"))
    protected void redux$convertBlock(BlockState state, CallbackInfoReturnable<BlockState> cir) {
        // TODO
//        if (state.is(AetherBlocks.LOCKED_CARVED_STONE.get())) {
//            return AetherBlocks.CARVED_STONE.get().defaultBlockState();
//        }
    }
}
