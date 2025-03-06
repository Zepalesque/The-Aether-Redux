package net.zepalesque.redux.data.prov;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.unity.data.prov.UnitySoundsProvider;

public abstract class ReduxSoundsProvider extends UnitySoundsProvider {
    protected ReduxSoundsProvider(PackOutput output, String modId, ExistingFileHelper helper) {
        super(output, modId, helper);
    }
}
