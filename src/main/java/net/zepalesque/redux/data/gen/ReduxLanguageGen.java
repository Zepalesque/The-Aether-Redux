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

        this.add(ReduxBlocks.CARVED_PILLAR);
        this.addLore(ReduxBlocks.CARVED_PILLAR, "A pillar made of Carved Stone. Pillars look nice for supporting a build, along with giving it nice corners.");
        this.add(ReduxBlocks.SENTRY_PILLAR);
        this.addLore(ReduxBlocks.SENTRY_PILLAR, "A pillar made of Sentry Stone. Pillars look nice for supporting a build, along with giving it nice corners.");
        this.add(ReduxBlocks.CARVED_BASE);
        this.addLore(ReduxBlocks.CARVED_BASE, "A nice decorative base block made of Carved Stone. Looks very nice at the bottom of walls!");
        this.add(ReduxBlocks.SENTRY_BASE);
        this.addLore(ReduxBlocks.SENTRY_BASE, "A nice decorative base block made of Sentry Stone. Looks very nice at the bottom of walls!");

        this.add(ReduxBlocks.LOCKED_CARVED_PILLAR);
        this.add(ReduxBlocks.LOCKED_SENTRY_PILLAR);
        this.add(ReduxBlocks.LOCKED_CARVED_BASE);
        this.add(ReduxBlocks.LOCKED_SENTRY_BASE);

        this.add(ReduxBlocks.TRAPPED_CARVED_PILLAR);
        this.add(ReduxBlocks.TRAPPED_SENTRY_PILLAR);
        this.add(ReduxBlocks.TRAPPED_CARVED_BASE);
        this.add(ReduxBlocks.TRAPPED_SENTRY_BASE);

        this.add(ReduxBlocks.BOSS_DOORWAY_CARVED_PILLAR);
        this.add(ReduxBlocks.BOSS_DOORWAY_SENTRY_PILLAR);
        this.add(ReduxBlocks.BOSS_DOORWAY_CARVED_BASE);
        this.add(ReduxBlocks.BOSS_DOORWAY_SENTRY_BASE);

        this.add(ReduxBlocks.RUNELIGHT);
        this.addLore(ReduxBlocks.RUNELIGHT, "A glowing block of circuitry made of Veridium, which can be easily toggled on and off. Found in Bronze Dungeons.");
        this.add(ReduxBlocks.LOCKED_RUNELIGHT);

        this.add(ReduxBlocks.LOCKED_SENTRITE_BRICKS);

        this.addPackDescription("mod", "The Aether: Redux Resources");
        this.addPackTitle("tintable_grass", "Redux - Tintable Grass");
        this.addPackDescription("tintable_grass", "Grass tint textures for the Aether: Redux");

        this.addPackTitle("bronze_upgrade", "Redux - Bronze Dungeon Upgrade");
        this.addPackDescription("bronze_upgrade", "Configurable in config/aether_redux/common.toml");
    }
}
