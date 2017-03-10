package me.aaiach.engine.lobby;

import me.aaiach.engine.utils.GameStatus;
import me.aaiach.engine.utils.Main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class Events implements Listener
{

	private Main _plugin = Main.getInstance();

	@EventHandler
	public void onDrop(PlayerDropItemEvent e)
	{
		e.setCancelled(true);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if(_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlock(BlockDamageEvent e)
	{
		e.setCancelled(true);
	}

	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		e.setCancelled(true);
	}


	@EventHandler
	public void onPvp(EntityDamageByEntityEvent e)
	{
		e.setCancelled(true);
	}


	@EventHandler
	public void onDamage(EntityDamageEvent e)
	{
		if(_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void on(PlayerAchievementAwardedEvent e)
	{
		e.setCancelled(true);
	}

	@EventHandler
	public void onPickup(PlayerPickupItemEvent e)
	{
		e.setCancelled(true);
	}

	@EventHandler
	public void onMelt(BlockFadeEvent e){
		if(e.getBlock().getType().equals(Material.ICE)
				|| (e.getBlock().getType().equals(Material.PACKED_ICE))
				|| (e.getBlock().getType().equals(Material.ICE)))
		{
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onVoidDamage(EntityDamageEvent event)
	{
		if(_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if (event.getCause().equals(EntityDamageEvent.DamageCause.VOID))
			{
				Entity _ent = event.getEntity();
				if ((_ent instanceof Player))
				{
					World _world = Bukkit.getServer().getWorld("world");
					((Player)_ent).teleport(new Location(_world, 0, 64, 0));
					((Player)_ent).setFallDistance(0.0F);
					event.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onFoodchange(FoodLevelChangeEvent e)
	{
		if(e.getEntity() instanceof Player)
		{
			e.setCancelled(true);
			Player p = (Player) e.getEntity();
			if(!(p.getFoodLevel() == 20)){
				p.setFoodLevel(20);
			}
		}
	}
}
