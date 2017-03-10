package me.aaiach.engine.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormat implements Listener{

	private Main _plugin = Main.getInstance();

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e)
	{
		e.setCancelled(true);

		Player p = e.getPlayer();
		String _name = p.getName();
		String _message = e.getMessage();

		if(_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			Bukkit.broadcastMessage("§b" + _name + "§9 > " + "§f" + _message);
		}
		else if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(_plugin.Game.getPlayersalive().contains(_name))
			{
				Bukkit.broadcastMessage("§b" + _name + "§9 > " + "§f" + _message);
			}
			else
			{
				Bukkit.broadcastMessage("§7" + "(Spec)" + "§b " + _name + "§9 > " + "§f" + _message);

			}
		}
	}
}
