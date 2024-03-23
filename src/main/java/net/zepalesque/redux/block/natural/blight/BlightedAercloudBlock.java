package net.zepalesque.redux.block.natural.blight;

import com.aetherteam.aether.block.natural.AercloudBlock;
import com.aetherteam.aether.effect.AetherEffects;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;

public class BlightedAercloudBlock extends AercloudBlock {
    public BlightedAercloudBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void entityInside(BlockState state, Level world, BlockPos pos, Entity entity) {
        super.entityInside(state, world, pos, entity);
        MobEffect effect = AetherEffects.INEBRIATION.get();
        if (entity instanceof LivingEntity livingEntity && !EquipmentUtil.hasCurio(livingEntity, ReduxItems.FEATHER_OF_WARDING.get())) {
            if (livingEntity.tickCount % (livingEntity.hasEffect(effect) ? 10 : 20) == 0) {
                if (livingEntity.getType().is(ReduxTags.EntityTypes.BLIGHTED_MOBS)) {
                    livingEntity.heal(1.0F);
                } else {
                    livingEntity.hurt(ReduxDamageTypes.source(world, ReduxDamageTypes.BLIGHT), 1.5F);
                    livingEntity.addEffect(new MobEffectInstance(effect, 1510, 0));
                }
            }
        }
    }
}
