package net.zepalesque.redux.world.biome;

import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.mojang.datafixers.util.Pair;
import io.github.razordevs.aeroblender.aether.AetherRegionType;

import java.util.function.BiFunction;
import java.util.function.Consumer;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.biome.Climate.Parameter;
import net.minecraft.world.level.biome.Climate.ParameterPoint;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;
import terrablender.api.Region;

import static net.minecraft.world.level.biome.Climate.Parameter.span;

public class ReduxRegion extends Region {
	public ReduxRegion(ResourceLocation name, int weight) {
		super(name, AetherRegionType.THE_AETHER, weight);
	}

	@Override
	public void addBiomes(
		Registry<Biome> registry,
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper
	) {
		var fullRange = Climate.Parameter.span(-1.5F, 1.5F);

		BiFunction<Parameter, Parameter, ParameterPoint> parameterPoint = (temp, span) -> new Climate.ParameterPoint(
			temp,
			span,
			fullRange,
			fullRange,
			fullRange,
			fullRange,
			0
		);

		var frosted = /*ReduxConfig.COMMON.enable_snowy_biomes.get() ?*/ ReduxBiomes.FROSTED_FORESTS /*: AetherBiomes.SKYROOT_FOREST*/;
		//var glaical = ReduxConfig.COMMON.enable_snowy_biomes.get() ? ReduxBiomes.GLACIAL_TUNDRA : AetherBiomes.SKYROOT_FOREST;
		//var cloudcaps = ReduxConfig.COMMON.enable_cloudcaps.get() ? ReduxBiomes.CLOUDCAPS : AetherBiomes.SKYROOT_GROVE;
		var blight = /*ReduxConfig.COMMON.enable_the_blight.get() ?*/ ReduxBiomes.THE_BLIGHT /*: AetherBiomes.SKYROOT_WOODLAND*/;
		//var highfields = ReduxConfig.COMMON.enable_skyfields.get() ? ReduxBiomes.SKYFIELDS : AetherBiomes.SKYROOT_MEADOW;
		//var shrublands = ReduxConfig.COMMON.enable_skyroot_shrublands.get() ? ReduxBiomes.SKYROOT_SHRUBLANDS : AetherBiomes.SKYROOT_MEADOW;
		var grove = /*ReduxConfig.COMMON.enable_gilded_biomes.get() ?*/ ReduxBiomes.GILDED_GROVES /*: AetherBiomes.SKYROOT_GROVE*/;
		//var grasslands = ReduxConfig.COMMON.enable_gilded_biomes.get() ? ReduxBiomes.GILDED_GRASSLANDS : AetherBiomes.SKYROOT_MEADOW;
		
		var temp1 = span(-1.5F, -0.5F);
		var temp2 = span(-0.5F, -0.2F);
		var temp3 = span(-0.2F, 0.2F);
		var temp4 = span(0.2F, 0.5F);
		var temp5 = span(0.5F, 1.5F);

		 addBiome(
			mapper,
			// TODO: revert biome params after readding glacial tundra
			parameterPoint.apply(temp1, span(/*-1.0F, -0.3F*/ -0.3F, 0.3F)),
			frosted
		);
		/*
	    addBiome(
	    	mapper,
	    	parameterPoint.apply(temp1, span(-0.3F, 0.3F)),
	    	glaical
	    ); */
		/* addBiome(
	    	mapper,
	    	parameterPoint.apply(temp1, span(0.3F, 1.0F)),
	    ); */

		addBiome(
			mapper,
			parameterPoint.apply(temp2, span(-1f, -0.4f)),
			blight
		);
		addBiome(
			mapper,
			parameterPoint.apply(temp2, span(-0.4F, -0.15F)),
			AetherBiomes.SKYROOT_FOREST
		);
		addBiome(
			mapper,
			parameterPoint.apply(temp2, span(-0.15F, 0.0F)),
			AetherBiomes.SKYROOT_MEADOW
		);
		addBiome(
			mapper,
			parameterPoint.apply(temp2, span(0.0F, 0.2F)),
			AetherBiomes.SKYROOT_WOODLAND
		);
	/*        addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(0.2F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
				highfields); //ReduxBiomes.SHIMMERING_HILLS);*/

	/*        addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(-1.0F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
				highfields);*/
		addBiome(
			mapper,
			parameterPoint.apply(temp3, span(-0.3F, -0.1F)),
			AetherBiomes.SKYROOT_FOREST
		);
		addBiome(
			mapper,
			parameterPoint.apply(temp3, span(-0.1F, 0.2F)),
			AetherBiomes.SKYROOT_MEADOW
		);
		/* addBiome(
			mapper,
			parameterPoint.apply(temp3, span(0.2F, 0.5F)),
			ReduxBiomes.SKYFIELDS
		);
		addBiome(
			mapper,
			parameterPoint.apply(temp3, span(0.5F, 1.0F)),
			shrublands
		); */

		addBiome(
			mapper,
			parameterPoint.apply(temp4, span(-1.0F, -0.6F)),
			AetherBiomes.SKYROOT_MEADOW
		);
	/*        addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.6F, -0.3F), fullRange, fullRange, fullRange, fullRange, 0),
				shrublands);
		addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.3F, -0.05F), fullRange, fullRange, fullRange, fullRange, 0),
				AetherBiomes.SKYROOT_GROVE);
		addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(-0.05F, 0.2F), fullRange, fullRange, fullRange, fullRange, 0),
				shrublands);
		addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(0.2F, 0.5F), fullRange, fullRange, fullRange, fullRange, 0),
				AetherBiomes.SKYROOT_GROVE); //ReduxBiomes.QUICKSOIL_DUNES);
		addBiome(mapper, new Climate.ParameterPoint(temp4, Climate.Parameter.span(0.5F, 1.0F), fullRange, fullRange, fullRange, fullRange, 0),
				shrublands); //ReduxBiomes.QUICKSOIL_OASIS);*/

	/*        addBiome(mapper, new Climate.ParameterPoint(temp5, Climate.Parameter.span(-1.0F, -0.4F), fullRange, fullRange, fullRange, fullRange, 0),
				ReduxBiomes.GILDED_GRASSLANDS);*/
		addBiome(
			mapper,
			parameterPoint.apply(temp5, span(-0.4F, 0.3F)),
			grove
		);
		/* addBiome(
			mapper,
			parameterPoint.apply(temp5, span(0.3F, 1.0F)),
			grasslands
		); */
	}
}