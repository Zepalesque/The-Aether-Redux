package net.zepalesque.redux.client.render;

import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.zepalesque.redux.Redux;

@EventBusSubscriber(modid = Redux.MODID, value = Dist.CLIENT, bus = Bus.MOD)
public class ReduxAtlases {
	public static final Material SKYROOT_CHEST_MATERIAL = getChestMaterial("skyroot_chest");
	public static final Material SKYROOT_CHEST_LEFT_MATERIAL = getChestMaterial("skyroot_chest_left");
	public static final Material SKYROOT_CHEST_RIGHT_MATERIAL = getChestMaterial("skyroot_chest_right");
	public static final Material TRAPPED_SKYROOT_CHEST_MATERIAL = getChestMaterial("trapped_skyroot_chest");
	public static final Material TRAPPED_SKYROOT_CHEST_LEFT_MATERIAL = getChestMaterial("trapped_skyroot_chest_left");
	public static final Material TRAPPED_SKYROOT_CHEST_RIGHT_MATERIAL = getChestMaterial("trapped_skyroot_chest_right");

	public static Material getChestMaterial(String chestName) {
		return new Material(Sheets.CHEST_SHEET, new ResourceLocation(Redux.MODID, "entity/tiles/chest/" + chestName));
	}

	@SubscribeEvent
	public static void onTextureStitchPre(TextureStitchEvent.Pre event) {
		if (event.getAtlas().location().equals(Sheets.CHEST_SHEET)) {
			event.addSprite(SKYROOT_CHEST_MATERIAL.texture());
			event.addSprite(SKYROOT_CHEST_LEFT_MATERIAL.texture());
			event.addSprite(SKYROOT_CHEST_RIGHT_MATERIAL.texture());
			event.addSprite(TRAPPED_SKYROOT_CHEST_MATERIAL.texture());
			event.addSprite(TRAPPED_SKYROOT_CHEST_LEFT_MATERIAL.texture());
			event.addSprite(TRAPPED_SKYROOT_CHEST_RIGHT_MATERIAL.texture());
		}
	}
}