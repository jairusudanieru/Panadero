package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.Configuration;
import dev.jairusu.panadero.Methods.WorldGroups;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class PlayerRespawn implements Listener {

   @EventHandler
   public void onPlayerRespawn(PlayerRespawnEvent event) {
      Player player = event.getPlayer();
      if (!Configuration.isAuthenticated(player)) return;
      List<String> worlds = Arrays.asList("world","world_nether","world_the_end");
      if (!worlds.contains(player.getWorld().getName())) return;
      Location spawnLocation = player.getBedSpawnLocation();

      if (spawnLocation == null) {
         spawnLocation = Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation();
         event.setRespawnLocation(spawnLocation);
         return;
      }

      int radius = Configuration.getInt("config.bedRadius");
      Location loc = spawnLocation;
      World world = loc.getWorld();
      for (int x = -radius; x < radius; x++) {
         for (int y = -radius; y < radius; y++) {
            for (int z = -radius; z < radius; z++) {
               Block block = world.getBlockAt(loc.getBlockX()+x, loc.getBlockY()+y, loc.getBlockZ()+z);
               if (block.getType().name().contains("_BED")) return;
               if (block.getType().equals(Material.RESPAWN_ANCHOR)) return;
            }
         }
      }

      spawnLocation = Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation();
      event.setRespawnLocation(spawnLocation);
   }

   @EventHandler
   public void onPlayerRespawn1(PlayerRespawnEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      if (!playerWorld.equals(WorldGroups.arenaWorld())) return;
      Bukkit.getScheduler().runTaskLater(Configuration.plugin, () -> {
         player.teleport(WorldGroups.arenaLocation());
         player.getInventory().clear();
      }, 1L);
   }

}
