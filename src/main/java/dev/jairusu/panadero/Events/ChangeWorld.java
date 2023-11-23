package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.*;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.List;

public class ChangeWorld implements Listener {

   @EventHandler
   public void onChangeWorld(PlayerChangedWorldEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();
      World previousWorld = event.getFrom();

      String playerWorldName = playerWorld.getName();
      String previousWorldName = previousWorld.getName();
      List<String> worldGroup = WorldGroups.worldGroups(playerWorld);
      List<String> previousGroup = WorldGroups.worldGroups(previousWorld);

      if (worldGroup.contains(playerWorldName) && !worldGroup.contains(previousWorldName)) {
         if (playerWorld.equals(WorldGroups.lobbyWorld())) {
            player.playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1, 1);
            LobbyItem.give(player);
         }

         for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!worldGroup.contains(onlinePlayer.getWorld().getName())) continue;
            player.showPlayer(Configuration.plugin, onlinePlayer);
            onlinePlayer.showPlayer(Configuration.plugin, player);
            MSGManager.sendJoinMessage(player, playerWorld, onlinePlayer);
         }
      }

      if (previousGroup.contains(previousWorldName) && !previousGroup.contains(playerWorldName)) {
         for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (!previousGroup.contains(onlinePlayer.getWorld().getName())) continue;
            player.hidePlayer(Configuration.plugin, onlinePlayer);
            onlinePlayer.hidePlayer(Configuration.plugin, player);
            MSGManager.sendQuitMessage(player, previousWorld, onlinePlayer);
         }
      }
   }

   @EventHandler
   public void changeWorldAFK(PlayerChangedWorldEvent event) {
      Player player = event.getPlayer();
      AFKManager.lastMovementHashMap.remove(player);
      if (!AFKManager.afkStatusHashMap.containsKey(player)) return;
      AFKManager.afkStatusHashMap.remove(player);
      AFKManager.removeToAFKTeam(player, AFKManager.afkTeam());
   }

}
