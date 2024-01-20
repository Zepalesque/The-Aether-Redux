package net.zepalesque.redux.capability.player;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.INBTSerializable;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.effect.ReduxEffects;
import org.jetbrains.annotations.Nullable;

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

            if (this.player.hasEffect(ReduxEffects.ADRENALINE_RUSH.get())) {
                MobEffectInstance i = this.player.getEffect(ReduxEffects.ADRENALINE_RUSH.get());
                double amount = Math.min(i.getDuration() / 600D, 1D);
                this.adrenalineStrength = (amount / 3D) * Math.min(3, i.getAmplifier() + 1);
            } else {
                this.adrenalineStrength = 0D;
            }
            GameRenderer renderer = Minecraft.getInstance().gameRenderer;
            String shaderTarget = getProperShader(this.adrenalineStrength);
            String currentShader = renderer.currentEffect() == null ? null : renderer.currentEffect().getName();
            if (shaderTarget != null && !shaderTarget.equals(currentShader)) {
                renderer.loadEffect(new ResourceLocation(shaderTarget));
            }
            if (shaderTarget == null && isAdrenalineShader(currentShader)) {
                renderer.shutdownEffect();
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

    private static final String HIGH = Redux.MODID + ":shaders/post/adrenaline_high.json";
    private static final String MED = Redux.MODID + ":shaders/post/adrenaline_med.json";
    private static final String LOW = Redux.MODID + ":shaders/post/adrenaline_low.json";

    private static @Nullable String getProperShader(double adrenalineStrength) {
        return adrenalineStrength >= 0.67 ? HIGH : adrenalineStrength >= 0.33 ? MED : adrenalineStrength > 0 ? LOW : null;
    }

    private static boolean isAdrenalineShader(String shader) {
        return shader.equals(HIGH) || shader.equals(MED) || shader.equals(LOW);
    }


    public float getTransparency(float partial) {
        float delta = (currPulseTicks + partial) / maxPulseTicks + 1;
        return this.adrenalineStrength <= 0D ? 0F : sinWaveInterp(delta) * (((float)adrenalineStrength/2F) + 0.5F);
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
