package net.zepalesque.redux.advancement.trigger;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.zepalesque.redux.Redux;


public class ReduxAdvancementTriggers {
    
    public static final PlayerTrigger THROW_GOLD_RING_INTO_LAVA = new PlayerTrigger(Redux.locate("throw_gold_ring_into_lava"));

    public static void init() {
        CriteriaTriggers.register(InfuseItemTrigger.INSTANCE);
        CriteriaTriggers.register(BlockStateRecipeTrigger.INSTANCE);
        CriteriaTriggers.register(DoubleJumpTrigger.INSTANCE);
        CriteriaTriggers.register(ExtendedReachBreakBlockTrigger.INSTANCE);
        CriteriaTriggers.register(THROW_GOLD_RING_INTO_LAVA);
    }
    
    public static PlayerTrigger.TriggerInstance throwRing() {
        return new PlayerTrigger.TriggerInstance(THROW_GOLD_RING_INTO_LAVA.getId(), EntityPredicate.Composite.wrap(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.location().setDimension(Level.NETHER).build()).build()));
    }
}
