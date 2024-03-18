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
import net.zepalesque.redux.event.hook.EquipmentHooks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public class CorruptedVinesPlantBlock extends GrowingPlantBodyBlock {
   public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 16.0D, 12.0D);
   private final Supplier<? extends GrowingPlantHeadBlock> head;

   public CorruptedVinesPlantBlock(BlockBehaviour.Properties properties, Supplier<? extends GrowingPlantHeadBlock> head) {
      super(properties, Direction.UP, SHAPE, false);
      this.head = head;
   }

   @Override
   public void entityInside(BlockState pState, Level pLevel, BlockPos pPos, Entity pEntity) {
      if (!pEntity.level.isClientSide() && pEntity instanceof LivingEntity living && !living.getType().is(ReduxTags.EntityTypes.BLIGHTED_MOBS) && living.getBoundingBox().intersects(getShape(pState, pLevel, pPos, CollisionContext.of(living)).bounds().move(pPos))) {
         if (!EquipmentHooks.isImmuneToBlightPlants(living)) {
            pEntity.makeStuckInBlock(pState, new Vec3(1D, 1D, 1D));
            if (!pLevel.isClientSide && (pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) {
               double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
               double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
               if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
                  pEntity.hurt(ReduxDamageTypes.CORRUPTED_VINES, 3.0F);
               }
            }
         }
         if (pLevel.random.nextInt(100) == 0) {
            CuriosApi.getCuriosHelper().findFirstCurio(living, stack -> stack.is(ReduxTags.Items.BLIGHTWARDING_ACCESSORIES))
                    .ifPresent(slotResult ->
                            slotResult.stack().hurtAndBreak(1, slotResult.slotContext().entity(),
                                    livingEntity -> CuriosApi.getCuriosHelper().onBrokenCurio(slotResult.slotContext())));
         }

      }
   }

   @Override
   public boolean isLadder(BlockState state, LevelReader level, BlockPos pos, LivingEntity entity) {
      return super.isLadder(state, level, pos, entity) && EquipmentHooks.isImmuneToBlightPlants(entity);
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return head.get();
   }
}