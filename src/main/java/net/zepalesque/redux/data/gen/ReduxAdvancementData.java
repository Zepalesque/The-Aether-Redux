package net.zepalesque.redux.data.gen;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.LocationPredicate;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.advancement.ReduxAdvancementTriggers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ReduxAdvancementData extends AdvancementProvider {

    public ReduxAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new ReduxAdvancements(Redux.MODID)));
    }

    public static class ReduxAdvancements implements AdvancementGenerator, IConditionBuilder  {

        private final String modid;

        public ReduxAdvancements(String modid) {
            this.modid = modid;
        }

        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> output, ExistingFileHelper existingFileHelper) {

            AdvancementHolder aether = Advancement.Builder.advancement().build(ResourceLocation.fromNamespaceAndPath(Aether.MODID, "the_aether"));

            Advancement.Builder.advancement()
                    .parent(aether)
                    .display(
                            AetherItems.GOLDEN_RING.get(),
                            Component.translatable("advancement." + modid + ".throw_ring_in_lava"),
                            Component.translatable("advancement." + modid + ".throw_ring_in_lava.desc"),
                            null,
                            AdvancementType.TASK, true, true, true)
                    .addCriterion("throw_ring_in_lava",
                            ReduxAdvancementTriggers.THROW_GOLD_RING_INTO_LAVA.get().createCriterion(
                                    new PlayerTrigger.TriggerInstance(EntityPredicate.wrap(Optional.of(EntityPredicate.Builder.entity().located(LocationPredicate.Builder.inDimension(Level.NETHER)).build())))
                            ))
                    .save(output, Redux.loc("throw_ring_in_lava"), existingFileHelper);

        }
    }
}
