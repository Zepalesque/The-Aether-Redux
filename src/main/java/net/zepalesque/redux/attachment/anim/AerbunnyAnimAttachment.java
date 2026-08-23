package net.zepalesque.redux.attachment.anim;

import com.aetherteam.aether.entity.passive.Aerbunny;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.client.renderer.entity.aerbunny.ReduxAerbunnyAnimations;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static net.zepalesque.redux.util.MiscUtil.unreachable;

public class AerbunnyAnimAttachment implements INBTSynchable {
	// server to client
	long lastStateChange = -ReduxAerbunnyAnimations.LAND_TICKS;
	// server to client
	byte state = LAND_STATE;
	
	// server only
	int twitchTimeout = Integer.MIN_VALUE;
	
	public final AnimationState fallAnim = new AnimationState();
	public final AnimationState onGroundAnim = new AnimationState();
	public final AnimationState jumpAnim = new AnimationState();
	public final AnimationState inAirAnim = new AnimationState();
	public final AnimationState landAnim = new AnimationState();
	public final AnimationState hurtAnim = new AnimationState();
	public final AnimationState idleAnim = new AnimationState();
	public final AnimationState twitchAnim = new AnimationState();
	public final AnimationState puffAnim = new AnimationState();
	
	private static final byte LAND_STATE = 0;
	private static final byte FALL_STATE = 1;
	private static final byte JUMP_STATE = 2;
	
	private static int transitionTime(byte state) {
		return switch (state) {
			case LAND_STATE -> ReduxAerbunnyAnimations.LAND_TICKS;
			case FALL_STATE -> ReduxAerbunnyAnimations.FALL_TICKS;
			case JUMP_STATE -> ReduxAerbunnyAnimations.START_JUMP_TICKS;
			default -> -1;
		};
	}
	
	private final Map<String, Triple<INBTSynchable.Type, Consumer<Object>, Supplier<Object>>> synchableFunctions =
		Map.ofEntries(
			Map.entry(
				"pose_data",
				Triple.of(
					Type.UUID,
					val -> this.setPoseData((UUID) val),
					this::getPoseData
				)
			)
		);
	
	public void startJump(Aerbunny bnuuy) {
		if (this.onGroundState(bnuuy)) {
			this.setPoseInfo(bnuuy, bnuuy.level().getGameTime(), JUMP_STATE);
		}
	}
	
	public void startFall(Aerbunny bnuuy) {
		if (this.onGroundState(bnuuy)) {
			this.setPoseInfo(bnuuy, bnuuy.level().getGameTime(), FALL_STATE);
		}
	}
	
	public void startLand(Aerbunny bnuuy) {
		if (this.inAirState(bnuuy)) this.setPoseChangeTick(bnuuy, bnuuy.level().getGameTime());
	}
	
	private AnimationState transState(byte state) {
		return switch (state) {
			case JUMP_STATE -> this.jumpAnim;
			case FALL_STATE -> this.fallAnim;
			case LAND_STATE -> this.landAnim;
			default -> throw unreachable();
		};
	}
	private AnimationState nonTransState(byte state) {
		return switch (state) {
			case JUMP_STATE, FALL_STATE -> this.inAirAnim;
			case LAND_STATE -> this.onGroundAnim;
			default -> throw unreachable();
		};
	}
	
	public boolean inAirState(Aerbunny bnuuy) {
		return this.state > 0;
	}
	
	public boolean onGroundState(Aerbunny bnuuy) {
		return this.state == 0;
	}
	
	private void changeState(Aerbunny bnuuy, byte state) {
		this.setSynched(bnuuy.getId(), Direction.CLIENT, "pose_change_tick", makePoseData(bnuuy.level().getGameTime(), state));
	}
	
	private void setPoseChangeTick(Aerbunny bnuuy, long tick) {
		this.setSynched(bnuuy.getId(), Direction.CLIENT, "pose_change_tick", makePoseData(tick, this.state));
	}
	
	
	private void setPoseInfo(Aerbunny bnuuy, long tick, byte state) {
		this.setSynched(bnuuy.getId(), Direction.CLIENT, "pose_change_tick", makePoseData(tick, state));
	}
	
