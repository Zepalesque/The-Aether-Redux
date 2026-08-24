package net.zepalesque.redux.mixin.mixins.common.entity;

import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public abstract Vec3 position();
	
	@Shadow
	protected abstract void reapplyPosition();
	
	@Shadow
	@Final
	protected SynchedEntityData entityData;
	
	@Shadow public abstract boolean onGround();
	
	@Shadow public abstract RandomSource getRandom();
}
