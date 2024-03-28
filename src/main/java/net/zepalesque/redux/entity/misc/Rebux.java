package net.zepalesque.redux.entity.misc;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.util.ITeleporter;
import net.minecraftforge.fluids.FluidType;
import net.zepalesque.redux.capability.player.ReduxPlayer;
import net.zepalesque.redux.entity.ReduxEntityTypes;

import javax.annotation.Nullable;

public class Rebux extends Entity {
    private static final int LIFETIME = 6000;
    private static final int MAX_SIZE = 128;
    private int age;
    private static final EntityDataAccessor<Integer> COUNT = SynchedEntityData.defineId(Rebux.class, EntityDataSerializers.INT);
    public int lifespan;
    public final float bobOffs;

    public Rebux(EntityType<?> entityType, Level level) {
        super(entityType, level);
        this.lifespan = LIFETIME;
        this.bobOffs = this.random.nextFloat() * 3.1415927F * 2.0F;
        this.setYRot(this.random.nextFloat() * 360.0F);
        this.setCount(1);
    }

    public Rebux(Level level, double posX, double posY, double posZ, int count) {
        this(level, posX, posY, posZ);
        this.setCount(count);
    }
    public Rebux(Level level, double posX, double posY, double posZ) {
        this(ReduxEntityTypes.REBUX.get(), level);
        this.setPos(posX, posY, posZ);
    }


