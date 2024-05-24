package net.zepalesque.redux;

import com.aetherteam.aether.data.generators.AetherBlockStateData;
import com.aetherteam.aether.data.generators.AetherItemModelData;
import com.aetherteam.aether.data.generators.AetherLanguageData;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.gen.ReduxBlockStateGen;
import net.zepalesque.redux.data.gen.ReduxItemModelGen;
import net.zepalesque.redux.data.gen.ReduxLanguageGen;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.tile.ReduxTiles;
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

        ReduxBlocks.BLOCKS.register(bus);
        ReduxItems.ITEMS.register(bus);
        ReduxEntities.ENTITIES.register(bus);
        ReduxTiles.TILES.register(bus);

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

        // Client Data
        generator.addProvider(event.includeClient(), new ReduxBlockStateGen(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxItemModelGen(packOutput, fileHelper));
        generator.addProvider(event.includeClient(), new ReduxLanguageGen(packOutput));
    }

    public static ResourceLocation loc(String path) {
        return new ResourceLocation(MODID, path);
    }

}
