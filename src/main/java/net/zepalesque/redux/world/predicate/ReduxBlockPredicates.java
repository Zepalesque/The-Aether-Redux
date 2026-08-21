package net.zepalesque.redux.world.predicate;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicateType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxBlockPredicates {
	public static final DeferredRegister<BlockPredicateType<?>>
		BLOCK_PREDICATES = Redux.reg(BuiltInRegistries.BLOCK_PREDICATE_TYPE);

	public static final DeferredHolder<BlockPredicateType<?>, BlockPredicateType<ColdPredicate>>
		COLD = BLOCK_PREDICATES.register(
			"cold",
			// The Java Programming Language™
			() -> () -> ColdPredicate.CODEC
		);
}
