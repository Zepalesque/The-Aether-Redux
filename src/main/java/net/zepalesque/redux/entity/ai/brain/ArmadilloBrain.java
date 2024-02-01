//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package net.zepalesque.redux.entity.ai.brain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.util.Pair;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.task.BreedTask;
import net.minecraft.entity.ai.brain.task.FleeTask;
import net.minecraft.entity.ai.brain.task.GoTowardsLookTargetTask;
import net.minecraft.entity.ai.brain.task.LookAroundTask;
import net.minecraft.entity.ai.brain.task.LookAtMobWithIntervalTask;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.ai.brain.task.RandomLookAroundTask;
import net.minecraft.entity.ai.brain.task.RandomTask;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.StayAboveWaterTask;
import net.minecraft.entity.ai.brain.task.StrollTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.ai.brain.task.TemptTask;
import net.minecraft.entity.ai.brain.task.TemptationCooldownTask;
import net.minecraft.entity.ai.brain.task.WaitTask;
import net.minecraft.entity.ai.brain.task.WalkTowardClosestAdultTask;
import net.minecraft.entity.ai.brain.task.WanderAroundTask;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.ArmadilloEntity.State;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.*;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.item.crafting.Ingredient;
import net.zepalesque.redux.entity.ReduxEntityTypes;
import net.zepalesque.redux.entity.passive.Mykapod;
import net.zepalesque.redux.misc.ReduxTags;

public class ArmadilloBrain {
    public static final Ingredient BREEDING_INGREDIENT;
    private static final float field_47797 = 2.0F;
    private static final float field_47798 = 1.0F;
    private static final float field_47799 = 1.25F;
    private static final float field_47800 = 1.25F;
    private static final float field_47801 = 1.0F;
    private static final double field_48338 = 2.0;
    private static final double field_48339 = 1.0;
    private static final UniformInt WALK_TOWARDS_CLOSEST_ADULT_RANGE;
    private static final ImmutableList<SensorType<? extends Sensor<? super Mykapod>>> SENSOR_TYPES;
    private static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES;
    private static final OneShot<Mykapod> UNROLL_TASK;

    public ArmadilloBrain() {
    }

    public static Brain.Provider<Mykapod> provider() {
        return Brain.provider(MEMORY_MODULE_TYPES, SENSOR_TYPES);
    }

    protected static Brain<?> create(Brain<Mykapod> brain) {
        addCoreActivities(brain);
        addIdleActivities(brain);
        addPanicActivities(brain);
        brain.setCoreActivities(Set.of(Activity.CORE));
        brain.setDefaultActivity(Activity.IDLE);
        brain.useDefaultActivity();
        return brain;
    }

    private static void addCoreActivities(Brain<Mykapod> brain) {
        brain.addActivity(Activity.CORE, 0, ImmutableList.of(new Swim(0.8F), new UnrollAndFleeTask(2.0F), new LookAtTargetSink(45, 90),
                new MoveToTargetSink() {
            protected boolean checkExtraStartConditions(ServerLevel serverWorld, Mob mobEntity) {
                if (mobEntity instanceof Mykapod mykapod) {
                    if (!mykapod.isHiding()) {
                        return false;
                    }
                }

                return super.checkExtraStartConditions(serverWorld, mobEntity);
            }
        }, new CountDownCooldownTicks(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS),
                new CountDownCooldownTicks(MemoryModuleType.GAZE_COOLDOWN_TICKS),
                UNROLL_TASK));
    }

    private static void addIdleActivities(Brain<Mykapod> brain) {
        brain.addActivity(Activity.IDLE, ImmutableList.of(Pair.of(0, SetEntityLookTargetSometimes.create(EntityType.PLAYER, 6.0F, UniformInt.of(30, 60))), Pair.of(1, new AnimalMakeLove(ReduxEntityTypes.ENTITY_TYPES.MYKAPOD.get(), 1.0F, 1)), Pair.of(2, new RunOne<>(ImmutableList.of(Pair.of(new FollowTemptation((armadillo) -> {
            return 1.25F;
        }, (armadillo) -> {
            return armadillo.isBaby() ? 1.0 : 2.0;
        }), 1), Pair.of(BabyFollowAdult.create(WALK_TOWARDS_CLOSEST_ADULT_RANGE, 1.25F), 1)))), Pair.of(3, new RandomLookAround(UniformInt.of(150, 250), 30.0F, 0.0F, 0.0F)), Pair.of(4, new RunOne<>(ImmutableMap.of(MemoryModuleType.WALK_TARGET, MemoryStatus.VALUE_ABSENT), ImmutableList.of(Pair.of(RandomStroll.stroll(1.0F), 1), Pair.of(SetWalkTargetFromLookTarget.create(1.0F, 3), 1), Pair.of(new DoNothing(30, 60), 1))))));
    }

