package me.aaiach.engine.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ActionBar
{

	private static Main _plugin = Main.getInstance();
	
	private static String _nmsver = null;

	public static void sendActionBar(Player player, String message)
	{
		if (_nmsver == null)
		{
			_nmsver = Bukkit.getServer().getClass().getPackage().getName();
			_nmsver = _nmsver.substring(_nmsver.lastIndexOf(".") + 1);
		}
		try
		{
			Class<?> craftPlayerClass = Class.forName("org.bukkit.craftbukkit." + _nmsver + ".entity.CraftPlayer");
			Object craftPlayer = craftPlayerClass.cast(player);
			Object packet = null;
			Class<?> packetPlayOutChat = Class.forName("net.minecraft.server." + _nmsver  + ".PacketPlayOutChat");
			Class<?> c5 = Class.forName("net.minecraft.server." + _nmsver + ".Packet");
			if (_nmsver.equalsIgnoreCase("v1_8_R1") || !_nmsver.startsWith("v1_8_"))
			{
				Class<?> chatSerializer = Class.forName("net.minecraft.server." + _nmsver + ".ChatSerializer");
				Class<?> iChatBaseComponentClass = Class.forName("net.minecraft.server." + _nmsver + ".IChatBaseComponent");
				Method a = chatSerializer.getDeclaredMethod("a", new Class<?>[] {String.class});
				Object iChatBaseComponent = iChatBaseComponentClass.cast(a.invoke(chatSerializer, "{\"text\": \"" + message + "\"}"));
				packet = packetPlayOutChat.getConstructor(new Class<?>[] {iChatBaseComponentClass, byte.class}).newInstance(new Object[] {iChatBaseComponent, (byte) 2});
			}
			else
			{
				Class<?> chatComponentText = Class.forName("net.minecraft.server." + _nmsver + ".ChatComponentText");
				Class<?> iChatBaseComponentClass  = Class.forName("net.minecraft.server." + _nmsver + ".IChatBaseComponent");
				Object iChatBaseComponent = chatComponentText.getConstructor(new Class<?>[] {String.class}).newInstance(new Object[] {message});
				packet = packetPlayOutChat.getConstructor(new Class<?>[] {iChatBaseComponentClass, byte.class}).newInstance(new Object[] {iChatBaseComponent, (byte) 2});
			}
			Method getHandle = craftPlayerClass.getDeclaredMethod("getHandle", new Class<?>[] {});
			Object entityPlayer = getHandle.invoke(craftPlayer);
			Field playerConnectionField = entityPlayer.getClass().getDeclaredField("playerConnection");
			Object playerConnection = playerConnectionField.get(entityPlayer);
			Method sendPacket = playerConnection.getClass().getDeclaredMethod("sendPacket",new Class<?>[] {c5});
			sendPacket.invoke(playerConnection, packet);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();

		}
	}


	public static void timeActionbar(Player p)
	{
		final String _box = "§a◼";

		Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(_plugin, new Runnable(){

			int i = 20;

			public void run()
			{
				if(i != 0)
				{
					for(Player p: Bukkit.getOnlinePlayers())
					{
						sendActionBar(p, StringUtils.repeat(_box, i));	
					}
					
					i--;
				}
			}
		}, 0L, 5L);
	}
}

