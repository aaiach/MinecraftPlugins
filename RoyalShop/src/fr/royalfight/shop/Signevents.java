package fr.royalfight.shop;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.plugins.Economy_Essentials;

import com.earth2me.essentials.api.*;

public class Signevents implements Listener{

	private Main plugin = Main.getInstance();

	public static com.earth2me.essentials.api.Economy econ;
	private static ArrayList<String> invlist = new ArrayList<String>();

	public static boolean isDouble(String s) {
		try { 
			Double.parseDouble(s); 
		} catch(NumberFormatException e) { 
			return false; 
		}
		return true;
	}

	@EventHandler
	public void onSignChange(SignChangeEvent e){

		Player p = e.getPlayer();


		if(e.getLine(0).contains("RoyalShop")){
			if(p.hasPermission("royalshop.create")){
				if(e.getLine(1) != null){
					if(e.getLine(2) != null){
						e.setLine(0, "§cRoyalShop");
						e.setLine(1, e.getLine(1));
						e.setLine(2, e.getLine(2));
						e.setLine(3, "§6P.O L'unité");

					}
				}
			}else{
				p.sendMessage("§cPermission manquante");
				e.setCancelled(true);
			}
		}
	}

	@EventHandler
	public void onRightClick(PlayerInteractEvent e){

		Block b = e.getClickedBlock();
		Player p = e.getPlayer();

		if (b == null) {
			return;
		}

		if(p.hasPermission("royalshop.use")){

			if (e.getAction() == Action.RIGHT_CLICK_BLOCK && b != null && (b.getType() == Material.SIGN_POST || b.getType() == Material.WALL_SIGN)) {
				Sign sign = (Sign) b.getState();
				if (sign.getLine(0).contains("RoyalShop")){
					if(sign.getLine(1) != null){
						if(sign.getLine(2) != null){

							String FullID = sign.getLine(1);
							int ID = Integer.parseInt(FullID.split(":")[0]);
							Short data = Short.parseShort((FullID.split(":")[1]));

							//int Price = Integer.parseInt(sign.getLine(2));

							String FullPrice = sign.getLine(2);

							ItemStack vendre64  = new ItemStack(Material.getMaterial(ID), 64, data);
							ItemMeta vendre64meta = vendre64.getItemMeta(); 
							vendre64meta.setDisplayName("§6Vends 64 pour §c");
							ArrayList<String> vendre64lore = new ArrayList<String>();

							if(isDouble(sign.getLine(2).split(":")[1])){
								double Sellprice = Double.parseDouble((FullPrice.split(":")[1]));
								vendre64lore.add(0, String.valueOf(Sellprice * 64));
								vendre64lore.add(1, "§cP.O");
							}else{
								vendre64lore.add(0, "§c§lVente impossible");
							}

							vendre64meta.setLore(vendre64lore);
							vendre64.setItemMeta(vendre64meta);



							ItemStack vendre32  = new ItemStack(Material.getMaterial(ID), 32, data);
							ItemMeta vendre32meta = vendre32.getItemMeta(); 
							vendre32meta.setDisplayName("§6Vends 32 pour §c");

							ArrayList<String> vendre32lore = new ArrayList<String>();
							if(isDouble(sign.getLine(2).split(":")[1])){
								double Sellprice = Double.parseDouble((FullPrice.split(":")[1]));
								vendre32lore.add(0, String.valueOf(Sellprice * 32));
								vendre32lore.add(1, "§cP.O");
							}else{
								vendre32lore.add(0, "§c§lVente impossible");
							}

							vendre32meta.setLore(vendre32lore);
							vendre32.setItemMeta(vendre32meta);



							ItemStack vendre1  = new ItemStack(Material.getMaterial(ID), 1, data);
							ItemMeta vendre1meta = vendre1.getItemMeta(); 
							vendre1meta.setDisplayName("§6Vends 1 pour §c");
							ArrayList<String> vendre1lore = new ArrayList<String>();

							if(isDouble(sign.getLine(2).split(":")[1])){
								double Sellprice = Double.parseDouble((FullPrice.split(":")[1]));
								vendre1lore.add(0, String.valueOf(Sellprice));
								vendre1lore.add(1, "§cP.O");
							}else{
								vendre1lore.add(0, "§c§lVente impossible");
							}

							vendre1meta.setLore(vendre1lore);
							vendre1.setItemMeta(vendre1meta);



							ItemStack buy64  = new ItemStack(Material.getMaterial(ID), 64, data);
							ItemMeta buy64meta = buy64.getItemMeta(); 
							buy64meta.setDisplayName("§6Achète 64 pour §c");
							ArrayList<String> buy64lore = new ArrayList<String>();

							if(isDouble(sign.getLine(2).split(":")[0])){
								double Buyprice = Double.parseDouble(FullPrice.split(":")[0]);
								buy64lore.add(0, String.valueOf(Buyprice * 64));
								buy64lore.add(1, "§cP.O");
							}else{
								buy64lore.add(0, "§c§lAchat impossible");
							}

							buy64meta.setLore(buy64lore);
							buy64.setItemMeta(buy64meta);



							ItemStack buy32  = new ItemStack(Material.getMaterial(ID), 32, data);
							ItemMeta buy32meta = buy64.getItemMeta(); 
							buy32meta.setDisplayName("§6Achète 32 pour §c");
							ArrayList<String> buy32lore = new ArrayList<String>();

							if(isDouble(sign.getLine(2).split(":")[0])){
								double Buyprice = Double.parseDouble(FullPrice.split(":")[0]);
								buy32lore.add(0, String.valueOf(Buyprice * 32));
								buy32lore.add(1, "§cP.O");
							}else{
								buy32lore.add(0, "§c§lAchat impossible");
							}

							buy32meta.setLore(buy32lore);
							buy32.setItemMeta(buy32meta);



							ItemStack buy1  = new ItemStack(Material.getMaterial(ID), 1, data);
							ItemMeta buy1meta = buy1.getItemMeta(); 
							buy1meta.setDisplayName("§6Achète 1 pour §c");
							ArrayList<String> buy1lore = new ArrayList<String>();

							if(isDouble(sign.getLine(2).split(":")[0])){
								double Buyprice = Double.parseDouble(FullPrice.split(":")[0]);
								buy1lore.add(0, String.valueOf(Buyprice));
								buy1lore.add(1, "§cP.O");
							}else{
								buy1lore.add(0, "§c§lAchat impossible");
							}

							buy1meta.setLore(buy1lore);
							buy1.setItemMeta(buy1meta);



							ItemStack vendretout = new ItemStack(Material.getMaterial(ID), 1, data);
							ItemMeta vendretoutmeta = vendretout.getItemMeta();
							vendretoutmeta.setDisplayName("§6Tout vendre pour");
							ArrayList<String> vendretoutlore = new ArrayList<String>();

							if(isDouble(sign.getLine(2).split(":")[1])){

								double Sellprice = Double.parseDouble((FullPrice.split(":")[1]));
								ItemStack[] invp = p.getInventory().getContents();

								double Sellall = 0;
								for(ItemStack is: invp){
									if((is != null) && ((is.getTypeId()) == ID) && (is.getData().getData() == data)){
										Sellall = Sellall + is.getAmount();
									}
								}

								double Sellallprice = (Sellall * Sellprice);
								vendretoutlore.add(0, String.valueOf(Sellallprice));
								vendretoutlore.add(1, "§cP.O");
							}else{
								vendretoutlore.add(0, "§c§lVente impossible");
							}

							vendretoutmeta.setLore(vendretoutlore);
							vendretout.setItemMeta(vendretoutmeta);

							Inventory inv;
							inv = Bukkit.createInventory(null, 27, "§c  Acheter             §cVendre");
							invlist.add(inv.getName());
							inv.setItem(0, buy64);
							inv.setItem(1, buy32);
							inv.setItem(2, buy1);
							inv.setItem(8, vendre64);
							inv.setItem(7, vendre32);
							inv.setItem(6, vendre1);
							inv.setItem(26, vendretout);

							p.openInventory(inv);



						}else{
							p.sendMessage("§6[§cRoyalFight§6] §cLe montant indiqué est invalide");
						}
					}else{
						p.sendMessage("§6[§cRoyalFight§6] §cLe Bloc indiqué est invalide");
					}
				}
			}
		}   
	}

