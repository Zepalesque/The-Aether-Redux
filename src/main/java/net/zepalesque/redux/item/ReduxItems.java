package net.zepalesque.redux.item;

import com.aetherteam.aether.item.AetherItems;
import com.aetherteam.aether.item.accessories.ring.RingItem;
import com.aetherteam.aether.item.combat.DartItem;
import com.aetherteam.aether.item.combat.DartShooterItem;
import com.aetherteam.aether.item.food.GummySwetItem;
import com.aetherteam.aether.item.materials.SwetBallItem;
import com.google.common.base.Supplier;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.client.audio.ReduxSoundEvents;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.item.accessory.*;
import net.zepalesque.redux.item.food.ReduxFoods;
import net.zepalesque.redux.item.misc.BlightedSporesItem;
import net.zepalesque.redux.item.misc.TooltipItem;
import net.zepalesque.redux.item.tools.VeridiumAxeItem;
import net.zepalesque.redux.item.tools.VeridiumHoeItem;
import net.zepalesque.redux.item.tools.VeridiumPickaxeItem;
import net.zepalesque.redux.item.tools.VeridiumShovelItem;
import net.zepalesque.redux.item.util.ReduxItemTiers;
import net.zepalesque.redux.item.weapons.SubzeroCrossbowItem;
import net.zepalesque.redux.item.weapons.VeridiumSwordItem;
import org.jetbrains.annotations.Nullable;

import java.util.function.UnaryOperator;

public class ReduxItems {

    public static class TooltipUtils {
        /**
         * Returns the given component, or the default message if the shift key is up.
         */
        public static final UnaryOperator<Component> SHIFT_OR_DEFAULT = (component -> Screen.hasShiftDown() ? component : Component.translatable("gui.aether_redux.shift_info", Minecraft.getInstance().options.keyShift.getKey().getDisplayName().getString()).withStyle(ChatFormatting.DARK_GRAY));
        /**
         * Returns the given component, or null if the shift key is up.
         */
        public static final UnaryOperator<Component> SHIFT_OR_NULL = (component -> Screen.hasShiftDown() ? component : null);
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Redux.MODID);

    public static final RegistryObject<Item> GOLDEN_SWET_BALL = register("golden_swet_ball", () -> new SwetBallItem(new Item.Properties()));
    public static final RegistryObject<Item> VANILLA_SWET_BALL = register("vanilla_swet_ball", () -> new SwetBallItem(new Item.Properties()));
    public static final RegistryObject<Item> VANILLA_GUMMY_SWET = register("vanilla_gummy_swet", GummySwetItem::new);
    public static final RegistryObject<Item> BLUE_SWET_JELLY = register("blue_swet_jelly", () -> new Item(new Item.Properties().food(ReduxFoods.SWET_JELLY)));
    public static final RegistryObject<Item> GOLDEN_SWET_JELLY = register("golden_swet_jelly", () -> new Item(new Item.Properties().food(ReduxFoods.SWET_JELLY)));
    public static final RegistryObject<Item> VANILLA_SWET_JELLY = register("vanilla_swet_jelly", () -> new Item(new Item.Properties().food(ReduxFoods.SWET_JELLY)));

    public static final RegistryObject<Item> BLUEBERRY_PIE = register("blueberry_pie", () -> new Item(new Item.Properties().food(ReduxFoods.BLUEBERRY_PIE)));
    public static final RegistryObject<Item> ENCHANTED_BLUEBERRY_PIE = register("enchanted_blueberry_pie", () -> new Item(new Item.Properties().food(ReduxFoods.ENCHANTED_BLUEBERRY_PIE)));



