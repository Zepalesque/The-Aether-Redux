package net.zepalesque.redux.mixin.mixins.common.entity;

import com.aetherteam.aether.entity.monster.Swet;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;
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

//    @Shadow public abstract float getWaterDamageScale();

    @Shadow public abstract int getSize();
    
    @Shadow public abstract void setSize(int size, boolean resetHealth);

    @Shadow private boolean wasOnGround;
    
    @Shadow
    public abstract EntityDimensions getDefaultDimensions(Pose pose);
    
    @Unique
    private static final EntityDimensions redux$dimensions = EntityDimensions.scalable(2.04F, 2.04F);

    @Override
    protected void redux$doPush(Entity entity, CallbackInfo ci) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get()) ci.cancel();
        else super.redux$doPush(entity, ci);
    }

    @WrapOperation(method = "createMobAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;createMobAttributes()Lnet/minecraft/world/entity/ai/attributes/AttributeSupplier$Builder;"))
    private static AttributeSupplier.Builder attributes(Operation<AttributeSupplier.Builder> original) {
        return original.call().add(Attributes.ATTACK_DAMAGE, 0.5D).add(Attributes.ATTACK_KNOCKBACK, 0.0D);
    }
    @WrapOperation(method = "getPassengerRidingPosition", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;add(DDD)Lnet/minecraft/world/phys/Vec3;"))
    public Vec3 redux$offset(Vec3 instance, double x, double y, double z, Operation<Vec3> original) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get()) {
            var height = (double) this.getDefaultDimensions(Pose.STANDING).height() * 0.75D * 0.5;
            return original.call(instance, 0d, height, 0d);
        } else return original.call(instance, x, y, z);
    }
    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void redux$interact(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get()) {
	        var i = player.getItemInHand(hand);
            var self = (Swet) (Object) this;
            if (SwetHooks.canGrow(self, i)) {
                if (!player.isCreative()) i.shrink(1);
                this.setSize(this.getSize() + 1, false);
                cir.setReturnValue(InteractionResult.SUCCESS);
            }

            if (!SwetHooks.canBeControlled(self)) cir.setReturnValue(InteractionResult.PASS);
        }
    }
    @Inject(method = "tick", at = @At("HEAD"))
    public void redux$tick(CallbackInfo ci) {
        var self = (Swet) (Object) this;
	    if (ReduxConfig.SERVER.pl_swet_behavior.get() && this.onGround() && !this.wasOnGround) {
		    var i = SwetHooks.getTrueScale(self);
		    @Nullable var particle = SwetHooks.getSquelchParticles(self);
		    
		    if (particle != null) for (var j = 0; j < i * 8; ++j) {
			    var f = self.getRandom().nextFloat() * ((float) Math.PI * 2F);
			    var f1 = self.getRandom().nextFloat() * 0.5F + 0.5F;
			    var f2 = Mth.sin(f) * (float) i * 0.5F * f1;
			    var f3 = Mth.cos(f) * (float) i * 0.5F * f1;
			    self.level().addParticle(particle, self.getX() + (double) f2, self.getY(), self.getZ() + (double) f3, 0.0D, 0.0D, 0.0D);
			    
		    }
	    }
    }

    @Inject(method = "getMountJumpStrength", at = @At("HEAD"), cancellable = true, remap = false)
    public void getMountJumpStrength(CallbackInfoReturnable<Double> cir) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get())
	        cir.setReturnValue(SwetHooks.getTrueScale((Swet) (Object) this) * 0.25F);
    }

    @Override
    public void redux$getRiddenSpeed(Player player, CallbackInfoReturnable<Float> cir) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get())
	        cir.setReturnValue((float) (this.getSpeed() + SwetHooks.getTrueScale((Swet) (Object) this)/* / 10F*/));
    }

    @Inject(method = "getDefaultDimensions", at = @At("HEAD"), cancellable = true)
    public void redux$getDimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get())
	        cir.setReturnValue(redux$dimensions.scale(0.255F * (float) SwetHooks.getTrueScale((Swet) (Object) this)));
    }

    @Inject(method = "canSpawnSplashParticles", at = @At("HEAD"), cancellable = true, remap = false)
    public void redux$canSpawnSplashParticles(CallbackInfoReturnable<Boolean> cir) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get()) cir.setReturnValue(false);
    }




    @Inject(method = "setSize", at = @At("HEAD"))
    protected void redux$setSize(int size, boolean resetHealth, CallbackInfo ci) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get()) {
	        var i = Mth.clamp(size, 1, 127);
            this.entityData.set(this.getIdSize(), i);
            this.reapplyPosition();
            this.refreshDimensions();
            var max_health = this.getAttribute(Attributes.MAX_HEALTH);
            if (max_health != null) max_health.setBaseValue(i * i);
            var speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null) speed.setBaseValue(0.3F + 0.05F * (float) i);
            var dmg = this.getAttribute(Attributes.ATTACK_DAMAGE);
            if (dmg != null) dmg.setBaseValue((double)i * 0.5D);
            if (resetHealth) this.setHealth(this.getMaxHealth());

            this.xpReward = i;
        }
    }

    @Inject(method = "getSize", at = @At("RETURN"), cancellable = true)
    protected void redux$getSize(CallbackInfoReturnable<Integer> cir) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get())
	        cir.setReturnValue(this.entityData.get(this.getIdSize()));
    }

    @Override
    protected void redux$finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType spawnType, SpawnGroupData spawnGroupData, CallbackInfoReturnable<SpawnGroupData> cir) {
//        RandomSource randomsource = level.getRandom();
//        this.setSize(randomsource.nextInt(2), true);
    }

    @Override
    protected void redux$isPushable(CallbackInfoReturnable<Boolean> cir) {
        if (ReduxConfig.SERVER.pl_swet_behavior.get()) cir.setReturnValue(false);
        else super.redux$isPushable(cir);
    }
}
