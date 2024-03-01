package net.zepalesque.redux.capability.player;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.effect.ReduxEffects;

public class AdrenalineModule implements PlayerTickModule {


    private final Player player;

    private int maxPulseTicks = 0;
    private int currPulseTicks = 0;
    // 0 to 1, determines how strong the effect should be. Also increases the pulse frequency.
    private double adrenalineStrength = 0D;
    private int cooldown = 0;

    public AdrenalineModule(Player plr) {
        this.player = plr;
    }

    public void tick() {
        if (this.player.level().isClientSide()) {
            if (this.cooldown > 0) {
                this.cooldown--;
            }
            if (this.player.hasEffect(ReduxEffects.ADRENALINE_RUSH.get())) {
                MobEffectInstance i = this.player.getEffect(ReduxEffects.ADRENALINE_RUSH.get());
                double amount = Math.min(i.getDuration() / 600D, 1D);
                this.adrenalineStrength = (amount / 3D) * Math.min(3, i.getAmplifier() + 1);
            } else {
                this.adrenalineStrength = 0D;
            }
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
        return this.adrenalineStrength <= 0D ? 0F : sinWaveInterp(delta) * 0.5F * (((float)adrenalineStrength/2F) + 0.5F);
    }

    public float getShaderStrength() {
        return this.adrenalineStrength > 0.0 ? ((float) this.adrenalineStrength * 0.67F) + 0.33F: 0.0F;
    }


    private static float sinWaveInterp(float delta) {
        return (Mth.sin((Mth.PI * 2 * delta)) / 2) + 0.5F;
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

    public boolean cooledDown() {
        return this.cooldown <= 0;
    }

    public void beginCooldown() {
        this.cooldown = 1200;
    }


    public void setMaxPulseTicks(int ticks) {
        this.maxPulseTicks = ticks;
    }




}
