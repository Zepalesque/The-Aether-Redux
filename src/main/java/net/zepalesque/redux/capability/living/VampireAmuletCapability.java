package net.zepalesque.redux.capability.living;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.VampireAmuletSyncPacket;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class VampireAmuletCapability implements VampireAmulet {


    private final LivingEntity mob;
    private boolean hasCurio = false;
    private boolean active = false;
    private int timer = 24000;
    private int timeWithout = 0;


    private final Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> synchableFunctions = Map.ofEntries(
            Map.entry("ability_enabled", Triple.of(Type.BOOLEAN, (object) -> this.setAbilityUse((Boolean) object), this::canUseAbility))
    );

    public VampireAmuletCapability(LivingEntity mob) {
        this.mob = mob;
    }

    @Override
    public Map<String, Triple<Type, Consumer<Object>, Supplier<Object>>> getSynchableFunctions() {
        return this.synchableFunctions;
    }

    @Override
    public LivingEntity getMob() {
        return this.mob;
    }

    @Override
    public boolean canUseAbility() {
        return this.active;
    }

    @Override
    public void setAbilityUse(boolean b) {
        this.active = b;
    }

    @Override
    public int countdown() {
        return this.timer;
    }

    @Override
    public boolean hasCurio() {
        return this.hasCurio;
    }

    @Override
    public void resetTimer() {
        this.timer = 24000;
    }

    @Override
    public void tick() {
        if (!this.mob.level().isClientSide()) {
            this.hasCurio = EquipmentUtil.hasCurio(this.getMob(), ReduxItems.VAMPIRE_AMULET.get());
            if (!hasCurio && timeWithout < 20) {
                timeWithout++;
            } else {
                timeWithout = 0;
            } if (timeWithout >= 20) {
                this.resetTimer();
            }

            if (this.hasCurio && this.timer > 0) {
                this.timer--;
            }
            if (this.hasCurio && this.timer <= 0 && !this.canUseAbility()) {
                this.setSynched(Direction.CLIENT, "ability_enabled", true);
            }
            if ((!this.hasCurio || this.timer > 0) && this.canUseAbility()) {
                this.setSynched(Direction.CLIENT, "ability_enabled", false);
            }
        }
    }

    @Override
    public BasePacket getSyncPacket(String s, Type type, Object o) {
        return new VampireAmuletSyncPacket(this.getMob().getId(), s, type, o);
    }


    @Override
    public SimpleChannel getPacketChannel() {
        return ReduxPacketHandler.INSTANCE;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        nbt.putBoolean("active", this.active);
        nbt.putInt("timer", this.timer);
        nbt.putBoolean("has_curio", this.hasCurio);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        this.active = nbt.getBoolean("active");
        this.timer = nbt.getInt("timer");
        this.hasCurio = nbt.getBoolean("has_curio");
    }
}
