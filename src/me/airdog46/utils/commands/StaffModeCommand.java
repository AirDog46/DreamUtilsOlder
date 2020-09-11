package me.airdog46.utils.commands;

import java.util.HashMap;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import me.airdog46.utils.MainUtils;
import net.md_5.bungee.api.ChatColor;

public class StaffModeCommand implements CommandExecutor {
	FileConfiguration config = MainUtils.plugin.getConfig();
	HashMap<Player, Boolean> mode = MainUtils.staffmode;
	HashMap<Player, ItemStack[]> previousInv = MainUtils.previousInventory;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender.hasPermission("utils.staffmode")) || !(sender instanceof Player)) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("NoPermission")));
			return true;
		}
		if (mode.get(sender) == false || mode.get(sender) == null) {
			previousInv.put(((Player) sender), ((Player) sender).getInventory().getContents());
			((Player) sender).getInventory().clear();
			((Player) sender).setGameMode(GameMode.CREATIVE);
			ItemStack compass = new ItemStack(Material.COMPASS);
			ItemMeta cM = compass.getItemMeta();
			cM.setDisplayName(ChatColor.AQUA + "WorldEdit Compass");
			compass.setItemMeta(cM);
			ItemStack playerHead = new ItemStack(Material.SKULL_ITEM);
			ItemMeta pHM = playerHead.getItemMeta();
			pHM.setDisplayName(ChatColor.AQUA + "Online Staff");
			playerHead.setItemMeta(pHM);
			SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
			skullMeta.setOwner(sender.getName());
			skullMeta.setDisplayName(ChatColor.AQUA + "Online staff");
			playerHead.setItemMeta(skullMeta);
			ItemStack randomPlayer = new ItemStack(Material.INK_SACK, 1, (short) 12);
			ItemMeta rPM = randomPlayer.getItemMeta();
			rPM.setDisplayName(ChatColor.AQUA + "Random Player");
			randomPlayer.setItemMeta(rPM);
			ItemStack betterView = new ItemStack(Material.CARPET, 1,(short) 1);
			ItemMeta bVM = betterView.getItemMeta();
			bVM.setDisplayName(ChatColor.AQUA + "Better View");
			betterView.setItemMeta(bVM);
			ItemStack worldEditWand = new ItemStack(Material.CHEST, 1);
			ItemMeta wEWM = worldEditWand.getItemMeta();
			wEWM.setDisplayName(ChatColor.AQUA + "Invsee Player");
			worldEditWand.setItemMeta(wEWM);
			ItemStack vanish = new ItemStack(Material.INK_SACK, 1,(short) 10);
			ItemMeta vM = vanish.getItemMeta();
			vM.setDisplayName(ChatColor.AQUA + "Vanish");
			vanish.setItemMeta(vM);
			ItemStack freezePlayer = new ItemStack(Material.INK_SACK);
			ItemMeta fPM = freezePlayer.getItemMeta();
			fPM.setDisplayName(ChatColor.AQUA + "Freeze Player");
			freezePlayer.setItemMeta(fPM);
			((Player) sender).getInventory().setItem(0, compass);
			((Player) sender).getInventory().setItem(1, playerHead);
			((Player) sender).getInventory().setItem(2, randomPlayer);
			((Player) sender).getInventory().setItem(4, betterView);
			((Player) sender).getInventory().setItem(6, worldEditWand);
			((Player) sender).getInventory().setItem(7, vanish);
			((Player) sender).getInventory().setItem(8, freezePlayer);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("StaffModeEnableSelf")));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("utils.staff.notify") && player != ((Player) sender)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("StaffModeEnableOthers")).replaceAll("%player%", sender.getName()));
				}
			}
			mode.put((Player) sender, true);
			return true;
		} else if (mode.put((Player) sender, true)) {
			((Player) sender).getInventory().clear();
			((Player) sender).getInventory().setContents(previousInv.get((Player) sender));
			((Player) sender).setGameMode(GameMode.SURVIVAL);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("StaffModeDisableSelf")));
			for (Player player : Bukkit.getOnlinePlayers()) {
				if (player.hasPermission("utils.staff.notify") && player != ((Player) sender)) {
					player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("StaffModeDisableOthers")).replaceAll("%player%", sender.getName()));
				}
			}
			mode.put((Player) sender, false);
			return true;
		}
		return false;
	}

}
