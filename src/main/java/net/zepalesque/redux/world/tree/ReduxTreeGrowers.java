package net.zepalesque.redux.world.tree;

import java.util.Optional;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.zepalesque.redux.data.resource.registries.ReduxFeatureConfig;

public class ReduxTreeGrowers {
	public static final TreeGrower GILDENROOT = new TreeGrower(
		"gildenroot",
		0.3F,
		Optional.of(ReduxFeatureConfig.LARGE_GILDENROOT_TREE),
		Optional.empty(),
		Optional.of(ReduxFeatureConfig.SMALL_GILDENROOT_TREE),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	);

	public static final TreeGrower STORMFIR = new TreeGrower(
		"stormfir",
		0.3F,
		Optional.empty(),
		Optional.empty(),
		Optional.of(ReduxFeatureConfig.STORMFIR_TREE),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	);

	public static final TreeGrower MOONFIR = new TreeGrower(
		"moonfir",
		0.3F,
		Optional.empty(),
		Optional.empty(),
		Optional.of(ReduxFeatureConfig.MOONFIR_TREE),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	);

	public static final TreeGrower BLIGHTWILLOW = new TreeGrower(
		"blightwillow",
		0.3F,
		Optional.empty(),
		Optional.empty(),
		Optional.of(ReduxFeatureConfig.BLIGHTWILLOW_TREE),
		Optional.empty(),
		Optional.empty(),
		Optional.empty()
	);
}
