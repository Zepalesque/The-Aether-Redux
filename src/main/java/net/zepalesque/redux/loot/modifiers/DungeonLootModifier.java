
package net.zepalesque.redux.loot.modifiers;

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

public class DungeonLootModifier extends LootModifier {
    public static final Codec<DungeonLootModifier> CODEC = RecordCodecBuilder.create((instance) -> codecStart(instance).and(Wrapper.codec(ItemStack.CODEC).listOf().fieldOf("entries").forGetter((modifier) -> modifier.entries)).and(IntProvider.CODEC.fieldOf("rolls").forGetter((modifier) -> modifier.rolls))
            .apply(instance, DungeonLootModifier::new));
    public final List<Wrapper<ItemStack>> entries;
    public final IntProvider rolls;

    public DungeonLootModifier(LootItemCondition[] conditionsIn, List<Wrapper<ItemStack>> entries, IntProvider rolls) {
        super(conditionsIn);
        this.entries = entries.stream().map(wrapper -> WeightedEntry.wrap(wrapper.getData().copy(), wrapper.getWeight().asInt())).toList();
        this.rolls = rolls;
    }

    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        RandomSource randomSource = context.getRandom();
        Vec3 vec3 = context.getParamOrNull(LootContextParams.ORIGIN);
        if (vec3 != null) {
            BlockPos pos = new BlockPos(vec3);
            BlockEntity blockEntity = context.getLevel().getBlockEntity(pos);
            if (blockEntity instanceof BaseContainerBlockEntity containerBlockEntity) {
                int rollCount = this.rolls.sample(randomSource);

                for(int i = 0; i < rollCount; ++i) {
                    boolean isFull = generatedLoot.size() == containerBlockEntity.getContainerSize();
                    if (!isFull) {
                        int weight = this.entries.stream().map((entry) -> entry.getWeight().asInt()).reduce(0, Integer::sum);
                        WeightedRandom.getRandomItem(randomSource, this.entries, weight).ifPresent(e -> generatedLoot.add(e.getData().copy()));
                    }
                }
            }
        }

        return generatedLoot;
    }

    public Codec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
