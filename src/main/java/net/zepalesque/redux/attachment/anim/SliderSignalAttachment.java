package net.zepalesque.redux.attachment.anim;

import com.aetherteam.aether.entity.monster.dungeon.boss.Slider;
import java.util.Objects;
import java.util.Optional;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.neoforged.neoforge.network.PacketDistributor;
import net.zepalesque.redux.attachment.ReduxDataAttachments;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.network.packet.SliderSignalPacket;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// Client-Side
public final class SliderSignalAttachment {
	private int signalTick = 0;

	@Nullable private Direction overrideDirection = null;

	private boolean hasOverriden = false;

	@Nullable private Entity target = null;

	public void onUpdate(Slider slider) {
		this.tickSignal(slider);
	}

	private void tickSignal(Slider slider) {
		if (this.signalTick > 0 && slider.level().isClientSide()) {
			if (this.signalTick == 2) this.playSound(slider);
			else if (this.signalTick == 1) this.overrideDirection = null;
			this.signalTick--;
		}
	}

	public boolean shouldGlow(Slider slider) {
		return this.signalTick == 8
			|| this.signalTick == 7
			|| this.signalTick == 2
			|| this.signalTick == 1;
	}

	public static void sendSignal(Slider slider) {
		if (!slider.level().isClientSide()) PacketDistributor.sendToPlayersNear(
			(ServerLevel) slider.level(),
			null,
			slider.getX(),
			slider.getY(),
			slider.getZ(),
			50D,
			new SliderSignalPacket.Signal(slider.getId())
		);
	}

	public static void syncDirection(Slider slider, Direction direction) {
		if (!slider.level().isClientSide() && direction != null) PacketDistributor.sendToPlayersNear(
			(ServerLevel) slider.level(),
			null,
			slider.getX(),
			slider.getY(),
			slider.getZ(),
			50D,
			new SliderSignalPacket.DirectionOverride(slider.getId(), direction)
		);
	}

	public static void syncTarget(Slider slider, Entity target) {
		if (!slider.level().isClientSide()) PacketDistributor.sendToPlayersNear(
			(ServerLevel) slider.level(),
			null,
			slider.getX(),
			slider.getY(),
			slider.getZ(),
			50D,
			new SliderSignalPacket.SyncTarget(
				slider.getId(),
				Optional.ofNullable(target).map(Entity::getId)
			)
		);
	}

	public void beginSignal(Slider slider) {
		if (this.getSignalTick() <= 2) {
			if (!this.hasOverriden) this.overrideDirection = null;
			this.setSignalTick(8);
			this.playSound(slider);
		}
	}

	private void playSound(Slider slider) {
		if (ReduxConfig.CLIENT.slider_signal_sfx.get()) slider
			.level()
			.playLocalSound(slider, ReduxSounds.SLIDER_SIGNAL.get(), SoundSource.HOSTILE, 1F, 1F);
	}

	public static @NotNull SliderSignalAttachment get(@NotNull Slider slider) {
		return slider.getData(ReduxDataAttachments.SLIDER_SIGNAL.get());
	}
	public int getSignalTick() {
		return this.signalTick;
	}

	public void setSignalTick(int signalTick) {
		this.signalTick = signalTick;
	}

	public void setOverrideDirection(Slider slider, Direction direction) {
		if (direction == null && !this.hasOverriden || direction == null && this.signalTick == 0)
			return;
		this.overrideDirection = direction;
		this.hasOverriden = true;
	}

	public Direction getOverrideDirection(Slider slider) {
		return this.overrideDirection;
	}

	public void setTarget(Slider slider, @Nullable Entity entity) {
		this.target = entity;
	}

	@Nullable public Entity getTarget(Slider slider) {
		return this.target;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.signalTick, this.hasOverriden, this.overrideDirection, this.target);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (SliderSignalAttachment) obj;
		return this.hasOverriden == that.hasOverriden
			&& this.signalTick == that.signalTick
			&& this.overrideDirection == that.overrideDirection
			&& this.target == that.target;
	}
	
	@Override
	public String toString() {
		return "SliderSignalAttachment[" +
			"signalTick=" + this.signalTick +
			", overrideDirection=" + this.overrideDirection +
			", hasOverriden=" + this.hasOverriden +
			", target=" + this.target +
			']';
	}
}
