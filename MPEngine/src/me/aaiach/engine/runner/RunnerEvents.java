package me.aaiach.engine.runner;

import java.util.HashSet;
import java.util.Iterator;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.aaiach.engin.kitutils.Kit;
import me.aaiach.engine.utils.GameManager;
import me.aaiach.engine.utils.GameStatus;
import me.aaiach.engine.utils.GameType;
import me.aaiach.engine.utils.Main;
import me.aaiach.engine.utils.Spawnhandler;

public class RunnerEvents implements Listener
{

	private Main _plugin = Main.getInstance();

	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		if(_plugin.Game.getType().equals(GameType.RUNNER))
		{
			e.setCancelled(true);
		}
	}


	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInteract(PlayerInteractEvent e)
	{
		if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(_plugin.Game.getType().equals(GameType.RUNNER))
			{
				if(!_plugin.Game.getPlayersalive().contains(e.getPlayer().getName()))
				{
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent e){
		if(_plugin.Game.getStatus().equals(GameStatus.INGAME))
		{
			if(_plugin.Game.getType().equals(GameType.RUNNER))
			{
				if(!RunnerKits.Star.getPlayerwithpower().contains(e.getPlayer().getName())) //Checking if the player didn't activate the Star kit power
				{
					if(_plugin.Game.getPlayersalive().contains(e.getPlayer().getName()))
					{
						removeBlock(e.getPlayer());
					}
				}
			}
		}
	}

	@EventHandler
	public void onEntityChange(EntityChangeBlockEvent e)
	{
		if(_plugin.Game.getStatus().equals(GameStatus.INGAME))
		{
			if(_plugin.Game.getType().equals(GameType.RUNNER))
			{
				if(e.getEntity() instanceof FallingBlock)
				{
					FallingBlock _fb = (FallingBlock) e.getEntity();
					fallingSound(e.getEntity());
					_fb.remove();
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
			if(_plugin.Game.getType().equals(GameType.RUNNER))
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

						if(_plugin.Game.getPlayersalive().contains(p.getName())) //Checking that the player isn't a spectator yet
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
			if(_plugin.Game.getType().equals(GameType.RUNNER))
			{
				if(_plugin.Game.getPlayersalive().contains(e.getPlayer().getName())) //Checking if the player isn't a spectator
				{
					GameManager.playerDeath(e.getPlayer());
				}
			}
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(_plugin.Game.getType().equals(GameType.RUNNER))
			{
				World _world = Bukkit.getServer().getWorld(_plugin.Game.getMapname());
				e.getPlayer().teleport(new Location(_world, 0, 64, 0));
				Spawnhandler.setSpectator(e.getPlayer(), true);
			}
		}
	}

	@EventHandler
	public void onKit(PlayerInteractEvent e)
	{
		if(_plugin.Game.getStatus().equals(GameStatus.INGAME))
		{
			if(_plugin.Game.getType().equals(GameType.RUNNER))
			{
				if((e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) || (e.getAction().equals(Action.RIGHT_CLICK_AIR)))
				{
					if((e.getPlayer().getItemInHand().getType().equals(RunnerKits.Ghost.getItem().getType()))
							|| (e.getPlayer().getItemInHand().getType().equals(RunnerKits.Star.getItem().getType()))
							|| (e.getPlayer().getItemInHand().getType().equals(RunnerKits.Jumper.getItem().getType())))
					{
						RunnerKits.kitaction(e.getPlayer(), Kit.getKit(e.getPlayer(), _plugin.Game.getType()).getAction()); //Executing the action related to the kit
					}
				}
			}
		}
	}


	private void fallingSound(Entity ent)
	{
		FallingBlock _block = (FallingBlock) ent;
		_block.getWorld().playEffect(_block.getLocation(), Effect.STEP_SOUND, 159);			
	}


	/**
	 * 
	 * @param p The player that will get a block under his feet removed
	 */
	@SuppressWarnings("deprecation")
	private void removeBlock(final Player p)
	{
		HashSet<Block> _locations = blocksToremove(p.getLocation());

		if(!_locations.equals(null))
		{
			Iterator<Block> _ite = _locations.iterator();		

			while(_ite.hasNext())
			{
				final Block blockStanding = _ite.next();

				if(blockStanding.getType().equals(Material.AIR)) continue;

				blockStanding.setType(Material.STAINED_CLAY);
				blockStanding.setData((byte) 3);

				Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable()
				{
					public void run()
					{
						blockStanding.setType(Material.AIR);
						World w = Bukkit.getServer().getWorld(_plugin.Game.getMapname());
						w.spawnFallingBlock(blockStanding.getLocation(), Material.STAINED_CLAY, (byte) 3);
					}
				}, 7);
			}
		}
	}

	/**
	 * 
	 * @param loc The location of the central block to be removed
	 * @return A set of blocks that will be removed under or near the location
	 */
	public static HashSet<Block> blocksToremove(Location loc)
	{
		HashSet<Block> _locations = new HashSet<Block>();
		Location footBlock = loc.subtract(0, 1, 0);
		double rotation = loc.getYaw();
		if(rotation > 45 && rotation <= 135)
		{
			//WEST
			_locations.add(footBlock.getBlock());
			_locations.add(footBlock.add(0, 0, 0.4).getBlock());
			_locations.add(footBlock.add(0, 0, -0.4).getBlock());
			_locations.add(footBlock.add(0.4, 0, 0).getBlock());

		}
		else if(rotation > 135 && rotation <= 225)
		{
			//NORTH
			_locations.add(footBlock.getBlock());
			_locations.add(footBlock.add(0.4, 0, 0).getBlock());
			_locations.add(footBlock.add(-0.4, 0, 0).getBlock());
			_locations.add(footBlock.add(0, 0, 0.4).getBlock());

			return _locations;
		}
		else if(rotation > 225 && rotation <= 315)
		{
			//EAST
			_locations.add(footBlock.getBlock());
			_locations.add(footBlock.add(0, 0, 0.4).getBlock());
			_locations.add(footBlock.add(0, 0, -0.4).getBlock());
			_locations.add(footBlock.add(-0.4, 0, 0).getBlock());

			return _locations;
		}
		else
		{
			//(rotation > 315 && rotation <= 45)
			//SOUTH
			_locations.add(footBlock.getBlock());
			_locations.add(footBlock.add(0.4, 0, 0).getBlock());
			_locations.add(footBlock.add(-0.4, 0, 0).getBlock());
			_locations.add(footBlock.add(0, 0, -0.4).getBlock());

			return _locations;
		}

		return _locations;

	}
}