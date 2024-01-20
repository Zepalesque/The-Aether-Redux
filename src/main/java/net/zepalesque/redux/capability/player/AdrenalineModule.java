package net.zepalesque.redux.capability.player;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

public class AdrenalineModule implements INBTSerializable, PlayerTickModule {

    private final Player player;

    private int maxPulseTicks = 0;
    private int currPulseTicks = 0;
    // 0 to 1, determines how strong the effect should be. Also increases the pulse frequency.
    private double adrenalineStrength = 0D;

    public AdrenalineModule(Player plr) {
        this.player = plr;
    }

    public void tick() {
        if (this.player.level().isClientSide()) {
            if (this.adrenalineStrength > 0) {
                this.maxPulseTicks = Mth.lerpInt((float) this.adrenalineStrength, 20, 10);
            } else {
                this.setMaxPulseTicks(0);
            }

            if (this.maxPulseTicks > 0) {
                if (this.currPulseTicks < this.maxPulseTicks) {
                    this.currPulseTicks++;
                } else {
                    this.currPulseTicks = 0;
                    doPulse(this.adrenalineStrength);
                }
            }
        }
    }

    public float getTransparency(float partial) {
        float delta = (currPulseTicks + partial) / maxPulseTicks + 1;
        return sinWaveInterp(delta);
    }


    private static float sinWaveInterp(float delta) {
        return (Mth.sin(Mth.PI * (delta + 0.5F)) / 2) + 0.5F;
    }



    private static SoundEvent getHeartbeat(double strength) {
        return strength >= 0.67 ? ReduxSoundEvents.HEARTBEAT_FAST.get() : strength >= 0.33 ? ReduxSoundEvents.HEARTBEAT_MED.get() : ReduxSoundEvents.HEARTBEAT_SLOW.get();
    }

    public Player getPlayer() {
        return this.player;
    }

    protected void doPulse(double strength) {
        this.player.level().playSound(this.player, this.player.getX(), this.player.getY(), this.player.getZ(), getHeartbeat(strength), SoundSource.PLAYERS, 0.8F, 1F);
    }


    public void setMaxPulseTicks(int ticks) {
        this.maxPulseTicks = ticks;
    }




    @Override
    public Tag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        return tag;
    }

    @Override
    public void deserializeNBT(Tag nbt) {
        if (nbt instanceof CompoundTag tag) {

        }
    }
}
