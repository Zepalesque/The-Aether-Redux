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

        this.addPackDescription("mod", "The Aether: Redux Resources");
        this.addPackTitle("tintable_grass", "Redux - Tintable Grass");
        this.addPackDescription("tintable_grass", "Grass tint textures for the Aether: Redux");
    }
}
