package dev.jairusu.panadero.Methods;

import fr.xephi.authme.api.v3.AuthMeApi;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.List;

public class Utilities {

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

   public static boolean isAuthenticated(Player player) {
      return AuthMeApi.getInstance().isAuthenticated(player);
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

   public static void giveItems(Player player) {
      player.getInventory().clear();
      player.getInventory().setItem(8, LobbyItem.LIME_DYE());
      player.getInventory().setItem(4, LobbyItem.COMPASS());
      player.getInventory().setItem(0, LobbyItem.INFO_BOOK());
   }

   public static void sendJoinMessage(Player player, World world, Player onlinePlayer) {
      List<String> worldGroup = Utilities.worldGroups(world);
      if (!Utilities.isAuthenticated(player)) return;
      String joinMessage = Configuration.getString("message.joinMessage");
      if (joinMessage == null || joinMessage.isEmpty()) return;
      joinMessage = joinMessage.replace("%player%",player.getName());

      if (!worldGroup.contains(onlinePlayer.getWorld().getName())) return;
      if (world.equals(Utilities.lobbyWorld())) return;
      onlinePlayer.sendMessage(Configuration.text(joinMessage));
   }

   public static void sendQuitMessage(Player player, World world, Player onlinePlayer) {
      List<String> worldGroup = Utilities.worldGroups(world);
      if (!Utilities.isAuthenticated(player)) return;
      String quitMessage = Configuration.getString("message.quitMessage");
      if (quitMessage == null || quitMessage.isEmpty()) return;
      quitMessage = quitMessage.replace("%player%",player.getName());

      if (!worldGroup.contains(onlinePlayer.getWorld().getName())) return;
      if (world.equals(Utilities.lobbyWorld())) return;
      onlinePlayer.sendMessage(Configuration.text(quitMessage));
   }

   public static void sendLogoutMessage(Player player, World world, Player onlinePlayer) {
      List<String> worldGroup = Utilities.worldGroups(world);
      String quitMessage = Configuration.getString("message.quitMessage");
      if (quitMessage == null || quitMessage.isEmpty()) return;
      quitMessage = quitMessage.replace("%player%",player.getName());

      if (!worldGroup.contains(onlinePlayer.getWorld().getName())) return;
      if (world.equals(Utilities.lobbyWorld())) return;
      onlinePlayer.sendMessage(Configuration.text(quitMessage));
   }

   public static void showOthers(Player player) {
      List<String> worldGroup = Utilities.worldGroups(player.getWorld());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         if (!Utilities.isAuthenticated(onlinePlayer)) continue;
         player.showPlayer(Configuration.plugin, onlinePlayer);
      }
   }

   public static void hideOthers(Player player) {
      List<String> worldGroup = Utilities.worldGroups(player.getWorld());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
         player.hidePlayer(Configuration.plugin, onlinePlayer);
      }
   }

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
