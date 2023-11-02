package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.*;
import fr.xephi.authme.events.LoginEvent;
import fr.xephi.authme.events.LogoutEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuit implements Listener {

   @EventHandler
   public void onPlayerJoin(PlayerJoinEvent event) {
      event.joinMessage(null);
      Player player = event.getPlayer();
      player.teleport(Utilities.authLocation());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         player.hidePlayer(Configuration.plugin, onlinePlayer);
         onlinePlayer.hidePlayer(Configuration.plugin, player);
      }
   }

   @EventHandler
   public void onPlayerLogin(LoginEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      player.teleport(Utilities.spawnLocation());
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         World onlineWorld = onlinePlayer.getWorld();
         if (!onlineWorld.equals(playerWorld)) continue;
         player.showPlayer(Configuration.plugin, onlinePlayer);
         if (onlinePlayer.getInventory().contains(LobbyItem.GRAY_DYE())) continue;
         onlinePlayer.showPlayer(Configuration.plugin, player);
      }
      player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
      Utilities.giveItems(player);
   }

   @EventHandler
   public void onPlayerQuit(PlayerQuitEvent event) {
      event.quitMessage(null);
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         Utilities.sendQuitMessage(player, playerWorld, onlinePlayer);
      }
   }

   @EventHandler
   public void onPlayerLogout(LogoutEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         player.hidePlayer(Configuration.plugin, onlinePlayer);
         onlinePlayer.hidePlayer(Configuration.plugin, player);
         Utilities.sendLogoutMessage(player, playerWorld, onlinePlayer);
      }
      player.teleport(Utilities.authLocation());
      player.getInventory().clear();
   }

   @EventHandler
   public void onPlayerQuitAFK(PlayerQuitEvent event) {
      Player player = event.getPlayer();
      AFKManager.lastMovementHashMap.remove(player);
      if (!AFKManager.afkStatusHashMap.containsKey(player)) return;
      AFKManager.afkStatusHashMap.remove(player);
      AFKManager.removeToAFKTeam(player, AFKManager.afkTeam());
   }


}
