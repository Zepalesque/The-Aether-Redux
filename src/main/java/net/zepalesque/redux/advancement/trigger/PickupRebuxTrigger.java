package net.zepalesque.redux.advancement.trigger;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.NotNull;

public class PickupRebuxTrigger extends SimpleCriterionTrigger<PickupRebuxTrigger.TriggerInstance> {
   static final ResourceLocation ID = Redux.locate("pickup_rebux");
   public static final PickupRebuxTrigger INSTANCE = new PickupRebuxTrigger();

   public ResourceLocation getId() {
      return ID;
   }

   public PickupRebuxTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate pEntityPredicate, DeserializationContext pConditionsParser) {
      int count = json.has("count") ? json.get("count").getAsInt() : 1;
      return new PickupRebuxTrigger.TriggerInstance(pEntityPredicate, count);
   }

   public void trigger(ServerPlayer pPlayer, String id) {
      this.trigger(pPlayer, (trigger) -> trigger.test(id));
   }


   public static class TriggerInstance extends AbstractCriterionTriggerInstance {

      private final int count;

      public TriggerInstance(ContextAwarePredicate pPlayer, int count) {
         super(PickupRebuxTrigger.ID, pPlayer);
         this.count = count;
      }

      public boolean test(int count) {
         return count >= this.count;
      }

      public static PickupRebuxTrigger.TriggerInstance forMin(int count) {
         return new PickupRebuxTrigger.TriggerInstance(ContextAwarePredicate.ANY, count);
      }

      @NotNull
      public JsonObject serializeToJson(SerializationContext context) {
         JsonObject jsonObject = super.serializeToJson(context);
         jsonObject.add("count", new JsonPrimitive(this.count));
         return jsonObject;
      }
   }
}