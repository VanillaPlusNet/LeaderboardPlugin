package dev.goldenedit.leaderboardplugin.listeners;

import dev.goldenedit.leaderboardplugin.LeaderboardPlugin;
import dev.goldenedit.leaderboardplugin.PlayerKill;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class DeathListener implements Listener {
    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        int points = 0;
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            Player victim = e.getEntity();
            LeaderboardPlugin.getPlugin().getLogger().info("Killer: " + killer.getName() + " Victim: " + victim.getName());
            if (killer.getAddress().getAddress().equals(victim.getAddress().getAddress()))
                return;
            points += calcPoints(victim);
            if (LeaderboardPlugin.playerKills.containsKey(killer.getUniqueId())) {
                for (PlayerKill playerKill : LeaderboardPlugin.playerKills.get(killer.getUniqueId())) {
                    if ((playerKill.getVictim().equals(victim.getUniqueId()) || playerKill.getVictimIP().equals(victim.getAddress().getAddress())) &&
                            playerKill.getTime().longValue() > System.currentTimeMillis())
                        return;
                }
                ArrayList<PlayerKill> playerKills = (ArrayList<PlayerKill>)LeaderboardPlugin.playerKills.get(killer.getUniqueId());
                playerKills.add(new PlayerKill(killer.getUniqueId(), victim.getUniqueId(), Long.valueOf(System.currentTimeMillis() + (3600000 * 1000 / points)), victim.getAddress().getAddress()));
                LeaderboardPlugin.playerKills.put(killer.getUniqueId(), playerKills);
                increaseKillCount(killer, points);
            } else {
                ArrayList<PlayerKill> playerKills = new ArrayList<>();
                playerKills.add(new PlayerKill(killer.getUniqueId(), victim.getUniqueId(), Long.valueOf(System.currentTimeMillis() + (3600000 * 1000 / points)), victim.getAddress().getAddress()));
                LeaderboardPlugin.playerKills.put(killer.getUniqueId(), playerKills);
                increaseKillCount(killer, points);
            }
            Component pointText = Component.text(points).color(TextColor.color(175354));
            Component text = ((TextComponent)Component.text(" §r(").append(pointText)).append((Component)Component.text("§r)"));
            e.deathMessage(e.deathMessage().append(text));
        }
    }

    public void increaseKillCount(Player player, int points) {
        if (LeaderboardPlugin.killCount.containsKey(player.getUniqueId())) {
            LeaderboardPlugin.killCount.computeIfPresent(player.getUniqueId(), (k, v) -> Integer.valueOf(v.intValue() + points));
        } else {
            LeaderboardPlugin.killCount.put(player.getUniqueId(), Integer.valueOf(points));
        }
    }

    public boolean hasArmor(Player player) {
        ItemStack[] armor = player.getInventory().getArmorContents();
        for (ItemStack item : armor) {
            if (item == null)
                return false;
            if (!item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL) && !item.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS))
                return false;
            if (item.getType().name().toLowerCase().contains("diamond")) {
                if (item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    if (item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) < 4)
                        return false;
                } else if (item.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS) &&
                        item.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS) < 4) {
                    return false;
                }
            } else if (item.getType().name().toLowerCase().contains("netherite")) {
                if (item.containsEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL)) {
                    if (item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL) < 3)
                        return false;
                } else if (item.containsEnchantment(Enchantment.PROTECTION_EXPLOSIONS) &&
                        item.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS) < 3) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int calcPoints(Player player) {
        int points = 100;
        ArrayList<ItemStack> armor = new ArrayList<>();
        armor.add(player.getInventory().getHelmet());
        armor.add(player.getInventory().getChestplate());
        armor.add(player.getInventory().getLeggings());
        armor.add(player.getInventory().getBoots());
        for (ItemStack item : armor) {
            if (item == null)
                continue;
            if (item.getType().name().toLowerCase().contains("diamond")) {
                points += 30;
            } else if (item.getType().name().toLowerCase().contains("netherite")) {
                points += 50;
            }
            for (Enchantment enchantment : item.getEnchantments().keySet()) {
                if (enchantment.equals(Enchantment.BINDING_CURSE) || enchantment.equals(Enchantment.VANISHING_CURSE))
                    continue;
                if (enchantment.equals(Enchantment.PROTECTION_ENVIRONMENTAL) || enchantment.equals(Enchantment.PROTECTION_EXPLOSIONS)) {
                    points += item.getEnchantmentLevel(enchantment) * 30;
                    continue;
                }
                points += item.getEnchantmentLevel(enchantment) * 10;
            }
        }
        return points;
    }
}
