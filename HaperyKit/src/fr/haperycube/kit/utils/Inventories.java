package fr.haperycube.kit.utils;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.haperycube.kit.kit.Mega;
import fr.haperycube.kit.kit.Nano;
import fr.haperycube.kit.kit.Tera;


public class Inventories implements Listener{

	public static Inventory maininventory;

	public static ItemStack nano = new ItemStack(Material.IRON_SWORD, 1);{
		ItemMeta nanometa = nano.getItemMeta(); 
		ArrayList<String> nanolist = new ArrayList<String>();
		nanometa.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "Nano");
		nanolist.add("           ");
		nanolist.add("§6§lAvantages");
		nanolist.add("           ");
		nanolist.add(ChatColor.GREEN + "1) Kit Nano");
		nanolist.add(ChatColor.GREEN + "2) 5 Coffres Surprise par mois");
		nanolist.add(ChatColor.GREEN + "3) Préfixe" + ChatColor.GREEN + " [Nano]");
		nanolist.add(ChatColor.GREEN + "4) 5€ par mois");
		nanometa.setLore(nanolist);
		nano.setItemMeta(nanometa);
	}
	
	public static ItemStack mega = new ItemStack(Material.DIAMOND_SWORD, 1);{
		ItemMeta megameta = mega.getItemMeta(); 
		ArrayList<String> megalist = new ArrayList<String>();
		megameta.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "Mega");
		megalist.add("           ");
		megalist.add("§6§lAvantages");
		megalist.add("           ");
		megalist.add(ChatColor.GREEN + "1) Kit Mega");
		megalist.add(ChatColor.GREEN + "2) 10 Coffres Surprise par mois");
		megalist.add(ChatColor.GREEN + "3) Préfixe" + ChatColor.YELLOW + " [Mega]");
		megalist.add(ChatColor.GREEN + "4) 10€ Par mois");
		megameta.setLore(megalist);
		mega.setItemMeta(megameta);
	}
	
	public static ItemStack tera = new ItemStack(Material.WOOD_SWORD, 1);{
		ItemMeta terameta = tera.getItemMeta(); 
		ArrayList<String> teralist = new ArrayList<String>();
		terameta.setDisplayName(ChatColor.GOLD + "" + ChatColor.UNDERLINE + "" + ChatColor.BOLD + "Tera");
		teralist.add("           ");
		teralist.add("§6§lAvantages");
		teralist.add("           ");
		teralist.add(ChatColor.GREEN + "1) Kit Tera");
		teralist.add(ChatColor.GREEN + "2) 20 Coffres Surprise par mois");
		teralist.add(ChatColor.GREEN + "3) Préfixe" + ChatColor.GOLD + " [Tera]");
		teralist.add(ChatColor.GREEN + "4) 20€ par mois");
		terameta.setLore(teralist);
		tera.setItemMeta(terameta);
	}



	public static void openInventory(Player p){
		maininventory = Bukkit.createInventory(null, 9, ChatColor.BLACK + "HaperyCube Manager");
		maininventory.setItem(3, nano);
		maininventory.setItem(4, mega);
		maininventory.setItem(5, tera);


		p.openInventory(maininventory);

	}

	@EventHandler
	public void onClick(InventoryClickEvent e){

		Player p = (Player) e.getWhoClicked();
		UUID uuid = p.getUniqueId();


		if(e.getInventory().getName().equals(maininventory.getName())){
			e.setCancelled(true);			

			if(e.getCurrentItem() == null){
				return;
			}
		}		

		if(e.getCurrentItem().equals(nano)){
			if(p.hasPermission("hc.kit.nano")){
				if(Nano.canObtainNano(uuid) == true){
					Nano.giveNano(p);

		

				}else{
					Nano.waitMessageNano(p);
				}
				
			}else{
				p.sendMessage("§3Vous n'avez pas la permission");

			}
		}
		
		if(e.getCurrentItem().equals(mega)){
			if(p.hasPermission("hc.kit.mega")){
				if(Mega.canObtainMega(uuid) == true){
					Mega.giveMega(p);

					

				}else{
					Mega.waitMessageMega(p);
				}
				
			}else{
				p.sendMessage("§3Vous n'avez pas la permission");

			}
		}
		
		if(e.getCurrentItem().equals(tera)){
			if(p.hasPermission("hc.kit.tera")){
				if(Tera.canObtainTera(uuid) == true){
					Tera.giveTera(p);

					

				}else{
					Tera.waitMessageTera(p);
				}
				
			}else{
				p.sendMessage("§3Vous n'avez pas la permission");

			}
		}
		
	}
}
