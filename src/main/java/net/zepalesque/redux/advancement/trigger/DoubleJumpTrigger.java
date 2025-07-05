package net.zepalesque.redux.advancement.trigger;

import com.aetherteam.aether.item.EquipmentUtil;
import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.item.ReduxItems;
import org.jetbrains.annotations.NotNull;

public class DoubleJumpTrigger extends SimpleCriterionTrigger<DoubleJumpTrigger.TriggerInstance> {
   static final ResourceLocation ID = Redux.locate("double_jump");
   public static final DoubleJumpTrigger INSTANCE = new DoubleJumpTrigger();

   public ResourceLocation getId() {
      return ID;
   }

   public DoubleJumpTrigger.TriggerInstance createInstance(JsonObject pJson, EntityPredicate.Composite pEntityPredicate, DeserializationContext pConditionsParser) {
       return new DoubleJumpTrigger.TriggerInstance(pEntityPredicate);
   }

   public void trigger(ServerPlayer pPlayer) {
      this.trigger(pPlayer, (trigger) -> EquipmentUtil.hasCurio(pPlayer, ReduxItems.AIRBOUND_CAPE.get()));
   }


   public static class TriggerInstance extends AbstractCriterionTriggerInstance {


      public TriggerInstance(EntityPredicate.Composite pPlayer) {
         super(DoubleJumpTrigger.ID, pPlayer);

      }

      public static DoubleJumpTrigger.TriggerInstance jump() {
         return new DoubleJumpTrigger.TriggerInstance(EntityPredicate.Composite.ANY);
      }

      @NotNull
      public JsonObject serializeToJson(SerializationContext pConditions) {
         return super.serializeToJson(pConditions);
      }
   }
}