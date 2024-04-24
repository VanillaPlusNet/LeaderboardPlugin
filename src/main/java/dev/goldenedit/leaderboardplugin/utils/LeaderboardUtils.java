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
                temp.add(new LeaderboardPlayer("Player", 0, "Null"));
            }

            LeaderboardPlugin.killCount.forEach((uuid, points) -> {
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
                if (offlinePlayer.hasPlayedBefore()) {
                    temp.add(new LeaderboardPlayer(offlinePlayer.getName(), points, offlinePlayer.getUniqueId().toString()));
                }
            });

            temp.sort((p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));
            leaderboard = temp;
        });
    }

    public static void showLeaderboard(CommandSender sender) {
        sender.sendMessage("Leaderboard:");
        int size = Math.min(leaderboard.size(), 1000); // Prevents out-of-bounds exception
        for (int i = 0; i < size; i++) {
            LeaderboardPlayer player = leaderboard.get(i);
            sender.sendMessage((i + 1) + ". " + player.getName() + " - " + player.getPoints());
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

    public static Integer getPoints(UUID uuid) {
        if (LeaderboardPlugin.killCount.containsKey(uuid))
            return LeaderboardPlugin.killCount.get(uuid);
        return 0;
    }

    public static Integer getPoints(Player player) {
        return getPoints(player.getUniqueId());
    }

    public static Integer getPoints(String name) {
        Player player = Bukkit.getPlayerExact(name);
        if (player != null) {
            return getPoints(player.getUniqueId());
        }
        return 0;
    }
}
