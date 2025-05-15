package com.spectrasonic.AdvancementExecutor;

import com.spectrasonic.AdvancementExecutor.managers.CommandManager;
import com.spectrasonic.AdvancementExecutor.managers.ConfigManager;
import com.spectrasonic.AdvancementExecutor.managers.EventManager;
import com.spectrasonic.Utils.MessageUtils;
import com.spectrasonic.Utils.CommandUtils;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private static Main instance;
    private CommandManager commandManager;
    private EventManager eventManager;
    private ConfigManager configManager;

    @Override
    public void onEnable() {
        instance = this;
        CommandUtils.setPlugin(this);
        this.configManager = new ConfigManager(this);
        this.commandManager = new CommandManager(this);
        this.eventManager = new EventManager(this);

        configManager.loadConfig();
        commandManager.registerCommands();
        eventManager.registerEvents();

        MessageUtils.sendStartupMessage(this);
    }

    @Override
    public void onDisable() {
        MessageUtils.sendShutdownMessage(this);
    }

    public static Main getInstance() {
        return instance;
    }
}
