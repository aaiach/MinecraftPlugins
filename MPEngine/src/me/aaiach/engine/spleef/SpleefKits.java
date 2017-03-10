package me.aaiach.engine.spleef;

import java.util.ArrayList;
import java.util.Arrays;

import me.aaiach.engin.kitutils.Kit;
import me.aaiach.engin.kitutils.KitAction;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class SpleefKits
{
	
	public static Kit Blast = new Kit("Blast", new ArrayList<String>(), null, new ItemStack(Material.IRON_PICKAXE, 1), KitAction.Blast);
	public static Kit Eggtastic = new Kit("Eggtastic", new ArrayList<String>(), null, new ItemStack(Material.EGG, 1), KitAction.Throw);
	public static Kit Builder = new Kit("Builder", new ArrayList<String>(), null, new ItemStack(Material.GLASS, 20), KitAction.Build);

	public static ArrayList<Kit> Kitlist = new ArrayList<Kit>(Arrays.asList(Blast, Eggtastic, Builder));

	/**
	 * 
	 * @param s The name of the kit to be returned
	 * @return The kit with the name s
	 */
	public static Kit getSpleefKit(String s)
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

	//The blast that will break surrounding blocks
	public static void blast(Player p, Block b)
	{

		Location _loc = b.getLocation();
		
		Long l = System.currentTimeMillis();
		long time = (l - Kit.CoolDown.get(p.getName()))/1000;
		
		if((time >= 0.5) || (!Kit.CoolDown.containsKey(p.getName())))
		{
			ArrayList<Location> blocks = new ArrayList<Location>(); //List of surrounding blocks
			blocks.add(_loc.clone().add(0, -1, 0));
			blocks.add(_loc.clone().add(1, 0, 0));
			blocks.add(_loc.clone().add(0, 0, 1));
			blocks.add(_loc.clone().add(-1, 0, 0));
			blocks.add(_loc.clone().add(0, 0, -1));
			
			for(Location blocklist: blocks)
			{					
				if(!(blocklist.getBlock().getType().equals(Material.AIR)))
				{
					blocklist.getBlock().setType(Material.AIR);
				}
			}
			Kit.CoolDown.put(p.getName(), l);
		}
	}
}
