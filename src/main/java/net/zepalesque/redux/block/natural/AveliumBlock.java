package net.zepalesque.redux.block.natural;

import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.block.natural.AetherGrassBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public class AveliumBlock extends AetherGrassBlock {
    
    public AveliumBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void performBonemeal(ServerLevel lvl, RandomSource rand, BlockPos pos, BlockState state) {
	    var above = pos.above();
        // TODO: is this correct?
	    var grass = AetherBlocks.AETHER_GRASS_BLOCK.get();

        loop: for (var i = 0; i < 128; ++i) {
	        var testPos = above;

            for (var j = 0; j < i / 16; ++j) {
                testPos = testPos.offset(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
                if (!lvl.getBlockState(testPos.below()).is(this) || lvl.getBlockState(testPos).isCollisionShapeFullBlock(lvl, testPos))
	                continue loop;
            }
	        
	        var testState = lvl.getBlockState(testPos);
            if (testState.is(grass) && rand.nextInt(10) == 0)
	            ((BonemealableBlock) grass).performBonemeal(lvl, rand, testPos, testState);
			// i hope adding an else here was ok
			else if (testState.isAir()) {
                Holder<PlacedFeature> featureHolder;
	            if (rand.nextInt(8) == 0) {
		            var list = lvl.getBiome(testPos).value().getGenerationSettings().getFlowerFeatures();
                    if (list.isEmpty()) continue;
                    featureHolder = ((RandomPatchConfiguration) list.get(rand.nextInt(list.size())).config()).feature();
                    featureHolder.value().place(lvl, lvl.getChunkSource().getGenerator(), rand, testPos);
                } else if (lvl.ensureCanWrite(testPos))
                    // TODO
//		            lvl.setBlock(
//			            testPos,
//			            rand.nextFloat() < 0.6F
//				            ? ReduxBlocks.AVELIUM_SPROUTS.get().defaultBlockState()
//				            : ReduxBlocks.AVELIUM_ROOTS.get().defaultBlockState(),
//			            3
//		            );
                    return; // remove after done
            }
        }
    }
}
