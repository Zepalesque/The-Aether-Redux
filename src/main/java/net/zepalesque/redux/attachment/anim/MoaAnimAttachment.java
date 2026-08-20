package net.zepalesque.redux.attachment.anim;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.aether.entity.passive.Moa;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.attachment.SliderSignalAttachment;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class MoaAnimAttachment {

    private byte legAnim;
    private byte prevLegAnim;

    public MoaAnimAttachment() {}
    
    
    public static @NotNull MoaAnimAttachment get(@NotNull Moa moa) {
        return moa.getData(ReduxDataAttachments.MOA_ANIM.get());
    }

    public byte getLegAnim(Moa moa) {
        return this.legAnim;
    }

    public byte getPrevLegAnim(Moa moa) {
        return this.prevLegAnim;
    }

    public void onUpdate(Moa moa) {
        this.handleLegAnim(moa);
    }

    public void handleLegAnim(Moa moa) {
        if (moa.level().isClientSide()) {
            this.prevLegAnim = this.legAnim;
			var onGround = moa.onGround();
	        if (!onGround && this.legAnim < 5) this.legAnim++;
	        else if (onGround && this.legAnim > 0) this.legAnim--;
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || this.getClass() != o.getClass()) return false;
	    var that = (MoaAnimAttachment) o;
        return this.legAnim == that.legAnim && this.prevLegAnim == that.prevLegAnim;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.legAnim, this.prevLegAnim);
    }
    
    @Override
    public String toString() {
        return "MoaAnimAttachment[" +
            "legAnim=" + this.legAnim +
            ", prevLegAnim=" + this.prevLegAnim +
            ']';
    }
}