package net.zepalesque.redux.mixin.common.block;

import com.aetherteam.aether.block.FrictionCapped;
import com.aetherteam.aether.mixin.mixins.common.accessor.BoatAccessor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.util.block.QuicksoilFrictionUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(FrictionCapped.class)
public interface FrictionCappedMixin {

    /**
     * @author Zepalesque
     * @reason null
     */
    @Overwrite(remap = false)
    default float getCappedFriction(@Nullable Entity entity, float defaultFriction) {
        if (this instanceof Block block && block.builtInRegistryHolder().is(ReduxTags.Blocks.QUICKSOIL_BEHAVIOR)) {
            return QuicksoilFrictionUtil.getCappedFriction(entity);
        }
        if (entity != null) {
            Vec3 motion = entity.getDeltaMovement();
            if (entity instanceof Boat boat) {
                float deltaRotation = ((BoatAccessor) boat).aether$getDeltaRotation();
                if (deltaRotation > 25.0F) {
                    ((BoatAccessor) boat).aether$setDeltaRotation(25.0F);
                } else if (deltaRotation < -25.0F) {
                    ((BoatAccessor) boat).aether$setDeltaRotation(-25.0F);
                }
            }
            if (Math.abs(motion.x()) > 1.0 || Math.abs(motion.z()) > 1.0) {
                return 0.99F;
            }
        }
        return defaultFriction;    }


}
