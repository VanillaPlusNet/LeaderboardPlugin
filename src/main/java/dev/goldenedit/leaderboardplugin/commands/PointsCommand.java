package dev.goldenedit.leaderboardplugin.commands;

import dev.goldenedit.leaderboardplugin.utils.LeaderboardUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PointsCommand implements CommandExecutor {
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Component message;
        if (args.length == 0 && sender instanceof Player) {
            Player player = (Player) sender;
            message = miniMessage.deserialize("<gray>You have <color:#02acfa>" + LeaderboardUtils.getPoints(player.getName()) + " <gray>points!");
            player.sendMessage(message);
        } else if (args.length > 0) {
            message = miniMessage.deserialize("<color:#02acfa>" + args[0] + "<gray> has <color:#02acfa>" + LeaderboardUtils.getPoints(args[0]) + " <gray>points!");
            sender.sendMessage(message);
        } else {
            sender.sendMessage("This command can only be used by players.");
        }
        return true;
    }
}
