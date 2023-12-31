package net.zepalesque.redux.mixin.common.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BlockBehaviour.Properties.class)
public interface BlockPropertiesAccessor {

    @Accessor("canOcclude")
    boolean canOcclude();

    @Accessor("replaceable")
    boolean replaceable();

}
