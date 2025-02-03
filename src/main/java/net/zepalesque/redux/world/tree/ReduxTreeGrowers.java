package net.zepalesque.redux.world.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.zepalesque.redux.data.resource.registries.ReduxFeatureConfig;

import java.util.Optional;

public class ReduxTreeGrowers {

    public static final TreeGrower GILDENROOT = new TreeGrower(
            "gildenroot",
            0.3F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ReduxFeatureConfig.SMALL_GILDENROOT_TREE),
            Optional.of(ReduxFeatureConfig.LARGE_GILDENROOT_TREE),
            Optional.empty(),
            Optional.empty()
    );

    public static final TreeGrower SHADEROOT = new TreeGrower(
            "shaderoot",
            0.3F,
            Optional.empty(),
            Optional.empty(),
            Optional.of(ReduxFeatureConfig.SMALL_SHADEROOT_TREE),
//            Optional.of(ReduxFeatureConfig.LARGE_GILDENROOT_TREE),
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
//            Optional.of(ReduxFeatureConfig.LARGE_GILDENROOT_TREE),
            Optional.empty(),
            Optional.empty(),
            Optional.empty()
    );
}
