package net.zepalesque.redux.world.predicate;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;

public class ColdPredicate implements BlockPredicate {
	public static final MapCodec<ColdPredicate> CODEC = MapCodec.unit(ColdPredicate::new);

	@Override
	public boolean test(WorldGenLevel lvl, BlockPos pos) {
		return lvl.getBiome(pos).value().coldEnoughToSnow(pos);
	}

	@Override
	public BlockPredicateType<?> type() {
		return ReduxBlockPredicates.COLD.get();
	}
}
