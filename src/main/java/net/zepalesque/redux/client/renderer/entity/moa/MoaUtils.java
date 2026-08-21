package net.zepalesque.redux.client.renderer.entity.moa;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.config.ReduxConfig;

public final class MoaUtils {

	public static boolean useNewModel(Moa moa) {
		var loc = getCustomMoaSkinLocation(moa);
		return loc == null && ReduxConfig.CLIENT.improved_moas.get();
	}

	public static ResourceLocation getCustomMoaSkinLocation(Moa moa) {
		var rider = moa.getLastRider();
		var uuid = moa.getMoaUUID();
		var skinData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
		var screen = Minecraft.getInstance().screen;
		if (screen instanceof MoaSkinsScreen moaSkinsScreen)
			if (moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(uuid))
				return moaSkinsScreen.getSelectedSkin().getSkinLocation();
		if (
			skinData.containsKey(rider)
			&& skinData.get(rider).moaUUID() != null
			&& Objects.equals(skinData.get(rider).moaUUID(), uuid)
		) {
			var skin = skinData.get(rider).moaSkin();
			if (skin == null) return null;
			return skin.getSkinLocation();
		} else return null;
	}
}
