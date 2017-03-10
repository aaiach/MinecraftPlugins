package me.aaiach.engine.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Spawnhandler implements Listener
{

	private static Main _plugin = Main.getInstance();

	public static void loadLobby(){
		Bukkit.getServer().createWorld(new WorldCreator("world"));
	}

	public static void loadMap(GameType gt)
	{

		File source = new File(System.getProperty("user.dir") + File.separator + gt.toString());
		File destination = new File(System.getProperty("user.dir") + File.separator + _plugin.Game.getMapname());

		try
		{
			copyWorld(source, destination, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		Bukkit.getServer().createWorld(new WorldCreator( _plugin.Game.getMapname()));
	}

	public static void unloadlobby()
	{
		Bukkit.getServer().unloadWorld("world", true);
	}

	public static void unloadMap(final GameType gt)
	{		
		final String world = _plugin.Game.getMapname();
		final World delete = Bukkit.getWorld(world);
		Bukkit.getScheduler().scheduleSyncDelayedTask(_plugin, new Runnable()
		{
			
			public void run()
			{
				Bukkit.getServer().unloadWorld(world , true);
				File deleteFolder = delete.getWorldFolder();
				deleteWorld(deleteFolder);	
			}
		}, 100);
		
		
	}
	
	


	public static void copyWorld(File source, File destination, StandardCopyOption cp) throws IOException
	{
		Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);

		String[] files = source.list();

		if (files != null)
		{
			for (String file : files)
			{
				File sourceFile = new File(source, file);
				File destinationFile = new File(destination, file);

				copyWorld(sourceFile, destinationFile, cp);
			}
		}
	}

	public static boolean deleteWorld(File path)
	{
		if(path.exists())
		{
			File files[] = path.listFiles();
			for(int i=0; i<files.length; i++)
			{
				if(files[i].isDirectory())
				{
					deleteWorld(files[i]);
				}
				else
				{
					files[i].delete();
				}
			}
		}
		return(path.delete());
	}

	/**
	 * 
	 * @param p Player to set spectator mode
	 * @param b Whether the player is put into.out of spectator mode
	 */
	public static void setSpectator(Player p, Boolean b)
	{		

		if(b.equals(true))
		{
			p.setAllowFlight(true);
			p.setFlying(true);
			for(Player pl: Bukkit.getOnlinePlayers()){
				pl.hidePlayer(p);
			}
			p.sendMessage("§9You are now in spectator mode");
		}

		if(b.equals(false))
		{
			p.setAllowFlight(false);
			p.setFlying(false);
			for(Player pl: Bukkit.getOnlinePlayers())
			{
				pl.showPlayer(p);
			}
		}
	}



	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		World world = null;

		if(_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(Bukkit.getServer().getWorld("world") != null){

				world = Bukkit.getServer().getWorld("world");
				p.teleport(new Location(world, 70, 70, 50));
			}
		}

		if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
			if(Bukkit.getServer().getWorld(_plugin.Game.getMapname()) != null)
			{
				world = Bukkit.getServer().getWorld(_plugin.Game.getMapname());
				p.teleport(new Location(world, 0, 64, 0));
				setSpectator(p, true);

			}
		}

		if(Bukkit.getOnlinePlayers().size() == _plugin.Game.getSlotstostart()){
			GameManager.startGame();
		}
	}
}
