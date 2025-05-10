package com.spectrasonic.AdvancementExecutor.listeners;

import com.spectrasonic.AdvancementExecutor.advancements.AdvancementActionRegistry;
import com.spectrasonic.Utils.MessageUtils;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.Optional;

/**
 * Listens for player advancements and executes configured actions.
 */
public class AdvancementListener implements Listener {

    @EventHandler
    public void onPlayerAdvancementDone(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        Advancement advancement = event.getAdvancement();
        String key = advancement.getKey().toString();

        Optional<java.util.function.Consumer<Player>> actionOpt = AdvancementActionRegistry.getAction(key);
        if (actionOpt.isPresent()) {
            // Replace %player% placeholder with actual player name in command
            java.util.function.Consumer<Player> action = p -> actionOpt.get().accept(p);
            action.accept(player);

            MessageUtils.sendMessage(player,
                    "<green>¡Has completado el logro!</green> <yellow>" + advancement.getKey().getKey() + "</yellow>");
        }
    }
}
