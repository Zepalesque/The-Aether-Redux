package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.data.providers.AetherLanguageProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.zepalesque.zenith.util.DatagenUtil;

public abstract class ReduxLanguageProvider extends AetherLanguageProvider {
    public ReduxLanguageProvider(PackOutput output, String id) {
        super(output, id);
    }

    public void add(DeferredItem<? extends Item> key) {
        this.addItem(key, DatagenUtil.getNameLocalized(key));
    }

    public void add(DeferredBlock<? extends Block> key) {
        this.addBlock(key, DatagenUtil.getNameLocalized(key));
    }

    public void add(DeferredHolder<EntityType<?>, ? extends EntityType<?>> key) {
        this.addEntityType(key, DatagenUtil.getNameLocalized(key));
    }
}
