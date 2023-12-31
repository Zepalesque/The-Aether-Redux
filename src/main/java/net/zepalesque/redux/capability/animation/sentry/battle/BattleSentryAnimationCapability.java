package net.zepalesque.redux.capability.animation.sentry.battle;

import com.aetherteam.aether_genesis.entity.monster.BattleSentry;

public class BattleSentryAnimationCapability implements BattleSentryAnimation {
    private final BattleSentry sentry;


    private byte jumpAnim;
    private byte prevJumpAnim;
    private byte wakeAnim;
    private byte prevWakeAnim;

    public BattleSentryAnimationCapability(BattleSentry sentry) {
        this.sentry = sentry;
    }

    @Override
    public BattleSentry getSentry() {
        return this.sentry;
    }

    public byte getJumpAnim() {
        return this.jumpAnim;
    }

    public byte getPrevJumpAnim() {
        return this.prevJumpAnim;
    }
    public byte getWakeAnim() {
        return this.wakeAnim;
    }

    public byte getPrevWakeAnim() {
        return this.prevWakeAnim;
    }

    @Override
    public void tick() {
        this.handleJumpAnim();
    }

    public void handleJumpAnim() {
        if (this.getSentry().level().isClientSide()) {
            this.prevWakeAnim = Byte.valueOf(this.wakeAnim);
            this.prevJumpAnim = Byte.valueOf(this.jumpAnim);

            if (!this.sentry.onGround() && this.sentry.wasOnGround)
            {
                this.jump();
            }


            if (this.jumpAnim > 0)
            {
                this.jumpAnim--;
            }

            if (this.sentry.isAwake() && this.wakeAnim < 5)
            {
             this.wakeAnim++;
            } else if (!this.sentry.isAwake() && this.wakeAnim > 0){
                this.wakeAnim--;
            }
        }
    }

    public void jump()
    {
        this.jumpAnim = 10;
    }

}