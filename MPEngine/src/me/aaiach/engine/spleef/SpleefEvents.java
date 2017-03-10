package me.aaiach.engine.spleef;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import me.aaiach.engin.kitutils.Kit;
import me.aaiach.engine.utils.GameManager;
import me.aaiach.engine.utils.GameStatus;
import me.aaiach.engine.utils.GameType;
import me.aaiach.engine.utils.Main;
import me.aaiach.engine.utils.Spawnhandler;

public class SpleefEvents implements Listener
{

	private Main _plugin = Main.getInstance();

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e)
	{
		if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(_plugin.Game.getType().equals(GameType.SPLEEF))
			{
				if(!_plugin.Game.getPlayersalive().contains(e.getPlayer().getName()))
				{
					e.setCancelled(true);
				}
			}
		}
	}



	@EventHandler
	public void onVoidDamage(EntityDamageEvent e)
	{
		if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(_plugin.Game.getType().equals(GameType.SPLEEF))
			{
				Entity _ent = e.getEntity();
				if ((_ent instanceof Player))
				{
					Player p = (Player) e.getEntity();

					if (e.getCause().equals(EntityDamageEvent.DamageCause.VOID) || e.getDamage() >= p.getHealth())
					{
						e.setCancelled(true);

						World _world = Bukkit.getServer().getWorld(_plugin.Game.getMapname());
						p.teleport(new Location(_world, 0, 64, 0));
						Spawnhandler.setSpectator(p, true);

						if(_plugin.Game.getPlayersalive().contains(p.getName()))
						{
							GameManager.playerDeath(p);
						}
						p.setFallDistance(0.0F);
					}
				}
			}
		}
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e)
	{
		if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(_plugin.Game.getType().equals(GameType.SPLEEF))
			{
				if(_plugin.Game.getPlayersalive().contains(e.getPlayer().getName()))
				{
					GameManager.playerDeath(e.getPlayer());
				}
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(_plugin.Game.getType().equals(GameType.SPLEEF))
			{
				World _world = Bukkit.getServer().getWorld(_plugin.Game.getMapname());
				e.getPlayer().teleport(new Location(_world, 0, 64, 0));
				Spawnhandler.setSpectator(e.getPlayer(), true);
			}
		}
	}


	@EventHandler
	public void onBreak(final BlockDamageEvent e)
	{
		if(_plugin.Game.getStatus().equals(GameStatus.INGAME))
		{
			if(_plugin.Game.getType().equals(GameType.SPLEEF))
			{
				final Block _block = e.getBlock();
				final Player p = e.getPlayer();
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable()
				{
					@SuppressWarnings("deprecation")
					public void run()
					{
						_block.setType(Material.AIR);
						_block.getWorld().playEffect(e.getBlock().getLocation(), Effect.STEP_SOUND, _block.getTypeId());			

						int amount;

						if(!Kit.getKit(e.getPlayer(), _plugin.Game.getType()).equals(null)) //Error here
						{
							if(Kit.getKit(e.getPlayer(), _plugin.Game.getType()).equals(SpleefKits.Eggtastic))
							{
								if(p.getInventory().getItem(0) != null && p.getInventory().getItem(0).getType().equals(Material.EGG))
									amount = p.getInventory().getItem(0).getAmount();
								else
									amount = 0;
								ItemStack eggs = new ItemStack(Material.EGG, amount + 1);
								if(amount < 16)
								{
									e.getPlayer().getInventory().setItem(0, eggs);
									e.getPlayer().updateInventory();
								}
							}
						}

						if(e.getPlayer().getItemInHand().getType().equals(SpleefKits.Blast.getItem().getType()))
						{
							if(Kit.getKit(e.getPlayer(), _plugin.Game.getType()).equals(SpleefKits.Blast)) 
							{
								SpleefKits.blast(e.getPlayer(), e.getBlock());
							}

						}

					}
				}, 4); //Delaying the block breaking
			}
		}
	}

	@EventHandler
	public void onProjectileHit(PlayerEggThrowEvent e)
	{
		if(_plugin.Game.getStatus().equals(GameStatus.INGAME))
		{
			if(_plugin.Game.getType().equals(GameType.SPLEEF))
			{
				Egg _egg = e.getEgg();
				for(int i = 0; i < 10; i++)
				{
					Block _target = _egg.getLocation().add(_egg.getVelocity().normalize().multiply(i / 5f)).getBlock();

					if(!_target.isEmpty())
					{
						_target.setType(Material.AIR);
						break;
					}
				}
				e.setHatching(false);
				_egg.getWorld().playEffect(_egg.getLocation(), Effect.STEP_SOUND, _egg.getLocation().getBlock().getType());			
			} 
		}
	}


	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if((!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
				&&(_plugin.Game.getType().equals(GameType.SPLEEF))
				&&(!Kit.getKit(e.getPlayer(), GameType.SPLEEF).equals(SpleefKits.Builder)))
		{
			e.setCancelled(true);
		}
	}
}




