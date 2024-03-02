package net.zepalesque.redux.compat.jei.category;

import com.aetherteam.aether.item.AetherItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.recipe.InfusionRecipe;

public class InfusionRecipeCategory extends AbstractStackingRecipeCategory<InfusionRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(Redux.MODID, "infusion");
    public static final ResourceLocation TEXTURE = new ResourceLocation(Redux.MODID, "textures/gui/infusion_jei_render.png");
    public static final RecipeType<InfusionRecipe> RECIPE_TYPE = RecipeType.create(Redux.MODID, "infusion", InfusionRecipe.class);

    public InfusionRecipeCategory(IGuiHelper guiHelper) {
        super("infusion", UID, guiHelper.createDrawable(TEXTURE, 0, 0, 84, 28), guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(AetherItems.AMBROSIUM_SHARD.get())), RECIPE_TYPE);
    }
}
