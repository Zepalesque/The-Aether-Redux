package net.zepalesque.redux.mixin.client.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin<T extends Entity>  {
    @Shadow protected float shadowRadius;

}