    @Override
    public void tick() {
        super.tick();

        this.xo = this.getX();
        this.yo = this.getY();
        this.zo = this.getZ();
        Vec3 vec3 = this.getDeltaMovement();
        float f = this.getEyeHeight() - 0.11111111F;
        FluidType fluidType = this.getMaxHeightFluidType();
        if (!fluidType.isAir() && !fluidType.isVanilla() && this.getFluidTypeHeight(fluidType) > (double)f) {
            this.setDeltaMovement(vec3.x * 0.9900000095367432, vec3.y + (double)(vec3.y < 0.05999999865889549 ? 5.0E-4F : 0.0F), vec3.z * 0.9900000095367432);
        } else if (this.isInWater() && this.getFluidHeight(FluidTags.WATER) > (double)f) {
            this.setUnderwaterMovement();
        } else if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) > (double)f) {
            this.setUnderLavaMovement();
        } else if (!this.isNoGravity()) {
            this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.04, 0.0));
        }

        if (this.level().isClientSide) {
            this.noPhysics = false;
        } else {
            this.noPhysics = !this.level().noCollision(this, this.getBoundingBox().deflate(1.0E-7));
            if (this.noPhysics) {
                this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
            }
        }

        if (!this.onGround() || this.getDeltaMovement().horizontalDistanceSqr() > 9.999999747378752E-6 || (this.tickCount + this.getId()) % 4 == 0) {
            this.move(MoverType.SELF, this.getDeltaMovement());
            float f1 = 0.98F;
            if (this.onGround()) {
                BlockPos groundPos = this.getBlockPosBelowThatAffectsMyMovement();
                f1 = this.level().getBlockState(groundPos).getFriction(this.level(), groundPos, this) * 0.98F;
            }

            this.setDeltaMovement(this.getDeltaMovement().multiply((double) f1, 0.98, (double) f1));
            if (this.onGround()) {
                Vec3 vec31 = this.getDeltaMovement();
                if (vec31.y < 0.0) {
                    this.setDeltaMovement(vec31.multiply(1.0, -0.5, 1.0));
                }
            }

            boolean flag = Mth.floor(this.xo) != Mth.floor(this.getX()) || Mth.floor(this.yo) != Mth.floor(this.getY()) || Mth.floor(this.zo) != Mth.floor(this.getZ());
            int i = flag ? 2 : 40;
            if (this.tickCount % i == 0 && !this.level().isClientSide && this.isMergable()) {
                this.mergeWithNeighbours();
            }

            if (this.age != -32768) {
                ++this.age;
            }

            this.hasImpulse |= this.updateInWaterStateAndDoFluidPushing();
            if (!this.level().isClientSide) {
                double d0 = this.getDeltaMovement().subtract(vec3).lengthSqr();
                if (d0 > 0.01) {
                    this.hasImpulse = true;
                }
            }

            if (!this.level().isClientSide && this.age >= this.lifespan) {
                this.discard();
            }
            if (this.getCount() <= 0 && !this.isRemoved()) {
                this.discard();
            }
        }
    }

    public void playerTouch(Player entity) {
        if (!this.level().isClientSide) {

            int count = this.getCount();
            ReduxPlayer.get(entity).ifPresent(reduxPlayer -> reduxPlayer.increaseRebux(count));
            this.setCount(0);
            this.discard();
        }

    }

    public int getCount() {
        return this.getEntityData().get(COUNT);
    }
    public void setCount(int count) {
        this.getEntityData().set(COUNT, count);
    }

    @Override
    protected void defineSynchedData() {
        this.entityData.define(COUNT, 0);
    }

    public BlockPos getBlockPosBelowThatAffectsMyMovement() {
        return this.getOnPos(0.999999F);
    }

    private void setUnderwaterMovement() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x * 0.9900000095367432, vec3.y + (double)(vec3.y < 0.05999999865889549 ? 5.0E-4F : 0.0F), vec3.z * 0.9900000095367432);
    }

    private void setUnderLavaMovement() {
        Vec3 vec3 = this.getDeltaMovement();
        this.setDeltaMovement(vec3.x * 0.949999988079071, vec3.y + (double)(vec3.y < 0.05999999865889549 ? 5.0E-4F : 0.0F), vec3.z * 0.949999988079071);
    }

    private void mergeWithNeighbours() {
        if (this.isMergable()) {

            for (Rebux rebux : this.level().getEntitiesOfClass(Rebux.class, this.getBoundingBox().inflate(0.5, 0.0, 0.5), (neighbour) -> neighbour != this && neighbour.isMergable())) {
                if (rebux.isMergable()) {
                    this.tryToMerge(rebux);
                    if (this.isRemoved()) {
                        break;
                    }
                }
            }
        }
    }

    private boolean isMergable() {
        return this.isAlive() && this.age != -32768 && this.age < 6000 && this.getCount() < MAX_SIZE;
    }

    private void tryToMerge(Rebux rebux) {
        int count = this.getCount();
        int count1 = rebux.getCount();
        int both = count + count1;
        if (both < MAX_SIZE) {
            if (count < MAX_SIZE) {
                this.setCount(both);
                rebux.setCount(0);
            } else {
                rebux.setCount(both);
                this.setCount(0);
            }
        }

    }

    @Nullable
    public Entity changeDimension(ServerLevel p_server, ITeleporter teleporter) {
        Entity entity = super.changeDimension(p_server, teleporter);
        if (!this.level().isClientSide && entity instanceof Rebux) {
            ((Rebux)entity).mergeWithNeighbours();
        }

        return entity;
    }

    public int getAge() {
        return this.age;
    }

    public void setUnlimitedLifetime() {
        this.age = -32768;
    }

    public void setExtendedLifetime() {
        this.age = -6000;
    }

    @Override
    public float getVisualRotationYInDegrees() {
        return 180.0F - this.getSpin(0.5F) / 6.2831855F * 360.0F;
    }

    public float getSpin(float partialTicks) {
        return ((float)this.getAge() + partialTicks) / 20.0F + this.bobOffs;
    }

    public boolean isAttackable() {
        return false;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putShort("Age", (short)this.age);
        compound.putInt("Lifespan", this.lifespan);
        compound.putInt("Count", this.getCount());
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        this.age = compound.getShort("Age");
        if (compound.contains("Lifespan")) {
            this.lifespan = compound.getInt("Lifespan");
        }
        if (compound.contains("Count")) {
            this.setCount(compound.getInt("Count"));
        }
    }
}
