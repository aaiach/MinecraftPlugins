package fr.haperycube.kit.kit;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.haperycube.kit.utils.Main;

public class Mega{

	private static Main plugin = Main.getInstance();
	
	public static File configfile = new File(plugin.getDataFolder().getPath() + "/config.yml");
	public static FileConfiguration configuration = YamlConfiguration.loadConfiguration(configfile);
	
	public static int MegaWaitDays = configuration.getInt("MegaWaitDays");


	public static ItemStack ds = new ItemStack(Material.DIAMOND_SWORD);{
		ItemMeta dsmeta = ds.getItemMeta();
		dsmeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		ds.setItemMeta(dsmeta);
	}

	public static ItemStack dh = new ItemStack(Material.DIAMOND_HELMET);{
		ItemMeta dhmeta = dh.getItemMeta();
		dhmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		dh.setItemMeta(dhmeta);
	}

	public static ItemStack dc = new ItemStack(Material.DIAMOND_CHESTPLATE);{
		ItemMeta dcmeta = dc.getItemMeta();
		dcmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		dc.setItemMeta(dcmeta);
	}

	public static ItemStack dl = new ItemStack(Material.DIAMOND_LEGGINGS);{
		ItemMeta dlmeta = dl.getItemMeta();
		dlmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		dl.setItemMeta(dlmeta);
	}


	public static ItemStack db = new ItemStack(Material.DIAMOND_BOOTS);{
		ItemMeta dbmeta = db.getItemMeta();
		dbmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		db.setItemMeta(dbmeta);
	}


	public static void giveMega(Player p){
		p.getInventory().addItem(ds);
		p.getInventory().addItem(dh);
		p.getInventory().addItem(dc);
		p.getInventory().addItem(dl);
		p.getInventory().addItem(db);
		
		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + p.getUniqueId().toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
		
		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		
		config.set("KitMega" + ".Days", currentday);

		
		try {
			config.save(playerfile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static boolean canObtainMega(UUID uuid){


		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + uuid.toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);


		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		int playerday = config.getInt("KitMega" + ".Days");

		
		if(currentday - playerday < MegaWaitDays){
			
			return false;
			
		} else{
			
			return true;
		}
	}
	
public static void waitMessageMega(Player p){
		
		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + p.getUniqueId().toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);

		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		int playerday = config.getInt("KitMega" + ".Days");
		
		int days = MegaWaitDays - (currentday - playerday);
		
		p.sendMessage("§9Vous devez encore attendre §b" + days + "§9 jours");

		
	}
}
