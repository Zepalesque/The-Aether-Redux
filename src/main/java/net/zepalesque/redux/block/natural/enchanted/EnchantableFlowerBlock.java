package net.zepalesque.redux.block.natural.enchanted;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.zepalesque.redux.block.util.ReduxStates;

import java.util.function.Supplier;

public class EnchantableFlowerBlock extends FlowerBlock {
    public EnchantableFlowerBlock(Supplier<MobEffect> effectSupplier, int pEffectDuration, Properties pProperties) {
        super(effectSupplier, pEffectDuration, pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(ReduxStates.ENCHANTED, false));
    }
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ReduxStates.ENCHANTED);
    }
}
