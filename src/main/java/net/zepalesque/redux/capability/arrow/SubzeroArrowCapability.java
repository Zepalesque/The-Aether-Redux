package net.zepalesque.redux.capability.arrow;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.SubzeroArrowSyncPacket;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SubzeroArrowCapability implements SubzeroArrow {
    private final AbstractArrow arrow;

    private boolean isSubzeroArrow;
    private boolean hitGround = false;
    private int slownessTime;
    private int hitGroundTimer;



    /**
     * Stores the following methods as able to be synced between client and server and vice-versa.
     */
    private final Map<String, Triple<INBTSynchable.Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("setSubzeroArrow", Triple.of(INBTSynchable.Type.BOOLEAN, (object) -> this.setSubzeroArrow((boolean) object), this::isSubzeroArrow)),
            Map.entry("setSlownessTime", Triple.of(INBTSynchable.Type.BOOLEAN, (object) -> this.setSlownessTime((int) object), this::getSlownessTime)),
            Map.entry("setHitGround", Triple.of(INBTSynchable.Type.BOOLEAN, (object) -> this.setHitGround((boolean) object), this::hitGround)),
            Map.entry("setHitGroundTimer", Triple.of(INBTSynchable.Type.BOOLEAN, (object) -> this.setHitGroundTimer((int) object), this::getHitGroundTimer  ))
    );
    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }
    @Override
    public BasePacket getSyncPacket(String key, Type type, Object value) {
        return new SubzeroArrowSyncPacket(this.getArrow().getId(), key, type, value);
    }

    @Override
    public SimpleChannel getPacketChannel() {
        return ReduxPacketHandler.INSTANCE;
    }

    public SubzeroArrowCapability(AbstractArrow arrow) {
        this.arrow = arrow;
    }

    @Override
    public AbstractArrow getArrow() {
        return this.arrow;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("SubzeroArrow", this.isSubzeroArrow());
        tag.putBoolean("SubzeroHitGround", this.hitGround());
        tag.putInt("SlownessTime", this.getSlownessTime());
        tag.putInt("SubzeroHitGroundTimer", this.getHitGroundTimer());
        return tag;
    }

    @Override
    public void deserializeNBT(CompoundTag tag) {
        if (tag.contains("SubzeroArrow")) {
            this.setSubzeroArrow(tag.getBoolean("SubzeroArrow"));
        }
        if (tag.contains("SlownessTime")) {
            this.setSlownessTime(tag.getInt("SlownessTime"));
        }
        if (tag.contains("SubzeroHitGroundTimer")) {
            this.setHitGroundTimer(tag.getInt("SubzeroHitGroundTimer"));
        }
        if (tag.contains("SubzeroHitGround")) {
            this.setHitGround(tag.getBoolean("SubzeroHitGround"));
        }
    }

    @Override
    public void setSubzeroArrow(boolean isSubzeroArrow) {
        this.isSubzeroArrow = isSubzeroArrow;
    }

    @Override
    public boolean isSubzeroArrow() {
        return this.isSubzeroArrow;
    }

    @Override
    public void setSlownessTime(int time) {
        this.slownessTime = time;
    }

    @Override
    public int getSlownessTime() {
        return this.slownessTime;
    }

    @Override
    public boolean hitGround() {
        return hitGround;
    }
    @Override
    public void setHitGround(boolean hitGround) {
        this.hitGround = hitGround;
    }

    @Override
    public void tick() {
        if (this.hitGroundTimer >= 0)
        {
            this.hitGroundTimer--;
        }
        if (this.hitGroundTimer == 0)
        {
            this.hitGround = true;
        }
    }

    @Override
    public int getHitGroundTimer() {
        return this.hitGroundTimer;
    }

    @Override
    public void setHitGroundTimer(int time) {
        this.hitGroundTimer = time;
    }
}
