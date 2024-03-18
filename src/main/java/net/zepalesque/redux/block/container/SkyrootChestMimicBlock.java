package net.zepalesque.redux.block.container;

import com.aetherteam.aether.block.dungeon.ChestMimicBlock;
import com.aetherteam.aether.client.AetherSoundEvents;
import com.aetherteam.aether.entity.monster.dungeon.Mimic;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.zepalesque.redux.blockentity.SkyrootChestMimicBlockEntity;
import net.zepalesque.redux.entity.ReduxEntityTypes;

public class SkyrootChestMimicBlock extends ChestMimicBlock {
    public SkyrootChestMimicBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SkyrootChestMimicBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!SkyrootChestBlock.isChestBlockedAt(level, pos) && !level.isClientSide()) {
            this.spawnMimic(state, level, pos);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean flag) {
        this.spawnMimic(state, level, pos);
    }

    private void spawnMimic(BlockState state, Level level, BlockPos pos) {
        Mimic mimic = ReduxEntityTypes.SKYROOT_MIMIC.get().create(level);
        if (mimic != null) {
            Direction direction = state.getValue(FACING);
            float angle = direction.toYRot();
            mimic.absMoveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, angle, 0.0F);
            mimic.setYHeadRot(angle);
            level.addFreshEntity(mimic);
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            level.playSound(null, pos, AetherSoundEvents.BLOCK_CHEST_MIMIC_OPEN.get(), SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
            mimic.spawnAnim();
        }
    }
}
