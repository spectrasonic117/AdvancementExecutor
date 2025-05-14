package com.spectrasonic.AdvancementExecutor.advancements;

import com.spectrasonic.Utils.MessageUtils;
import com.spectrasonic.Utils.CommandUtils;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Optional;
import java.util.function.Consumer;

public class Dificil_AdvancementActionRegistry {

    @Getter
    private static final Map<String, Consumer<Player>> advancementActions = new HashMap<>();
    private static final Set<UUID> completedPlayers = new HashSet<>();
    private static final Map<UUID, Set<String>> playerAdvancements = new HashMap<>();
    private static final int TOTAL_ADVANCEMENTS = 15; // Total number of advancements in this category

    static {
        // ---- Nivel Dificil ----
        // Husbandry advancements
        register("minecraft:story/cure_zombie_villager",
                player -> handleAdvancement(player, "minecraft:story/cure_zombie_villager"));
        register("minecraft:husbandry/story/shiny_gear",
                player -> handleAdvancement(player, "minecraft:story/shiny_gear"));
        register("minecraft:husbandry/repair_wolf_armor",
                player -> handleAdvancement(player, "minecraft:husbandry/repair_wolf_armor"));
        register("minecraft:husbandry/obtain_netherite_hoe",
                player -> handleAdvancement(player, "minecraft:husbandry/obtain_netherite_hoe"));
        register("minecraft:husbandry/froglights",
                player -> handleAdvancement(player, "minecraft:husbandry/froglights"));
        register("minecraft:husbandry/allay_deliver_item_to_player",
                player -> handleAdvancement(player, "minecraft:husbandry/allay_deliver_item_to_player"));
        register("minecraft:husbandry/feed_snifflet",
                player -> handleAdvancement(player, "minecraft:husbandry/feed_snifflet"));
        register("minecraft:husbandry/allay_deliver_cake_to_note_block",
                player -> handleAdvancement(player, "minecraft:husbandry/allay_deliver_cake_to_note_block"));
        
        // Nether advancements
        register("minecraft:nether/all_potions",
                player -> handleAdvancement(player, "minecraft:nether/all_potions"));
        register("minecraft:nether/all_effects",
                player -> handleAdvancement(player, "minecraft:nether/all_effects"));
        register("minecraft:nether/create_full_beacon",
                player -> handleAdvancement(player, "minecraft:nether/create_full_beacon"));
        register("minecraft:nether/uneasy_alliance",
                player -> handleAdvancement(player, "minecraft:nether/uneasy_alliance"));
        
        // End advancements
        register("minecraft:end/respawn_dragon",
                player -> handleAdvancement(player, "minecraft:end/respawn_dragon"));
        register("minecraft:end/dragon_breath",
                player -> handleAdvancement(player, "minecraft:end/dragon_breath"));
        register("minecraft:end/levitate",
                player -> handleAdvancement(player, "minecraft:end/levitate"));
    }

    private static void handleAdvancement(Player player, String advancementKey) {
        UUID playerId = player.getUniqueId();
        
        // Skip if already completed all advancements
        if (completedPlayers.contains(playerId)) {
            return;
        }

        // Initialize player's advancement set if needed
        playerAdvancements.putIfAbsent(playerId, new HashSet<>());
        
        // Add the advancement to player's completed set
        Set<String> playerAdvs = playerAdvancements.get(playerId);
        
        // Skip if player already has this advancement
        if (!playerAdvs.add(advancementKey)) {
            return;
        }
        
        // Send the standard message for completing this advancement
        MessageUtils.sendMessage(player, "<green>Logro Completado</green>");
        
        // Check if all advancements are completed
        if (playerAdvs.size() >= TOTAL_ADVANCEMENTS) {
            completedPlayers.add(playerId);
            // Notify the player they completed the category
            MessageUtils.sendMessage(player, "<green>¡Felicidades! Has completado todos los logros de la categoría Difícil</green>");
            // Execute console command
            CommandUtils.ConsoleCommand("say " + player.getName() + " ha completado la categoría Difícil");
        } else {
            // Show progress
            MessageUtils.sendMessage(player, "<yellow>Progreso: " + playerAdvs.size() + "/" + TOTAL_ADVANCEMENTS + " logros completados</yellow>");
        }
    }

    public static void register(String advancementKey, Consumer<Player> action) {
        advancementActions.put(advancementKey, action);
    }

    public static Optional<Consumer<Player>> getAction(String advancementKey) {
        return Optional.ofNullable(advancementActions.get(advancementKey));
    }
}