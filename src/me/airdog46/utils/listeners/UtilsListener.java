package me.airdog46.utils.listeners;

import java.util.ArrayList;
import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;

public class UtilsListener implements Listener {
	HashMap<Player, Boolean> frozenPlayer = MainUtils.frozen;
	HashMap<Player, Boolean> staffModePlayer = MainUtils.staffmode;
	FileConfiguration config = MainUtils.plugin.getConfig();
	
	@EventHandler
	public void onJoinEvent(PlayerJoinEvent k) {
		k.setJoinMessage("");
		frozenPlayer.put(k.getPlayer(), false);
		staffModePlayer.put(k.getPlayer(), false);
	}
	
	@EventHandler
	public void onMoveEvent(PlayerMoveEvent e) {
			if (MainUtils.frozen.get(e.getPlayer()) == true) {
					Location frozenLocation = e.getPlayer().getLocation();
					frozenLocation.setY(e.getPlayer().getWorld().getHighestBlockYAt(frozenLocation));
					e.getPlayer().teleport(frozenLocation);
			}
		}

	@EventHandler
	public void onInteractEvent(PlayerInteractEvent j) {
		if (frozenPlayer.get(j.getPlayer()) == true) {
			j.setCancelled(true);
		}
		if (staffModePlayer.get(j.getPlayer())) {
			switch (j.getPlayer().getInventory().getHeldItemSlot()) {
			case 1:
				ArrayList<String> onlineStaff = new ArrayList<String>();
				for (Player player : Bukkit.getOnlinePlayers()) {
					if (player.hasPermission("utils.staff")) {
						onlineStaff.add(player.getName());
					}
				}
				j.getPlayer().sendMessage(ChatColor.YELLOW + "Online Staff: " + ChatColor.WHITE + onlineStaff);
				j.setCancelled(true);
				break;
			case 2:
		//		Random a = new Random();
		//		int random = a.nextInt(Bukkit.getOnlinePlayers().size());
		//		Object[] onlinePlayers = Bukkit.getOnlinePlayers().stream().toArray();
		//		j.getPlayer().teleport(Bukkit.getServer().getPlayer((Integer[]) onlinePlayers[random]));
				j.getPlayer().sendMessage(ChatColor.RED + "Under Construction");
				j.setCancelled(true);
				break;
			case 7:
				j.getPlayer().chat("/vanish");
				j.setCancelled(true);
				break;
			}
			j.setCancelled(true);
		}
	}
}
