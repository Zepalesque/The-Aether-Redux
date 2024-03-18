package net.zepalesque.redux.client.render.blockentity;

import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.zepalesque.redux.blockentity.ReduxBlockEntityTypes;
import net.zepalesque.redux.client.render.ReduxAtlases;

import javax.annotation.Nonnull;

public class SkyrootChestRenderer<T extends BlockEntity & LidBlockEntity> extends ChestRenderer<T> {
	public SkyrootChestRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Nonnull
	@Override
	protected Material getMaterial(@Nonnull T blockEntity, @Nonnull ChestType chestType) {
		return
			blockEntity.getType() == ReduxBlockEntityTypes.TRAPPED_SKYROOT_CHEST.get() ?
			chestType == ChestType.LEFT ? ReduxAtlases.TRAPPED_SKYROOT_CHEST_LEFT_MATERIAL :
			chestType == ChestType.RIGHT ? ReduxAtlases.TRAPPED_SKYROOT_CHEST_RIGHT_MATERIAL :
			ReduxAtlases.TRAPPED_SKYROOT_CHEST_MATERIAL :
			chestType == ChestType.LEFT ? ReduxAtlases.SKYROOT_CHEST_LEFT_MATERIAL :
			chestType == ChestType.RIGHT ? ReduxAtlases.SKYROOT_CHEST_RIGHT_MATERIAL :
			ReduxAtlases.SKYROOT_CHEST_MATERIAL;
	}
}