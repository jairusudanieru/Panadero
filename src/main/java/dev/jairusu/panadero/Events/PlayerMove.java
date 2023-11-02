package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.AFKManager;
import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMove implements Listener {

   @EventHandler
   public void onPlayerVoid(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      double voidLevel = Configuration.getDouble("config.voidYLevel");
      Location spawnLocation = Configuration.getLocation("location.spawnLocation");
      if (spawnLocation == null) spawnLocation = player.getWorld().getSpawnLocation();
      if (!player.getWorld().equals(WorldGroups.lobbyWorld())) return;
      if (player.getLocation().getY() > voidLevel) return;
      player.setFallDistance(0);
      player.teleport(spawnLocation);
   }

   @EventHandler
   public void onPlayerMoveAFK(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      if (event.hasChangedOrientation()) return;
      AFKManager.lastMovementHashMap.put(player, System.currentTimeMillis());
      if (!AFKManager.afkStatusHashMap.containsKey(player)) return;
      AFKManager.afkStatusHashMap.remove(player);
      AFKManager.removeToAFKTeam(player, AFKManager.afkTeam());
   }

}
