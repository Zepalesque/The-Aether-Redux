package net.zepalesque.redux.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.FluidType.Properties;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.ForgeRegistries.Keys;
import net.zepalesque.redux.Redux;
import org.joml.Vector3f;

public class ReduxFluidTypes {
    public static final ResourceLocation SYRUP_STILL = Redux.locate("block/natural/maple_syrup_still");
    public static final ResourceLocation SYRUP_FLOWING = Redux.locate("block/natural/maple_syrup_flow");
    public static final ResourceLocation SYRUP_OVERLAY = Redux.locate("block/natural/maple_syrup_overlay");
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(Keys.FLUID_TYPES, Redux.MODID);
    public static final RegistryObject<FluidType> SYRUP = register("maple_syrup_fluid", SYRUP_STILL, SYRUP_FLOWING, SYRUP_OVERLAY, Properties.create().descriptionId("block.aether_redux.maple_syrup").canSwim(false).viscosity(2000));

    private static RegistryObject<FluidType> register(String name, ResourceLocation still, ResourceLocation flow, ResourceLocation overlay, FluidType.Properties properties) {
        return FLUID_TYPES.register(name, () -> new ReduxFluid(still, flow, overlay, 0xFFFFFF, new Vector3f((float) 221 / 255, (float) 99 / 255, (float) 55 / 255), properties));
    }

}
