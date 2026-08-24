package net.zepalesque.redux.attachment.anim;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.aether.entity.passive.Moa;
import net.minecraft.util.Mth;
import net.zepalesque.redux.attachment.CockatriceShootingAttachment;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.config.ReduxConfig;
import org.jetbrains.annotations.NotNull;

public final class CockatriceAnimAttachment {
	// All of these are client-only
	byte targetAnim;
	byte prevTargetAnim;
	byte legAnim;
	byte prevLegAnim;
	
	public static @NotNull CockatriceAnimAttachment get(@NotNull Cockatrice birb) {
		return birb.getData(ReduxDataAttachments.COCKATRICE_ANIM.get());
	}
	
	public float getTargetAnim(Cockatrice birb, float partial) {
		return Mth.lerp(partial, this.prevTargetAnim, this.targetAnim) * 0.1F;
	}
	
	public float getLegAnim(Cockatrice birb, float partial) {
		return Mth.lerp(partial, this.prevLegAnim, this.legAnim) * 0.2f;
	}
	
	public void clientTick(Cockatrice birb) {
		this.handleLegAnim(birb);
		this.handleTargetAnim(birb);
	}
	
	public void handleLegAnim(Cockatrice birb) {
		this.prevLegAnim = this.legAnim;
		var onGround = birb.onGround();
		if (!onGround && this.legAnim < 5) ++this.legAnim;
		else if (onGround && this.legAnim > 0) --this.legAnim;
	}
	
	public void handleTargetAnim(Cockatrice birb) {
		if (ReduxConfig.SERVER.improved_cockatrice_behavior.get()) {
			var att = CockatriceShootingAttachment.get(birb);
			this.prevTargetAnim = this.targetAnim;
			var shooting = att.isShooting();
			if (shooting && this.targetAnim < 10) ++this.targetAnim;
			else if (!shooting && this.targetAnim > 0) --this.targetAnim;
		}
	}
}
