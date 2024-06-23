package net.zepalesque.redux.mixin.mixins.common.accessor;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

@Mixin(ModConfigSpec.ConfigValue.class)
public interface ConfigValueAccessor {
    @Accessor("spec")
    ModConfigSpec redux$getSpec();

}
