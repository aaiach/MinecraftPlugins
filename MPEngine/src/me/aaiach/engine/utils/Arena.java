package me.aaiach.engine.utils;

import java.util.ArrayList;

public class Arena
{

	protected GameStatus Status;
	protected GameType Type;
	protected String Mapname;
	protected Integer Slotstostart;
	protected ArrayList<String> Playersalive;

	public Arena()
	{
		Status = GameStatus.INLOBBY;
		Type = null;
		Mapname = null;
		Slotstostart = 0;
		Playersalive = null;

	}

	public Arena(GameStatus aStatus, GameType aType, String aMapname, Integer aSlotstostart, ArrayList<String> aPlayersalive)
	{
		Status = aStatus;
		Type = aType;
		Mapname = aMapname;
		Slotstostart = aSlotstostart;
		Playersalive = aPlayersalive;

	}

	public GameStatus getStatus()
	{
		return Status;
	}
	public GameType getType()
	{
		return Type;
	}
	public String getMapname()
	{
		return Mapname;
	}
	public Integer getSlotstostart()
	{
		return Slotstostart;
	}
	public ArrayList<String> getPlayersalive()
	{
		return Playersalive;
	}


	public void setStatus(GameStatus aStatus)
	{
		Status = aStatus;
	}
	public void setType(GameType aType)
	{
		Type = aType;
	}
	public void setMapname(String aMapname)
	{
		Mapname = aMapname;
	}
	public void setSlotstostart(Integer aSlotstostart)
	{
		Slotstostart = aSlotstostart;
	}
	public void setPlayersalive(ArrayList<String> aPlayersalive)
	{
		Playersalive = aPlayersalive;
	}


}
