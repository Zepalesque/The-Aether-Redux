package net.zepalesque.redux.advancement.trigger;

import net.minecraft.advancements.CriteriaTriggers;

public class ReduxAdvancementTriggers {

    public static void init() {
        CriteriaTriggers.register(FallFromAetherTrigger.INSTANCE);
        CriteriaTriggers.register(InfuseItemTrigger.INSTANCE);
        CriteriaTriggers.register(BlockStateRecipeTrigger.INSTANCE);
        CriteriaTriggers.register(DoubleJumpTrigger.INSTANCE);
        CriteriaTriggers.register(ExtendedReachBreakBlockTrigger.INSTANCE);
        CriteriaTriggers.register(AprilReduxSpecificTrigger.INSTANCE);
    }
}
