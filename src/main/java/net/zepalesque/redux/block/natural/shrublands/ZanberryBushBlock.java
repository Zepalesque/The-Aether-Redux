package net.zepalesque.redux.block.natural.shrublands;

import com.aetherteam.aether.AetherConfig;
import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import org.jetbrains.annotations.Nullable;

public class ZanberryBushBlock extends AetherBushBlock {
    public ZanberryBushBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, false));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AetherBlockStateProperties.DOUBLE_DROPS);
    }

    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (AetherConfig.SERVER.berry_bush_consistency.get() && entity instanceof LivingEntity living && living.getBoundingBox().intersects(getShape(state, level, pos, CollisionContext.of(living)).bounds().move(pos)) && entity.getType() != EntityType.FOX && entity.getType() != EntityType.BEE) {
            entity.makeStuckInBlock(state, new Vec3(0.5, 0.75, 0.5));

            if (!level.isClientSide && (entity.xOld != entity.getX() || entity.zOld != entity.getZ())) {
                double d0 = Math.abs(entity.getX() - entity.xOld);
                double d1 = Math.abs(entity.getZ() - entity.zOld);
                if (d0 >= (double)0.003F || d1 >= (double)0.003F) {
                    entity.hurt(ReduxDamageTypes.ZANBERRY_BUSH, 1.0F);
                }
            }
        }

    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (AetherConfig.SERVER.berry_bush_consistency.get()) {
            Block.dropResources(state, level, pos, null, player, ItemStack.EMPTY);
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            level.setBlock(pos, ReduxBlocks.ZANBERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 3);
            level.gameEvent(GameEvent.BLOCK_CHANGE, pos, Context.of(player, state));
            return InteractionResult.sidedSuccess(level.isClientSide());
        } else {
            return super.use(state, level, pos, player, hand, hit);
        }
    }

    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack tool) {
        super.playerDestroy(level, player, pos, state, blockEntity, tool);
        if (tool.getEnchantmentLevel(Enchantments.SILK_TOUCH) <= 0) {
            level.setBlock(pos, ReduxBlocks.ZANBERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 3);
            if (AetherConfig.SERVER.berry_bush_consistency.get()) {
                level.destroyBlock(pos, true, player);
            }
        }

    }

    public void onBlockExploded(BlockState state, Level level, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, level, pos, explosion);
        level.setBlock(pos, ReduxBlocks.ZANBERRY_BUSH_STEM.get().defaultBlockState().setValue(AetherBlockStateProperties.DOUBLE_DROPS, state.getValue(AetherBlockStateProperties.DOUBLE_DROPS)), 3);
        if (AetherConfig.SERVER.berry_bush_consistency.get()) {
            level.destroyBlock(pos, true);
        }

    }

    public VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        try {
            return AetherConfig.SERVER.berry_bush_consistency.get() ? Shapes.empty() : state.getShape(level, pos);
        } catch (IllegalStateException var6) {
            return state.getShape(level, pos);
        }
    }
}
