package net.zepalesque.redux.command;

import com.aetherteam.aether.capability.player.AetherPlayer;
import com.aetherteam.aether.network.AetherPacketHandler;
import com.aetherteam.aether.network.packet.clientbound.HealthResetPacket;
import com.aetherteam.nitrogen.capability.INBTSynchable;
import com.aetherteam.nitrogen.network.PacketRelay;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.GameProfileArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.zepalesque.redux.capability.player.ReduxPlayer;

import java.util.Collection;

public class RebuxCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("rebux")
                .then(Commands.literal("player").requires((commandSourceStack) -> commandSourceStack.hasPermission(2))
                        .then(Commands.literal("set")
                                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                                                .suggests((context, builder) -> {
                                                    PlayerList playerlist = context.getSource().getServer().getPlayerList();
                                                    return SharedSuggestionProvider.suggest(playerlist.getPlayers().stream().map((player) -> player.getGameProfile().getName()), builder);
                                                }).then(Commands.argument("value", IntegerArgumentType.integer(0)).executes((context) -> setRebux(context.getSource(), GameProfileArgument.getGameProfiles(context, "targets"), IntegerArgumentType.getInteger(context, "value"))))
                                        )
                        ).then(Commands.literal("add")
                                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                                                .suggests((context, builder) -> {
                                                    PlayerList playerlist = context.getSource().getServer().getPlayerList();
                                                    return SharedSuggestionProvider.suggest(playerlist.getPlayers().stream().map((player) -> player.getGameProfile().getName()), builder);
                                                }).then(Commands.argument("value", IntegerArgumentType.integer()).executes((context) -> addRebux(context.getSource(), GameProfileArgument.getGameProfiles(context, "targets"), IntegerArgumentType.getInteger(context, "value"))))
                                        )
                        ).then(Commands.literal("query")
                                .then(Commands.argument("targets", GameProfileArgument.gameProfile())
                                                .suggests((context, builder) -> {
                                                    PlayerList playerlist = context.getSource().getServer().getPlayerList();
                                                    return SharedSuggestionProvider.suggest(playerlist.getPlayers().stream().map((player) -> player.getGameProfile().getName()), builder);
                                                }).executes((context) -> queryRebux(context.getSource(), GameProfileArgument.getGameProfiles(context, "targets")))
                                        )
                        )


                )
        );
    }

    /**
     * Sets the Life Shard (half) heart count of a list of players to a specific value.
     * @param source The {@link CommandSourceStack}.
     * @param gameProfiles A {@link Collection} of {@link GameProfile}s to execute the command on.
     * @param value The {@link Integer} value for the amount of Life Shard hearts.
     * @return An {@link Integer}.
     */
    private static int setRebux(CommandSourceStack source, Collection<GameProfile> gameProfiles, int value) {
        PlayerList playerList = source.getServer().getPlayerList();
        for (GameProfile gameProfile : gameProfiles) {
            ServerPlayer player = playerList.getPlayer(gameProfile.getId());
            if (player != null) {
                ReduxPlayer.get(player).ifPresent(aetherPlayer -> {
                    Player innerPlayer = aetherPlayer.getPlayer();
                    aetherPlayer.setSynched(INBTSynchable.Direction.CLIENT, "rebux", value);
                    source.sendSuccess(() -> Component.translatable("commands.aether_redux.capability.player.rebux.set", innerPlayer.getDisplayName(), value), true);
                });
            }
        }
        return 1;
    }
    private static int addRebux(CommandSourceStack source, Collection<GameProfile> gameProfiles, int value) {
        ServerLevel level = source.getLevel();
        PlayerList playerList = source.getServer().getPlayerList();
        for (GameProfile gameProfile : gameProfiles) {
            ServerPlayer player = playerList.getPlayer(gameProfile.getId());
            if (player != null) {
                ReduxPlayer.get(player).ifPresent(aetherPlayer -> {
                    Player innerPlayer = aetherPlayer.getPlayer();
                    int count = aetherPlayer.rebuxCount();
                    aetherPlayer.setSynched(INBTSynchable.Direction.CLIENT, "rebux", count + value);
                    source.sendSuccess(() -> Component.translatable("commands.aether_redux.capability.player.rebux.add", innerPlayer.getDisplayName(), value), true);
                });
            }
        }
        return 1;
    }
    private static int queryRebux(CommandSourceStack source, Collection<GameProfile> gameProfiles) {
        ServerLevel level = source.getLevel();
        PlayerList playerList = source.getServer().getPlayerList();
        for (GameProfile gameProfile : gameProfiles) {
            ServerPlayer player = playerList.getPlayer(gameProfile.getId());
            if (player != null) {
                ReduxPlayer.get(player).ifPresent(aetherPlayer -> {
                    Player innerPlayer = aetherPlayer.getPlayer();
                    int count = aetherPlayer.rebuxCount();
                    source.sendSuccess(() -> Component.translatable("commands.aether_redux.capability.player.rebux.query", count, innerPlayer.getDisplayName()), true);
                });
            }
        }
        return 1;
    }
}