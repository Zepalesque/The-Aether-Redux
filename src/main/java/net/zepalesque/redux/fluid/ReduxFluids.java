//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.fluid;

import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.item.ReduxItems;

public class ReduxFluids {
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Redux.MODID);
    public static final RegistryObject<FlowingFluid> SYRUP_STILL = FLUIDS.register("maple_syrup", () -> new SyrupFluid.Still(ReduxFluids.SYRUP_PROPERTIES));
    public static final RegistryObject<FlowingFluid> SYRUP_FLOW = FLUIDS.register("flowing_maple_syrup", () -> new SyrupFluid.Flow(ReduxFluids.SYRUP_PROPERTIES));
    public static final ForgeFlowingFluid.Properties SYRUP_PROPERTIES = (new ForgeFlowingFluid.Properties(ReduxFluidTypes.SYRUP, SYRUP_STILL, SYRUP_FLOW)).slopeFindDistance(2).levelDecreasePerBlock(2).block(ReduxBlocks.MAPLE_SYRUP).bucket(ReduxItems.SYRUP_BUCKET);


}