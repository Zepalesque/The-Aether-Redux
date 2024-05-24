package net.zepalesque.redux;

import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.zenith.api.condition.ConfigCondition;
import net.zepalesque.zenith.api.condition.config.ConfigSerializer;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Redux.MODID)
public class Redux {
    public static final String MODID = "aether_redux";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Redux(IEventBus bus) {
        bus.addListener(this::commonSetup);
        bus.addListener(this::dataSetup);


        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ReduxConfig.COMMON_SPEC, MODID + "/common.toml");
        ConfigCondition.registerSerializer("redux_common", new ConfigSerializer(ReduxConfig.Common::serialize, ReduxConfig.Common::deserialize));
    }

    private void commonSetup(final FMLCommonSetupEvent event) {

    }

    private void dataSetup(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper fileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();
        PackOutput packOutput = generator.getPackOutput();
    }
}
