package net.zepalesque.redux.advancement.trigger;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.zepalesque.redux.Redux;

/**
 * Criterion trigger used for checking an item infused with an Ambrosium Shard.
 */
// TODO: Rename to StackingRecipeTrigger and add a parameter for the recipe type
public class InfuseItemTrigger extends SimpleCriterionTrigger<InfuseItemTrigger.Instance> {
    private static final ResourceLocation ID = new ResourceLocation(Redux.MODID, "infuse_with_ambrosium");
    public static final InfuseItemTrigger INSTANCE = new InfuseItemTrigger();

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public InfuseItemTrigger.Instance createInstance(JsonObject json, ContextAwarePredicate entity, DeserializationContext context) {
        ItemPredicate ingredient = json.has("infused_item") ? ItemPredicate.fromJson(json.get("infused_item")) : ItemPredicate.ANY;
        ItemPredicate result = json.has("result_item") ? ItemPredicate.fromJson(json.get("result_item")) : ItemPredicate.ANY;
        return new InfuseItemTrigger.Instance(entity, ingredient, result);
    }

    public void trigger(ServerPlayer player, ItemStack ingredient, ItemStack result) {
        this.trigger(player, (instance) -> instance.test(ingredient, result));
    }

    public static class Instance extends AbstractCriterionTriggerInstance {

        private final ItemPredicate infusedItem;
        private final ItemPredicate resultItem;

        public Instance(ContextAwarePredicate entity, ItemPredicate infusedItem, ItemPredicate resultItem) {
            super(InfuseItemTrigger.ID, entity);
            this.infusedItem = infusedItem;
            this.resultItem = resultItem;
        }

        public static InfuseItemTrigger.Instance forIngredient(ItemPredicate item) {
            return new InfuseItemTrigger.Instance(ContextAwarePredicate.ANY, item, ItemPredicate.ANY);
        }

        public static InfuseItemTrigger.Instance forIngredient(ItemLike item) {
            return forIngredient(ItemPredicate.Builder.item().of(item).build());
        }

        public static InfuseItemTrigger.Instance forResult(ItemPredicate item) {
            return new InfuseItemTrigger.Instance(ContextAwarePredicate.ANY, ItemPredicate.ANY, item);
        }

        public static InfuseItemTrigger.Instance forResult(ItemLike item) {
            return forResult(ItemPredicate.Builder.item().of(item).build());
        }
        public static InfuseItemTrigger.Instance forConversion(ItemPredicate ingredient, ItemPredicate result) {
            return new InfuseItemTrigger.Instance(ContextAwarePredicate.ANY, ingredient, result);
        }

        public static InfuseItemTrigger.Instance forConversion(ItemLike ingredient, ItemLike result) {
            return forConversion(ItemPredicate.Builder.item().of(ingredient).build(), ItemPredicate.Builder.item().of(result).build());
        }

        public static InfuseItemTrigger.Instance forAny() {
            return forIngredient(ItemPredicate.ANY);
        }

        public boolean test(ItemStack ingredient, ItemStack result) {
            return this.infusedItem.matches(ingredient) && this.resultItem.matches(result);
        }

        @Override
        public JsonObject serializeToJson(SerializationContext context) {
            JsonObject jsonObject = super.serializeToJson(context);
            if (this.infusedItem != null && this.infusedItem != ItemPredicate.ANY)
            {
                jsonObject.add("infused_item", this.infusedItem.serializeToJson());
            }
            if (this.resultItem != null && this.resultItem != ItemPredicate.ANY)
            {
                jsonObject.add("result_item", this.resultItem.serializeToJson());
            }
            return jsonObject;
        }
    }
}
