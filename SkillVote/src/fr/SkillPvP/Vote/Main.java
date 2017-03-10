package fr.SkillPvP.Vote;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin{

	public ArrayList<String> votants = new ArrayList<String>();

	public static int oui;
	public static int non;
	public static String question;
	public static boolean activated;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if(label.equalsIgnoreCase("sv")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(p.hasPermission("sv")){
					if(args.length > 1){

						StringBuilder message = new StringBuilder();
						for (int i = 0; i < args.length; i ++) {
							message.append(" ");
							message.append(args[i]);														
						}
						question = "" + message;


					} else if(args.length == 1){
						if(args[0].equals("start")){
							if(question != null){
								activated = true;
								Bukkit.broadcastMessage("\n §7[§e§lSkill§a§lVote§7] §b§l" + question +  "\n §6§oUtilisez §a/oui §6§oou §c/non §6§opour voter \n");
							}else{
								p.sendMessage("§cAucune question n'est définie");
							}
						}

						if(args[0].equals("stop")){
							if((activated = true) && (question != null)){
								activated = false;
								Bukkit.broadcastMessage( "\n §7[§e§lSkill§a§lVote&7] §b§l<questions> \n §a§lOui : §e " + oui + " personnes \n §c§lNon : §e " + non + " personnes \n");
								votants.clear();

							}else{
								p.sendMessage("§cAucun sondage n'est en cours");
							}
						}

						if(args[0].equals("reset")){
							if(question != null){
								if(activated = false){

								oui = 0;
								non = 0;
								votants.clear();
								}else{
									p.sendMessage("§cUtiliser /sv stop avant de reset");
								}
							}else{
								p.sendMessage("§cAucun sondage n'est en cours");
							}
						}
					}
				}
			}
		}

		if(label.equalsIgnoreCase("oui")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(activated = true){
					if(question != null){
						if(!votants.contains(p)){
							oui = oui + 1;
							votants.add(p.getName());
							p.sendMessage("§bVotre vote a été pris en compte! Merci");
						}else{
							p.sendMessage("§cVous avez déjà voté");
						}
					}else{
						p.sendMessage("§cAucun sondage n'est en cours");
					}
				}else{
					p.sendMessage("§cAucun sondage n'est en cours");
				}
			}
		}
		
		if(label.equalsIgnoreCase("non")){
			if(sender instanceof Player){
				Player p = (Player) sender;
				if(activated = true){
					if(question != null){
						if(!votants.contains(p)){
							non = non + 1;
							votants.add(p.getName());
							p.sendMessage("§bVotre vote a été pris en compte! Merci");

						}else{
							p.sendMessage("§cVous avez déjà voté");
						}
					}else{
						p.sendMessage("§cAucun sondage n'est en cours");
					}
				}else{
					p.sendMessage("§cAucun sondage n'est en cours");
				}
			}
		}
		return false;
	}
}
