package net.zepalesque.redux.advancement;

import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;

public class ReduxAdvancementTriggers {
    public static final DeferredRegister<CriterionTrigger<?>>
        TRIGGERS = DeferredRegister.create(Registries.TRIGGER_TYPE, Redux.MODID);

    // TODO: Expand into actual system
    public static final DeferredHolder<CriterionTrigger<?>, PlayerTrigger>
        THROW_GOLD_RING_INTO_LAVA = TRIGGERS.register(
            "throw_gold_ring_into_lava",
            PlayerTrigger::new
        );

}
