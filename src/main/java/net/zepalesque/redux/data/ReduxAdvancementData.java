package net.zepalesque.redux.data;

import com.aetherteam.aether.Aether;
import com.aetherteam.aether.entity.AetherEntityTypes;
import com.aetherteam.aether.item.AetherItems;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.advancement.trigger.*;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;
import net.zepalesque.redux.recipe.ReduxRecipeTypes;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public class ReduxAdvancementData extends ForgeAdvancementProvider {
    public ReduxAdvancementData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper helper) {
        super(output, registries, helper, List.of(new ReduxAdvancements()));
    }


    public static class ReduxAdvancements implements AdvancementGenerator, IConditionBuilder  {







        @Override
        public void generate(HolderLookup.Provider provider, Consumer<Advancement> consumer, ExistingFileHelper existingFileHelper) {

            Advancement fallingWithStyle = Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "enter_aether"))
                    .display(AetherItems.GOLDEN_PARACHUTE.get(),
                            Component.translatable("advancement.aether_redux.fall_from_aether"),
                            Component.translatable("advancement.aether_redux.fall_from_aether.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("fall_from_aether", FallFromAetherTrigger.TriggerInstance.fall())
                    .save(consumer, Redux.locate("fall_from_aether"), existingFileHelper);


            Advancement takeThatPhysics = Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "bronze_dungeon"))
                    .display(ReduxItems.AIRBOUND_CAPE.get(),
                            Component.translatable("advancement.aether_redux.double_jump"),
                            Component.translatable("advancement.aether_redux.double_jump.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("double_jump", DoubleJumpTrigger.TriggerInstance.jump())
                    .save(consumer, Redux.locate("double_jump"), existingFileHelper);

            Advancement telekinesis = Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "silver_dungeon"))
                    .display(ReduxItems.VALKYRIE_RING.get(),
                            Component.translatable("advancement.aether_redux.mine_extended"),
                            Component.translatable("advancement.aether_redux.mine_extended.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("mine_extended", ExtendedReachBreakBlockTrigger.TriggerInstance.mineBlock())
                    .save(consumer, Redux.locate("mine_extended"), existingFileHelper);

            Advancement pyromaniac = Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "gold_dungeon"))
                    .display(ReduxItems.PHOENIX_EMBLEM.get(),
                            Component.translatable("advancement.aether_redux.kill_sheepuff_with_fireball"),
                            Component.translatable("advancement.aether_redux.kill_sheepuff_with_fireball.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("kill_sheepuff_with_fireball",
                            KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity()
                                    .of(AetherEntityTypes.SHEEPUFF.get()),
                                    DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(ReduxTags.DamageTypes.FIREBALL)).direct(EntityPredicate.Builder.entity().of(ReduxEntityTypes.VOLATILE_FIRE_CRYSTAL.get()))))
                    .save(consumer, Redux.locate("kill_sheepuff_with_fireball"), existingFileHelper);



            Advancement nextLevel = Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "enter_aether"))
                    .display(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(),
                            Component.translatable("advancement.aether_redux.infuse_veridium"),
                            Component.translatable("advancement.aether_redux.infuse_veridium.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("infuse_veridium", InfuseItemTrigger.Instance.forIngredient(ItemPredicate.Builder.item().of(ReduxTags.Items.VERIDIUM_ADVANCEMENT_INFUSABLE).build()))
                    .save(consumer, Redux.locate("infuse_veridium"), existingFileHelper);


            Advancement darkMagic = Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "enter_aether"))
                    .display(ReduxItems.BLIGHTED_SPORES.get(),
                            Component.translatable("advancement.aether_redux.convert_with_blighted_spores"),
                            Component.translatable("advancement.aether_redux.convert_with_blighted_spores.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("convert_with_blighted_spores", BlockStateRecipeTrigger.TriggerInstance.forRecipe(ReduxRecipeTypes.SPORE_BLIGHTING.get()))
                    .save(consumer, Redux.locate("convert_with_blighted_spores"), existingFileHelper);

            Advancement blightsBane = Advancement.Builder.advancement()
                    .parent(new ResourceLocation(Aether.MODID, "enter_aether"))
                    .display(ReduxItems.PURIFIED_LUXBUDS.get(),
                            Component.translatable("advancement.aether_redux.obtain_purified_luxbuds"),
                            Component.translatable("advancement.aether_redux.obtain_purified_luxbuds.desc"),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("obtain_purified_luxbuds", InventoryChangeTrigger.TriggerInstance.hasItems(ReduxItems.PURIFIED_LUXBUDS.get()))
                    .save(consumer, Redux.locate("obtain_purified_luxbuds"), existingFileHelper);
        }
    }

}
