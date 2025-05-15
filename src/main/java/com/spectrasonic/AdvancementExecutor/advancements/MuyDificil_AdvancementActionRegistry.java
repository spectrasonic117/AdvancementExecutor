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

public class MuyDificil_AdvancementActionRegistry {

    @Getter
    private static final Map<String, Consumer<Player>> advancementActions = new HashMap<>();
    private static final Set<UUID> completedPlayers = new HashSet<>();
    private static final Map<UUID, Set<String>> playerAdvancements = new HashMap<>();
    private static final int TOTAL_ADVANCEMENTS = 16; // Total number of advancements in this category

    static {
        // ---- Nivel Muy Dificil ----
        // Husbandry advancements
        register("minecraft:husbandry/complete_catalogue",
                player -> handleAdvancement(player, "minecraft:husbandry/complete_catalogue"));
        register("minecraft:husbandry/whole_pack",
                player -> handleAdvancement(player, "minecraft:husbandry/whole_pack"));
        register("minecraft:husbandry/bred_all_animals",
                player -> handleAdvancement(player, "minecraft:husbandry/bred_all_animals"));
        register("minecraft:husbandry/balanced_diet",
                player -> handleAdvancement(player, "minecraft:husbandry/balanced_diet"));
        
        // Adventure advancements
        register("minecraft:adventure/crafters_crafting_crafters",
                player -> handleAdvancement(player, "minecraft:adventure/crafters_crafting_crafters"));
        register("minecraft:adventure/kill_all_mobs",
                player -> handleAdvancement(player, "minecraft:adventure/kill_all_mobs"));
        register("minecraft:adventure/totem_of_undying",
                player -> handleAdvancement(player, "minecraft:adventure/totem_of_undying"));
        register("minecraft:adventure/trim_with_all_exclusive_armor_patterns",
                player -> handleAdvancement(player, "minecraft:adventure/trim_with_all_exclusive_armor_patterns"));
        register("minecraft:adventure/play_jukebox_in_meadows",
                player -> handleAdvancement(player, "minecraft:adventure/play_jukebox_in_meadows"));
        register("minecraft:adventure/overoverkill",
                player -> handleAdvancement(player, "minecraft:adventure/overoverkill"));
        register("minecraft:adventure/adventuring_time",
                player -> handleAdvancement(player, "minecraft:adventure/adventuring_time"));
        
        // Nether advancements
        register("minecraft:nether/all_effects",
                player -> handleAdvancement(player, "minecraft:nether/all_effects"));
        register("minecraft:nether/uneasy_alliance",
                player -> handleAdvancement(player, "minecraft:nether/uneasy_alliance"));
        register("minecraft:nether/netherite_armor",
                player -> handleAdvancement(player, "minecraft:nether/netherite_armor"));
                
        // End advancements
        register("minecraft:end/levitate",
                player -> handleAdvancement(player, "minecraft:end/levitate"));
        register("minecraft:end/elytra",
                player -> handleAdvancement(player, "minecraft:end/elytra"));
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
        CommandUtils.ConsoleCommand("hexamissions mission muydificil muydificil-" + advancementName + " " + player.getName() + " force-success");
        
        // Check if all advancements are completed
        if (playerAdvs.size() >= TOTAL_ADVANCEMENTS) {
            completedPlayers.add(playerId);
            // Notify the player they completed the category
            MessageUtils.sendMessage(player, "<green>¡Felicidades! Has completado todos los logros de la categoría <#FF00BE><b>Muy Difícil</b></#FF00BE></green>");
            // Execute console command
            CommandUtils.ConsoleCommand("say " + player.getName() + " ha completado la categoría Muy Difícil");
        } else {
            // Show progress
            MessageUtils.sendMessage(player, "<yellow>Progreso <#FF00BE><b>Muy Difícil</b></#FF00BE>: " + playerAdvs.size() + "/" + TOTAL_ADVANCEMENTS + " logros completados</yellow>");
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