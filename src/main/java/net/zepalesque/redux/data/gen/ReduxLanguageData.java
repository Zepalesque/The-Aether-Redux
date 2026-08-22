package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.audio.ReduxSounds;
import net.zepalesque.redux.config.ReduxConfig;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;
import net.zepalesque.redux.data.resource.registries.ReduxJukeboxSongs;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.ReduxItems;

public class ReduxLanguageData extends ReduxLanguageProvider {
	public ReduxLanguageData(PackOutput output) {
		super(output, Redux.MODID);
	}

	@Override
	@SuppressWarnings("deprecation")
	protected void addTranslations() {
		Redux.BLOCK_SETS.forEach(set -> set.langData(this));
		
		this.addBlock(ReduxBlocks.GILDLEAF_AMBER_LOG);
		this.addLore(ReduxBlocks.GILDLEAF_AMBER_LOG, "TODO");
		this.addBlock(ReduxBlocks.GILDLEAF_AMBER_WOOD);
		this.addLore(ReduxBlocks.GILDLEAF_AMBER_WOOD, "TODO");
		
		this.addBlock(ReduxBlocks.SILVEROOT_LEAVES);
		this.addLore(ReduxBlocks.SILVEROOT_LEAVES, "Leaves of the Silveroot tree, a variation of Skyroot that has been touched by Ambrosium but has not fully adapted as Golden Oaks have. These sometimes will drop Silveroot Saplings");
		this.addBlock(ReduxBlocks.SILVEROOT_LEAF_PILE);
		this.addLore(ReduxBlocks.SILVEROOT_LEAF_PILE, "A pile of Silveroot Leaves. These can be stacked on top of eachother to make various sizes!");
		
		this.addBlock(ReduxBlocks.PRISMA_LEAVES);
		this.addLore(ReduxBlocks.PRISMA_LEAVES, "Leaves of the Prisma tree, a flowering relative of the Aether's rare Crystal trees! These sometimes will drop Prisma Saplings.");
		this.addBlock(ReduxBlocks.PRISMA_LEAF_PILE);
		this.addLore(ReduxBlocks.PRISMA_LEAF_PILE, "A pile of Prisma Leaves. These can be stacked on top of eachother to make various sizes!");
		
		this.addBlock(ReduxBlocks.STORMFIR_LEAVES);
		this.addLore(ReduxBlocks.STORMFIR_LEAVES, "Leaves of the Stormfir tree, a hardy subspecies of moonfir that can withstand even the blight.");
		
		this.addBlock(ReduxBlocks.STORMFIR_LEAF_PILE);
		this.addLore(ReduxBlocks.STORMFIR_LEAF_PILE, "A pile of Stormfir Leaves. These can be stacked on top of eachother to make various sizes!");
		
		this.addBlock(ReduxBlocks.BLIGHTWILLOW_LEAVES);
		this.addLore(ReduxBlocks.BLIGHTWILLOW_LEAVES, "Leaves of the Blightwillow tree, The most common tree found in the Blight. Some biologists across the Aether theorize that these may be a blighted form of Golden Oak trees.");
		
		this.addBlock(ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES);
		this.addLore(ReduxBlocks.INFECTED_BLIGHTWILLOW_LEAVES, "Blightwillow leaves that have been inhabited by the very essence of the Blight itself.");
		
		this.addBlock(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE);
		this.addLore(ReduxBlocks.BLIGHTWILLOW_LEAF_PILE, "A pile of Blightwillow Leaves. These can be stacked on top of eachother to make various sizes!");
		
		this.addBlock(ReduxBlocks.GOLDEN_CLOVERS);
		this.addLore(ReduxBlocks.GOLDEN_CLOVERS, "A nice patch of clovers that can be found in the Gilded Groves.");
		
		this.addBlock(ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK);
		this.addLore(ReduxBlocks.BLIGHTED_AETHER_GRASS_BLOCK, "A block of the Aether's grass which has been corrupted by the Blight.");
		
		this.addBlock(ReduxBlocks.AVELIUM);
		this.addLore(ReduxBlocks.AVELIUM, "A grassy mold inhabiting some Aether Dirt. Can be found in the Cloudcap Ridge!");
		
		this.addBlock(ReduxBlocks.GOLDEN_VINES);
		this.addBlock(ReduxBlocks.GOLDEN_VINES_PLANT);
		this.addLore(ReduxBlocks.GOLDEN_VINES, "A golden vine that grows in a symbiotic relationship with Golden Oak trees.");
		
		this.addBlock(ReduxBlocks.SHADED_VINES);
		this.addBlock(ReduxBlocks.SHADED_VINES_PLANT);
		this.addLore(ReduxBlocks.SHADED_VINES, "A purple relative to Golden Vines. These will oftentimes grow on Blightwillow trees in the Blight.");
		
		this.addBlock(ReduxBlocks.CARVED_PILLAR);
		this.addLore(ReduxBlocks.CARVED_PILLAR, "A pillar made of Carved Stone. Pillars look nice for supporting a build, along with giving it nice corners.");
		this.addBlock(ReduxBlocks.SENTRY_PILLAR);
		this.addLore(ReduxBlocks.SENTRY_PILLAR, "A pillar made of Sentry Stone. Pillars look nice for supporting a build, along with giving it nice corners.");
		this.addBlock(ReduxBlocks.CARVED_BASE);
		this.addLore(ReduxBlocks.CARVED_BASE, "A nice decorative base block made of Carved Stone. Looks very nice at the bottom of walls!");
		this.addBlock(ReduxBlocks.SENTRY_BASE);
		this.addLore(ReduxBlocks.SENTRY_BASE, "A nice decorative base block made of Sentry Stone. Looks very nice at the bottom of walls!");
		
		this.addBlock(ReduxBlocks.LOCKED_CARVED_PILLAR);
		this.addBlock(ReduxBlocks.LOCKED_SENTRY_PILLAR);
		this.addBlock(ReduxBlocks.LOCKED_CARVED_BASE);
		this.addBlock(ReduxBlocks.LOCKED_SENTRY_BASE);
		
		this.addBlock(ReduxBlocks.TRAPPED_CARVED_PILLAR);
		this.addBlock(ReduxBlocks.TRAPPED_SENTRY_PILLAR);
		this.addBlock(ReduxBlocks.TRAPPED_CARVED_BASE);
		this.addBlock(ReduxBlocks.TRAPPED_SENTRY_BASE);
		
		this.addBlock(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR);
		this.addBlock(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR);
		this.addBlock(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE);
		this.addBlock(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE);
		
		this.addBlock(ReduxBlocks.RUNELIGHT);
		this.addLore(ReduxBlocks.RUNELIGHT, "A glowing block of circuitry made of Veridium, which can be easily toggled on and off. Found in Bronze Dungeons.");
		this.addBlock(ReduxBlocks.LOCKED_RUNELIGHT);
		
		this.addBlock(ReduxBlocks.LOCKED_POLISHED_SENTRITE);
		
		this.addBlock(ReduxBlocks.WYNDSPROUTS);
		this.addLore(ReduxBlocks.WYNDSPROUTS, "A common plant found in the Aether. They occasionally drop Wynd Oats, the main edible source of grain in the Aether.");
		
		this.addBlock(ReduxBlocks.LUXWEED);
		this.addLore(ReduxBlocks.LUXWEED, "A blighted relative of Wyndsprouts. It gives off a subtle glow, lighting the area around it.");
		
		this.addBlock(ReduxBlocks.SKYSPROUTS);
		this.addLore(ReduxBlocks.SKYSPROUTS, "A relative of the common Wyndsprouts, this flowering grass is found in the Skyfields.");
		
		this.addBlock(ReduxBlocks.BLEAKMOSS_BLOCK);
		this.addLore(ReduxBlocks.BLEAKMOSS_BLOCK, "A corrupted, blighted variation of the Aether's Flutemoss. This can be found in the Blight, and occasionally underground.");
		this.addBlock(ReduxBlocks.BLEAKMOSS_CARPET);
		this.addLore(ReduxBlocks.BLEAKMOSS_CARPET, "A blanket-like, vegetative layer of Bleakmoss. This has the capacity to grow when bonemealed.");
		
		this.addBlock(ReduxBlocks.GILDENMOSS_BLOCK);
		this.addLore(ReduxBlocks.GILDENMOSS_BLOCK, "The enchanted variation of the Aether's Flutemoss. This can be found in the Blight.");
		this.addBlock(ReduxBlocks.GILDENMOSS_CARPET);
		this.addLore(ReduxBlocks.GILDENMOSS_CARPET, "A blanket-like, vegetative layer of Gildenmoss.");
		
		this.addItem(ReduxItems.AERBOUND_CAPE);
		this.addLore(ReduxItems.AERBOUND_CAPE, "A cape found in the Bronze Dungeon. It allows the wearer to double-jump!");
	    this.addItem(ReduxItems.SENTRY_RING, "Sentry Ring");
        this.addLore(ReduxItems.SENTRY_RING, "One of many different variations of the enchanted ring. When worn, hitting mobs will release buring blue embers that can strike nearby mobs!");

		this.addItem(ReduxItems.WYND_OATS);
		this.addLore(ReduxItems.WYND_OATS, "A pile of Wynd Oats. These can be grown into the Wynd Oat plant.");
		this.addItem(ReduxItems.WYND_OAT_PANICLE);
		this.addLore(ReduxItems.WYND_OAT_PANICLE, "A panicle of grown Wynd Oats. This can be used for a variety of recipes.");

		this.addItem(ReduxItems.BLUEBERRY_PIE, "Blueberry Pie");
		this.addLore(ReduxItems.BLUEBERRY_PIE, "A pie made of the Aether's native blue berries. These delicious treats can make a great gift for a friend.");
		this.addItem(ReduxItems.ENCHANTED_BLUEBERRY_PIE, "Enchanted Blueberry Pie");
		this.addLore(ReduxItems.ENCHANTED_BLUEBERRY_PIE, "An enchanted variant of the Blueberry Pie. This fancy pastry gives you a short regeneration effect!");

		this.addItem(ReduxItems.WYND_BAGEL);
		this.addLore(ReduxItems.WYND_BAGEL, "A nice bagel made with some harvested Wynd Oats.");
		this.addItem(ReduxItems.BLUEBERRY_BAGEL);
		this.addLore(ReduxItems.BLUEBERRY_BAGEL, "A bagel made with Blue Berries. This is much more filling than a plain Wynd Bagel");

		this.addItem(ReduxItems.OATMEAL);
		this.addLore(ReduxItems.OATMEAL, "A nice bowl of Oatmeal. Specifically, this is Wynd Oatmeal, as it was made with Wynd Oats.");
		
		this.addItem(ReduxItems.VERIDIUM_INGOT);
		this.addLore(ReduxItems.VERIDIUM_INGOT, "An bar of pure Veridium, a metal that when coming in contact with ambrosium, takes on a glowing light blue color, strengthening temporarily.");
		this.addItem(ReduxItems.VERIDIUM_NUGGET);
		this.addLore(ReduxItems.VERIDIUM_NUGGET, "A small chunk of Veridium. This can be crafted to and from Veridium Ingots.");
		
		this.addItem(ReduxItems.RAW_VERIDIUM);
		this.addLore(ReduxItems.RAW_VERIDIUM, "A chunk of Raw Veridium. This can be smelted into an ingot.");
		
		this.addItem(ReduxItems.MUSIC_DISC_SENTIENCE, "Ancient Sentrite Music Disc");
		this.addLore(ReduxItems.MUSIC_DISC_SENTIENCE, "A music disc that plays \"Sentience\" by Emile van Krieken.");
		this.addJukeboxSong(ReduxJukeboxSongs.SENTIENCE, "Emile van Krieken - Sentience");
		
		this.addItem(ReduxItems.REFINED_SENTRITE);
		this.addLore(ReduxItems.REFINED_SENTRITE, "The purified form of Sentrite. This can be used for a variety of different things, but is commonly found associated with Sentry technology.");
		
		this.addItem(ReduxItems.SENTRITE_CHUNK);
		this.addLore(ReduxItems.SENTRITE_CHUNK, "A chunk of purified Sentrite. These are occasionally dropped from Sentries, and can be crafted into Refined Sentrite.");
		
		this.addItem(ReduxItems.SENTRITE_SHEARS);
		this.addLore(ReduxItems.SENTRITE_SHEARS, "Shears made of Refined Sentrite. These can be used to shear sheep, gather leaves, and more!");
		
		this.addBlock(ReduxBlocks.SENTRITE_CHAIN);
		this.addLore(ReduxBlocks.SENTRITE_CHAIN, "A chain made of purified Sentrite. This is crafted with a piece of Refined Sentrite and two Sentrite Chunks.");
		this.addBlock(ReduxBlocks.SENTRITE_LANTERN);
		this.addLore(ReduxBlocks.SENTRITE_LANTERN, "A lantern made of purified Sentrite. You can place it on the ground or hang it on the ceiling!");
		this.addBlock(ReduxBlocks.SENTRITE_BARS);
		this.addLore(ReduxBlocks.SENTRITE_BARS, "Metallic bars of purified Sentrite. These can be used as decorative fences or windows!");
		
		this.addBlock(ReduxBlocks.RUNIC_LANTERN);
		this.addLore(ReduxBlocks.RUNIC_LANTERN, "A lantern made with Sentry technology. You can place it on the ground or hang it on the ceiling!");
		
		this.addItem(ReduxItems.VERIDIUM_PICKAXE);
		this.addLore(ReduxItems.VERIDIUM_PICKAXE, "A pickaxe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		this.addItem(ReduxItems.INFUSED_VERIDIUM_PICKAXE);
		this.addLore(ReduxItems.INFUSED_VERIDIUM_PICKAXE, "A pickaxe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		
		this.addItem(ReduxItems.VERIDIUM_AXE);
		this.addLore(ReduxItems.VERIDIUM_AXE, "A axe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		this.addItem(ReduxItems.INFUSED_VERIDIUM_AXE);
		this.addLore(ReduxItems.INFUSED_VERIDIUM_AXE, "A axe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		
		this.addItem(ReduxItems.VERIDIUM_SHOVEL);
		this.addLore(ReduxItems.VERIDIUM_SHOVEL, "A shovel made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		this.addItem(ReduxItems.INFUSED_VERIDIUM_SHOVEL);
		this.addLore(ReduxItems.INFUSED_VERIDIUM_SHOVEL, "A shovel made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		
		this.addItem(ReduxItems.VERIDIUM_SWORD);
		this.addLore(ReduxItems.VERIDIUM_SWORD, "A sword made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		this.addItem(ReduxItems.INFUSED_VERIDIUM_SWORD);
		this.addLore(ReduxItems.INFUSED_VERIDIUM_SWORD, "A sword made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		
		this.addItem(ReduxItems.VERIDIUM_DART_SHOOTER);
		this.addLore(ReduxItems.VERIDIUM_DART_SHOOTER, "A dart shooter made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it shoot faster and apply a glowing effect for a short time!");
		this.addItem(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER);
		this.addLore(ReduxItems.INFUSED_VERIDIUM_DART_SHOOTER, "A dart shooter made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it shoot faster and apply a glowing effect for a short time!");
		this.addItem(ReduxItems.VERIDIUM_DART);
		this.addLore(ReduxItems.VERIDIUM_DART, "A dart made of Veridium. This can be used with a Veridium Dart Shooter");
		
		this.addItem(ReduxItems.VERIDIUM_HOE);
		this.addLore(ReduxItems.VERIDIUM_HOE, "A hoe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		this.addItem(ReduxItems.INFUSED_VERIDIUM_HOE);
		this.addLore(ReduxItems.INFUSED_VERIDIUM_HOE, "A hoe made of Veridium. This can be infused by right-clicking with an Ambrosium Shard to make it far more powerful for a short time!");
		
		this.addEntityType(ReduxEntities.CAT_FISH);
		this.addItem(ReduxItems.CAT_FISH_SPAWN_EGG);
		
		this.addEntityType(ReduxEntities.ARTEMID);
		this.addItem(ReduxItems.ARTEMID_SPAWN_EGG);
		
		this.addBlock(ReduxBlocks.VERIDIUM_ORE);
		this.addLore(ReduxBlocks.VERIDIUM_ORE, "The ore of Veridium. This can be found around the Aether's caves.");
		
		this.addBlock(ReduxBlocks.RAW_VERIDIUM_BLOCK, "Block of Raw Veridium");
		this.addLore(ReduxBlocks.RAW_VERIDIUM_BLOCK, "A block of raw Veridium. This can be crafted from Raw Veridium.");
		
		this.addBlock(ReduxBlocks.VERIDIUM_BLOCK, "Block of Veridium");
		this.addLore(ReduxBlocks.VERIDIUM_BLOCK, "A block of pure Veridium. This can be crafted from Veridium Ingots.");
		
		this.addBlock(ReduxBlocks.REFINED_SENTRITE_BLOCK, "Block of Refined Sentrite");
		this.addLore(ReduxBlocks.REFINED_SENTRITE_BLOCK, "A block of the refined form of Sentrite, crafted with Refined Sentrite.");
		
		this.addBlock(ReduxBlocks.LOGICATOR, "Redstone Logicator");
		this.addLore(ReduxBlocks.LOGICATOR, "A fascinating circuit made with an exotic material not found in the Aether - Redstone. This little diode takes in two inputs on the side, and will perform a logical operation on the two for the output. The operation is controlled by the torch on the top and the back input. The torch controls AND/OR mode, and the back input controls exclusivity (XNOR/XOR).");
		
		this.addBlock(ReduxBlocks.HOLYSILT);
		this.addLore(ReduxBlocks.HOLYSILT, "A fine gravel made of Holystone, among other minerals. It supports its own weight when found naturally, but be careful as stepping on it may destabilize it!");
		
		this.addBlock(ReduxBlocks.CLOUD_CAP);
		this.addLore(ReduxBlocks.CLOUD_CAP, "The cap of the large Cloudcap Mushrooms, found in the Cloudcap Ridge.");
		
		this.addBlock(ReduxBlocks.CLOUDCAP_GILL_BLOCK);
		this.addLore(ReduxBlocks.CLOUDCAP_GILL_BLOCK, "The ribbed, spore-producing undersides of the caps of Cloudcap Mushrooms, found in the Cloudcap Ridge.");
		
		this.addEntityType(ReduxEntities.EMBER);
		this.addEntityType(ReduxEntities.VERIDIUM_DART);
		this.addEntityType(ReduxEntities.INFUSED_VERIDIUM_DART);
		
		this.addItem(ReduxItems.WILLOW_SPORES);
		this.addLore(ReduxItems.WILLOW_SPORES, "Spores from the Blightwillow tree. These seem to channel the pure essence of the Blight, and can be used to spread it.");
		
		this.addBlock(ReduxBlocks.VERBENA_CLUSTER);
		this.addLore(ReduxBlocks.VERBENA_CLUSTER, "A plant that grows on Quicksoil. When prepared properly, it can increase your speed when consumed.");
		
		this.addItem(ReduxItems.TURBO_VERBENA);
		this.addLore(ReduxItems.TURBO_VERBENA, "A piece of Turbo Verbena, a succulent which grows on quicksoil. Eating it gives you a very short speed boost!");
		
		this.addBlock(ReduxBlocks.CAELGAE_PATCH);
		this.addLore(ReduxBlocks.CAELGAE_PATCH, "A patch of Caelgae. This can be found floating on lakes!");
		
		this.addItem(ReduxItems.CAELGAE_CLUMP);
		this.addLore(ReduxItems.CAELGAE_CLUMP, "The Aether's native algae species. Can be eaten, or crafted into Seaweed Salad.");
		
		this.addItem(ReduxItems.SEAWEED_SALAD);
		this.addLore(ReduxItems.SEAWEED_SALAD, "A nice dish made from Caelgae and Wynd Oats. Good for exploration!");
		
		this.addBlock(ReduxBlocks.BLOOMTAIL);
		this.addLore(ReduxBlocks.BLOOMTAIL, "An aquatic plant found in the Aether's lakes.");
		
		this.addBlock(ReduxBlocks.ECHYSIA);
		this.addLore(ReduxBlocks.ECHYSIA, "A leafy cave plant. This can be found growing on Flutemoss patches underground!");
		
		this.addBlock(ReduxBlocks.LUNAERA);
		this.addLore(ReduxBlocks.LUNAERA, "A flowering grass found in the skyfields. It absorbs moonlight, making it slightly bioluminescent.");

		this.addBlock(ReduxBlocks.AVELIUM_ROOTS);
		this.addLore(ReduxBlocks.AVELIUM_ROOTS, "Sprouting roots that grow on Avelium in the Cloudcap Ridge.");

		this.addBlock(ReduxBlocks.CLOUDCAP_NETTING);
		this.addLore(ReduxBlocks.CLOUDCAP_NETTING, "The silky, weblike netting of a huge Cloudcap Mushroom. Can be found in the Cloudcap Ridge!");

		this.addTooltip("shift_info", "Hold [%s] for more info...");
		this.addTooltip("infusion_charge", "Infusion Charge: %s");
		this.addTooltip("infusion_info", "Can be infused by right-clicking the item in your inventory while hovering over it with an Ambrosium Shard");
		this.addTooltip("aerbound_cape_aerjump_ability", "Grants ability to double jump by pressing [%s]");
		
		this.addTooltip("cape_modifier", "When on Back");
		
		this.addPackDescription("mod", "The Aether: Redux Resources");
		
		this.addPackTitle("bronze_upgrade", "Redux - Bronze Dungeon Upgrade");
		this.addPackDescription("bronze_upgrade", "Configurable in config/aether_redux/common.toml");
		
		this.addSubtitle(ReduxSounds.INFUSE_ITEM, "Item infuses");
		this.addSubtitle(ReduxSounds.INFUSION_EXPIRE, "Item infusion runs out");
		this.addSubtitle(ReduxSounds.WILLOW_SPORES_CONVERT, "Willow Spores used");
		this.addSubtitle(ReduxSounds.LOGICATOR_CLICK, "Logicator clicks");
		this.addSubtitle(ReduxSounds.AERJUMP, "Something aerjumps");
		this.addSubtitle(ReduxSounds.SLIDER_SIGNAL, "Slider signals");
		this.addSubtitle(ReduxSounds.EMBER_BOUNCE_BIG, "Ember shatters");
		this.addSubtitle(ReduxSounds.EMBER_BOUNCE_MEDIUM, "Ember crackles");
		this.addSubtitle(ReduxSounds.EMBER_BOUNCE_SMALL, "Ember sparks");
		
		this.addBiome(ReduxBiomes.GILDED_GROVES, "Gilded Groves");
		this.addBiome(ReduxBiomes.THE_BLIGHT, "The Blight");
		this.addBiome(ReduxBiomes.FROSTED_FORESTS, "Frosted Forests");
		this.addBiome(ReduxBiomes.SKYFIELDS, "Skyfields");
		this.addBiome(ReduxBiomes.CLOUDCAP_RIDGE, "Cloudcap Ridge");

		this.addGuiText("sentry_ring_embers", "Targets of melee attacks release burning embers");
		
		for (var cfg : ReduxConfig.CLIENT.trans.cfgs()) this.addCfg(cfg);
		for (var cfg : ReduxConfig.COMMON.trans.cfgs()) this.addCfg(cfg);
		for (var cfg : ReduxConfig.SERVER.trans.cfgs()) this.addCfg(cfg);
		
		for (var cat : ReduxConfig.CLIENT.trans.cats().entrySet()) this.add(cat.getKey(), cat.getValue());
		for (var cat : ReduxConfig.COMMON.trans.cats().entrySet()) this.add(cat.getKey(), cat.getValue());
		for (var cat : ReduxConfig.SERVER.trans.cats().entrySet()) this.add(cat.getKey(), cat.getValue());
		
		this.addAdvancement("throw_ring_in_lava", "Cast It into the Fire! Destroy It!", "Throw a Golden Ring into lava in the Nether");
	}
}
