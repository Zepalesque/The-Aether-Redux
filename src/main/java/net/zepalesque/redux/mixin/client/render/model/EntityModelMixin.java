package net.zepalesque.redux.mixin.client.render.model;

import com.aetherteam.aether.entity.passive.Moa;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityModel.class)
public class EntityModelMixin<T extends Entity> {

    @Inject(method = "prepareMobModel", at = @At(value = "TAIL"), remap = false)
    public void prepareMobModel(T entity, float limbSwing, float limbSwingAmount, float partialTicks, CallbackInfo ci) {}

}
