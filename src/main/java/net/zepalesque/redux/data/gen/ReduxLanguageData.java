package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.zenith.util.DatagenUtil;

public class ReduxLanguageData extends ReduxLanguageProvider {

    public ReduxLanguageData(PackOutput output) {
        super(output, Redux.MODID);
    }

    @Override
    protected void addTranslations() {
        Redux.WOOD_SETS.forEach(set -> set.langData(this));
        Redux.STONE_SETS.forEach(set -> set.langData(this));

        add(ReduxBlocks.SHORT_AETHER_GRASS);
        addLore(ReduxBlocks.SHORT_AETHER_GRASS, "Blades of the Aether's grass. It feels slightly cool to the touch.");

        add(ReduxBlocks.CLOUDROOT_LEAVES);
        addLore(ReduxBlocks.CLOUDROOT_LEAVES, "Leaves of the Cloudroot tree, a variation of Skyroot that has been touched by Ambrosium but has not fully adapted as Golden Oaks have. These sometimes will drop Cloudroot Saplings");

        add(ReduxBlocks.CLOUDROOT_SAPLING);
        addLore(ReduxBlocks.CLOUDROOT_SAPLING, "The sapling of the Cloudroot tree. It can be grown by waiting or using Bone Meal.");

        add(ReduxBlocks.CARVED_PILLAR);
        addLore(ReduxBlocks.CARVED_PILLAR, "A pillar made of Carved Stone. Pillars look nice for supporting a build, along with giving it nice corners.");
        add(ReduxBlocks.SENTRY_PILLAR);
        addLore(ReduxBlocks.SENTRY_PILLAR, "A pillar made of Sentry Stone. Pillars look nice for supporting a build, along with giving it nice corners.");
        add(ReduxBlocks.CARVED_BASE);
        addLore(ReduxBlocks.CARVED_BASE, "A nice decorative base block made of Carved Stone. Looks very nice at the bottom of walls!");
        add(ReduxBlocks.SENTRY_BASE);
        addLore(ReduxBlocks.SENTRY_BASE, "A nice decorative base block made of Sentry Stone. Looks very nice at the bottom of walls!");

        add(ReduxBlocks.LOCKED_CARVED_PILLAR);
        add(ReduxBlocks.LOCKED_SENTRY_PILLAR);
        add(ReduxBlocks.LOCKED_CARVED_BASE);
        add(ReduxBlocks.LOCKED_SENTRY_BASE);

        add(ReduxBlocks.TRAPPED_CARVED_PILLAR);
        add(ReduxBlocks.TRAPPED_SENTRY_PILLAR);
        add(ReduxBlocks.TRAPPED_CARVED_BASE);
        add(ReduxBlocks.TRAPPED_SENTRY_BASE);

        add(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR);
        add(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR);
        add(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE);
        add(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE);

        add(ReduxBlocks.RUNELIGHT);
        addLore(ReduxBlocks.RUNELIGHT, "A glowing block of circuitry made of Veridium, which can be easily toggled on and off. Found in Bronze Dungeons.");
        add(ReduxBlocks.LOCKED_RUNELIGHT);

        add(ReduxBlocks.LOCKED_SENTRITE_BRICKS);


        add(ReduxBlocks.WYNDSPROUTS);
        add(ReduxBlocks.POTTED_WYNDSPROUTS);
        addLore(ReduxBlocks.WYNDSPROUTS, "A common plant found in the Aether. They occasionally drop Wynd Oats, the main edible source of grain in the Aether.");

        add(ReduxBlocks.SKYSPROUTS);
        add(ReduxBlocks.POTTED_SKYSPROUTS);
        addLore(ReduxBlocks.SKYSPROUTS, "A relative of the common Wyndsprouts, this flowering grass is found in the Skyfields.");


        add(ReduxItems.WYND_OATS);
        addLore(ReduxItems.WYND_OATS, "A pile of Wynd Oats. These can be grown into the Wynd Oat plant.");
        add(ReduxItems.WYND_OAT_PANICLE);
        addLore(ReduxItems.WYND_OAT_PANICLE, "A panicle of grown Wynd Oats. This can be used for a variety of recipes.");
        // TODO: Reimplement said recipes

//        add(ReduxItems.WYND_BAGEL);
//        addLore(ReduxItems.WYND_BAGEL, "A nice bagel made with some harvested Wynd Oats.");
//
//        add(ReduxItems.BLUEBERRY_BAGEL);
//        addLore(ReduxItems.BLUEBERRY_BAGEL, "A bagel made with Blue Berries. This is much more filling than a plain Wynd Bagel");
//
//        add(ReduxItems.OATMEAL);
//        addLore(ReduxItems.OATMEAL, "A nice bowl of Oatmeal. Specifically, this is Wynd Oatmeal, as it was made with Wynd Oats.");

        add(ReduxItems.VERIDIUM_INGOT);
        addLore(ReduxItems.VERIDIUM_INGOT, "An bar of pure Veridium, a metal that when coming in contact with ambrosium, takes on a glowing light blue color, strengthening temporarily.");
        add(ReduxItems.VERIDIUM_NUGGET);
        addLore(ReduxItems.VERIDIUM_NUGGET, "A small chunk of Veridium. This can be crafted to and from Veridium Ingots.");

        add(ReduxItems.RAW_VERIDIUM);
        addLore(ReduxItems.RAW_VERIDIUM, "A chunk of Raw Veridium. This can be smelted into an ingot.");


        add(ReduxItems.REFINED_SENTRITE);
        addLore(ReduxItems.REFINED_SENTRITE, "The purified form of Sentrite. This can be used for a variety of different things, but is commonly found associated with Sentry technology.");

        add(ReduxItems.SENTRITE_CHUNK);
        addLore(ReduxItems.SENTRITE_CHUNK, "A chunk of purified Sentrite. These are occasionally dropped from Sentries, and can be crafted into Refined Sentrite.");

        add(ReduxBlocks.SENTRITE_CHAIN);
        addLore(ReduxBlocks.SENTRITE_CHAIN, "A chain made of purified Sentrite. This is crafted with a piece of Refined Sentrite and two Sentrite Chunks.");
        add(ReduxBlocks.SENTRITE_LANTERN);
        addLore(ReduxBlocks.SENTRITE_LANTERN, "A lantern made of Sentrite. You can place it on the ground or hang it on the ceiling!");

        add(ReduxItems.VERIDIUM_PICKAXE);
        addLore(ReduxItems.VERIDIUM_PICKAXE, "A pickaxe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        add(ReduxItems.INFUSED_VERIDIUM_PICKAXE);
        addLore(ReduxItems.INFUSED_VERIDIUM_PICKAXE, "A pickaxe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        add(ReduxItems.VERIDIUM_AXE);
        addLore(ReduxItems.VERIDIUM_AXE, "A axe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        add(ReduxItems.INFUSED_VERIDIUM_AXE);
        addLore(ReduxItems.INFUSED_VERIDIUM_AXE, "A axe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        add(ReduxItems.VERIDIUM_SHOVEL);
        addLore(ReduxItems.VERIDIUM_SHOVEL, "A shovel made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        add(ReduxItems.INFUSED_VERIDIUM_SHOVEL);
        addLore(ReduxItems.INFUSED_VERIDIUM_SHOVEL, "A shovel made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        add(ReduxItems.VERIDIUM_HOE);
        addLore(ReduxItems.VERIDIUM_HOE, "A hoe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        add(ReduxItems.INFUSED_VERIDIUM_HOE);
        addLore(ReduxItems.INFUSED_VERIDIUM_HOE, "A hoe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");



        addGuiText("shift_info", "Hold [%s] for more info...");
        addTooltip("infusion_charge", "Infusion Charge: %s");
        addGuiText("infusion_info", "Can be infused by right-clicking with an Ambrosium Shard");

        addPackDescription("mod", "The Aether: Redux Resources");
        addPackTitle("tintable_grass", "Redux - Tintable Grass");
        addPackDescription("tintable_grass", "Grass tint textures for the Aether: Redux");

        addPackTitle("bronze_upgrade", "Redux - Bronze Dungeon Upgrade");
        addPackDescription("bronze_upgrade", "Configurable in config/aether_redux/common.toml");

        addSubtitle(ReduxSounds.INFUSE_ITEM, DatagenUtil::subtitleFor, "Item infuses");
        addSubtitle(ReduxSounds.INFUSION_EXPIRE, DatagenUtil::subtitleFor, "Item infusion runs out");
    }
}
