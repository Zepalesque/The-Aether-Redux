package net.zepalesque.redux.attachment;

import com.aetherteam.aether.entity.block.FloatingBlockEntity;
import com.aetherteam.aether.entity.monster.Swet;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartTNT;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.resource.registries.ReduxDamages;
import net.zepalesque.redux.event.hook.SwetHooks;
import org.jetbrains.annotations.NotNull;

public final class SwetMassAttaachment {
	private static final AttributeModifier KNOCKBACK_RESISTANCE_MODIFIER = new AttributeModifier(
		Redux.loc("temp_swet_knockback_resistance"),
		1,
		AttributeModifier.Operation.ADD_VALUE);
	
	private float massStuck = 0;
	
	public void tick(Swet swet) {
		if (ReduxConfig.SERVER.pl_swet_behavior.get() && !swet.isDeadOrDying()) {
			this.massStuck = 0;
			var lvl = swet.level();
			lvl.getEntities(swet, swet.getBoundingBox().inflate(0.9, 0.9, 0.9)).forEach(entity -> {
				var box = entity.getBoundingBox();
				this.massStuck += (float) (box.getXsize() * box.getYsize() * box.getZsize());
			});
			for (var entity : lvl.getEntities(swet, swet.getBoundingBox()))
				this.onEntityCollision(swet, entity);
		}
	}
	
	private void onEntityCollision(Swet swet, Entity entity) {
		// special absorption rules
		// TODO: just add swets to the tag??
		if (entity instanceof Swet || entity.getType().is(ReduxTags.Entities.SWET_PASSTHROUGH)) return;
		// Make items ride the swet. They often shake free with the jiggle physics
		if (entity instanceof ItemEntity item) {
			if (SwetHooks.canGrow(swet, item.getItem())) {
				swet.setSize(swet.getSize() + 1, false);
				item.remove(Entity.RemovalReason.KILLED);
				return;
			}
			item.startRiding(swet, true);
			return;
		}
		if (entity instanceof LivingEntity living && EquipmentUtil.hasSwetCape(living)) return;
		var absorbable = isAbsorbable(entity, swet.level());
		if (SwetHooks.canAbsorbEntities(swet) && absorbable) {
			// The higher this number, the stiffer the wobble is
			if (this.massStuck < 1) this.massStuck = 1;
			// dampened oscillator (nonlinear restoring force): x'' = -μx' - kx
			Vec3 center = swet
				.getBoundingBox()
				.getCenter()
				.add(
					0,
					0.45F
						* swet.getBoundingBox().getYsize()
						- (swet.getSize() == 0 ? -0.25F : 1),
					0
				);
			var suckVelocity = // acceleration (x'')
				center.subtract(entity.position()) // entity displacement (-x)
					.scale(Mth.clamp(0.25 + this.massStuck / 100, 0, 1)) // coefficient (k)
					.add(swet
						.getDeltaMovement()
						.subtract(entity.getDeltaMovement()) // delta velocity (-x')
							.scale(0.45
								/ this.massStuck
								/ SwetHooks.getAbsorbVectorScale(swet)) // coefficient (μ)
					);
			
			var maxSpeed = SwetHooks.getAbsorbVectorScale(swet) * 0.1 + 0.25;
			if (suckVelocity.length() != 0)
				// clamp the suck velocity
				suckVelocity = suckVelocity.scale(Math.min(1, maxSpeed / suckVelocity.length()));
			
			entity.setDeltaMovement(entity.getDeltaMovement().add(suckVelocity));
			entity.hasImpulse = true;
			entity.fallDistance = 0;
		}
		
		// TODO: Tag for swet-harmable entities
		if (entity instanceof Player livingEntity
			&& SwetHooks.canAbsorbEntities(swet)
			&& SwetHooks.canDamageEntities(swet)) {
			// Hack to prevent knockback; TODO: find a better way to prevent knockback
			var knockbackResistance = livingEntity.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
			if (absorbable && knockbackResistance != null) {
				knockbackResistance.addTransientModifier(KNOCKBACK_RESISTANCE_MODIFIER);
				this.damage(swet, livingEntity);
				knockbackResistance.removeModifier(KNOCKBACK_RESISTANCE_MODIFIER);
			} else this.damage(swet, livingEntity);
			
		}
	}
	
	private void damage(Swet swet, LivingEntity livingEntity) {
		if (livingEntity.hurt(ReduxDamages.entitySource(swet.level(), ReduxDamages.SWET, swet), SwetHooks.getDamage(swet)))
			swet.playSound(
				ReduxSounds.SWET_ATTACK.get(),
				1.0F,
				(swet.getRandom().nextFloat() - swet.getRandom().nextFloat())
					* 0.2F
					+ 1.0F
			);
	}
	
	
	private static boolean isAbsorbable(Entity entity, Level world) {
		if (entity.canBeCollidedWith()) return false;
		
		// TODO: JUST MAKE A TAG HOLY
		if (!(entity instanceof LivingEntity
			|| entity instanceof PrimedTnt
			|| entity instanceof MinecartTNT
			|| entity instanceof FloatingBlockEntity
			/* ArmorStands are LivingEntities */
		)) return false;
		
		var canPickupNonPlayers = world.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING);
		var isPet = entity instanceof TamableAnimal pet && pet.isTame();
		var isEligiblePlayer = entity instanceof Player player && !player.getAbilities().flying;
		var isEligiblePet = isPet && world.getDifficulty() != Difficulty.EASY;
		var isEligibleNonPlayer = !(entity instanceof Player || isPet) && canPickupNonPlayers;
		
		return !entity.isShiftKeyDown() && (isEligiblePlayer || isEligiblePet || isEligibleNonPlayer);
	}
	
	public static @NotNull SwetMassAttaachment get(@NotNull Swet swet) {
		return swet.getData(ReduxDataAttachments.SWET_MASS.get());
	}
}
