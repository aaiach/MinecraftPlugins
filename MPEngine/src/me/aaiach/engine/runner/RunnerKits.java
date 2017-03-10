package me.aaiach.engine.runner;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.aaiach.engin.kitutils.Kit;
import me.aaiach.engin.kitutils.KitAction;
import me.aaiach.engine.utils.ActionBar;
import me.aaiach.engine.utils.Main;

public class RunnerKits
{


	private static Main _plugin = Main.getInstance();

	public static Kit Jumper = new Kit("Jumper", new ArrayList<String>(), null, new ItemStack(Material.IRON_PICKAXE, 1), KitAction.Jump);
	public static Kit Star = new Kit("Star", new ArrayList<String>(), new ArrayList<String>(), new ItemStack(Material.NETHER_STAR, 3), KitAction.Noblockfalling);
	public static Kit Ghost = new Kit("Ghost", new ArrayList<String>(), null, new ItemStack(Material.BLAZE_ROD, 3), KitAction.Invisibility);

	public static ArrayList<Kit> Kitlist = new ArrayList<Kit>(Arrays.asList(Jumper, Star, Ghost));


	/**
	 * 
	 * @param s The name of the kit to be returned
	 * @return The kit with the name s
	 */
	public static Kit getRunnerKit(String s)
	{
		Kit k = null;
		for (Kit kit: Kitlist)
		{
			if(kit.getName().equals(s))
			{
				k = kit;
			}
		}
		return k;
	}

	/**
	 * 
	 * @param p The player that will experience the action
	 * @param ka The action that will be executed on the player
	 */
	public static void kitaction(Player p, KitAction ka)
	{
		switch (ka) 
		{
		case Jump:
			jump(p);

			break;
		case Invisibility:
			ghost(p);;

			break;

		case Noblockfalling:
			star(p);

			break;
		default:
			break;
		}
	}

	//Leap
	public static void jump(Player p)
	{
		Long l = System.currentTimeMillis();
		long time = (l - Kit.CoolDown.get(p.getName()))/1000;

		if((time <= 0) || (time >= 10) || (!Kit.CoolDown.containsKey(p.getName())))
		{
			p.setVelocity(p.getLocation().getDirection().multiply(1.2).setY(1.5));
			Kit.CoolDown.put(p.getName(), l);
		}
		else
		{
			p.sendMessage("§9Leap available in " + (10 - time) + " seconds");
		}
	}


	//Period where blocks do not fall under the player's feet
	public static void star(final Player p)
	{
		final ArrayList<String> _players = Star.getPlayerwithpower();
		Long l = System.currentTimeMillis();
		long time = (l - Kit.CoolDown.get(p.getName()))/1000;

		if((time >= 10) || (!Kit.CoolDown.containsKey(p.getName())))
		{
			if(p.getItemInHand().getAmount() == 1){
				p.setItemInHand(new ItemStack(Material.AIR));
			}
			else
			{
				p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
			}

			_players.add(p.getName());
			p.sendMessage("§9You are now invincible");
			Kit.CoolDown.put(p.getName(), l);
			ActionBar.timeActionbar(p);

			Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable()
			{
				public void run()
				{
					_players.remove(p.getName());
					p.sendMessage("§9You are no longer invincible");
				}
			}, 100);
		}else{
			p.sendMessage("§9Invincibility available in " + (10 - time) + " seconds");
		}
	}

	//Period of invisibility
	public static void ghost(final Player p)
	{

		Long l = System.currentTimeMillis();
		long time = (l - Kit.CoolDown.get(p.getName()))/1000;

		if((time >= 10) || (!Kit.CoolDown.containsKey(p.getName())))
		{
			if(p.getItemInHand().getAmount() == 1)
			{
				p.setItemInHand(new ItemStack(Material.AIR));
			}
			else
			{
				p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
			}			
			for(Player _onlinePlayers: Bukkit.getOnlinePlayers())
			{
				_onlinePlayers.hidePlayer(p);
				ActionBar.timeActionbar(p);
			}
			p.sendMessage("§9You are now invisible");
			Kit.CoolDown.put(p.getName(), l);
			Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable()
			{
				public void run()
				{
					for(Player _onlinePlayers: Bukkit.getOnlinePlayers())
					{
						_onlinePlayers.showPlayer(p);
					}	
					p.sendMessage("§9You are no longer invisible");
				}

			}, 100);
		}else{
			p.sendMessage("§9Invisibility available in " + (10 - time) + " seconds");
		}
	}
} 



