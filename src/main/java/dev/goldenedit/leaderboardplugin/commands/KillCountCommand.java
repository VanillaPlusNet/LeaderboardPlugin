package dev.goldenedit.leaderboardplugin.commands;

import dev.goldenedit.leaderboardplugin.utils.LeaderboardUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KillCountCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("You have " + LeaderboardUtils.getKills(sender.getName()) + " kills!");
        } else {
            sender.sendMessage(args[0] + " has " + LeaderboardUtils.getKills(args[0]) + " kills!");
        }
        return true;
    }
}