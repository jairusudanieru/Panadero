package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.InventoryGUI;
import dev.jairusu.panadero.Methods.LobbyItem;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class PlayerInteract implements Listener {

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      ItemStack item = event.getItem();

      if (!player.getWorld().equals(WorldGroups.lobbyWorld())) return;
      Action action = event.getAction();
      if (!action.isRightClick() || item == null) return;

      if (item.equals(LobbyItem.COMPASS())) {
         Inventory inventory = InventoryGUI.getInventory();
         player.openInventory(inventory);
      } else if (item.equals(LobbyItem.LIME_DYE())) {
         hideOthers(player);
         player.getInventory().setItem(8, LobbyItem.GRAY_DYE());
      } else if (item.equals(LobbyItem.GRAY_DYE())) {
         showOthers(player);
         player.getInventory().setItem(8, LobbyItem.LIME_DYE());
      } else if (item.equals(LobbyItem.INFO_BOOK())) {
         player.sendMessage(Configuration.text("<reset>Welcome To Pandesal!"));
      }
   }

   @EventHandler
   public void onPlayerTouch(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      if (!player.getWorld().equals(WorldGroups.lobbyWorld())) return;
      if (event.getAction() != Action.RIGHT_CLICK_BLOCK) return;
      if (player.getGameMode().equals(GameMode.CREATIVE)) return;
      event.setCancelled(true);
   }

   private static void showOthers(Player player) {
      List<String> worldGroup = WorldGroups.worldGroups(player.getWorld());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         if (!Configuration.isAuthenticated(onlinePlayer)) continue;
         player.showPlayer(Configuration.plugin, onlinePlayer);
      }
   }

   private static void hideOthers(Player player) {
      List<String> worldGroup = WorldGroups.worldGroups(player.getWorld());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         player.hidePlayer(Configuration.plugin, onlinePlayer);
      }
   }

}
