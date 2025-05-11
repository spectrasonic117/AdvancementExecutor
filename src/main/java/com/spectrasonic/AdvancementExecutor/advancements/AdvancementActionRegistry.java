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

    }

    public static void register(String advancementKey, Consumer<Player> action) {
        advancementActions.put(advancementKey, action);
    }

    public static Optional<Consumer<Player>> getAction(String advancementKey) {
        return Optional.ofNullable(advancementActions.get(advancementKey));
    }
}
