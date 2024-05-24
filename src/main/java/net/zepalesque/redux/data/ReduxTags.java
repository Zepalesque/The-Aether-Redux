package net.zepalesque.redux.data;

import com.aetherteam.aether.api.AetherAdvancementSoundOverrides;
import com.aetherteam.aether.api.registers.AdvancementSoundOverride;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.zepalesque.redux.Redux;

public class ReduxTags {


    public static class Blocks {

        public static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, Redux.loc(name));
        }
    }

    public static class Items {

        public static final TagKey<Item> AETHER_GRASS_NONREPLACING = tag("aether_grass_nonreplacing");

        public static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, Redux.loc(name));
        }
    }

    public static class Entities {
        
        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, Redux.loc(name));
        }
    }

    public static class Biomes {
        
        private static TagKey<Biome> tag(String name) {
            return TagKey.create(Registries.BIOME, Redux.loc(name));
        }
    }
    
    public static class DmgTypes {

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, Redux.loc(name));
        }
    }
    
    public static class Sounds {
        
        private static TagKey<SoundEvent> tag(String name) {
            return TagKey.create(Registries.SOUND_EVENT, Redux.loc(name));
        }
    }

    public static class AdvancementSFX {

        private static TagKey<AdvancementSoundOverride> tag(String name) {
            return TagKey.create(AetherAdvancementSoundOverrides.ADVANCEMENT_SOUND_OVERRIDE_REGISTRY_KEY, Redux.loc(name));
        }
    }
}
