package net.zepalesque.redux.blockset.flower.type;

import com.mojang.datafixers.util.Pair;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.bush.CustomBoundsFlowerBlock;
import net.zepalesque.redux.blockset.util.MutableLoreGeneration;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.ReduxRecipeProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.data.prov.tags.ReduxItemTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.api.blockset.AbstractFlowerSet;
import net.zepalesque.zenith.api.blockset.util.CraftingMatrix;
import net.zepalesque.zenith.mixin.mixins.common.accessor.FireAccessor;
import net.zepalesque.zenith.util.TabUtil;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public abstract class BaseFlowerSet extends AbstractFlowerSet implements MutableLoreGeneration<BaseFlowerSet> {

    public final String id, textureFolder;
    protected String lore;
    protected final VoxelShape shape;
    private float compost = 0.65F;

    protected final Map<CraftingMatrix, Pair<Supplier<? extends ItemLike>, RecipeCategory>> crafted = new HashMap<>();
    protected final Map<Integer, Triple<Supplier<? extends ItemLike>, Integer, RecipeCategory>> shapeless = new HashMap<>();
    protected final Map<Supplier<? extends ItemLike>, Float> smelted = new HashMap<>();
    protected final Map<Supplier<CreativeModeTab>, Supplier<? extends ItemLike>> creativeTabOrdering = new HashMap<>();
    protected final Collection<TagKey<Block>> tags = new ArrayList<>();
    protected final Collection<TagKey<Block>> potTags = new ArrayList<>();
    protected final Collection<TagKey<Item>> itemTags = new ArrayList<>();
    @Nullable
    protected Pair<Integer, Integer> flammability = Pair.of(60, 100);

    protected final DeferredBlock<CustomBoundsFlowerBlock> flower;
    protected final DeferredBlock<FlowerPotBlock> pot;
    private UnaryOperator<Properties> properties;
    private UnaryOperator<Properties> potProperties;

    public BaseFlowerSet(String id, String textureFolder, VoxelShape shape, Supplier<MobEffect> effect, int duration, OffsetType offset, SoundType sound) {
        this.id = id;
        DeferredRegister.Blocks blocks = ReduxBlocks.BLOCKS;
        DeferredRegister.Items items = ReduxItems.ITEMS;
        this.textureFolder = textureFolder;
        this.shape = shape;
        this.flower = flower(blocks, items, id, effect, duration, offset, sound);
        this.pot = pot(blocks, id);
    }

    @Override
    protected DeferredBlock<CustomBoundsFlowerBlock> flower(DeferredRegister.Blocks registry, DeferredRegister.Items items, String id, Supplier<MobEffect> effect, int duration, OffsetType offset, SoundType sound) {
        var flower = registry.register(id, () -> new CustomBoundsFlowerBlock(this.shape, effect, duration, this.properties.apply(Properties.ofFullCopy(Blocks.DANDELION)
                .sound(sound)
                .offsetType(offset)
        )));
        items.register(flower.getId().getPath(), () -> new BlockItem(flower.get(), new Item.Properties()));
        return flower;
    }

    @Override
    public DeferredBlock<CustomBoundsFlowerBlock> flower() {
        return this.flower;
    }

    @Override
    protected DeferredBlock<FlowerPotBlock> pot(DeferredRegister.Blocks registry, String id) {
        return registry.register("potted_" + id, () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT, () -> this.flower().get(), this.potProperties.apply(Properties.ofFullCopy(Blocks.FLOWER_POT))));
    }

    @Override
    public DeferredBlock<FlowerPotBlock> pot() {
        return this.pot;
    }

    @Override
    public AbstractFlowerSet craftsInto(Supplier<? extends ItemLike> block, CraftingMatrix shape, RecipeCategory category) {
        this.crafted.put(shape, Pair.of(block, category));
        return this;
    }

    @Override
    public AbstractFlowerSet craftsIntoShapeless(int ingredientCount, Supplier<? extends ItemLike> result, int resultCount, RecipeCategory category) {
        this.shapeless.put(ingredientCount, Triple.of(result, resultCount, category));
        return this;
    }

    @Override
    public AbstractFlowerSet withFlowerTag(TagKey<Block> tag) {
        this.tags.add(tag);
        return this;
    }

    @Override
    public AbstractFlowerSet withPotTag(TagKey<Block> tag) {
        this.potTags.add(tag);
        return this;
    }

    @Override
    public AbstractFlowerSet withItemTag(TagKey<Item> tag) {
        this.itemTags.add(tag);
        return this;
    }

    @Override
    public AbstractFlowerSet creativeTab(Supplier<CreativeModeTab> tab, Supplier<? extends ItemLike> placeAfter) {
        this.creativeTabOrdering.put(tab, placeAfter);
        return this;
    }

    @Override
    public AbstractFlowerSet compost(float amount) {
        this.compost = amount;
        return this;
    }

    @Override
    public AbstractFlowerSet withProperties(UnaryOperator<Properties> prop) {
        UnaryOperator<Properties> old = this.properties;
        this.properties = original -> prop.apply(old.apply(original));
        return this;
    }

    @Override
    public AbstractFlowerSet withPotProperties(UnaryOperator<Properties> prop) {
        UnaryOperator<Properties> old = this.potProperties;
        this.potProperties = original -> prop.apply(old.apply(original));
        return this;
    }

    @Override
    public AbstractFlowerSet flammable(int encouragement, int flammability) {
        this.flammability = Pair.of(encouragement, flammability);
        return this;
    }

    @Override
    public AbstractFlowerSet inflammable() {
        this.flammability = null;
        return this;
    }

    @Override
    public void mapData(ReduxDataMapProvider data) {
        var compostables = data.builder(NeoForgeDataMaps.COMPOSTABLES);
        data.addCompost(compostables, this.flower, this.compost);
    }

    @Override
    public void flammables(FireAccessor accessor) {
        if (this.flammability != null) {
            accessor.callSetFlammable(this.flower().get(), flammability.getFirst(), flammability.getSecond());
        }
    }

    @Override
    public void registerRenderers(EntityRenderersEvent.RegisterRenderers event) { }

    @Override
    public void langData(ReduxLanguageProvider data) {
        data.addBlock(this.flower());
        if (this.lore != null) { data.addLore(this.flower(), this.lore); }
        data.addBlock(this.pot());
    }

    @Override
    public void recipeData(ReduxRecipeProvider data, RecipeOutput consumer) {
        this.crafted.forEach((matrix, result) ->
                matrix.apply(ShapedRecipeBuilder.shaped(result.getSecond(), result.getFirst().get(), matrix.count())
                                .define('#', this.flower().get()))
                        .unlockedBy(ReduxRecipeProvider.getHasName(this.flower().get()), ReduxRecipeProvider.has(this.flower().get()))
                        .save(consumer, data.name(ReduxRecipeProvider.getConversionRecipeName(result.getFirst().get(), this.flower().get()))));
        this.shapeless.forEach((ingredient, result) ->
            ShapelessRecipeBuilder.shapeless(result.getRight(), result.getLeft().get(), result.getMiddle())
                    .requires(this.flower(), ingredient)
                    .unlockedBy(ReduxRecipeProvider.getHasName(this.flower().get()), ReduxRecipeProvider.has(this.flower().get()))
                    .save(consumer, data.name(ReduxRecipeProvider.getConversionRecipeName(result.getLeft().get(), this.flower().get()))));
    }

    @Override
    public void blockTagData(ReduxBlockTagsProvider data) {
        this.tags.forEach(tag -> data.tag(tag).add(this.flower().get()));
        this.potTags.forEach(tag -> data.tag(tag).add(this.pot().get()));
    }


    @Override
    public void itemTagData(ReduxItemTagsProvider data) {
        this.itemTags.forEach(tag -> data.tag(tag).add(this.flower().get().asItem()));
    }

    @Override
    public void lootData(ReduxBlockLootProvider data) {
        data.dropSelf(this.flower().get());
        data.dropPottedContents(this.pot.get());
    }

    @Override
    public BaseFlowerSet withLore(String lore) {
        this.lore = lore;
        return this;
    }

    // Ignore the prev value, implementation is different here
    @Override
    public Supplier<? extends ItemLike> addToCreativeTab(BuildCreativeModeTabContentsEvent event, Supplier<? extends ItemLike> prev) {
        for (Map.Entry<Supplier<CreativeModeTab>, Supplier<? extends ItemLike>> triple : this.creativeTabOrdering.entrySet()) {
            Supplier<CreativeModeTab> tabToAddTo = triple.getKey();
            if (event.getTab() == tabToAddTo.get()) {
                Supplier<? extends ItemLike> addAfter = triple.getValue();
                TabUtil.putAfter(addAfter, this.flower(), event);
                return this.flower();
            }
        }
        return prev;
    }
}
