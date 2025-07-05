package net.zepalesque.redux.compat.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.block.redstone.LogicatorBlock;
import net.zepalesque.redux.block.util.state.enums.LogicatorMode;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum LogicatorProvider implements IBlockComponentProvider {
    INSTANCE;
    
    public static final ResourceLocation LOC = Redux.locate("logicator");
    
    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockState state = accessor.getBlockState();
        Block block = state.getBlock();
        if (block == ReduxBlocks.LOGICATOR.get()) {
            LogicatorMode mode = state.getValue(LogicatorBlock.MODE);
            
            Component modeInfo = Component.translatable("tooltip.aether_redux.jade.logicator_" + mode.getSerializedName()).withStyle(ChatFormatting.WHITE);
            tooltip.add(Component.translatable("tooltip.jade.mode", modeInfo));
        }
    }
    
    @Override
    public ResourceLocation getUid() {
        return LOC;
    }
}
