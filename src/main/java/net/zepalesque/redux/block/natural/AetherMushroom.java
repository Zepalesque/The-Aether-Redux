package net.zepalesque.redux.block.natural;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.event.EventHooks;
import net.zepalesque.unity.block.natural.bush.CustomBoundsBushBlock;

public class AetherMushroom extends CustomBoundsBushBlock implements BonemealableBlock {
	private final ResourceKey<ConfiguredFeature<?, ?>> feature;

	public AetherMushroom(
		VoxelShape shape,
		Properties props,
		ResourceKey<ConfiguredFeature<?, ?>> feature
	) {
		super(shape, props);
		this.feature = feature;
	}

	public boolean growMushroom(
		ServerLevel lvl,
		RandomSource rand,
		BlockPos pos,
		BlockState state
	) {
		var opt = lvl.registryAccess().registryOrThrow(Registries.CONFIGURED_FEATURE).getHolder(this.feature);
		
		var event = EventHooks.fireBlockGrowFeature(lvl, rand, pos, opt.orElse(null));
		if (event.isCanceled()) return false;
		
		var featureOpt = Optional.ofNullable(event.getFeature());
		if (featureOpt.isEmpty()) return false;

		var feature = featureOpt.get().value();
		var success = feature.place(lvl, lvl.getChunkSource().getGenerator(), rand, pos);

		if (!success) lvl.setBlock(pos, state, 3);
		return success;
	}

	@Override
	public boolean isValidBonemealTarget(LevelReader lvl, BlockPos pos, BlockState state) {
		return true;
	}

	@Override
	public boolean isBonemealSuccess(Level lvl, RandomSource rand, BlockPos pos, BlockState state) {
		return rand.nextFloat() < .4;
	}

	@Override
	public void performBonemeal(ServerLevel lvl, RandomSource rand, BlockPos pos, BlockState state) {
		this.growMushroom(lvl, rand, pos, state);
	}
}
