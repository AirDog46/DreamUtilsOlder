package me.airdog46.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;

public class TeleportAllCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("utils.teleportall")) || !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.teleport((Player) sender);
		}
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("TeleportAllSelf")));
		for (Player pl : Bukkit.getOnlinePlayers()) {
			if (pl.hasPermission("utils.admin.notify") && pl != (Player) sender) {
				pl.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("TeleportAllOthers").replaceAll("%sender%", ((Player) sender).getName())));
			}
		}
		return true;
	}

}