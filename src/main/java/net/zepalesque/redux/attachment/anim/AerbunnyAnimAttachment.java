package net.zepalesque.redux.attachment.anim;

import com.aetherteam.aether.entity.passive.Aerbunny;
import com.aetherteam.nitrogen.attachment.INBTSynchable;
import com.aetherteam.nitrogen.network.packet.SyncPacket;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.client.renderer.entity.aerbunny.ReduxAerbunnyAnimations;
import net.zepalesque.redux.network.packet.AerbunnyAnimTriggerPacket;
import net.zepalesque.redux.network.packet.AerbunnySyncPacket;
import net.zepalesque.redux.util.MiscUtil;
import org.apache.commons.lang3.tuple.Triple;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class AerbunnyAnimAttachment implements INBTSynchable {
	// server to client
	long lastStateChange = -ReduxAerbunnyAnimations.LAND_TICKS;
	// server to client
	byte state = LAND_STATE;
	
	// server only
	int twitchTimeout = Integer.MIN_VALUE;
	// server only
	int onGroundTime = ReduxAerbunnyAnimations.LAND_TICKS;
	
	private AerbunnyAnimAttachment(long lastStateChange, byte state, int twitchTimeout) {
		this.lastStateChange = lastStateChange;
		this.state = state;
		this.twitchTimeout = twitchTimeout;
	}
	
	public AerbunnyAnimAttachment(IAttachmentHolder holder) {
		if (holder instanceof Aerbunny bnuuy) bnuuy.setOnGround(true);
		else throw new ClassCastException("Holder had type `%s`, was expecting subtype of `%s`".formatted(holder.getClass().getName(), Aerbunny.class.getName()));
	}
	
	public final AnimationState fallAnim = new AnimationState();
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
	
	private static final String POSE_DATA_ID = "pose_data";
	
	public static final Codec<AerbunnyAnimAttachment> CODEC = RecordCodecBuilder.create(
		builder -> builder.group(
			Codec.LONG
				.fieldOf("last_state_change")
				.forGetter(instance -> instance.lastStateChange),
			MiscUtil.byteRange(LAND_STATE, JUMP_STATE)
				.fieldOf("state")
				.forGetter(instance -> instance.state),
			Codec.INT
				.fieldOf("twitch_timeout")
				.forGetter(instance -> instance.twitchTimeout)
		).apply(builder, AerbunnyAnimAttachment::new)
	);
	
	private static long transitionTime(byte state) {
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
				POSE_DATA_ID,
				Triple.of(
					Type.UUID,
					val -> this.setPoseData((UUID) val),
					this::getPoseData
				)
			)
		);
	
	public void serverJump(Aerbunny bnuuy) {
		this.setPoseInfo(bnuuy, bnuuy.level().getGameTime(), JUMP_STATE);
	}
	
	public boolean inAirState(Aerbunny bnuuy) {
		return this.state > 0;
	}
	
	public boolean onGroundState(Aerbunny bnuuy) {
		return this.state == 0;
	}
	
	private void changeState(Aerbunny bnuuy, byte state) {
		if (state != this.state)
			this.setPoseInfo(bnuuy, bnuuy.level().getGameTime(), state);
	}
	
	private void setPoseInfo(Aerbunny bnuuy, long tick, byte state) {
		this.setSynched(bnuuy.getId(), Direction.CLIENT, POSE_DATA_ID, makePoseData(tick, state));
	}
	
	public long getLastStateChange() {
		return this.lastStateChange;
	}
	
	public void clientPuff(Aerbunny bnuuy) {
		this.puffAnim.start(bnuuy.tickCount);
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
		return new AerbunnySyncPacket(entityID, key, type, value);
	}
	
	private static final boolean DEBUG = true;
	
	public void serverTick(Aerbunny bnuuy) {
		var vehicle = bnuuy.getVehicle();
		if (bnuuy.onGround()) ++this.onGroundTime;
		else this.onGroundTime = 0;
		
		if (this.state != LAND_STATE && (bnuuy.onGround() || vehicle instanceof Player)) {
			this.changeState(bnuuy, LAND_STATE);
		}
		else if (this.state == LAND_STATE && !bnuuy.onGround() && !(vehicle instanceof Player))
			this.changeState(bnuuy, FALL_STATE);
		
		if (this.twitchTimeout == Integer.MIN_VALUE)
			this.twitchTimeout = randTwitchTimeout(bnuuy);
		else if (this.twitchTimeout <= 0) {
			this.twitchTimeout = randTwitchTimeout(bnuuy);
			PacketDistributor.sendToPlayersNear(
				(ServerLevel) bnuuy.level(),
				null,
				bnuuy.getX(),
				bnuuy.getY(),
				bnuuy.getZ(),
				127D,
				new AerbunnyAnimTriggerPacket.TwitchAnim(bnuuy.getId())
			);
		} else if (bnuuy.onGround() && this.onGroundTime >= ReduxAerbunnyAnimations.LAND_TICKS) --this.twitchTimeout;
	}
	
	public void onClientHurt(Aerbunny bnuuy) {
		this.hurtAnim.start(bnuuy.tickCount);
	}
	
	public void doTwitchAnim(Aerbunny bnuuy) {
		this.twitchAnim.start(bnuuy.tickCount);
	}
	
	public void clientTick(Aerbunny bnuuy) {
		this.idleAnim.startIfStopped(bnuuy.tickCount);
		
		if (!this.isInPoseTransition(bnuuy)) switch (this.state) {
			case FALL_STATE, JUMP_STATE -> {
				this.inAirAnim.startIfStopped(bnuuy.tickCount);
				this.fallAnim.stop();
				this.jumpAnim.stop();
				this.landAnim.stop();
			}
			case LAND_STATE -> {
				this.inAirAnim.stop();
				this.fallAnim.stop();
				this.jumpAnim.stop();
				this.landAnim.stop();
			}
		} else switch (this.state) {
			case FALL_STATE -> {
				this.fallAnim.startIfStopped(bnuuy.tickCount);
				this.inAirAnim.stop();
				this.jumpAnim.stop();
				this.landAnim.stop();
			} case LAND_STATE -> {
				this.landAnim.startIfStopped(bnuuy.tickCount);
				this.inAirAnim.stop();
				this.fallAnim.stop();
				this.jumpAnim.stop();
			} case JUMP_STATE -> {
				this.jumpAnim.startIfStopped(bnuuy.tickCount);
				this.inAirAnim.stop();
				this.fallAnim.stop();
				this.landAnim.stop();
			}
		}
	}
	
	private static int randTwitchTimeout(Aerbunny bnuuy) {
		return bnuuy.getRandom().nextInt(1600) + ReduxAerbunnyAnimations.TWITCH_TICKS + 400;
	}
	
	public boolean isInPoseTransition(Aerbunny bnuuy) {
		return this.getPoseTime(bnuuy) < transitionTime(this.state);
	}
	
	public long getPoseTime(Aerbunny bnuuy) {
		return bnuuy.level().getGameTime() - this.lastStateChange;
	}
	
//	public boolean visuallyInAir(Aerbunny bnuuy) {
//		return this.getPoseTime(bnuuy) < 0L != this.inAirState(bnuuy);
//	}
	
	public static @NotNull AerbunnyAnimAttachment get(@NotNull Aerbunny bnuuy) {
		return bnuuy.getData(ReduxDataAttachments.AERBUNNY_ANIM.get());
	}
}