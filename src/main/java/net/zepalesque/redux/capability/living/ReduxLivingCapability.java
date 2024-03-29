package net.zepalesque.redux.capability.living;

import com.aetherteam.aether.item.EquipmentUtil;
import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.network.simple.SimpleChannel;
import net.zepalesque.redux.capability.player.RainbowCloudModule;
import net.zepalesque.redux.item.ReduxItems;
import net.zepalesque.redux.network.ReduxPacketHandler;
import net.zepalesque.redux.network.packet.VampireAmuletSyncPacket;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ReduxLivingCapability implements ReduxLiving {


    private final LivingEntity mob;
    private final RainbowCloudModule rainbow;

    public ReduxLivingCapability(LivingEntity mob) {
        this.mob = mob;
        this.rainbow = new RainbowCloudModule(mob);
    }



    @Override
    public RainbowCloudModule getRainbowModule() {
        return this.rainbow;
    }


    @Override
    public LivingEntity getMob() {
        return this.mob;
    }

    @Override
    public void tick() {
        if (!this.mob.level().isClientSide()) {
            this.rainbow.tick();
        }
    }


}
