package net.zepalesque.redux.world.structure;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;

public class ReduxStructureTypes {
    public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, Redux.MODID);
    public static final RegistryObject<StructureType<EmptyStructure>> EMPTY = STRUCTURE_TYPES.register("empty", () -> () -> EmptyStructure.CODEC);
}
