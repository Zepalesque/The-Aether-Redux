package net.zepalesque.redux.data.resource.registries;

import static net.zepalesque.unity.data.resource.builders.base.BaseFeatureBuilders.*;

import java.util.Optional;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.resource.builders.ReduxStateListEntryBuilders;
import net.zepalesque.zenith.api.extstate.ExtendableStateList;

public class ReduxStateListEntries extends ReduxStateListEntryBuilders {
	
	public static final ResourceKey<ExtendableStateList.Entry> ECHYSIA = create("echysia");
	
	public static void bootstrap(BootstrapContext<ExtendableStateList.Entry> context) {
		context.register(ECHYSIA, new ExtendableStateList.Entry(
			Optional.empty(),
			Optional.of(
				SimpleWeightedRandomList.<BlockState>builder()
					.add(drops(ReduxBlocks.ECHYSIA), 21)
					.build()
			)
		));
	}
}
