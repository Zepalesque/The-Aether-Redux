package net.zepalesque.redux.capability.animation.mimic;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ICompoundTagNonSynching;
import net.zepalesque.redux.capability.ReduxCapabilities;

public interface MimicAnimation extends ICompoundTagNonSynching {
    Mimic getMimic();

    static LazyOptional<MimicAnimation> get(Mimic mimic) {
        return mimic.getCapability(ReduxCapabilities.MIMIC_ANIM);
    }
    byte getOpenAnim();
    byte getPrevOpenAnim();

    void open();

    void tick();
}
