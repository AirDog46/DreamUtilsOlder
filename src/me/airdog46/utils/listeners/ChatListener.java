package me.airdog46.utils.listeners;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;

public class ChatListener implements Listener {
	HashMap<Player, Boolean> frozenPlayer = MainUtils.frozen;
	FileConfiguration config = MainUtils.plugin.getConfig();
	
	@EventHandler
	public void onChatEvent(AsyncPlayerChatEvent f) {
	//	System.out.println(f.getFormat() + "    " + f.getMessage());
		if (frozenPlayer.get(f.getPlayer()) == true) {
			for (Player player : Bukkit.getOnlinePlayers()) {
				player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("FrozenPrefix")) + " " + f.getPlayer().getDisplayName() + ": " + f.getMessage());
			}
			f.setCancelled(true);
		}
	}
}
