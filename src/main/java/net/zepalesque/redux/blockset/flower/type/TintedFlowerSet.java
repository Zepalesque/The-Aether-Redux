package net.zepalesque.redux.blockset.flower.type;

import java.util.function.Supplier;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.blockset.util.TintableSet;

public abstract class TintedFlowerSet<B extends Block> extends BaseFlowerSet<B>
implements
	TintableSet
{
	private final int tintdex, itemTint;

	public TintedFlowerSet(
		String id,
		String textureFolder,
		Supplier<B> constructor,
		int tintdex,
		int itemTint) {
		super(id, textureFolder, constructor);
		this.tintdex = tintdex;
		this.itemTint = itemTint | 0xFF000000;
	}

	@Override
	public int getTintIndex() {
		return this.tintdex;
	}

	@Override
	public int getDefaultItemTint() {
		return this.itemTint;
	}
}
