//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.fluid;

import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import org.jetbrains.annotations.NotNull;

public abstract class SyrupFluid extends ForgeFlowingFluid {
    protected SyrupFluid(ForgeFlowingFluid.Properties properties) {
        super(properties);
    }

    public int getAmount(FluidState fluidState) {
        return fluidState.getValue(LEVEL);
    }

    public boolean isSource(@NotNull FluidState fluidState) {
        return false;
    }

    public static class Still extends SyrupFluid {
        protected Still(ForgeFlowingFluid.Properties properties) {
            super(properties);
        }

        public int getAmount(FluidState p_76485_) {
            return 8;
        }

        public boolean isSource(@NotNull FluidState p_76483_) {
            return true;
        }
    }

    public static class Flow extends SyrupFluid {
        public Flow(ForgeFlowingFluid.Properties properties) {
            super(properties);
        }

        protected void createFluidStateDefinition(StateDefinition.@NotNull Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }
    }
}