package dev.jairusu.panadero.Events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnEggs implements Listener {

   @EventHandler
   public void onPlayerInteract(PlayerInteractEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      World creative = Bukkit.getWorld("creative");
      if (creative == null) return;
      if (!playerWorld.equals(creative)) return;

      if (player.getInventory().getItemInMainHand().getType().name().contains("SPAWN_EGG") ||
      player.getInventory().getItemInOffHand().getType().name().contains("SPAWN_EGG")) {
         event.setCancelled(true);
      }
   }

}
