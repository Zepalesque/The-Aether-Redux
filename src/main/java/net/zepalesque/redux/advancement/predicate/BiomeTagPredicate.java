package net.zepalesque.redux.advancement.predicate;

import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.EntitySubPredicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class BiomeTagPredicate implements EntitySubPredicate {

    protected final TagKey<Biome> biomeTag;

    public BiomeTagPredicate(TagKey<Biome> biomeTag) {
        this.biomeTag = biomeTag;
    }

    public static BiomeTagPredicate of(TagKey<Biome> biomeTag) {
        return new BiomeTagPredicate(biomeTag);
    }

    @Override
    public boolean matches(Entity pEntity, ServerLevel pLevel, @Nullable Vec3 p_218830_) {
        return false;
    }

    @Override
    public JsonObject serializeCustomData() {
        return null;
    }

    @Override
    public Type type() {
        return null;
    }
}
