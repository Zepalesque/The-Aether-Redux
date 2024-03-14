package net.zepalesque.redux.capability.animation.moa;

import com.aetherteam.aether.entity.passive.Moa;

public class MoaAnimationCapability implements MoaAnimation {
    private final Moa moa;


    private byte legAnim;
    private byte prevLegAnim;

    public MoaAnimationCapability(Moa pMoa) {
        this.moa = pMoa;
    }

    @Override
    public Moa getMoa() {
        return this.moa;
    }

    public byte getLegAnim() {
        return this.legAnim;
    }

    public byte getPrevLegAnim() {
        return this.prevLegAnim;
    }

    @Override
    public void tick() {
        this.handleLegAnim();
    }

    @Override
    public void handleLegAnim() {
        if (this.getMoa().level.isClientSide()) {
            this.prevLegAnim = this.legAnim;
            if (!this.moa.isEntityOnGround() && this.legAnim < 5) {
                this.legAnim++;
            }
            if (this.moa.isEntityOnGround() && this.legAnim > 0) {
                this.legAnim--;
            }
        }
    }
}