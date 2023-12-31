package net.zepalesque.redux.entity.misc;

import com.aetherteam.aether.item.AetherItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.zepalesque.redux.api.blockhandler.WoodHandler;

import javax.annotation.Nonnull;

public class ReduxChestBoat extends ChestBoat implements ReduxBoatBehavior {
    private WoodHandler woodHandler;
    public ReduxChestBoat(EntityType<? extends ReduxChestBoat> type, Level level) {
        super(type, level);
    }

    public ReduxChestBoat(WoodHandler pWoodHandler, Level level, double x, double y, double z) {
        this(pWoodHandler.chestBoatEntity.get(), level);
        this.setPos(x, y, z);
        this.xo = x;
        this.yo = y;
        this.zo = z;
    }

    @Nonnull
    public Item getDropItem() {
        return getBoat();
    }

    protected void checkFallDamage(double y, boolean onGround, @Nonnull BlockState state, @Nonnull BlockPos pos) {
        this.fall(this, y, onGround);
    }

    @Nonnull
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    public Item getStick() {
        return AetherItems.SKYROOT_STICK.get();
    }

    @Override
    public Item getPlanks() {
        return this.woodHandler == null ? Blocks.AIR.asItem() : ((ItemLike)this.woodHandler.planks.get()).asItem();

    }

    @Override
    public Item getBoat() {
        return this.woodHandler == null ? Blocks.AIR.asItem() : ((ItemLike)this.woodHandler.chestBoatItem.get()).asItem();
    }
}
