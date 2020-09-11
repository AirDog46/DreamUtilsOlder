package me.airdog46.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.airdog46.utils.MainUtils;
import me.airdog46.utils.otherstuff.BukkitReflection;
import net.md_5.bungee.api.ChatColor;

public class SetMaxPlayersCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("utils.setmaxplayers"))) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (args.length < 1) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("SetPlayersUsage").replaceAll("%command%", label)));
			return true;
		}
		Server server = Bukkit.getServer();
		try {
		BukkitReflection.setMaxPlayers(server, Integer.parseInt(args[0]));
		} catch (NumberFormatException e) {
			sender.sendMessage(ChatColor.RED + "Please insert a valid integer.");
			return true;
		}
		String slotschange = ChatColor.translateAlternateColorCodes('&', config.getString("MaxPlayersSet")).replaceAll("%slots%", args[0]);
		sender.sendMessage(slotschange);
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.hasPermission("utils.admin.notify") && player != sender) {
				player.sendMessage(ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "[" + sender.getName() + ": " + slotschange + ChatColor.GRAY.toString() + ChatColor.ITALIC.toString() + "]");
			}
		}
		// TODO Auto-generated method stub
		return true;
	}
	
}
