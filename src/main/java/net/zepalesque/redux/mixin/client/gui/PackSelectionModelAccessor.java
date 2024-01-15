package net.zepalesque.redux.mixin.client.gui;

import net.minecraft.client.gui.screens.packs.PackSelectionModel;
import net.minecraft.server.packs.repository.Pack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(PackSelectionModel.class)
public interface PackSelectionModelAccessor {

    @Accessor("selected")
    List<Pack> getPacks();

}
