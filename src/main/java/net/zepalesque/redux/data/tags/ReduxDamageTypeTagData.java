package net.zepalesque.redux.data.tags;

import com.aetherteam.aether.data.resources.registries.AetherDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.zepalesque.redux.Redux;
import net.zepalesque.redux.misc.ReduxTags;

import java.util.concurrent.CompletableFuture;

public class ReduxDamageTypeTagData extends TagsProvider<DamageType> {
    public ReduxDamageTypeTagData(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, Registries.DAMAGE_TYPE, registries, Redux.MODID, existingFileHelper);
    }


    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ReduxTags.DamageTypes.IS_ATTACK).add(DamageTypes.PLAYER_ATTACK, DamageTypes.MOB_ATTACK);
        this.tag(ReduxTags.DamageTypes.FIREBALL).add(AetherDamageTypes.FIRE_CRYSTAL);
    }
}
