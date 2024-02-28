
package net.zepalesque.redux.loot.modifiers;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedEntry.Wrapper;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DungeonModifier extends LootModifier {

    public static final Supplier<Codec<DungeonModifier>> CODEC = Suppliers.memoize(() -> RecordCodecBuilder.create(inst -> codecStart(inst)
            .and(WeightedEntry.Wrapper.codec(ItemStack.CODEC).listOf().fieldOf("items").forGetter(m -> m.items))
            .and(Codec.FLOAT.fieldOf("spawn_chance").forGetter(m -> m.chance))
            .and(Codec.BOOL.fieldOf("replace_if_full").forGetter(m -> m.replace))
            .apply(inst, DungeonModifier::new)));

    public final List<WeightedEntry.Wrapper<ItemStack>> items;
    public final int totalWeight;
    public final float chance;
    public final boolean replace;

    public DungeonModifier(final LootItemCondition[] conditionsIn, List<WeightedEntry.Wrapper<ItemStack>> items, float chance, boolean shouldReplace) {
        super(conditionsIn);
        this.items = items.stream().map(wrapper -> WeightedEntry.wrap(wrapper.getData().copy(), wrapper.getWeight().asInt())).toList();
        int w = 0;
        for (WeightedEntry.Wrapper<ItemStack> item : this.items) {
            w += item.getWeight().asInt();
        }
        this.totalWeight = w;
        this.chance = chance;
        this.replace = shouldReplace;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {

        // size of the loots
        int size = generatedLoot.size();

        // is the loot full?
        boolean isFull = size == 27;

        // size diff for when it's not full
        int sizeDiff = 27-size;

        if (this.replace) {
            ObjectArrayList<ItemStack> list = new ObjectArrayList<>();

            for (ItemStack stack : generatedLoot) {
                // chest is full => x chance to replace the item by one of my item
                // chest is not full => we don't replace any loot
                if (isFull) {
                    if (context.getRandom().nextFloat() > chance) {
                        WeightedRandom.getRandomItem(context.getRandom(), this.items, totalWeight).ifPresent(e -> list.add(e.getData()));
                    }
                } else {
                    list.add(stack);
                }
            }
            // if the loot is not full, for each slot remaining, have x chance to add one of our item in the empty slots
            if (!isFull) {
                for (int i = 0; i <= sizeDiff; i++) {
                    if (context.getRandom().nextFloat() > chance) {
                        WeightedRandom.getRandomItem(context.getRandom(), this.items, totalWeight).ifPresent(e -> list.add(e.getData()));
                    }
                }
            }
            return list;
        } else {
            // if the loot is not full, for each slot remaining, have x chance to add one of our item in the empty slots
            if (!isFull) {
                for (int i = 0; i <= sizeDiff; i++) {
                    if (context.getRandom().nextFloat() > chance) {
                        WeightedRandom.getRandomItem(context.getRandom(), this.items, totalWeight).ifPresent(e -> generatedLoot.add(e.getData()));
                    }
                }
            }
            return generatedLoot;
        }
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC.get();
    }
}
