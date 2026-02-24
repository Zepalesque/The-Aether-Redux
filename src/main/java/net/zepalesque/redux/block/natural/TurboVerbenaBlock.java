package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class TurboVerbenaBlock extends FlowerBlock {
	public TurboVerbenaBlock(Holder<MobEffect> effect, float seconds, BlockBehaviour.Properties properties) {
		super(effect, seconds, properties);
	}

	@Override
	protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
		return level.getBlockState(pos.below()).is(AetherBlocks.QUICKSOIL);
	}
}
