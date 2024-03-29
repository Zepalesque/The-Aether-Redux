package net.zepalesque.redux.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.Redux;

@Mod.EventBusSubscriber(modid = Redux.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ReduxCommands {
    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        RebuxCommand.register(dispatcher);
    }
}