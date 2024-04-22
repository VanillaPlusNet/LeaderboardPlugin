package dev.goldenedit.leaderboardplugin.commands;

import dev.goldenedit.leaderboardplugin.listeners.DeathListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CalcPoints implements CommandExecutor {
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player))
            return true;
        Player player = (Player)sender;
        player.sendMessage("Points: " + DeathListener.calcPoints(player));
        return true;
    }
}
