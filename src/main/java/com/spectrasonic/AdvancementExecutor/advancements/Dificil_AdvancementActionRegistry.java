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
        register("minecraft:nether/ride_strider_in_overworld_lava",
                player -> handleAdvancement(player, "minecraft:nether/ride_strider_in_overworld_lava"));
        register("minecraft:adventure/use_lodestone",
                player -> handleAdvancement(player, "minecraft:adventure/use_lodestone"));
        register("minecraft:nether/obtain_ancient_debris",
                player -> handleAdvancement(player, "nether/obtain_ancient_debris"));
        
        // End advancements
        register("minecraft:end/respawn_dragon",
                player -> handleAdvancement(player, "minecraft:end/respawn_dragon"));
        register("minecraft:end/find_end_city",
                player -> handleAdvancement(player, "minecraft:end/find_end_city"));
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
        
        // Extract advancement name after last slash
        String advancementName = advancementKey.substring(advancementKey.lastIndexOf('/') + 1);
        // Execute command for this advancement
        CommandUtils.ConsoleCommand("hexamissions mission dificil dificil-" + advancementName + " " + player.getName() + " force-success");
        
        // Check if all advancements are completed
        if (playerAdvs.size() >= TOTAL_ADVANCEMENTS) {
            completedPlayers.add(playerId);
            // Notify the player they completed the category
            MessageUtils.sendMessage(player, "<green>¡Felicidades! Has completado todos los logros de la categoría <red><b>Difícil</b></red></green>");
            // Execute console command
            CommandUtils.ConsoleCommand("say " + player.getName() + " ha completado la categoría Difícil");
        } else {
            // Show progress
            MessageUtils.sendMessage(player, "<yellow>Progreso <red><b>Difícil</b></red>: " + playerAdvs.size() + "/" + TOTAL_ADVANCEMENTS + " logros completados</yellow>");
        }
    }

    public static void register(String advancementKey, Consumer<Player> action) {
        advancementActions.put(advancementKey, action);
    }

    public static Optional<Consumer<Player>> getAction(String advancementKey) {
        return Optional.ofNullable(advancementActions.get(advancementKey));
    }

    public static void resetPlayerAdvancements(UUID playerId) {
        playerAdvancements.remove(playerId);
        completedPlayers.remove(playerId);
    }

    public static void addPoints(UUID playerId, int points) {
        Set<String> playerAdvs = playerAdvancements.getOrDefault(playerId, new HashSet<>());
        for (int i = 0; i < points; i++) {
            playerAdvs.add("dummy_advancement_" + i); // Add dummy advancements to simulate points
        }
        playerAdvancements.put(playerId, playerAdvs);
    }

    public static void subtractPoints(UUID playerId, int points) {
        Set<String> playerAdvs = playerAdvancements.getOrDefault(playerId, new HashSet<>());
        for (int i = 0; i < points; i++) {
            playerAdvs.remove("dummy_advancement_" + i); // Remove dummy advancements to simulate points
        }
        playerAdvancements.put(playerId, playerAdvs);
    }
}