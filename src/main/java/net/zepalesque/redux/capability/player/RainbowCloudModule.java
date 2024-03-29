package net.zepalesque.redux.capability.player;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.util.level.LevelUtil;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class RainbowCloudModule implements PlayerTickModule {

    private int boostTimer;
    private final LivingEntity player;
    private boolean inProgress;
    private static final Predicate<BlockState> stateTest = state -> state.is(ReduxBlocks.RAINBOW_AERCLOUD.get());

    public RainbowCloudModule(LivingEntity plr) {
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
            if (!LevelUtil.isBlockInAABB(this.player.getBoundingBox(), this.player.level(), stateTest, false)) {
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
    }
    public void cancel(boolean alterTimer) {
        if (alterTimer) {
            this.boostTimer = 0;
        }
        this.inProgress = false;
    }

    public boolean canStart() {
        return !this.inProgress;
    }

    @Override
    public LivingEntity getPlayer() {
        return this.player;
    }
}
