package net.zepalesque.redux.block.natural.blight;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.zepalesque.redux.block.natural.CustomBoundsFlowerBlock;
import net.zepalesque.redux.block.util.CommonPlantBounds;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.event.hook.EquipmentHooks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;

import java.util.function.Supplier;

public class BlightshadeBlock extends CustomBoundsFlowerBlock {
    public BlightshadeBlock(Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
        super(CommonPlantBounds.BUSH, effectSupplier, pEffectDuration, pProperties);
    }

    @Override
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        super.entityInside(pState, pLevel, pPos, pEntity);
        if (!pEntity.level().isClientSide() && pEntity instanceof Player player && player.getBoundingBox().intersects(getShape(pState, pLevel, pPos, CollisionContext.of(player)).bounds().move(pPos)) && !EquipmentHooks.isImmuneToBlightPlants(player) && !player.getType().is(ReduxTags.EntityTypes.BLIGHTED_MOBS))
        {
            if ((pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) {
                double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
                double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
                if (d0 >= 0.003D || d1 >= 0.003D) {
                    ReduxPlayer.get(player).ifPresent((reduxPlayer) -> reduxPlayer.getBlightshadeModule().blightshade(pPos, getShape(pState, pLevel, pPos, CollisionContext.of(pEntity)).bounds().move(pPos)));
                }
            }
        }
    }
}
