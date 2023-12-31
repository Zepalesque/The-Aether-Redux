package net.zepalesque.redux.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;

/**
 * Overrides anything container-related or item-related because these in-world recipes have no container. Instead, custom behavior is implemented by recipes that extend this.
 */
public interface StackingRecipe extends Recipe<Container> {
    Ingredient getIngredient();

    ItemStack getResult();

    int infusionAmount();

    ItemStack getResultStack(ItemStack originalState);


    @Override
    default boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    default ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    default boolean canCraftInDimensions(int pWidth, int pHeight) {
        return false;
    }

    @Override
    default ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    default NonNullList<ItemStack> getRemainingItems(Container container) {
        return NonNullList.create();
    }

    @Override
    default boolean isSpecial() {
        return true;
    }
}

