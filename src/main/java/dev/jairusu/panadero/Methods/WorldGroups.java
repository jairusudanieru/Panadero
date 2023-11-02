package dev.jairusu.panadero.Methods;

import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

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
      Location authLocation = Configuration.getLocation("location.authLocation");
      if (authLocation == null) authLocation = lobbyWorld().getSpawnLocation();
      return authLocation;
   }

   public static Location spawnLocation() {
      Location spawnLocation = Configuration.getLocation("location.spawnLocation");
      if (spawnLocation == null) spawnLocation = lobbyWorld().getSpawnLocation();
      return spawnLocation;
   }

   public static World lobbyWorld() {
      World lobbyWorld = Configuration.getWorld("config.lobbyWorld");
      if (lobbyWorld == null) lobbyWorld = Bukkit.getWorld("world");
      return lobbyWorld;
   }

   public static void setDefaults() {
      for (World world : Bukkit.getWorlds()) {
         world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
         world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
      }
   }
}
