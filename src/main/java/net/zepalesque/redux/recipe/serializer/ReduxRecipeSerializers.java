package net.zepalesque.redux.recipe.serializer;

import com.aetherteam.nitrogen.recipe.serializer.BlockStateRecipeSerializer;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.recipe.BlightedSporeRecipe;
import net.zepalesque.redux.recipe.InfusionRecipe;

public class ReduxRecipeSerializers {
	public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Redux.MODID);

	public static final RegistryObject<BlockStateRecipeSerializer<BlightedSporeRecipe>> SPORE_BLIGHTING = RECIPE_SERIALIZERS.register("spore_blighting", BlightedSporeRecipe.Serializer::new);
	public static final RegistryObject<StackingRecipeSerializer<InfusionRecipe>> INFUSION = RECIPE_SERIALIZERS.register("infusion", InfusionRecipe.Serializer::new);
}