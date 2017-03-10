package fr.haperycube.kit.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commandes implements CommandExecutor{

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(cmd.getName().equalsIgnoreCase("kit")){
			if(sender instanceof Player){
				

					Player p = (Player) sender;

					if(p.hasPermission("hc.kit")){
						if(args.length == 0){
						Inventories.openInventory(p);

					}else{
						p.sendMessage("&3Utilisez /kit");

					}
				}else{
					p.sendMessage("§3Vous n'avez pas la permission");
				}
			}
		}


		return false;
	}


}
