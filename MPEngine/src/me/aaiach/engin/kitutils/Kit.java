package me.aaiach.engin.kitutils;

import java.util.ArrayList;
import java.util.HashMap;

import me.aaiach.engine.runner.RunnerKits;
import me.aaiach.engine.spleef.SpleefKits;
import me.aaiach.engine.utils.GameType;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Kit 
{

	public static HashMap<String, Long> CoolDown = new HashMap<String, Long>();

	protected String Name;
	protected ArrayList<String> Players;
	protected ArrayList<String> Playerswithpower;
	protected ItemStack Item;
	protected KitAction Action;


	public Kit()
	{
		Name = null;
		Players = null;
		Playerswithpower = null;
		Item = null;
		Action = null;
	}

	public Kit(String aName, ArrayList<String> aPlayers, ArrayList<String> aPlayerswithpower, ItemStack aItem, KitAction aAction)
	{
		Name = aName;
		Players = aPlayers;
		Playerswithpower = aPlayerswithpower;
		Item = aItem;
		Action = aAction;
	}


	public String getName()
	{
		return Name;
	}
	public ArrayList<String> getPlayers()
	{
		return Players;
	}
	public ArrayList<String> getPlayerwithpower()
	{
		return Playerswithpower;
	}
	public ItemStack getItem()
	{
		return Item;
	}
	public KitAction getAction()
	{
		return Action;
	}



	public void setName(String aName)
	{
		Name = aName;
	}
	public void setPlayers(ArrayList<String> aPlayers)
	{
		Players = aPlayers;
	}
	public void setPlayerswithpower(ArrayList<String> aPlayerswithpower)
	{
		Playerswithpower = aPlayerswithpower;
	}
	public void setItem(ItemStack aItem)
	{
		Item = aItem;
	}
	public void setAction(KitAction aAction)
	{
		Action = aAction;	
	}


	/**
	 * Setting a kit to a player
	 * @param p The player which will receive the kit
	 * @param kitname The kit that will be given to a player
	 * @param gt The game for which the kit will be set
	 */
	public static void setKit(Player p, String kitname, GameType gt)
	{
		if(gt.equals(GameType.RUNNER))
		{
			for(Kit k: RunnerKits.Kitlist)
			{
				if(k.getPlayers().contains(p.getName()))
				{
					k.getPlayers().remove(p.getName());
					k.setPlayers(k.getPlayers());
				}
			}

			ArrayList<String> players = RunnerKits.getRunnerKit(kitname).getPlayers();
			players.add(p.getName());
			RunnerKits.getRunnerKit(kitname).setPlayers(players);

			p.sendMessage("§9You have selected the §b" + kitname + " §9kit" );

		}

		if(gt.equals(GameType.SPLEEF))
		{
			for(Kit k: SpleefKits.Kitlist)
			{
				if(k.getPlayers().contains(p.getName()))
				{
					k.getPlayers().remove(p.getName());
					k.setPlayers(k.getPlayers());

				}
			}

			ArrayList<String> _players = SpleefKits.getSpleefKit(kitname).getPlayers();
			_players.add(p.getName());
			SpleefKits.getSpleefKit(kitname).setPlayers(_players);

			p.sendMessage("§9You have selected the §b" + kitname + " §9kit" );
		}
	}

	/**
	 * Getting the kit a player has chosen
	 * @param p The player we are looking for
	 * @param gt The game from which the kit is from
	 */
	public static Kit getKit(Player p, GameType gt)
	{
		Kit kit = null;

		if(gt.equals(GameType.RUNNER))
		{
			for(Kit k: RunnerKits.Kitlist)
			{
				if(k.getPlayers().contains(p.getName()))
				{
					kit = k;
				}
			}
		}
		if(gt.equals(GameType.SPLEEF))
		{
			for(Kit k: SpleefKits.Kitlist)
			{
				if(k.getPlayers().contains(p.getName()))
				{
					kit = k;
				}
			}
		}
		return kit;
	}
}
