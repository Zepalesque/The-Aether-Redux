package net.zepalesque.redux.world.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.zepalesque.redux.data.resource.registries.ReduxFeatureConfig;

import java.util.Optional;

public class ReduxTreeGrowers {

    public static final TreeGrower CLOUDROOT = new TreeGrower(
            "cloudroot",
            0.3F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ReduxFeatureConfig.CLOUDROOT_TREE),
            // TODO: Large cloudroot
            Optional.of(ReduxFeatureConfig.CLOUDROOT_TREE),
            Optional.empty(),
            Optional.empty()
    );
}
