package net.zepalesque.redux.capability.player;

import com.aetherteam.nitrogen.capability.INBTSynchable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
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

    int prevRebux();
    int rebuxCount();

    void setRebux(int amount);

    void increaseRebux(int amount);

    LoreBookModule getLoreModule();
    BlightshadeModule getBlightshadeModule();
    AdrenalineModule getAdrenalineModule();

    void tick();
    boolean doubleJump();

    void login();

    boolean increaseAirJumpCount();
    int getPrevTickAirJumps();


    int getAirJumpCooldown();

    void deserializeSynchableNBT(CompoundTag tag);


    boolean fireballSetup();

    boolean canShootFireball();


}
