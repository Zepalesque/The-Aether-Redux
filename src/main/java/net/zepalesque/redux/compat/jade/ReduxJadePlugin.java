package net.zepalesque.redux.compat.jade;

import net.zepalesque.redux.block.redstone.LogicatorBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class ReduxJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(LogicatorProvider.INSTANCE, LogicatorBlock.class);
    }
}