package dev.goldenedit.leaderboardplugin.listeners;

import dev.goldenedit.leaderboardplugin.LeaderboardPlugin;
import dev.goldenedit.leaderboardplugin.PlayerKill;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class DeathListener implements Listener {
    private HashMap<UUID, ArrayList<PlayerKill>> recentKills = new HashMap<>();

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            Player victim = e.getEntity();
            LeaderboardPlugin.getPlugin().getLogger().info("Killer: " + killer.getName() + " Victim: " + victim.getName());
            if (killer.getAddress().getAddress().equals(victim.getAddress().getAddress()))
                return;

            if (!canEarnPoints(killer, victim))
                return;

            int points = calcPoints(victim);
            ArrayList<PlayerKill> playerPoints = LeaderboardPlugin.playerPoints.getOrDefault(killer.getUniqueId(), new ArrayList<>());
            playerPoints.add(new PlayerKill(killer.getUniqueId(), victim.getUniqueId(), Long.valueOf(System.currentTimeMillis() + (3600000 * 1000 / points)), victim.getAddress().getAddress()));
            LeaderboardPlugin.playerPoints.put(killer.getUniqueId(), playerPoints);
            increaseKillCount(killer, points);

            Component pointText = Component.text(points).color(TextColor.color(175354));
            Component text = ((TextComponent)Component.text(" §r(").append(pointText)).append((Component)Component.text("§r)"));
            e.deathMessage(e.deathMessage().append(text));
        }
    }

    private boolean canEarnPoints(Player killer, Player victim) {
        long currentTime = System.currentTimeMillis();
        ArrayList<PlayerKill> kills = recentKills.computeIfAbsent(killer.getUniqueId(), k -> new ArrayList<>());
        kills.removeIf(k -> (currentTime - k.getTime()) > 21600000); // 6 hours in milliseconds

        long count = kills.stream().filter(k -> k.getVictim().equals(victim.getUniqueId())).count();
        if (count >= 3) {
            killer.sendMessage(Component.text("You cannot earn points for killing " + victim.getName() + " more than three times in 6 hours.", NamedTextColor.RED));
            return false;
        }

        kills.add(new PlayerKill(killer.getUniqueId(), victim.getUniqueId(), currentTime, victim.getAddress().getAddress()));
        return true;
    }

    public void increaseKillCount(Player player, int points) {
        LeaderboardPlugin.killCount.merge(player.getUniqueId(), points, Integer::sum);
    }

    public static int calcPoints(Player player) {
        int points = 100;
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack item : armor) {
            if (item != null && (item.getType().name().toLowerCase().contains("diamond") || item.getType().name().toLowerCase().contains("netherite"))) {
                points += (item.getType().name().toLowerCase().contains("diamond")) ? 30 : 50;
                for (Enchantment enchantment : item.getEnchantments().keySet()) {
                    int levelPoints = enchantment.equals(Enchantment.PROTECTION_ENVIRONMENTAL) || enchantment.equals(Enchantment.PROTECTION_EXPLOSIONS) ? 30 : 10;
                    points += item.getEnchantmentLevel(enchantment) * levelPoints;
                }
            }
        }
        return points;
    }
}
