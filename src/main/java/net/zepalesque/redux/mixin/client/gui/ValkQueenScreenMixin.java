package net.zepalesque.redux.mixin.client.gui;

import com.aetherteam.aether.client.gui.component.dialogue.DialogueChoiceComponent;
import com.aetherteam.aether.client.gui.screen.ValkyrieQueenDialogueScreen;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.zepalesque.redux.item.ReduxItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// TODO: Investigate a better solution
@Mixin(ValkyrieQueenDialogueScreen.class)
public abstract class ValkQueenScreenMixin extends ScreenMixin {

    @Shadow public abstract MutableComponent buildDialogueChoice(String key);

    @Shadow protected abstract void finishChat(byte interactionID);

    @Shadow protected abstract void setDialogueAnswer(Component component);

    @Shadow public abstract void setupDialogueChoices(DialogueChoiceComponent... options);

    @Redirect(method = "init", at = @At(value = "INVOKE", target = "Lcom/aetherteam/aether/client/gui/screen/ValkyrieQueenDialogueScreen;setupDialogueChoices([Lcom/aetherteam/aether/client/gui/component/dialogue/DialogueChoiceComponent;)V"))
    public void initialize(ValkyrieQueenDialogueScreen instance, DialogueChoiceComponent... options) {
        this.setupDialogueChoices(new DialogueChoiceComponent(this.buildDialogueChoice("question"), (button) -> {
            this.finishChat((byte)0);
        }), new DialogueChoiceComponent(this.buildDialogueChoice("challenge"), (button) -> {
            this.setDialogueAnswer(Component.translatable("gui.aether.queen.dialog.challenge"));
            int medals = this.getMinecraft().player.getInventory().countItem((Item) AetherItems.VICTORY_MEDAL.get());
            /* injected line */ boolean hasGrandMedal = this.getMinecraft().player.getInventory().countItem(ReduxItems.GRAND_VICTORY_MEDAL.get()) >= 1;
            DialogueChoiceComponent startFightChoice =
            /* injected line */ hasGrandMedal ? new DialogueChoiceComponent(Component.translatable("gui.aether_redux.player.dialog.has_grand_medal"), (button1) -> {this.finishChat((byte)1);}) :
            medals >= 10 ? new DialogueChoiceComponent(this.buildDialogueChoice("have_medals"), (button1) -> {
                this.finishChat((byte)1);
            }) : new DialogueChoiceComponent(this.buildDialogueChoice("no_medals").append(" (" + medals + "/10)"), (button1) -> {
                this.finishChat((byte)1);
            });
            this.setupDialogueChoices(startFightChoice, new DialogueChoiceComponent(this.buildDialogueChoice("deny_fight"), (button1) -> {
                this.finishChat((byte)2);
            }));
        }), new DialogueChoiceComponent(this.buildDialogueChoice("leave"), (pButton) -> {
            this.finishChat((byte)3);
        }));
    }
}
