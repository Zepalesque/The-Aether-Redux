package net.zepalesque.redux.mixin.client.block;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import teamrazor.deepaether.event.DAClientModBusEvents;

@Mixin(DAClientModBusEvents.class)
public class DeepAetherColorMixin {
    
/*    @WrapWithCondition(method = "registerBlockColors", at = {
        @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/RegisterColorHandlersEvent$Block;register(Lnet/minecraft/client/color/block/BlockColor;[Lnet/minecraft/world/level/block/Block;)V", ordinal = 0),
        @At(value = "INVOKE", target = "Lnet/minecraftforge/client/event/RegisterColorHandlersEvent$Block;register(Lnet/minecraft/client/color/block/BlockColor;[Lnet/minecraft/world/level/block/Block;)V", ordinal = 2)
    }, remap = false)
    private static boolean stupidJavaBlackMagic(RegisterColorHandlersEvent.Block instance, BlockColor blockColor, Block[] blocks) {
        // Cancel deep aether's tall glowing grass stuff
        return false;
    }*/
}
