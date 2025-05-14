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

public class Medio_AdvancementActionRegistry {

    @Getter
    private static final Map<String, Consumer<Player>> advancementActions = new HashMap<>();
    private static final Set<UUID> completedPlayers = new HashSet<>();
    private static final Map<UUID, Set<String>> playerAdvancements = new HashMap<>();
    private static final int TOTAL_ADVANCEMENTS = 24; // Updated total number of advancements in this category

    static {
        // ---- Nivel Medio ----
        register("minecraft:story/iron_tools",
                player -> handleAdvancement(player, "minecraft:story/iron_tools"));
        register("minecraft:story/lava_bucket",
                player -> handleAdvancement(player, "minecraft:story/lava_bucket"));
        register("minecraft:story/obtain_armor",
                player -> handleAdvancement(player, "minecraft:story/obtain_armor"));
        register("minecraft:husbandry/make_a_sign_glow",
                player -> handleAdvancement(player, "minecraft:husbandry/make_a_sign_glow"));
        register("minecraft:adventure/salvage_sherd",
                player -> handleAdvancement(player, "minecraft:adventure/salvage_sherd"));
        register("minecraft:husbandry/wax_off",
                player -> handleAdvancement(player, "minecraft:husbandry/wax_off"));
        register("minecraft:adventure/avoid_vibration",
                player -> handleAdvancement(player, "minecraft:adventure/avoid_vibration"));
        register("minecraft:adventure/trade",
                player -> handleAdvancement(player, "minecraft:adventure/trade"));
        register("minecraft:adventure/who_needs_rockets",
                player -> handleAdvancement(player, "minecraft:adventure/who_needs_rockets"));
        register("minecraft:adventure/brush_armadillo",
                player -> handleAdvancement(player, "minecraft:adventure/brush_armadillo"));
        register("minecraft:adventure/walk_on_powder_snow_with_leather_boots",
                player -> handleAdvancement(player, "minecraft:adventure/walk_on_powder_snow_with_leather_boots"));
        register("minecraft:adventure/minecraft_trials_edition",
                player -> handleAdvancement(player, "minecraft:adventure/minecraft_trials_edition"));
        register("minecraft:adventure/lighten_up",
                player -> handleAdvancement(player, "minecraft:adventure/lighten_up"));
        register("minecraft:adventure/spyglass_at_parrot",
                player -> handleAdvancement(player, "minecraft:adventure/spyglass_at_parrot"));
        register("minecraft:adventure/under_lock_and_key",
                player -> handleAdvancement(player, "minecraft:adventure/under_lock_and_key"));
        register("minecraft:nether/brew_potion",
                player -> handleAdvancement(player, "minecraft:nether/brew_potion"));
        register("minecraft:nether/get_wither_skull",
                player -> handleAdvancement(player, "minecraft:nether/get_wither_skull"));
        register("minecraft:nether/distract_piglin",
                player -> handleAdvancement(player, "minecraft:nether/distract_piglin"));
        register("minecraft:nether/obtain_crying_obsidian",
                player -> handleAdvancement(player, "minecraft:nether/obtain_crying_obsidian"));
        register("minecraft:nether/charge_respawn_anchor",
                player -> handleAdvancement(player, "minecraft:nether/charge_respawn_anchor"));
        register("minecraft:nether/loot_bastion",
                player -> handleAdvancement(player, "minecraft:nether/loot_bastion"));
        register("minecraft:end/root",
                player -> handleAdvancement(player, "minecraft:end/root"));
        register("minecraft:end/enter_end_gateway",
                player -> handleAdvancement(player, "minecraft:end/enter_end_gateway"));
        register("minecraft:end/dragon_breath",
                player -> handleAdvancement(player, "minecraft:end/dragon_breath"));
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
        // MessageUtils.sendMessage(player, "<green>Logro Completado</green>");
        
        // Check if all advancements are completed
        if (playerAdvs.size() >= TOTAL_ADVANCEMENTS) {
            completedPlayers.add(playerId);
            // Notify the player they completed the category
            MessageUtils.sendMessage(player, "<green>¡Felicidades! Has completado todos los logros de la categoría <yellow><b>Medio</b></yellow></green>");
            // Execute console command
            CommandUtils.ConsoleCommand("say " + player.getName() + " ha completado la categoría Medio");
        } else {
            // Show progress
            MessageUtils.sendMessage(player, "<yellow>Progreso <b>Medio</b>: " + playerAdvs.size() + "/" + TOTAL_ADVANCEMENTS + " logros completados</yellow>");
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
}