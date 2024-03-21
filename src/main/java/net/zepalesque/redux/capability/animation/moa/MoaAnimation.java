package net.zepalesque.redux.capability.animation.moa;

import com.aetherteam.aether.entity.passive.Moa;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ICompoundTagNonSynching;
import net.zepalesque.redux.capability.ReduxCapabilities;

public interface MoaAnimation extends ICompoundTagNonSynching {
    Moa getMoa();

    static LazyOptional<MoaAnimation> get(Moa moa) {
        return moa.getCapability(ReduxCapabilities.MOA_ANIM);
    }
    byte getLegAnim();
    byte getPrevLegAnim();

    void handleLegAnim();

    void tick();
}
