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

public class Tera{

	private static Main plugin = Main.getInstance();
	
	public static File configfile = new File(plugin.getDataFolder().getPath() + "/config.yml");
	public static FileConfiguration configuration = YamlConfiguration.loadConfiguration(configfile);
	
	public static int TeraWaitDays = configuration.getInt("TeraWaitDays");


	public static ItemStack os = new ItemStack(Material.WOOD_SWORD);{
		ItemMeta osmeta = os.getItemMeta();
		osmeta.addEnchant(Enchantment.DAMAGE_ALL, 5, true);
		os.setItemMeta(osmeta);
	}

	public static ItemStack oh = new ItemStack(Material.CHAINMAIL_HELMET);{
		ItemMeta ohmeta = oh.getItemMeta();
		ohmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		oh.setItemMeta(ohmeta);
	}

	public static ItemStack oc = new ItemStack(Material.CHAINMAIL_CHESTPLATE);{
		ItemMeta ocmeta = oc.getItemMeta();
		ocmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		oc.setItemMeta(ocmeta);
	}

	public static ItemStack ol = new ItemStack(Material.CHAINMAIL_LEGGINGS);{
		ItemMeta olmeta = ol.getItemMeta();
		olmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		ol.setItemMeta(olmeta);
	}

	public static ItemStack ob = new ItemStack(Material.CHAINMAIL_BOOTS);{
		ItemMeta obmeta = ob.getItemMeta();
		obmeta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 4, true);
		ob.setItemMeta(obmeta);
	}

	public static void giveTera(Player p){
		p.getInventory().addItem(os);
		p.getInventory().addItem(oh);
		p.getInventory().addItem(oc);
		p.getInventory().addItem(ol);
		p.getInventory().addItem(ob);
		
		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + p.getUniqueId().toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);
		
		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		
		config.set("KitTera" + ".Days", currentday);

		
		try {
			config.save(playerfile);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public static boolean canObtainTera(UUID uuid){


		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + uuid.toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);


		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		int playerday = config.getInt("KitTera" + ".Days");

		
		if(currentday - playerday < TeraWaitDays){
			
			return false;
			
		} else{
			
			return true;
		}
	}
	
	public static void waitMessageTera(Player p){
		
		File playerfile = new File(plugin.getDataFolder().getPath() + "/" + p.getUniqueId().toString() + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(playerfile);

		Calendar currentdate = Calendar.getInstance();
		
		int currentday = currentdate.get(Calendar.DAY_OF_YEAR);

		int playerday = config.getInt("KitTera" + ".Days");
		
		int days = TeraWaitDays - (currentday - playerday);
		
		p.sendMessage("§9Vous devez encore attendre §b" + days + "§9 jours");

		
	}

}
