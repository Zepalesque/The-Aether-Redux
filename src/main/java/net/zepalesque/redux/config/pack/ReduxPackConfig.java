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

    public final PackConfig<Boolean> better_aechor_plants;
    public final PackConfig<ClassicOption> classic_cockatrices;
    public final PackConfig<SwetBallType> swet_ball_type;
    public final PackConfig<Boolean> use_jappafied_textures;
    public final PackConfig<Boolean> menu_panorama;

    public final PackConfig<Boolean> auto_apply;

    public final PackConfig<Boolean> tintable_grass;

    public ReduxPackConfig(Category base) {
        this.base = base;
        PackConfig.Builder builder = base.builder();
        builder.push("nature");

        builder.pop();
        builder.push("mob");
        // Self-explanatory
        this.better_aechor_plants = builder.comment().cfg("better_aechor_plants", true, WidgetMappers.BOOL);
        // Classic green cockatrices
        this.classic_cockatrices = builder.comment().cfg("classic_cockatrices", ClassicOption.modern, ClassicOption.MAPPER);
        builder.pop();
        builder.pop();
        builder.push("item");
        // Which behavior for swet balls to use
        this.swet_ball_type = builder.comment().cfg("swet_ball_type", SwetBallType.gel, SwetBallType.MAPPER);
        builder.pop();
        builder.push("gui");
        // Use Redux menu panorama
        this.menu_panorama = builder.comment().cfg("menu_panorama", true, WidgetMappers.BOOL);
        builder.pop();
        // Jappafied Aethers compat TODO: use ozzified as true 'default' textures rather than using ozzified when this is disabled
        this.use_jappafied_textures = builder.comment().cfg("use_jappafied_textures", false, WidgetMappers.BOOL);
        // Auto-apply the resource pack
        this.auto_apply = builder.comment().cfg("auto_apply", true, WidgetMappers.BOOL);
        // Allows for tinting grass along with some flower stems
        this.tintable_grass = builder.comment().cfg("tintable_grass", true, WidgetMappers.BOOL);
    }

    public static ReduxOverridesPackResources generate(String id, String title, Component desc) {
        ReduxPackConfig config = Redux.packConfig;
        List<Conditional<PackResources>> packs = List.of();
        if (config != null) {
            packs = List.of(
                    Conditional.of(BuiltinPackUtils.createPack("resource/nature/golder_vines"), config.use_jappafied_textures),
                    Conditional.of(BuiltinPackUtils.createPack("resource/nature/tintable_grass"), config.tintable_grass),
                    Conditional.of(BuiltinPackUtils.createPack("resource/mob/better_aechor_plants"), config.better_aechor_plants),
                    Conditional.of(BuiltinPackUtils.createPack("resource/mob/classic_cockatrices"), () -> config.classic_cockatrices.get() == ClassicOption.classic),
                    Conditional.of(BuiltinPackUtils.createPack("resource/item/swet_ball"), () -> config.swet_ball_type.get() == SwetBallType.consistent_name),
                    Conditional.of(BuiltinPackUtils.createPack("resource/item/swet_gel"), () -> config.swet_ball_type.get() == SwetBallType.gel),
                    Conditional.of(BuiltinPackUtils.createPack("resource/gui/menu_panorama"), config.menu_panorama),
                    Conditional.of(BuiltinPackUtils.createPack("resource/jappafied_textures"), config.use_jappafied_textures),
                    Conditional.of(BuiltinPackUtils.createPack("resource/item/genesis_jelly"), Redux::aetherGenesisCompat)
            );
        }
        return new ReduxOverridesPackResources(id, true, new PackMetadataSection(
                desc, SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES)), packs);

    }
}
