package net.zepalesque.redux.capability.player;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public interface PlayerTickModule {


    void tick();
    LivingEntity getPlayer();
}
