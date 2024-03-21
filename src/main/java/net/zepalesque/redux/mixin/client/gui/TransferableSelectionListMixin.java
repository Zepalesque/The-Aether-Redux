package net.zepalesque.redux.mixin.client.gui;

import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import net.zepalesque.redux.api.MixinMenuStorage;
import net.zepalesque.redux.client.gui.screen.config.PackConfigMenu;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TransferableSelectionList.class)
public class TransferableSelectionListMixin implements MixinMenuStorage {

    @Unique
    @Nullable
    public PackConfigMenu menu;


    @Override
    public @Nullable PackConfigMenu getMenu() {
        return this.menu;
    }

    @Override
    public void setMenu(PackConfigMenu menu) {
        this.menu = menu;
        // TODO 1.19.2: ensure nothing bad happens because of this being commented out
//        ((MixinMenuStorage) this.get).setMenu(this.menu);
    }
}
