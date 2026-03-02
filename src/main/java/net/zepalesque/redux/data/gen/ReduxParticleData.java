package net.zepalesque.redux.data.gen;

import java.util.List;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.client.particle.ReduxParticles;
import net.zepalesque.redux.data.prov.ReduxParticleProvider;
import net.zepalesque.zenith.util.ArrayUtil;

public class ReduxParticleData extends ReduxParticleProvider {
    public ReduxParticleData(PackOutput output, ExistingFileHelper fileHelper) {
        super(output, fileHelper);
    }

    @Override
    protected void addDescriptions() {
        this.spriteSet(ReduxParticles.GILDENROOT_LEAF.get(), Redux.loc("leaves/gildenroot"));
        this.spriteSet(ReduxParticles.STORMROOT_LEAF.get(), Redux.loc("leaves/stormroot"));
        this.spriteSet(ReduxParticles.BLIGHTWILLOW_LEAF.get(), Redux.loc("leaves/blightwillow"));
        this.spriteSet(ReduxParticles.INFECTED_BLIGHTWILLOW_LEAF.get(), Redux.loc("leaves/infected_blightwillow"));
        this.spriteSet(ReduxParticles.CRYSTAL_LEAF.get(), Redux.loc("leaves/crystal"));
        this.spriteSet(ReduxParticles.SKYROOT_LEAF.get(), Redux.loc("leaves/skyroot"));
        this.spriteSet(ReduxParticles.GOLDEN_OAK_LEAF.get(), Redux.loc("leaves/golden_oak"));

        ResourceLocation[] lightningTextures = ArrayUtil.generateContents(new ResourceLocation[10], i -> Redux.loc("lightning/lightning" + i));
        this.spriteSet(ReduxParticles.WHIRLWIND_LIGHTNING.get(), List.of(lightningTextures));

        ResourceLocation[] sparkTextures = ArrayUtil.generateContents(new ResourceLocation[3], i -> Redux.loc("spark/spark" + i));
        this.spriteSet(ReduxParticles.SPARK.get(), List.of(sparkTextures));

        this.spriteSet(ReduxParticles.BLOSSOM_FLARE.get(), Redux.loc("blossom_flare"));

    }
}
