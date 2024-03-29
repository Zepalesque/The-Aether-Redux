package net.zepalesque.redux.mixin.common.entity;

import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import javax.annotation.Nullable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract double getZ();

    @Shadow public abstract double getY();

    @Shadow public abstract double getX();

    @Shadow public abstract Level level();

    @Shadow public abstract Component getName();

    @Shadow public abstract boolean hasCustomName();

    @Shadow @Nullable public abstract Component getCustomName();

    @Shadow public abstract boolean onGround();

    @Shadow protected abstract void reapplyPosition();

    @Shadow @Final protected SynchedEntityData entityData;

    @Shadow public abstract SynchedEntityData getEntityData();

}
