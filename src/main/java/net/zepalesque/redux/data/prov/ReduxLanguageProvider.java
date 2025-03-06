package net.zepalesque.redux.data.prov;

import net.minecraft.data.PackOutput;
import net.zepalesque.unity.data.prov.UnityLanguageProvider;

public abstract class ReduxLanguageProvider extends UnityLanguageProvider {
    public ReduxLanguageProvider(PackOutput output, String id) {
        super(output, id);
    }
}
