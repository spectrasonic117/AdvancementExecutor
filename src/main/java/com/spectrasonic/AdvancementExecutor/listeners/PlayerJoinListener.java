package com.spectrasonic.AdvancementExecutor.listeners;

import com.spectrasonic.Utils.CommandUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerJoinListener implements Listener {

    private static final Set<UUID> processedPlayers = new HashSet<>();

    @EventHandler
    public void onPlayerFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        
        if (!player.hasPlayedBefore() && !processedPlayers.contains(player.getUniqueId())) {
            String command = "hexamissions mission all all " + player.getName() + " join";
            CommandUtils.ConsoleCommand(command);
            processedPlayers.add(player.getUniqueId());
        }
    }
}