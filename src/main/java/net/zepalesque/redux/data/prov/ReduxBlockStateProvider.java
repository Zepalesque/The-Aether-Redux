package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.data.providers.AetherBlockStateProvider;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.block.natural.AetherShortGrassBlock;
import net.zepalesque.redux.block.state.ReduxStates;

public abstract class ReduxBlockStateProvider extends AetherBlockStateProvider {

    public ReduxBlockStateProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    public void tintableShortGrass(AetherShortGrassBlock block, String location) {
        this.getVariantBuilder(block).forAllStates((state) -> {
            boolean enchanted = state.getValue(ReduxStates.ENCHANTED);
            if (enchanted) {
                return ConfiguredModel.builder()
                        .modelFile(models()
                                .cross(state.getValue(ReduxStates.GRASS_SIZE).getSerializedName() + "_enchanted_grass",
                                        modLoc("block/natural/enchanted_" + state.getValue(ReduxStates.GRASS_SIZE).getSerializedName()  + "_grass"))
                                .renderType("cutout")).build();
            }

            return ConfiguredModel.builder()
                    .modelFile(models()
                            .singleTexture(state.getValue(ReduxStates.GRASS_SIZE).getSerializedName() + "_aether_grass",
                                    mcLoc("block/tinted_cross"), "cross",
                                    modLoc("block/natural/aether_" + state.getValue(ReduxStates.GRASS_SIZE).getSerializedName() + "_grass"))
                            .renderType("cutout")).build();
        });
    }
}
