//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.fluid;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidInteractionRegistry;
import net.minecraftforge.fluids.FluidType;
import net.zepalesque.redux.block.ReduxBlocks;

import java.util.*;

public class ReduxFluidInteractions {
    private static final Map<FluidType, List<FluidInteractionRegistry.InteractionInformation>> INTERACTIONS = new HashMap();

    public static synchronized void addInteraction(FluidType source, FluidInteractionRegistry.InteractionInformation interaction) {
        INTERACTIONS.computeIfAbsent(source, (s) -> new ArrayList<>()).add(interaction);
    }

    public static boolean canInteract(Level level, BlockPos pos) {
        FluidState state = level.getFluidState(pos);

        for (Direction direction : LiquidBlock.POSSIBLE_FLOW_DIRECTIONS) {
            BlockPos relativePos = pos.relative(direction.getOpposite());
            List<FluidInteractionRegistry.InteractionInformation> interactions = INTERACTIONS.getOrDefault(state.getFluidType(), Collections.emptyList());

            for (FluidInteractionRegistry.InteractionInformation interaction : interactions) {
                if (interaction.predicate().test(level, pos, relativePos, state)) {
                    interaction.interaction().interact(level, pos, relativePos, state);
                    return true;
                }
            }
        }

        return false;
    }

    static {
        addInteraction(ReduxFluidTypes.SYRUP.get(), new FluidInteractionRegistry.InteractionInformation(ForgeMod.WATER_TYPE.get(), (fluidState) -> ReduxBlocks.HARDENED_SYRUP.get().defaultBlockState()));
        addInteraction(ReduxFluidTypes.SYRUP.get(), new FluidInteractionRegistry.InteractionInformation(ForgeMod.LAVA_TYPE.get(), (fluidState) -> ReduxBlocks.CHARCOAL_BLOCK.get().defaultBlockState()));
    }
}