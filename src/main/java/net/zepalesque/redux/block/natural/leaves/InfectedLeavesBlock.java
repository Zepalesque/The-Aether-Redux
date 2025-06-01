package net.zepalesque.redux.block.natural.leaves;

import com.aetherteam.aether.block.AetherBlockStateProperties;
import com.aetherteam.aether.block.natural.AetherDoubleDropsLeaves;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.state.ReduxStates;
import net.zepalesque.redux.item.ReduxItems;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

// TODO: harvesting
public class InfectedLeavesBlock extends AetherDoubleDropsLeaves {

    private final Supplier<? extends ParticleOptions> particle;

    public InfectedLeavesBlock(Supplier<? extends ParticleOptions> particle, Properties properties) {
        super(properties);
        this.particle = particle;
//        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.HAS_SPORES, false));
        
    }

    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource random) {
        super.animateTick(state, level, pos, random);

        if (random.nextInt(10) == 0) {
            BlockPos below = pos.below();
            BlockState blockstate = level.getBlockState(below);
            if (!blockstate.isCollisionShapeFullBlock(level, below)) {
            }
        }
    }
}
