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

public class Intermedio_AdvancementActionRegistry {

    @Getter
    private static final Map<String, Consumer<Player>> advancementActions = new HashMap<>();
    private static final Set<UUID> completedPlayers = new HashSet<>();
    private static final Map<UUID, Set<String>> playerAdvancements = new HashMap<>();
    private static final int TOTAL_ADVANCEMENTS = 29; // Total number of advancements in this category

    static {
        // ---- Nivel Intermedio ----
        // Story advancements
        register("minecraft:story/enchant_item",
                player -> handleAdvancement(player, "minecraft:story/enchant_item"));
        register("minecraft:story/form_obsidian",
                player -> handleAdvancement(player, "minecraft:story/form_obsidian"));
        register("minecraft:story/enter_the_nether",
                player -> handleAdvancement(player, "minecraft:story/enter_the_nether"));
        register("minecraft:story/follow_ender_eye",
                player -> handleAdvancement(player, "minecraft:story/follow_ender_eye"));
        register("minecraft:story/mine_diamond",
                player -> handleAdvancement(player, "minecraft:story/mine_diamond"));

        // Husbandry advancements
        register("minecraft:husbandry/tadpole_in_a_bucket",
                player -> handleAdvancement(player, "minecraft:husbandry/tadpole_in_a_bucket"));
        register("minecraft:husbandry/plant_any_sniffer_seed",
                player -> handleAdvancement(player, "minecraft:husbandry/plant_any_sniffer_seed"));
        register("minecraft:husbandry/safely_harvest_honey",
                player -> handleAdvancement(player, "minecraft:husbandry/safely_harvest_honey"));
        register("minecraft:husbandry/obtain_sniffer_egg",
                player -> handleAdvancement(player, "minecraft:husbandry/obtain_sniffer_egg"));
        register("minecraft:husbandry/silk_touch_nest",
                player -> handleAdvancement(player, "minecraft:husbandry/silk_touch_nest"));
        register("minecraft:husbandry/ride_a_boat_with_a_goat",
                player -> handleAdvancement(player, "minecraft:husbandry/ride_a_boat_with_a_goat"));
        register("minecraft:husbandry/kill_axolotl_target",
                player -> handleAdvancement(player, "minecraft:husbandry/kill_axolotl_target"));
        register("minecraft:husbandry/wax_on",
                player -> handleAdvancement(player, "minecraft:husbandry/wax_on"));
        register("minecraft:husbandry/axolotl_in_a_bucket",
                player -> handleAdvancement(player, "minecraft:husbandry/axolotl_in_a_bucket"));

        // Adventure advancements
        register("minecraft:adventure/two_birds_one_arrow",
                player -> handleAdvancement(player, "minecraft:adventure/two_birds_one_arrow"));
        register("minecraft:adventure/kill_mob_near_sculk_catalyst",
                player -> handleAdvancement(player, "minecraft:adventure/kill_mob_near_sculk_catalyst"));
        register("minecraft:adventure/blowback",
                player -> handleAdvancement(player, "minecraft:adventure/blowback"));
        register("minecraft:adventure/read_power_of_chiseled_bookshelf",
                player -> handleAdvancement(player, "minecraft:adventure/read_power_of_chiseled_bookshelf"));
        register("minecraft:adventure/voluntary_exile",
                player -> handleAdvancement(player, "minecraft:adventure/voluntary_exile"));
        register("minecraft:adventure/spyglass_at_ghast",
                player -> handleAdvancement(player, "minecraft:adventure/spyglass_at_ghast"));
        register("minecraft:adventure/craft_decorated_pot_using_only_sherds",
                player -> handleAdvancement(player, "minecraft:adventure/craft_decorated_pot_using_only_sherds"));
        register("minecraft:adventure/summon_iron_golem",
                player -> handleAdvancement(player, "minecraft:adventure/summon_iron_golem"));
        register("minecraft:adventure/bullseye",
                player -> handleAdvancement(player, "minecraft:adventure/bullseye"));

        // Nether advancements
        register("minecraft:nether/create_beacon",
                player -> handleAdvancement(player, "minecraft:nether/create_beacon"));
        register("minecraft:nether/summon_wither",
                player -> handleAdvancement(player, "minecraft:nether/summon_wither"));
        register("minecraft:nether/fast_travel",
                player -> handleAdvancement(player, "minecraft:nether/fast_travel"));
        register("minecraft:nether/return_to_sender",
                player -> handleAdvancement(player, "minecraft:nether/return_to_sender"));

        // End advancements
        register("minecraft:end/kill_dragon",
                player -> handleAdvancement(player, "minecraft:end/kill_dragon"));
        register("minecraft:end/dragon_egg",
                player -> handleAdvancement(player, "minecraft:end/dragon_egg"));
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
            MessageUtils.sendMessage(player, "<green>¡Felicidades! Has completado todos los logros de la categoría <#FC5F00><b>Intermedio</b></#FC5F00></green>");
            // Execute console command
            CommandUtils.ConsoleCommand("say " + player.getName() + " ha completado la categoría Intermedio");
        } else {
            // Show progress
            MessageUtils.sendMessage(player, "<yellow>Progreso <#FC5F00><b>Intermedio</b></#FC5F00>: " + playerAdvs.size() + "/" + TOTAL_ADVANCEMENTS + " logros completados</yellow>");
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