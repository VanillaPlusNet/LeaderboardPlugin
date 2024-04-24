package dev.goldenedit.leaderboardplugin.commands;

import dev.goldenedit.leaderboardplugin.LeaderboardPlayer;
import dev.goldenedit.leaderboardplugin.LeaderboardPlugin;
import dev.goldenedit.leaderboardplugin.utils.AnnouncementsUtils;
import dev.goldenedit.leaderboardplugin.utils.LeaderboardUtils;
import dev.goldenedit.leaderboardplugin.utils.SchedulerUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class LeaderboardResetCommand implements CommandExecutor{
    // Get top 3 players
    LeaderboardPlayer position1 = LeaderboardUtils.leaderboard.get(0);
    LeaderboardPlayer position2 = LeaderboardUtils.leaderboard.get(0);
    LeaderboardPlayer position3 = LeaderboardUtils.leaderboard.get(0);
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Give top 3 players Legend rank for 5 days

        CommandSender console = Bukkit.getConsoleSender();
        Bukkit.dispatchCommand(console, "lp user " + position1.getUuid() + " parent addtemp legend 5d accumulate");
        Bukkit.dispatchCommand(console, "lp user " + position2.getUuid() + " parent addtemp legend 5d accumulate");
        Bukkit.dispatchCommand(console, "lp user " + position3.getUuid() + " parent addtemp legend 5d accumulate");
        String topbc = String.format("&#02acfaThe top 3 players in points this month &#FFD40B%s&#02acfa, &#BCC6CC%s &#02acfaand &#e07f1f%s &#02acfahave received &6Legend Rank &#02acfafor 5 days.", position1.getName(), position2.getName(), position3.getName());
        AnnouncementsUtils.broadcastMessage(topbc);


        // Reset the leaderboard

        LeaderboardPlugin.killCount.clear(); // Clear main hashmap

        // Save and post the data
        LeaderboardUtils.sortLeaderboard(); // Run sort to populate leaderboard with placeholder values.
        SchedulerUtils.runAsync(LeaderboardPlugin::saveData); // Saves empty data.json and posts empty data to Cloudflare KV async

        return true;
    }
}
