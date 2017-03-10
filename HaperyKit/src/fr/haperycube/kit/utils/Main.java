package fr.haperycube.kit.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class Main extends JavaPlugin implements Listener{

	public static Main instance;
	
	File configfile = new File(getDataFolder().getPath() + "/config.yml");
	FileConfiguration configuration = YamlConfiguration.loadConfiguration(configfile);

	public void onEnable(){
		registerCommands();
		registerEvents();
		instance = this;
		
		if(!configfile.exists()){
			getInstance().getDataFolder().mkdirs();
			try {
				configfile.createNewFile();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			configuration.set("NanoWaitDays" , 7);
			configuration.set("MegaWaitDays" , 7);
			configuration.set("TeraWaitDays" , 7);


			
			try {
				configuration.save(configfile);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

	private void registerCommands() {	

		getCommand("kit").setExecutor(new Commandes());

	}	

	private void registerEvents() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new Inventories(), this);



	}

	public static Main getInstance() {
		return instance;
	}




	@EventHandler
	public void onJoin(PlayerJoinEvent e){

		Player p = e.getPlayer();
		UUID uuid = p.getUniqueId();

		FileConfiguration config = null;
		File file = new File(getDataFolder().getPath() + "/" + uuid.toString() + ".yml");

		if(!file.exists()){
			getInstance().getDataFolder().mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}


			config = YamlConfiguration.loadConfiguration(file);
			config.set("KitNano" + ".Days", 0);
			config.set("KitMega" + ".Days", 0);
			config.set("KitTera" + ".Days", 0);


			
			try {
				config.save(file);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}

	}

}
