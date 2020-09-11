package me.airdog46.utils.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import me.airdog46.utils.MainUtils;

public class FreezeCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
	//	System.out.println(arg3.length);
		if (!(arg0.hasPermission("utils.freeze"))) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (arg3.length == 0) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FreezeCmdUsage").replaceAll("%command%", arg2)));
			return true;
		}
		Player frozenPlayer = Bukkit.getServer().getPlayer(arg3[0]);
		if (frozenPlayer == null) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FreezeCmdUsage").replaceAll("%command%", arg2)));
			return true;
		}
		if (MainUtils.frozen.get(frozenPlayer) == false || MainUtils.frozen.get(frozenPlayer) == null) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FreezeSuccess")).replaceAll("%player%", frozenPlayer.getName()));
			frozenPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FrozenMessage")));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("utils.notification.freeze")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FrozenAlert"))
							.replaceAll("%frozen%", frozenPlayer.getName())
							.replaceAll("%freezer%", arg0.getName()));
				}
			}
			MainUtils.frozen.put(frozenPlayer, true);
			return true;
		} else if (MainUtils.frozen.get(frozenPlayer) == true) {
			arg0.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("UnFreezeSuccess")).replaceAll("%player%", frozenPlayer.getName()));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("utils.notification.freeze")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("UnFrozenAlert"))
							.replaceAll("%frozen%", frozenPlayer.getName())
							.replaceAll("%freezer%", arg0.getName()));
				}
			}
			MainUtils.frozen.put(frozenPlayer, false);
			return true;
		}
		return false;
	}
}
