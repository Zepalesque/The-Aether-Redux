package net.zepalesque.redux.compat.jei.category;

import com.aetherteam.nitrogen.integration.jei.categories.AbstractRecipeCategory;
import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.recipe.AbstractStackingRecipe;

import java.util.Arrays;
import java.util.Objects;

public abstract class AbstractStackingRecipeCategory<T extends AbstractStackingRecipe> extends AbstractRecipeCategory<T> {

    public AbstractStackingRecipeCategory(String id, ResourceLocation uid, IDrawable background, IDrawable icon, RecipeType<T> recipeType) {
        super(id, uid, background, icon, recipeType);
    }

    public void setRecipe(IRecipeLayoutBuilder builder, T recipe, IFocusGroup focusGroup) {

        builder.addSlot(RecipeIngredientRole.INPUT, 8, 6).addItemStacks(Arrays.stream(recipe.getIngredient().getItems()).toList());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 60, 6).addItemStacks(Arrays.stream(recipe.getIngredient().getItems()).map(stack -> Objects.requireNonNullElse(recipe.getResultStack(stack), ItemStack.EMPTY)).toList());

    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.aether_redux.jei." + this.id);
    }


    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, PoseStack poseStack, double mouseX, double mouseY) {
    }

    private static <T extends AbstractStackingRecipe> Component createBurnTimeText(T recipe) {
        return Component.translatable("gui.aether_redux.jei.infusion_charge", 8);
    }
}
