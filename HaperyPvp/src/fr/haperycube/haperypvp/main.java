package fr.haperycube.haperypvp;

import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;



public class main extends JavaPlugin implements Listener{
	
	public Location loc;
	
	public Inventory duel;	
	public HashMap<String, Location> spawnpoint1 = new HashMap<String, Location>();
	public HashMap<String, Location> spawnpoint2 = new HashMap<String, Location>();
	public HashMap<String, Player> duelist = new HashMap<String, Player>();
	public HashMap <String, Boolean> isinfight = new HashMap<String, Boolean>();
	
	public void onEnable(){
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(this,this);
		duel = Bukkit.createInventory(null, 9, "§0§nDuel Request");
		duel.setItem(3, createItem(Material.REDSTONE_BLOCK, 1, (short) 0, "§4Refuser le Duel", null));
		duel.setItem(5, createItem(Material.EMERALD_BLOCK, 1, (short) 0, "§aAccepter Le duel", null));
		
		for(Player p : Bukkit.getOnlinePlayers()){
			if (!isinfight.containsKey(p.getName())) {
				isinfight.put(p.getName(), false);
		     }
		}
	}

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if (cmd.getName().equalsIgnoreCase("duel")){
			Player dsender = (Player) sender;
			if (args.length == 1){		
				Player target = Bukkit.getPlayerExact(args[0]);
				duelist.put("b", dsender);
				if(target != null){
					if(isinfight.get(target.getName()) == false){
						if(dsender.getName()!=target.getName()){
							target.openInventory(duel);
						}else{
							dsender.sendMessage("§cIl est impossible de se battre contre soi-même");
						}
					}else{
						dsender.sendMessage("§cCe joueur est déjà en duel");
					}
				}
			}else{
				dsender.sendMessage("§c/duel [Nom de joueur]");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("spawnpoint1")){
			Player p = (Player) sender;
			if(p.getLocation()!=null){
				spawnpoint1.put("d", p.getLocation());
				p.sendMessage("§aSpawn point 1 Défini");
			}
			else{
				p.sendMessage("§cPosition invalide");
			}
		}
		
		if (cmd.getName().equalsIgnoreCase("spawnpoint2")){
			Player p = (Player) sender;
			if(p.getLocation()!=null){
				spawnpoint2.put("e", p.getLocation());
				p.sendMessage("§aSpawn point 2 Défini");
			}
			else{
				p.sendMessage("§cPosition invalide");
			}
		}
		
		
		
		return false;
		
	}
	
	public ItemStack createItem(Material material, int amount, int shrt, String displayname, String lore) {
		ItemStack item = new ItemStack(material, amount, (short) shrt);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayname);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	
	@EventHandler
	public void duelinv(InventoryClickEvent event){
		Player dreceiver = (Player) event.getWhoClicked();
		Player dsender = duelist.get("b");
		Location ploc1 = spawnpoint1.get("d");
		Location ploc2 = spawnpoint2.get("e");

	
		if(event.getInventory().getName().equals(duel.getName())){
			event.setCancelled(true);
		
		if(event.getCurrentItem() == null){
			return;
		}
		
		if(event.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
			event.setCancelled(true);
			dsender.sendMessage("§cVotre demande de duel est refusée");
			dreceiver.sendMessage("§cVous avez refusé la demande de Duel");
			dreceiver.closeInventory();

			}
		if(event.getCurrentItem().getType() == Material.EMERALD_BLOCK){
			if(spawnpoint1.containsKey("d") && spawnpoint2.containsKey("e")){
				isinfight.put(dsender.getName(), true);
				isinfight.put(dreceiver.getName(), true);
				dreceiver.closeInventory();
				dsender.sendMessage("§aDemande de duel acceptée");
				dsender.getInventory().clear();
				dsender.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				dsender.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				dsender.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				dsender.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
				dsender.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
				dsender.getInventory().addItem(new ItemStack(Material.BOW));
				dsender.getInventory().addItem(new ItemStack(Material.ARROW, 32));
				dsender.getInventory().addItem(new ItemStack(Material.COOKED_BEEF));
				dsender.teleport(ploc1);
				dsender.setGameMode(GameMode.SURVIVAL);
				dsender.setHealth(20);
				dreceiver.getInventory().clear();
				dreceiver.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
				dreceiver.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
				dreceiver.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
				dreceiver.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
				dreceiver.getInventory().addItem(new ItemStack(Material.IRON_SWORD));
				dreceiver.getInventory().addItem(new ItemStack(Material.BOW));
				dreceiver.getInventory().addItem(new ItemStack(Material.ARROW, 32));
				dreceiver.getInventory().addItem(new ItemStack(Material.COOKED_BEEF));
				dreceiver.teleport(ploc2);		
				dreceiver.setGameMode(GameMode.SURVIVAL);
				dreceiver.setHealth(20);
				}else{
					dsender.sendMessage("§cAucun spawnpoint défini, faire /spawnpoint1 et /spawnpoint2");
					dreceiver.sendMessage("§cAucun spawnpoint défini, faire /spawnpoint1 et /spawnpoint2");
				}
			}
		}
	}
	
	@EventHandler
	public void infight(EntityDeathEvent e){
		if(e.getEntity() instanceof Player){
			Player dlooser = (Player) e.getEntity();
			Player dwinner = dlooser.getKiller();
			if(isinfight.get(dlooser.getName()) == true){
				if(isinfight.get(dwinner.getName()) == true){
					dlooser.sendMessage("§cTu as perdu le duel contre : §6" + dwinner.getName());
					dwinner.sendMessage("§aTu as gagné le duel contre : §6" + dlooser.getName());
					isinfight.put(dlooser.getName(), false);
					isinfight.put(dwinner.getName(), false);
				}	
			}
		}
	}
}
