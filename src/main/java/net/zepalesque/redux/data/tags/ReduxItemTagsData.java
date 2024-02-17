package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether_genesis.item.GenesisItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.misc.ReduxTags;
import org.jetbrains.annotations.Nullable;
import teamrazor.deepaether.init.DAItems;

import java.util.concurrent.CompletableFuture;

public class ReduxItemTagsData extends ItemTagsProvider {

    public ReduxItemTagsData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, CompletableFuture<TagLookup<Block>> blockTags, @Nullable ExistingFileHelper helper) {
        super(output, registries, blockTags, Redux.MODID, helper);
    }


    protected void addTags(HolderLookup.Provider provider) {

        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS)        {
            this.copy(woodHandler.logsBlockTag, woodHandler.logsTag);
            this.tag(AetherTags.Items.PLANKS_CRAFTING).add(woodHandler.planks.get().asItem());
            this.tag(AetherTags.Items.SKYROOT_STICK_CRAFTING).add(woodHandler.planks.get().asItem());
            this.tag(AetherTags.Items.SKYROOT_TOOL_CRAFTING).add(woodHandler.planks.get().asItem());
            this.tag(AetherTags.Items.SKYROOT_REPAIRING).add(woodHandler.planks.get().asItem());
            this.tag(ItemTags.BOATS).add(woodHandler.boatItem.get());
            this.tag(ItemTags.CHEST_BOATS).add(woodHandler.chestBoatItem.get());
        }
        this.tag(ItemTags.ARROWS).add(ReduxItems.VERIDIUM_ARROW.get());

        this.tag(ReduxTags.Items.REDUX_PHYG_FOOD_ITEMS).add(ReduxItems.WYNDSPROUT_SEEDS.get());
        this.tag(ReduxTags.Items.REDUX_FLYING_COW_FOOD_ITEMS).add(ReduxItems.BUNDLE_OF_WYNDSPROUTS.get());
        this.tag(ReduxTags.Items.GLIMMERCOW_TEMPTATION_ITEMS).add(ReduxItems.BUNDLE_OF_WYNDSPROUTS.get());
        this.tag(ReduxTags.Items.MYKAPOD_TEMPTATION_ITEMS).add(ReduxItems.LIGHTROOT_CLUMP.get());
        this.tag(ReduxTags.Items.MYKAPOD_SHED_FOOD).add(ReduxBlocks.SHIMMERSTOOL.get().asItem());
        this.tag(ReduxTags.Items.MYKAPOD_FOLLOW_ITEMS).addTag(ReduxTags.Items.MYKAPOD_TEMPTATION_ITEMS).addTag(ReduxTags.Items.MYKAPOD_SHED_FOOD);
        this.tag(AetherTags.Items.PHYG_TEMPTATION_ITEMS).replace(true).addTag(ReduxTags.Items.REDUX_PHYG_FOOD_ITEMS);
        this.tag(AetherTags.Items.FLYING_COW_TEMPTATION_ITEMS).replace(true).addTag(ReduxTags.Items.REDUX_FLYING_COW_FOOD_ITEMS);
        this.tag(ReduxTags.Items.SWET_JELLY).add(
                ReduxItems.BLUE_SWET_JELLY.get(),
                ReduxItems.GOLDEN_SWET_JELLY.get(),
                ReduxItems.VANILLA_SWET_JELLY.get(),
                GenesisItems.BLUE_SWET_JELLY.get(),
                GenesisItems.GOLDEN_SWET_JELLY.get(),
                GenesisItems.DARK_SWET_JELLY.get()
        );

        this.tag(ItemTags.PICKAXES).add(ReduxItems.VERIDIUM_PICKAXE.get(), ReduxItems.INFUSED_VERIDIUM_PICKAXE.get());
        this.tag(ItemTags.SHOVELS).add(ReduxItems.VERIDIUM_SHOVEL.get(), ReduxItems.INFUSED_VERIDIUM_SHOVEL.get());
        this.tag(ItemTags.HOES).add(ReduxItems.VERIDIUM_HOE.get(), ReduxItems.INFUSED_VERIDIUM_HOE.get());
        this.tag(ItemTags.AXES).add(ReduxItems.VERIDIUM_AXE.get(), ReduxItems.INFUSED_VERIDIUM_AXE.get());
        this.tag(ItemTags.SWORDS).add(ReduxItems.VERIDIUM_SWORD.get(), ReduxItems.INFUSED_VERIDIUM_SWORD.get());

        this.tag(AetherTags.Items.SLIDER_DAMAGING_ITEMS).add(ReduxItems.VERIDIUM_PICKAXE.get(), ReduxItems.INFUSED_VERIDIUM_PICKAXE.get());
        this.tag(AetherTags.Items.TREATED_AS_AETHER_ITEM).add(
                ReduxItems.VERIDIUM_PICKAXE.get(),
                ReduxItems.VERIDIUM_AXE.get(),
                ReduxItems.VERIDIUM_HOE.get(),
                ReduxItems.VERIDIUM_SHOVEL.get(),
                ReduxItems.VERIDIUM_SWORD.get(),
                ReduxItems.INFUSED_VERIDIUM_PICKAXE.get(),
                ReduxItems.INFUSED_VERIDIUM_AXE.get(),
                ReduxItems.INFUSED_VERIDIUM_HOE.get(),
                ReduxItems.INFUSED_VERIDIUM_SHOVEL.get(),
                ReduxItems.INFUSED_VERIDIUM_SWORD.get()
        );


        this.tag(ReduxTags.Items.GOLDEN_SWET_JELLY).add(ReduxItems.GOLDEN_SWET_JELLY.get()).addOptional(new ResourceLocation("aether_genesis","golden_swet_jelly"));
        this.tag(ReduxTags.Items.BLUE_SWET_JELLY).add(ReduxItems.BLUE_SWET_JELLY.get()).addOptional(new ResourceLocation("aether_genesis","blue_swet_jelly"));
        this.tag(ReduxTags.Items.GOLDEN_SWET_BALL).add(ReduxItems.GOLDEN_SWET_BALL.get()).addOptional(new ResourceLocation("aether_genesis","golden_swet_ball")).addOptional(DAItems.GOLDEN_SWET_BALL.getId());
        this.tag(AetherTags.Items.ACCESSORIES_PENDANTS).add(
                ReduxItems.VAMPIRE_AMULET.get(),
                ReduxItems.GRAND_VICTORY_MEDAL.get()
        );
        this.tag(AetherTags.Items.ACCESSORIES_CAPES).add(
                ReduxItems.AIRBOUND_CAPE.get()
        );
        this.tag(AetherTags.Items.ACCESSORIES_RINGS).add(
                ReduxItems.ENCHANTED_RING.get(),
                ReduxItems.RING_OF_WISDOM.get(),
                ReduxItems.SENTRY_RING.get(),
                ReduxItems.SHROOM_RING.get()
        );
        this.tag(AetherTags.Items.ACCESSORIES_SHIELDS).add(
                ReduxItems.SNAILSHELL_SHIELD.get()
        );
        this.tag(AetherTags.Items.ACCESSORIES_MISCELLANEOUS).add(
                ReduxItems.PHOENIX_EMBLEM.get(),
                ReduxItems.COCKATRICE_FEATHER.get(),
                ReduxItems.FEATHER_OF_WARDING.get()
        );
        this.tag(ReduxTags.Items.BLUEBERRY_PIE_EGGS).addTag(AetherTags.Items.MOA_EGGS).add(Items.EGG).addOptional(DAItems.QUAIL_EGG.getId());
        this.tag(ReduxTags.Items.SKYROOT_BOWLS).addOptional(new ResourceLocation("aether_genesis", "skyroot_bowl"));
        this.tag(ReduxTags.Items.MOUSE_EAR_CAPS).addOptional(new ResourceLocation("aether_genesis", "mouse_ear_caps"));
        this.tag(ItemTags.MUSIC_DISCS).add(ReduxItems.MUSIC_DISC_LABYRINTHS_VENGEANCE.get());
        this.tag(ReduxTags.Items.VERIDIUM_ADVANCEMENT_INFUSABLE).add(
                ReduxItems.VERIDIUM_PICKAXE.get(),
                ReduxItems.VERIDIUM_AXE.get(),
                ReduxItems.VERIDIUM_SHOVEL.get(),
                ReduxItems.VERIDIUM_HOE.get(),
                ReduxItems.VERIDIUM_SWORD.get()
        );
        this.tag(ReduxTags.Items.BLIGHTWARDING_ACCESSORIES).add(
                ReduxItems.COCKATRICE_FEATHER.get(),
                ReduxItems.FEATHER_OF_WARDING.get()
        );
        this.tag(ReduxTags.Items.IS_SKYROOT_TOOL).add(
                AetherItems.SKYROOT_SWORD.get(),
                AetherItems.SKYROOT_PICKAXE.get(),
                AetherItems.SKYROOT_AXE.get(),
                AetherItems.SKYROOT_SHOVEL.get(),
                AetherItems.SKYROOT_HOE.get()
        );
    }

}

