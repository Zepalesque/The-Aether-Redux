package net.zepalesque.redux.advancement.trigger;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.NotNull;

public class AprilReduxSpecificTrigger extends SimpleCriterionTrigger<AprilReduxSpecificTrigger.TriggerInstance> {
   static final ResourceLocation ID = Redux.locate("april_redux_trigger");
   public static final AprilReduxSpecificTrigger INSTANCE = new AprilReduxSpecificTrigger();

   public ResourceLocation getId() {
      return ID;
   }

   public AprilReduxSpecificTrigger.TriggerInstance createInstance(JsonObject json, ContextAwarePredicate pEntityPredicate, DeserializationContext pConditionsParser) {
      String trigger = json.has("type") ? json.get("type").getAsString() : "none";
      return new AprilReduxSpecificTrigger.TriggerInstance(pEntityPredicate, trigger);
   }

   public void trigger(ServerPlayer pPlayer, String id) {
      this.trigger(pPlayer, (trigger) -> trigger.test(id));
   }


   public static class TriggerInstance extends AbstractCriterionTriggerInstance {

      private final String type;

      public TriggerInstance(ContextAwarePredicate pPlayer, String id) {
         super(AprilReduxSpecificTrigger.ID, pPlayer);
         this.type = id;
      }

      public boolean test(String id) {
         return this.type.equals(id);
      }

      public static AprilReduxSpecificTrigger.TriggerInstance type(String id) {
         return new AprilReduxSpecificTrigger.TriggerInstance(ContextAwarePredicate.ANY, id);
      }

      @NotNull
      public JsonObject serializeToJson(SerializationContext context) {
         JsonObject jsonObject = super.serializeToJson(context);
         jsonObject.add("type", new JsonPrimitive(this.type));
         return super.serializeToJson(context);
      }
   }
}