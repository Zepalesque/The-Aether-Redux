package net.zepalesque.redux.data.prov;

import com.aetherteam.aether.data.providers.AetherLanguageProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.zepalesque.zenith.util.DatagenUtil;

import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ReduxLanguageProvider extends AetherLanguageProvider {
    public ReduxLanguageProvider(PackOutput output, String id) {
        super(output, id);
    }

    public void addItem(DeferredItem<? extends Item> key) {
        this.addItem(key, DatagenUtil.getNameLocalized(key));
    }

    public void addBlock(DeferredBlock<? extends Block> key) {
        this.addBlock(key, DatagenUtil.getNameLocalized(key));
    }

    public void addEntityType(DeferredHolder<EntityType<?>, ? extends EntityType<?>> key) {
        this.addEntityType(key, DatagenUtil.getNameLocalized(key));
    }

    public void addTooltip(String key, String name) {
        this.add("tooltip." + this.id + "." + key, name);
    }

    public void addSubtitle(Supplier<SoundEvent> sound, Function<SoundEvent, String> factory, String subtitle) {
        this.add(factory.apply(sound.get()), subtitle);
    }
}
