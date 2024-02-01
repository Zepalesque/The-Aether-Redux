
package net.zepalesque.redux.entity.passive;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Map;
import java.util.function.IntFunction;

public class Mykapod extends PathfinderMob {
    public Mykapod(EntityType<? extends Mykapod> entityType, Level level) {
        super(entityType, level);
    }

    private static final EntityDataAccessor<Boolean> IS_HIDING = SynchedEntityData.defineId(Mykapod.class, EntityDataSerializers.BOOLEAN);


    /**
     * Should be a value from 0 to 60. 0 to 30 represent the progress of the hiding animation, 30 to 60 represent it exiting the shell.
     */
    @OnlyIn(Dist.CLIENT)
    public int hideAnim, prevHideAnim = 0;

    @OnlyIn(Dist.CLIENT)
    private boolean wasHiding;

    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 2.0D));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0D).add(Attributes.MOVEMENT_SPEED, (double)0.1F);
    }

    @Override
    public void tick() {
        this.wasHiding = this.isHiding();
        this.prevHideAnim = this.hideAnim;
        super.tick();

        // Handle animation stuff
        if (this.level().isClientSide()) {
            if ((this.isHiding() && this.hideAnim < 30)) {
                this.hideAnim++;
            }
            if (((!this.isHiding() && this.wasHiding) || (this.hideAnim > 30 && this.hideAnim < 60))) {
                this.hideAnim = 31;
                this.prevHideAnim = 30;
            }
            if (!this.isHiding() && this.hideAnim >= 60) {
                this.hideAnim = 0;
                this.prevHideAnim = 0;
            }
        }
    }



    public boolean isHiding() {
        return this.getEntityData().get(IS_HIDING);
    }

    public void setHiding(boolean hiding) {
        this.getEntityData().set(IS_HIDING, hiding);
    }


    protected SoundEvent getAmbientSound() {
        return SoundEvents.COW_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.COW_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.COW_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState block) {
        this.playSound(SoundEvents.COW_STEP, 0.15F, 1.0F);
    }


/**
     * Returns the volume for the sounds this mob makes.
     */

    protected float getSoundVolume() {
        return 0.4F;
    }


}
