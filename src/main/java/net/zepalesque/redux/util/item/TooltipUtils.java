package net.zepalesque.redux.util.item;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.Collection;

public class TooltipUtils {
    public static final Component INFO = Component.translatable(
        "tooltip.aether_redux.shift_info",
        Minecraft.getInstance().options.keyShift
            .getKey()
            .getDisplayName()
            .getString()
    ).withStyle(ChatFormatting.DARK_GRAY);

    private static final Collection<Component> INFO_ARRAY = ImmutableList.of(INFO);

    /**
     * Returns the given components, or the default info message if the shift key is up.
     */
    public static Collection<Component> shiftForInfo(Collection<Component> whenDown) {
        return shiftDownElseMulti(whenDown, INFO_ARRAY);
    }

    /**
     * Returns the given component, or the default info message if the shift key is up.
     */
    public static Component shiftForInfo(Component whenDown) {
        return shiftDownElse(whenDown, INFO);
    }

    @Nullable
    public static Component shiftDownElse(Component whenDown, @Nullable Component otherwise) {
        var mc = Minecraft.getInstance();
        if (InputConstants.isKeyDown(
            mc.getWindow().getWindow(),
            mc.options.keyShift.getKey().getValue()
        )) return whenDown;
        else return otherwise;
    }

    @Nullable
    public static Collection<Component> shiftDownElseMulti(
        Collection<Component> whenDown,
        @Nullable Collection<Component> otherwise) {
        var mc = Minecraft.getInstance();
        if (InputConstants.isKeyDown(
            mc.getWindow().getWindow(),
            mc.options.keyShift.getKey().getValue()
        )) return whenDown;
        else return otherwise;
    }
}