package net.zepalesque.redux.compat.jei;

import com.aetherteam.aether.item.AetherItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.compat.jei.category.InfusionRecipeCategory;
import net.zepalesque.redux.recipe.ReduxRecipeTypes;

import javax.annotation.Nonnull;
import java.util.Objects;

@JeiPlugin
public class ReduxJEIPlugin implements IModPlugin {

    @Nonnull
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Redux.MODID, "jei");
    }

    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new InfusionRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
 }

    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager rm = Objects.requireNonNull(Minecraft.getInstance().level).getRecipeManager();

        registration.addRecipes(InfusionRecipeCategory.RECIPE_TYPE, rm.getAllRecipesFor(ReduxRecipeTypes.INFUSION.get()));
    }

    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

        registration.addRecipeCatalyst(new ItemStack(AetherItems.AMBROSIUM_SHARD.get()), InfusionRecipeCategory.RECIPE_TYPE);
    }
}
