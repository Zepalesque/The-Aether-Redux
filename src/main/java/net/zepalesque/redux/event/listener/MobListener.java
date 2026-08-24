package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import com.aetherteam.aether.entity.passive.Aerbunny;
import com.aetherteam.aether.entity.passive.Moa;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityJoinLevelEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.CockatriceShootingAttachment;
import net.zepalesque.redux.attachment.ReduxPlayerAttachment;
import net.zepalesque.redux.attachment.anim.AerbunnyAnimAttachment;
import net.zepalesque.redux.attachment.anim.CockatriceAnimAttachment;
import net.zepalesque.redux.attachment.anim.MoaAnimAttachment;
import net.zepalesque.redux.attachment.anim.SliderSignalAttachment;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.MobHooks;
import net.zepalesque.redux.event.hook.QuicksoilHooks;
import net.zepalesque.redux.network.packet.AerbunnyAnimTriggerPacket;

@EventBusSubscriber(modid = Redux.MODID)
public class MobListener {
	@SubscribeEvent
	public static void modifyAI(EntityJoinLevelEvent event) {
		if (event.getEntity() instanceof Cockatrice cockatrice && ReduxConfig.SERVER.improved_cockatrice_behavior.get()) {
			MobHooks.modifyCockatriceAI(cockatrice);
		}
	}

	@SubscribeEvent
	public static void onTick(EntityTickEvent.Post event) {
		final var entity = event.getEntity();

		if (ReduxConfig.SERVER.revamped_quicksoil_movement.get() && QuicksoilHooks.shouldAlterMovement(entity))
			QuicksoilHooks.alterMovement(entity);
		
		var client = entity.level().isClientSide();
		
		// meanwhile in another universe: `match entity { player: Player => { .. }, .. }`
		switch (entity) {
			case Player player -> {
				var att = ReduxPlayerAttachment.get(player);
				att.onUpdate(player);
			} case Slider slider when client -> {
				var att = SliderSignalAttachment.get(slider);
				att.onUpdate(slider);
			} case Moa moa when client -> {
				var att = MoaAnimAttachment.get(moa);
				att.onUpdate(moa);
			} case Cockatrice cockatrice -> {
				if (!client) {
					var att = CockatriceShootingAttachment.get(cockatrice);
					att.serverTick(cockatrice);
				} else {
					var att = CockatriceAnimAttachment.get(cockatrice);
					att.clientTick(cockatrice);
				}
			} case Aerbunny bnuuy -> {
				var att = AerbunnyAnimAttachment.get(bnuuy);
				if (client) att.clientTick(bnuuy);
				else att.serverTick(bnuuy);
			} default -> {}
		}
	}
	
	@SubscribeEvent
	public static void onHurt(LivingDamageEvent.Post event) {
		if (event.getEntity() instanceof Aerbunny bnuuy && !bnuuy.level().isClientSide())
			PacketDistributor.sendToPlayersNear(
				(ServerLevel) bnuuy.level(),
				null,
				bnuuy.getX(),
				bnuuy.getY(),
				bnuuy.getZ(),
				127D,
				new AerbunnyAnimTriggerPacket.HurtAnim(bnuuy.getId())
			);
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
