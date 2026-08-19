package net.zepalesque.redux.data.gen.tags;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.data.resources.registries.AetherBiomes;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.data.ReduxTags;
import net.zepalesque.redux.data.resource.registries.ReduxBiomes;

public class ReduxBiomeTagsData extends BiomeTagsProvider {
	public ReduxBiomeTagsData(
		PackOutput output,
		CompletableFuture<HolderLookup.Provider> registries,
		@Nullable ExistingFileHelper helper
	) {
		super(output, registries, Redux.MODID, helper);
	}

	@Override
	public void addTags(HolderLookup.Provider provider) {
		this.tag(AetherTags.Biomes.IS_AETHER).add(ReduxBiomes.GILDED_GROVES,
			ReduxBiomes.THE_BLIGHT,
			ReduxBiomes.FROSTED_FORESTS,
			ReduxBiomes.SKYFIELDS,
			ReduxBiomes.CLOUDCAP_RIDGE
			// java needs trailing commas
		);

		this.tag(ReduxTags.Biomes.HAS_CLOUDBED).addTag(AetherTags.Biomes.IS_AETHER);

		this.tag(ReduxTags.Biomes.HAS_LAKES).addTag(AetherTags.Biomes.IS_AETHER);
		
		this.tag(ReduxTags.Biomes.HAS_CAT_FISH).addTag(ReduxTags.Biomes.HAS_LAKES);

		this.tag(ReduxTags.Biomes.HAS_VERBENA).addTag(ReduxTags.Biomes.HAS_LAKES);

		this.tag(ReduxTags.Biomes.HAS_CAELGAE).addTag(ReduxTags.Biomes.HAS_LAKES);
		
		this.tag(ReduxTags.Biomes.HAS_BLOOMTAIL).addTag(ReduxTags.Biomes.HAS_LAKES);
		
		this.tag(ReduxTags.Biomes.BLOOMTAIL_BONEMEAL).addTag(AetherTags.Biomes.IS_AETHER);

		this.tag(ReduxTags.Biomes.HAS_CAVES).addTag(AetherTags.Biomes.IS_AETHER);
		
		this.tag(ReduxTags.Biomes.HAS_CONDITIONAL_SNOW).addTag(AetherTags.Biomes.IS_AETHER);
		
		this.tag(ReduxTags.Biomes.AEROGEL_LAKE_MATERIAL).add(
			ReduxBiomes.FROSTED_FORESTS,
			ReduxBiomes.CLOUDCAP_RIDGE
		);

		this.tag(ReduxTags.Biomes.MODIFY_MUSIC).addTag(AetherTags.Biomes.IS_AETHER);

		this.tag(ReduxTags.Biomes.MODIFY_SKY_COLOR).addTag(AetherTags.Biomes.IS_AETHER);

		this.tag(ReduxTags.Biomes.MODIFY_WATER_COLOR).addTag(AetherTags.Biomes.IS_AETHER);

		// Gilded Groves use their own decreased sentrite feature
		this.tag(ReduxTags.Biomes.HAS_SENTRITE)
			.addTag(AetherTags.Biomes.IS_AETHER)
			.remove(ReduxBiomes.GILDED_GROVES);

		// Gilded Groves use their own increased angilite feature
		this.tag(ReduxTags.Biomes.HAS_ANGILITE)
			.addTag(AetherTags.Biomes.IS_AETHER)
			.remove(ReduxBiomes.GILDED_GROVES);

		this.tag(ReduxTags.Biomes.HAS_WYNDSPROUTS).add(AetherBiomes.SKYROOT_GROVE,
			AetherBiomes.SKYROOT_MEADOW,
			AetherBiomes.SKYROOT_WOODLAND,
			ReduxBiomes.GILDED_GROVES,
			ReduxBiomes.SKYFIELDS
		);
	}
}
