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

// Command
// hexamissions mission facil facil-breed_an_animal <player> force-success

public class Facil_AdvancementActionRegistry {

    @Getter
    private static final Map<String, Consumer<Player>> advancementActions = new HashMap<>();
    private static final Set<UUID> completedPlayers = new HashSet<>();
    private static final Map<UUID, Set<String>> playerAdvancements = new HashMap<>();
    private static final int TOTAL_ADVANCEMENTS = 17; // Total number of advancements in this category

    static {
        // ---- Nivel Facil ----
        register("minecraft:story/mine_stone",
                player -> handleAdvancement(player, "minecraft:story/mine_stone"));
        register("minecraft:story/upgrade_tools",
                player -> handleAdvancement(player, "minecraft:story/upgrade_tools"));
        register("minecraft:story/smelt_iron",
                player -> handleAdvancement(player, "minecraft:story/smelt_iron"));
        register("minecraft:husbandry/plant_seed",
                player -> handleAdvancement(player, "minecraft:husbandry/plant_seed"));
        register("minecraft:husbandry/fishy_business",
                player -> handleAdvancement(player, "minecraft:husbandry/fishy_business"));
        register("minecraft:husbandry/tactical_fishing",
                player -> handleAdvancement(player, "minecraft:husbandry/tactical_fishing"));
        register("minecraft:husbandry/breed_an_animal",
                player -> handleAdvancement(player, "minecraft:husbandry/breed_an_animal"));
        register("minecraft:husbandry/tame_an_animal",
                player -> handleAdvancement(player, "minecraft:husbandry/tame_an_animal"));
        register("minecraft:adventure/sleep_in_bed",
                player -> handleAdvancement(player, "minecraft:adventure/sleep_in_bed"));
        register("minecraft:adventure/ol_betsy",
                player -> handleAdvancement(player, "minecraft:adventure/ol_betsy"));
        register("minecraft:adventure/trim_with_any_armor_pattern",
                player -> handleAdvancement(player, "minecraft:adventure/trim_with_any_armor_pattern"));
        register("minecraft:adventure/shoot_arrow",
                player -> handleAdvancement(player, "minecraft:adventure/shoot_arrow"));
        register("minecraft:adventure/kill_a_mob",
                player -> handleAdvancement(player, "minecraft:adventure/kill_a_mob"));
        register("minecraft:nether/obtain_blaze_rod",
                player -> handleAdvancement(player, "minecraft:nether/obtain_blaze_rod"));
        register("minecraft:nether/find_fortress",
                player -> handleAdvancement(player, "minecraft:nether/find_fortress"));
        register("minecraft:nether/ride_strider",
                player -> handleAdvancement(player, "minecraft:nether/ride_strider"));
        register("minecraft:nether/find_bastion",
                player -> handleAdvancement(player, "minecraft:nether/find_bastion"));
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
        playerAdvs.add(advancementKey);
        
        // Extract advancement name after last slash
        String advancementName = advancementKey.substring(advancementKey.lastIndexOf('/') + 1);
        // Execute command for this advancement
        CommandUtils.ConsoleCommand("hexamissions mission facil facil-" + advancementName + " " + player.getName() + " force-success");
        
        // Check if all advancements are completed
        if (playerAdvs.size() >= TOTAL_ADVANCEMENTS) {
            completedPlayers.add(playerId);
            MessageUtils.sendMessage(player, "<green>¡Felicidades! Has completado todos los logros de la categoría <b>Facil</b></green>");
            // Execute console command
            CommandUtils.ConsoleCommand("say " + player.getName() + " ha completado la categoría Facil");
        } else {
            // Show progress
            MessageUtils.sendMessage(player, "<yellow>Progreso <green><b>Facil</b></green>: " + playerAdvs.size() + "/" + TOTAL_ADVANCEMENTS + " logros completados</yellow>");
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