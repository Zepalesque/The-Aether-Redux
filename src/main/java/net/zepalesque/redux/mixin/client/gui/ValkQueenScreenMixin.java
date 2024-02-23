package net.zepalesque.redux.mixin.client.gui;

import com.aetherteam.aether.client.gui.component.dialogue.DialogueChoiceComponent;
import com.aetherteam.aether.client.gui.screen.ValkyrieQueenDialogueScreen;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.network.chat.Component;
import net.zepalesque.redux.item.ReduxItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ValkyrieQueenDialogueScreen.class)
public abstract class ValkQueenScreenMixin extends ScreenMixin {

    @Shadow protected abstract void finishChat(byte interactionID);

    @ModifyVariable(method = "lambda$init$4", at = @At(value = "LOAD"), index = 3)
    private DialogueChoiceComponent startFightChoice$modify(DialogueChoiceComponent original) {
        boolean hasGrandMedal = this.getMinecraft().player.getInventory().countItem(ReduxItems.GRAND_VICTORY_MEDAL.get()) >= 1 || EquipmentUtil.hasCurio(this.getMinecraft().player, ReduxItems.GRAND_VICTORY_MEDAL.get());
        return hasGrandMedal ? new DialogueChoiceComponent(Component.translatable("gui.aether_redux.player.dialog.has_grand_medal"), (button1) -> this.finishChat((byte)1)) : original;
    }
}
