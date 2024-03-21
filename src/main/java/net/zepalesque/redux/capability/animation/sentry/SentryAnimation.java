package net.zepalesque.redux.capability.animation.sentry;

import com.aetherteam.aether.entity.monster.dungeon.Sentry;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ICompoundTagNonSynching;
import net.zepalesque.redux.capability.ReduxCapabilities;

public interface SentryAnimation extends ICompoundTagNonSynching {
    Sentry getSentry();

    static LazyOptional<SentryAnimation> get(Sentry sentry) {
        return sentry.getCapability(ReduxCapabilities.SENTRY_ANIM);
    }
    byte getJumpAnim();
    byte getPrevJumpAnim();

    void jump();

    void tick();
}
