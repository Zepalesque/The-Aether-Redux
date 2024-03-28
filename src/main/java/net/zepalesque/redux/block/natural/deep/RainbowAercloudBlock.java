package net.zepalesque.redux.block.natural.deep;

import com.aetherteam.aether.block.natural.AercloudBlock;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;

public class RainbowAercloudBlock extends AercloudBlock {
    public RainbowAercloudBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        if (entity instanceof Player p && !p.level().isClientSide()) {
            ReduxPlayer.get(p).ifPresent(redux -> {
                if (!redux.getRainbowModule().alreadyCharging()) {
                    redux.getRainbowModule().begin(state.getShape(world, pos, CollisionContext.of(entity)).bounds().move(pos));
                }
            });
        }
    }
}
