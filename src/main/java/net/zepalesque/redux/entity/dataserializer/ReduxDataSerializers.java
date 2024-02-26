package net.zepalesque.redux.entity.dataserializer;

import com.ibm.icu.util.GenderInfo;
import com.mojang.serialization.Codec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.entity.passive.Mykapod;

public class ReduxDataSerializers {

    public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZERS = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, Redux.MODID);
    public static final RegistryObject<EntityDataSerializer<Mykapod.HideStatus>> HIDE_STATUS = SERIALIZERS.register("hide_status", () -> EntityDataSerializer.simpleEnum(Mykapod.HideStatus.class));


}
