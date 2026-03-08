package net.zepalesque.redux.mixin.mixins.common.item;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.item.property.ReduxFoods;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import javax.annotation.Nullable;

@Mixin(BoneMealItem.class)
public class BonemealItemMixin {
	@WrapOperation(method = "growWaterPlant", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
	private static BlockState redux$growWaterPlant(Block instance, Operation<BlockState> original, ItemStack stack, Level level, BlockPos pos, @Nullable Direction clickedSide) {
		if (level.getBiome(pos).is(ReduxTags.Biomes.BLOOMTAIL_BONEMEAL))
			return ReduxBlocks.BLOOMTAIL.get().defaultBlockState();
		else return original.call(instance);
	}
}
