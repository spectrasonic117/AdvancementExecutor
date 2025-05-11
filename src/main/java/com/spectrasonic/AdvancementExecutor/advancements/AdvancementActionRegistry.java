package com.spectrasonic.AdvancementExecutor.advancements;

// import com.spectrasonic.Utils.CommandUtils;
import com.spectrasonic.Utils.MessageUtils;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Registry for advancement actions.
 */
public class AdvancementActionRegistry {

    @Getter
    private static final Map<String, Consumer<Player>> advancementActions = new HashMap<>();

    static {
        // ---- Nivel Facil ----
        register("minecraft:story/mine_stone",
                player -> {
                    player.getInventory().addItem(new ItemStack(Material.STONE, 1));
                    MessageUtils.sendMessage(player, "<green>Has recibido 1 STONE</green>");
                });
        register("minecraft:story/upgrade_tools",
                player -> {
                    player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE, 1));
                    MessageUtils.sendMessage(player,
                            "<green>Has recibido 1 IRON_PICKAXE</green>");
                });
        register("minecraft:story/smelt_iron",
                player -> {
                    player.getInventory().addItem(new ItemStack(Material.IRON_INGOT, 3));
                    MessageUtils.sendMessage(player, "<green>Has recibido 3 IRON_INGOT</green>");
                });
        register("minecraft:husbandry/plant_seed",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:husbandry/fishy_business",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:husbandry/tactical_fishing",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:husbandry/breed_an_animal",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:husbandry/tame_an_animal",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:adventure/sleep_in_bed",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:adventure/ol_betsy",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:adventure/trim_with_any_armor_pattern",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:adventure/shoot_arrow",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:adventure/kill_a_mob",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:nether/obtain_blaze_rod",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:nether/find_fortress",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:nether/ride_strider",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
        register("minecraft:nether/find_bastion",
                player -> {
                    MessageUtils.sendMessage(player, "<green>Logro obtenido</green>");
                });
    }

    public static void register(String advancementKey, Consumer<Player> action) {
        advancementActions.put(advancementKey, action);
    }

    public static Optional<Consumer<Player>> getAction(String advancementKey) {
        return Optional.ofNullable(advancementActions.get(advancementKey));
    }
}
