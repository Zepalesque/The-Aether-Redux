package net.zepalesque.redux.capability.animation.sentry.battle;

import com.aetherteam.aether_genesis.entity.monster.BattleSentry;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ICompoundTagNonSynching;
import net.zepalesque.redux.capability.ReduxCapabilities;

public  interface BattleSentryAnimation extends ICompoundTagNonSynching {
     BattleSentry getSentry();

    static <T extends BattleSentry> LazyOptional<BattleSentryAnimation> get(T sentry) {
        return sentry.getCapability(ReduxCapabilities.BATLLE_SENTRY_ANIM);
    }
    byte getJumpAnim();
    byte getPrevJumpAnim();

    void jump();

    void tick();

    byte getWakeAnim();
    byte getPrevWakeAnim();
}
