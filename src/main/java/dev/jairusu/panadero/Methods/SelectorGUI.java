package dev.jairusu.panadero.Methods;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class SelectorGUI {

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

}
