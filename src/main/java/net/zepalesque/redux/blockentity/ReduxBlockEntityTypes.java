package net.zepalesque.redux.blockentity;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;

public class ReduxBlockEntityTypes {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Redux.MODID);

    public static final RegistryObject<BlockEntityType<HolystoneFurnaceBlockEntity>> HOLYSTONE_FURNACE = BLOCK_ENTITY_TYPES.register("holystone_furnace", () ->
            BlockEntityType.Builder.of(HolystoneFurnaceBlockEntity::new, ReduxBlocks.HOLYSTONE_FURNACE.get()).build(null));

    public static final RegistryObject<BlockEntityType<SkyrootChestBlockEntity>> SKYROOT_CHEST = BLOCK_ENTITY_TYPES.register("skyroot_chest", () ->
            BlockEntityType.Builder.of(SkyrootChestBlockEntity::new, ReduxBlocks.SKYROOT_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<TrappedSkyrootChestBlockEntity>> TRAPPED_SKYROOT_CHEST = BLOCK_ENTITY_TYPES.register("trapped_skyroot_chest", () ->
            BlockEntityType.Builder.of(TrappedSkyrootChestBlockEntity::new, ReduxBlocks.TRAPPED_SKYROOT_CHEST.get()).build(null));

    public static final RegistryObject<BlockEntityType<SkyrootChestMimicBlockEntity>> SKYROOT_CHEST_MIMIC = BLOCK_ENTITY_TYPES.register("skyroot_chest_mimic", () ->
            BlockEntityType.Builder.of(SkyrootChestMimicBlockEntity::new, ReduxBlocks.SKYROOT_CHEST_MIMIC.get()).build(null));

}
