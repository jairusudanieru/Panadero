package dev.jairusu.panadero.Methods;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryGUI {

   private static Inventory inventory;

   public static void makeInventory() {
      Component inventoryTitle = Configuration.text("<reset>Server Selector");
      inventory = Bukkit.createInventory(null, 9 * 3, inventoryTitle);
      inventory.setItem(10, LobbyItem.CREATIVE());
      inventory.setItem(13, LobbyItem.SURVIVAL());
      inventory.setItem(16, LobbyItem.PVP_ARENA());
   }

   public static Inventory getInventory() {
      return inventory;
   }

   public static void giveItems(Player player) {
      player.getInventory().clear();
      player.getInventory().setItem(8, LobbyItem.LIME_DYE());
      player.getInventory().setItem(4, LobbyItem.COMPASS());
      player.getInventory().setItem(0, LobbyItem.INFO_BOOK());
   }

}
