package net.zepalesque.redux.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxRecipeTypes {
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(ForgeRegistries.RECIPE_TYPES, Redux.MODID);
    public static final RegistryObject<RecipeType<BlightedSporeRecipe>> SPORE_BLIGHTING = RECIPE_TYPES.register("spore_blighting", () -> RecipeType.simple(new ResourceLocation(Redux.MODID, "spore_blighting")));

    public static final RegistryObject<RecipeType<InfusionRecipe>> INFUSION = RECIPE_TYPES.register("infusion", () -> RecipeType.simple(new ResourceLocation(Redux.MODID, "infusion")));

}