package net.zepalesque.redux.data;

import com.aetherteam.aether.data.providers.AetherLanguageProvider;
import com.google.common.collect.ImmutableList;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.fluids.FluidType;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.blockhandler.WoodHandler;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.resource.ReduxDamageTypes;
import net.zepalesque.redux.data.resource.biome.registry.ReduxBiomes;
import net.zepalesque.redux.effect.ReduxEffects;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.fluid.ReduxFluidTypes;
import net.zepalesque.redux.item.ReduxItems;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class ReduxLanguageData extends AetherLanguageProvider {
    protected final Map<String, String> TIPS = new HashMap<>();
    protected final PackOutput out;


    public ReduxLanguageData(PackOutput output) {
        super(output, Redux.MODID);
        this.out = output;
    }

    @Override
    protected void addTranslations() {


        addItem(ReduxItems.VANILLA_GUMMY_SWET, "Vanilla Gummy Swet");
        addLore(ReduxItems.VANILLA_GUMMY_SWET, "A sweet smelling gummy, it can be found in random chests in Bronze and Silver dungeons. It restores the player's hunger to full. Very useful for boss fights.");

        addItem(ReduxItems.SUBZERO_CROSSBOW, "Subzero Crossbow");
        addLore(ReduxItems.SUBZERO_CROSSBOW, "An icy crossbow found in Gold Dungeons. This slows enemies when they are hit.");

        addItem(ReduxItems.SPEAR_OF_THE_BLIGHT, "Spear of Regeneration");
        addLore(ReduxItems.SPEAR_OF_THE_BLIGHT, "A spear crafted with a Goodbunny Tooth, Skyroot Stick, and Zanite Gemstone. This nifty tool can be used to inflict Regeneration on enemies! It's not very useful against most mobs, but for undead ones it's nice, and it can be used to heal allies as well.");

        addItem(ReduxItems.ZANBERRY, "Zanberry");
        addLore(ReduxItems.ZANBERRY, "A type of berry found on bushes in the Skyroot Shrublands. This can be eaten, and nourishes slightly more hunger than Blueberries.");

        addItem(ReduxItems.BLUE_SWET_JELLY, "Blue Swet Jelly");
        addLore(ReduxItems.BLUE_SWET_JELLY, "A delicious treat made with the remains of a Blue Swet.");
        addItem(ReduxItems.GOLDEN_SWET_JELLY, "Golden Swet Jelly");
        addLore(ReduxItems.GOLDEN_SWET_JELLY, "A delicious treat made with the remains of a Golden Swet.");
        addItem(ReduxItems.VANILLA_SWET_JELLY, "Vanilla Swet Jelly");
        addLore(ReduxItems.VANILLA_SWET_JELLY, "A delicious treat made with the remains of a Vanilla Swet.");
        addItem(ReduxItems.GOLDEN_SWET_BALL, "Golden Swet Ball");
        addLore(ReduxItems.GOLDEN_SWET_BALL, "A glowing, gooey orb of a Golden Swet's gel.");
        addItem(ReduxItems.VANILLA_SWET_BALL, "Vanilla Swet Ball");
        addLore(ReduxItems.VANILLA_SWET_BALL, "A gooey, sweet-smelling orb of a Vanilla Swet's gel. True to the name, this smells like vanilla.");

        addItem(ReduxItems.WYNDSPROUT_SEEDS, "Wyndsprout Seeds");
        addLore(ReduxItems.WYNDSPROUT_SEEDS, "Some seeds from the wild Wyndsprout plant. Can be grown or eaten.");
        addItem(ReduxItems.BUNDLE_OF_WYNDSPROUTS, "Bundle of Wyndsprouts");
        addLore(ReduxItems.BUNDLE_OF_WYNDSPROUTS, "A bundle of grown Wyndsprouts. Flying Cows like to munch on these.");

        addItem(ReduxItems.SKYSPROUT_SEEDS, "Skysprout Seeds");
        addLore(ReduxItems.SKYSPROUT_SEEDS, "Some seeds from the wild Skysprout plant. Can be grown and harvested for a drop that makes some decoration blocks.");
        addItem(ReduxItems.SKYBUD, "Skybud");
        addLore(ReduxItems.SKYBUD, "A flower bud from some fully grown Skysprouts. Can be used to make some nice Flower Garland!");

        addItem(ReduxItems.BLUEBERRY_PIE, "Blueberry Pie");
        addLore(ReduxItems.BLUEBERRY_PIE, "A pie made of the Aether's native blueberries. These treats taste amazing, and can make a great gift for a friend.");
        addItem(ReduxItems.ENCHANTED_BLUEBERRY_PIE, "Enchanted Blueberry Pie");
        addLore(ReduxItems.ENCHANTED_BLUEBERRY_PIE, "An enchanted variant of the Blueberry Pie. This fancy treat gives you a short regeneration effect!");

        addItem(ReduxItems.LEMON_POPROCKS, "Lemon Poprocks");
        addLore(ReduxItems.LEMON_POPROCKS, "Some delicious poprocks found in the Sugarfields!");

        addItem(ReduxItems.ENCHANTED_RING, "Enchanted Ring");
        addLore(ReduxItems.ENCHANTED_RING, "A ring with a large amount of magical potential. You can use it to craft many other types of rings with a variety of abilities!");

        addItem(ReduxItems.RING_OF_WISDOM, "Ring of Wisdom");
        addLore(ReduxItems.RING_OF_WISDOM, "One of many variations of the enchanted ring, this one is crafted using Holystone and a strange green crystal not native to the Aether, known as an Emerald");

        addItem(ReduxItems.SENTRY_RING, "Sentry Ring");
        addLore(ReduxItems.SENTRY_RING, "One of many different variations of the enchanted ring. When worn, hitting mobs will release buring blue embers that can strike nearby mobs!");

        addItem(ReduxItems.SHROOM_RING, "Shroom Ring");
        addLore(ReduxItems.SHROOM_RING, "One of many different variations of the enchanted ring. When worn, taking damage with low health will have a chance to give an Adrenaline effect, giving a temporary stat boost in turn for a stat debuff when it expires.");

        addItem(ReduxItems.VAMPIRE_AMULET, "Vampire Amulet");
        addLore(ReduxItems.VAMPIRE_AMULET, "A pendant found in Bronze Dungeon reward chests. It gives the wearer life steal at night, but decreases their damage resistance at day");

        addItem(ReduxItems.AIRBOUND_CAPE, "Airbound Cape");
        addLore(ReduxItems.AIRBOUND_CAPE, "A cape found in the Bronze Dungeon. It allows the wearer to double-jump!");

        addItem(ReduxItems.GRAND_VICTORY_MEDAL, "Grand Victory Medal");
        addLore(ReduxItems.GRAND_VICTORY_MEDAL, "A pendant dropped by the Valkyrie Queen. In Valkyrie culture, having one of these shows you are a noble warrior.");

        addItem(ReduxItems.SOLAR_EMBLEM, "Solar Emblem");
        addLore(ReduxItems.SOLAR_EMBLEM, "An ancient emblem once used by followers of the Sun Spirit. It allows the wearer to shoot fireballs!");

        addItem(ReduxItems.VANILLA_SWET_SPAWN_EGG, "Vanilla Swet Spawn Egg");
        addLore(ReduxItems.VANILLA_SWET_SPAWN_EGG, "A spawn egg. Spawns a Vanilla Swet.");

        addItem(ReduxItems.SHIMMERCOW_SPAWN_EGG, "Crazy Cow Spawn Egg");
        addLore(ReduxItems.SHIMMERCOW_SPAWN_EGG, "A spawn egg. Spawns a Crazy Cow.");


        addItem(ReduxItems.MYKAPOD_SPAWN_EGG, "Milkapod Spawn Egg");
        addLore(ReduxItems.MYKAPOD_SPAWN_EGG, "A spawn egg. Spawns a Milkapod.");

        addItem(ReduxItems.BLIGHTBUNNY_SPAWN_EGG, "Goodbunny Spawn Egg");
        addLore(ReduxItems.BLIGHTBUNNY_SPAWN_EGG, "A spawn egg. Spawns a Goodbunny.");

        addItem(ReduxItems.PHUDGE_SPAWN_EGG, "Phudge Spawn Egg");
        addLore(ReduxItems.PHUDGE_SPAWN_EGG, "A spawn egg. Spawns a Phudge.");

        addItem(ReduxItems.BLIGHTED_SPORES, "Mini Hearts");
        addLore(ReduxItems.BLIGHTED_SPORES, "Some heart-shaped objects from the Hallowed tree. They can make some things into their nice versions, such as Mossy Holystone to Nicemoss Holystone");

        addItem(ReduxItems.VERIDIUM_DART, "Veridium Dart");
        addLore(ReduxItems.VERIDIUM_DART, "The ammo for the Veridium Dart Shooter. When shot with an Infused Veridium Dart Shooter, hit enemies will glow!");
        addItem(ReduxItems.VERIDIUM_DART_SHOOTER, "Veridium Dart Shooter");
        addLore(ReduxItems.VERIDIUM_DART_SHOOTER, "A Dart Shooter which shoots Veridium Darts. This can be infused, by right-clicking it with an ambrosium shard!");
        addItem(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER, "Infused Veridium Dart Shooter");
        addLore(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER, "A Dart Shooter which shoots Veridium Darts. This can be infused, by right-clicking it with an ambrosium shard!");

        addItem(ReduxItems.SLIDER_MUSIC_DISC, "Slider Music Disc");
        addDiscDesc(ReduxItems.SLIDER_MUSIC_DISC, "Emile van Krieken - Labyrinth's Vengeance");
        addLore(ReduxItems.SLIDER_MUSIC_DISC, "A music disc that plays Labyrinth's Vengeance by Emile van Krieken. These are dropped on occasion when a Sentry kills another Sentry.");

        addItem(ReduxItems.MOUSE_EAR_SOUP, "Mouse Ear Soup");
        addLore(ReduxItems.MOUSE_EAR_SOUP, "Mouse Ear Soup is a surprisingly good dish, but it will certainly not get you praise from most. However, while most people will raise their eyebrows wondering why in Veradex you have leather ears in a bowl of water, a select few will recognize your true culinary genious, gaining you their utmost and complete respect.");


        addBlock(ReduxBlocks.SHORT_AETHER_GRASS, "Aether Grass");
        addLore(ReduxBlocks.SHORT_AETHER_GRASS, "Blades of the Aether's grass. It feels slightly cool to the touch.");

        addBlock(ReduxBlocks.AVELIUM, "Moovelium");
        addLore(ReduxBlocks.AVELIUM, "A fungal grass-like growth that spreads across Aether Dirt. Can be found in the NotMoolands.");

        addBlock(ReduxBlocks.DEEP_GRASS_BLOCK, "Deep Grass Block");
        addLore(ReduxBlocks.DEEP_GRASS_BLOCK, "A shiny, familiar block of grass found in the Deeper Aether.");

        addBlock(ReduxBlocks.SHORT_DEEP_GRASS, "Deep Grass");
        addLore(ReduxBlocks.SHORT_DEEP_GRASS, "Blades of the Deeper Aether biome's shiny grass.");

        addBlock(ReduxBlocks.SUGARGRASS_BLOCK, "Sugargrass Block");
        addLore(ReduxBlocks.SUGARGRASS_BLOCK, "sugar on some Cocoa Soil, how on earth this can exist naturally is unknown.");

        addBlock(ReduxBlocks.SUGARSPROUTS, "Sugarsprouts");
        addLore(ReduxBlocks.SUGARSPROUTS, "Formations that grow out of the sugar crystals of Sugargrass. Despite being pure crystal, it is still referred to with a plant-like name.");

        addBlock(ReduxBlocks.COCOA_SOIL, "Cocoa Soil");
        addLore(ReduxBlocks.COCOA_SOIL, "Essentially the Sugarfields' version of dirt. It seems to be dark chocolate.");

        addBlock(ReduxBlocks.PINK_CANDYFLOSS_BLOCK, "Pink Candyfloss Block");
        addLore(ReduxBlocks.PINK_CANDYFLOSS_BLOCK, "A big cube of pure Pink Cotton Candy, also known as Candyfloss. Tasty!");

        addBlock(ReduxBlocks.BLUE_CANDYFLOSS_BLOCK, "Blue Candyfloss Block");
        addLore(ReduxBlocks.BLUE_CANDYFLOSS_BLOCK, "A big cube of pure Blue Cotton Candy, also known as Candyfloss. Tasty!");

        addBlock(ReduxBlocks.PAPER_PILLAR, "Paper Pillar");
        addLore(ReduxBlocks.PAPER_PILLAR, "A pillar constructed from paper. These are commonly found holding up the top parts of the large Cotton Candy found in the Sugarfields!");

        addBlock(ReduxBlocks.PAPER_BLOCK, "Paper Block");
        addLore(ReduxBlocks.PAPER_BLOCK, "A bunch of paper compressed into a singular block. The pillar variant of this is common in the Sugarfields.");

        addBlock(ReduxBlocks.VANILLA, "Vanilla");
        addBlock(ReduxBlocks.POTTED_VANILLA, "Potted Vanilla");
        addLore(ReduxBlocks.VANILLA, "Finally, after all these years, they added vanilla to the Aether!!!");

        addBlock(ReduxBlocks.FLAWLESS_BLOOM, "Flawless Bloom");
        addBlock(ReduxBlocks.POTTED_FLAWLESS_BLOOM, "Potted Flawless Bloom");
        addLore(ReduxBlocks.FLAWLESS_BLOOM, "A flower found in the Deeper Aether biome. It seems almost like it was bestowed upon the world by an undamaged creature of great power.");

        addBlock(ReduxBlocks.ROOTROSE, "Rootrose");
        addBlock(ReduxBlocks.POTTED_ROOTROSE, "Potted Rootrose");
        addLore(ReduxBlocks.ROOTROSE, "A flower found in the Deeper Aether biome. It almost seems like it could be a tree, if it really wanted to.");

        addBlock(ReduxBlocks.EDELWEISS, "Edelweiss");
        addBlock(ReduxBlocks.POTTED_EDELWEISS, "Potted Edelweiss");
        addLore(ReduxBlocks.EDELWEISS, "A nice flower found in the Crystallium Woods biome.");

        addBlock(ReduxBlocks.JELLYSHROOM, "Jelloshroom");
        addBlock(ReduxBlocks.POTTED_JELLYSHROOM, "Potted Jelloshroom");
        addLore(ReduxBlocks.JELLYSHROOM, "A gelatinous mushroom found in the Cowcaps and the Sugarfields.");

        addBlock(ReduxBlocks.SHIMMERSTOOL, "Wackyshroom");
        addBlock(ReduxBlocks.POTTED_SHIMMERSTOOL, "Potted Wackyshroom");
        addLore(ReduxBlocks.SHIMMERSTOOL, "A short mushroom found in the Cowcaps. These are most commonly found nearby rocks. They can also sometimes be found on the backs of the native Crazy Cow!");

        addBlock(ReduxBlocks.CLOUD_CAP_BLOCK, "Cow Cap Block");
        addLore(ReduxBlocks.CLOUD_CAP_BLOCK, "The cap of the large Cowcap Mushrooms, found in the Cowcaps.");
        addBlock(ReduxBlocks.CLOUDCAP_SPORES, "Cowcap Spores");
        addLore(ReduxBlocks.CLOUDCAP_SPORES, "A block of spores from the large Cowcap Mushrooms, found in the Cowcaps.");

        addBlock(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK, "Jelloshroom Jello Block");
        addLore(ReduxBlocks.JELLYSHROOM_JELLY_BLOCK, "Jelly from a large Jelloshroom, which can be found in the Cowcaps.");

        addBlock(ReduxBlocks.COARSE_AETHER_DIRT, "Coarse Aether Dirt");
        addLore(ReduxBlocks.COARSE_AETHER_DIRT, "A variant of Aether Dirt that does not regrow any grass.");

        addBlock(ReduxBlocks.PEPPERMINT_LOG, "Peppermint Log");
        addLore(ReduxBlocks.PEPPERMINT_LOG, "The logs of the large Candy Canes in the Sugarfields. This can be stripped into Minty Logs, or turned into Minty Planks!");

        addBlock(ReduxBlocks.PEPPERMINT_BLOCK, "Peppermint Block");
        addLore(ReduxBlocks.PEPPERMINT_BLOCK, "The all-sided variant of Peppermint Logs.");

        addBlock(ReduxBlocks.BLIGHTMOSS_BLOCK, "Nicemoss Block");
        addLore(ReduxBlocks.BLIGHTMOSS_BLOCK, "Moss that has been befriended by the Unblight. This can be found in some caves in the Aether.");
        addBlock(ReduxBlocks.BLIGHTMOSS_CARPET, "Nicemoss Carpet");
        addLore(ReduxBlocks.BLIGHTMOSS_CARPET, "A thin blanket of Nicemoss. This can be found in some caves in the Aether.");


        addBlock(ReduxBlocks.FUNGAL_GROWTH, "Fungal Growth");
        addLore(ReduxBlocks.FUNGAL_GROWTH, "A fungal spread found in some caves in the Aether");
        addBlock(ReduxBlocks.FUNGAL_CARPET, "Fungal Carpet");
        addLore(ReduxBlocks.FUNGAL_CARPET, "A thin blanket of fungal growth. This can be found in some caves in the Aether.");

        addBlock(ReduxBlocks.GOLDEN_VINES, "Golden Vines");
        addBlock(ReduxBlocks.GOLDEN_VINES_PLANT, "Golden Vines Plant");
        addLore(ReduxBlocks.GOLDEN_VINES, "A type of gold-colored vine that commonly grows under the leaves of Golden Oak trees.");
        addBlock(ReduxBlocks.GILDED_VINES, "Gilded Vines");
        addBlock(ReduxBlocks.GILDED_VINES_PLANT, "Gilded Vines Plant");
        addLore(ReduxBlocks.GILDED_VINES, "A type of off-white vine that commonly grows under the leaves of Gilded Oak trees.");


        addBlock(ReduxBlocks.CORRUPTED_VINES, "Aer o'Stalks");
        addBlock(ReduxBlocks.CORRUPTED_VINES_PLANT, "Aer o'Stalks Plant");
        addLore(ReduxBlocks.CORRUPTED_VINES, "it will never be graviton reference ...              :cereal_spittake:");

        addBlock(ReduxBlocks.LUXWEED, "Lightsprouts");
        addBlock(ReduxBlocks.POTTED_LUXWEED, "Potted Lightsprouts");
        addLore(ReduxBlocks.LUXWEED, "A pretty glowing plant found in the Unblight biome. These are a relative of Wyndsprouts.");

        addBlock(ReduxBlocks.WYNDSPROUTS, "Wyndsprouts");
        addBlock(ReduxBlocks.POTTED_WYNDSPROUTS, "Potted Wyndsprouts");
        addLore(ReduxBlocks.WYNDSPROUTS, "A common plant found in the Aether. They occasionally drop Oats, the main edible source of grain in the Aether.");

        addBlock(ReduxBlocks.SKYSPROUTS, "Skysprouts");
        addBlock(ReduxBlocks.POTTED_SKYSPROUTS, "Potted Skysprouts");
        addLore(ReduxBlocks.SKYSPROUTS, "A relative of the common Wyndsprouts, this flowering grass is found in the Skyfields.");

        addBlock(ReduxBlocks.ZANBERRY_BUSH, "Zanberry Bush");
        addLore(ReduxBlocks.ZANBERRY_BUSH, "A nice bush of Zanberries!");

        addBlock(ReduxBlocks.ZANBERRY_BUSH_STEM, "Zanberry Bush Stem");
        addBlock(ReduxBlocks.POTTED_ZANBERRY_BUSH_STEM, "Potted Zanberry Bush Stem");
        addLore(ReduxBlocks.ZANBERRY_BUSH_STEM, "The stem of the Zanberry Bush. This will grow into a full bush, and will have Zanberries, which can be picked!");

        addBlock(ReduxBlocks.CLOUDCAP_MUSHLING, "Cowcap Mushling");
        addBlock(ReduxBlocks.POTTED_CLOUDCAP_MUSHLING, "Potted Cowcap Mushling");
        addLore(ReduxBlocks.CLOUDCAP_MUSHLING, "A mushroom found commonly in the Cowcaps. It glows faintly.");

        addBlock(ReduxBlocks.SPIROLYCTIL, "Spirolyctil");
        addBlock(ReduxBlocks.POTTED_SPIROLYCTIL, "Potted Spirolyctil");
        addLore(ReduxBlocks.SPIROLYCTIL, "A pretty purple flower found in the Unblight. It smells kinda like pickles.");

        addBlock(ReduxBlocks.BLIGHTSHADE, "Moonbud");
        addBlock(ReduxBlocks.POTTED_BLIGHTSHADE, "Potted Moonbud");
        addLore(ReduxBlocks.BLIGHTSHADE, "What on earth are these doing here??? These are supposed to be in 2.1- I MEAN UHH THAT'S CANCELLED ITS THIS UPDATE INSTEAD ((((:::   (very real, very true)");

        addBlock(ReduxBlocks.BLIGHTWILLOW_LEAVES, "Heartwillow Leaves");
        addLore(ReduxBlocks.BLIGHTWILLOW_LEAVES, "The leaves of the Heartwillow tree. These sometimes drop Heartwillow Saplings.");

        addBlock(ReduxBlocks.FIELDSPROOT_LEAVES, "Prismroot Leaves");
        addLore(ReduxBlocks.FIELDSPROOT_LEAVES, "The colorful leaves of the flowering Prismroot trees! These will occasionally drop Prismroot Saplings.");
        addBlock(ReduxBlocks.FIELDSPROOT_PETALS, "Prismroot Petals");
        addLore(ReduxBlocks.FIELDSPROOT_PETALS, "The vibrant petals of Prismroot trees, which are found commonly underneath them.");

        addBlock(ReduxBlocks.PRISMATIC_FIELDSPROOT_LEAVES, "Prismatic Prismroot Leaves");
        addLore(ReduxBlocks.PRISMATIC_FIELDSPROOT_LEAVES, "The colorful leaves of the Prismatic Prismroot trees! These will occasionally drop Prismatic Prismroot Saplings.");

        addBlock(ReduxBlocks.AZURE_FIELDSPROOT_LEAVES, "Azure Prismroot Leaves");
        addLore(ReduxBlocks.AZURE_FIELDSPROOT_LEAVES, "The colorful leaves of the Azure Prismroot trees! These will occasionally drop Azure Prismroot Saplings.");

        addBlock(ReduxBlocks.SPECTRAL_FIELDSPROOT_LEAVES, "Spectral Prismroot Leaves");
        addLore(ReduxBlocks.SPECTRAL_FIELDSPROOT_LEAVES, "The colorful leaves of the Spectral Prismroot trees! These will occasionally drop Spectral Prismroot Saplings.");

        addBlock(ReduxBlocks.GOLDEN_LEAF_PILE, "Golden Leaf Pile");
        addLore(ReduxBlocks.GOLDEN_LEAF_PILE, "A pile of Golden Oak Leaves. These can be found under Golden Oak trees.");
        addBlock(ReduxBlocks.GILDED_LEAF_PILE, "Gilded Leaf Pile");
        addLore(ReduxBlocks.GILDED_LEAF_PILE, "A pile of Gilded Oak Leaves. These can be found under Gilded Oak trees..");
        addBlock(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, "Heartwillow Leaf Pile");
        addLore(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, "A pile of Heartwillow Leaves. These can be found in the Unblight biome.");

        addBlock(ReduxBlocks.BLIGHTWILLOW_SAPLING, "Heartwillow Sapling");
        addBlock(ReduxBlocks.POTTED_BLIGHTWILLOW_SAPLING, "Potted Heartwillow Sapling");
        addLore(ReduxBlocks.BLIGHTWILLOW_SAPLING, "The sapling of the Heartwillow tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.PEPPERMINT_BARKLING, "Peppermint Barkling");
        addBlock(ReduxBlocks.POTTED_PEPPERMINT_BARKLING, "Potted Peppermint Barkling");
        addLore(ReduxBlocks.PEPPERMINT_BARKLING, "A small peppermint growth obtained by stripping a Peppermint Log. Can be grown with Bone Meal into a full-sized Candy Cane!");

        addBlock(ReduxBlocks.BLUE_COTTON_CANDY, "Blue Cotton Candy");
        addBlock(ReduxBlocks.POTTED_BLUE_COTTON_CANDY, "Potted Blue Cotton Candy");
        addLore(ReduxBlocks.BLUE_COTTON_CANDY, "A miniature cotton candy blob on a stick! Yum!");

        addBlock(ReduxBlocks.PINK_COTTON_CANDY, "Pink Cotton Candy");
        addBlock(ReduxBlocks.POTTED_PINK_COTTON_CANDY, "Potted Pink Cotton Candy");
        addLore(ReduxBlocks.PINK_COTTON_CANDY, "A miniature cotton candy blob on a stick! Yum!");

        addBlock(ReduxBlocks.FIELDSPROOT_SAPLING, "Prismroot Sapling");
        addBlock(ReduxBlocks.POTTED_FIELDSPROOT_SAPLING, "Potted Prismroot Sapling");
        addLore(ReduxBlocks.FIELDSPROOT_SAPLING, "The sapling of the colorful Prismroot tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING, "Prismatic Prismroot Sapling");
        addBlock(ReduxBlocks.POTTED_PRISMATIC_FIELDSPROOT_SAPLING, "Potted Prismatic Prismroot Sapling");
        addLore(ReduxBlocks.PRISMATIC_FIELDSPROOT_SAPLING, "The sapling of the Prismatic Prismroot tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.AZURE_FIELDSPROOT_SAPLING, "Azure Prismroot Sapling");
        addBlock(ReduxBlocks.POTTED_AZURE_FIELDSPROOT_SAPLING, "Potted Azure Prismroot Sapling");
        addLore(ReduxBlocks.AZURE_FIELDSPROOT_SAPLING, "The sapling of the Azure Prismroot tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.SPECTRAL_FIELDSPROOT_SAPLING, "Spectral Prismroot Sapling");
        addBlock(ReduxBlocks.POTTED_SPECTRAL_FIELDSPROOT_SAPLING, "Potted Spectral Prismroot Sapling");
        addLore(ReduxBlocks.SPECTRAL_FIELDSPROOT_SAPLING, "The sapling of the Spectral Prismroot tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.CRYSTAL_SAPLING, "Crystal Sapling");
        addBlock(ReduxBlocks.POTTED_CRYSTAL_SAPLING, "Potted Crystal Sapling");
        addLore(ReduxBlocks.CRYSTAL_SAPLING, "A sapling of a Crystal tree. This can be grown, or enchanted at an altar into the fruit variant.");

        addBlock(ReduxBlocks.CRYSTAL_FRUIT_SAPLING, "Crystal Fruit Sapling");
        addBlock(ReduxBlocks.POTTED_CRYSTAL_FRUIT_SAPLING, "Potted Crystal Fruit Sapling");
        addLore(ReduxBlocks.CRYSTAL_FRUIT_SAPLING, "A sapling of a Crystal tree, with fruits on it. This can be grown with bonemeal.");

        addBlock(ReduxBlocks.PURPLE_CRYSTAL_FRUIT_SAPLING, "Purple Crystal Fruit Sapling");
        addBlock(ReduxBlocks.POTTED_PURPLE_CRYSTAL_FRUIT_SAPLING, "Potted Purple Crystal Fruit Sapling");
        addLore(ReduxBlocks.PURPLE_CRYSTAL_FRUIT_SAPLING, "A sapling of a Purple Crystal tree, with fruits on it. This can be grown with bonemeal.");

        addBlock(ReduxBlocks.AVELIUM_ROOTS, "Moo Roots");
        addBlock(ReduxBlocks.POTTED_AVELIUM_ROOTS, "Potted Moo Roots");
        addLore(ReduxBlocks.AVELIUM_ROOTS, "Sprouting roots that grow on Moovelium in the Cowcaps.");

        addBlock(ReduxBlocks.AVELIUM_SPROUTS, "Moo Sprouts");
        addLore(ReduxBlocks.AVELIUM_SPROUTS, "A smaller variant of Moovelium Roots. Can be found in the Cowcaps.");

        addFluidType(ReduxFluidTypes.SYRUP, "Maple Syrup");

        addItem(ReduxItems.SKYROOT_SYRUP_BUCKET, "Skyroot Syrup Bucket");
        addLore(ReduxItems.SKYROOT_SYRUP_BUCKET, "A skyroot bucket filled with delicious Maple Syrup!");
        addItem(ReduxItems.SYRUP_BUCKET, "Syrup Bucket");
        addLore(ReduxItems.SYRUP_BUCKET, "A bucket filled with delicious Maple Syrup!");

        addBlock(ReduxBlocks.GILDED_OAK_LEAVES, "Gilded Oak Leaves");
        addLore(ReduxBlocks.GILDED_OAK_LEAVES, "The leaves of the Gilded Oak tree. These sometimes drop Gilded Oak Saplings.");
        addBlock(ReduxBlocks.GILDED_OAK_SAPLING, "Gilded Oak Sapling");
        addBlock(ReduxBlocks.POTTED_GILDED_OAK_SAPLING, "Potted Gilded Oak Sapling");
        addLore(ReduxBlocks.GILDED_OAK_SAPLING, "The sapling of the Gilded Oak tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES, "Nice Skyroot Leaves");
        addLore(ReduxBlocks.BLIGHTED_SKYROOT_LEAVES, "The leaves of the nice friendly variant of the Skyroot Tree. These sometimes drop Nice Skyroot Saplings.");
        addBlock(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING, "Nice Skyroot Sapling");
        addBlock(ReduxBlocks.POTTED_BLIGHTED_SKYROOT_SAPLING, "Potted Nice Skyroot Sapling");
        addLore(ReduxBlocks.BLIGHTED_SKYROOT_SAPLING, "The sapling of the nice friendly variant of the Skyroot Tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.GLACIA_LEAVES, "Glacia Leaves");
        addLore(ReduxBlocks.GLACIA_LEAVES, "The leaves of the Glacia tree. These sometimes drop Glacia Saplings.");
        addBlock(ReduxBlocks.GLACIA_SAPLING, "Glacia Sapling");
        addBlock(ReduxBlocks.POTTED_GLACIA_SAPLING, "Potted Glacia Sapling");
        addLore(ReduxBlocks.GLACIA_SAPLING, "The sapling of the Glacia tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.PURPLE_GLACIA_LEAVES, "Purple Glacia Leaves");
        addLore(ReduxBlocks.PURPLE_GLACIA_LEAVES, "The leaves of the purple variant of the Glacia tree. These sometimes drop Purple Glacia Saplings.");
        addBlock(ReduxBlocks.PURPLE_GLACIA_SAPLING, "Purple Glacia Sapling");
        addBlock(ReduxBlocks.POTTED_PURPLE_GLACIA_SAPLING, "Potted Purple Glacia Sapling");
        addLore(ReduxBlocks.PURPLE_GLACIA_SAPLING, "The sapling of the purple variant of the Glacia tree. It can be grown by waiting or using Bone Meal.");

        addBlock(ReduxBlocks.DIVINITE, "Divinite");
        addLore(ReduxBlocks.DIVINITE, "A rock found in the Aether. This rock is commonly used in structures built by Valkyries. Additionally, when enchanted, it buds into a dazzlingly bright glowing stone!");
        addBlock(ReduxBlocks.DIVINITE_SLAB, "Divinite Slab");
        addLore(ReduxBlocks.DIVINITE_SLAB, "Crafted from Divinite. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.DIVINITE_STAIRS, "Divinite Stairs");
        addLore(ReduxBlocks.DIVINITE_STAIRS, "Crafted from Divinite. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.DIVINITE_WALL, "Divinite Wall");
        addLore(ReduxBlocks.DIVINITE_WALL, "Crafted from Divinite. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        addBlock(ReduxBlocks.ROCK_CANDY, "Rock Candy");
        addLore(ReduxBlocks.ROCK_CANDY, "A type of crystal found in clusters in the Sugarfields. Sometimes, you'll find Poprock Ore in these clumps!");
        addBlock(ReduxBlocks.ROCK_CANDY_SLAB, "Rock Candy Slab");
        addLore(ReduxBlocks.ROCK_CANDY_SLAB, "Crafted from Rock Candy. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.ROCK_CANDY_STAIRS, "Rock Candy Stairs");
        addLore(ReduxBlocks.ROCK_CANDY_STAIRS, "Crafted from Rock Candy. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.ROCK_CANDY_WALL, "Rock Candy Wall");
        addLore(ReduxBlocks.ROCK_CANDY_WALL, "Crafted from Rock Candy. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        addBlock(ReduxBlocks.HOLYSILT, "Holysilt");
        addLore(ReduxBlocks.HOLYSILT, "Fine particles of broken up holystone and other minerals. Can be found underground, and around the blight. Careful, it's not very stable and may fall beneath your feet!");



        addBlock(ReduxBlocks.CHARCOAL_BLOCK, "Block of Charcoal");
        addLore(ReduxBlocks.CHARCOAL_BLOCK, "A block of compressed Charcoal.");
        addBlock(ReduxBlocks.HARDENED_SYRUP, "Hardened Syrup");
        addLore(ReduxBlocks.HARDENED_SYRUP, "A block of maple syrup, which due to some process or another has hardened.");

        addBlock(ReduxBlocks.SENTRITE, "Sentrite");
        addLore(ReduxBlocks.SENTRITE, "A dark rock found in the Aether. A mixture of this and holystone is what makes up the walls of the Bronze Dungeon.");
        addBlock(ReduxBlocks.SENTRITE_SLAB, "Sentrite Slab");
        addLore(ReduxBlocks.SENTRITE_SLAB, "Crafted from Sentrite. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.SENTRITE_STAIRS, "Sentrite Stairs");
        addLore(ReduxBlocks.SENTRITE_STAIRS, "Crafted from Sentrite. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.SENTRITE_WALL, "Sentrite Wall");
        addLore(ReduxBlocks.SENTRITE_WALL, "Crafted from Sentrite. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        addBlock(ReduxBlocks.SENTRITE_BRICKS, "Sentrite Bricks");
        addLore(ReduxBlocks.SENTRITE_BRICKS, "Bricks made of Sentrite. These can be used as a nice building block!");
        addBlock(ReduxBlocks.SENTRITE_BRICK_SLAB, "Sentrite Brick Slab");
        addLore(ReduxBlocks.SENTRITE_BRICK_SLAB, "Crafted from Sentrite Bricks. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.SENTRITE_BRICK_STAIRS, "Sentrite Brick Stairs");
        addLore(ReduxBlocks.SENTRITE_BRICK_STAIRS, "Crafted from Sentrite Bricks. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.SENTRITE_BRICK_WALL, "Sentrite Brick Wall");
        addLore(ReduxBlocks.SENTRITE_BRICK_WALL, "Crafted from Sentrite Bricks. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        addBlock(ReduxBlocks.HOLEFIRE_STONE, "Holefire Stone");
        addLore(ReduxBlocks.HOLEFIRE_STONE, "The main block making up the Lobotium Dungeon. It is warm to the touch.");
        addBlock(ReduxBlocks.HOLEFIRE_SPIKE, "Holefire Spike");
        addLore(ReduxBlocks.HOLEFIRE_SPIKE, "A sharp spike found in the Lobotium Dungeon. Careful, touching it causes damage!");
        addBlock(ReduxBlocks.GLOWY_HOLEFIRE_STONE, "Glowy Holefire Stone");
        addLore(ReduxBlocks.GLOWY_HOLEFIRE_STONE, "The block lighting up the Lobotium Dungeon.");
        addBlock(ReduxBlocks.HOLEFIRE_SLAB, "Holefire Slab");
        addLore(ReduxBlocks.HOLEFIRE_SLAB, "Crafted from Holefire Stone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.HOLEFIRE_STAIRS, "Holefire Stairs");
        addLore(ReduxBlocks.HOLEFIRE_STAIRS, "Crafted from Holefire Stone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.HOLEFIRE_WALL, "Holefire Wall");
        addLore(ReduxBlocks.HOLEFIRE_WALL, "Crafted from Holefire Stone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");
        addBlock(ReduxBlocks.HOLEFIRE_PILLAR, "Holefire Pillar");
        addLore(ReduxBlocks.HOLEFIRE_PILLAR, "A pillar used in the construction of the Lobotium Dungeon.");
        addBlock(ReduxBlocks.HOLEFIRE_PILLAR_TOP, "Holefire Pillar Top");
        addLore(ReduxBlocks.HOLEFIRE_PILLAR_TOP, "The top part of the pillars in the Lobotium Dungeon.");

        addBlock(ReduxBlocks.BLIGHTED_AERCLOUD, "Nice Aercloud");
        addLore(ReduxBlocks.BLIGHTED_AERCLOUD, "A light teal cloud found in the Unblight. Standing in this will NOT harm you, and will heal nice mobs such as Goodbunnies and Cockatrices!");

        addBlock(ReduxBlocks.RAINBOW_AERCLOUD, "Rainbow Aercloud");
        addLore(ReduxBlocks.RAINBOW_AERCLOUD, "An insane, chromatic cloud type found in the Deeper Aether. Gives you a lot of speed.");

        addBlock(ReduxBlocks.IRIDIA, "Iridia");
        addBlock(ReduxBlocks.POTTED_IRIDIA, "Potted Iridia");
        addLore(ReduxBlocks.IRIDIA, "An iridescent flower found in the Skyfields.");

        addBlock(ReduxBlocks.XAELIA_PATCH, "Xaelia Patch");
        addLore(ReduxBlocks.XAELIA_PATCH, "A small patch of some chromatic flowers found in the Skyfields.");

        addBlock(ReduxBlocks.GILDED_HOLYSTONE, "Gilded Holystone");
        addLore(ReduxBlocks.GILDED_HOLYSTONE, "The enchanted form of Mossy Holystone. This rock covered in golden moss glitters in the sunlight.");
        addBlock(ReduxBlocks.GILDED_HOLYSTONE_SLAB, "Gilded Holystone Slab");
        addLore(ReduxBlocks.GILDED_HOLYSTONE_SLAB, "Crafted from Gilded Holystone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.GILDED_HOLYSTONE_STAIRS, "Gilded Holystone Stairs");
        addLore(ReduxBlocks.GILDED_HOLYSTONE_STAIRS, "Crafted from Gilded Holystone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.GILDED_HOLYSTONE_WALL, "Gilded Holystone Wall");
        addLore(ReduxBlocks.GILDED_HOLYSTONE_WALL, "Crafted from Gilded Holystone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        addBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE, "Nicemoss Holystone");
        addLore(ReduxBlocks.BLIGHTMOSS_HOLYSTONE, "The blighted form of Mosssy Holystone. The viscious moss on this rock wilts in the sunlight and flourishes in the moonlight.");
        addBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB, "Nicemoss Holystone Slab");
        addLore(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_SLAB, "Crafted from Nicemoss Holystone. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS, "Nicemoss Holystone Stairs");
        addLore(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_STAIRS, "Crafted from Nicemoss Holystone. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL, "Nicemoss Holystone Wall");
        addLore(ReduxBlocks.BLIGHTMOSS_HOLYSTONE_WALL, "Crafted from Nicemoss Holystone. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        addBlock(ReduxBlocks.CARVED_STONE_BRICKS, "Carved Stone Bricks");
        addLore(ReduxBlocks.CARVED_STONE_BRICKS, "Bricks made of carved stone. These can be found in the Bronze dungeon.");
        addBlock(ReduxBlocks.CARVED_STONE_PILLAR, "Carved Stone Pillar");
        addLore(ReduxBlocks.CARVED_STONE_PILLAR, "A pillar made of carved stone. Can be found in the Bronze dungeon.");
        addBlock(ReduxBlocks.CARVED_STONE_BRICK_SLAB, "Carved Stone Brick Slab");
        addLore(ReduxBlocks.CARVED_STONE_BRICK_SLAB, "Crafted from Carved Stone Bricks. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.CARVED_STONE_BRICK_STAIRS, "Carved Stone Brick Stairs");
        addLore(ReduxBlocks.CARVED_STONE_BRICK_STAIRS, "Crafted from Carved Stone Bricks. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.CARVED_STONE_BRICK_WALL, "Carved Stone Brick Wall");
        addLore(ReduxBlocks.CARVED_STONE_BRICK_WALL, "Crafted from Carved Stone Bricks. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        addBlock(ReduxBlocks.VERIDIUM_LANTERN, "Veridium Lantern");
        addLore(ReduxBlocks.VERIDIUM_LANTERN, "A lantern made of Veridium. You can place it on the ground or hang it on the ceiling!");

        addBlock(ReduxBlocks.VERIDIUM_CHAIN, "Veridium Chain");
        addLore(ReduxBlocks.VERIDIUM_CHAIN, "A chain made of pure Veridium. This is crafted with a Veridium Ingot and two Veridium Nuggets.");

        addItem(ReduxItems.VERIDIUM_NUGGET, "Veridium Nugget");
        addLore(ReduxItems.VERIDIUM_NUGGET, "A small chunk of Veridium. This can be crafted to and from Veridium Ingots.");

        addItem(ReduxItems.VERIDIUM_PICKAXE, "Veridium Pickaxe");
        addLore(ReduxItems.VERIDIUM_PICKAXE, "A pickaxe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_PICKAXE, "Infused Veridium Pickaxe");
        addLore(ReduxItems.INFUSED_VERIDIUM_PICKAXE, "A pickaxe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addItem(ReduxItems.VERIDIUM_SHOVEL, "Veridium Shovel");
        addLore(ReduxItems.VERIDIUM_SHOVEL, "A shovel made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_SHOVEL, "Infused Veridium Shovel");
        addLore(ReduxItems.INFUSED_VERIDIUM_SHOVEL, "A shovel made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addItem(ReduxItems.VERIDIUM_HOE, "Veridium Hoe");
        addLore(ReduxItems.VERIDIUM_HOE, "A hoe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_HOE, "Infused Veridium Hoe");
        addLore(ReduxItems.INFUSED_VERIDIUM_HOE, "A hoe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addItem(ReduxItems.VERIDIUM_AXE, "Veridium Axe");
        addLore(ReduxItems.VERIDIUM_AXE, "An axe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_AXE, "Infused Veridium Axe");
        addLore(ReduxItems.INFUSED_VERIDIUM_AXE, "An axe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addItem(ReduxItems.VERIDIUM_SWORD, "Veridium Sword");
        addLore(ReduxItems.VERIDIUM_SWORD, "A sword made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
        addItem(ReduxItems.INFUSED_VERIDIUM_SWORD, "Infused Veridium Sword");
        addLore(ReduxItems.INFUSED_VERIDIUM_SWORD, "A sword made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");

        addItem(ReduxItems.VERIDIUM_INGOT, "Veridium Ingot");
        addLore(ReduxItems.VERIDIUM_INGOT, "An bar of pure Veridium, a metal that when coming in contact with ambrosium, takes on a glowing light blue color, strengthening temporarily.");

        addItem(ReduxItems.RAW_VERIDIUM, "Raw Veridium");
        addLore(ReduxItems.RAW_VERIDIUM, "A chunk of Raw Veridium. This can be smelted into an ingot.");

        addItem(ReduxItems.CRYSTAL_GOLD_INGOT, "Crystal Gold Ingot");
        addLore(ReduxItems.CRYSTAL_GOLD_INGOT, "An bar of pure Crystal Gold, which can be used to craft the pillars seen on the sides of Silver Dungeons.");

        addItem(ReduxItems.RAW_CRYSTAL_GOLD, "Raw Crystal Gold");
        addLore(ReduxItems.RAW_CRYSTAL_GOLD, "A chunk of Raw Crystal Gold. This can be smelted into an ingot.");

        addItem(ReduxItems.CRYSTAL_GOLD_NUGGET, "Crystal Gold Nugget");
        addLore(ReduxItems.CRYSTAL_GOLD_NUGGET, "A small chunk of Crystal Gold, crafted with an ingot.");

        addItem(ReduxItems.RAW_GRAVITITE, "Raw Gravitite");
        addLore(ReduxItems.RAW_GRAVITITE, "A chunk of the Aether's pink Gravitite. This can be enchanted at an Altar, and turned into armor and tools with gravity-based abilities!");

        addItem(ReduxItems.RAW_VALKYRUM, "Raw Valkyrum");
        addLore(ReduxItems.RAW_VALKYRUM, "A chunk of a very rare ore, mined with the Pickaxe of Arkenzus. This can be used to make Valkyrum Ingots, which can be turned into Valkyrum armor, weapons, and tools!");

        addItem(ReduxItems.GRAVITITE_INGOT, "Gravitite Ingot");
        addLore(ReduxItems.GRAVITITE_INGOT, "After putting Gravitite Ore into an Altar and enchanting it, you get a Gravitite Ingot. This can be made into gravitite tools and armor.");

        addBlock(ReduxBlocks.GRAVITITE_BLOCK, "Gravitite Block");
        addLore(ReduxBlocks.GRAVITITE_BLOCK, "A block of solid Gravitite. When powered with redstone, this will float!");

        addBlock(ReduxBlocks.RAW_GRAVITITE_BLOCK, "Raw Gravitite Block");
        addLore(ReduxBlocks.RAW_GRAVITITE_BLOCK, "A block of Raw Gravitite. This can be crafted with Raw Gravitite, and acts as a way to store more in a smaller space.");

        addItem(ReduxItems.MYKAPOD_SHELL_CHUNK, "Milkapod Shell Chunk");
        addLore(ReduxItems.MYKAPOD_SHELL_CHUNK, "A fragment of the outer layer of a Milkapod. Can be used to craft the Snailshell Shield!");

        addItem(ReduxItems.SNAILSHELL_SHIELD, "Snailshell Shield");
        addLore(ReduxItems.SNAILSHELL_SHIELD, "A shield accessory. When worn, you will not take any knockback.");

        addItem(ReduxItems.BLIGHTBUNNY_FANG, "Goodbunny Tooth");
        addLore(ReduxItems.BLIGHTBUNNY_FANG, "The tooth of a Goodbunny. These can be used to make the Spear of Regeneration, which gives hit mobs regeneration!");

        addItem(ReduxItems.COCKATRICE_FEATHER, "Cockatrice Feather");
        addLore(ReduxItems.COCKATRICE_FEATHER, "A feather from a Cockatrice. This is said to do absolutely nothing. It can also be crafted into its upgraded variant, the Feather of Warding, which does even less nothing!");

        addItem(ReduxItems.FEATHER_OF_WARDING, "Feather of Warding");
        addLore(ReduxItems.FEATHER_OF_WARDING, "The upgraded variant of the Cockatrice Feather. While having all the benefits of its less powerful version, this also gives the wearer immunity to Inebriation!");

        addItem(ReduxItems.WYNDSPROUT_BAGEL, "Wyndsprout Bagel");
        addLore(ReduxItems.WYNDSPROUT_BAGEL, "A nice bagel made of cultivated Wyndsprouts.");

        addItem(ReduxItems.FIREINTHEHOLE, "FIREINTHEHOLE");
        addLore(ReduxItems.FIREINTHEHOLE, "LDM ball cube ball big cube ball next Rest In Peace niflheim vismuth slow wave fast robot keep going! slow ship go! slow ball Auto? fast dual ship slow fast ufo slow How? fast ship slow cube fast ball lol slow robot in out slow ufo take a break ????? !!!!! ?? ?! ??! !! ?! !! ?!!? ??! !! ?!!? ??! ?! ?? !! ?? !! ?? ?! ??! ?!!? ?? ??! ?! !! ?!!? slow ufo go! go! Yeah! fast cube dual wave watch out! faster! OMG! mini next next great! verified by NIFLHEIM koreaqwer");

        addItem(ReduxItems.BLUEBERRY_BAGEL, "Blueberry Bagel");
        addLore(ReduxItems.BLUEBERRY_BAGEL, "A bagel made with Blue Berries. This is much more filling than a plain Wyndsprout Bagel");

        addItem(ReduxItems.OATMEAL, "Oatmeal");
        addLore(ReduxItems.OATMEAL, "A nice bowl of Oatmeal, made with Wyndsprout Seeds.");

        addBlock(ReduxBlocks.LIGHTROOTS, "Lightroots");
        addItem(ReduxItems.LIGHTROOT_CLUMP, "Lightroot Clump");
        addLore(ReduxItems.LIGHTROOT_CLUMP, "A clump of the glowing roots that grow in the Cowcaps. These can be eaten, and give night vision.");

        addItem(ReduxItems.SENTRY_CHIP, "Sentry Chip");
        addLore(ReduxItems.SENTRY_CHIP, "A circuitboard piece from a Sentry. Nobody knows who created these, just that the Valkyries have utilized them for all of recorded history.");

        addItem(ReduxItems.LOBOTIUM_KEY, "Lobotium Key");
        addLore(ReduxItems.LOBOTIUM_KEY, "A key to open the chest for the Lobotium Dungeon, how convenient! Good thing you got it out of limbo stuck in that item frame, isn't it?");


        addBlock(ReduxBlocks.GOLDEN_CLOVER, "Golden Clover");
        addBlock(ReduxBlocks.POTTED_GOLDEN_CLOVER, "Potted Golden Clover");
        addLore(ReduxBlocks.GOLDEN_CLOVER, "A four-leaved clover found in the Gilded Groves. Makes a nice decoration, and can be placed in a flower pot!");

        addBlock(ReduxBlocks.SPLITFERN, "Splitfern");
        addBlock(ReduxBlocks.POTTED_SPLITFERN, "Potted Splitfern");
        addLore(ReduxBlocks.SPLITFERN, "A soft, fern-like plant with smooth leaves, with a split-end top.");

        addBlock(ReduxBlocks.AURUM, "Aurum");
        addBlock(ReduxBlocks.POTTED_AURUM, "Potted Aurum");
        addLore(ReduxBlocks.AURUM, "A golden flower found in the Gilded Groves. Some say it brings luck!");

        addBlock(ReduxBlocks.ZYATRIX, "Zyatrix");
        addBlock(ReduxBlocks.POTTED_ZYATRIX, "Potted Zyatrix");
        addLore(ReduxBlocks.ZYATRIX, "A purple, tulip-like flower found in the Gilded Grasslands. It shimmers in the moonlight!");

        addBlock(ReduxBlocks.LUMINA, "Lumina");
        addBlock(ReduxBlocks.POTTED_LUMINA, "Potted Lumina");
        addLore(ReduxBlocks.LUMINA, "A flower found in the Frosted Forests. It gives off a glow, lighting the area around it");

        addBlock(ReduxBlocks.DAGGERBLOOM, "Daggerbloom");
        addBlock(ReduxBlocks.POTTED_DAGGERBLOOM, "Potted Daggerbloom");
        addLore(ReduxBlocks.DAGGERBLOOM, "A flower found in the Frosted Forests. Its petals are as cold as ice");

        addBlock(ReduxBlocks.THERATIP, "Theratip");
        addBlock(ReduxBlocks.POTTED_THERATIP, "Potted Theratip");
        addLore(ReduxBlocks.THERATIP, "A flower found growing on coarse dirt in the Skyroot Shrublands. This plant's pretty pink flowers grow in clusters on the top of the stem.");

        addBlock(ReduxBlocks.FLAREBLOSSOM, "Flareblossom");
        addBlock(ReduxBlocks.POTTED_FLAREBLOSSOM, "Potted Flarebloom");
        addLore(ReduxBlocks.FLAREBLOSSOM, "A rare, exotic flower found on Gold Dungeons. Its properties have not yet been discovered...");

        addBlock(ReduxBlocks.INFERNIA, "Infernia");
        addBlock(ReduxBlocks.POTTED_INFERNIA, "Potted Infernia");
        addLore(ReduxBlocks.INFERNIA, "A flower found on Gold Dungeons. Its petals are hot, like a flame");

        addBlock(ReduxBlocks.POPROCK_ORE, "Poprock Ore");
        addLore(ReduxBlocks.POPROCK_ORE, "An ore found in boulders made of Rock Candy. This will drop Lemon Poprocks when mined.");

        addBlock(ReduxBlocks.VERIDIUM_ORE, "Veridium Ore");
        addLore(ReduxBlocks.VERIDIUM_ORE, "The ore of Veridium. This can be found around the Aether");

        addBlock(ReduxBlocks.VERIDIUM_BLOCK, "Block of Veridium");
        addLore(ReduxBlocks.VERIDIUM_BLOCK, "A block of pure Veridium. This can be crafted from Veridium Ingots.");

        addBlock(ReduxBlocks.RAW_VERIDIUM_BLOCK, "Block of Raw Veridium");
        addLore(ReduxBlocks.RAW_VERIDIUM_BLOCK, "A block of raw Veridium. This can be crafted from Raw Veridium.");

        addBlock(ReduxBlocks.CRYSTAL_GOLD_ORE, "Crystal Gold Ore");
        addLore(ReduxBlocks.CRYSTAL_GOLD_ORE, "The ore of Crystal Gold. This can be found around the Aether");

        addBlock(ReduxBlocks.CRYSTAL_GOLD_BLOCK, "Block of Crystal Gold");
        addLore(ReduxBlocks.CRYSTAL_GOLD_BLOCK, "A block of pure Crystal Gold. This can be crafted from Crystal Gold Ingots.");

        addBlock(ReduxBlocks.RAW_CRYSTAL_GOLD_BLOCK, "Block of Raw Crystal Gold");
        addLore(ReduxBlocks.RAW_CRYSTAL_GOLD_BLOCK, "A block of raw Crystal Gold. This can be crafted from Raw Crystal Gold.");

        addBlock(ReduxBlocks.RAW_VALKYRUM_BLOCK, "Block of Raw Valkyrum");
        addLore(ReduxBlocks.RAW_VALKYRUM_BLOCK, "A block of raw Valkyrum. This can be crafted from Raw Valkyrum.");


        addBlock(ReduxBlocks.SHELL_SHINGLES, "Shell Shingles");
        addLore(ReduxBlocks.SHELL_SHINGLES, "Some nice shingles made from the shed shell of the Milkapod. This blast-resistant block can be placed by dispensers and broken by pistons.");
        addBlock(ReduxBlocks.SHELL_SHINGLE_SLAB, "Shell Shingle Slab");
        addLore(ReduxBlocks.SHELL_SHINGLE_SLAB, "Crafted from Shell Shingles. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.SHELL_SHINGLE_STAIRS, "Shell Shingle Stairs");
        addLore(ReduxBlocks.SHELL_SHINGLE_STAIRS, "Crafted from Shell Shingles. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.SHELL_SHINGLE_WALL, "Shell Shingle Wall");
        addLore(ReduxBlocks.SHELL_SHINGLE_WALL, "Crafted from Shell Shingles. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");

        addBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLES, "Enchanted Shell Shingles");
        addLore(ReduxBlocks.ENCHANTED_SHELL_SHINGLES, "Shell Shingles that have been enchanted. These have been hardened back into the form of the Milkapod's inner shell, making them take longer to break.");
        addBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB, "Enchanted Shell Shingle Slab");
        addLore(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_SLAB, "Crafted from Enchanted Shell Shingles. Slabs are half blocks, versatile for decoration and smooth slopes. Try adding some to a building's roofing!");
        addBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS, "Enchanted Shell Shingle Stairs");
        addLore(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_STAIRS, "Crafted from Enchanted Shell Shingles. Stairs are useful for adding verticality to builds and are often used for decoration too!");
        addBlock(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL, "Enchanted Shell Shingle Wall");
        addLore(ReduxBlocks.ENCHANTED_SHELL_SHINGLE_WALL, "Crafted from Enchanted Shell Shingles. Can be used for decorative enclosures and defences. Great for keeping nasty intruders away!");


        addBiome(ReduxBiomes.CLOUDCAPS, "Cowcaps");
        addBiome(ReduxBiomes.FROSTED_FORESTS, "Frosted Forests");
        addBiome(ReduxBiomes.GLACIAL_TUNDRA, "Glacial Tundra");
        addBiome(ReduxBiomes.GILDED_GROVES, "Gilded Groves");
        addBiome(ReduxBiomes.GILDED_GRASSLANDS, "Gilded Grasslands");
        addBiome(ReduxBiomes.SKYFIELDS, "Skyfields");
        addBiome(ReduxBiomes.THE_BLIGHT, "The Nice");
        addBiome(ReduxBiomes.SKYROOT_SHRUBLANDS, "Skyroot Shrublands");

        add("item.minecraft.potion.effect.intoxication", "Potion of Intoxication");
        add("item.minecraft.splash_potion.effect.intoxication", "Splash Potion of Intoxication");
        add("item.minecraft.lingering_potion.effect.intoxication", "Lingering Potion of Intoxication");
        add("item.minecraft.tipped_arrow.effect.intoxication", "Arrow of Intoxication");

        add("menu.aether_redux.lobotium_treasure_chest", "Lobotium Treasure Chest");
        addGeneric("lobotium_treasure_chest_locked", "This Treasure Chest must be unlocked with a Lobotium Key.");

        addGuiText("bittersweet_charm_poison_chance", "10% chance to poison targets of melee attacks");
        addGuiText("wisdom_ring_xp_increase", "+20-30% XP from mobs");
        addGuiText("grand_medal_regen", "Regeneration which increases as health drops");
        addGuiText("grand_medal_queen_refight", "Allows replaying the Valkyrie Queen fight without collecting more medals");
        addGuiText("airbound_cape_jump_boost", "Grants ability to double jump");
        addGuiText("solar_emblem_fireball", "Grants ability to shoot fireballs");
        addGuiText("sentry_ring_embers", "Targets of melee attacks release burning embers");
        addGuiText("shroom_ring_adrenaline", "Taking damage at low health gives an Adrenaline effect for temporary stat boosts, at a price of a debuff soon after");
        addGuiText("cockatrice_feather_protection", "literally does nothing");
        addGuiText("feather_of_warding_immunity", "does less than nothing");

        addGuiText("player.dialog.has_grand_medal", "I'm ready, I have a Grand Victory Medal!");

        addGuiText("vampire_amulet_night_ability", "After being worn for one overworld day (as symbolized by a gold highlight), will grant the ability to absorb dealt damage");
        addGuiText("vampire_amulet_day_debuff", "Causes more damage to be taken at day");

        addGuiText("shift_info", "Hold [%s] for more info...");
        addGuiText("infusion_tooltip", "Can be infused by right-clicking with an Ambrosium Shard");

        addGuiText("luxbuds_tooltip", "Occasionally gives the helpful Niceward effect when eaten");
        addGuiText("purified_luxbuds_tooltip", "Gives the helpful Niceward effect when eaten");

        addGuiText("funny_speed_boost", "You feel the Wind™ carry you beneath your feet.");


        addGuiText("jade.entity_model", " (Model changed by the Aether: Redux)");
        add("config.jade.plugin_aether_redux.modification_notices", "Redux - Modify Indicator (Jade)");


        addGuiText("pack_config.file", "Config File: %s.json");
        addGuiText("pack_config.category", "Current Category: %s");
        addGuiText("pack_config.top", "Top of Hierarchy");
        addGuiText("pack_config.page", "Page %s");

        addEntityType(ReduxEntityTypes.VANILLA_SWET, "Vanilla Swet");
        addEntityType(ReduxEntityTypes.SHIMMERCOW, "Crazy Cow");
        addEntityType(ReduxEntityTypes.MYKAPOD, "Milkapod");
        addEntityType(ReduxEntityTypes.BLIGHTBUNNY, "Goodbunny");
        addEntityType(ReduxEntityTypes.PHUDGE, "Phudge");
        addEntityType(ReduxEntityTypes.EMBER, "Ember");
        addEntityType(ReduxEntityTypes.INFUSED_VERIDIUM_DART, "Infused Veridium Dart");
        addEntityType(ReduxEntityTypes.VOLATILE_FIRE_CRYSTAL, "Volatile Fire Crystal");
        addEntityType(ReduxEntityTypes.VERIDIUM_DART, "Veridium Dart");
        addEntityType(ReduxEntityTypes.BLIGHTBUNNY_SPAWNER, "Goodbunny Spawner");
        addEntityType(ReduxEntityTypes.COCKATRICE_SPAWNER, "Cockatrice Spawner");
        addEntityType(ReduxEntityTypes.REBUX, "Rebux");

        addPackConfigEnum("classic", "Classic");
        addPackConfigEnum("modern", "Modern");
        addPackConfigCategory("mob", "Mobs", "Changes to the textures of the Aether's mobs");
        addPackConfig("purple_aechor_plants", "Purple Aechor Plants", "Makes Aechor Plants more consistent with their petal items");
        addPackConfig("cockatrice_texture", "Cockatrice Texture", "Switch between cockatrice textures. Options are Stinky (the newer version of Redux's texture), Classic (original) and Redux Retro (Redux's old texture from indev versions)");
        addPackConfigEnum("blighted", "Stinky");
        addPackConfigEnum("redux_retro", "Redux Retro");
        addPackConfig("tintable_grass", "Tinted Grass", "Use modified models to allow tintable Aether Grass blocks and plants. Only disable if you know what you're doing!");
        addPackConfigCategory("dungeon", "Dungeons", "Changes to the textures of the Aether's dungeons");
        addPackConfigCategory("item", "Items", "Changes to Items from the Aether");
        addPackConfig("swet_ball_type", "Swet Ball Type", "Switch between three different behaviors for Swet Balls: The original, consistently named, and replacing them with Swet Gel");
        addPackConfigEnum("original_ball", "Original");
        addPackConfigEnum("consistent_name", "Consistent Name");
        addPackConfigEnum("gel", "Swet Gel");
        addPackConfig("veridium_type", "Veridium Type", "Switch between three different sprites for the Veridium toolset - The glowing blue Modern variant, the darker and more sleek Shadow variant, and the Classic one based on the old textures for the tools");
        addPackConfigEnum("shadow", "Shadow");
        addPackConfigCategory("gui", "GUI", "Changes to Aether GUI elements");
        addPackConfig("use_jappafied_textures", "Use Jappafied Textures", "Uses textures designed to fit with the Jappafied Aethers resource pack slightly better");
        addPackConfig("auto_apply", "Automatic Application", "Enables the resource pack automatically when removed");
        addPackConfig("smelter_menu_type", "Smelter Menu Type", "Switch between three different types of Aether Smelter Menus: The originals, classic-styled improvemenets, and modernized menus");
        addPackConfigCategory("audio", "Audio", "Changes to different sounds heard in the Aether");
        addPackConfig("better_aechor_sounds", "Upgraded Aechor Plant Sounds", "Improves the sounds of the Aechor Plants, giving them more natural and plant-like sounds.");
        addPackConfig("better_aerwhale_sounds", "Upgraded Aerwhale Sounds", "Improves the sounds of Aerwhales, giving them their sounds heard in early builds of the Aether II: Highlands.");
        addPackConfig("better_cockatrice_sounds", "Upgraded Cockatrice Sounds", "Improves the sounds of Cockatrices, giving them their sounds heard in early builds of the Aether II: Highlands.");
        addPackConfig("better_mimic_awaken_sound", "Upgraded Mimic Awaken Sound", "Improves the sound of a Mimic appearing, making you slightly hear a sound similar to when you awaken the Slider.");
        addPackConfig("better_moa_sounds", "Upgraded Moa Sounds", "Improves the sounds of Moas, giving them their sounds heard in early builds of the Aether II: Highlands.");
        addPackConfig("better_sentry_sounds", "Upgraded Sentry Sounds", "Improves the sounds of Sentries, giving them some of their sounds heard in early builds of the Aether II: Highlands.");
        addPackConfig("better_slider_sounds", "Upgraded Slider Sounds", "Improves the sounds of the Slider, with more labyrinth-themed ambient droning and a more Sentry-like hurt sound.");
        addPackConfig("better_tempest_sounds", "Upgraded Tempest Sounds (Requires: The Aether: Genesis)", "Improves the sounds of the Aether: Genesis's Tempests, giving them their sounds heard in early builds of the Aether II: Highlands.");

        addMenuTitle("crystallium", "Redux - Crystallium Woods");
        addMenuTitle("deep", "Redux - Deeper Aether");
        addMenuTitle("candy", "Redux - Sugarfields");

        addPackTitle("overrides", "Redux - Aether Overrides");
        addPackDescription("overrides", "Configurable overrides for the Aether: Redux");


        addSubtitle("entity", "mimic_slam", "Mimic slams");
        addSubtitle("entity", "swet_attack", "Swet attacks");
        addSubtitle("entity", "shimmercow_ambient", "Crazy Cow moos");
        addSubtitle("entity", "shimmercow_hurt", "Crazy Cow hurts");
        addSubtitle("entity", "shimmercow_death", "Crazy Cow dies");
        addSubtitle("entity", "crazy_cow_ambient", "Crazy Cow §kvwakazoom moo dae§rs");
        addSubtitle("entity", "crazy_cow_hurt", "Crazy Cow §kscream§rs");
        addSubtitle("entity", "crazy_cow_death", "Crazy Cow §kasks u why u did dis§r");
        addSubtitle("entity", "quail_ambient", "Definitely Quail™ screeches");
        addSubtitle("entity", "quail_hurt", "Definitely Quail™ hurts");
        addSubtitle("entity", "quail_death", "Definitely Quail™ dies");
        addSubtitle("entity", "sentry_pounce", "Sentry pounces");
        addSubtitle("entity", "sentry_land", "Sentry lands");
        addSubtitle("entity", "sentry_ambient", "Sentry grumbles");
        addSubtitle("entity", "ember_bounce", "Ember impacts");
        addSubtitle("entity", "mykapod_shell_crack", "Milkapod shell cracks");
        addSubtitle("entity", "mykapod_shell_shed", "Milkapod sheds");
        addSubtitle("entity", "mykapod_shell_break", "Milkapod shell breaks");
        addSubtitle("entity", "mykapod_move", "Milkapod squelches");
        addSubtitle("entity", "mykapod_hurt", "Milkapod hurts");
        addSubtitle("entity", "mykapod_death", "Milkapod dies");
        addSubtitle("entity", "blightbunny_hurt", "Goodbunny hurts");
        addSubtitle("entity", "blightbunny_death", "Goodbunny dies");
        addSubtitle("entity", "jellobunny_hurt", "Jellobunny hurts");
        addSubtitle("entity", "jellobunny_death", "Jellobunny dies");
        addSubtitle("entity", "phudge.ambient", "Phudge squeals");
        addSubtitle("entity", "phudge.angry", "Phudge snorts angrily");
        addSubtitle("entity", "phudge.hurt", "Phudge hurts");
        addSubtitle("entity", "phudge.death", "Phudge dies");
        addSubtitle("entity", "phudge.saddle", "Saddle equips");
        addSubtitle("item.accessory", "equip_bittersweet_charm", "Bittersweet Charm jingles");
        addSubtitle("item.accessory", "equip_enchanted_ring", "Enchanted Ring thunks");
        addSubtitle("item.accessory", "equip_shroom_ring", "Room Shing thunks");
        addSubtitle("item.accessory", "equip_grand_medal", "Grand Victory Medal clinks");
        addSubtitle("item.accessory", "equip_wisdom_ring", "Ring of Wisdom clanks");
        addSubtitle("item.accessory", "equip_sentry_ring", "Sentry Ring clanks");
        addSubtitle("item.accessory", "equip_vampire_amulet", "Vampire Amulet clinks");
        addSubtitle("item", "lobotomy_eat", "'FI-FI-FIRE IN DA HOLE!!!'");
        addSubtitle("item", "lobotomy_effect", "'FIRE IN THE HOLE!'");
        addSubtitle("item", "lobotomy_effect_high", "'FEER IN DA HEEL!'");
        addSubtitle("generic", "boost_jump", "Something double-jumps");
        addSubtitle("generic", "fireball_shoot", "Fireball shoots");
        addSubtitle("generic", "coin_pickup", "Rebux jingles");
        addSubtitle("block", "blightshade_spray", "Niceshade sprays");
        addSubtitle("item", "convert_ambrosium", "Ambrosium enchants");
        addSubtitle("item", "convert_swet_ball", "Swet Ball squelches");
        addSubtitle("item", "convert_blighted_spores", "Mini Hearts transform");
        addSubtitle("item", "infuse_item", "Item infuses");
        addSubtitle("item", "infusion_expire", "Infusion expires");
        addSubtitle("item", "poprocks_crackle", "Poprocks crackle");
        addSubtitle("item", "spear_throw", "Spear wooshes");
        addSubtitle("item", "spear_land", "Spear clings");
        addSubtitle("block", "quickroots_pick", "Quickroot pops");
        addSubtitle("block", "lightroots_pick", "Lightroot Clump pops");
        addSubtitle("block", "fungus_bounce", "Springjelly Shroom bounces with its Spring Jelly");
        addSubtitle("block", "aercloud.gold_aercloud_whoosh", "Golden Aercloud whooshes");
        addSubtitle("block", "aercloud.purple_aercloud_zoom", "Purple Aercloud zooms");
        addSubtitle("block", "aercloud.green_aercloud_wubble", "Green Aercloud wubbles");
        addSubtitle("block", "aercloud.rainbow_cloud_start", "Rainbow Aercloud warps");
        addSubtitle("block", "aercloud.rainbow_cloud_end", "Rainbow Aercloud launches");

        addSubtitle("item", "subzero_crossbow_load", "Subzero Crossbow loads");
        addSubtitle("item", "subzero_crossbow_charge", "Subzero Crossbow charges");
        addSubtitle("item", "subzero_crossbow_shoot", "Subzero Crossbow shoots");
        addSubtitle("item", "subzero_crossbow_hit", "Subzero Arrow hits");

        addKeyInfo("category", "The Aether: Redux");
        addKeyInfo("shoot_fireball.desc", "Shoot Fireball");

        addEffect(ReduxEffects.BLIGHTWARD, "this shouldn't even exist anymore what");
        addEffect(ReduxEffects.ADRENALINE_RUSH, "Adrenaline Rush");
        addEffect(ReduxEffects.ADRENAL_FATIGUE, "Adrenal Fatigue");
        addEffect(ReduxEffects.THE_LOBOTOMY, "The Lobotomy");

        addTooltip("ambrosium_charge", "Infusion Level: %s");

        addGuiText("welcome_line1", "Welcome to the Aether: Redux!");
        addGuiText("welcome_line2", "This mod has a lot of configuration in the configs, resource packs, and data packs.");
        addGuiText("welcome_line3", "Be sure to check them all out to see all of the cool features!");
        addGuiText("welcome_line4", "This message will only appear once.");
        addGuiText("continue_delay", "The continue button will unlock in %s seconds.");
        addGuiText("continue_delay_expired", "The continue button has been unlocked.");
        addGuiText("continue_delay_unfocused", "Delay paused until window refocused! Time remaining: %s seconds");
        addGuiText("continue_delay_unmoved", "Please move the mouse to begin continue button cooldown.");
        addGuiText("continue_to_menu", "Continue to Main Menu");
        addGuiText("jei.infusion_charge", "Increases Infusion Level by %s");
        addGuiText("jei.infusion", "Infusion");
        addGuiText("coin_count", "Rebux: %s");
        addCommand("capability.player.rebux.set", "Set Rebux count for %s to %s");
        addCommand("capability.player.rebux.add", "Added %s Rebux to %s's count");
        addCommand("capability.player.rebux.query", "Player %s has %s Rebux");


        addGuiText("advancement_suffix", "\n- The Aether: Redux -");
        addGuiText("advancement_suffix_modified", "\n- Modified by the Aether: Redux -");


        addAdvancement("base", "The Aether: Redux");
        addAdvancementDesc("base", "Classic hostile paradise, with some upgrades and tune-ups");
        addAdvancement("install", "YOU HAVE BEEN THE INSTALL !!");
        addAdvancementDesc("install", "Thank you for downloading the Aether: Redux's Update Negative Omega Point Zero Zero One version!!!");
        addAdvancement("hug", "OMG how wholesome!!!");
        addAdvancementDesc("hug", "Get hugged by a Cockatrice");
        addAdvancement("nineteen_rebux", "19 Dollar Rebux Card");
        addAdvancementDesc("nineteen_rebux", "Collect 19 Rebux");
        addAdvancement("rebuxionaire", "Rebuxionaire");
        addAdvancementDesc("rebuxionaire", "Collect 100 Rebux");
        addAdvancement("eat_sugarfields", "Everything here is eatable");
        addAdvancementDesc("eat_sugarfields", "Eat a block from the Sugarfields");
        addAdvancement("enter_deeper", "The Deepest of Aethers");
        addAdvancementDesc("enter_deeper", "Enter the Deeper Aether biome, a shimmering landscape with unusual wildlife");
        addAdvancement("crystal_gold", "I can’t believe it’s not Mutation!");
        addAdvancementDesc("crystal_gold", "Mine some Crystal Gold from the Crystallium Woods");
        addAdvancement("lobotium_dungeon", "Where the holes once had fires…");
        addAdvancementDesc("lobotium_dungeon", "Discover a Lobotium Dungeon");
        addAdvancement("holefire_spike", "NEW BEST: 99%");
        addAdvancementDesc("holefire_spike", "Take damage from a Holefire Spike");
        addAdvancement("phudge_bite", "Even I'M eatable");
        addAdvancementDesc("phudge_bite", "But that is called 'cannibalism', my dear friends, and is in fact frowned upon in most societies");
        addAdvancement("fall_from_aether", "Falling with Style!");
        addAdvancementDesc("fall_from_aether", "Fall out of the Aether");
        addAdvancement("infuse_veridium", "Next Level");
        addAdvancementDesc("infuse_veridium", "Infuse a Veridium weapon or tool by right-clicking it with an Ambrosium Shard");
        addAdvancement("enter_blight", "Shattered Glass");
        addAdvancementDesc("enter_blight", "Enter the Unblight biome, a dangerous zone filled with hazardous plants and creatures");
        addAdvancement("convert_with_blighted_spores", "Dark Magic");
        addAdvancementDesc("convert_with_blighted_spores", "good friend nice-ify a block by right-clicking it while holding some Mini Hearts");
        addAdvancement("obtain_subzero_crossbow", "Below Zero");
        addAdvancementDesc("obtain_subzero_crossbow", "Obtain the Subzero Crossbow, a chilling ranged weapon found in the Gold Dungeon");

        addAdvancementDesc("gravitite_ingot", "Use an Altar to obtain a Gravitite Ingot");

        addAdvancement("feather_of_warding", "It's less than worthless, my friend!");
        addAdvancementDesc("feather_of_warding", "Obtain the Feather of Warding, which does nothing.");

        addAdvancement("double_jump", "Murderer of Logic");
        addAdvancementDesc("double_jump", "Obtain an Airbound cape from a Bronze Dungeon and use it to destroy physics with facts and logicc.");
        addAdvancement("grand_medal", "Didn't I just get like 10 of these?");
        addAdvancementDesc("grand_medal", "Obtain the Grand Victory Medal, a symbol of great honor in Valkyrie culture, which will give you health regen, scaling up as you lose health!");
        addAdvancement("kill_sheepuff_with_fireball", "There is no fire in this hole");
        addAdvancementDesc("kill_sheepuff_with_fireball", "Obtain the Solar Emblem from the Gold Dungeon and kill a Sheepuff with a fireball. How cruel ):");

        addAdvancement("enter_skyfields", "Prismacolor Photosynthesis");
        addAdvancementDesc("enter_skyfields", "Enter the Skyfields, a biome filled with colorful flora and abundant fauna");

        addAdvancement("enter_gilded_groves", "All That Glitters");
        addAdvancementDesc("enter_gilded_groves", "Enter the Gilded Groves, a golden forest filled with Golden Amber");

        addAdvancement("enter_frosted_forests", "Walking in a Winter Wonderland" );
        addAdvancementDesc("enter_frosted_forests", "Enter the Frosted Forests, an ice-cold forest covered in snow");

        addAdvancement("enter_cloudcap_jungle", "Moolander");
        addAdvancementDesc("enter_cloudcap_jungle", "Enter the Cowcaps, a fungal forest with various types of mushrooms");

        addAdvancement("mouse_ear_soup", "Who Let Them Cook?");
        addAdvancementDesc("mouse_ear_soup", "Craft and cosume the legendary Mouse Ear Soup, one of the Aether's finest, yet least well-known dishes.");

        addDeath(ReduxDamageTypes.ZANBERRY_BUSH, "%1$s was poked to death by a zanberry bush, how sad ):");
        addDeath(ReduxDamageTypes.CORRUPTED_VINES, "%1$s was poked to death by some Corrupted Vines");
        addDeath(ReduxDamageTypes.EMBER, "%1$s was sparked by a flying ember");
        addDeath(ReduxDamageTypes.SPIKE, "%1$s forgot to jump");
        addDeath(ReduxDamageTypes.SPIKE_FALL, "%1$s was susceptible to a blind jump");
        addDeathByPlayer(ReduxDamageTypes.EMBER, "%1$s was sparked by %2$s's flying ember");
        addDeath(ReduxDamageTypes.BLIGHT, "%1$s was uhhhh uhm uhh nothing happened to them");
        addDeathByPlayer(ReduxDamageTypes.BLIGHT, "%1$s was uhhhh uhm uhh nothing happened to them but if it did you should ask %2$s about it");
        addDeath(ReduxDamageTypes.SWET, "%1$s was absorbed by %2$s");
//        addDeathByPlayer(ReduxDamageTypes.SWET, "%1$s was absorbed by %2$s");
        
        addProTipNoCompat("tip_color", "Tips from the Aether: Redux are in this lavender color!");
        addGuiTextTip("tip_color", "Tips from the Aether: Redux have a lavender-colored title!");

        addProTip("veridium_infusion", "Veridium tools can be temporarily infused into better versions by right-clicking them with an Ambrosium Shard!");
        addProTip("model_changes", "The Aether: Redux has some model changes to some of the Aether’s mobs, which can be toggled in the client-side config!");
        addProTip("feather_of_warding", "Sometimes, Cockatrices will drop one of their feathers, which, plot twist, do nothing.");
        addProTip("oatmeal", "Wyndsprout seeds can be crafed into Oatmeal, a nice snack that can fill up a few hunger points for relatively cheap.");
        addProTip("blight_spear", "Goodbunny teeth can be used to create the Spear of Regeneration, a throwable weapon that inflicts Regeneration on enemies, and is useful against undead mobs");
        addProTip("wyndsprouts", "You can plant Wyndsprout seeds, and when they finish growing, you can use their drops to create tasty Bagels!");
        addProTip("quicksoil", "Be careful on quicksoil, it may seem fun, but its slipperiness can cause you to fall off the island if you're not careful!");
        addProTip("enchanted_ring", "With some planks and an Ambrosium Shard, you can craft the Enchanted Ring, which can be turned into various different useful rings!");
        addProTip("veridium_ore", "On occasion, you'll find a blue ore in the Aether known as Veridium. This can be used to make some decoration blocks, as well as useful Veridium tools!");
        addProTip("the_blight", "Be careless of the purple Unblight biome found in the Aether, it is filled with no dangers at all nope");
        addProTip("snailshell_shield", "Feeding a Milkapod a Wackyshroom will make it shed the outer layer of its shell. These shell fragments can be used to craft a useful accessory that will decrease knockback.");
        addProTip("divinite", "Divinite can be enchanted into Glowstone, giving a relatively easy way to leave the Aether safely if you get stuck.");
        addProTip("redux_configs", "The Aether: Redux has a large amount of configs, including many that affect the base mod, so make sure to check out its config file!");
        addProTip("swet_escape", "You can escape a Swet's gelatinous grasp by sneaking. It will still attack you though, so be quick!");

        for (WoodHandler woodHandler : Redux.WoodHandlers.WOOD_HANDLERS) {
            woodHandler.generateLanguageData(this);
        }

    }


    public void addTooltip(String key, String name) {
        this.add("tooltip." + Redux.MODID + "." + key, name);
    }

    public void addPackConfig(String key, String name) {
        this.addGuiText("pack_config.config." + key, name);
    }
    public void addPackConfigDesc(String key, String name) {
        this.addGuiText("pack_config.config_desc." + key, name);
    }
    public void addPackConfigCategory(String key, String name) {
        this.addGuiText("pack_config.category." + key, name);
    }
    public void addPackConfigCategoryDesc(String key, String name) {
        this.addGuiText("pack_config.category_desc." + key, name);
    }
    public void addPackConfigEnum(String key, String name) {
        this.addGuiText("pack_config.enums." + key, name);
    }

    public void addPackConfigCategory(String key, String name, String desc) {
        this.addPackConfigCategory(key, name);
        this.addPackConfigCategoryDesc(key, desc);
    }


    public void addDeath(ResourceKey<DamageType> key, String name) {
        this.add("death.attack." + this.id + "." + key.location().getPath(), name);
    }
    public void addDeathByPlayer(ResourceKey<DamageType> key, String name) {
        this.add("death.attack." + this.id + "." + key.location().getPath() + ".player", name);
    }

    public void addPackConfig(String key, String name, String desc) {
        this.addPackConfig(key, name);
        this.addPackConfigDesc(key, desc);
    }

    @Override
    public void addBiome(ResourceKey<Biome> biome, String name) {
        super.addBiome(biome, name);
        this.add("biomes." + this.id + "." + biome.location().getPath(), name);
    }

    public void addProTip(String key, String name) {
        String fullKey = "aether.pro_tips.line." + this.id + "." + key;
        this.add(fullKey, name);
        this.TIPS.put(fullKey, key);
    }
    public void addProTipNoCompat(String key, String name) {
        String fullKey = "aether.pro_tips.line." + this.id + "." + key;
        this.add(fullKey, name);
    }
    public void addGuiTextTip(String key, String name) {
        this.addGuiText(key, name);
        this.TIPS.put("gui." + this.id + "." + key, key);
    }
    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return generateTips(super.run(cache), cache);
    }

    private CompletableFuture<?> generateTips(CompletableFuture<?> languageGen, CachedOutput cache) {
        ImmutableList.Builder<CompletableFuture<?>> futuresBuilder = new ImmutableList.Builder<>();
        futuresBuilder.add(languageGen);

        for (Map.Entry<String, String> entry : this.TIPS.entrySet()) {
            JsonObject object = new JsonObject();
            object.add("title", Component.Serializer.toJsonTree(Component.translatable("tipsmod.title.default").withStyle(ChatFormatting.BOLD, ChatFormatting.UNDERLINE).withStyle(style -> style.withColor(Redux.REDUX_PURPLE))));
            object.add("tip", Component.Serializer.toJsonTree(Component.translatable((entry).getKey()).withStyle(ChatFormatting.WHITE)));
            futuresBuilder.add(DataProvider.saveStable(cache, GSON.toJsonTree(object), this.out.getOutputFolder().resolve("packs/resource/redux_tips/assets/aether_redux/tips/" + entry.getValue() + ".json")));
        }

        return CompletableFuture.allOf(futuresBuilder.build().toArray(CompletableFuture[]::new));
    }

    public void addFluidType(Supplier<? extends FluidType> key, String name) {
        this.add(/*"fluid_type." + */key.get().getDescriptionId(), name);
    }
}
