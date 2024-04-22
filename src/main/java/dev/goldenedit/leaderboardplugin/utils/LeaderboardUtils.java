package dev.goldenedit.leaderboardplugin.utils;

import dev.goldenedit.leaderboardplugin.LeaderboardPlayer;
import dev.goldenedit.leaderboardplugin.LeaderboardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

import static dev.goldenedit.leaderboardplugin.LeaderboardPlugin.getPlugin;

public class LeaderboardUtils {
    public static ArrayList<LeaderboardPlayer> leaderboard = new ArrayList<>();

    public static void sortLeaderboard() {
        SchedulerUtils.runAsync(() -> {
            ArrayList<LeaderboardPlayer> temp = new ArrayList<>();
            for (int i = 0; i < 1000; i++) {
                temp.add(new LeaderboardPlayer("Player", 0));
            }

            LeaderboardPlugin.killCount.forEach((uuid, kills) -> {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if (offlinePlayer.hasPlayedBefore()) {
                    temp.add(new LeaderboardPlayer(offlinePlayer.getName(), kills));
                }
            });

            temp.sort((p1, p2) -> Integer.compare(p2.getKills(), p1.getKills()));
            leaderboard = temp;
        });
    }

    public static void showLeaderboard(CommandSender sender) {
        sender.sendMessage("Leaderboard:");
        int size = Math.min(leaderboard.size(), 1000); // Prevents out-of-bounds exception
        for (int i = 0; i < size; i++) {
            LeaderboardPlayer player = leaderboard.get(i);
            sender.sendMessage((i + 1) + ". " + player.getName() + " - " + player.getKills());
        }
        if (size == 0) {
            sender.sendMessage("No players on the leaderboard yet.");
        }
    }

    public static int getPlace(String name) {
        for (int i = 0; i < leaderboard.size(); i++) {
            if (leaderboard.get(i).getName().equals(name))
                return i;
        }
        return -1;
    }

    public static Integer getKills(UUID uuid) {
        if (LeaderboardPlugin.killCount.containsKey(uuid))
            return LeaderboardPlugin.killCount.get(uuid);
        return 0;
    }

    public static Integer getKills(Player player) {
        return getKills(player.getUniqueId());
    }

    public static Integer getKills(String name) {
        Player player = Bukkit.getPlayerExact(name);
        if (player != null) {
            return getKills(player.getUniqueId());
        }
        return 0;
    }
}
