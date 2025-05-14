package com.spectrasonic.AdvancementExecutor.managers;

import co.aikar.commands.PaperCommandManager;
import com.spectrasonic.AdvancementExecutor.Commands.AdvanvementExecutorCommand;
import com.spectrasonic.AdvancementExecutor.Main;
import lombok.RequiredArgsConstructor;

/**
 * Handles command registration and processing.
 */
@RequiredArgsConstructor
public class CommandManager {

    private final Main plugin;
    private PaperCommandManager commandManager;

    public void registerCommands() {
        // Initialize ACF
        this.commandManager = new PaperCommandManager(plugin);
        
        // Enable Brigadier integration for better command support
        commandManager.enableUnstableAPI("brigadier");
        commandManager.enableUnstableAPI("help");
        
        // Register command completions
        registerCompletions();
        
        // Register commands
        commandManager.registerCommand(new AdvanvementExecutorCommand());
    }
    
    private void registerCompletions() {
        commandManager.getCommandCompletions().registerCompletion("difficulty", c -> 
            java.util.Arrays.asList("facil", "medio", "intermedio", "dificil", "muydificil")
        );
    }
}