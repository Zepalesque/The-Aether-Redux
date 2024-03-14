package net.zepalesque.redux.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

public class PushReactionBlock extends Block {
    private final PushReaction reaction;

    public PushReactionBlock(Properties properties, PushReaction reaction) {
        super(properties);
        this.reaction = reaction;
    }

    @Override
    public PushReaction getPistonPushReaction(BlockState state) {
        return this.reaction;
    }
}
