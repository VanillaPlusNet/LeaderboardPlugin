package dev.goldenedit.leaderboardplugin.commands;

import dev.goldenedit.leaderboardplugin.utils.LeaderboardUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KillBoardCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        LeaderboardUtils.showLeaderboard(sender);
        return true;
    }
}
