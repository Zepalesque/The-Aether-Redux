package net.zepalesque.redux.mixin.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.extensions.IForgeBlock;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.util.block.QuicksoilFrictionUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(IForgeBlock.class)
public interface IForgeBlockQuicksoilBehaviorMixin {

    /**
     * @author Zepalesque
     * @reason Quicksoil tag thingy lol
     */
    @Overwrite(remap = false)
    default float getFriction(BlockState state, LevelReader level, BlockPos pos, @Nullable Entity entity) {
        if (this instanceof Block block && block.builtInRegistryHolder().is(ReduxTags.Blocks.QUICKSOIL_BEHAVIOR)) {
            return QuicksoilFrictionUtil.getCappedFriction(entity);
        }
        return ((Block)this).getFriction();
    }


}
