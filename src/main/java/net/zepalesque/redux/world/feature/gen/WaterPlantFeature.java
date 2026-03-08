package net.zepalesque.redux.world.feature.gen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class WaterPlantFeature extends Feature<WaterPlantFeature.Config> {
	public WaterPlantFeature(Codec<Config> codec) {
		super(codec);
	}
	
	@Override
	public boolean place(FeaturePlaceContext<WaterPlantFeature.Config> ctx) {
		var success = false;
		var rand = ctx.random();
		var lvl = ctx.level();
		var origin = ctx.origin();
		var xOffs = rand.nextInt(8) - rand.nextInt(8);
		var zOffs = rand.nextInt(8) - rand.nextInt(8);
		var y = lvl.getHeight(Heightmap.Types.OCEAN_FLOOR, origin.getX() + xOffs, origin.getZ() + zOffs);
		var pos = new BlockPos(origin.getX() + xOffs, y, origin.getZ() + zOffs);
		if (lvl.getBlockState(pos).is(Blocks.WATER)) {
			var state = ctx.config().block().getState(rand, pos);
			if (state.canSurvive(lvl, pos)) {
				lvl.setBlock(pos, state, 2);
				success = true;
			}
		}
		
		return success;
	}
	public record Config(BlockStateProvider block) implements FeatureConfiguration {
		public static final Codec<Config> CODEC = BlockStateProvider.CODEC.fieldOf("block").xmap(Config::new, Config::block).codec();
			
			/*RecordCodecBuilder.create(
			builder -> builder.group(
				BlockStateProvider.CODEC.fieldOf("block").forGetter(Config::block)
			).apply(builder, Config::new)
		);*/
	}
}
