package net.zepalesque.redux.world.biome;

import static net.minecraft.world.level.biome.Climate.Parameter.span;

import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import com.mojang.datafixers.util.Pair;
import io.github.razordevs.aeroblender.aether.AetherRegionType;
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

public class ReduxRegion extends Region {
	public ReduxRegion(ResourceLocation name, int weight) {
		super(name, AetherRegionType.THE_AETHER, weight);
	}
	
	private static final Climate.Parameter FULL = Climate.Parameter.span(-1.5F, 1.5F);
	private static ParameterPoint parameterPoint(Parameter temp, Parameter span) {
		return new Climate.ParameterPoint(
			temp,
			span,
			FULL,
			FULL,
			FULL,
			FULL,
			0
		);
	}
	
	@Override
	public void addBiomes(
		Registry<Biome> registry,
		Consumer<Pair<Climate.ParameterPoint, ResourceKey<Biome>>> mapper
	) {

		var frosted = /*ReduxConfig.COMMON.enable_snowy_biomes.get() ?*/ ReduxBiomes.FROSTED_FORESTS /*: AetherBiomes.SKYROOT_FOREST*/;
		//var glaical = ReduxConfig.COMMON.enable_snowy_biomes.get() ? ReduxBiomes.GLACIAL_TUNDRA : AetherBiomes.SKYROOT_FOREST;
		var cloudcaps = /*ReduxConfig.COMMON.enable_cloudcaps.get() ?*/ ReduxBiomes.CLOUDCAPS /*: AetherBiomes.SKYROOT_GROVE*/;
		var blight = /*ReduxConfig.COMMON.enable_the_blight.get() ?*/ ReduxBiomes.THE_BLIGHT /*: AetherBiomes.SKYROOT_WOODLAND*/;
		var highfields = /*ReduxConfig.COMMON.enable_skyfields.get() ?*/ ReduxBiomes.SKYFIELDS /*: AetherBiomes.SKYROOT_MEADOW*/;
		//var shrublands = ReduxConfig.COMMON.enable_skyroot_shrublands.get() ? ReduxBiomes.SKYROOT_SHRUBLANDS : AetherBiomes.SKYROOT_MEADOW;
		var grove = /*ReduxConfig.COMMON.enable_gilded_biomes.get() ?*/ ReduxBiomes.GILDED_GROVES /*: AetherBiomes.SKYROOT_GROVE*/;
		//var grasslands = ReduxConfig.COMMON.enable_gilded_biomes.get() ? ReduxBiomes.GILDED_GRASSLANDS : AetherBiomes.SKYROOT_MEADOW;
		
		// TODO: static final value ,,,, :3:3
		var temp1 = span(-1.5F, -0.5F);
		var temp2 = span(-0.5F, -0.2F);
		var temp3 = span(-0.2F, 0.2F);
		var temp4 = span(0.2F, 0.5F);
		var temp5 = span(0.5F, 1.5F);
		
		this.addBiome(
			mapper,
			// TODO: revert biome params after readding glacial tundra
			parameterPoint(temp1, span(/*-1.0F, -0.3F*/ -0.3F, 0.3F)),
			frosted
		);
		/*
	    addBiome(
	    	mapper,
	    	parameterPoint(temp1, span(-0.3F, 0.3F)),
	    	glaical
	    ); */
		this.addBiome(
	    	mapper,
	    	parameterPoint(temp1, span(0.3F, 1.0F)),
	    	cloudcaps
	    );
		
		this.addBiome(
			mapper,
			parameterPoint(temp2, span(-1f, -0.4f)),
			blight
		);
		this.addBiome(
			mapper,
			parameterPoint(temp2, span(-0.4F, -0.15F)),
			AetherBiomes.SKYROOT_FOREST
		);
		this.addBiome(
			mapper,
			parameterPoint(temp2, span(-0.15F, 0.0F)),
			AetherBiomes.SKYROOT_MEADOW
		);
		this.addBiome(
			mapper,
			parameterPoint(temp2, span(0.0F, 0.2F)),
			AetherBiomes.SKYROOT_WOODLAND
		);
		this.addBiome(mapper, new Climate.ParameterPoint(temp2, Climate.Parameter.span(0.2F, 1.0F), FULL, FULL, FULL, FULL, 0),
				highfields); //ReduxBiomes.SHIMMERING_HILLS);
		
		this.addBiome(mapper, new Climate.ParameterPoint(temp3, Climate.Parameter.span(-1.0F, -0.3F), FULL, FULL, FULL, FULL, 0),
				highfields);
		this.addBiome(
			mapper,
			parameterPoint(temp3, span(-0.3F, -0.1F)),
			AetherBiomes.SKYROOT_FOREST
		);
		this.addBiome(
			mapper,
			parameterPoint(temp3, span(-0.1F, 0.2F)),
			AetherBiomes.SKYROOT_MEADOW
		);
		this.addBiome(
			mapper,
			parameterPoint(temp3, span(0.2F, 0.5F)),
			ReduxBiomes.SKYFIELDS
		);
		
		/* addBiome(
			mapper,
			parameterPoint(temp3, span(0.5F, 1.0F)),
			shrublands
		); */
		
		this.addBiome(
			mapper,
			parameterPoint(temp4, span(-1.0F, -0.6F)),
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
		this.addBiome(
			mapper,
			parameterPoint(temp5, span(-0.4F, 0.3F)),
			grove
		);
		/* addBiome(
			mapper,
			parameterPoint(temp5, span(0.3F, 1.0F)),
			grasslands
		); */
	}
}