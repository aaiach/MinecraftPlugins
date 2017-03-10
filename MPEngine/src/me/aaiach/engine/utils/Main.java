package me.aaiach.engine.utils;

import me.aaiach.engin.kitutils.KitManager;
import me.aaiach.engine.lobby.Events;
import me.aaiach.engine.runner.RunnerEvents;
import me.aaiach.engine.spleef.SpleefEvents;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin
{
	
	public static Main instance;

	public Arena Game = new Arena();
	
	public void onEnable()
	{
		instance = this;
		registerEvents();
		registerCommands();
		GameType gt = GameManager.gameGenerator();
		Game.setStatus(GameStatus.INLOBBY);
		Game.setType(gt);
		Game.setSlotstostart(2);
		Game.setMapname(Game.getType().toString() + ".1" + System.currentTimeMillis());
		Game.setPlayersalive(null);
	}
	
	public void onDisable()
	{
		if((Game.getStatus().equals(GameStatus.INGAME))||((Game.getStatus().equals(GameStatus.STARTED))))
		{
			GameManager.endGame();
		}
	}
	
	private void registerEvents()
	{
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new Spawnhandler(), this);
		pm.registerEvents(new Events(), this);
		pm.registerEvents(new RunnerEvents(), this);
		pm.registerEvents(new KitManager(), this);
		pm.registerEvents(new SpleefEvents(), this);
		pm.registerEvents(new ChatFormat(), this);
	}
	
	private void registerCommands()
	{
		getCommand("kit").setExecutor(new KitManager());
	}
	
	public static Main getInstance()
	{
		return instance;
	}
	
}
