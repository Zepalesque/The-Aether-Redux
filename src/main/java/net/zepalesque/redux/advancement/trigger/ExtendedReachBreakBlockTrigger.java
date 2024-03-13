package net.zepalesque.redux.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.NotNull;

public class ExtendedReachBreakBlockTrigger extends SimpleCriterionTrigger<ExtendedReachBreakBlockTrigger.TriggerInstance> {
   static final ResourceLocation ID = Redux.locate("extended_reach_break_block");
   public static final ExtendedReachBreakBlockTrigger INSTANCE = new ExtendedReachBreakBlockTrigger();

   public ResourceLocation getId() {
      return ID;
   }

   public ExtendedReachBreakBlockTrigger.TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pEntityPredicate, DeserializationContext pConditionsParser) {
       return new ExtendedReachBreakBlockTrigger.TriggerInstance(pEntityPredicate);
   }

   public void trigger(ServerPlayer pPlayer) {
      this.trigger(pPlayer, (trigger) -> true);
   }


   public static class TriggerInstance extends AbstractCriterionTriggerInstance {


      public TriggerInstance(EntityPredicate.Composite pPlayer) {
         super(ExtendedReachBreakBlockTrigger.ID, pPlayer);

      }

      public static ExtendedReachBreakBlockTrigger.TriggerInstance mineBlock() {
         return new ExtendedReachBreakBlockTrigger.TriggerInstance(EntityPredicate.Composite.ANY);
      }

      @NotNull
      public JsonObject serializeToJson(SerializationContext pConditions) {
         return super.serializeToJson(pConditions);
      }
   }
}