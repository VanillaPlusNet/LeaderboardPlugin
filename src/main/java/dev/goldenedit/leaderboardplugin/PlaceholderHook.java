package dev.goldenedit.leaderboardplugin;

import dev.goldenedit.leaderboardplugin.utils.LeaderboardUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class PlaceholderHook extends PlaceholderExpansion {
    private final Plugin plugin = LeaderboardPlugin.getPlugin();

    public String getAuthor() {
        return "someauthor";
    }

    public String getIdentifier() {
        return "leaderboardplugin";
    }

    public String getVersion() {
        return "1.0.0";
    }

    public boolean persist() {
        return true;
    }

    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("position1"))
            return ChatColor.translateAlternateColorCodes('&', "&f1. &x&F&F&D&4&0&B" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(0)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(0)).getKills());
        if (params.equalsIgnoreCase("position2"))
            return ChatColor.translateAlternateColorCodes('&', "&f2. &x&B&C&C&6&C&C" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(1)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(1)).getKills());
        if (params.equalsIgnoreCase("position3"))
            return ChatColor.translateAlternateColorCodes('&', "&f3. &x&e&0&7&f&1&f" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(2)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(2)).getKills());
        if (params.equalsIgnoreCase("position4"))
            return ChatColor.translateAlternateColorCodes('&', "&f4. &x&3&0&6&E&F&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(3)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(3)).getKills());
        if (params.equalsIgnoreCase("position5"))
            return ChatColor.translateAlternateColorCodes('&', "&f5. &x&E&D&0&A&3&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(4)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(4)).getKills());
        if (params.equalsIgnoreCase("self")) {
            if (LeaderboardUtils.getPlace(player.getName()) != -1) {
                String place = String.valueOf(LeaderboardUtils.getPlace(player.getName()));
                return ChatColor.translateAlternateColorCodes('&', "&f" + place + ".&7 " + player.getName() + " &7- &f" + LeaderboardUtils.getKills(player.getUniqueId()));
            }
            return ChatColor.translateAlternateColorCodes('&', "&fYou&7 - " + player.getName() + " &7- &f" + LeaderboardUtils.getKills(player.getUniqueId()));
        }
        return null;
    }

    private String equalizeLength(String s) {
        String out = s;
        String spaces = "";
        while (out.length() + spaces.length() < 12)
            spaces = spaces + " ";
        return out + spaces;
    }
}