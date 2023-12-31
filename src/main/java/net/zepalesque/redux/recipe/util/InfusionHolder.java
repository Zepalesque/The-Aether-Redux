package net.zepalesque.redux.recipe.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.ItemStack;

public class InfusionHolder {
    public static final Codec<InfusionHolder> CODEC = RecordCodecBuilder.create((infusion) ->
            infusion.group(ItemStack.CODEC.fieldOf("infused").forGetter((config) -> config.infused),
                            ItemStack.CODEC.fieldOf("result").forGetter((config) -> config.result))
            .apply(infusion, InfusionHolder::new));


    private final ItemStack infused;
    private final ItemStack result;

    public InfusionHolder(ItemStack infused, ItemStack result) {
        this.infused = infused;
        this.result = result;
    }

    public ItemStack getInfused() {
        return infused;
    }

    public ItemStack getResult() {
        return result;
    }
}
