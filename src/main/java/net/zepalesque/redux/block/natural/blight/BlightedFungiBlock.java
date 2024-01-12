package net.zepalesque.redux.block.natural.blight;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import com.aetherteam.aether.effect.AetherEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.effect.ReduxEffects;

public class                    BlightedFungiBlock extends AetherBushBlock {
    protected static final VoxelShape SHAPE = Block.box(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public BlightedFungiBlock(Properties properties) {
        super(properties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        Vec3 vec3 = pState.getOffset(pLevel, pPos);
        return SHAPE.move(vec3.x, vec3.y, vec3.z);
    }
    public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
        if (pEntity instanceof LivingEntity living && living.getBoundingBox().intersects(getShape(pState, pLevel, pPos, CollisionContext.of(living)).bounds().move(pPos)) && pEntity.getType() != EntityType.FOX && pEntity.getType() != EntityType.BEE) {
            pEntity.makeStuckInBlock(pState, new Vec3(1D, 1D, 1D));
            if (!pLevel.isClientSide && (pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) {
                double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
                double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
                if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                    boolean preventBlight = !living.hasEffect(ReduxEffects.BLIGHTWARD.get()) && !living.getType().is(ReduxTags.EntityTypes.BLIGHTED_MOBS);
                    pEntity.hurt(ReduxDamageTypes.source(pLevel, ReduxDamageTypes.BLIGHTED_FUNGI), preventBlight ? 1.0F : 2.0F);
                    if (preventBlight) {
                        ((LivingEntity) pEntity).addEffect(new MobEffectInstance(AetherEffects.INEBRIATION.get(), 110, 0, false, false));
                    }
                }
            }

        }
    }
}
