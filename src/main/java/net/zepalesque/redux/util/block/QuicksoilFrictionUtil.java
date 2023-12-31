package net.zepalesque.redux.util.block;

import com.aetherteam.aether.mixin.mixins.common.accessor.BoatAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.config.enums.QuicksoilSetting;
import org.jetbrains.annotations.Nullable;

public class QuicksoilFrictionUtil {
    public static float getCappedFriction(@Nullable Entity entity) {
        if (entity != null) {
            Vec3 motion = entity.getDeltaMovement();
            if (entity.getVehicle() instanceof Boat boat) {
                float deltaRotation = ((BoatAccessor) boat).aether$getDeltaRotation();
                if (deltaRotation > 10.0F) {
                    ((BoatAccessor) boat).aether$setDeltaRotation(10.0F);
                } else if (deltaRotation < -10.0F) {
                    ((BoatAccessor) boat).aether$setDeltaRotation(-10.0F);
                }
            }
            double maxMotion = ReduxConfig.COMMON.quicksoil_movement_system.get() == QuicksoilSetting.genesis ? 2.0D :  1.0D;
            if (ReduxConfig.COMMON.quicksoil_movement_system.get() != QuicksoilSetting.highlands & (Math.abs(motion.x()) > maxMotion || Math.abs(motion.z()) > maxMotion)) {
                return entity instanceof Boat ? 0.9F : 0.99F;
            }
        }
        if (entity instanceof Boat)
        {
            return ReduxConfig.COMMON.quicksoil_movement_system.get() == QuicksoilSetting.genesis ? 1.0F : ReduxConfig.COMMON.quicksoil_movement_system.get() == QuicksoilSetting.classic ? 0.96F : 0.6F;
        }
        return ReduxConfig.COMMON.quicksoil_movement_system.get() == QuicksoilSetting.genesis ? 1.23F : ReduxConfig.COMMON.quicksoil_movement_system.get() == QuicksoilSetting.classic ? 1.1F : 0.6F;
    }
}
