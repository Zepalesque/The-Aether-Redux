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
        this.withExistingParent(this.blockName(block), this.blockName(baseBlock))
                .texture("overlay", new ResourceLocation(Aether.MODID, "block/" + overlay))
                .element().from(0.0F, 0.0F, -0.1F).to(16.0F, 16.0F, -0.1F).rotation().angle(0.0F).axis(Direction.Axis.Y).origin(8.0F, 8.0F, 6.9F).end().face(Direction.NORTH).texture("#overlay").emissivity(15, 15).end().end()
                .transforms()
                .transform(ItemDisplayContext.THIRD_PERSON_RIGHT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.THIRD_PERSON_LEFT_HAND).rotation(75.0F, 45.0F, 0.0F).translation(0.0F, 2.5F, 0.0F).scale(0.375F, 0.375F, 0.375F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_RIGHT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.FIRST_PERSON_LEFT_HAND).rotation(-90.0F, -180.0F, -45.0F).scale(0.4F, 0.4F, 0.4F).end()
                .transform(ItemDisplayContext.GROUND).rotation(90.0F, 0.0F, 0.0F).translation(0.0F, 3.0F, 0.0F).scale(0.25F, 0.25F, 0.25F).end()
                .transform(ItemDisplayContext.GUI).rotation(30.0F, 135.0F, 0.0F).scale(0.625F, 0.625F, 0.625F).end()
                .transform(ItemDisplayContext.FIXED).scale(0.5F, 0.5F, 0.5F).end()
                .end();
    }
}
