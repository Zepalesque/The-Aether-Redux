package net.zepalesque.redux.block.natural.cloudcap;

import com.aetherteam.aether.block.natural.AetherBushBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;

import java.util.Optional;

public class ShimmerstoolBlock extends AetherBushBlock /*implements BonemealableBlock TODO: spread patch when bonemealed?*/ {

    protected static final VoxelShape SHAPE_CAP = Block.box(1.0D, 3.0D, 1.0D, 15.0D, 7.0D, 15.0D);
    protected static final VoxelShape SHAPE_STEM = Block.box(6.0D, 0.0D, 6.0D, 10.0D, 3.0D, 10.0D);


    public ShimmerstoolBlock(Properties pProperties) {
        super(pProperties);
    }

    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return Shapes.or(SHAPE_CAP, SHAPE_STEM);
    }
}
