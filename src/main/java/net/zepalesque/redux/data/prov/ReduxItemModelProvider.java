package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.data.providers.AetherItemModelProvider;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
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

    public void itemOverlayBlock(Block block, Block baseBlock, String overlay) {
        this.withExistingParent(this.blockName(block), this.texture(this.blockName(baseBlock)))
                .texture("overlay", new ResourceLocation(Aether.MODID, "block/" + overlay))
                .element().from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F).rotation().angle(0.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 6.9F).end().face(Direction.NORTH).texture("#overlay").emissivity(15, 15).end().end()
    }
}
