package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.data.providers.AetherItemModelProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.Supplier;

public abstract class ReduxItemModelProvider extends AetherItemModelProvider {

    public ReduxItemModelProvider(PackOutput output, String id, ExistingFileHelper helper) {
        super(output, id, helper);
    }

    public ItemModelBuilder itemBlockFlat(Supplier<? extends Block> block, String location) {
        return withExistingParent(blockName(block.get()), mcLoc("item/generated"))
                .texture("layer0", texture(blockName(block.get()), location));
    }

    public ItemModelBuilder itemBlockFlatCustomTexture(Supplier<? extends Block> block, String locationPlusName) {
        return withExistingParent(blockName(block.get()), mcLoc("item/generated"))
                .texture("layer0", texture(locationPlusName));
    }
}
