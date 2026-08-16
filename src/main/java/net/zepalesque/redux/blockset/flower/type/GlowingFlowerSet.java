package net.zepalesque.redux.blockset.flower.type;

import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.data.prov.ReduxBlockStateProvider;
import net.zepalesque.redux.data.prov.ReduxItemModelProvider;
import net.zepalesque.zenith.util.function.Consumers;

public class GlowingFlowerSet<B extends Block> extends UntintedFlowerSet<B> {
	public GlowingFlowerSet(
		String id,
		String textureFolder,
		Supplier<B> constructor
	) {
		super(id, textureFolder, constructor);
	}

	@Override
	public void blockData(ReduxBlockStateProvider data) {
		data.crossGlowOverlay(this.flower().get(), this.textureFolder);
		Consumers.C3<Block, Block, String> pot = this.usePottedPrefix
			? data::tintedPotGlowOverlayAlt
			: data::tintedPotGlowOverlay;
		pot.accept(this.pot().get(), this.flower().get(), this.textureFolder);
	}

	@Override
	public void itemData(ReduxItemModelProvider data) {
		data.itemBlockFlatGlow(this.flower().get(), this.textureFolder);
	}
}
