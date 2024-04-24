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
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public final class LeaderboardPlugin extends JavaPlugin {

    public static Plugin plugin;

    public static HashMap<UUID, ArrayList<PlayerKill>> playerPoints = new HashMap<>();

    public static HashMap<UUID, Integer> killCount = new HashMap<>();

    private static FileWriter fileWriter;

    private static File file = new File("plugins/LeaderboardPlugin/data.json");

    public void onEnable() {
        saveDefaultConfig();
        plugin = (Plugin)this;
        if (file.exists())
            try {
                String json = FileUtils.readFileToString(file, "UTF-8");
                JsonObject jsonObject = (JsonObject)(new Gson()).fromJson(json, JsonObject.class);
                jsonObject.entrySet().forEach(entry -> {
                    UUID uuid = UUID.fromString((String)entry.getKey());
                    Integer points = Integer.valueOf(((JsonElement)entry.getValue()).getAsInt());
                    killCount.put(uuid, points);
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
                LeaderboardUtils.leaderboard.add(new LeaderboardPlayer("Player", 0, "Null"));
        }
        SchedulerUtils.runRepeating(LeaderboardUtils::sortLeaderboard, 1200L); // Sorts the in-game leaderboard every 60s
        SchedulerUtils.runRepeatingAsync(LeaderboardPlugin::saveData, 6000L); // Saves and posts data async every 300 seconds
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            (new PlaceholderHook()).register();
    }

    public void onDisable() {
        saveData();
    }

    private static void saveData() {
        ArrayList<LeaderboardPlayer> leaderboardSorted = new ArrayList<>();
        LeaderboardPlugin.killCount.forEach((uuid, points) -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if (offlinePlayer.hasPlayedBefore()) {
                leaderboardSorted.add(new LeaderboardPlayer(offlinePlayer.getName(), points, uuid.toString()));
            }
        });

        leaderboardSorted.sort((p1, p2) -> Integer.compare(p2.getPoints(), p1.getPoints()));

        JsonObject jsonObject = new JsonObject();
        leaderboardSorted.forEach(player -> {
            if (!player.getUuid().equals("Null")) {
                jsonObject.addProperty(player.getUuid(), player.getPoints());
            }
        });
        try {
            if (!plugin.getDataFolder().exists())
                plugin.getDataFolder().mkdirs();
            File file = new File(plugin.getDataFolder(), "data.json");
            if (!file.exists())
                file.createNewFile();
            fileWriter = new FileWriter(file);
            fileWriter.write(jsonObject.toString());
            fileWriter.close();
            plugin.getLogger().info("Saved leaderboard data to file.");
            postData(jsonObject.toString()); // Call the method to post data
        } catch (Exception e) {
            e.printStackTrace();
            plugin.getLogger().info("Failed to save leaderboard data to file.");
        }
    }


    private static void postData(String jsonData) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(plugin.getConfig().getString("cloudflare.url")))
                .header("Content-Type", "text/plain")
                .header("Authorization", plugin.getConfig().getString("cloudflare.auth_token"))
                .POST(HttpRequest.BodyPublishers.ofString(jsonData))
                .build();

        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenAccept(response -> {
                    plugin.getLogger().info("Response status code: " + response.statusCode());
                    plugin.getLogger().info("Response body: " + response.body());
                })
                .exceptionally(e -> {
                    plugin.getLogger().info("Failed to send data: " + e.getMessage());
                    return null;
                });
    }


    public static Plugin getPlugin() {
        return plugin;
    }
}
