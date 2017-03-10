package me.aaiach.engin.kitutils;

import me.aaiach.engine.runner.RunnerKits;
import me.aaiach.engine.spleef.SpleefKits;
import me.aaiach.engine.utils.GameStatus;
import me.aaiach.engine.utils.GameType;
import me.aaiach.engine.utils.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KitManager implements Listener, CommandExecutor
{

	private static Main _plugin = Main.getInstance();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{	
		if(sender instanceof Player)
		{
			if(_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
			{
				Player p = (Player) sender;
				if(args.length == 0)
				{
					openInventory(p, _plugin.Game.getType());
				}
			}
		}
		return false;
	}

	/**
	 * Opening the Kit menu used in the lobby
	 * @param p The player that will see the inventory
	 * @param gt The game for which kits will be shown for
	 */
	public void openInventory(Player p, GameType gt)
	{
		if(gt.equals(GameType.RUNNER))
		{
			ItemStack _jumper = new ItemStack(RunnerKits.Jumper.getItem());
			ItemMeta _jumperMeta = _jumper.getItemMeta();
			_jumperMeta.setDisplayName("Jumper Kit - §6Right click axe to jump!");
			_jumper.setItemMeta(_jumperMeta);

			ItemStack _star = new ItemStack(RunnerKits.Star.getItem());
			ItemMeta _starMeta = _star.getItemMeta();
			_starMeta.setDisplayName("Star Kit - §6Right click star to run freely for 3 seconds!");
			_star.setItemMeta(_starMeta);

			ItemStack _ghost = new ItemStack(RunnerKits.Ghost.getItem());
			ItemMeta _ghostMeta = _ghost.getItemMeta();
			_ghostMeta.setDisplayName("Ghost Kit - §6Right click to dissapear!");
			_ghost.setItemMeta(_ghostMeta);

			Inventory _kitInv = Bukkit.createInventory(null, 9, "§cChoose a kit!");
			_kitInv.setItem(2, _jumper);
			_kitInv.setItem(4, _star);
			_kitInv.setItem(6, _ghost);

			p.openInventory(_kitInv);
		}

		if(gt.equals(GameType.SPLEEF))
		{
			ItemStack _builder = new ItemStack(SpleefKits.Builder.getItem());
			ItemMeta _builderMeta = _builder.getItemMeta();
			_builderMeta.setDisplayName("Builder Kit - §6Place blocks that will help you out!");
			_builder.setItemMeta(_builderMeta);

			ItemStack _blast = new ItemStack(SpleefKits.Blast.getItem());
			ItemMeta _blastMeta = _blast.getItemMeta();
			_blastMeta.setDisplayName("Blast Kit - §6Break more blocks with your pickaxe!");
			_blast.setItemMeta(_blastMeta);

			ItemStack _eggtastic = new ItemStack(SpleefKits.Eggtastic.getItem());
			ItemMeta _eggtasticMeta = _eggtastic.getItemMeta();
			_eggtasticMeta.setDisplayName("Eggtastic Kit - §6Throw eggs to break blocks!");
			_eggtastic.setItemMeta(_eggtasticMeta);

			Inventory _kitInv = Bukkit.createInventory(null, 9, "§cChoose a kit!");
			_kitInv.setItem(2, _builder);
			_kitInv.setItem(4, _blast);
			_kitInv.setItem(6, _eggtastic);

			p.openInventory(_kitInv);
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e)
	{
		e.setCancelled(true);
		Player p = (Player) e.getWhoClicked();
		String _kitName = e.getCurrentItem().getItemMeta().getDisplayName().split(" ")[0];
		Kit.setKit(p, _kitName, _plugin.Game.getType());
	}


}
