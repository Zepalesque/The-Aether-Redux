package net.zepalesque.redux.block.natural.deep;

import com.aetherteam.aether.block.natural.AercloudBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.zepalesque.redux.capability.living.ReduxLiving;
import net.zepalesque.redux.capability.player.ReduxPlayer;

public class RainbowAercloudBlock extends AercloudBlock {
    public RainbowAercloudBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (entity instanceof LivingEntity living) {
            ReduxLiving.get(living).ifPresent(redux -> {
                if (redux.getRainbowModule().canStart()) {
                    redux.getRainbowModule().begin(state.getShape(world, pos, CollisionContext.of(entity)).bounds().move(pos));
                }
            });
        }
    }
}
