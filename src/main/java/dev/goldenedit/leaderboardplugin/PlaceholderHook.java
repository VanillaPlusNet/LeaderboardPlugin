package dev.goldenedit.leaderboardplugin;

import dev.goldenedit.leaderboardplugin.utils.LeaderboardUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.Plugin;

public class PlaceholderHook extends PlaceholderExpansion {
    private final Plugin plugin = LeaderboardPlugin.getPlugin();

    public String getAuthor() {
        return "goldenedit";
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
        // Can't use MiniMessage because PlaceholderAPI doesn't support it.
        if (params.equalsIgnoreCase("points")) {
            Integer points = LeaderboardUtils.getPoints(player.getUniqueId());
            if (points != null) {
                return ChatColor.translateAlternateColorCodes('&', "" + points);
            } else {
                return ChatColor.translateAlternateColorCodes('&', "&cData not available");
            }
        }
        if (params.equalsIgnoreCase("position1"))
            return ChatColor.translateAlternateColorCodes('&', "&61. &x&F&F&D&4&0&B" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(0)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(0)).getPoints());
        if (params.equalsIgnoreCase("position2"))
            return ChatColor.translateAlternateColorCodes('&', "&62. &x&B&C&C&6&C&C" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(1)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(1)).getPoints());
        if (params.equalsIgnoreCase("position3"))
            return ChatColor.translateAlternateColorCodes('&', "&63. &x&e&0&7&f&1&f" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(2)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(2)).getPoints());
        if (params.equalsIgnoreCase("position4"))
            return ChatColor.translateAlternateColorCodes('&', "&f4. &x&3&0&6&E&F&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(3)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(3)).getPoints());
        if (params.equalsIgnoreCase("position5"))
            return ChatColor.translateAlternateColorCodes('&', "&f5. &x&E&D&0&A&3&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(4)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(4)).getPoints());
        if (params.equalsIgnoreCase("position6"))
            return ChatColor.translateAlternateColorCodes('&', "&f6. &x&E&D&0&A&3&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(5)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(5)).getPoints());
        if (params.equalsIgnoreCase("position7"))
            return ChatColor.translateAlternateColorCodes('&', "&f7. &x&E&D&0&A&3&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(6)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(6)).getPoints());
        if (params.equalsIgnoreCase("position8"))
            return ChatColor.translateAlternateColorCodes('&', "&f8. &x&E&D&0&A&3&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(7)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(7)).getPoints());
        if (params.equalsIgnoreCase("position9"))
            return ChatColor.translateAlternateColorCodes('&', "&f9. &x&E&D&0&A&3&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(8)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(8)).getPoints());
        if (params.equalsIgnoreCase("position10"))
            return ChatColor.translateAlternateColorCodes('&', "&f10. &x&E&D&0&A&3&F" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(9)).getName()) + " &7- &f" + ((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(9)).getPoints());
        if (params.equalsIgnoreCase("position1_player"))
            return ChatColor.translateAlternateColorCodes('&', "&x&F&F&D&4&0&B" + equalizeLength(((LeaderboardPlayer)LeaderboardUtils.leaderboard.get(0)).getName()));
        if (params.equalsIgnoreCase("position1_points"))
            return ChatColor.translateAlternateColorCodes('&', "" + LeaderboardUtils.getPoints(player.getName()));
        if (params.equalsIgnoreCase("self")) {
            if (LeaderboardUtils.getPlace(player.getName()) != -1) {
                String place = String.valueOf(LeaderboardUtils.getPlace(player.getName()));
                return ChatColor.translateAlternateColorCodes('&', "&f" + place + ".&7 " + player.getName() + " &7- &f" + LeaderboardUtils.getPoints(player.getUniqueId()));
            }
            return ChatColor.translateAlternateColorCodes('&', "&fYou&7 - " + player.getName() + " &7- &f" + LeaderboardUtils.getPoints(player.getUniqueId()));
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