	@EventHandler (priority = EventPriority.LOWEST)
	public void onClick(InventoryClickEvent e){
		Player p = (Player) e.getWhoClicked();

		if(invlist.contains(e.getInventory().getName())){
			e.setCancelled(true);

		}

		if(invlist.contains(e.getInventory().getName())){
			if(e.getCurrentItem() != null){
				if(e.getSlot() <=3){
					if(e.getCurrentItem().getItemMeta() != null){

						String price = e.getCurrentItem().getItemMeta().getLore().get(0);
						Material item = e.getCurrentItem().getType();
						int amount = e.getCurrentItem().getAmount();
						byte data = e.getCurrentItem().getData().getData();

						if((plugin.econ != null)){
							if(isDouble(e.getCurrentItem().getItemMeta().getLore().get(0))){
								if(plugin.econ.has(p, Double.parseDouble(price))){

									//plugin.econ.depositPlayer(p, Double.parseDouble(price) - (2 * Double.parseDouble(price)));
									try {
										com.earth2me.essentials.api.Economy.substract(p.getName(), BigDecimal.valueOf(Double.parseDouble(price)));;
									} catch (NumberFormatException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (NoLoanPermittedException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									} catch (UserDoesNotExistException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									p.getInventory().addItem(new ItemStack(item, amount, data));

									p.sendMessage("§6[§cRoyalFight§6] §aVous avez acheté " + amount + " " + item + " pour " + price + " P.O");
									//p.closeInventory();


								}else{
									p.sendMessage("§6[§cRoyalFight§6] §cVous manquez d'argent");
								}
							}else{
								p.sendMessage("§6§6[§cRoyalFight§6] Achat impossible");
							}
						}
					}
					
					
					
					
					
				} else if((e.getSlot() >= 4) && (e.getSlot() < 10)){

					if(e.getCurrentItem().getItemMeta() != null){


						Material item = e.getCurrentItem().getType();
						int amount = e.getCurrentItem().getAmount();
						byte data = e.getCurrentItem().getData().getData();


						if((plugin.econ != null)){
							if(isDouble(e.getCurrentItem().getItemMeta().getLore().get(0))){

								String price = e.getCurrentItem().getItemMeta().getLore().get(0);
								double sell = (Double.parseDouble(price));

								if(p.getInventory().getItemInHand().getType().equals(item)){
									if(p.getInventory().getItemInHand().getData().equals(e.getCurrentItem().getData())){
										if(p.getInventory().getItemInHand().getAmount() >= amount){

											if((amount == (p.getItemInHand().getAmount()))){
												p.setItemInHand(new ItemStack(Material.AIR));
												plugin.econ.depositPlayer(p, sell);

											}else{
											
											plugin.econ.depositPlayer(p, sell);
											p.getItemInHand().setAmount(p.getItemInHand().getAmount() - amount);
											}


											
											p.sendMessage("§6[§cRoyalFight§6] §aVous avez vendu " + amount + " " + item + " pour " + price + " P.O");
											//p.closeInventory();


										}else{
											p.sendMessage("§6[§cRoyalFight§6] §cVous n'avez pas assez de cet item dans votre main");
										}
									}else{
										p.sendMessage("§6[§cRoyalFight§6] §cVous n'avez pas le bon item dans votre main");

									}


								}else if(p.getInventory().contains(item)){

									if(p.getInventory().contains(new ItemStack(item, amount, data))){

										plugin.econ.depositPlayer(p, sell);
										p.getInventory().removeItem(new ItemStack(item, amount, data));
										p.sendMessage("§6[§cRoyalFight§6] §aVous avez vendu " + amount + " " + item + " pour " + price + " P.O");
										//p.closeInventory();


									}else{
										p.sendMessage("§6[§cRoyalFight§6] §cVous n'avez pas ce qu'il faut dans votre main/ inventaire");
									}

								}else{
									p.sendMessage("§6[§cRoyalFight§6] §cVous n'avez pas le bon item dans votre inventaire");


								}
							}else{
								p.sendMessage("§6§6[§cRoyalFight§6] Vente impossible");
							}
						}
					}
				} else if((e.getSlot() > 11)){

					if(e.getCurrentItem().getItemMeta() != null){

						Material item = e.getCurrentItem().getType();
						byte data = e.getCurrentItem().getData().getData();

						ItemStack[] inv = p.getInventory().getContents();
						int amount= 0;


						for(ItemStack is: inv){
							if((is != null) && ((is.getTypeId()) == item.getId()) && (is.getData().getData() == data)){
								amount = amount + is.getAmount();
							}
						}
						

						if(isDouble(e.getCurrentItem().getItemMeta().getLore().get(0))){

							//String price = e.getCurrentItem().getItemMeta().getLore().get(0);
							String priceofone = e.getInventory().getItem(6).getItemMeta().getLore().get(0);
							double Sellall = 0;
							for(ItemStack is: inv){
								if((is != null) && ((is.getType()) == item) && (is.getData().getData() == data)){
									Sellall = Sellall + is.getAmount();
								}
							}
							
							double price = Sellall*(Double.parseDouble(priceofone));

							if(p.getInventory().contains(item)){

								plugin.econ.depositPlayer(p, price);
								p.getInventory().removeItem(new ItemStack(item, amount, data));

								for(ItemStack is: inv){
									if((is != null) && ((is.getTypeId()) == item.getId()) && (is.getData().getData() == data)){
										p.getInventory().remove(is);
									}
								}
								

								p.sendMessage("§6[§cRoyalFight§6] §aVous avez vendu " + amount + " " + item + " pour " + price + " P.O");
								//p.closeInventory();

							}else{
								p.sendMessage("§6[§cRoyalFight§6] §cVous n'avez pas le bon item dans votre inventaire");
							}
						}else{
							p.sendMessage("§6§6[§cRoyalFight§6] Vente impossible");
						}
					}
				}
			}
		}
	}

	@EventHandler
	public void close(InventoryCloseEvent e){
		if(invlist.contains(e.getInventory().getName())){
			invlist.remove(e.getInventory().getName());


		}		
	}

	@EventHandler
	public void onClick(InventoryPickupItemEvent e){
		if(invlist.contains(e.getInventory().getName())){
			e.setCancelled(true);
		}
	}

	@EventHandler 
	public void onClick(InventoryInteractEvent e){
		if(invlist.contains(e.getInventory().getName()))
			e.setCancelled(true);
	}
	
	@EventHandler (priority = EventPriority.LOWEST)
	public void oniMove(InventoryMoveItemEvent e){
		if(invlist.contains(e.getDestination().getName())){
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onChat (AsyncPlayerChatEvent e){
		if(e.getMessage().startsWith("shoppinghelp")){
			plugin.shopping(e.getPlayer());
		}
	}
}




