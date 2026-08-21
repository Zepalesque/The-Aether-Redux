package net.zepalesque.redux.item;

import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.resource.registries.ReduxJukeboxSongs;
import net.zepalesque.redux.entity.ReduxEntities;
import net.zepalesque.redux.item.accessories.cape.AerboundCapeItem;
import net.zepalesque.redux.item.combat.VeridiumDartItem;
import net.zepalesque.redux.item.combat.VeridiumDartShooter;
import net.zepalesque.redux.item.combat.VeridiumSwordItem;
import net.zepalesque.redux.item.misc.WillowSporesItem;
import net.zepalesque.redux.item.property.ReduxFoods;
import net.zepalesque.redux.item.property.ReduxItemTiers;
import net.zepalesque.redux.item.tools.VeridiumAxeItem;
import net.zepalesque.redux.item.tools.VeridiumHoeItem;
import net.zepalesque.redux.item.tools.VeridiumPickaxeItem;
import net.zepalesque.redux.item.tools.VeridiumShovelItem;

public class ReduxItems extends ReduxItemBuilders {
	public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Redux.MODID);

	public static final DeferredItem<Item> BLUEBERRY_PIE = ITEMS.register(
		"blueberry_pie",
		() -> new Item(new Item.Properties().food(ReduxFoods.BLUEBERRY_PIE))
	);
	public static final DeferredItem<Item> ENCHANTED_BLUEBERRY_PIE = ITEMS.register(
		"enchanted_blueberry_pie",
		() -> new Item(new Item.Properties().food(ReduxFoods.ENCHANTED_BLUEBERRY_PIE))
	);

	public static final DeferredItem<ItemNameBlockItem> WYND_OATS = ITEMS.register("wynd_oats", () ->
		new ItemNameBlockItem(
			ReduxBlocks.WYNDOATS.get(),
			new Item.Properties().food(ReduxFoods.WYND_OATS)
		)
	);

	public static final DeferredItem<Item> WYND_OAT_PANICLE = ITEMS.registerSimpleItem("wynd_oat_panicle");

	public static final DeferredItem<PlaceOnWaterBlockItem> CAELGAE_PATCH = ITEMS.register(
		"caelgae_patch",
		() -> new PlaceOnWaterBlockItem(ReduxBlocks.CAELGAE_PATCH.get(), new Item.Properties())
	);

	public static final DeferredItem<Item> CAELGAE_CLUMP = ITEMS.register(
		"caelgae_clump",
		() -> new Item(new Item.Properties().food(ReduxFoods.CAELGAE_CLUMP))
	);

	public static final DeferredItem<Item> SEAWEED_SALAD = ITEMS.register(
		"seaweed_salad",
		() -> new Item(new Item.Properties().food(ReduxFoods.SEAWEED_SALAD).stacksTo(1))
	);

	public static final DeferredItem<Item> RAW_VERIDIUM = ITEMS.registerSimpleItem("raw_veridium");
	public static final DeferredItem<Item> VERIDIUM_INGOT = ITEMS.registerSimpleItem("veridium_ingot");
	public static final DeferredItem<Item> VERIDIUM_NUGGET = ITEMS.registerSimpleItem("veridium_nugget");

	public static final DeferredItem<Item> REFINED_SENTRITE = ITEMS.registerSimpleItem("refined_sentrite");
	public static final DeferredItem<Item> SENTRITE_CHUNK = ITEMS.registerSimpleItem("sentrite_chunk");
	
	public static final DeferredItem<ShearsItem> SENTRITE_SHEARS = ITEMS.register(
		"sentrite_shears",
		() -> new ShearsItem(new Item.Properties().durability(242).component(DataComponents.TOOL, ShearsItem.createToolProperties()))
	);

	public static final DeferredItem<VeridiumPickaxeItem.Uninfused> VERIDIUM_PICKAXE = ITEMS.register(
		"veridium_pickaxe",
		() -> new VeridiumPickaxeItem.Uninfused(
			ReduxItemTiers.VERIDIUM,
			new Item.Properties().attributes(PickaxeItem.createAttributes(ReduxItemTiers.VERIDIUM, 0, -2.9F))
		)
	);
	public static final DeferredItem<VeridiumAxeItem.Uninfused> VERIDIUM_AXE = ITEMS.register(
		"veridium_axe",
		() -> new VeridiumAxeItem.Uninfused(
			ReduxItemTiers.VERIDIUM,
			new Item.Properties().attributes(AxeItem.createAttributes(ReduxItemTiers.VERIDIUM, 1, 0 - 3.2F))
		)
	);
	public static final DeferredItem<VeridiumShovelItem.Uninfused> VERIDIUM_SHOVEL = ITEMS.register(
		"veridium_shovel",
		() -> new VeridiumShovelItem.Uninfused(
			ReduxItemTiers.VERIDIUM,
			new Item.Properties().attributes(ShovelItem.createAttributes(ReduxItemTiers.VERIDIUM, 0, -3.1F))
		)
	);
	public static final DeferredItem<VeridiumHoeItem.Uninfused> VERIDIUM_HOE = ITEMS.register(
		"veridium_hoe",
		() -> new VeridiumHoeItem.Uninfused(
			ReduxItemTiers.VERIDIUM,
			new Item.Properties().attributes(HoeItem.createAttributes(ReduxItemTiers.VERIDIUM, 0, -1.7F))
		)
	);
	public static final DeferredItem<VeridiumPickaxeItem> INFUSED_VERIDIUM_PICKAXE = ITEMS.register(
		"infused_veridium_pickaxe",
		() -> new VeridiumPickaxeItem(
			ReduxItemTiers.INFUSED_VERIDIUM,
			new Item.Properties().attributes(PickaxeItem.createAttributes(ReduxItemTiers.INFUSED_VERIDIUM, 1, -2.6F)),
			VERIDIUM_PICKAXE
		)
	);
	public static final DeferredItem<VeridiumAxeItem> INFUSED_VERIDIUM_AXE = ITEMS.register(
		"infused_veridium_axe",
		() -> new VeridiumAxeItem(
			ReduxItemTiers.INFUSED_VERIDIUM,
			new Item.Properties().attributes(AxeItem.createAttributes(ReduxItemTiers.INFUSED_VERIDIUM, 1, -2.9F)),
			VERIDIUM_AXE
		)
	);
	public static final DeferredItem<VeridiumShovelItem> INFUSED_VERIDIUM_SHOVEL = ITEMS.register(
		"infused_veridium_shovel",
		() -> new VeridiumShovelItem(
			ReduxItemTiers.INFUSED_VERIDIUM,
			new Item.Properties().attributes(ShovelItem.createAttributes(ReduxItemTiers.INFUSED_VERIDIUM, 0, -2.8F)),
			VERIDIUM_SHOVEL
		)
	);
	public static final DeferredItem<VeridiumHoeItem> INFUSED_VERIDIUM_HOE = ITEMS.register(
		"infused_veridium_hoe",
		() -> new VeridiumHoeItem(
			ReduxItemTiers.INFUSED_VERIDIUM,
			new Item.Properties().attributes(HoeItem.createAttributes(ReduxItemTiers.INFUSED_VERIDIUM, 0, -1.4F)),
			VERIDIUM_HOE
		)
	);

	public static final DeferredItem<VeridiumSwordItem.Uninfused> VERIDIUM_SWORD = ITEMS.register(
		"veridium_sword",
		() -> new VeridiumSwordItem.Uninfused(
			ReduxItemTiers.VERIDIUM,
			new Item.Properties().attributes(SwordItem.createAttributes(ReduxItemTiers.VERIDIUM, 2, -2.5F))
		)
	);
	public static final DeferredItem<VeridiumSwordItem> INFUSED_VERIDIUM_SWORD = ITEMS.register(
		"infused_veridium_sword",
		() -> new VeridiumSwordItem(
			ReduxItemTiers.INFUSED_VERIDIUM,
			new Item.Properties().attributes(SwordItem.createAttributes(ReduxItemTiers.VERIDIUM, 4, -2.2F)),
			VERIDIUM_SWORD
		)
	);

	public static final DeferredItem<VeridiumDartItem> VERIDIUM_DART = ITEMS.register(
		"veridium_dart",
		() -> new VeridiumDartItem(new Item.Properties())
	);
	public static final DeferredItem<VeridiumDartShooter.Uninfused> VERIDIUM_DART_SHOOTER = ITEMS.register(
		"veridium_dart_shooter",
		() -> new VeridiumDartShooter.Uninfused(VERIDIUM_DART, 9, new Item.Properties().stacksTo(1))
	);
	public static final DeferredItem<VeridiumDartShooter> INFUSED_VERIDIUM_DART_SHOOTER =
		ITEMS.register("infused_veridium_dart_shooter", () ->
			new VeridiumDartShooter(
				VERIDIUM_DART,
				7,
				new Item.Properties().stacksTo(1),
				ReduxItems.VERIDIUM_DART_SHOOTER
			)
		);

	public static final DeferredItem<Item> MUSIC_DISC_SENTIENCE = ITEMS.register(
		"music_disc_sentience",
		() -> new Item(
			new Item.Properties()
				.stacksTo(1)
				.rarity(Rarity.RARE)
				.jukeboxPlayable(ReduxJukeboxSongs.SENTIENCE)
		)
	);

	public static final DeferredItem<WillowSporesItem> WILLOW_SPORES = ITEMS.register(
		"willow_spores",
		() -> new WillowSporesItem(new Item.Properties())
	);
	
	public static final DeferredItem<Item> TURBO_VERBENA = ITEMS.register(
		"turbo_verbena",
		() -> new Item(
			new Item.Properties()
				.food(ReduxFoods.TURBO_VERBENA)
		)
	);

	public static final DeferredItem<AerboundCapeItem> AERBOUND_CAPE = ITEMS.register(
		"aerbound_cape",
		() -> new AerboundCapeItem(new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT))
	);

	public static final DeferredItem<DeferredSpawnEggItem> ARTEMID_SPAWN_EGG = ITEMS.register(
		"artemid_spawn_egg",
		() -> new DeferredSpawnEggItem(
			ReduxEntities.ARTEMID,
			0x595d66,
			0xa8bddd,
			new Item.Properties()
		)
	);
	public static final DeferredItem<DeferredSpawnEggItem> CAT_FISH_SPAWN_EGG = ITEMS.register(
		"cat_fish_spawn_egg",
		() -> new DeferredSpawnEggItem(
			ReduxEntities.CAT_FISH,
			0x907365,
			0xFAF3E3,
			new Item.Properties()
		)
	);

	public static void registerAccessories() {
		registerAccessory(AERBOUND_CAPE.get());
	}
}
