package net.zepalesque.redux.extstate;

import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.unity.block.UnityBlocks;
import net.zepalesque.unity.data.resource.builders.base.BaseFeatureBuilders;
import net.zepalesque.zenith.api.extstate.ExtendableStateList;
import net.zepalesque.zenith.core.Zenith;

public class ReduxStateLists {

    public static final DeferredRegister<ExtendableStateList> STATE_LISTS = Redux.reg(
        Zenith.Keys.EXTENDABLE_STATE_LIST
    );

    public static final DeferredHolder<ExtendableStateList, ExtendableStateList> BLEAKMOSS = STATE_LISTS.register(
        "bleakmoss",
            () -> new ExtendableStateList(250, 150, SimpleWeightedRandomList.<BlockState>builder()
                .add(BaseFeatureBuilders.drops(UnityBlocks.SHORT_AETHER_GRASS), 150)
                .add(BaseFeatureBuilders.drops(ReduxBlocks.BLEAKMOSS_CARPET), 75)
                .add(BaseFeatureBuilders.drops(ReduxBlocks.ECHYSIA), 21)
                .build()
            )
    );
    public static final DeferredHolder<ExtendableStateList, ExtendableStateList> GILDENMOSS = STATE_LISTS.register(
        "gildenmoss",
            () -> new ExtendableStateList(250, 150, SimpleWeightedRandomList.<BlockState>builder()
                .add(BaseFeatureBuilders.drops(UnityBlocks.SHORT_AETHER_GRASS), 150)
                .add(BaseFeatureBuilders.drops(ReduxBlocks.GILDENMOSS_CARPET), 75)
                .add(BaseFeatureBuilders.drops(ReduxBlocks.ECHYSIA), 21)
                .build()
            )
    );
}
