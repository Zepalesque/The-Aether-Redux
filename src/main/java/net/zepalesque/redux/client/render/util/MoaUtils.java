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
        return ReduxConfig.CLIENT.moa_model_upgrade.get();
    }

}
