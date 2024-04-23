package dev.goldenedit.leaderboardplugin;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.goldenedit.leaderboardplugin.commands.CalcPoints;
import dev.goldenedit.leaderboardplugin.commands.KillBoardCommand;
import dev.goldenedit.leaderboardplugin.commands.PointsCommand;
import dev.goldenedit.leaderboardplugin.listeners.DeathListener;
import dev.goldenedit.leaderboardplugin.utils.LeaderboardUtils;
import dev.goldenedit.leaderboardplugin.utils.SchedulerUtils;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class LeaderboardPlugin extends JavaPlugin {

    public static Plugin plugin;

    public static HashMap<UUID, ArrayList<PlayerKill>> playerKills = new HashMap<>();

    public static HashMap<UUID, Integer> killCount = new HashMap<>();

    private static FileWriter fileWriter;

    private static File file = new File("plugins/LeaderboardPlugin/data.json");

    public void onEnable() {
        plugin = (Plugin)this;
        if (file.exists())
            try {
                String json = FileUtils.readFileToString(file, "UTF-8");
                JsonObject jsonObject = (JsonObject)(new Gson()).fromJson(json, JsonObject.class);
                jsonObject.entrySet().forEach(entry -> {
                    UUID uuid = UUID.fromString((String)entry.getKey());
                    Integer kills = Integer.valueOf(((JsonElement)entry.getValue()).getAsInt());
                    killCount.put(uuid, kills);
                });
                getPlugin().getLogger().info("Loaded data from file");
            } catch (Exception e) {
                e.printStackTrace();
                getPlugin().getLogger().info("Failed to load data from file");
            }
        Bukkit.getPluginManager().registerEvents((Listener)new DeathListener(), (Plugin)this);
        getCommand("points").setExecutor((CommandExecutor)new PointsCommand());
        getCommand("killboard").setExecutor((CommandExecutor)new KillBoardCommand());
        getCommand("calcpoints").setExecutor((CommandExecutor)new CalcPoints());
        SchedulerUtils.setPlugin((Plugin)this);
        for (int i = 0; i < 10; i++) {
            if (LeaderboardUtils.leaderboard.size() < 10)
                LeaderboardUtils.leaderboard.add(new LeaderboardPlayer("Player", 0));
        }
        SchedulerUtils.runRepeating(LeaderboardUtils::sortLeaderboard, 1200L);
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, LeaderboardPlugin::saveData, 6000L);
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            (new PlaceholderHook()).register();
    }

    public void onDisable() {
        saveData();
    }

    private static void saveData() {
        JsonObject jsonObject = new JsonObject();
        for (UUID uuid : killCount.keySet())
            jsonObject.addProperty(uuid.toString(), killCount.get(uuid));
        try {
            if (!plugin.getDataFolder().exists())
                plugin.getDataFolder().mkdirs();
            File file = new File(plugin.getDataFolder(), "data.json");
            if (!file.exists())
                file.createNewFile();
            fileWriter = new FileWriter("plugins/LeaderboardPlugin/data.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
            plugin.getLogger().info("Saved leaderboard data to file.");
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info(e.toString());
            plugin.getLogger().info("Failed to save leaderboard data to file.");
        }
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}
