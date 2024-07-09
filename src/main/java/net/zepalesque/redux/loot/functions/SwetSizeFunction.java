package net.zepalesque.redux.loot.functions;

import com.aetherteam.aether.entity.monster.Swet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.Set;

public class SwetSizeFunction extends LootItemConditionalFunction {
    private static final Builder<?> INSTANCE = simpleBuilder(SwetSizeFunction::new);

    protected SwetSizeFunction(LootItemCondition[] predicates) {
        super(predicates);
    }

    public ItemStack run(ItemStack stack, LootContext context) {
        Entity e = context.getParamOrNull(LootContextParams.THIS_ENTITY);
        if (e instanceof Swet swet) {
            int i = Mth.floor((swet.getSize() - 1) / 2F) + context.getRandom().nextInt(2);
            stack.setCount(i);
        }
        return stack;
    }

    @Override
    public LootItemFunctionType getType() {
        return ReduxLootFunctions.SWET_GEL_COUNT.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.THIS_ENTITY)    ;
    }

    public static LootItemConditionalFunction.Builder<?> instance() {
        return INSTANCE;
    }

    public static class Serializer extends LootItemConditionalFunction.Serializer<SwetSizeFunction> {
        public void serialize(JsonObject json, SwetSizeFunction copyNameFunction, JsonSerializationContext serializationContext) {
            super.serialize(json, copyNameFunction, serializationContext);
        }

        public SwetSizeFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, LootItemCondition[] conditions) {
            return new SwetSizeFunction(conditions);
        }
    }
}