    private static void addPanicActivities(Brain<ArmadilloEntity> brain) {
        brain.setTaskList(Activity.PANIC, ImmutableList.of(Pair.of(0, new RollUpTask())), Set.of(Pair.of(MemoryModuleType.DANGER_DETECTED_RECENTLY, MemoryStatus.VALUE_PRESENT)));
    }

    public static void updateActivities(ArmadilloEntity armadillo) {
        armadillo.getBrain().resetPossibleActivities(ImmutableList.of(Activity.PANIC, Activity.IDLE));
    }

    public static Ingredient getBreedingIngredient() {
        return BREEDING_INGREDIENT;
    }

    static {
        BREEDING_INGREDIENT = Ingredient.ofItems(new ItemConvertible[]{Items.SPIDER_EYE});
        WALK_TOWARDS_CLOSEST_ADULT_RANGE = UniformIntProvider.create(5, 16);
        SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.HURT_BY, SensorType.ARMADILLO_TEMPTATIONS, SensorType.NEAREST_ADULT, SensorType.ARMADILLO_SCARE_DETECTED);
        MEMORY_MODULE_TYPES = ImmutableList.of(MemoryModuleType.IS_PANICKING, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.WALK_TARGET, MemoryModuleType.LOOK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.GAZE_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, new MemoryModuleType[]{MemoryModuleType.BREED_TARGET, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.DANGER_DETECTED_RECENTLY});
        UNROLL_TASK = TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryAbsent(MemoryModuleType.DANGER_DETECTED_RECENTLY)).apply(context, (memoryQueryResult) -> {
                return (serverWorld, armadillo, l) -> {
                    if (armadillo.isNotIdle()) {
                        armadillo.unroll();
                        return true;
                    } else {
                        return false;
                    }
                };
            });
        });
    }

    public static class UnrollAndFleeTask extends AnimalPanic {
        public UnrollAndFleeTask(float f) {
            super(f, (entity) -> {
                return entity.shouldEscapePowderSnow() || entity.isOnFire();
            });
        }

        protected void run(ServerWorld serverWorld, PathAwareEntity pathAwareEntity, long l) {
            if (pathAwareEntity instanceof ArmadilloEntity armadilloEntity) {
                armadilloEntity.unroll();
            }

            super.run(serverWorld, pathAwareEntity, l);
        }
    }

    public static class RollUpTask extends MultiTickTask<ArmadilloEntity> {
        public RollUpTask() {
            super(Map.of());
        }

        protected void keepRunning(ServerWorld serverWorld, ArmadilloEntity armadilloEntity, long l) {
            super.keepRunning(serverWorld, armadilloEntity, l);
            if (armadilloEntity.shouldSwitchToScaredState()) {
                armadilloEntity.setState(State.SCARED);
                if (armadilloEntity.isOnGround()) {
                    armadilloEntity.playSoundIfNotSilent(SoundEvents.ENTITY_ARMADILLO_LAND);
                }
            }

        }

        protected boolean shouldRun(ServerWorld serverWorld, ArmadilloEntity armadilloEntity) {
            return armadilloEntity.isOnGround();
        }

        protected boolean shouldKeepRunning(ServerWorld serverWorld, ArmadilloEntity armadilloEntity, long l) {
            return true;
        }

        protected void run(ServerWorld serverWorld, ArmadilloEntity armadilloEntity, long l) {
            armadilloEntity.startRolling();
        }

        protected void finishRunning(ServerWorld serverWorld, ArmadilloEntity armadilloEntity, long l) {
            if (!armadilloEntity.canRollUp()) {
                armadilloEntity.unroll();
            }

        }
    }
}