	public long getLastStateChange() {
		return this.lastStateChange;
	}
	
	private static UUID makePoseData(long tick, byte state) {
		var most = Byte.toUnsignedLong(state);
		return new UUID(most, tick);
	}
	
	private UUID getPoseData() {
		return makePoseData(this.lastStateChange, this.state);
	}
	
	private void setPoseData(UUID id) {
		var tick = id.getLeastSignificantBits();
		var state = (byte) id.getMostSignificantBits();
		this.lastStateChange = tick;
		this.state = state;
	}
	
	@Override
	public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
		return this.synchableFunctions;
	}
	
	@Override
	public SyncPacket getSyncPacket(int entityID, String key, Type type, Object value) {
		return null;
	}
	
	private static final boolean DEBUG = true;
	
	private void serverTick(Aerbunny bnuuy) {
		var vehicle = bnuuy.getVehicle();
		
		if (this.state != LAND_STATE && (bnuuy.onGround() || vehicle instanceof Player))
			this.changeState(bnuuy, LAND_STATE);
	}
	
	public void onClientHurt(Aerbunny bnuuy) {
		this.hurtAnim.start(bnuuy.tickCount);
	}
	
	private void clientTick(Aerbunny bnuuy) {
		this.idleAnim.startIfStopped(bnuuy.tickCount);
		if (this.twitchTimeout == Integer.MIN_VALUE)
			this.twitchTimeout = randTwitchTimeout(bnuuy);
		else if (this.twitchTimeout <= 0) {
			this.twitchTimeout = randTwitchTimeout(bnuuy);
			this.twitchAnim.animateWhen(!this.isInPoseTransition(bnuuy), bnuuy.tickCount);
		} else if (this.onGroundState(bnuuy))--this.twitchTimeout;
		
		if (!this.isInPoseTransition(bnuuy)) switch (this.state) {
			case FALL_STATE, JUMP_STATE -> {
				this.inAirAnim.startIfStopped(bnuuy.tickCount);
				this.onGroundAnim.stop();
				this.fallAnim.stop();
				this.jumpAnim.stop();
				this.landAnim.stop();
				if (DEBUG) System.out.println("in air");
			}
			case LAND_STATE -> {
				this.onGroundAnim.startIfStopped(bnuuy.tickCount);
				this.inAirAnim.stop();
				this.fallAnim.stop();
				this.jumpAnim.stop();
				this.landAnim.stop();
				if (DEBUG) System.out.println("on ground");
			}
		} else switch (this.state) {
			case FALL_STATE -> {
				if (DEBUG) System.out.println("falling");
				this.fallAnim.startIfStopped(bnuuy.tickCount);
				this.onGroundAnim.stop();
				this.inAirAnim.stop();
				this.jumpAnim.stop();
				this.landAnim.stop();
			} case LAND_STATE -> {
				if (DEBUG) System.out.println("landing");
				this.landAnim.startIfStopped(bnuuy.tickCount);
				this.inAirAnim.stop();
				this.fallAnim.stop();
				this.jumpAnim.stop();
				this.onGroundAnim.stop();
			} case JUMP_STATE -> {
				if (DEBUG) System.out.println("jumping");
				this.jumpAnim.startIfStopped(bnuuy.tickCount);
				this.inAirAnim.stop();
				this.fallAnim.stop();
				this.landAnim.stop();
				this.onGroundAnim.stop();
			}
		}
	}
	
	private static int randTwitchTimeout(Aerbunny bnuuy) {
		return bnuuy.getRandom().nextInt(400) + ReduxAerbunnyAnimations.TWITCH_TICKS;
	}
	
	public boolean isInPoseTransition(Aerbunny bnuuy) {
		return this.getPoseTime(bnuuy) < transitionTime(this.state);
	}
	
	public long getPoseTime(Aerbunny bnuuy) {
		return bnuuy.level().getGameTime() - this.lastStateChange;
	}
	
	public boolean visuallyInAir(Aerbunny bnuuy) {
		return this.getPoseTime(bnuuy) < 0L != this.inAirState(bnuuy);
	}
}