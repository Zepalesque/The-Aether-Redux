package net.zepalesque.redux.event.listener;

import com.aetherteam.aether.item.EquipmentUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.zepalesque.redux.advancement.trigger.ExtendedReachBreakBlockTrigger;
import net.zepalesque.redux.event.hook.BlockBreakHooks;
import net.zepalesque.redux.item.ReduxItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BlockBreakListener {




    @SubscribeEvent
    public static void modifyMiningSpeed(PlayerEvent.BreakSpeed event)
    {
        float modifiedSpeed = BlockBreakHooks.getBreakSpeed(event.getState().getBlock(), event.getNewSpeed());
        if (modifiedSpeed != event.getNewSpeed())
        {
            event.setNewSpeed(modifiedSpeed);
        }
    }

    @SubscribeEvent
    public static void breakBlock(BlockEvent.BreakEvent event)
    {
        Player player = event.getPlayer();

        double reach = event.getPlayer().getAttributeValue(ForgeMod.BLOCK_REACH.get());
        double baseReach = event.getPlayer().getAttributeBaseValue(ForgeMod.BLOCK_REACH.get());
        LevelAccessor level = event.getLevel();
        float pitch = player.getXRot();
        float yaw = player.getYRot();
        Vec3 eyePos = player.getEyePosition();
        float f2 = Mth.cos(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = Mth.sin(-yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -Mth.cos(-pitch * ((float)Math.PI / 180F));
        float f5 = Mth.sin(-pitch * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3 vec31 = eyePos.add((double)f6 * reach, (double)f5 * reach, (double)f7 * reach);
        BlockHitResult bhr = level.clip(new ClipContext(eyePos, vec31, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, player));

    }
}
