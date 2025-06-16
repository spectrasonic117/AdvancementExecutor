package com.spectrasonic.AdvancementExecutor.Commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.spectrasonic.AdvancementExecutor.advancements.*;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;
import java.util.UUID;

@CommandAlias("advancementexecutor|ae")
@CommandPermission("advancementexecutor.admin")
@Description("Main command for managing player advancements")
public class AdvanvementExecutorCommand extends BaseCommand {

    @Subcommand("reset")
    @CommandCompletion("@players|all @difficulty")
    @CommandPermission("advancementexecutor.admin")
    @Description("Reset a player's advancement counter for a specific difficulty or all difficulties")
    public void onReset(
            @Name("player") String target,
            @Name("dificultad") @Values("facil|medio|intermedio|dificil|muydificil|all") String difficulty
    ) {
        if (target.equalsIgnoreCase("all")) {
            // Reset all difficulty categories for all online players
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerId = player.getUniqueId();
                Facil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                Medio_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                Intermedio_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                Dificil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                MuyDificil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
            }
            getCurrentCommandIssuer().sendMessage("§aSe han reiniciado todos los contadores de logros para todos los jugadores.");
            return;
        }

        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            getCurrentCommandIssuer().sendMessage("§cPlayer not found.");
            return;
        }

        UUID playerId = player.getUniqueId();

        if (difficulty.equalsIgnoreCase("all")) {
            // Reset all difficulty categories for a specific player
            Facil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
            Medio_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
            Intermedio_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
            Dificil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
            MuyDificil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);

            getCurrentCommandIssuer().sendMessage(String.format("§aSe han reiniciado todos los contadores de logros de %s", player.getName()));
            return;
        }

        switch (difficulty.toLowerCase()) {
            case "facil":
                Facil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                break;
            case "medio":
                Medio_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                break;
            case "intermedio":
                Intermedio_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                break;
            case "dificil":
                Dificil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                break;
            case "muydificil":
                MuyDificil_AdvancementActionRegistry.resetPlayerAdvancements(playerId);
                break;
            default:
                getCurrentCommandIssuer().sendMessage("Invalid difficulty level.");
                return;
        }

        String message = String.format("§aSe ha reiniciado el contador de logros de %s para la dificultad: %s",
                player.getName(),
                getFormattedDifficulty(difficulty));

        getCurrentCommandIssuer().sendMessage(message);
    }

    @Subcommand("add")
    @CommandCompletion("@players|all @difficulty @integer")
    @CommandPermission("advancementexecutor.admin")
    @Description("Add points to a player's advancement counter for a specific difficulty")
    public void onAdd(
            @Name("player") String target,
            @Name("dificultad") @Values("facil|medio|intermedio|dificil|muydificil") String difficulty,
            @Name("points") int points
    ) {
        if (target.equalsIgnoreCase("all")) {
            // Add points for all online players
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerId = player.getUniqueId();
                switch (difficulty.toLowerCase()) {
                    case "facil":
                        Facil_AdvancementActionRegistry.addPoints(playerId, points);
                        break;
                    case "medio":
                        Medio_AdvancementActionRegistry.addPoints(playerId, points);
                        break;
                    case "intermedio":
                        Intermedio_AdvancementActionRegistry.addPoints(playerId, points);
                        break;
                    case "dificil":
                        Dificil_AdvancementActionRegistry.addPoints(playerId, points);
                        break;
                    case "muydificil":
                        MuyDificil_AdvancementActionRegistry.addPoints(playerId, points);
                        break;
                }
            }
            getCurrentCommandIssuer().sendMessage(String.format("§aSe han añadido %d puntos a todos los jugadores para la dificultad: %s",
                    points,
                    getFormattedDifficulty(difficulty)));
            return;
        }

        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            getCurrentCommandIssuer().sendMessage("§cPlayer not found.");
            return;
        }

        UUID playerId = player.getUniqueId();

        switch (difficulty.toLowerCase()) {
            case "facil":
                Facil_AdvancementActionRegistry.addPoints(playerId, points);
                break;
            case "medio":
                Medio_AdvancementActionRegistry.addPoints(playerId, points);
                break;
            case "intermedio":
                Intermedio_AdvancementActionRegistry.addPoints(playerId, points);
                break;
            case "dificil":
                Dificil_AdvancementActionRegistry.addPoints(playerId, points);
                break;
            case "muydificil":
                MuyDificil_AdvancementActionRegistry.addPoints(playerId, points);
                break;
            default:
                getCurrentCommandIssuer().sendMessage("Invalid difficulty level.");
                return;
        }

        String message = String.format("§aSe han añadido %d puntos al contador de logros de %s para la dificultad: %s",
                points,
                player.getName(),
                getFormattedDifficulty(difficulty));

        getCurrentCommandIssuer().sendMessage(message);
    }

    @Subcommand("subtract")
    @CommandCompletion("@players|all @difficulty @integer")
    @CommandPermission("advancementexecutor.admin")
    @Description("Subtract points from a player's advancement counter for a specific difficulty")
    public void onSubtract(
            @Name("player") String target,
            @Name("dificultad") @Values("facil|medio|intermedio|dificil|muydificil") String difficulty,
            @Name("points") int points
    ) {
        if (target.equalsIgnoreCase("all")) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                UUID playerId = player.getUniqueId();
                switch (difficulty.toLowerCase()) {
                    case "facil":
                        Facil_AdvancementActionRegistry.subtractPoints(playerId, points);
                        break;
                    case "medio":
                        Medio_AdvancementActionRegistry.subtractPoints(playerId, points);
                        break;
                    case "intermedio":
                        Intermedio_AdvancementActionRegistry.subtractPoints(playerId, points);
                        break;
                    case "dificil":
                        Dificil_AdvancementActionRegistry.subtractPoints(playerId, points);
                        break;
                    case "muydificil":
                        MuyDificil_AdvancementActionRegistry.subtractPoints(playerId, points);
                        break;
                }
            }
            getCurrentCommandIssuer().sendMessage(String.format("§aSe han restado %d puntos a todos los jugadores para la dificultad: %s",
                    points,
                    getFormattedDifficulty(difficulty)));
            return;
        }

        Player player = Bukkit.getPlayer(target);
        if (player == null) {
            getCurrentCommandIssuer().sendMessage("§cPlayer not found.");
            return;
        }

        UUID playerId = player.getUniqueId();
        switch (difficulty.toLowerCase()) {
            case "facil":
                Facil_AdvancementActionRegistry.subtractPoints(playerId, points);
                break;
            case "medio":
                Medio_AdvancementActionRegistry.subtractPoints(playerId, points);
                break;
            case "intermedio":
                Intermedio_AdvancementActionRegistry.subtractPoints(playerId, points);
                break;
            case "dificil":
                Dificil_AdvancementActionRegistry.subtractPoints(playerId, points);
                break;
            case "muydificil":
                MuyDificil_AdvancementActionRegistry.subtractPoints(playerId, points);
                break;
            default:
                getCurrentCommandIssuer().sendMessage("Invalid difficulty level.");
                return;
        }

        String message = String.format("§aSe han restado %d puntos al contador de logros de %s para la dificultad: %s",
                points,
                player.getName(),
                getFormattedDifficulty(difficulty));

        getCurrentCommandIssuer().sendMessage(message);
    }

    private String getFormattedDifficulty(String difficulty) {
        switch (difficulty.toLowerCase()) {
            case "facil":
                return "§aFácil";
            case "medio":
                return "§eMedio";
            case "intermedio":
                return "§6Intermedio";
            case "dificil":
                return "§cDifícil";
            case "muydificil":
                return "§4Muy Difícil";
            default:
                return difficulty;
        }
    }
}
