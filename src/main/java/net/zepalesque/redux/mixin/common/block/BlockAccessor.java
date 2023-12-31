package net.zepalesque.redux.mixin.common.block;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.class)
public interface BlockAccessor {


    @Accessor("soundType")
    SoundType getSoundType();

    @Accessor("soundType")
    void setSoundType(SoundType soundType);


}
