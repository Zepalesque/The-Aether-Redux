package net.zepalesque.redux.capability.animation.mimic;

import com.aetherteam.aether.entity.monster.dungeon.Mimic;

public class MimicAnimationCapability implements MimicAnimation {
    private final Mimic mimic;


    private byte openAnim;
    private byte prevOpenAnim;

    public MimicAnimationCapability(Mimic pMimic) {
        this.mimic = pMimic;
    }

    @Override
    public Mimic getMimic() {
        return this.mimic;
    }

    public byte getOpenAnim() {
        return this.openAnim;
    }

    public byte getPrevOpenAnim() {
        return this.prevOpenAnim;
    }

    @Override
    public void tick() {
        this.handleOpenAnim();
    }

    public void handleOpenAnim() {
        this.prevOpenAnim = this.openAnim;
        if (this.openAnim > 0)
        {
            this.openAnim--;
        }
    }

    public void open()
    {
        this.openAnim = 10;
    }

}