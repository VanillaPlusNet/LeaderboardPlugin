package dev.goldenedit.leaderboardplugin.utils;

import dev.goldenedit.leaderboardplugin.LeaderboardPlayer;
import dev.goldenedit.leaderboardplugin.LeaderboardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class LeaderboardUtils {
    public static ArrayList<LeaderboardPlayer> leaderboard = new ArrayList<>();

    public static void sortLeaderboard() {
        SchedulerUtils.runAsync(() -> {
            ArrayList<LeaderboardPlayer> temp = new ArrayList<>();
            for (int i = 0; i < 1000; i++)
                temp.add(new LeaderboardPlayer("Player", 0));
            LeaderboardPlugin.killCount.forEach(());
            temp.sort(());
            leaderboard = temp;
        });
    }

    public static void showLeaderboard(CommandSender sender) {
        sender.sendMessage("Leaderboard:");
        for (int i = 0; i < 1000; i++)
            sender.sendMessage((i + 1) + ". " + ((LeaderboardPlayer)leaderboard.get(i)).getName() + " - " + ((LeaderboardPlayer)leaderboard.get(i)).getKills());
    }

    public static int getPlace(String name) {
        for (int i = 0; i < 1000; i++) {
            if (((LeaderboardPlayer)leaderboard.get(i)).getName().equals(name))
                return i;
        }
        return -1;
    }

    public static Integer getKills(UUID uuid) {
        if (LeaderboardPlugin.killCount.containsKey(uuid))
            return (Integer)LeaderboardPlugin.killCount.get(uuid);
        return Integer.valueOf(0);
    }

    public static Integer getKills(Player player) {
        if (LeaderboardPlugin.killCount.containsKey(player.getUniqueId()))
            return (Integer)LeaderboardPlugin.killCount.get(player.getUniqueId());
        return Integer.valueOf(0);
    }

    public static Integer getKills(String name) {
        if (Bukkit.getPlayer(name) != null) {
            Player player = Bukkit.getPlayer(name);
            if (LeaderboardPlugin.killCount.containsKey(player.getUniqueId()))
                return (Integer)LeaderboardPlugin.killCount.get(player.getUniqueId());
            return Integer.valueOf(0);
        }
        return Integer.valueOf(0);
    }
}
