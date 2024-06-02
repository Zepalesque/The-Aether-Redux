package net.zepalesque.redux.data.prov;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.ParticleDescriptionProvider;

public abstract class ReduxParticleProvider extends ParticleDescriptionProvider {
    protected ReduxParticleProvider(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }
}
