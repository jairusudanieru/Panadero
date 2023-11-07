package dev.jairusu.panadero.Methods;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldGroups {

   public static List<String> worldGroups(World world) {
      ConfigurationSection section = Configuration.getConfigSection("worldGroups");
      if (section == null) return new ArrayList<>();
      for (String groupName : section.getKeys(false)) {
         List<String> worldNames = section.getStringList(groupName);
         if (!worldNames.contains(world.getName())) continue;
         return worldNames;
      }
      return new ArrayList<>();
   }

   public static Location authLocation() {
      Location location = Configuration.getLocation("location.authLocation");
      if (location == null) location = lobbyWorld().getSpawnLocation();
      return location;
   }

   public static Location arenaLocation() {
      Location location = Configuration.getLocation("location.arenaLocation");
      if (location == null) location = lobbyWorld().getSpawnLocation();
      return location;
   }

   public static Location spawnLocation() {
      Location location = Configuration.getLocation("location.spawnLocation");
      if (location == null) location = lobbyWorld().getSpawnLocation();
      return location;
   }

   public static World lobbyWorld() {
      World world = Configuration.getWorld("config.lobbyWorld");
      if (world == null) world = Bukkit.getWorld("world");
      return world;
   }

   public static World arenaWorld() {
      Location location = Configuration.getLocation("location.pvpArenaLocation");
      World world = location.getWorld();
      if (world == null) world = Bukkit.getWorld("world");
      return world;
   }

   public static World creativeWorld() {
      Location location = Configuration.getLocation("location.creativeLocation");
      World world = location.getWorld();
      if (world == null) world = Bukkit.getWorld("world");
      return world;
   }

   public static World survivalWorld() {
      Location location = Configuration.getLocation("location.survivalLocation");
      World world = location.getWorld();
      if (world == null) world = Bukkit.getWorld("world");
      return world;
   }

   public static void setDefaults() {
      for (World world : Bukkit.getWorlds()) {
         world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
         world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
      }
   }

   public static boolean inArenaHub(Player player) {
      Location corner1 = Configuration.getLocation("arenaHub.pos1");
      Location corner2 = Configuration.getLocation("arenaHub.pos2");
      Location playerLocation = player.getLocation();

      double x1 = Math.min(corner1.getX(), corner2.getX());
      double x2 = Math.max(corner1.getX(), corner2.getX());
      double y1 = Math.min(corner1.getY(), corner2.getY());
      double y2 = Math.max(corner1.getY(), corner2.getY());
      double z1 = Math.min(corner1.getZ(), corner2.getZ());
      double z2 = Math.max(corner1.getZ(), corner2.getZ());

      return (playerLocation.getX() >= x1 && playerLocation.getX() <= x2
              && playerLocation.getY() >= y1 && playerLocation.getY() <= y2
              && playerLocation.getZ() >= z1 && playerLocation.getZ() <= z2);
   }

}
