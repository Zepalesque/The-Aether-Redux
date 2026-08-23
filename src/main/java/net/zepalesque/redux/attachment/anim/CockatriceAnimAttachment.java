package net.zepalesque.redux.attachment.anim;

import com.aetherteam.aether.entity.monster.Cockatrice;
import net.zepalesque.redux.attachment.CockatriceShootingAttachment;
import net.zepalesque.redux.config.ReduxConfig;

public class CockatriceAnimAttachment {
	// All of these are client-only
	byte targetAnim;
	byte prevTargetAnim;
	byte legAnim;
	byte prevLegAnim;
	
	public byte getTargetAnim() {
		return this.targetAnim;
	}
	
	public byte getPrevTargetAnim() {
		return this.prevTargetAnim;
	}
	
	public byte getLegAnim() {
		return this.legAnim;
	}
	
	public byte getPrevLegAnim() {
		return this.prevLegAnim;
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
