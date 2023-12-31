package net.zepalesque.redux.event.listener;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.event.hook.WeaponHooks;

@Mod.EventBusSubscriber(modid = Redux.MODID)
public class WeaponListener {

    @SubscribeEvent
    public static void onArrowHit(ProjectileImpactEvent event) {
        HitResult hitResult = event.getRayTraceResult();
        Projectile projectile = event.getProjectile();
        if (!event.isCanceled()) {
            WeaponHooks.subzeroArrowHit(hitResult, projectile);
        }
    }
}
