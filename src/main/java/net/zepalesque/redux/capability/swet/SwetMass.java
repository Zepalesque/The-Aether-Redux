package net.zepalesque.redux.capability.swet;

import com.aetherteam.aether.entity.monster.Swet;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ICompoundTagNonSynching;
import net.zepalesque.redux.capability.ReduxCapabilities;

public interface SwetMass extends /*INBTSynchable<CompoundTag>*/ ICompoundTagNonSynching {

    Swet getSwet();

    static LazyOptional<SwetMass> get(Swet entity) {
        return entity.getCapability(ReduxCapabilities.SWET_MASS);
    }

    void tick();

}
