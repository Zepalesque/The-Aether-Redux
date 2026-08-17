package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.miscellaneous.FacingPillarBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.client.particle.ReduxParticles;

public class CloudcapGillBlock extends FacingPillarBlock {
	public CloudcapGillBlock(Properties properties) {
		super(properties);
		this.registerDefaultState(
			this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false)
		);
	}
	
	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
	}
	
	public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
		var x = pos.getX();
		var y = pos.getY();
		var z = pos.getZ();
		if (level.random.nextInt(16) == 0 && level.isStateAtPosition(pos.below(), bs -> !bs.canOcclude() || !bs.isFaceSturdy(level, pos, Direction.UP))) {
			
			var sporeX = (double) x + random.nextDouble();
			var sporeY = (double) y + 0.7D;
			var sporeZ = (double) z + random.nextDouble();
			level.addParticle(ReduxParticles.FALLING_CLOUDCAP_SPORE.get(), sporeX, sporeY, sporeZ, 0.0D, 0.0D, 0.0D);
		}
		var mutpos = new BlockPos.MutableBlockPos();
		
		for(int l = 0; l <  4; ++l) {
			mutpos.set(x + Mth.nextInt(random, -10, 10), y - random.nextInt(10), z + Mth.nextInt(random, -10, 10));
			var airstate = level.getBlockState(mutpos);
			if (!airstate.isCollisionShapeFullBlock(level, mutpos)) {
				level.addParticle(ReduxParticles.CLOUDCAP_AIR_SPORE.get(), (double)mutpos.getX() + random.nextDouble(), (double)mutpos.getY() + random.nextDouble(), (double)mutpos.getZ() + random.nextDouble(), 0.0D, 0.0D, 0.0D);
			}
		}
		
	}
}
