package fr.haperycube.adc;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin implements Listener{
				
	public static ArrayList<Player> infight = new ArrayList<>();

	public void onEnable(){
		
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
    
  }	

		@EventHandler
		public void onDamage(EntityDamageByEntityEvent e){
				
			if ((e.getDamager() instanceof Player)){
				Player damager = (Player)e.getDamager();
				
				if ((e.getEntity() instanceof Player)){			
					Player victim = (Player)e.getEntity();
					
					if (!infight.contains(damager) && !damager.hasPermission("haperycombat.bypass")){
				          infight.add(damager);
				          damager.sendMessage("§c[HaperyCombat] §6Vous êtes en combat, ne vous deconectez pas");
				          delay(damager);

				          if (!infight.contains(victim) && !victim.hasPermission("haperycombat.bypass")) {
				            infight.add(victim);
				            victim.sendMessage("§c[HaperyCombat] §6Vous êtes en combat, ne vous deconectez pas");
				            delay(victim);
				          }
						}
					}
				}
			}
		
		@EventHandler
		public void onQuit(PlayerQuitEvent e) {
			Player p = e.getPlayer();
			if (infight.contains(p) && !p.hasPermission("haperycombat.bypass")) {
				
			
				for (ItemStack item : p.getInventory().getContents()){
					if ((item != null) && (!item.getType().equals(Material.AIR))){
						p.getWorld().dropItemNaturally(p.getLocation(), item);
					}
				}
				
				
				for (ItemStack item : p.getInventory().getArmorContents()){
					if ((item != null) && (!item.getType().equals(Material.AIR))){
						p.getWorld().dropItemNaturally(p.getLocation(), item);
        }
      }
				p.teleport(p.getWorld().getSpawnLocation());
				p.getInventory().clear();


    }
  }

		
		
		public void delay(final Player p) {
			getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
				public void run() {
					infight.remove(p);
					p.sendMessage("§c[HaperyCombat] §6Vous n'êtes plus en combat");
				}
			}, 260L);
		}
}