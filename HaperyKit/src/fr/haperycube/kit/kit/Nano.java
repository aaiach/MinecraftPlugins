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

public class Nano{

	private static Main plugin = Main.getInstance();
	
	public static File configfile = new File(plugin.getDataFolder().getPath() + "/config.yml");
	public static FileConfiguration configuration = YamlConfiguration.loadConfiguration(configfile);
	
	public static int NanoWaitDays = configuration.getInt("NanoWaitDays");



	public static ItemStack is = new ItemStack(Material.IRON_SWORD);{
		ItemMeta ismeta = is.getItemMeta();
		ismeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		is.setItemMeta(ismeta);
	}

	public static ItemStack ih = new ItemStack(Material.IRON_HELMET);{
		ItemMeta ihmeta = ih.getItemMeta();
		ihmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		ih.setItemMeta(ihmeta);
	}

	public static ItemStack ic = new ItemStack(Material.IRON_CHESTPLATE);{
		ItemMeta icmeta = ic.getItemMeta();
		icmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		ic.setItemMeta(icmeta);
	}

	public static ItemStack il = new ItemStack(Material.IRON_LEGGINGS);{
		ItemMeta ilmeta = il.getItemMeta();
		ilmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		il.setItemMeta(ilmeta);
	}

	public static ItemStack ib = new ItemStack(Material.IRON_BOOTS);{
		ItemMeta ibmeta = ib.getItemMeta();
		ibmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		ib.setItemMeta(ibmeta);
	}
	
	

	public static void giveNano(Player p){
		p.getInventory().addItem(is);
		p.getInventory().addItem(ih);
		p.getInventory().addItem(ic);
		p.getInventory().addItem(il);
		p.getInventory().addItem(ib);
		
		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + p.getUniqueId().toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
		
		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		
		config.set("KitNano" + ".Days", currentday);

		
		try {
			config.save(playerfile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static boolean canObtainNano(UUID uuid){


		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + uuid.toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);


		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		int playerday = config.getInt("KitNano" + ".Days");

		
		if(currentday - playerday < NanoWaitDays){
			
			return false;
			
		} else{
			
			return true;
		}
	}
	
	public static void waitMessageNano(Player p){
		
		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + p.getUniqueId().toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);

		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		int playerday = config.getInt("KitNano" + ".Days");
		
		int days = NanoWaitDays - (currentday - playerday);
		
		p.sendMessage("§9Vous devez encore attendre §b" + days + "§9 jours");

		
	}

}
