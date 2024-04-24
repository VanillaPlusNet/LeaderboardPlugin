package dev.goldenedit.leaderboardplugin.utils;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class AnnouncementsUtils {
    /**
     * Broadcasts a message to all players on the server.
     * @param message The message to broadcast.
     */
    public static void broadcastMessage(String message) {
        String command = "bc " + message;
        CommandSender console = Bukkit.getConsoleSender();
        Bukkit.dispatchCommand(console, command);
    }

}
