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
        this.commandManager = new PaperCommandManager(plugin);
        registerCompletions();      
        commandManager.registerCommand(new AdvanvementExecutorCommand());
    }
    private void registerCompletions() {
        commandManager.getCommandCompletions().registerCompletion("difficulty", c -> 
            java.util.Arrays.asList("facil", "medio", "intermedio", "dificil", "muydificil", "all")
        );
    }
}