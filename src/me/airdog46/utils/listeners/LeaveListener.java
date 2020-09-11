package me.airdog46.utils.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;

public class LeaveListener implements Listener {
	FileConfiguration config = MainUtils.plugin.getConfig();
	HashMap<Player, Boolean> frozenPlayer = MainUtils.frozen;
	HashMap<Player, Boolean> mode = MainUtils.staffmode;
	
	@EventHandler
	public void onLeaveEvent(PlayerQuitEvent h) {
		h.setQuitMessage("");
		if (frozenPlayer.get(h.getPlayer()) == true) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("utils.freeze")) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("LeftWhileFrozen").replaceAll("%player%", h.getPlayer().getName())));
				}
			}
		}
		if (mode.get(h.getPlayer())) {
			h.getPlayer().getInventory().clear();
		}
		frozenPlayer.remove(h.getPlayer());
		mode.remove(h.getPlayer());
	}
}
