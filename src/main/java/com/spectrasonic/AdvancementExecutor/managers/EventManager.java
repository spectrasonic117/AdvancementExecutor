package com.spectrasonic.AdvancementExecutor.managers;

import com.spectrasonic.AdvancementExecutor.Main;
import com.spectrasonic.AdvancementExecutor.listeners.AdvancementListener;
import com.spectrasonic.AdvancementExecutor.listeners.PlayerJoinListener;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.PluginManager;

/**
 * Manages event listener registration.
 */
@RequiredArgsConstructor
public class EventManager {

    private final Main plugin;

    public void registerEvents() {
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(new AdvancementListener(), plugin);
        pm.registerEvents(new PlayerJoinListener(), plugin);
    }
}