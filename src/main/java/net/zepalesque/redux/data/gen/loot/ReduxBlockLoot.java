package net.zepalesque.redux.data.gen.loot;

import com.aetherteam.aether.block.AetherBlocks;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.prov.loot.ReduxBlockLootProvider;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReduxBlockLoot extends ReduxBlockLootProvider {

    private static final Set<Item> EXPLOSION_RESISTANT = Stream.of(AetherBlocks.TREASURE_CHEST.get()).map(ItemLike::asItem).collect(Collectors.toSet());

    public ReduxBlockLoot() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        Redux.WOOD_SETS.forEach(set -> set.lootData(this));

    }
}
