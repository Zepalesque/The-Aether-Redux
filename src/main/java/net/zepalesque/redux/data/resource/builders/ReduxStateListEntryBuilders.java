package net.zepalesque.redux.data.resource.builders;

import net.minecraft.resources.ResourceKey;
import net.zepalesque.redux.Redux;
import net.zepalesque.zenith.api.extendablestate.ExtendableStateList;
import net.zepalesque.zenith.core.Zenith;

public class ReduxStateListEntryBuilders {
	protected static ResourceKey<ExtendableStateList.Entry> create(String pName) {
		return ResourceKey.create(Zenith.Keys.EXTENDABLE_STATE_LIST_ENTRY, Redux.loc(pName));
	}
}
