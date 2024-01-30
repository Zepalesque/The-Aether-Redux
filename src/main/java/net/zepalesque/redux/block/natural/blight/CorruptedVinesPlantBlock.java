package net.zepalesque.redux.block.natural.blight;

import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;

public class CorruptedVinesPlantBlock extends GrowingPlantBodyBlock {
   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);

   public CorruptedVinesPlantBlock(BlockBehaviour.Properties p_154873_) {
      super(p_154873_, Direction.UP, SHAPE, false);
   }
   
   @Override
   public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
      if (!pEntity.level().isClientSide() && pEntity instanceof LivingEntity living && !living.getType().is(ReduxTags.EntityTypes.BLIGHTED_MOBS) && !EquipmentUtil.hasCurio(living, ReduxItems.COCKATRICE_FEATHER.get()) && living.getBoundingBox().intersects(getShape(pState, pLevel, pPos, CollisionContext.of(living)).bounds().move(pPos))) {
         pEntity.makeStuckInBlock(pState, new Vec3(1D, 1D, 1D));
         if (!pLevel.isClientSide && (pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) {
            double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
            double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
            if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
               pEntity.hurt(ReduxDamageTypes.source(pLevel, ReduxDamageTypes.CORRUPTED_VINES), 3.0F);
            }
         }

      }
   }

   @Override
   public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
      return super.isLadder(state, level, pos, entity) && EquipmentUtil.hasCurio(entity, ReduxItems.COCKATRICE_FEATHER.get());
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return (GrowingPlantHeadBlock) Blocks.TWISTING_VINES;
   }
}