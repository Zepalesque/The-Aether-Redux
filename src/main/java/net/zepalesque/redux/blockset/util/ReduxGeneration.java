package net.zepalesque.redux.blockset.util;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.data.prov.tags.ReduxItemTagsProvider;
import net.zepalesque.zenith.api.blockset.BlockSet;

public interface ReduxGeneration extends BlockSet {

    @Override
    default void blockData(BlockStateProvider provider) {
        if (provider instanceof ReduxBlockStateProvider data) {
            this.blockData(data);
        }
    }

    void blockData(ReduxBlockStateProvider data);


    @Override
    default void itemData(ItemModelProvider provider) {
        if (provider instanceof ReduxItemModelProvider data) {
            this.itemData(data);
        }
    }

    void itemData(ReduxItemModelProvider data);


    @Override
    default void langData(LanguageProvider provider) {
        if (provider instanceof ReduxLanguageProvider data) {
            this.langData(data);
        }
    }


    void langData(ReduxLanguageProvider data);

    @Override
    default void recipeData(RecipeProvider provider, RecipeOutput output) {
        if (provider instanceof ReduxRecipeProvider data) {
            this.recipeData(data, output);
        }
    }

    void recipeData(ReduxRecipeProvider data, RecipeOutput consumer);


    @Override
    default void blockTagData(BlockTagsProvider provider) {
        if (provider instanceof ReduxBlockTagsProvider data) {
            this.blockTagData(data);
        }
    }

    void blockTagData(ReduxBlockTagsProvider data);


    @Override
    default void itemTagData(ItemTagsProvider provider) {
        if (provider instanceof ReduxItemTagsProvider data) {
            this.itemTagData(data);
        }
    }

    void itemTagData(ReduxItemTagsProvider data);


    @Override
    default void lootData(BlockLootSubProvider provider) {
        if (provider instanceof ReduxBlockLootProvider data) {
            this.lootData(data);
        }
    }

    void lootData(ReduxBlockLootProvider data);



}