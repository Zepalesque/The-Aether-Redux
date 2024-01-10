package net.zepalesque.redux.config.pack;

import net.minecraft.SharedConstants;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.api.Conditional;
import net.zepalesque.redux.api.packconfig.Category;
import net.zepalesque.redux.api.packconfig.PackConfig;
import net.zepalesque.redux.api.serialization.client.WidgetMappers;
import net.zepalesque.redux.builtin.BuiltinPackUtils;
import net.zepalesque.redux.client.resource.ReduxOverridesPackResources;
import net.zepalesque.redux.config.enums.pack.ClassicAnyOption;
import net.zepalesque.redux.config.enums.pack.ClassicOption;
import net.zepalesque.redux.config.enums.pack.DungeonType;
import net.zepalesque.redux.config.enums.pack.SwetBallType;

import java.util.List;

public class ReduxPackConfig {


    public final Category base;

    public final PackConfig<Boolean> blue_icestone;
    public final PackConfig<Boolean> better_aerclouds;
    public final PackConfig<Boolean> better_aechor_plants;
    public final PackConfig<DungeonType> dungeon_type;
    public final PackConfig<ClassicOption> classic_blight;
    public final PackConfig<ClassicOption> classic_moas;
    public final PackConfig<ClassicOption> classic_cockatrices;
    public final PackConfig<SwetBallType> swet_ball_type;
    public final PackConfig<Boolean> use_jappafied_textures;
    public final PackConfig<Boolean> menu_panorama;
    public final PackConfig<ClassicAnyOption> smelter_menu_type;

    public final PackConfig<Boolean> auto_apply;

    public final PackConfig<Boolean> tintable_grass;

    public ReduxPackConfig(Category base) {
        this.base = base;
        PackConfig.Builder builder = base.builder();
        builder.push("nature");
        // Self-explanatory
        this.blue_icestone = builder.comment().cfg("blue_icestone", true, WidgetMappers.BOOL);
        // Self-explanatory
        this.better_aerclouds = builder.comment().cfg("better_aerclouds", true, WidgetMappers.BOOL);
        // Self-explanatory
        this.classic_blight = builder.comment().cfg("classic_blight", ClassicOption.modern, ClassicOption.MAPPER);
        builder.pop();
        builder.push("mob");
        // Self-explanatory
        this.better_aechor_plants = builder.comment().cfg("better_aechor_plants", true, WidgetMappers.BOOL);
        // Use moas with teeth, may be removed
        this.classic_moas = builder.comment().cfg("classic_moas", ClassicOption.modern, ClassicOption.MAPPER);
        // Classic green cockatrices
        this.classic_cockatrices = builder.comment().cfg("classic_cockatrices", ClassicOption.modern, ClassicOption.MAPPER);
        builder.pop();
        builder.push("dungeon");
        // Which dungeon design to use
        this.dungeon_type = builder.comment().cfg("dungeon_type", DungeonType.ancient, DungeonType.MAPPER);
        builder.pop();
        builder.push("item");
        // Which behavior for swet balls to use
        this.swet_ball_type = builder.comment().cfg("swet_ball_type", SwetBallType.gel, SwetBallType.MAPPER);
        builder.pop();
        builder.push("gui");
        // Use Redux menu panorama
        this.menu_panorama = builder.comment().cfg("menu_panorama", true, WidgetMappers.BOOL);
        // Use improved smelter guis
        this.smelter_menu_type = builder.comment().cfg("smelter_menu_type", ClassicAnyOption.modern, ClassicAnyOption.MAPPER);
        builder.pop();
        // Jappafied Aethers compat TODO: use ozzified as true 'default' textures rather than using ozzified when this is disabled
        this.use_jappafied_textures = builder.comment().cfg("use_jappafied_textures", false, WidgetMappers.BOOL);
        // Auto-apply the resource pack
        this.auto_apply = builder.comment().cfg("auto_apply", true, WidgetMappers.BOOL);
        this.tintable_grass = builder.comment().cfg("tintable_grass", true, WidgetMappers.BOOL);
    }

    public static ReduxOverridesPackResources generate(String id, String title, Component desc) {
        ReduxPackConfig config = Redux.packConfig;

        List<Conditional<PackResources>> packs = List.of(
                Conditional.of(BuiltinPackUtils.createPack("nature/classic_blight"), () -> config.classic_blight.get() == ClassicOption.classic),
                Conditional.of(BuiltinPackUtils.createPack("nature/blue_icestone"), config.blue_icestone),
                Conditional.of(BuiltinPackUtils.createPack("nature/better_aerclouds"), config.better_aerclouds),
                Conditional.of(BuiltinPackUtils.createPack("nature/golder_vines"), config.use_jappafied_textures::get),
                Conditional.of(BuiltinPackUtils.createPack("nature/tintable_grass"), config.tintable_grass::get),
                Conditional.of(BuiltinPackUtils.createPack("mob/better_aechor_plants"), config.better_aechor_plants),
                Conditional.of(BuiltinPackUtils.createPack("mob/classic_moas"), () -> config.classic_moas.get() == ClassicOption.classic),
                Conditional.of(BuiltinPackUtils.createPack("mob/classic_cockatrices"), () -> config.classic_cockatrices.get() == ClassicOption.classic),
                Conditional.of(BuiltinPackUtils.createPack("dungeon/ancient_vaults"), () -> config.dungeon_type.get() == DungeonType.ancient),
                Conditional.of(BuiltinPackUtils.createPack("dungeon/retro_dungeon"), () -> config.dungeon_type.get() == DungeonType.retro),
                Conditional.of(BuiltinPackUtils.createPack("item/swet_ball"), () -> config.swet_ball_type.get() == SwetBallType.consistent_name),
                Conditional.of(BuiltinPackUtils.createPack("item/swet_gel"), () -> config.swet_ball_type.get() == SwetBallType.gel),
                Conditional.of(BuiltinPackUtils.createPack("gui/menu_panorama"), config.menu_panorama),
                Conditional.of(BuiltinPackUtils.createPack("gui/classic_smelters"), () -> config.smelter_menu_type.get() == ClassicAnyOption.classic),
                Conditional.of(BuiltinPackUtils.createPack("gui/modern_smelters"), () -> config.smelter_menu_type.get() == ClassicAnyOption.modern),
                Conditional.of(BuiltinPackUtils.createPack("ozzified_textures"), () -> !config.use_jappafied_textures.get()),
                Conditional.of(BuiltinPackUtils.createPack("item/genesis_jelly"), Redux::aetherGenesisCompat)
        );

        return new ReduxOverridesPackResources(id, true, new PackMetadataSection(
                desc, SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES)), packs);

    }
}
