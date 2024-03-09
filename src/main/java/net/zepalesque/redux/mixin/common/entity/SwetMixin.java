package net.zepalesque.redux.mixin.common.entity;

import com.aetherteam.aether.entity.monster.Swet;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.event.hook.SwetHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Swet.class)
public abstract class SwetMixin extends SlimeMixin {

    @Shadow public abstract float getWaterDamageScale();

    @Shadow public abstract int getSize();

    @Shadow public abstract EntityDimensions getDimensions(Pose pose);

    @Shadow public abstract void setSize(int size, boolean resetHealth);

    @Shadow private boolean wasOnGround;
    @Unique
    private static final EntityDimensions redux$dimensions = EntityDimensions.scalable(2.04F, 2.04F);

    @Override
    protected void redux$doPush(Entity entity, CallbackInfo ci) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            ci.cancel();
        } else {
            super.redux$doPush(entity, ci);
        }
    }

    @WrapOperation(method = "createMobAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;createMobAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"))
    private static AttributeSupplier.Builder attributes(Operation<AttributeSupplier.Builder> original) {
        return original.call().add(Attributes.ATTACK_DAMAGE, 0.5D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
    }
    @Inject(method = "getPassengersRidingOffset", at = @At(value = "HEAD"), cancellable = true)
    public void redux$offset(CallbackInfoReturnable<Double> cir) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            cir.setReturnValue((double) this.getDimensions(Pose.STANDING).height * 0.75D);
        }
    }
    @Inject(method = "mobInteract", at = @At(value = "HEAD"), cancellable = true)
    public void redux$interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            ItemStack i = player.getItemInHand(hand);
            if (SwetHooks.shouldGrow(i, this.getType())) {
                if (!player.isCreative()) {
                    i.shrink(1);
                }
                this.
                this.setSize(this.getSize() + 1, false);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }

            if (!SwetHooks.canBeControlled((Swet) (Object) this)) {
                cir.setReturnValue(InteractionResult.PASS);
            }
        }
    }
    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void redux$tick(CallbackInfo ci) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            if (this.onGround() && !this.wasOnGround) {
                double i = SwetHooks.getTrueScale((Swet) (Object) this);
                @Nullable ParticleOptions particle = SwetHooks.getSquelchParticles((Swet) (Object) this);

                if (particle != null) {
                    for (int j = 0; j < i * 8; ++j) {
                        float f = ((Swet) (Object) this).getRandom().nextFloat() * ((float) Math.PI * 2F);
                        float f1 = ((Swet) (Object) this).getRandom().nextFloat() * 0.5F + 0.5F;
                        float f2 = Mth.sin(f) * (float) i * 0.5F * f1;
                        float f3 = Mth.cos(f) * (float) i * 0.5F * f1;
                        ((Swet) (Object) this).level().addParticle(particle, ((Swet) (Object) this).getX() + (double) f2, ((Swet) (Object) this).getY(), ((Swet) (Object) this).getZ() + (double) f3, 0.0D, 0.0D, 0.0D);

                    }
                }
            }
        }
    }

    @Inject(method = "getMountJumpStrength", at = @At(value = "HEAD"), cancellable = true, remap = false)
    public void getMountJumpStrength(CallbackInfoReturnable<Double> cir) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            cir.setReturnValue(SwetHooks.getTrueScale((Swet) (Object) this) * 0.25F);
        }
    }

    @Inject(method = "getDimensions", at = @At("HEAD"), cancellable = true)
    public void redux$getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            cir.setReturnValue(redux$dimensions.scale(0.255F * (float) SwetHooks.getTrueScale((Swet) (Object) this)));
        }
    }

    @Inject(method = "canSpawnSplashParticles", at = @At("HEAD"), cancellable = true, remap = false)
    public void redux$canSpawnSplashParticles(CallbackInfoReturnable<Boolean> cir) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            cir.setReturnValue(false);
        }
    }




    @Inject(method = "setSize", at = @At("HEAD"))
    protected void redux$setSize(int size, boolean resetHealth, CallbackInfo ci) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            int i = Mth.clamp(size, 1, 127);
            this.entityData.set(ID_SIZE, i);
            this.reapplyPosition();
            this.refreshDimensions();
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue((double) (i * i));
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue((double) (0.3F + 0.05F * (float) i));
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue((double)i * 0.5D);
            if (resetHealth) {
                this.setHealth(this.getMaxHealth());
            }

            this.xpReward = i;
        }
    }

    @Inject(method = "getSize", at = @At("RETURN"), cancellable = true)
    protected void redux$getSize(CallbackInfoReturnable<Integer> cir) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            cir.setReturnValue(this.entityData.get(ID_SIZE));
        }
    }

    @Override
    protected void redux$finalize(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {
//        RandomSource randomsource = level.getRandom();
//        this.setSize(randomsource.nextInt(2), true);
    }

    @Override
    protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {
        if (ReduxConfig.COMMON.pl_swet_behavior.get()) {
            cir.setReturnValue(false);
        } else {
            super.redux$isPushable(cir);
        }
    }
}
