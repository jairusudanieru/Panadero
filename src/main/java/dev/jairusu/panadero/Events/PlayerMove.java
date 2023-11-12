package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.AFKManager;
import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.List;

public class PlayerMove implements Listener {

   @EventHandler
   public void onPlayerVoid(PlayerMoveEvent event) {
      Player player = event.getPlayer();
      double voidLevel = Configuration.getDouble("config.voidYLevel");
      Location spawnLocation = player.getWorld().getSpawnLocation();
      if (!player.getWorld().equals(WorldGroups.lobbyWorld()) &&
         !player.getWorld().equals(WorldGroups.authWorld())) return;
      if (player.getLocation().getY() > voidLevel) return;
      player.setFallDistance(0);
      player.teleport(spawnLocation);
   }

   @EventHandler
   public void onPlayerMoveAFK(PlayerMoveEvent event) {
      if (event.hasChangedOrientation()) return;
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      List<String> worldGroups = WorldGroups.worldGroups(WorldGroups.survivalWorld());
      if (!worldGroups.contains(playerWorld.getName())) return;

      AFKManager.lastMovementHashMap.put(player, System.currentTimeMillis());
      if (!AFKManager.afkStatusHashMap.containsKey(player)) return;
      AFKManager.afkStatusHashMap.remove(player);
      AFKManager.removeToAFKTeam(player, AFKManager.afkTeam());
   }

}
