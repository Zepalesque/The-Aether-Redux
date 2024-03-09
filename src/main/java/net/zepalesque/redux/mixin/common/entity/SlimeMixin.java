package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.ServerLevelAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slime.class)
public abstract class SlimeMixin extends MobMixin {
    @Shadow public abstract void refreshDimensions();

    @Shadow @Final
    public static EntityDataAccessor<Integer> ID_SIZE;

    @Shadow public abstract EntityType<? extends Slime> getType();

    @Inject(method = "getSoundPitch", at = @At("RETURN"), cancellable = true)
    protected void getSoundPitch(CallbackInfoReturnable<Float> cir) {
    }

    @Inject(method = "push(Lnet/minecraft/world/entity/Entity;)V", at = @At("HEAD"), cancellable = true)
    protected void redux$push(Entity entity, CallbackInfo ci) {}

    @Inject(method = "finalizeSpawn", at = @At("TAIL"))
    protected void redux$finalize(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, SpawnGroupData spawnData, CompoundTag dataTag, CallbackInfoReturnable<SpawnGroupData> cir) {}

}
