package net.zepalesque.redux.entity.util;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class ManyEntitySpawner extends Mob {
    private final EntityType<? extends Mob> typeToSpawn;


    protected ManyEntitySpawner(EntityType<? extends Mob> entityType, Level level, EntityType<? extends Mob> spawnedMob) {
        super(entityType, level);
        typeToSpawn = spawnedMob;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0);
    }

    public static EntityType.EntityFactory<ManyEntitySpawner> fabricate(Supplier<? extends EntityType<? extends Mob>> spawnedMob) {
        return (entityType, level) -> new ManyEntitySpawner(entityType, level, spawnedMob.get());
    }


    protected void spawnEntities() {
        if (!this.level().isClientSide()) {
            for (int i = 0; i < 10; i++) {
                Mob e = this.typeToSpawn.create(this.level());
                if (e != null) {
                    e.copyPosition(this);
                    e.yBodyRot = this.yBodyRot;
                    // TODO: Test this, see if it is a good idea
                    e.setPersistenceRequired();

                    this.level().addFreshEntity(e);
                }
            }
        }
    }


    public static boolean checkManyEntitySpawnerSpawnRules(EntityType<? extends ManyEntitySpawner> spawner, LevelAccessor level, MobSpawnType reason, BlockPos pos, RandomSource random) {
        return Mob.checkMobSpawnRules(spawner, level, reason, pos, random) && level.canSeeSky(pos) && level.getDifficulty() != Difficulty.PEACEFUL;
    }


    @Nullable
    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData spawnData, @Nullable CompoundTag dataTag) {
        SpawnGroupData data = super.finalizeSpawn(level, difficulty, reason, spawnData, dataTag);
        this.spawnEntities();
        this.discard();
        return data;
    }
}
