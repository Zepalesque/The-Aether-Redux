package net.zepalesque.redux.blockentity;

import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.recipebook.SmeltingRecipeBookComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.zepalesque.redux.Redux;

public class HolystoneFurnaceScreen extends AbstractFurnaceScreen<HolystoneFurnaceMenu> {
	private static final ResourceLocation FURNACE_GUI_TEXTURES = new ResourceLocation(Redux.MODID, "textures/gui/menu/holystone_furnace.png");

	public HolystoneFurnaceScreen(HolystoneFurnaceMenu container, Inventory inventory, Component title) {
		super(container, new SmeltingRecipeBookComponent(), inventory, title, FURNACE_GUI_TEXTURES);
	}
}