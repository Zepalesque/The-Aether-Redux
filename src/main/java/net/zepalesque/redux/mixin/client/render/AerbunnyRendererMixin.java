package net.zepalesque.redux.mixin.client.render;

import com.aetherteam.aether.client.renderer.entity.AerbunnyRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.zepalesque.redux.Redux;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Calendar;

@Mixin(AerbunnyRenderer.class)
public class AerbunnyRendererMixin {
    @Unique
    private static final ResourceLocation SPOOKY_AERBUNNY_TEXTURE = Redux.locate("textures/entity/mobs/aerbunny/spooky_aerbunny.png");

    @Unique
    private boolean halloween = false;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    public void init(EntityRendererProvider.Context context, CallbackInfo ci) {
        Calendar calendar = Calendar.getInstance();
        if ((calendar.get(Calendar.MONTH) == Calendar.OCTOBER && calendar.get(Calendar.DAY_OF_MONTH) >= 30) || (calendar.get(Calendar.MONTH) == Calendar.NOVEMBER && calendar.get(Calendar.DAY_OF_MONTH) <= 1)) {
            this.halloween = true;
        }
    }


    @Inject(method = "getTextureLocation(Lnet/minecraft/world/entity/Entity;)Lnet/minecraft/resources/ResourceLocation;", at = @At(value = "HEAD"), cancellable = true)
    public void getTex(Entity par1, CallbackInfoReturnable<ResourceLocation> cir) {
        if (this.halloween) {
            cir.setReturnValue(SPOOKY_AERBUNNY_TEXTURE);
        }
    }

}
