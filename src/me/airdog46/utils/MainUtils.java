package me.airdog46.utils;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import me.airdog46.utils.commands.FlyCommand;
import me.airdog46.utils.commands.FreezeCommand;
import me.airdog46.utils.commands.InvseeCommand;
import me.airdog46.utils.commands.PingCommand;
import me.airdog46.utils.commands.SetMaxPlayersCommand;
import me.airdog46.utils.commands.StaffModeCommand;
import me.airdog46.utils.commands.SudoCommand;
import me.airdog46.utils.commands.TeleportAllCommand;
import me.airdog46.utils.commands.TeleportCommand;
import me.airdog46.utils.commands.TeleportHereCommand;
import me.airdog46.utils.listeners.*;

public class MainUtils extends JavaPlugin {
	
	public static MainUtils plugin;
	public static HashMap<Player, Boolean> frozen = new HashMap<>();
	public static HashMap<Player, Boolean> staffmode = new HashMap<>();
	public static HashMap<Player, ItemStack[]> previousInventory = new HashMap<>();
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		plugin = this;
		this.getServer().getPluginManager().registerEvents(new UtilsListener(), this);
		this.getServer().getPluginManager().registerEvents(new ChatListener(), this);
		this.getServer().getPluginManager().registerEvents(new LeaveListener(), this);
		this.getServer().getPluginManager().registerEvents(new CommandListener(), this);
		this.getServer().getPluginManager().registerEvents(new InventoryListener(), this);
		this.getServer().getPluginManager().registerEvents(new InteractAtEntityListener(), this);

		this.getCommand("freeze").setExecutor(new FreezeCommand());
		this.getCommand("staffmode").setExecutor(new StaffModeCommand());
		this.getCommand("sudo").setExecutor(new SudoCommand());
		this.getCommand("setmaxplayers").setExecutor(new SetMaxPlayersCommand());
		this.getCommand("ping").setExecutor(new PingCommand());
		this.getCommand("invsee").setExecutor(new InvseeCommand());
		this.getCommand("teleport").setExecutor(new TeleportCommand());
		this.getCommand("teleporthere").setExecutor(new TeleportHereCommand());
		this.getCommand("teleportall").setExecutor(new TeleportAllCommand());
		this.getCommand("fly").setExecutor(new FlyCommand());
	}
	

	@Override
	public void onDisable() {
		
	}
}
