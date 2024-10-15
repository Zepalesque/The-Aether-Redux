package net.zepalesque.redux.client.render.util;

import com.aetherteam.aether.client.gui.screen.perks.MoaSkinsScreen;
import com.aetherteam.aether.entity.passive.Moa;
import com.aetherteam.aether.perk.data.ClientMoaSkinPerkData;
import com.aetherteam.aether.perk.types.MoaData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.zepalesque.redux.config.ReduxConfig;

import java.util.Map;
import java.util.UUID;

public class MoaUtils {

    public static boolean useNewModel(Moa moa) {
        ResourceLocation loc = getMoaSkinLocation(moa);
        return loc == null && (ReduxConfig.CLIENT.moa_model_upgrade.get());
    }

    public static ResourceLocation getMoaSkinLocation(Moa moa) {
        UUID lastRiderUUID = moa.getLastRider();
        UUID moaUUID = moa.getMoaUUID();
        Map<UUID, MoaData> userSkinsData = ClientMoaSkinPerkData.INSTANCE.getClientPerkData();
        Screen var6 = Minecraft.getInstance().screen;
        if (var6 instanceof MoaSkinsScreen moaSkinsScreen) {
            if (moaSkinsScreen.getSelectedSkin() != null && moaSkinsScreen.getPreviewMoa() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID() != null && moaSkinsScreen.getPreviewMoa().getMoaUUID().equals(moaUUID)) {
                return moaSkinsScreen.getSelectedSkin().getSkinLocation();
            }
        }
        return userSkinsData.containsKey(lastRiderUUID) && userSkinsData.get(lastRiderUUID).moaUUID() != null && userSkinsData.get(lastRiderUUID).moaUUID().equals(moaUUID) ? userSkinsData.get(lastRiderUUID).moaSkin().getSkinLocation() : null;
    }
}
