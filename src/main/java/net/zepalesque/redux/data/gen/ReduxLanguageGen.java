package net.zepalesque.redux.data.gen;

import net.minecraft.data.PackOutput;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.block.ReduxBlocks;
import net.zepalesque.redux.data.prov.ReduxLanguageProvider;

public class ReduxLanguageGen extends ReduxLanguageProvider {

    public ReduxLanguageGen(PackOutput output) {
        super(output, Redux.MODID);
    }

    @Override
    protected void addTranslations() {
        Redux.WOOD_SETS.forEach(set -> set.langData(this));
        Redux.STONE_SETS.forEach(set -> set.langData(this));

        this.add(ReduxBlocks.SHORT_AETHER_GRASS);
        this.addLore(ReduxBlocks.SHORT_AETHER_GRASS, "Blades of the Aether's grass. It feels slightly cool to the touch.");

        this.add(ReduxBlocks.CLOUDROOT_LEAVES);
        this.addLore(ReduxBlocks.CLOUDROOT_LEAVES, "Leaves of the Cloudroot tree, a variation of Skyroot that has been touched by Ambrosium but has not fully adapted as Golden Oaks have. These sometimes will drop Cloudroot Saplings");

        this.add(ReduxBlocks.CLOUDROOT_SAPLING);
        this.addLore(ReduxBlocks.CLOUDROOT_SAPLING, "The sapling of the Cloudroot tree. It can be grown by waiting or using Bone Meal.");

        this.addPackDescription("mod", "The Aether: Redux Resources");
        this.addPackTitle("tintable_grass", "Redux - Tintable Grass");
        this.addPackDescription("tintable_grass", "Grass tint textures for the Aether: Redux");
    }
}
