package net.zepalesque.redux.item;

import io.wispforest.accessories.api.AccessoriesAPI;
import io.wispforest.accessories.api.Accessory;
import net.minecraft.world.item.Item;

public class ReduxItemBuilders {
    protected static <T extends Item & Accessory> void registerAccessory(T accessory) {
        AccessoriesAPI.registerAccessory(accessory, accessory);
    }
}
