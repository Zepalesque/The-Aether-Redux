package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.aether.entity.passive.Moa;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;
import net.zepalesque.redux.attachment.SliderSignalAttachment;
import net.zepalesque.redux.attachment.anim.MoaAnimAttachment;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.QuicksoilHooks;

@EventBusSubscriber(modid = Redux.MODID)
public class MobListener {
	@SubscribeEvent
	public static void onTick(EntityTickEvent.Post event) {
		final var entity = event.getEntity();

		if (ReduxConfig.SERVER.revamped_quicksoil_movement.get() && QuicksoilHooks.shouldAlterMovement(entity))
			QuicksoilHooks.alterMovement(entity);
		
		var client = entity.level().isClientSide();
		
		switch (entity) {
			case Player player -> {
				var attachment = ReduxPlayerAttachment.get(player);
				attachment.onUpdate(player);
			} case Slider slider when client -> {
				var attachment = SliderSignalAttachment.get(slider);
				attachment.onUpdate(slider);
			} case Moa moa when client -> {
				var attachment = MoaAnimAttachment.get(moa);
				attachment.onUpdate(moa);
			}
			default -> {}
		}
	}

	@SubscribeEvent
	public static void onFall(LivingFallEvent event) {
		if (event.getEntity() instanceof Player player) {
			var attachment = ReduxPlayerAttachment.get(player);
			var jumps = attachment.getPrevPerformedAerjumps();
			if (attachment.getPrevPerformedAerjumps() > 0) {
				var distance = event.getDistance() - jumps - 1F;
				event.setDistance(distance);
			}
		}
	}

	/*@SubscribeEvent
	public static void initializeBoundingBoxes(EntityEvent.Size event) {
		if (event.getEntity().getType().equals(AetherEntityTypes.WHIRLWIND.get())) {
			event.setNewSize(EntityDimensions.fixed(2.25F, 4.125F));
		}
	}*/
}
