package net.zepalesque.redux.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zepalesque.redux.Redux;
import org.jetbrains.annotations.NotNull;

public class FallFromAetherTrigger extends SimpleCriterionTrigger<FallFromAetherTrigger.TriggerInstance> {
   static final ResourceLocation ID = Redux.locate("fall_from_aether");
   public static final FallFromAetherTrigger INSTANCE = new FallFromAetherTrigger();

   public ResourceLocation getId() {
      return ID;
   }

   public FallFromAetherTrigger.TriggerInstance createInstance(JsonObject pJson, ContextAwarePredicate pEntityPredicate, DeserializationContext pConditionsParser) {
       return new FallFromAetherTrigger.TriggerInstance(pEntityPredicate);
   }

   public void trigger(ServerPlayer pPlayer) {
      this.trigger(pPlayer, (trigger) -> true);
   }


   public static class TriggerInstance extends AbstractCriterionTriggerInstance {


      public TriggerInstance(ContextAwarePredicate pPlayer) {
         super(FallFromAetherTrigger.ID, pPlayer);

      }

      public static FallFromAetherTrigger.TriggerInstance fall() {
         return new FallFromAetherTrigger.TriggerInstance(ContextAwarePredicate.ANY);
      }

      @NotNull
      public JsonObject serializeToJson(SerializationContext pConditions) {
         return super.serializeToJson(pConditions);
      }
   }
}