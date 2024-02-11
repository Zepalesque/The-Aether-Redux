package net.zepalesque.redux.entity.util;

import com.aetherteam.aether.AetherTags;
import com.aetherteam.aether.entity.monster.Swet;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class EntitySpawner extends Mob {
    private final EntityType<? extends Mob> typeToSpawn;

    protected EntitySpawner(EntityType<? extends Mob> entityType, Level level, EntityType<? extends Mob> spawnedMob) {
        super(entityType, level);
        typeToSpawn = spawnedMob;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0);
    }

    public static EntityType.EntityFactory<EntitySpawner> fabricate(Supplier<? extends EntityType<? extends Mob>> spawnedMob) {
        return (entityType, level) -> new EntitySpawner(entityType, level, spawnedMob.get());
    }

    protected Mob spawnEntity() {
        if (!this.level().isClientSide()) {
            Mob e = this.typeToSpawn.create(this.level());
            if (e != null) {
                e.copyPosition(this);
                e.yBodyRot = this.yBodyRot;
                // TODO: Test this, see if it is a good idea
                e.setPersistenceRequired();
                this.level().addFreshEntity(e);
                return e;
            }
        }
        return null;
    }


    public static boolean checkEntitySpawnerSpawnRules(EntityType<? extends EntitySpawner> spawner, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(AetherTags.Blocks.SWET_SPAWNABLE_ON) && level.getRawBrightness(pos, 0) > 8 && level.getDifficulty() != Difficulty.PEACEFUL;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        this.spawnEntity();
        this.discard();
        return data;
    }
}
