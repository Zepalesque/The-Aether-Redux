package net.zepalesque.redux.blockset.leaf;

import net.zepalesque.redux.Redux;
import net.zepalesque.redux.blockset.leaf.type.SnowableLeafSet;
import net.zepalesque.zenith.api.blockset.type.AbstractFlowerSet;
import net.zepalesque.zenith.api.blockset.type.AbstractLeafSet;

public class ReduxLeafSets {
	
//	public static final SnowableLeafSet
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static <T extends AbstractLeafSet<T>> T register(T set) {
		Redux.BLOCK_SETS.add(set);
		return set;
	}
}
