package dev.jairusu.panadero.Events;

import dev.jairusu.panadero.Methods.WorldGroups;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.List;

public class PlayerDeath implements Listener {

   @EventHandler
   public void onPlayerDeath(PlayerDeathEvent event) {
      Player player = event.getPlayer();
      World playerWorld = player.getWorld();

      Component deathMessage = event.deathMessage();
      if (deathMessage == null) return;

      List<String> worldGroups = WorldGroups.worldGroups(playerWorld);
      for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
         if (!worldGroups.contains(onlinePlayer.getWorld().getName())) continue;
         onlinePlayer.sendMessage(deathMessage);
      }
   }

}
