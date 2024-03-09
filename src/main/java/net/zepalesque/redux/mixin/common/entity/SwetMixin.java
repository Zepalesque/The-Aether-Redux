package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.Swet;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ServerLevelAccessor;
import net.zepalesque.redux.config.ReduxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Swet.class)
public abstract class SwetMixin extends SlimeMixin {

    @Shadow public abstract float getWaterDamageScale();

    @Shadow public abstract int getSize();

    @Shadow public abstract EntityDimensions getDimensions(Pose pose);

    @Shadow public abstract void setSize(int size, boolean resetHealth);

    @Unique
    private static final EntityDimensions redux$dimensions = EntityDimensions.scalable(2.04F, 2.04F);

    @Override
    protected void redux$push(Entity entity, CallbackInfo ci) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            ci.cancel();
        } else {
            super.redux$push(entity, ci);
        }
    }

    @WrapOperation(method = "createMobAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;createMobAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"))
    private static AttributeSupplier.Builder attributes(Operation<AttributeSupplier.Builder> original) {
        return original.call().add(Attributes.ATTACK_DAMAGE, 0.5D);
    }
    @Inject(method = "getPassengersRidingOffset", at = @At(value = "HEAD"), cancellable = true)
    public void redux$offset(CallbackInfoReturnable<Double> cir) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            cir.setReturnValue((double) this.getDimensions(Pose.STANDING).height * 0.75D);
        }
    }

    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    public void redux$getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            cir.setReturnValue(redux$dimensions.scale(0.255F * 0.5F * (float) this.getSize() * (1 - (this.getWaterDamageScale() * (10F / 9)))));
        }
    }




    @Inject(method = "setSize", at = @At("HEAD"))
    protected void redux$setSize(int size, boolean resetHealth, CallbackInfo ci) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            int i = Mth.clamp(size, 1, 127);
            this.entityData.set(ID_SIZE, i);
            this.reapplyPosition();
            this.refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double) (i * i));
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) (0.2F + 0.1F * (float) i));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)i * 0.5D);
            if (resetHealth) {
                this.setHealth(this.getMaxHealth());
            }

            this.xpReward = i;
        }
    }

    @Inject(method = "getSize", at = @At("RETURN"), cancellable = true)
    protected void redux$getSize(CallbackInfoReturnable<Integer> cir) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            cir.setReturnValue(this.entityData.get(ID_SIZE));
        }
    }

    @Override
    protected void redux$finalize(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
        super.redux$finalize(level, difficulty, reason, spawnData, dataTag, cir);
        this.setSize(this.getRandom().nextInt(2), true);

    }

    @Override
    protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {
        if (ReduxConfig.COMMON.improved_swet_behavior.get()) {
            cir.setReturnValue(false);
        } else {
            super.redux$isPushable(cir);
        }
    }
}
