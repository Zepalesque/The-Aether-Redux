package net.zepalesque.redux.capability.animation.sentry;

import com.aetherteam.aether.entity.monster.dungeon.Sentry;

public class SentryAnimationCapability implements SentryAnimation {
    private final Sentry sentry;


    private byte jumpAnim;
    private byte prevJumpAnim;

    public SentryAnimationCapability(Sentry sentry) {
        this.sentry = sentry;
    }

    @Override
    public Sentry getSentry() {
        return this.sentry;
    }

    public byte getJumpAnim() {
        return this.jumpAnim;
    }

    public byte getPrevJumpAnim() {
        return this.prevJumpAnim;
    }

    @Override
    public void tick() {
        this.handleJumpAnim();
    }

    public void handleJumpAnim() {
        if (this.getSentry().level().isClientSide()) {
            this.prevJumpAnim = Byte.valueOf(this.jumpAnim);

            if (!this.sentry.onGround() && this.sentry.wasOnGround)
            {
                this.jump();
            }


            if (this.jumpAnim > 0)
            {
                this.jumpAnim--;
            }
        }
    }

    public void jump()
    {
        this.jumpAnim = 10;
    }

}