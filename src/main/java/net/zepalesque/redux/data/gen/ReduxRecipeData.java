package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.block.AetherBlocks;
import com.aetherteam.aether.item.AetherItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.common.Tags;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.blockset.flower.ReduxFlowerSets;
import net.zepalesque.redux.blockset.stone.ReduxStoneSets;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.recipe.recipes.InfusionRecipe;
import net.zepalesque.unity.block.UnityBlocks;
import org.jetbrains.annotations.NotNull;

public class ReduxRecipeData extends ReduxRecipeProvider {

    public ReduxRecipeData(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Redux.MODID);
    }

    @Override
    protected void buildRecipes(@NotNull RecipeOutput output) {
        Redux.BLOCK_SETS.forEach(set -> set.recipeData(this, output));

        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.VERIDIUM_NUGGET.get(), RecipeCategory.MISC, ReduxItems.VERIDIUM_INGOT.get(), "veridium_nugget", "veridium_nugget_to_veridium_ingot");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.SENTRITE_CHUNK.get(), RecipeCategory.MISC, ReduxItems.REFINED_SENTRITE.get(), "sentrite_chunk", "sentrite_chunk_to_refined_sentrite");
        smeltingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxBlocks.VERIDIUM_ORE.get(), 0.8F).save(output, name("smelt_veridium"));
        blastingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxBlocks.VERIDIUM_ORE.get(), 0.8F).save(output, name("blast_veridium"));
        smeltingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxItems.RAW_VERIDIUM.get(), 0.8F).save(output, name("smelt_raw_veridium"));
        blastingOreRecipe(ReduxItems.VERIDIUM_INGOT.get(), ReduxItems.RAW_VERIDIUM.get(), 0.8F).save(output, name("blast_raw_veridium"));
        smeltingOreRecipe(ReduxItems.REFINED_SENTRITE.get(), ReduxStoneSets.SENTRITE.block().get(), 0.8F, 300).save(output, name("refine_sentrite_smelt"));
        blastingOreRecipe(ReduxItems.REFINED_SENTRITE.get(), ReduxStoneSets.SENTRITE.block().get(), 0.8F, 150).save(output, name("refine_sentrite_blast"));

        enchantingRecipe(RecipeCategory.DECORATIONS, ReduxFlowerSets.GILDENROOT_SAPLING.flower().get(), AetherBlocks.SKYROOT_SAPLING.get(), 0.1F, 1000).save(output);

        triConversion(output,
            ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK.get(),
            AetherBlocks.AETHER_GRASS_BLOCK.get(),
            AetherBlocks.ENCHANTED_AETHER_GRASS_BLOCK.get()
        );
        triConversion(output,
            ReduxBlocks.STORMFIR_LEAVES.get(),
            AetherBlocks.SKYROOT_LEAVES.get(),
            ReduxBlocks.GILDENROOT_LEAVES.get()
        );
        triConversion(output,
            ReduxFlowerSets.STORMFIR_SAPLING.flower().get(),
            AetherBlocks.SKYROOT_SAPLING.get(),
            ReduxFlowerSets.GILDENROOT_SAPLING.flower().get()
        );
        triConversion(output,
            ReduxFlowerSets.STORMFIR_SAPLING.pot().get(),
            AetherBlocks.POTTED_SKYROOT_SAPLING.get(),
            ReduxFlowerSets.GILDENROOT_SAPLING.pot().get()
        );
        triConversion(output,
            ReduxBlocks.STORMFIR_LEAF_PILE.get(),
            UnityBlocks.SKYROOT_LEAF_PILE.get(),
            ReduxBlocks.GILDENROOT_LEAF_PILE.get()
        );
        triConversion(output,
            ReduxStoneSets.BLEAKMOSS_HOLYSTONE.block().get(),
            AetherBlocks.MOSSY_HOLYSTONE.get(),
            ReduxStoneSets.GILDED_HOLYSTONE.block().get()
        );
        triConversion(output,
            ReduxStoneSets.BLEAKMOSS_HOLYSTONE.slab().get(),
            AetherBlocks.MOSSY_HOLYSTONE_SLAB.get(),
            ReduxStoneSets.GILDED_HOLYSTONE.slab().get()
        );
        triConversion(output,
            ReduxStoneSets.BLEAKMOSS_HOLYSTONE.stairs().get(),
            AetherBlocks.MOSSY_HOLYSTONE_STAIRS.get(),
            ReduxStoneSets.GILDED_HOLYSTONE.stairs().get()
        );
        triConversion(output,
            ReduxStoneSets.BLEAKMOSS_HOLYSTONE.wall().get(),
            AetherBlocks.MOSSY_HOLYSTONE_WALL.get(),
            ReduxStoneSets.GILDED_HOLYSTONE.wall().get()
        );
        triConversion(output,
            ReduxBlocks.BLEAKMOSS_BLOCK.get(),
            UnityBlocks.FLUTEMOSS_BLOCK.get(),
            ReduxBlocks.GILDENMOSS_BLOCK.get()
        );
        triConversion(output,
            ReduxBlocks.BLEAKMOSS_CARPET.get(),
            UnityBlocks.FLUTEMOSS_CARPET.get(),
            ReduxBlocks.GILDENMOSS_CARPET.get()
        );
        
        
        layerBlock(output, ReduxBlocks.GILDENROOT_LEAF_PILE.get(), ReduxBlocks.GILDENROOT_LEAVES.get(), 6);
        layerBlock(output, ReduxBlocks.STORMFIR_LEAF_PILE.get(), ReduxBlocks.STORMFIR_LEAVES.get(), 6);
        layerBlock(output, ReduxBlocks.BLIGHTWILLOW_LEAF_PILE.get(), ReduxBlocks.BLIGHTWILLOW_LEAVES.get(), 6);

        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_BASE.get(), AetherBlocks.CARVED_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_PILLAR.get(), AetherBlocks.CARVED_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_BASE.get(), AetherBlocks.SENTRY_STONE.get());
        stonecuttingRecipe(output, RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_PILLAR.get(), AetherBlocks.SENTRY_STONE.get());

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_BASE.get(), 4)
                .define('#', AetherBlocks.CARVED_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherBlocks.CARVED_STONE.get()), has(AetherBlocks.CARVED_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.CARVED_PILLAR.get(), 6)
                .define('#', AetherBlocks.CARVED_STONE.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherBlocks.CARVED_STONE.get()), has(AetherBlocks.CARVED_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_BASE.get(), 4)
                .define('#', AetherBlocks.SENTRY_STONE.get())
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherBlocks.SENTRY_STONE.get()), has(AetherBlocks.SENTRY_STONE.get()))
                .save(output);
        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.SENTRY_PILLAR.get(), 6)
                .define('#', AetherBlocks.SENTRY_STONE.get())
                .pattern("##")
                .pattern("##")
                .pattern("##")
                .unlockedBy(getHasName(AetherBlocks.SENTRY_STONE.get()), has(AetherBlocks.SENTRY_STONE.get()))
                .save(output);
        
        ShapelessRecipeBuilder.shapeless(RecipeCategory.FOOD, ReduxItems.SEAWEED_SALAD)
            .requires(Items.BOWL)
            .requires(ReduxItems.CAELGAE_CLUMP, 3)
            .requires(ReduxItems.WYND_OATS)
            .unlockedBy(getHasName(ReduxItems.CAELGAE_CLUMP), has(ReduxItems.CAELGAE_CLUMP))
            .save(output);
        
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ReduxItems.SENTRITE_SHEARS)
            .define('S', ReduxItems.REFINED_SENTRITE)
            .pattern(" S")
            .pattern("S ")
            .unlockedBy(getHasName(ReduxItems.REFINED_SENTRITE), has(ReduxItems.REFINED_SENTRITE))
            .save(output);

        CompoundTag infusionInfo = new CompoundTag();
        infusionInfo.putShort(InfusionRecipe.ADDED_INFUSION, (short) 4);
        Holder<SoundEvent> infusionSound = ReduxSounds.INFUSE_ITEM;

        infuse(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(), ReduxItems.VERIDIUM_PICKAXE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_pickaxe_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(), ReduxItems.INFUSED_VERIDIUM_PICKAXE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_pickaxe_increase_infusion"));

        infuse(ReduxItems.INFUSED_VERIDIUM_AXE.get(), ReduxItems.VERIDIUM_AXE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_axe_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_AXE.get(), ReduxItems.INFUSED_VERIDIUM_AXE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_axe_increase_infusion"));

        infuse(ReduxItems.INFUSED_VERIDIUM_SHOVEL.get(), ReduxItems.VERIDIUM_SHOVEL.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_shovel_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_SHOVEL.get(), ReduxItems.INFUSED_VERIDIUM_SHOVEL.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_shovel_increase_infusion"));

        infuse(ReduxItems.INFUSED_VERIDIUM_HOE.get(), ReduxItems.VERIDIUM_HOE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_hoe_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_HOE.get(), ReduxItems.INFUSED_VERIDIUM_HOE.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_hoe_increase_infusion"));

        infuse(ReduxItems.INFUSED_VERIDIUM_SWORD.get(), ReduxItems.VERIDIUM_SWORD.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_sword_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_SWORD.get(), ReduxItems.INFUSED_VERIDIUM_SWORD.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_sword_increase_infusion"));

        infuse(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER.get(), ReduxItems.VERIDIUM_DART_SHOOTER.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_dart_shooter_infuse"));

        infuse(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER.get(), ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER.get())
                .withSound(infusionSound)
                .withExtra(infusionInfo)
                .save(output, Redux.loc("veridium_dart_shooter_increase_infusion"));


        makePickaxe(ReduxItems.VERIDIUM_PICKAXE, ReduxItems.VERIDIUM_INGOT).save(output);
        makeShovel(ReduxItems.VERIDIUM_SHOVEL, ReduxItems.VERIDIUM_INGOT).save(output);
        makeAxe(ReduxItems.VERIDIUM_AXE, ReduxItems.VERIDIUM_INGOT).save(output);
        makeHoe(ReduxItems.VERIDIUM_HOE, ReduxItems.VERIDIUM_INGOT).save(output);
        makeSword(ReduxItems.VERIDIUM_SWORD, ReduxItems.VERIDIUM_INGOT).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ReduxItems.VERIDIUM_DART.get(), 4)
                .define('F', Tags.Items.FEATHERS)
                .define('/', AetherTags.Items.SKYROOT_STICKS)
                .define('V', ReduxItems.VERIDIUM_INGOT.get())
                .pattern("F")
                .pattern("/")
                .pattern("V")
                .unlockedBy("has_feather", has(Tags.Items.FEATHERS))
                .unlockedBy(getHasName(ReduxItems.VERIDIUM_INGOT.get()), has(ReduxItems.VERIDIUM_INGOT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ReduxItems.VERIDIUM_DART_SHOOTER.get(), 1)
                .define('H', AetherBlocks.HOLYSTONE.get())
                .define('V', ReduxItems.VERIDIUM_INGOT.get())
                .pattern("H")
                .pattern("H")
                .pattern("V")
                .unlockedBy(getHasName(ReduxItems.VERIDIUM_INGOT.get()), has(ReduxItems.VERIDIUM_INGOT.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReduxBlocks.SENTRITE_LANTERN.get(), 1)
                .define('C', ReduxItems.SENTRITE_CHUNK.get())
                .define('A', AetherBlocks.AMBROSIUM_TORCH.get())
                .pattern("CCC")
                .pattern("CAC")
                .pattern("CCC")
                .unlockedBy(getHasName(ReduxItems.SENTRITE_CHUNK.get()), has(ReduxItems.SENTRITE_CHUNK.get()))
                .unlockedBy(getHasName(ReduxItems.REFINED_SENTRITE.get()), has(ReduxItems.REFINED_SENTRITE.get()))
                .unlockedBy(getHasName(AetherBlocks.AMBROSIUM_TORCH.get()), has(AetherBlocks.AMBROSIUM_TORCH.get()))
                .unlockedBy(getHasName(AetherItems.AMBROSIUM_SHARD.get()), has(AetherItems.AMBROSIUM_SHARD.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReduxBlocks.SENTRITE_BARS.get(), 16)
                .define('P', ReduxItems.REFINED_SENTRITE.get())
                .pattern("PPP")
                .pattern("PPP")
                .unlockedBy(getHasName(ReduxItems.REFINED_SENTRITE.get()), has(ReduxItems.REFINED_SENTRITE.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ReduxBlocks.SENTRITE_CHAIN.get(), 3)
                .define('I', ReduxItems.REFINED_SENTRITE.get())
                .define('N', ReduxItems.SENTRITE_CHUNK.get())
                .pattern("N")
                .pattern("I")
                .pattern("N")
                .unlockedBy(getHasName(ReduxItems.SENTRITE_CHUNK.get()), has(ReduxItems.SENTRITE_CHUNK.get()))
                .unlockedBy(getHasName(ReduxItems.REFINED_SENTRITE.get()), has(ReduxItems.REFINED_SENTRITE.get()))
                .save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.REDSTONE, ReduxBlocks.LOGICATOR.get())
                .define('S', Blocks.STONE)
                .define('R', Items.REDSTONE)
                .define('T', Blocks.REDSTONE_TORCH)
                // TODO: switch this to Sentry Chip/Circuit
                .define('V', ReduxItems.VERIDIUM_INGOT.get())
                .pattern("RTR")
                .pattern("SVS")
                .unlockedBy(getHasName(ReduxItems.VERIDIUM_INGOT.get()), has(ReduxItems.VERIDIUM_INGOT.get()))
                .save(output);

        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.VERIDIUM_INGOT.get(), RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.VERIDIUM_BLOCK.get(), "veridium_ingot_from_veridium_block", "veridium_ingot");
        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.RAW_VERIDIUM.get(), RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.RAW_VERIDIUM_BLOCK.get(), "raw_veridium_from_raw_veridium_block", "raw_veridium");

        oreBlockStorageRecipesRecipesWithCustomUnpacking(output, RecipeCategory.MISC, ReduxItems.REFINED_SENTRITE.get(), RecipeCategory.BUILDING_BLOCKS, ReduxBlocks.REFINED_SENTRITE_BLOCK.get(), "refined_sentrite_from_refined_sentrite_block", "refined_sentrite");
    }
}