    public static final RegistryObject<Item> WYNDSPROUT_SEEDS = register("wyndsprout_seeds", () -> new ItemNameBlockItem(ReduxBlocks.WYNDSPROUTS_CROP.get(), new Item.Properties().food(ReduxFoods.WYNDSPROUT_SEEDS)));
    public static final RegistryObject<Item> BUNDLE_OF_WYNDSPROUTS = register("bundle_of_wyndsprouts", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WYNDSPROUT_BAGEL = register("wyndsprout_bagel", () -> new Item(new Item.Properties().food(ReduxFoods.WYNDSPROUT_BAGEL)));
    public static final RegistryObject<Item> OATMEAL = register("oatmeal", () -> new BowlFoodItem(new Item.Properties().food(ReduxFoods.OATMEAL).stacksTo(1)));

    public static final RegistryObject<Item> SKYSPROUT_SEEDS = register("skysprout_seeds", () -> new ItemNameBlockItem(ReduxBlocks.SKYSPROUTS_CROP.get(), new Item.Properties().food(ReduxFoods.SKYSPROUT_SEEDS)));
    public static final RegistryObject<Item> SKYBUD = register("skybud", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> RAW_GRAVITITE = register("raw_gravitite", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPECTRAL_DART = ITEMS.register("spectral_dart", () -> new DartItem(ReduxEntityTypes.SPECTRAL_DART, new Item.Properties()));
    public static final RegistryObject<Item> SPECTRAL_DART_SHOOTER = ITEMS.register("spectral_dart_shooter", () -> new DartShooterItem(SPECTRAL_DART, (new Item.Properties()).stacksTo(1)));

    public static final RegistryObject<Item> ENCHANTED_RING = register("enchanted_ring", () -> new RingItem(ReduxSoundEvents.EQUIP_ENCHANTED_RING, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON)));
    public static final RegistryObject<Item> GRAND_VICTORY_MEDAL = register("grand_victory_medal", () -> new AbilityTooltipPendantItem(Redux.locate("grand_medal"), ReduxSoundEvents.EQUIP_GRAND_MEDAL, new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT), "grand_medal_regen", "grand_medal_queen_refight"));
    public static final RegistryObject<Item> SENTRY_RING = register("sentry_ring", () -> new AbilityTooltipRingItem(ReduxSoundEvents.EQUIP_SENTRY_RING, new Item.Properties().stacksTo(1), "sentry_ring_embers"));
    public static final RegistryObject<Item> SHROOM_RING = register("shroom_ring", () -> new AbilityTooltipRingItem(ReduxSoundEvents.EQUIP_SHROOM_RING, new Item.Properties().stacksTo(1), "shroom_ring_adrenaline"));
    public static final RegistryObject<Item> AIRBOUND_CAPE = register("airbound_cape", () -> new AirboundCapeItem("airbound_cape", new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT), "airbound_cape_jump_boost"));
    public static final RegistryObject<Item> PHOENIX_EMBLEM = register("phoenix_emblem", () -> new AbilityTooltipMiscItem(new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT), "phoenix_emblem_fireball"));

    public static final RegistryObject<Item> RING_OF_WISDOM = register("ring_of_wisdom", () -> new AbilityTooltipRingItem(ReduxSoundEvents.EQUIP_WISDOM_RING, new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON), "wisdom_ring_xp_increase"));

    public static final RegistryObject<Item> MOUSE_EAR_SOUP = register("mouse_ear_soup", () -> new BowlFoodItem(new Item.Properties().food(ReduxFoods.MOUSE_EAR_SOUP)));

    public static final RegistryObject<Item> SUBZERO_CROSSBOW = register("subzero_crossbow", () -> new SubzeroCrossbowItem(new Item.Properties().stacksTo(1).durability(1024).rarity(AetherItems.AETHER_LOOT)));

    public static final RegistryObject<SpawnEggItem> VANILLA_SWET_SPAWN_EGG = ITEMS.register("vanilla_swet_spawn_egg", () -> new ForgeSpawnEggItem(ReduxEntityTypes.VANILLA_SWET, 0xF7F3E3, 0xD3CABB, new Item.Properties()));
    public static final RegistryObject<Item> MUSIC_DISC_LABYRINTHS_VENGEANCE = ITEMS.register("music_disc_labyrinths_vengeance", () -> (new RecordItem(1, ReduxSoundEvents.MUSIC_DISC_LABYRINTHS_VENGEANCE, (new Item.Properties()).stacksTo(1).rarity(Rarity.RARE), 4300)));

    public static final RegistryObject<SpawnEggItem> GLIMMERCOW_SPAWN_EGG = ITEMS.register("glimmercow_spawn_egg", () -> new ForgeSpawnEggItem(ReduxEntityTypes.GLIMMERCOW, 0x7B7F90, 0x4A73CC, new Item.Properties()));

    public static final RegistryObject<Item> LUXBUDS = register("luxbuds", () -> new TooltipItem(new Item.Properties().food(ReduxFoods.GLOWBUDS),
            (stack, level, components, tooltipFlag) -> {
                Component gbtt = TooltipUtils.SHIFT_OR_DEFAULT.apply(Component.translatable("gui.aether_redux.luxbuds_tooltip").withStyle(ChatFormatting.GRAY));
                @Nullable Component itt = TooltipUtils.SHIFT_OR_NULL.apply(Component.translatable("gui.aether_redux.infusion_tooltip").withStyle(ChatFormatting.GRAY));
                components.add(gbtt);
                if (itt != null) { components.add(itt); }
            }));

    public static final RegistryObject<Item> PURIFIED_LUXBUDS = register("purified_luxbuds", () -> new TooltipItem(new Item.Properties().food(ReduxFoods.PURIFIED_GLOWBUDS),
            (stack, level, components, tooltipFlag) -> {
                Component gbtt = TooltipUtils.SHIFT_OR_DEFAULT.apply(Component.translatable("gui.aether_redux.purified_luxbuds_tooltip").withStyle(ChatFormatting.GRAY));
                components.add(gbtt);
            }));

    public static final RegistryObject<Item> BLIGHTED_SPORES = register("blighted_spores", () -> new BlightedSporesItem(new Item.Properties()));


    public static final RegistryObject<Item> RAW_VERIDIUM = register("raw_veridium", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VERIDIUM_INGOT = register("veridium_ingot", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> VERIDIUM_NUGGET = register("veridium_nugget", () -> new Item(new Item.Properties()));

    public static final RegistryObject<SwordItem> VERIDIUM_SWORD = register("veridium_sword", () -> new VeridiumSwordItem(ReduxItemTiers.VERIDIUM, 2, -2.5F, new Item.Properties()));
    public static final RegistryObject<PickaxeItem> VERIDIUM_PICKAXE = register("veridium_pickaxe", () -> new VeridiumPickaxeItem(ReduxItemTiers.VERIDIUM, 0, -2.9F, new Item.Properties()));
    public static final RegistryObject<ShovelItem> VERIDIUM_SHOVEL = register("veridium_shovel", () -> new VeridiumShovelItem(ReduxItemTiers.VERIDIUM, 0, -3.1F, new Item.Properties()));
    public static final RegistryObject<AxeItem> VERIDIUM_AXE = register("veridium_axe", () -> new VeridiumAxeItem(ReduxItemTiers.VERIDIUM, 1, 0 -3.2F, new Item.Properties()));
    public static final RegistryObject<HoeItem> VERIDIUM_HOE = register("veridium_hoe", () -> new VeridiumHoeItem(ReduxItemTiers.VERIDIUM, 0, -1.7F, new Item.Properties()));
    public static final RegistryObject<SwordItem> INFUSED_VERIDIUM_SWORD = register("infused_veridium_sword", () -> new VeridiumSwordItem(ReduxItemTiers.INFUSED_VERIDIUM, 5, -2.2F, new Item.Properties()));
    public static final RegistryObject<PickaxeItem> INFUSED_VERIDIUM_PICKAXE = register("infused_veridium_pickaxe", () -> new VeridiumPickaxeItem(ReduxItemTiers.INFUSED_VERIDIUM, 1, -2.6F, new Item.Properties()));
    public static final RegistryObject<ShovelItem> INFUSED_VERIDIUM_SHOVEL = register("infused_veridium_shovel", () -> new VeridiumShovelItem(ReduxItemTiers.INFUSED_VERIDIUM, 0, -2.8F, new Item.Properties()));
    public static final RegistryObject<AxeItem> INFUSED_VERIDIUM_AXE = register("infused_veridium_axe", () -> new VeridiumAxeItem(ReduxItemTiers.INFUSED_VERIDIUM, 1, -2.9F, new Item.Properties()));
    public static final RegistryObject<HoeItem> INFUSED_VERIDIUM_HOE = register("infused_veridium_hoe", () -> new VeridiumHoeItem(ReduxItemTiers.INFUSED_VERIDIUM, 0, -1.4F, new Item.Properties()));

    public static final RegistryObject<Item> COCKATRICE_FEATHER = register("cockatrice_feather", () -> new AbilityTooltipMiscItem(new Item.Properties().stacksTo(1), "cockatrice_feather_immunity"));



    public static final RegistryObject<Item> QUICKROOT = register("quickroot", () -> new ItemNameBlockItem(ReduxBlocks.QUICKROOTS.get(), new Item.Properties().food(ReduxFoods.QUICKROOT)));

    public static final RegistryObject<Item> ZANBERRY = register("zanberry", () -> new Item(new Item.Properties().food(ReduxFoods.CHROMABERRY)));

    public static final RegistryObject<Item> LIGHTROOT_CLUMP = register("lightroot_clump", () -> new ItemNameBlockItem(ReduxBlocks.LIGHTROOTS.get(),  new Item.Properties().food(ReduxFoods.LIGHTROOT_CLUMP)));

    public static final RegistryObject<Item> VAMPIRE_AMULET = register("vampire_amulet", () -> new VampireAmuletItem(ReduxSoundEvents.EQUIP_VAMPIRE_AMULET, new Item.Properties().stacksTo(1).rarity(AetherItems.AETHER_LOOT), "vampire_amulet_night_ability", "vampire_amulet_day_debuff"));

    public static final RegistryObject<Item> SENTRY_CHIP = register("sentry_chip", () -> new Item(new Item.Properties()));

    public static <T extends Item> RegistryObject<T> register(final String name, final Supplier<T> item)
    {
        return ITEMS.register(name, item);
    }


}
