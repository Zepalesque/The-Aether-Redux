package net.zepalesque.redux.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.zepalesque.redux.item.util.VeridiumItem;

import javax.annotation.Nullable;

public abstract class AbstractStackingRecipe implements StackingRecipe {
    protected final RecipeType<?> type;
    protected final ResourceLocation id;
    protected final Ingredient ingredient;
    protected final ItemStack result;
    protected final int infusionAmount;


    public AbstractStackingRecipe(RecipeType<?> type, ResourceLocation id, Ingredient ingredient, ItemStack result, int infusionAmount) {
        this.type = type;
        this.id = id;
        this.ingredient = ingredient;
        this.result = result;
        this.infusionAmount = infusionAmount;
    }


    public boolean matches(Level level, ItemStack item) {
        return this.getIngredient().test(item);
    }

    /**
     * Sets up a new {@link ItemStack} with the original {@link ItemStack}'s NBT data. Returns null if infusion fails
     * Then the new {@link ItemStack}'s NBT data are modified based on the original {@link ItemStack}'s data.
     *
     * @param originalStack The original {@link ItemStack} being interacted with.
     * @return The new result {@link ItemStack}.
     */
    @Nullable
    @Override
    public ItemStack getResultStack(ItemStack originalStack) {
        ItemStack resultStack = this.getResult();
        resultStack.setTag(originalStack.getTag());
        ItemStack i = VeridiumItem.infuse(resultStack, this.infusionAmount);
        resultStack.setCount(originalStack.getCount());
        return i;
    }

    @Override
    public RecipeType<?> getType() {
        return this.type;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public Ingredient getIngredient() {
        return this.ingredient;
    }

    @Override
    public int infusionAmount() {
        return this.infusionAmount;
    }

    @Override
    public ItemStack getResult() {
        return this.result;
    }

}
