package net.zepalesque.redux.attachment;

import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.entity.monster.Cockatrice;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.network.packet.CockatriceShootPacket;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class CockatriceShootingAttachment implements INBTSynchable {
	
	public static final Codec<CockatriceShootingAttachment> CODEC = RecordCodecBuilder.create(
		builder -> builder.group(
			Codec.BOOL
				.fieldOf("is_shooting")
				.forGetter(CockatriceShootingAttachment::isShooting),
			Codec.INT
				.fieldOf("nearby")
				.forGetter(CockatriceShootingAttachment::getNearby),
			Codec.INT
				.fieldOf("refresh_time")
				.forGetter(CockatriceShootingAttachment::getRefreshTime),
			Codec.BOOL
				.fieldOf("was_melee")
				.forGetter(CockatriceShootingAttachment::wasMelee)
		).apply(builder, CockatriceShootingAttachment::new)
	);
	
	private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions =
		Map.ofEntries(
			Map.entry(
				"shooting",
				Triple.of(
					Type.BOOLEAN,
					val -> this.setShootingInternal((boolean) val),
					this::isShooting
				)
			)
		);
	
	// Sync to clients
	private boolean isShooting;
	// Serverside
	private int nearby;
	// Serverside
	private int refreshTime = 0;
	// Serverside
	private boolean wasMelee = false;
	
	private CockatriceShootingAttachment(
		boolean isShooting,
		int nearby,
		int refreshTime,
		boolean wasMelee
	) {
		this.isShooting = isShooting;
		this.nearby = nearby;
		this.refreshTime = refreshTime;
		this.wasMelee = wasMelee;
	}
	
	public CockatriceShootingAttachment() {}
	
	private static final int REFRESH_RESET = 200;
	private static final int GROUP_THRESHOLD = 3;
	
	// SAFETY: must be done on server only
	public void serverTick(Cockatrice birb) {
		if (ReduxConfig.SERVER.improved_cockatrice_behavior.get()) {
			var target = birb.getTarget();
			
			if (this.refreshTime <= 0) {
				this.refreshNearby(birb);
				this.refreshTime = REFRESH_RESET;
			} else --this.refreshTime;
			
			if (!this.wasMelee && target != null && this.refreshTime < REFRESH_RESET)
				this.refreshNearby(birb);
			
			var hasInebriation = target != null && target.hasEffect(AetherEffects.INEBRIATION);
			var inGroup = this.nearby >= GROUP_THRESHOLD;
			var shooting = !hasInebriation
				&& target != null
				&& !inGroup
				|| !ReduxConfig.SERVER.improved_cockatrice_behavior.get();
			if (!shooting && target != null) this.wasMelee = true;
			else if (target == null) this.wasMelee = false;
			
			this.setShooting(birb, shooting && !this.wasMelee);
		}
	
	}
	
	public void refreshNearby(Cockatrice birb) {
		if (birb.getTarget() != null) {
			var level = birb.level();
			var bounds = birb.getBoundingBox().inflate(10.0D);
			
			this.nearby = level.getEntities(birb, bounds, entity -> entity.getType() == AetherEntityTypes.COCKATRICE.get()).size();
		} else this.nearby = 0;
	}
	
	@Override
	public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
		return this.synchableFunctions;
	}
	
	@Override
	public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
		return new CockatriceShootPacket(entityID, key, type, value);
	}
	
	private void setShootingInternal(boolean shooting) {
		this.isShooting = shooting;
	}
	
	public void setShooting(Cockatrice birb, boolean shooting) {
		this.setSynched(
			birb.getId(),
			Direction.CLIENT,
			"shooting",
			shooting
		);
	}
	
	public boolean isShooting() {
		return this.isShooting;
	}
	
	public int getNearby() {
		return this.nearby;
	}
	
	public int getRefreshTime() {
		return this.refreshTime;
	}
	
	public boolean wasMelee() {
		return this.wasMelee;
	}
	
	public static @NotNull CockatriceShootingAttachment get(@NotNull Cockatrice birb) {
		return birb.getData(ReduxDataAttachments.COCKATRICE_SHOOTING.get());
	}
}
