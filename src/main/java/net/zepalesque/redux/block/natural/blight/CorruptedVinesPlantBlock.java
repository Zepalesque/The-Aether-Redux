package net.zepalesque.redux.block.natural.blight;

import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrowingPlantBodyBlock;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.event.hook.EquipmentHooks;
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
      if (!pEntity.level().isClientSide() && pEntity instanceof LivingEntity living && !living.getType().is(ReduxTags.EntityTypes.BLIGHTED_MOBS) && living.getBoundingBox().intersects(getShape(pState, pLevel, pPos, CollisionContext.of(living)).bounds().move(pPos))) {
         pEntity.makeStuckInBlock(pState, new Vec3(1D, 1D, 1D));
         if (!pLevel.isClientSide && (pEntity.xOld != pEntity.getX() || pEntity.zOld != pEntity.getZ())) {
            double d0 = Math.abs(pEntity.getX() - pEntity.xOld);
            double d1 = Math.abs(pEntity.getZ() - pEntity.zOld);
            if (d0 >= (double) 0.003F || d1 >= (double) 0.003F) {
               living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600));
               if (living instanceof Player p) {
                  p.displayClientMessage(Component.translatable("gui.aether_redux.funny_speed_boost").withStyle(style -> style.applyFormats(ChatFormatting.AQUA, ChatFormatting.ITALIC)), true);
               }
            }
         }
         if (pLevel.random.nextInt(100) == 0) {
            EquipmentUtil.findFirstCurio(living, stack -> stack.is(ReduxTags.Items.BLIGHTWARDING_ACCESSORIES))
                    .ifPresent(slotResult ->
                            slotResult.stack().hurtAndBreak(1, slotResult.slotContext().entity(),
                                    livingEntity -> CuriosApi.broadcastCurioBreakEvent(slotResult.slotContext())));
         }

      }
   }

   protected GrowingPlantHeadBlock getHeadBlock() {
      return head.get();
   }
}