package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.LobbyItem;
import dev.jairusu.panadero.Methods.Utilities;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PlayerInteract implements Listener {

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack item = event.getItem();

      if (!player.getWorld().equals(Utilities.lobbyWorld())) return;
      Action action = event.getAction();
      if (!action.isRightClick() || item == null) return;

      if (item.equals(LobbyItem.COMPASS())) {
         Inventory inventory = Utilities.getInventory();
         player.openInventory(inventory);
      } else if (item.equals(LobbyItem.LIME_DYE())) {
         Utilities.hideOthers(player);
         player.getInventory().setItem(8, LobbyItem.GRAY_DYE());
      } else if (item.equals(LobbyItem.GRAY_DYE())) {
         Utilities.showOthers(player);
         player.getInventory().setItem(8, LobbyItem.LIME_DYE());
      } else if (item.equals(LobbyItem.INFO_BOOK())) {
         player.sendMessage(Configuration.text("<reset>Welcome To Pandesal!"));
      }
   }

   @EventHandler
   public void onPlayerTouch(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      if (!player.getWorld().equals(Utilities.lobbyWorld())) return;
      if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
      if (player.getGameMode().equals(GameMode.CREATIVE)) return;
      event.setCancelled(true);
   }

}
