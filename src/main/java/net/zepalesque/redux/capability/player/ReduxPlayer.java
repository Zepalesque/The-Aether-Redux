package net.zepalesque.redux.capability.player;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.util.LazyOptional;
import net.zepalesque.redux.capability.ReduxCapabilities;

public interface ReduxPlayer extends INBTSynchable<CompoundTag> {
    Player getPlayer();

    static LazyOptional<ReduxPlayer> get(Player player) {
        return player.getCapability(ReduxCapabilities.REDUX_PLAYER);
    }
    int getMaxAirJumps();
    void setMaxAirJumps(int jumps);
    int getAirJumpsPerformed();


    int ticksInAir();

    LoreBookModule getLoreModule();
    BlightshadeModule getBlightshadeModule();

    void tick();
    boolean doubleJump();


    boolean increaseAirJumpCount();
    int getPrevTickAirJumps();


    int getAirJumpCooldown();

    void deserializeSynchableNBT(CompoundTag tag);

    int fireballCooldown();


    boolean fireballSetup();

    boolean canShootFireball();


}
