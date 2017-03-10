package me.aaiach.engine.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import me.aaiach.engin.kitutils.Kit;
import me.aaiach.engine.runner.RunnerKits;
import me.aaiach.engine.spleef.SpleefKits;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class GameManager
{

	private static Main _plugin = Main.getInstance();

	public static GameType gameGenerator()
	{
		int i = new Random().nextInt(GameType.values().length);
		return GameType.values()[i];
	}

	//Chat countdown before the game starts
	public static void gamecountDown()
	{
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(_plugin, new Runnable()
		{
			int i = 10;

			public void run()
			{
				if(_plugin.Game.getStatus().equals(GameStatus.STARTED))
				{
					if(i != -1)
					{
						if(i != 0)
						{
							Bukkit.broadcastMessage("§9The game will begin in §b" + i + " §9seconds");
							i--;
						}
						else
						{
							Bukkit.broadcastMessage("§9Go!");
							_plugin.Game.setStatus(GameStatus.INGAME);
							i--;
						}
					}
				}
				else
				{
					i = -1;
				}
			}

		}, 0L, 20L);
	}

	//Lobby countdown before players are teleported to the arena
	public static void startGame()
	{
		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(_plugin, new Runnable()
		{
			int i = 30;

			public void run()
			{
				if(_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
				{
					if(i != -1)
					{
						if(i != 0)
						{
							for(Player p: Bukkit.getOnlinePlayers())
							{
								lobbyCountdown(p, i);	
							}
							i--;
						}
						else
						{
							if(Bukkit.getOnlinePlayers().size() > 1)
							{
								initialiseGame();
								i--;
							}
							else
							{
								endGame();
								Bukkit.broadcastMessage("§9There are not enough players to start the game");
								i--;
							}
						}
					}
				}
				else
				{
					i = -1;
				}
			}

		}, 0L, 20L);
	}

	public static void initialiseGame()
	{
		_plugin.Game.setStatus(GameStatus.STARTED);

		Spawnhandler.loadMap(_plugin.Game.getType());

		ArrayList<String> _onlinePlayers = new ArrayList<String>();

		for(Player p: Bukkit.getOnlinePlayers())
		{
			World _world = Bukkit.getServer().getWorld(_plugin.Game.getMapname());
			Location _spawn = new Location(_world, 0, 64, 0);
			p.teleport(_spawn);

			Spawnhandler.unloadlobby();
			_onlinePlayers.add(p.getName());
			
			if(RunnerKits.Ghost.getPlayers().contains(p.getName()))
			{
				p.getInventory().addItem(RunnerKits.Ghost.getItem());
				Kit.CoolDown.put(p.getName(), 0L);
			}
			if(RunnerKits.Star.getPlayers().contains(p.getName()))
			{
				p.getInventory().addItem(RunnerKits.Star.getItem());
				Kit.CoolDown.put(p.getName(), 0L);
			}
			if(RunnerKits.Jumper.getPlayers().contains(p.getName()))
			{
				p.getInventory().addItem(RunnerKits.Jumper.getItem());
				Kit.CoolDown.put(p.getName(), 0L);
			}


			if(SpleefKits.Blast.getPlayers().contains(p.getName()))
			{
				p.getInventory().addItem(SpleefKits.Blast.getItem());
				Kit.CoolDown.put(p.getName(), 0L);
			}
			if(SpleefKits.Builder.getPlayers().contains(p.getName()))
			{
				p.getInventory().addItem(SpleefKits.Builder.getItem());
				Kit.CoolDown.put(p.getName(), 0L);
			}
			if(SpleefKits.Eggtastic.getPlayers().contains(p.getName()))
			{
				p.getInventory().addItem(SpleefKits.Eggtastic.getItem());
				Kit.CoolDown.put(p.getName(), 0L);
			}
		}
		
		_plugin.Game.setPlayersalive(_onlinePlayers);
		gameScoreboard();
		gamecountDown();

	}

	public static void endGame()
	{
		Spawnhandler.loadLobby();

		World _world = Bukkit.getServer().getWorld("world");
		Location _spawn = new Location(_world, 70, 70, 50);

		for(Player p: Bukkit.getOnlinePlayers())
		{
			Spawnhandler.setSpectator(p, false);
			p.teleport(_spawn);
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
			p.setHealth(20L);
			p.getInventory().clear();
		}
		
		for(Kit k: RunnerKits.Kitlist)
		{
			k.getPlayers().clear();
		}
		for(Kit k: SpleefKits.Kitlist)
		{
			k.getPlayers().clear();
		}

		if(!_plugin.Game.getStatus().equals(GameStatus.INLOBBY))
		{
		Spawnhandler.unloadMap(_plugin.Game.getType());
		}
		GameType gt = gameGenerator();

		_plugin.Game.setStatus(GameStatus.INLOBBY);
		_plugin.Game.setType(gt);
		_plugin.Game.setSlotstostart(2);
		_plugin.Game.setMapname(_plugin.Game.getType().toString() + "." + System.currentTimeMillis());
		_plugin.Game.setPlayersalive(null);

		if(Bukkit.getOnlinePlayers().size() >= _plugin.Game.getSlotstostart())
		{
			startGame();
		}

	}

	//Scoreboard countdown in the lobby
	public static void lobbyCountdown(Player p, Integer cd)
	{
		Scoreboard _board = Bukkit.getScoreboardManager().getNewScoreboard();

		Objective _objective = _board.registerNewObjective("Mineplex", "Engine");
		_objective.setDisplayName("§9Welcome fellow cats");
		_objective.setDisplaySlot(DisplaySlot.SIDEBAR);	

		Score _score = _objective.getScore("§6Game §e" + _plugin.Game.getType().toString());
		_score.setScore(0);

		Score _score2 = _objective.getScore("§6Starts in");
		_score2.setScore(cd);

		p.setScoreboard(_board);
	}
	
	//Game scoreboard with list of dead and alive players
	public static void gameScoreboard()
	{
		Scoreboard _board = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective _objective = _board.registerNewObjective("Mineplex", "Engine");
		_objective.setDisplayName("§9Welcome fellow cats");
		_objective.setDisplaySlot(DisplaySlot.SIDEBAR);	

		Score score2 = _objective.getScore("§6Game §e" + _plugin.Game.getType().toString());
		score2.setScore(1);

		Iterator<? extends Player> i = Bukkit.getOnlinePlayers().iterator();

		while(i.hasNext())
		{
			Player p = i.next();
			Score _score;
			
			if(_plugin.Game.getPlayersalive().contains(p.getName()))
			{
				_score = _objective.getScore("§6" + p.getName());
				_score.setScore(0);

			}
			else
			{
				_score = _objective.getScore("§7" + p.getName());
				_score.setScore(0);
			}
		}

		for(Player p: Bukkit.getOnlinePlayers())
		{
			p.setScoreboard(_board);
		}
	}

	//Eliminating a player from the game
	public static void playerDeath(Player died)
	{
		if(_plugin.Game.equals(GameStatus.INLOBBY))
		{
			return;
		}

		ArrayList<String> _players = _plugin.Game.getPlayersalive();
		_players.remove(died.getName());

		_plugin.Game.setPlayersalive(_players);
		gameScoreboard();

		Bukkit.broadcastMessage("§6§l" + died.getName() + " died!");

		if(_players.size() == 1)
		{
			for(int i=0; i < 50; i ++)
			{
				Bukkit.broadcastMessage("\n");
			}

			Bukkit.broadcastMessage("§b§l" + _players.get(0) + " has won the game!");
			Bukkit.broadcastMessage("\n");
			endGame();
		}
	}
}
