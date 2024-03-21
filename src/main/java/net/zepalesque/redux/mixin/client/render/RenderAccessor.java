package net.zepalesque.redux.mixin.client.render;

import net.minecraft.client.renderer.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityRenderer.class)
public interface RenderAccessor {

    @Accessor("shadowRadius")
    void setShadowRadius(float radius);
}
