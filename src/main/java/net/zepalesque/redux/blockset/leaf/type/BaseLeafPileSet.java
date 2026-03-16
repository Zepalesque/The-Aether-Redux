package net.zepalesque.redux.blockset.leaf.type;

import com.mojang.datafixers.util.Pair;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.ReduxNaturalWall;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;
import net.zepalesque.redux.data.prov.tags.ReduxBlockTagsProvider;
import net.zepalesque.redux.data.prov.tags.ReduxItemTagsProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.unity.block.natural.leaves.LeafPileBlock;
import net.zepalesque.zenith.util.item.TabUtil;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseLeafPileSet<L extends LeavesBlock, S extends SaplingBlock, Self extends BaseLeafPileSet<L, S, Self>> extends BaseLeafSet<L, S, Self> {
	
	private final DeferredBlock<LeafPileBlock> pile;
	
	private float pileCompost;
	
	private String pileLore;
	
	protected final Map<Supplier<CreativeModeTab>, Pair<ItemLike, TabAdditionPhase>> pileBeforeOrdering =
		new HashMap<>();
	protected final Map<Supplier<CreativeModeTab>, Pair<ItemLike, TabAdditionPhase>> pileAfterOrdering =
		new HashMap<>();
	protected final Map<Supplier<CreativeModeTab>, TabAdditionPhase> pileAppended = new HashMap<>();
	
	protected final Pair<Collection<TagKey<Item>>, Collection<TagKey<Block>>> pileTags = Pair.of(new ArrayList<>(), new ArrayList<>());
	
	
	public BaseLeafPileSet(String id, String saplTexFold, String leafTexFold, TreeGrower grower, Supplier<L> leaves, Function<TreeGrower, S> sapling) {
		super(id, saplTexFold, leafTexFold, grower, leaves, sapling);
		var blocks = ReduxBlocks.BLOCKS;
		var items = ReduxItems.ITEMS;
		this.pile = pile(blocks, items, id);
	}
	protected DeferredBlock<LeafPileBlock> pile(
		DeferredRegister.Blocks registry,
		DeferredRegister.Items items,
		String id
	) {
		var block = registry.register(id + "_leaf_pile", () ->
			new LeafPileBlock(this.leaves)
		);
		items.register(block.getId().getPath(), () ->
			new BlockItem(block.get(), new Item.Properties())
		);
		return block;
	}
	
	public DeferredBlock<LeafPileBlock> pile() {
		return this.pile;
	}
	
	public Self pileCompost(float amount) {
		this.pileCompost = amount;
		return self();
	}
	
	@Override
	public void mapData(ReduxDataMapProvider data) {
		super.mapData(data);
		var compostables = data.builder(NeoForgeDataMaps.COMPOSTABLES);
		data.addCompost(compostables, this.pile(), this.pileCompost);
	}
	
	@Override
	public void blockData(ReduxBlockStateProvider data) {
		this.mainBlockData(data);
		data.leafPile(this.pile().get(), this.leaves().get(), this.leafTexFold);
	}
	
	public abstract void mainBlockData(ReduxBlockStateProvider data);
	
	
	@Override
	public void itemData(ReduxItemModelProvider data) {
		this.mainItemData(data);
		data.leafPile(this.pile().get());
	}
	
	public abstract void mainItemData(ReduxItemModelProvider data);
	
	public Self withPileItemTag(TagKey<Item> tag) {
		this.pileTags.getFirst().add(tag);
		return self();
	}
	
	public Self withPileTag(TagKey<Block> tag) {
		this.pileTags.getSecond().add(tag);
		return self();
	}
	
	@Override
	public void blockTagData(ReduxBlockTagsProvider data) {
		super.blockTagData(data);
		this.pileTags.getSecond().forEach(tag -> data.tag(tag).add(this.pile().get()));
	}
	
	@Override
	public void itemTagData(ReduxItemTagsProvider data) {
		super.itemTagData(data);
		this.pileTags.getFirst().forEach(tag -> data.tag(tag).add(this.pile().asItem()));
	}
	
	public Self withPileLore(String lore) {
		this.pileLore = lore;
		return self();
	}
	
	@Override
	public void langData(ReduxLanguageProvider data) {
		super.langData(data);
		data.addBlock(this.pile());
		if (this.pileLore != null) data.addLore(this.pile(), this.pileLore);
	}
	
	public Self pileTabAfter(
		Supplier<CreativeModeTab> tab,
		ItemLike placeAfter,
		TabAdditionPhase phase
	) {
		this.pileAfterOrdering.put(tab, Pair.of(placeAfter, phase));
		return self();
	}
	
	public Self pileTabBefore(Supplier<CreativeModeTab> tab, ItemLike placeBefore, TabAdditionPhase phase) {
		this.pileBeforeOrdering.put(tab, Pair.of(placeBefore, phase));
		return self();
	}
	
	public Self pileTabAppend(Supplier<CreativeModeTab> tab, TabAdditionPhase phase) {
		this.pileAppended.put(tab, phase);
		return self();
	}
	
	
	@Override
	@Nullable
	public ItemLike addToCreativeTab(BuildCreativeModeTabContentsEvent event, ItemLike prev, TabAdditionPhase phase) {
		var p = super.addToCreativeTab(event, prev, phase);
		
		for (var entry : this.pileAfterOrdering.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var pair = entry.getValue();
				if (phase == pair.getSecond()) TabUtil.putAfter(event, pair.getFirst(), this.pile());
			}
		}
		for (var entry : this.pileBeforeOrdering.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var pair = entry.getValue();
				if (phase == pair.getSecond()) TabUtil.putBefore(event, pair.getFirst(), this.pile());
			}
		}
		for (var entry : this.pileAppended.entrySet()) {
			var tabToAddTo = entry.getKey();
			if (TabUtil.isForTab(event, tabToAddTo)) {
				var current = entry.getValue();
				if (phase == current) TabUtil.put(event, this.pile());
			}
		}
		
		return null;
	}
	
	@Override
	public void lootData(ReduxBlockLootProvider data) {
		super.lootData(data);
		data.add(this.pile().get(), data.shears());
	}
}
