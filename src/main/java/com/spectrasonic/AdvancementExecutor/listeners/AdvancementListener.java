package com.spectrasonic.AdvancementExecutor.listeners;

import com.spectrasonic.AdvancementExecutor.advancements.Facil_AdvancementActionRegistry;
import com.spectrasonic.AdvancementExecutor.advancements.Medio_AdvancementActionRegistry;
import com.spectrasonic.AdvancementExecutor.advancements.Intermedio_AdvancementActionRegistry;
import com.spectrasonic.AdvancementExecutor.advancements.Dificil_AdvancementActionRegistry;
import com.spectrasonic.AdvancementExecutor.advancements.MuyDificil_AdvancementActionRegistry;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.function.Consumer;
import java.util.Optional;

/**
 * Listens for player advancements and executes configured actions.
 */
public class AdvancementListener implements Listener {

    @EventHandler
    public void onPlayerAdvancement(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        Advancement advancement = event.getAdvancement();
        String key = advancement.getKey().toString();

        // Check each registry for the advancement
        checkAndExecuteAction(Facil_AdvancementActionRegistry.getAction(key), player);
        checkAndExecuteAction(Medio_AdvancementActionRegistry.getAction(key), player);
        checkAndExecuteAction(Intermedio_AdvancementActionRegistry.getAction(key), player);
        checkAndExecuteAction(Dificil_AdvancementActionRegistry.getAction(key), player);
        checkAndExecuteAction(MuyDificil_AdvancementActionRegistry.getAction(key), player);
    }

    private void checkAndExecuteAction(Optional<Consumer<Player>> action, Player player) {
        action.ifPresent(consumer -> consumer.accept(player));
    }
}