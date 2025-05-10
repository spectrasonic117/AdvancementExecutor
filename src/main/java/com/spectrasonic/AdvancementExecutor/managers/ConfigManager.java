package com.spectrasonic.AdvancementExecutor.managers;

import com.spectrasonic.AdvancementExecutor.Main;
import lombok.RequiredArgsConstructor;
import org.bukkit.configuration.file.FileConfiguration;

/**
 * Centralizes all configuration operations.
 */
@RequiredArgsConstructor
public class ConfigManager {

    private final Main plugin;

    public void loadConfig() {
        plugin.saveDefaultConfig();
    }

    public FileConfiguration getConfig() {
        return plugin.getConfig();
    }
}
