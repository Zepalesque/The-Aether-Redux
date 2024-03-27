package net.zepalesque.redux.data;

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
import net.zepalesque.redux.advancement.trigger.AprilReduxSpecialTrigger;
import net.zepalesque.redux.advancement.trigger.DoubleJumpTrigger;
import net.zepalesque.redux.advancement.trigger.FallFromAetherTrigger;
import net.zepalesque.redux.advancement.trigger.InfuseItemTrigger;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;

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

            Advancement install = Advancement.Builder.advancement()
                    .display(ReduxItems.INSTALL_ICON.get(),
                            Component.translatable("advancement.aether_redux.install"),
                            Component.translatable("advancement.aether_redux.install.desc")
                                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),
                            new ResourceLocation(Redux.MODID, "textures/block/natural/sentrite.png"),
                            FrameType.TASK, true, false, false)
                    .addCriterion("install", AprilReduxSpecialTrigger.TriggerInstance.type("install"))
                    .save(consumer, Redux.locate("install"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(install)
                    .display(AetherItems.GOLDEN_PARACHUTE.get(),
                            Component.translatable("advancement.aether_redux.fall_from_aether"),
                            Component.translatable("advancement.aether_redux.fall_from_aether.desc")
                                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("fall_from_aether", FallFromAetherTrigger.TriggerInstance.fall())
                    .save(consumer, Redux.locate("fall_from_aether"), existingFileHelper);

           Advancement.Builder.advancement()
                    .parent(install)
                    .display(ReduxItems.HUG_ICON.get(),
                            Component.translatable("advancement.aether_redux.hug"),
                            Component.translatable("advancement.aether_redux.hug.desc")
                                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("hug", AprilReduxSpecialTrigger.TriggerInstance.type("hug"))
                    .save(consumer, Redux.locate("hug"), existingFileHelper);

           Advancement.Builder.advancement()
                    .parent(install)
                    .display(ReduxBlocks.COCOA_SOIL.get(),
                            Component.translatable("advancement.aether_redux.eat_sugarfields"),
                            Component.translatable("advancement.aether_redux.eat_sugarfields.desc")
                                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),
                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("eat_sugarfields", ConsumeItemTrigger.TriggerInstance.usedItem(ItemPredicate.Builder.item().of(ReduxTags.Items.EDIBLE_SUGARFIELDS_BLOCKS).build()))
                    .save(consumer, Redux.locate("eat_sugarfields"), existingFileHelper);

            Advancement cape =Advancement.Builder.advancement()
                    .parent(install)
                    .display(ReduxItems.AIRBOUND_CAPE.get(),
                            Component.translatable("advancement.aether_redux.double_jump"),
                            Component.translatable("advancement.aether_redux.double_jump.desc")
                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),
                    null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("double_jump", DoubleJumpTrigger.TriggerInstance.jump())
                    .save(consumer, Redux.locate("double_jump"), existingFileHelper);

            Advancement medal = Advancement.Builder.advancement()
                    .parent(cape)
                    .display(ReduxItems.GRAND_VICTORY_MEDAL.get(),
                            Component.translatable("advancement.aether_redux.grand_medal"),
                            Component.translatable("advancement.aether_redux.grand_medal.desc")
                                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),

                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("obtain_grand_medal", InventoryChangeTrigger.TriggerInstance.hasItems(ReduxItems.GRAND_VICTORY_MEDAL.get()))
                    .save(consumer, Redux.locate("obtain_grand_medal"), existingFileHelper);
            Advancement.Builder.advancement()
                    .parent(medal)
                    .display(ReduxItems.SOLAR_EMBLEM.get(),
                            Component.translatable("advancement.aether_redux.kill_sheepuff_with_fireball"),
                            Component.translatable("advancement.aether_redux.kill_sheepuff_with_fireball.desc")
                                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),

                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("kill_sheepuff_with_fireball",
                            KilledTrigger.TriggerInstance.playerKilledEntity(EntityPredicate.Builder.entity()
                                    .of(AetherEntityTypes.SHEEPUFF.get()),
                                    DamageSourcePredicate.Builder.damageType().tag(TagPredicate.is(ReduxTags.DamageTypes.FIREBALL)).direct(EntityPredicate.Builder.entity().of(ReduxEntityTypes.VOLATILE_FIRE_CRYSTAL.get()))))
                    .save(consumer, Redux.locate("kill_sheepuff_with_fireball"), existingFileHelper);

            Advancement veridium = Advancement.Builder.advancement()
                    .parent(install)
                    .display(ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(),
                            Component.translatable("advancement.aether_redux.infuse_veridium"),
                            Component.translatable("advancement.aether_redux.infuse_veridium.desc")
                                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),

                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("infuse_veridium", InfuseItemTrigger.Instance.forIngredient(ItemPredicate.Builder.item().of(ReduxTags.Items.VERIDIUM_ADVANCEMENT_INFUSABLE).build()))
                    .save(consumer, Redux.locate("infuse_veridium"), existingFileHelper);

            Advancement.Builder.advancement()
                    .parent(veridium)
                    .display(ReduxItems.FEATHER_OF_WARDING.get(),
                            Component.translatable("advancement.aether_redux.feather_of_warding"),
                            Component.translatable("advancement.aether_redux.feather_of_warding.desc")
                                    .append(Component.translatable("gui.aether_redux.advancement_suffix").withStyle(style -> style.withColor(Redux.REDUX_PURPLE))),

                            null,
                            FrameType.TASK, true, true, false)
                    .addCriterion("obtain_feather_of_warding", InventoryChangeTrigger.TriggerInstance.hasItems(ReduxItems.FEATHER_OF_WARDING.get()))
                    .save(consumer, Redux.locate("obtain_feather_of_warding"), existingFileHelper);
        }
    }

}
