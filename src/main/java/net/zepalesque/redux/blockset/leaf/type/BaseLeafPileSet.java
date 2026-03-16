package net.zepalesque.redux.blockset.leaf.type;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.natural.ReduxNaturalWall;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxDataMapProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.unity.block.natural.leaves.LeafPileBlock;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class BaseLeafPileSet<L extends LeavesBlock, S extends SaplingBlock, Self extends BaseLeafPileSet<L, S, Self>> extends BaseLeafSet<L, S, Self> {
	
	private final DeferredBlock<LeafPileBlock> pile;
	
	private float pileCompost;
	
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
}
