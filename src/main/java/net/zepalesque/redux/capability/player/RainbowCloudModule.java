package net.zepalesque.redux.capability.player;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import org.jetbrains.annotations.Nullable;

public class RainbowCloudModule implements PlayerTickModule {

    private int boostTimer;
    private final Player player;
    private boolean inProgress;
    private @Nullable AABB bounds;

    public RainbowCloudModule(Player plr) {
        this.player = plr;
    }

    @Override
    public void tick() {
        if (this.boostTimer == 23) {
            if (!this.player.level().isClientSide()) {
                this.player.level().playSound(null, this.player.blockPosition(), ReduxSoundEvents.RAINBOW_CLOUD_END.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
            }
        }
        if (this.boostTimer == 15) {
            this.player.addDeltaMovement(new Vec3(0, 5, 0));

        }
            if (this.bounds == null || !this.player.getBoundingBox().intersects(this.bounds)) {
            this.cancel(true);
        }
        if (this.boostTimer > 0) {
            this.boostTimer--;
        } else {
            this.cancel(false);
        }
    }

    public void begin(AABB bounds) {
        this.boostTimer = 45;
        if (!this.player.level().isClientSide()) {
            this.player.level().playSound(null, this.player.blockPosition(), ReduxSoundEvents.RAINBOW_CLOUD_START.get(), SoundSource.BLOCKS, 0.8F, 1.0F);
        }
        this.inProgress = true;
        this.bounds = bounds;
    }
    public void cancel(boolean alterTimer) {
        if (alterTimer) {
            this.boostTimer = 0;
        }
        this.inProgress = false;
        this.bounds = null;
    }

    public boolean canStart() {
        return !this.inProgress;
    }

    @Override
    public Player getPlayer() {
        return this.player;
    }
}
